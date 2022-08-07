package life.tz.JavaGuide.io;

import java.io.*;

/**
 * 打印流
 * 分为 PrintStream 与 PrintWriter
 * 注意打印流只有输出流
 */
public class PrintFlow {

    public static void main(String[] args) throws IOException {
//        printStream();
        printWriter();
    }

    // 字节打印流
    static void printStream() throws IOException {
        // 控制台打印流
        PrintStream stream = System.out;
        stream.close();


        // 查看 System.out 源码可知，其内部维护了一个指向控制台的 PrintStream
        // 我们可以将这个PrintStream 更改为文件 Stream
        System.setOut(new PrintStream(new FileOutputStream("D:\\test\\log.txt",true), true));
        System.out.println("hello");
        System.out.print("tz fun");


        // 可以直接使用打印流
        PrintStream printStream = new PrintStream(new FileOutputStream("D:\\test\\log2.txt", true));
        printStream.println(12);
        printStream.print("hello");
        printStream.print("状态");
        printStream.close();

    }

    // 字符打印流
    static void printWriter() throws IOException{

        PrintWriter printWriter = new PrintWriter(new FileWriter("D:\\test\\log3.txt", true));

        printWriter.println("hello 你好！");
        // 注意printWriter.close()方法调用后才会将内容刷到指定地方
        printWriter.close();
    }
}
