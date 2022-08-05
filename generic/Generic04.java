package life.tz.JavaGuide.generic;

public class Generic04 {

    public static void main(String[] args) {
        // 调用泛型方法在编译器编译过程中给泛型赋值类型
        Car car = new Car();
        car.B("hjj", 12);

        Cat<Integer, Double> cat = new Cat<>();
        cat.hi(1, 23, 10.8f);
    }
}

/**
 * 普通类也可以定义泛型方法
 */
class Car{

    public void A(){}

    public <K, V> void B(K k, V v){}
}

/**
 * 泛型类中可以定义泛型方法
 * @param <K>
 * @param <V>
 */
class Cat<K, V> {

    // <T, R> 为泛型方法中的泛型的定义
    public <T, R> void show(T t, R r) {

    }

    //  泛型方法可以使用泛型类的泛型
    public <T, R> void hi(K k, T t, R r) {
        System.out.println(k.getClass());
        System.out.println(t.getClass());
        System.out.println(r.getClass());
    }

    // 注意该方法不是泛型方法，只是使用了泛型的方法
    public void hello(K k) {

    }

}
