package life.tz.JavaGuide.io;

import java.io.File;

/**
 * File类与文件创建
 */
public class FileCreate {

    public static void main(String[] args) {
        String path = "D:\\test";

        File parent = new File(path);

        createFile(path + File.separator + "1.txt");
        createFile(parent, "2.txt");
        createFile(path, "3.txt");

    }

    static boolean createFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            boolean delete = file.delete();
            if (!delete) return false;
        }
        boolean res = false;
        try {
            res = file.createNewFile();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    static boolean createFile(File parent, String path) {
        File file = new File(parent, path);
        if (file.exists()) {
            boolean delete = file.delete();
            if (!delete) return false;
        }
        boolean res = false;
        try {
            res = file.createNewFile();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    static boolean createFile(String parent, String path) {
        File file = new File(parent, path);
        if (file.exists()) {
            boolean delete = file.delete();
            if (!delete) return false;
        }
        boolean res = false;
        try {
            res = file.createNewFile();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
}
