package life.tz.JavaGuide.io;

import java.io.File;
import java.io.IOException;

/**
 * File file 的相关信息
 */
public class FileInformation {

    public static void main(String[] args) throws IOException {
        String path = "D:\\test";
        File file = new File(path + File.separator + "4.txt");
        file.createNewFile();

        System.out.println(file.exists());
        System.out.println(file.getName());
        System.out.println(file.length());
        System.out.println(file.canExecute());

        System.out.println(file.getCanonicalPath());
        System.out.println(file.getAbsolutePath());
        // 返回当前磁盘下该文件最大能达到多少
        System.out.println(((file.getFreeSpace() / 1024) / 1024) / 1024 + " gb");
        System.out.println(file.getParent());
    }


}
