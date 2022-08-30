package life.tz.JavaGuide.internet.bio;

import life.tz.JavaGuide.ThreadPool.WorkRejectHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * socket 客户端
 * 通过 Socket socket = new Socket(ip, port) 建立与服务端的连接
 * 使用 socket.getOutputStream() 等向服务端发送数据
 * 使用 socket.getInputStream() 接收服务端的数据
 */
public class SocketClient {

    private String ip = "192.168.1.4";

    private int port = 9999;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
//        String ip = "192.168.1.4";
//        int port = 9999;
//        // 连接ip/port的服务，成功后返回socket
//        Socket socket = new Socket(ip, port);
//        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write("hello server, i am client".getBytes());
//        outputStream.close();
//        System.out.println("client success");
//        socket.close();
        SocketClient client = new SocketClient();
        ThreadPoolExecutor poolExecutor = client.InitExecutor(5, 10, 10, 50);
        List<Future<Message>> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Message message = new Message("hello, i am client " + i);
            SocketClientTask task = new SocketClientTask(new Socket("192.168.1.4", 9999), message);
            Future<Message> future = poolExecutor.submit(task);
            list.add(future);
        }
        Thread.sleep(500);
        for (Future<Message> future : list) {
            if (future == null) continue;
            System.out.println(future.get().toString());
        }
        poolExecutor.shutdown();
    }

    public ThreadPoolExecutor InitExecutor(int core, int max, int alive, int capacity) {
        ThreadFactory factory = Executors.defaultThreadFactory();
        return new ThreadPoolExecutor(
                core, max, alive, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity),
                factory,
                new WorkRejectHandler()
        );
    }

}

// 模拟并发情况，使用线程池同时提交多项数据
class SocketClientTask implements Callable {

    private Socket socket;

    private Message message;

    public SocketClientTask(Socket socket, Message message) {
        this.socket = socket;
        this.message = message;
    }

    @Override
    public Message call() throws Exception {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())){
            outputStream.writeObject(message);
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (Message)inputStream.readObject();
        } catch (Exception e) {
            System.out.println("exception occur : " + e.getMessage());
        }
        return null;
    }
}
