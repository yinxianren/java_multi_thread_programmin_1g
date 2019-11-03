package org.yinxianren.com.entity;

/**
 * Created with IntelliJ IDEA.
 * User: panda
 * Date: 2019/11/3
 * Time: 下午2:26
 * Description:
 */
public class SynchronizedObject {

    private String userName;
    private String password;

    synchronized public void printString(String userName,String password){
        try {
            this.userName = userName;
            Thread.sleep(2000);
            this.password = password;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void printString2(){
        System.out.println("------------begin----------");
         if(Thread.currentThread().getName().equalsIgnoreCase("thread-me-01")){
             System.out.println("thread name:"+Thread.currentThread().getName()+"\t into suspend stats");
             Thread.currentThread().suspend();
             System.out.println("thread name:"+Thread.currentThread().getName()+"\t end suspend stats");
         }
        System.out.println("------------end----------");
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
