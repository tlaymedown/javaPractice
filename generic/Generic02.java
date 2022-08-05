package life.tz.JavaGuide.generic;

/**
 * 泛型类与泛型接口的定义与测试
 */
public class Generic02 {

    public static void main(String[] args) {
        Person<Integer> person = new Person<>();

        person.setDescription(5);
        System.out.println(person.getDescription());

        Type<Integer, String, Person<Integer>, Character> type = new Type<>("name", 1, "23", new Person<>(12), 'c');
        type.show();
    }
}

/**
 * 使用泛型规定当前类中某些变量或方法的类型（返回值）
 * 指定的E在编译期间就确定了（代码指定）
 * @param <E>
 */
class Person<E> {

    private E description;

    public Person (E description) {
        this.description = description;
    }

    public Person(){}

    public E getDescription() {
        return description;
    }

    public void setDescription(E desp) {
        this.description = desp;
    }
}

class Type<T, K, V, R> {
    private String name;

    private T t;

    private K k;

    private V v;

    private R r;

    public void show() {
        System.out.println(t.getClass() + " \n" + k.getClass() + " \n" + v.getClass() + " \n" + r.getClass());
    }

    public Type(String name, T t, K k, V v, R r) {
        this.name = name;
        this.t = t;
        this.k = k;
        this.v = v;
        this.r = r;
    }
}


interface A<K, V> {

}

interface B<K, V> extends A<K, V>{

}

interface C extends A<String, Integer> {

}

class TestA implements A<String, Integer> {

}
