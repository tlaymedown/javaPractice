package life.tz.JavaGuide.Thread;

/**
 * 并发条件下不对票数做限制，会造成超卖现象
 * 使用锁机制进行控制
 * 注意多个线程加锁的对象必须是同一个对象
 *
 * 同步方法使用synchronized的锁对象默认为
 * 1：普通方法：this
 * 2: 静态方法：class
 * Ticket2类中对此进行了演示
 *
 *
 */
public class Thread01 {

    public static void main(String[] args) throws InterruptedException {

        Ticket1 ticket1 = new Ticket1(100);
        Thread thread1 = new Thread(ticket1);
        Thread thread2 = new Thread(ticket1);
        Thread thread3 = new Thread(ticket1);

        long l1 = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread3.start();


        thread1.join();
        thread2.join();
        thread3.join();

        long l2 = System.currentTimeMillis();


        Ticket2 ticket2 = new Ticket2(100);
        Thread thread21 = new Thread(ticket2);
        Thread thread22 = new Thread(ticket2);
        Thread thread23 = new Thread(ticket2);

        long l3 = System.currentTimeMillis();
        thread21.start();
        thread22.start();
        thread23.start();

        thread21.join();
        thread22.join();
        thread23.join();

        long l4 = System.currentTimeMillis();

        System.out.println("Ticket1 : " + (l2 - l1) + " ms") ;
        System.out.println("Ticket2 : " + (l4 - l3) + " ms") ;

    }
}

/**
 * 相比与Ticket1，此处使用乐观锁，只在tickets--处加锁，避免了模拟业务处Thread.sleep(50)的时间消耗
 */
class Ticket2 implements Runnable {

    private int tickets;

    private Object o = new Object();

    public Ticket2(int tickets) {
        this.tickets = tickets;
    }

    // 此时默认锁为 Ticket2.class
    public synchronized static void hello() {
        System.out.println("hello");
    }

    // 可以使用 其他对象进行加锁控制
    public void hello2() {
        synchronized (o) {
            System.out.println("hello2");
        }
    }

    public synchronized void sell() {
        if (tickets <= 0) return;
        tickets--;
    }

    @Override
    public void run() {
        while (true) {
            if (tickets <= 0) {
                System.out.println("售空--- " + tickets);
                break;
            }
            try {
                Thread.sleep(50);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            sell();
            System.out.println(Thread.currentThread().getName() + " now ticket : " + tickets);
        }
    }
}

/**
 * 使用悲观锁，对整个流程加锁
 */
class Ticket1 implements Runnable {

    private int tickets;

    public Ticket1(int tickets) {
        this.tickets = tickets;
    }

    public synchronized boolean sell() {
        if (tickets <= 0) {
            System.out.println("售空--- " + tickets);
            return false;
        }
        try {
            Thread.sleep(50);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " now ticket : " + --tickets);
        return true;
    }

    @Override
    public void run() {
        while (true) {
            boolean res = sell();
            if (!res) break;
        }
    }
}

class Ticket implements Runnable {

    private int tickets = 100;

    @Override
    public void run() {

        while (true) {

            if (tickets <= 0) {
                System.out.println("售空--- " + tickets);
                break;
            }
            try {
                Thread.sleep(50);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (this) {
                if (tickets <= 0) break;
                --tickets;
            }
            System.out.println(Thread.currentThread().getName() + " now ticket : " + tickets);
        }
    }
}


