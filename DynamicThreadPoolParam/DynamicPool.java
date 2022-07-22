package life.tz.JavaGuide.DynamicThreadPoolParam;


import life.tz.JavaGuide.ThreadPool.WorkPool;
import life.tz.demo.DynamicArray;

import java.util.concurrent.*;

/**
 * 在运行时状态下修改线程池的状态
 * 1：核心线程数
 * 2：最大线程数
 * 3：阻塞队列长度,根据LinkedBlockingQueue<E> 自定义一个自己的ResizeableLinkedBlockingQueue, 将capacity的final修饰符去除，添加volatile保证线程安全
 */
public class DynamicPool {

    public static void main(String[] args) throws InterruptedException {
        dynamicModifyExecutor();
    }

    public static ThreadPoolExecutor threadPool() {
        return new ThreadPoolExecutor(
                2, 5, 10, TimeUnit.SECONDS,
                new ResizeableLinkedBlockingQueue<>(15),
                new NamedThreadFactory(Executors.defaultThreadFactory(), "动态修改"),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

    public static void dynamicModifyExecutor() throws InterruptedException {
        ThreadPoolExecutor pool = threadPool();
        pool.prestartAllCoreThreads();
        for (int i = 0; i < 20; i++) {
            pool.execute(() -> {
                ThreadPoolStatus(pool, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        ThreadPoolStatus(pool, "改变之前");
        pool.setCorePoolSize(10);
        pool.setMaximumPoolSize(10);
        // 扩大corePoolSize后，必须保证corePoolSize <= maxPoolSize
        // 由于设置队列大小为100， 扩大corePoolSize后，队列未满，不会创建新线程，使用pool的线程预热
        pool.prestartAllCoreThreads();
        ResizeableLinkedBlockingQueue<Runnable> queue = (ResizeableLinkedBlockingQueue<Runnable>)pool.getQueue();
        queue.setCapacity(100);
        ThreadPoolStatus(pool, "改变之后");
        // 阻塞主线程
        Thread.currentThread().join();
    }


    public static void ThreadPoolStatus(ThreadPoolExecutor poolExecutor, String name) {
        ResizeableLinkedBlockingQueue<Runnable> queue = (ResizeableLinkedBlockingQueue<Runnable>)poolExecutor.getQueue();
        System.out.println(Thread.currentThread().getName() + " - " + name + " -: " +
                " 核心线程数：" + poolExecutor.getCorePoolSize() +
                " 活动线程数：" + poolExecutor.getActiveCount() +
                " 最大线程数：" + poolExecutor.getMaximumPoolSize() +
                " 线程活跃度：" + divide(poolExecutor.getActiveCount(), poolExecutor.getMaximumPoolSize()) +
                " 任务完成数：" + poolExecutor.getCompletedTaskCount() +
                " 队列大小：" + (queue.size() + queue.remainingCapacity()) +
                " 当前排队线程数：" + queue.size() +
                " 队列剩余大小：" + queue.remainingCapacity() +
                " 队列使用率：" + divide(queue.size(), queue.size() + queue.remainingCapacity())
                );
    }


    public static String divide(int num1, int num2) {
        return String.format("%1.2f%%", Double.parseDouble(num1 + "") / Double.parseDouble(num2 + "") * 100);
    }


}
