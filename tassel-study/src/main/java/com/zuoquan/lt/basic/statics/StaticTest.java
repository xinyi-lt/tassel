package com.zuoquan.lt.basic.statics;

/**
 * Created by xmj on 2017/5/29.
 * 解释：http://blog.csdn.net/hyl713/article/details/11925071
 *http://blog.sina.com.cn/s/blog_1552a7b490102w3xk.html
 *
 * http://www.cnblogs.com/sophine/p/3531282.html
 *
 * /构造块：直接在类中定义且没有加static关键字的代码块称为{}构造代码块。
 * 构造代码块在创建对象时被调用，每次创建对象都会被调用，并且构造代码块的执行次序优先于类构造函数。
 *
 * 普通代码块：在方法或语句中出现的{}就称为普通代码块。普通代码块和一般的语句执行顺序由他们在代码中出现的次序决定
 *  --“先出现先执行”
 *
 *  静态代码块:在java中使用static关键字声明的代码块。静态块用于初始化类，为类的属性初始化。每个静态代码块只会执行一次。由于JVM在加载类时会执行静态代码块，所以静态代码块先于主方法执行。
 //如果类中包含多个静态代码块，那么将按照"先定义的代码先执行，后定义的代码后执行"。
 //注意：1 静态代码块不能存在于任何方法体内。2 静态代码块不能直接访问静态实例变量和实例方法，需要通过类的实例对象来访问。
 *
 * 一.static
 1.静态代码块：
 代码在项目启动的时候就执行，这种代码是主动执行的，而且只执行一次。他常用做类属性的初始化。static{}
 2.静态方法：
 在类加载的时候就已经加载，在类名调用时才执行，这种代码是被动执行的。
 注意：静态方法里只能调用同类中其他静态成员；
 不能以任何方式引用this和super关键字，因为静态方法使用前不需创建任何对象实例，所以就不存在this。
 3.静态变量：
 属于整个类的变量而不是某个对象，任何方法都不能声明static变量。

 */
public class StaticTest {
    public static int k = 0;
    public static StaticTest t1 = new StaticTest("t1");
    public static StaticTest t2 = new StaticTest("t2");
    public static int i = print("i");
    public static int n = 99;
    public int j = print("j");

    {
        print("构造快");
    }

    static {
        print("静态块");
    }

    public StaticTest(String str) {
        System.out.println((++k) + ":" + str + " i=" + i + " n=" + n);
        ++n;
        ++i;
    }

    public static int print(String str) {
        System.out.println((++k) + ":" + str + " i=" + i + " n=" + n);
        ++i;
        return ++n;
    }
    public static void main(String[] args) {
        StaticTest t = new StaticTest("init");
    }
}
