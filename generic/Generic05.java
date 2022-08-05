package life.tz.JavaGuide.generic;

import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型传值与通配符
 * 通常有这样的问题：
 * List<?> 与 List<E>有什么区别？即?与E有什么区别
 *
 * E 表示定义泛型类，接口或方法时用到的泛型代表符
 *
 * ? 表示接收泛型传值时的代表符
 *
 * 即 E等符号（K, V） 是在定义泛型时用的
 *
 * ? 是在使用泛型（传参等）时用的
 *
 */
@SuppressWarnings({"all"})
public class Generic05 {

    public static void main(String[] args) {
        List<Object> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<AA> list3 = new ArrayList<>();
        List<BB> list4 = new ArrayList<>();
        List<CC> list5 = new ArrayList<>();

        printCollectoin1(list1);
        printCollectoin1(list2);
        printCollectoin1(list3);
        printCollectoin1(list4);
        printCollectoin1(list5);

        printCollectoin2(list4);
        printCollectoin2(list5);

        printCollectoin3(list1);
        printCollectoin3(list3);
        printCollectoin3(list4);

    }

    // 任意类型
    public static void printCollectoin1(List<?> list) {
        for (Object o : list) {
            System.out.println(o.toString());
        }
    }

    // <= BB的可以使用
    public static void printCollectoin2(List<? extends BB> list) {
        for (Object o : list) {
            System.out.println(o.toString());
        }
    }

    // >= BB
    public static void printCollectoin3(List<? super BB> list) {
        for (Object o : list) {
            System.out.println(o.toString());
        }
    }
}

class AA{

}

class BB extends AA{}

class CC extends BB{}


