package org.yinxianren.com.chapter01;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: panda
 * Date: 2019/11/2
 * Time: 下午3:34
 * Description:
 */
public class Sleep {


    class Test02Thread extends Thread{
         //sleep 让当前 “正在执行的线程” 休眠
        //三个sleep 都是让当前线程 test02Thread 休眠 1 s
        @Override
        public void run() {
            try {
                System.out.println("Thread.currentThread().sleep(1000);"+Thread.currentThread().getName());
                Thread.currentThread().sleep(1000);
                System.out.println("Thread.sleep(1000);name:");
                Thread.sleep(1000);
                System.out.println("this.sleep(1000); name:"+this.getName());
                this.sleep(1000);
                System.out.println("end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
  /*
main start
Thread.currentThread().sleep(1000);test02Thread
Thread.sleep(1000);name:
this.sleep(1000); name:test02Thread
end
main end
   */
    @Test
    public void test() throws InterruptedException {
        System.out.println(" main start");
        Test02Thread test02Thread = new Test02Thread();
        test02Thread.setName("test02Thread");
        test02Thread.start();
        test02Thread.join();
        System.out.println(" main end");
    }

}
