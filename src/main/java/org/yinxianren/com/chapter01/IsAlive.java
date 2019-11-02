package org.yinxianren.com.chapter01;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: panda
 * Date: 2019/11/2
 * Time: 下午3:06
 * Description:
 */
public class IsAlive {


    /*
       isAlive()的作用是测试线程是否处于活动状态，什么是活动状态呢？ 活动状态就是线程已经 启动，但未终止；线程处于正在运行，或者准备开始运行的状态，就认为线程是存活的
     */

    @Test
    public  void test_01() throws InterruptedException {
        Runnable  runnable = ()->{
            System.out.println("run -->Thread.currentThread.isAlive:"+Thread.currentThread().isAlive() +"\t ThreadName:"+Thread.currentThread().getName());
        };

        Thread thread = new Thread(runnable,"MyThread");
        System.out.println("main-->before starting is alive :"+thread.isAlive());
        thread.start();
        System.out.println("main-->after starting is alive:"+thread.isAlive());
        Thread.sleep(1000);
        System.out.println("main-->after sleep is alive :"+thread.isAlive());
//        main-->before starting is alive :false
//        main-->after starting is alive:true
//        run -->Thread.currentThread.isAlive:true	 ThreadName:MyThread
//        main-->after sleep is alive :false
    }

    class Test01Thread extends Thread{

        public Test01Thread(){
            System.out.println("1:"+Thread.currentThread().getName() + "\t" +Thread.currentThread().isAlive());//1:main	true
            System.out.println("2："+this.getName() + "\t" +this.isAlive());//2：Thread-0	false
        }

        public void run(){
            System.out.println("3:"+Thread.currentThread().getName() + "\t" +Thread.currentThread().isAlive());//3:A	true
            System.out.println("4："+this.getName() + "\t" +this.isAlive());//4：A	true
        }
    }

    @Test
    public void test_02(){
        Test01Thread test =  new Test01Thread();
        test.setName("A");
        test.start();
    }




}
