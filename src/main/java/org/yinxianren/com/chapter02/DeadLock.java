package org.yinxianren.com.chapter02;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: panda
 * Date: 2019/11/3
 * Time: 下午7:28
 * Description:
 */
public class DeadLock {

    @Test
    public void test() throws InterruptedException {

        class DealThread implements Runnable{
            public Object object1 = new Object();
            public Object object2 = new Object();
            public void run(){
                try {
                    if (Thread.currentThread().getName().equalsIgnoreCase("a")) {
                        synchronized (object1) {
                            System.out.println(Thread.currentThread().getName());
                            Thread.sleep(3000);
                            synchronized (object2){
                                System.out.println("A->B");
                            }
                        }
                    } else if (Thread.currentThread().getName().equalsIgnoreCase("b")) {
                        synchronized (object2) {
                            System.out.println(Thread.currentThread().getName());
                            Thread.sleep(3000);
                            synchronized (object1){
                                System.out.println("B->A");
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        DealThread d = new DealThread();
        Thread a = new Thread(d,"a");
        a.start();
        Thread b = new Thread(d,"b");
        b.start();
        b.join();
    }

}
