package life.tz.JavaGuide.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class MonitorThread implements Runnable{

    private ThreadPoolExecutor poolExecutor;
    private Integer delay;
    private Boolean run;

    public MonitorThread(ThreadPoolExecutor poolExecutor, int delay) {
        this.poolExecutor = poolExecutor;
        this.delay = delay;
        this.run = true;
    }

    public void shutdown() {
        this.run = false;
    }

    @Override
    public void run() {
        while(run){
            System.out.println(
                    String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                            this.poolExecutor.getPoolSize(),
                            this.poolExecutor.getCorePoolSize(),
                            this.poolExecutor.getActiveCount(),
                            this.poolExecutor.getCompletedTaskCount(),
                            this.poolExecutor.getTaskCount(),
                            this.poolExecutor.isShutdown(),
                            this.poolExecutor.isTerminated()));
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
