package life.tz.JavaGuide.io;

import java.io.*;

/**
 * 包装流(处理流)与节点流
 *
 * 前面讲解的 FileInputStream, FileWriter 等都只是对文件的操作
 * 当然还有对数组的操作流，对管道的操作流，对字符串的操作流，但这些都只是节点流（只能做一件事）
 *
 * io流中提供了一个基于buffer的处理流，例如 BufferedReader，看其源码，发现其内部有 Reader变量，即其可以适应其他Reader子类对象
 * 当然，处理流还有 Object(只有字节类型: ObjectInputStream, ObjectOutputStream), Filter(字节字符两种类型都有)
 *
 */
public class packingFlow {

    public static void main(String[] args) throws IOException {
        String source = "D:\\test\\1.txt";
        String dest = "D:\\test\\10.txt";
        buffer(new File(source), new File(dest));
    }

    static void bufferedReader_test() throws IOException {
        String path = "D:\\test\\1.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String readLine = "";

        while ((readLine = reader.readLine()) != null) {
            System.out.println(readLine);
        }
        reader.close();
    }

    // Buffer 做字符流复制操作
    static void buffer(File source, File dest) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(source));
        BufferedWriter writer = new BufferedWriter(new FileWriter(dest, true));
        String content = "";
        while ((content = reader.readLine()) != null) {
            writer.write(content);
            writer.newLine();
        }
        reader.close();
        writer.close();
    }
}
