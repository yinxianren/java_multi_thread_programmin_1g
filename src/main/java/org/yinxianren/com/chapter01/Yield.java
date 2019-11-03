package org.yinxianren.com.chapter01;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: panda
 * Date: 2019/11/3
 * Time: 下午4:07
 * Description:
 */
public class Yield {

    /*
     *yield()：  方法的作用是放弃当前cpu资源，将它让给其他任务取占用cpu执行时间，
     *         但放弃的时间不确定，有可能刚刚放弃，马上又获得cup时间片；
     */

    /**
     *
     * @throws InterruptedException
     *   没有使用yield 的情况：total = 891396832;	 spend time =4
     *   使用yield 的情况：total = 891396832;	 spend time =229
     *
     */
    @Test
    public void test01() throws InterruptedException {
        class Test extends Thread{
            @Override
            public void run() {
                long beginTime = System.currentTimeMillis();
                long total = 0;
                for(int i=0;i<500000;i++){
                    total = i+i*i;
                    this.yield();
                }
                long endTime = System.currentTimeMillis();
                System.out.println("total = " +total+";\t spend time ="+(endTime-beginTime));
            }
        }
        Test t= new Test();
        t.start();
        t.join();
    }


}
