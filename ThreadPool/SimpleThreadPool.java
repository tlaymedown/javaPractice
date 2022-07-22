package life.tz.JavaGuide.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool {



    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            Runnable work = new WorkThread("" + i);
            executor.execute(work);
        }

        executor.shutdown();
        while (!executor.isTerminated()){}
        System.out.println("end--------");
    }

}
