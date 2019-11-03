package org.yinxianren.com.chapter01;

/**
 * Created with IntelliJ IDEA.
 * User: panda
 * Date: 2019/11/2
 * Time: 下午4:01
 * Description:
 */

import org.junit.Test;
import org.yinxianren.com.entity.SynchronizedObject;

/**
 *
 * 1.停止一个线程意味着在线程处理完成之前停掉正在操作，也就是放弃当前的操作；
 * 2.Thread.stop() 可以停止一个线程，但是这个方法是线程不安全的；
 * 3.大多数停止一个线程的操作使用Thread.interrupt()方法，但这个方法不会终止一个正在运行的线程，还需要加入一个判断可以完成线程的停止；
 *
 * java 中有3种方式来结束线程
 * 1.使用退出标示，使线程正常退出，也就是但run方法完成后线程终止
 * 2.使用stop方法强制终止线程，但是这种方法不推荐，stop、suspend、resume都是被废弃的方法；
 * 3.使用interrupt方法中断线程
 *
 *
 */
public class StopThread {

    /**
     *  interrupt()方法，单独使用是没有效果的
     * @throws InterruptedException
     */
    @Test
    public void test01() throws InterruptedException {
        class Test extends  Thread{
            @Override
            public void run() {
                for(int i=0;i<500;i++){
                    System.out.println(i);
                }
            }
        }

        Test  t = new Test();
        t.start();
        t.join();
        t.interrupt();
    }

    /**
     *   isInterrupted() 判断当前线程是否已经中断
     * @throws InterruptedException
     */
    @Test
    public void test02() throws InterruptedException {
        class Test extends  Thread{
            @Override
            public void run() {
                for(int i=0;i<500;i++){
                    System.out.println(i);
                }
            }
        }

        Test  t = new Test();
        t.start();
        t.interrupt();
        System.out.println(t.interrupted());//false
        System.out.println(t.interrupted());//false
        System.out.println(t.interrupted());//false
        t.join();
        System.out.println(t.interrupted());//false
        System.out.println(t.interrupted());//false
        System.out.println(t.interrupted());//false
    }

    /**
     *  this.interrupted() 测试当前线程是否终止
     *  从测试结果来看，第1个true,子线程还在执行中，但线程并没有中断，第1个false,子线程已经正常运行结束了
     *
     *  结果：interrupted(),判断线程还没结算之前，是否被打上interrupt标记，线程结算就无效了
     * @throws InterruptedException
     */
    @Test
    public void test03() throws InterruptedException {
        class Test extends  Thread{
            @Override
            public void run() {
                for(int i=0;i<500;i++){
                    System.out.println(i);
                }
            }
        }

        Test  t = new Test();
        t.start();
        t.interrupt();
        System.out.println(t.isInterrupted());//true
        System.out.println(t.isInterrupted());//true
        System.out.println(t.isInterrupted());//true
        t.join();
        System.out.println(t.isInterrupted());//false
        System.out.println(t.isInterrupted());//false
        System.out.println(t.isInterrupted());//false
    }


    /**
     *
     * @throws InterruptedException
     *
     * 1486
     * 1487
     * 1488
     * 1489
     * 线程已经结束了，退出
     * 我还是会执行的！！哈哈.....
     *
     *结果：只是结算for循环的代码，并没有结束run方法
     */
    @Test
    public void test04() throws InterruptedException {
        class Test extends  Thread{
            @Override
            public void run() {
                for(int i=0;i<50000;i++){
                    if(this.interrupted()){
                        System.out.println("线程已经结束了，退出");
                        break;
                    }
                    System.out.println(i);
                }
                System.out.println("我还是会执行的！！哈哈.....");
            }

        }

        Test  t = new Test();
        t.start();
        Thread.sleep(10);
        t.interrupt();
        t.join();
    }

    /**
     *
     * @throws InterruptedException
     *1130
     * 1131
     * 线程已经结束了，退出
     * java.lang.InterruptedException
     * 	at org.yinxianren.com.chapter01.StopThread$5Test.run(StopThread.java:154)
     *
     * 	结果：线程真正结算了
     */
    @Test
    public void test05() throws InterruptedException {
        class Test extends  Thread{
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 50000; i++) {
                        if (this.interrupted()) {
                            System.out.println("线程已经结束了，退出");
                            throw new InterruptedException();
                        }
                        System.out.println(i);
                    }
                    System.out.println("我还是会执行的！！哈哈.....");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        Test  t = new Test();
        t.start();
        Thread.sleep(10);
        t.interrupt();
        t.join();
    }

    /**
     * 测试： return 和 interrupted 结束中断线程执行
     * @throws InterruptedException
     *
     * 1614
     * 1615
     * 1616
     * 1617
     * 线程已经结束了，退出
     */
    @Test
    public void test05_1() throws InterruptedException {
        class Test extends  Thread{
            @Override
            public void run() {

                for (int i = 0; i < 50000; i++) {
                    if (this.interrupted()) {
                        System.out.println("线程已经结束了，退出");
                        return ;
                    }
                    System.out.println(i);
                }
                System.out.println("我还是会执行的！！哈哈.....");
            }
        }

        Test  t = new Test();
        t.start();
        Thread.sleep(10);
        t.interrupt();
        t.join();
    }

    /**
     * 睡眠中，终止线程测试
     * @throws InterruptedException
     *
     *
     * before sleep
     * java.lang.InterruptedException: sleep interrupted
     * 	at java.lang.Thread.sleep(Native Method)
     * 	at org.yinxianren.com.chapter01.StopThread$6Test.run(StopThread.java:191)
     *
     * 	结果： 线程可以被终止
     */
    @Test
    public void test06() throws InterruptedException {

        class Test extends  Thread{
            @Override
            public void run() {
                try {
                    System.out.println("before sleep");
                    Thread.sleep(200000);
                    System.out.println("after sleep");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Test  t = new Test();
        t.start();
        Thread.sleep(3000);
        t.interrupt();
        t.join();
    }


    /**
     *  测试暴力停止
     * @throws InterruptedException
     *
     * 27
     * 28
     * 29
     * 30
     *
     *
     *
     * Process finished with exit code 0
     *
     *  结果：异常信息也没有
     */
    @Test
    public void test07() throws InterruptedException {

        class Test extends  Thread{
            @Override
            public void run() {
                try {
                    int i = 0;
                    while (true) {
                        System.out.println(++i);
                        Thread.sleep(100);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        Test  t = new Test();
        t.start();
        Thread.sleep(3000);
        t.stop();
        t.join();
    }

    /**
     *  测试 stop() 和ThreadDeath，
     * 18
     * 19
     * 20
     * java.lang.ThreadDeath
     * 	at java.lang.Thread.stop(Thread.java:853)

     * @throws InterruptedException
     */
    @Test
    public void test08() throws InterruptedException {

        class Test extends  Thread{
            @Override
            public void run() {
                try {
                    int i = 0;
                    while (true) {
                        System.out.println(++i);
                        Thread.sleep(100);
                    }
                }catch (ThreadDeath e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Test  t = new Test();
        t.start();
        Thread.sleep(2000);
        t.stop();
        t.join();
    }

    /**
     *  stop 释放锁的不良后果，程序中尽量不要使用stop方法
     *
     * @throws InterruptedException
     *
     *  java.lang.ThreadDeath
     * 	at java.lang.Thread.stop(Thread.java:853)
     * name:航海号
     * pwd:123698745
     */
    @Test
    public void test09() throws InterruptedException {

        class Test extends  Thread{

            SynchronizedObject synchronizedObject;
            public Test(SynchronizedObject synchronizedObject){
                this.synchronizedObject = synchronizedObject;
            }

            @Override
            public void run() {
                try {
                    synchronizedObject.printString("清水号","abc123456");
                }catch (ThreadDeath e){
                    e.printStackTrace();
                }
            }
        }
        SynchronizedObject synchronizedObject = new SynchronizedObject();
        synchronizedObject.setUserName("航海号");
        synchronizedObject.setPassword("123698745");
        Test  t = new Test(synchronizedObject);
        t.start();
        t.stop();
        t.join();

        System.out.println("name:"+synchronizedObject.getUserName());
        System.out.println("pwd:"+synchronizedObject.getPassword());
    }
}
