package life.tz.JavaGuide.Thread;

/**
 * 线程常用方法：
 * start与run区别， run只是一个方法，并未创建新线程，start会创建一个线程去执行run方法
 *
 * yield与join区别，yield让当前线程让出cpu，并将当前线程置入就绪态，cpu从就绪态中选择一个线程执行，可能还会选到当前线程
 * join表示让出cpu给指定线程
 * 在main线程中调用t1.join()表示main线程让出cpu给t1并等待t1执行完再执行
 *
 * setDaemon: 设置守护线程：守护线程会在所有工作线程结束后自动结束
 *
 * interrupt: 中断一个线程，被中断线程会抛出中断异常，可用于唤醒沉睡中的线程
 *
 *
 *
 */
public class Thread02 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Car("sub thread"));
//        thread.start();
//        thread.setDaemon(true);
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            if (i == 5) {
//                thread.join();
//                Thread.yield();
                thread.start();
                thread.join();
            }
        }
    }
}

class Car implements Runnable{

    private String name;

    public Car(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            System.out.println(name + " : " + i);
        }
    }
}
