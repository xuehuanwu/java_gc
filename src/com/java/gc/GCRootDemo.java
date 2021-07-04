package com.java.gc;

/**
 * 在java中，可作为GC Root的对象有：
 * 1、虚拟机栈(栈帧中的本地变量表)中引用的对象
 * 2、方法区中的类静态属性引用的对象
 * 3、方法区中常量引用的对象
 * 4、本地方法栈中JNI(即native方法)引用的对象
 */
public class GCRootDemo {

    // 静态属性
    private static String str;
    // 常量
    private final byte[] bytes = new byte[100 * 1024 * 1024];

    public static void main(String[] args) {
        // 虚拟机栈
        GCRootDemo t1 = new GCRootDemo();

        System.gc();
        System.out.println("第一次GC完成");
    }
}
