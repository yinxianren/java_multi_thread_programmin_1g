package org.yinxianren.com.chapter01;

import org.junit.Test;
import org.yinxianren.com.entity.SynchronizedObject;

/**
 * Created with IntelliJ IDEA.
 * User: panda
 * Date: 2019/11/3
 * Time: 下午3:11
 * Description:
 */
public class SuspendThread {


    /**
     *   测试： suspend()  和 resume()
     * @throws InterruptedException
     *
     * index:2184831313
     * index:2184831313
     * -------------------
     * index:4401729711
     * index:4401729711
     */
    @Test
    public  void test01() throws InterruptedException {
        class Test extends Thread{
            private long index=0;

            @Override
            public void run() {
                while (true)
                    index += 1 ;
            }

            public long getIndex() {
                return index;
            }

            public void setIndex(long index) {
                this.index = index;
            }
        }

        Test t= new Test();
        t.start();
        Thread.sleep(3000);
        //A阶段
        t.suspend();
        System.out.println("index:"+t.getIndex());
        Thread.sleep(3000);
        System.out.println("index:"+t.getIndex());
        System.out.println("-------------------");
        //B阶段
        t.resume();
        Thread.sleep(3000);
        t.suspend();
        System.out.println("index:"+t.getIndex());
        Thread.sleep(3000);
        System.out.println("index:"+t.getIndex());


    }

    /**
     *  测试使用suspend来暂停线程，结果是独占公共资源，使得其他线程无法获取特定资源
     * @throws InterruptedException
     *
     *  ------------begin----------
     * thread name:thread-me-01	 into suspend stats
     * 开始执行第二个线程！
     * Process finished with exit code 0
     */
    @Test
    public void test02() throws InterruptedException {
        SynchronizedObject synchronizedObject = new SynchronizedObject();
        Thread t1= new Thread(){
            @Override
            public void run() {
                super.run();
                synchronizedObject.printString2();
            }
        };
        t1.setName("thread-me-01");
        t1.start();
        Thread.sleep(1000);


        Thread t2= new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println("开始执行第二个线程！");
                synchronizedObject.printString2();
            }
        };
        t2.setName("thread-me-02");
        t2.start();
        t2.join(5000);
    }

    /**
     *  测试另外一种独占锁的情况； 该情况导致的原因：println()方法加锁了，导致main线程无法无法获得该锁
     * @throws InterruptedException
     *
     * 527355
     * 527356
     * 527357
     * 527358
     *
     * Process finished with exit code 130 (interrupted by signal 2: SIGINT)
     */
    @Test
    public void test03() throws InterruptedException {
        class Test extends Thread{
            int i =1 ;
            @Override
            public void run() {
                while (true){
                    System.out.println(i ++ );
                }
            }
        }

        Test t = new Test();
        t.start();
        Thread.sleep(1000);
        t.suspend();
        System.out.println("main end");
    }

}
