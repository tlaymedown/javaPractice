package life.tz.JavaGuide.DynamicThreadPoolParam;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义可命名线程工厂
 */
public class NamedThreadFactory implements ThreadFactory {

    private final ThreadFactory factory;
    private final String name;
    private final AtomicInteger threadNum= new AtomicInteger();

    public NamedThreadFactory(ThreadFactory factory, String name) {
        this.factory = factory;
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = factory.newThread(r);
        thread.setName(name + " [#" + threadNum.incrementAndGet() + "]");
        return thread;
    }
}
