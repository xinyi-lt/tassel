package com.zuoquan.lt.sensitive;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by liutao on 2017/6/16.
 */
@Aspect
@Component
public class SensitiveDataAspect {
	
    private static final Logger LOGGER = LoggerFactory.getLogger("com.touna.sensitive.aop");
    
    /** 加密(远程不处理) */
    private static final String ENCRYPT = "ENCRYPT";
    
    /** 加密(远程保存) */
    private static final String ENCRYPTANDSAVE = "ENCRYPTANDSAVE";
    
    /** 解密(远程服务) */
    private static final String DECODE = "DECODE";

//    @Pointcut("@annotation(com.touna.loan.biz.security.SensitiveParamHandler)")
    @Pointcut("(execution(* com.touna.loan.*.dao.intf..*.*(..)))")
    public void sensitiveDataCut(){

    }

    @Around("sensitiveDataCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature ms=(MethodSignature) joinPoint.getSignature();
        Method method=ms.getMethod();

        boolean isAnnotation = method.isAnnotationPresent(SensitiveMethod.class);


        if(isAnnotation){
            SensitiveMethod annotationInfo = method.getAnnotation(SensitiveMethod.class);
            MethodType methodType = annotationInfo.methodType();

            Object[] args = joinPoint.getArgs();

            if(MethodType.SAVE.equals(methodType)
                    || MethodType.UPDATE.equals(methodType)){

                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                if (parameterAnnotations == null || parameterAnnotations.length == 0) {
                    return null;
                }

                int paramIndex = 0;

                for (Annotation[] parameterAnnotation : parameterAnnotations) {
                    for (Annotation annotation : parameterAnnotation) {
                        //加了SensitiveParam注解的参数，进行处理
                        if (annotation instanceof SensitiveParam) {
                            Object paramObj = args[paramIndex];
                            //1.参数类型为String
                            if (paramObj instanceof String) {

                                String paramStr = (String) paramObj;
                                if(null != paramStr && !paramStr.trim().equals("")){
                                    args[paramIndex] = getMessageDigest(paramStr);
                                }

                            } else if (paramObj instanceof List){
                                Type paramType = method.getGenericParameterTypes()[paramIndex];
                                if(paramType instanceof ParameterizedType){
                                    ParameterizedType pt = (ParameterizedType) paramType;
                                    Class cls = (Class) pt.getActualTypeArguments()[0];

                                    if(cls.isAssignableFrom(String.class)) {
                                        //String
                                        args[paramIndex] = getMessageDigestForList((List<String>) paramObj);
                                    }else {
                                        //model类型
                                        List<Field> fieldList = new ArrayList<Field>();

                                        for (Field field : cls.getDeclaredFields()) {
                                            if (field.isAnnotationPresent(SensitiveField.class)){
                                                field.setAccessible(true);
                                                fieldList.add(field);
                                            }
                                        }

                                        for (Object model  : (List)paramObj) {
                                            for (Field field : fieldList) {
                                                Object obj = field.get(model);
                                                if(obj instanceof String){
                                                    String fieldStr = (String) obj;
                                                    if(null != fieldStr && !fieldStr.trim().equals("")){
                                                        field.set(model, getMessageDigest(fieldStr));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            } else if (paramObj instanceof Map){

                            } else if (paramObj.getClass().isArray()){

                            }else {
                                Field[] fields = paramObj.getClass().getDeclaredFields();
                                for (Field field : fields) {
                                    if (field.isAnnotationPresent(SensitiveField.class)){
                                        field.setAccessible(true);
                                        Object obj = field.get(paramObj);
                                        if(obj instanceof String){
                                            String fieldStr = (String) obj;
                                            if(null != fieldStr && !fieldStr.trim().equals("")){
                                                field.set(paramObj, getMessageDigest(fieldStr));
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                    paramIndex++;
                }

                return joinPoint.proceed(args);

            } else if(MethodType.GET.equals(methodType)){
            	
            	//加密处理参数
            	dealParameter(method, args,ENCRYPT);
            	
                Object resultObj = joinPoint.proceed(args);
                
                //解密处理结果
                deal(resultObj,DECODE);

                return resultObj;
            }
        }

        return joinPoint.proceed();

    }
    
    /**
     * 
     * @author GYL 
     * @date 2017年6月20日 下午5:27:16 
     * @param  method
     * @param  args
     * @param  type 处理方式(加密，解密)
     * @return void     
     * @throws 
     * @description:处理参数
     */
    private void dealParameter(Method method,Object[] args,String type) {
    	try {
	    	Annotation[][] parameterAnnotations = method.getParameterAnnotations();
	    	
	        int paramIndex = 0;
	        for (Annotation[] parameterAnnotation : parameterAnnotations) {
	            for (Annotation annotation : parameterAnnotation) {
	                //加了SensitiveParam注解的参数，进行处理
	                if (annotation instanceof SensitiveParam) {
	                    Object paramObj = args[paramIndex];
	                    
	                    deal(paramObj,type);
	                }
	            }
	            paramIndex++;
	        }
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * 
     * @author GYL 
     * @date 2017年6月20日 下午5:19:01 
     * @param obj
     * @param type 处理方式(加密，解密)
     * @return void     
     * @throws 
     * @description:根据不同数据类型，做相应的处理
     */
	private void deal(Object obj,String type) {
		if(obj instanceof List){
    		List<Object>  objList = (List)obj;
    		int objSize = objList.size();
    		for (int i = 0; i < objSize; i++) {
    			Object obj1 = objList.get(i);
    			if(obj1 instanceof String){
    				changeString(obj1,type);
    			}else {
    				changeBeanObject(obj1,type);
				}
			}
    	}else if(obj instanceof String){
    		changeString(obj,type);
		} else if (obj instanceof Map){

        } else if (obj.getClass().isArray()){

        } else{
			changeBeanObject(obj,type);
		}
	}
	/**
	 * 
	 * @author GYL 
	 * @date 2017年6月20日 下午5:20:06 
	 * @param obj
	 * @param type 处理方式(加密，解密)
	 * @return void     
	 * @throws 
	 * @description:修改实体bean类型
	 */
	private void changeBeanObject(Object obj,String type) {
		List<Field>  list = Arrays.asList(obj.getClass().getDeclaredFields());
    	int size = list.size();
    	Field field = null;
    	for(int i=0 ;i < size; i++){
    		field = list.get(i);
    		if(field.isAnnotationPresent(SensitiveField.class)){
    			try {
	    			if(field.get(obj) instanceof String){
	    				field.setAccessible(true);
	    				if(type == DECODE){
	    					System.out.println("调用服务解密查询,返回json");
	    					field.set(obj,"123456");
		    			}else if(type == ENCRYPTANDSAVE){
		    				String md5Str = getMessageDigest((String)field.get(obj));
		    				System.out.println("调用服务保存,返回json");
	    					field.set(obj,md5Str);
						}else if (type == ENCRYPT) {
							String md5Str = getMessageDigest((String)field.get(obj));
							field.set(obj,md5Str);
						}
	    			}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		}
    	}
	}
	/**
	 * 
	 * @author GYL 
	 * @date 2017年6月20日 下午5:20:33 
	 * @param obj
	 * @param type 处理方式(加密，解密)
	 * @return void     
	 * @throws 
	 * @description:修改String类型的值
	 */
	private void changeString(Object obj,String type) {
		
		 if(obj instanceof String){
			String objString = (String)obj;
			try {
				Field field  = objString.getClass().getDeclaredField("value");
				field .setAccessible(true); 
				if(type == DECODE){
					System.out.println("调用服务解密查询,返回json");
					field.set(objString, "123456".toCharArray());
    			}else if(type == ENCRYPTANDSAVE){
    				String md5Str = getMessageDigest(objString);
    				System.out.println("调用服务保存,返回json");
					field.set(obj,md5Str);
				}else if (type == ENCRYPT) {
					String md5Str = getMessageDigest(objString);
					field.set(obj,md5Str);
				}
			
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}

    /**
     * 获取MD5信息摘要字符串
     * @param msg
     * @return
     * @throws NoSuchAlgorithmException
     */
    private String getMessageDigest(String msg) throws NoSuchAlgorithmException {
        return MD5Util.getMD5Hex(msg);
    }

    private List<String> getMessageDigestForList(List<String> msgList) throws NoSuchAlgorithmException {
        List<String> msgDigestList = new ArrayList<String>();
        for (String msg : msgList) {
            msgDigestList.add(getMessageDigest(msg));
        }
        return msgDigestList;
    }
}
