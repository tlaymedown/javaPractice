package life.tz.JavaGuide.internet.bio;

import life.tz.JavaGuide.ThreadPool.WorkRejectHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketServer {

    private String ip = "";

    private int port = 9999;

    public static void main(String[] args) throws IOException {
//        // 监听9999端口
//        ServerSocket serverSocket = new ServerSocket(9999);
//        System.out.println("server 9999 ----");
//        // 如何有客户端连接，会返回socket对象, 未连接时为阻塞状态
//        Socket socket = serverSocket.accept();
//        InputStream inputStream = socket.getInputStream();
//        byte[] buf = new byte[1024];
//        int readSize = 0;
//        while ((readSize = inputStream.read(buf)) != -1) {
//            System.out.println(new String(buf, 0, readSize));
//        }
//        inputStream.close();
//        System.out.println("server socket : " + socket.getClass());
//        socket.close();
//        serverSocket.close();
        SocketServer server = new SocketServer();
        server.start(9999);
    }

    // 为socketServer准备一个线程池
    public ThreadPoolExecutor InitExecutor(int core, int max, int alive, int capacity) {
        ThreadFactory factory = Executors.defaultThreadFactory();
        return new ThreadPoolExecutor(
                core, max, alive, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity),
                factory,
                new WorkRejectHandler()
        );
    }

    // 执行 socketServer，提供一个 socket 服务端
    public void start(int port) {
        System.out.println("start server---");
        ThreadPoolExecutor poolExecutor = null;
        try (ServerSocket serverSocket = new ServerSocket(port)){
            Socket socket;
            poolExecutor = InitExecutor(5, 10, 10, 50);
            // 持续监听 port 端口
            while ((socket = serverSocket.accept()) != null) {
                SocketServerTask task = new SocketServerTask(socket);
                poolExecutor.execute(task);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (poolExecutor != null) poolExecutor.shutdown();
        }
        System.out.println("end server---");
    }
}

class SocketServerTask implements Runnable {

    private Socket socket;

    public SocketServerTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
        ){
            Message message = (Message) inputStream.readObject();
            System.out.println(message.getData().toString());
            message.setData("new data");
            outputStream.writeObject(message);
            outputStream.flush();
        }
        catch (Exception e) {
            System.out.println("exception occur : " + e.getMessage());
        }
    }
}
