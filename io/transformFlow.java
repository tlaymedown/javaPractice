package life.tz.JavaGuide.io;


import java.io.*;

/**
 * 转换流
 * 字符流读取 采用 utf-8形式，且字符流无法更换编码
 * 但字节流可以指定编码
 *
 * 这时就引出转换流，注意转换流是 字节流 -> 字符流
 * InputStreamReader 中提供了指定编码的构造方法
 */
public class transformFlow {

    public static void main(String[] args) throws IOException {
        // 注意此文件为国标码(中国为gbk)
        String path = "D:\\test\\test.txt";
        read(new File(path));
        readByTrans(new File(path));
    }

    // 此时用字符流读取会乱码，为什么不直接用字节流？ 有中文
    static void read(File source) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(source));

        String res = "";
        while ((res = reader.readLine()) != null) {
            System.out.println(res);
        }
        reader.close();
    }

    static void readByTrans(File source) throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(source), "gbk");
        int readSize = 0;
        char[] buf = new char[12];
        while ((readSize = reader.read(buf)) != -1) {
            System.out.println(new String(buf, 0, readSize));
        }
        reader.close();
    }

    static void writeByTran(File dest) throws IOException {
        OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream(dest), "gbk");
    }
}
