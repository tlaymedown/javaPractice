package life.tz.JavaGuide.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

/**
 * 文件复制操作，对比了几种复制方法
 */
public class FileStream {

    public static void main(String[] args) {
        String source = "C:\\Users\\Laymedown\\Pictures\\photo\\喵喻1.jpg", dest1 = "D:\\test\\喵喻1.jpg";
        String dest2 = "D:\\test\\喵喻2.jpg";
        String dest3 = "D:\\test\\喵喻3.jpg";
        String dest4 = "D:\\test\\喵喻4.jpg";
        String dest5 = "D:\\test\\喵喻5.jpg";
        long l1 = System.currentTimeMillis();
        try {
            FileUtil.fileCopy(source, dest1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long l2 = System.currentTimeMillis();

        try {
            FileUtil.fileCopy2(new File(source), new File(dest2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long l3 = System.currentTimeMillis();

        try {
            FileUtil.fileCopy3(new File(source), new File(dest3));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long l4 = System.currentTimeMillis();

        try {
            FileUtil.fileCopy4(new File(source), new File(dest4));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long l5 = System.currentTimeMillis();

        try {
            FileUtil.fileCopy5(new File(source), new File(dest5));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long l6 = System.currentTimeMillis();

        System.out.println("fileStream : " + (l2 - l1) + " ms");
        System.out.println("fileChannel: " + (l3 - l2) + " ms");
        System.out.println("Files.copy : " + (l4 - l3) + " ms");
        // 注意： 字符流读取二进制一定会失败， 这里测速用的为图片（二进制）
        System.out.println("FileWriter : " + (l5 - l4) + " ms");
        System.out.println("bufferStream : " + (l6 - l5) + " ms");

    }
}

class FileUtil {

    static void fileCopy (String sourceFile, String destFile) throws IOException {
        File source = new File(sourceFile);
        File dest = new File(destFile);
        fileCopy1(source, dest);
    }

    /**
     * 使用字节流拷贝文件
     * 注意：此方法效率较低，只做测速使用
     */
    static void fileCopy1 (File sourceFile, File destFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(sourceFile);
        FileOutputStream outputStream = new FileOutputStream(destFile, true);

        byte[] buf = new byte[2048];
        int readSize = 0;

        while ((readSize = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, readSize);
        }

        inputStream.close();
        outputStream.close();
    }

    /**
     * 使用 Channel 进行文件复制，效率更高
     * @param sourceFile
     * @param destFile
     * @throws IOException
     */
    static void fileCopy2 (File sourceFile, File destFile) throws IOException {
        FileChannel input = new FileInputStream(sourceFile).getChannel();
        FileChannel output = new FileOutputStream(destFile).getChannel();

        output.transferFrom(input, 0, input.size());
        input.close();
        output.close();
    }

    /**
     * 使用 Files 提供的copy
     * @param sourceFile
     * @param destFile
     * @throws IOException
     */
    static void fileCopy3 (File sourceFile, File destFile) throws IOException {
        Files.copy(sourceFile.toPath(), destFile.toPath());
    }

    static void fileCopy4 (File sourceFile, File destFile) throws IOException {
        FileReader fileReader = new FileReader(sourceFile);
        FileWriter fileWriter = new FileWriter(destFile, true);
        char[] buf = new char[12];
        int readSize = 0;

        while ((readSize = fileReader.read(buf)) != -1) {
            fileWriter.write(buf, 0, readSize);
        }
        fileWriter.close();
        fileReader.close();
    }

    /**
     * 使用包装流 BufferStream
     * @param sourceFile
     * @param destFile
     * @throws IOException
     */
    static void fileCopy5 (File sourceFile, File destFile) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destFile));

        byte[] buf = new byte[1024];
        int readSize = 0;
        while ((readSize = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, readSize);
        }
        inputStream.close();
        outputStream.close();
    }
}
