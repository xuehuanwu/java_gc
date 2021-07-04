package com.java.gc;

import java.util.Random;

/**
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC    (garbage-first)
 * =====================================================================================================================
 * UseG1GC是一款面向服务端应用的收集器，把内存划分成多个独立的子区域(Region)，可以近似理解为一个围棋的棋盘
 * Region区域化垃圾收集器: 化整为零，避免全内存扫描，只需要按区域进行扫描即可。每个区域不会固定地为某个代服务，而是按需在年轻代和老年代之间切换。
 * =====================================================================================================================
 * 回收过程
 * 1、初始标记: 只标记GC Root能直接关联到的对象
 * 2、并发标记: 进行GC Root Tracing的过程
 * 3、最终标记: 修正并发标记期间，因程序运行导致标记发生变化的那一部分对象
 * 4、筛选回收: 根据时间来进行价值最大化的回收
 * =====================================================================================================================
 * 常用配置参数
 * -XX:G1HeapRegionSize=n: 设置G1区域的大小，值是2的幂，范围是1M到32M。目标是根据最小的Java堆大小划分出约2048个区域
 * -XX:MaxGCPauseMillis=n: 最大GC停顿时间(单位毫秒)，这是个软目标，JVM将尽可能(但不保证)停顿小于这个时间
 * -XX:InitiatingHeapOccupancyPercent=n: 堆占用了多少的时候就触发GC，默认为45
 * -XX:ConcGCThreads=n: 并发GC使用的线程数
 * -XX:G1ReservePercent=n: 设置作为空闲空间的预留内存百分比，以降低目标空间溢出的风险，默认为10%
 */
public class G1Demo {

    public static void main(String[] args) {
        System.out.println("GCDemo hello!");
        try {
            String str = "java";
            while (true) {
                str += str + new Random().nextInt(111) + new Random().nextInt(222);
                str.intern();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
