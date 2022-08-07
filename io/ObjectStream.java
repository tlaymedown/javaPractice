package life.tz.JavaGuide.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;

/**
 * 对象流（属于处理流），可用于序列化操作
 */
public class ObjectStream {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String path = "D:\\test\\out.dat";
        output(new File(path));
        input(new File(path));
    }

    static void output(File dest) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dest));

        outputStream.writeInt(100);
        outputStream.writeBoolean(false);
        outputStream.writeObject(new User(12, "tom"));
        outputStream.writeUTF("hello你好");

        outputStream.close();
    }

    // 注意反序列化时读取数据要与序列化操作顺序一致
    static void input (File source) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(source));

        int i = inputStream.readInt();
        System.out.println(i);
        boolean b = inputStream.readBoolean();
        System.out.println(b);

        // 解读对象时的注意点：在前面写入文件时已经指定 User 类的位置，这里必须使用相同包的User类
        // 这里我们在同一个地方测试，这是没有问题的，若序列化与反序列化操作不在同一个地方，需要将序列化的对象放在公共区域
        // 比如将pojo放在 commons 中
        Object o = inputStream.readObject();
        User user = (User) o;
        System.out.println(user.toString());

        String s = inputStream.readUTF();
        System.out.println(s);
        inputStream.close();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User implements Serializable{
    int age;
    String name;
}
