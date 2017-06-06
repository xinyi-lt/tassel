package com.zuoquan.lt.pattern.proxy;

import com.zuoquan.lt.entity.User;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/6/6.
 */
public class Test {
    public static void main(String[] args) {
        // JDK动态代理
        testJDKProxy();

        // Cglib接口代理
        testCglibInterfaceProxy();

        // Cglib类代理
        testCglibClassProxy();
    }

    private static void testJDKProxy() {
        User user = new User();
        user.setName("tom");
        UserProxy.getUserProxy().saveUser(user);
    }

    static class UserProxy{

        private static final InvocationHandler USER_HANDLE = new InvocationHandler() {


            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("JDK接口动态代理-开始保存用户");
                Object result = method.invoke(new UserInterfaceImpl(), args);
                System.out.println("JDK接口动态代理-保存用户结果: " + result);
                System.out.println();
                return result;
            }
        };

        public static UserInterface getUserProxy() {
            UserInterface userInterface = (UserInterface) Proxy.newProxyInstance(UserProxy.class.getClassLoader(),
                    new Class[] { UserInterface.class }, USER_HANDLE);
            return userInterface;
        }
    }

    private static void testCglibInterfaceProxy() {
        User user = new User();
        user.setName("tom");
        UserCglibProxy.getUserProxy().saveUser(user);
    }

    static class UserCglibProxy {

        private static final net.sf.cglib.proxy.InvocationHandler USER_HANDLE = new net.sf.cglib.proxy.InvocationHandler() {

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("Cglib接口动态代理-开始保存用户");
                Object result = method.invoke(new UserInterfaceImpl(), args);
                System.out.println("Cglib接口动态代理-保存用户结果: " + result);
                System.out.println();
                return result;
            }
        };

        public static UserInterface getUserProxy() {
            UserInterface userInterface = (UserInterface) net.sf.cglib.proxy.Proxy.newProxyInstance(
                    UserCglibProxy.class.getClassLoader(), new Class[] { UserInterface.class }, USER_HANDLE);
            return userInterface;
        }
    }

    private static void testCglibClassProxy() {
        User user = new User();
        user.setName("tom");
        UserInterfaceImpl userImpl = (UserInterfaceImpl) ClassCgLibProxy.getUserProxy(new UserInterfaceImpl());
        userImpl.saveUser(user);
    }

    static class ClassCgLibProxy {

        private static final MethodInterceptor USER_HANDLE = new MethodInterceptor() {

            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("Cglib类动态代理-开始保存用户");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("Cglib类动态代理-保存用户结果: " + result);
                System.out.println();
                return result;
            }
        };

        public static Object getUserProxy(Object target) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(target.getClass());
            enhancer.setCallback(USER_HANDLE);
            return enhancer.create();
        }
    }
}
