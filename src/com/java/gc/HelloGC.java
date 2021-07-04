package com.java.gc;

/**
 * 如何查看一个正在运行中java程序，它的某个jvm参数是否开启？具体值是多少？
 * 第一种方法：
 * 1、jps -l
 * 2、jinfo -flag XXX(属性) XXX(进程编号)  查看jvm某个属性值(或是否开启)
 * jinfo -flags XXX(进程编号)           查看jvm所有参数值
 * 第二种方法：
 * java -XX:+PrintFlagsInitial                  查看jvm初始化参数
 * java -XX:+PrintFlagsFinal -version           查看jvm修改更新值(=表示初始值 :=表示修改更新)
 * java -XX:+PrintCommandLineFlags -version     查看jvm默认垃圾回收器(jdk8默认并行垃圾回收器)
 * =====================================================================================================================
 * jvm参数
 * 1、标配参数：-version -help -showversion
 * 2、X参数：-Xint解释执行 -Xcomp第一次使用就编译成本地代码 -Xmixed混合模式
 * 3、XX参数
 * 3.1 Boolean类型
 * -XX:+XXX  表示开启
 * -XX:-XXX  表示关闭
 * 3.2 KV设值类型
 * -XX:key=value
 * =====================================================================================================================
 * jvm常用参数
 * -Xms 等价于 -XX:InitialHeapSize 初始大小内存，默认为物理内存1/64
 * -Xmx 等价于 -XX:MaxHeapSize     最大分配内存，默认为物理内存1/4
 * -Xss 等价于 -XX:ThreadStackSize 设置单个线程栈的大小，一般默认为512k~1024k，默认值跟平台有关(linux64位默认为1024k)
 * -Xmn 设置年轻代大小
 * -XX:MetaspaceSize               设置元空间大小
 * 元空间的本质和永久代类似，都是对jvm规范中方法区的实现。区别在于，元空间并不在虚拟机中，而是使用本地内存，默认情况下，元空间的大小仅受本地内存限制
 * -XX:+PrintGCDetails  输出详细GC收集日志信息
 * -XX:SurvivorRatio    设置新生代中eden区的比例值，S0/S1相同。默认Eden:S0:S1=8:1:1
 * -XX:NewRatio         设置老年代的占比，剩下的1给新生代。默认-XX:NewRatio=2，即新生代占1，老年代占2
 * -XX:MaxTenuringThreshold     设置垃圾最大年龄(jdk8最大值15)
 * =====================================================================================================================
 * 典型jvm调优案例
 * -Xms2048m -Xmx2048m -Xss1024k -XX:MetaspaceSize=512m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:UseSerialGC
 * 初始内存  最大堆内存  初始栈大小 元空间大小   打印HotSpotVM 采用的自动优化参数     打印GC详细日志    串行垃圾回收器
 */
public class HelloGC {

    // 栈管运行，堆管存储
    public static void main(String[] args) throws Exception {
        hello();
//        memory();
    }

    private static void hello() throws InterruptedException {
        System.out.println("hello java");
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void memory() {
        // jvm内存总量
        long totalMemory = Runtime.getRuntime().totalMemory();
        // jvm最大内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("TOTAL_MEMORY(-Xms)=" + totalMemory + "(字节)、" + (totalMemory / 1024 / 1024) + "MB");
        System.out.println("MAX_MEMORY(-Xmx)=" + maxMemory + "(字节)、" + (maxMemory / 1024 / 1024) + "MB");
    }
}
