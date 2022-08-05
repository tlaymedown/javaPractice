package life.tz.JavaGuide.Thread;

import static life.tz.test.Test3.swap;

/**
 *  * 锁的释放
 *  * 1：同步方法或代码块执行完之后会自动释放锁
 *  * 2：产生异常会释放锁
 *  * 3：return（同步方法）, break（同步代码块）结束相关锁的代码时会释放锁
 *  * 4：使用wait()
 *
 *  注意wait() 与 notify()辅助使用
 *  wait() 会释放当前对象的锁（不会释放线程所有的锁），并进入等待，
 *  notify() 唤醒当前对象锁中在等待的线程
 */
public class Thread03 {

    private static Object lock1 = new Object();

    private static Object lock2 = new Object();

    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println("线程1获得 lock1");

                    synchronized (lock2) {
                        System.out.println("线程1获得 lock2");
                        try {
                            System.out.println("线程1释放 lock1");
                            lock1.wait();
                            lock1.notify();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
                System.out.println("线程1结束");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    synchronized (lock1) {
                        System.out.println("线程2获得 lock1");
                        // 通知线程1 lock1继续执行
                        lock1.notify();
                        lock1.wait();
                        synchronized (lock2) {
                            System.out.println("线程2获得 lock2");
                        }
                    }
                    System.out.println("线程2结束");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

    }

}

class Food implements Runnable{

    private Object lock1 = new Object();

    private Object lock2 = new Object();

    private int sleep;

    public Food() {
        this(0);
    }

    public Food(int sleepSeconds) {
        sleep = sleepSeconds;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(sleep * 1000);

            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " 获得lock1");

                synchronized (lock2) {

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
