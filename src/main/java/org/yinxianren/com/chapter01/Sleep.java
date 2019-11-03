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
 main start,THis.getId=1
test02Thread getId=10
Thread.currentThread().sleep(1000);test02Thread
Thread.sleep(1000);name:
this.sleep(1000); name:test02Thread
end
 main end
   */
    @Test
    public void test() throws InterruptedException {
        System.out.println(" main start,THis.getId="+Thread.currentThread().getId());
        Test02Thread test02Thread = new Test02Thread();
        System.out.println("test02Thread getId="+test02Thread.getId());
        test02Thread.setName("test02Thread");
        test02Thread.start();
        test02Thread.join();//线程main 和线程test02Thread 是异步执行的，让join方法让主线程等待test02Thread执行在继续执行
        System.out.println(" main end");
    }

}
