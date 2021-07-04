package com.java.gc;

import java.util.Random;

/**
 * GC垃圾回收算法，是内存回收的方法论，垃圾收集器就是算法落地实现。
 * 1、引用计数
 * 2、复制拷贝
 * 3、标记清除
 * 4、标记整理
 * ps: 目前还没有完美的收集器，更没有万能的收集器，只是针对具体应用最适合的收集器，进行分代收集
 * =====================================================================================================================
 * 4中主要垃圾收集器
 * 1、串行垃圾回收器(Serial): 为单线程环境设计且只使用一个线程进行垃圾回收，会暂停所有的用户线程。所以不适合服务器环境
 * 2、并行垃圾回收器(Parallel): 多个垃圾收集线程并行工作，此时用户线程是暂停的，适用于科学计算/大数据处理等弱交互场景(高吞吐量场景)
 * 3、并发垃圾回收器(CMS): 用户线程和垃圾收集线程同时执行(不一定是并行，可能交替执行)，不需要停顿用户线程，适用于对响应时间有要求的场景(强交互场景)
 * 4、G1垃圾回收器: 将堆内存分割成不同的区域，然后并发的对其进行垃圾回收
 * =====================================================================================================================
 * java的gc回收类型(7大垃圾收集器)
 * * * Young Gen(新生代)推荐使用:
 * 1、UseSerialGC(串行gc): -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC   (DefNew + Tenured)
 * 2、UseParNewGC(新生代并行gc): -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC   (ParNew + Tenured)
 * ps: -XX:ParallelGCThreads 限制线程数量，默认开启和CPU数目相同的线程数
 * 3、UseParallelGC(并行gc): -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC   (PSYoungGen + ParOldGen)
 * * * Old Gen(老年代)推荐使用:
 * 1、UseSerialOldGC串行老年代gc: 是Serial垃圾收集器老年代版本，jdk8已经被优化掉了，没有了。
 * 2、UseConcMarkSweepGC(并发标记清除gc): -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC   (ParNew + concurrent mark-sweep)
 * ps: ParNew(Young区用)+CMS(Old区用)+Serial Old的收集器组合，Serial Old将作为CMS出错的后背收集器
 * 3、UseParallelOldGC(老年代并行gc): -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC   (PSYoungGen + ParOldGen)
 * ps: jdk6之前(Parallel Scavenge + Serial Old)，jdk8及以后(Parallel Scavenge + Parallel Old)
 * * * UseG1GC: jdk9默认回收类型
 * =====================================================================================================================
 * 并发标记清除(CMS): 必须在老年代堆内存用尽之前完成垃圾回收，否则回收失败，将触发担保机制，串行老年代收集器将会以STW(用户线程全部停止)的方式进行一次GC,从而造成较大停顿时间
 * 1、初始标记(CMS initial mark): 标记GC Root可以直达的对象，耗时短
 * 2、并发标记(CMS concurrent mark): 从第一部标记的对象出发，并发地标记可达对象
 * 3、重新标记(CMS remark): 重新进行标记，修正Concurrent Mark时间由于用户程序运行而导致对象关系间的变化及新创建的对象，耗时短
 * 4、并发清除(CMS concurrent sweep): 并行地进行无用对象的回收
 * 优点：并发收集低停顿
 * 缺点：并发执行对CPU资源压力大，采用的标记清除算法会导致大量碎片
 * =====================================================================================================================
 * GC日志参数说明
 * DefNew: Default New Generation   默认新生代
 * Tenured: Old 老年代
 * ParNew: Parallel New Generation 并行新生代
 * PSYoungGen: Parallel Scavenge 并行新生代
 * ParOldGen: Parallel Old Generation 并行老年代
 */
public class GCDemo {

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
