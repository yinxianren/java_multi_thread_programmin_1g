package org.yinxianren.com.chapter01;

/**
 * 发生线程不安全
 *
 * i=5	thread=Thread-2
 * i=5	thread=Thread-1
 * i=4	thread=Thread-3
 * i=3	thread=Thread-4
 * i=2	thread=Thread-5
 *
 *  虽然println 方法进行同步，当是 i-- ，是发生在println 方法之前，所以可能会出现线程不安全的问题，
 *  public void println(String x) {
 *         synchronized (this) {
 *             print(x);
 *             newLine();
 *         }
 *     }
 *
 *
 *  解决方法：
 *    public void run(){
 *     synchronized (this) {
 *         System.out.println("i=" + (i--) + "\tthread="+Thread.currentThread().getName());
 *      }
 *    }
 *
 */

public class MyThread extends Thread {

    public MyThread(){
        System.out.println("constructor-->currentThread.getName():"+Thread.currentThread().getName());
        System.out.println("constructor-->this.getName():"+this.getName());
    }

    /*
run i=5	thread=A	 this.name=Thread-0
run i=4	thread=B	 this.name=Thread-0
run i=3	thread=C	 this.name=Thread-0
run i=2	thread=D	 this.name=Thread-0
run i=1	thread=E	 this.name=Thread-0
     */
    private int i = 5;
    public void run(){  //run 方法是一个自调用方法，不是有main线程启动的
        //currentThread() 返回代码段正被那个线程调用信息  .run i=5	thread=A	 this.name=Thread-0
        System.out.println("run i=" + (i--) + "\tthread="+Thread.currentThread().getName()+"\t this.name="+this.getName());
    }

}


class Run{

    public static void main(String[] args){
        System.out.println("main currentThread.getName="+Thread.currentThread().getName());
        MyThread run = new MyThread();
        Thread t1 = new Thread(run,"A");
        Thread t2 = new Thread(run,"B");
        Thread t3 = new Thread(run,"C");
        Thread t4 = new Thread(run,"D");
        Thread t5 = new Thread(run,"E");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();


    }

}