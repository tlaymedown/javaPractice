package life.tz.JavaGuide.generic;


import java.util.ArrayList;
import java.util.List;

/**
 * 泛型E就是指定一个类型
 * 比如 int a = 5; E = Integer, String， 将int值赋给a，将Integer赋给E，
 *
 */
@SuppressWarnings({"all"})
public class Generic01 {

    public static void main(String[] args) {
        List<Dog> list = new ArrayList<>();

        list.add(new Dog("1", 1));
        list.add(new Dog("2", 2));
        list.add(new Dog("3", 3));

        for (Dog dog: list) {
            System.out.println(dog.getName() + " : " + dog.getAge());
        }
    }


}

class Dog{
    private String name;

    private Integer age;

    public Dog(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
