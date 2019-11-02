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

    private int i = 5;
    public void run(){
        System.out.println("i=" + (i--) + "\tthread="+Thread.currentThread().getName());
    }

}


class Run{

    public static void main(String[] args){
        MyThread run = new MyThread();
        Thread t1 = new Thread(run);
        Thread t2 = new Thread(run);
        Thread t3 = new Thread(run);
        Thread t4 = new Thread(run);
        Thread t5 = new Thread(run);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();


    }

}