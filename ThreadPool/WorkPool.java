package life.tz.JavaGuide.ThreadPool;

import java.util.concurrent.*;

/**
 * 自定义线程池中的参数
 * 线程池的拒绝策略为 max_pool_size + queue_capacity 数量，超过此数量会拒绝任务
 * Executors 工具类提供的几个线程池实现是基于ThreadPoolExecutor的
 */
public class WorkPool {

    // 线程池的核心线程数
    private static final Integer CORE_POOL_SIZE = 2;

    // 线程池最大线程数
    private static final Integer MAX_POOL_SIZE = 5;

    // 等待队列的容量
    private static final Integer QUEUE_CAPACITY = 2;

    // 线程池中非核心线程最大空闲时间
    private static final Integer KEEP_ALIVE_TIME = 60;

    public static void main(String[] args) throws InterruptedException {

        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY),
                threadFactory,
                new WorkRejectHandler()
        );

        // 我们可以实现一个线程池监控线程
        MonitorThread monitorThread = new MonitorThread(poolExecutor, 3);
        Thread monitor = new Thread(monitorThread);
        monitor.start();

        for (int i = 0; i < 10; i++) {
            WorkThread workThread = new WorkThread("cmd " + i);
            poolExecutor.execute(workThread);
        }

        poolExecutor.shutdown();
        monitorThread.shutdown();
        System.out.println("Finished All Thread");

    }
}
