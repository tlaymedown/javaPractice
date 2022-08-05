package life.tz.JavaGuide.generic;

import org.omg.CORBA.INTERNAL;

import java.math.BigDecimal;
import java.util.*;

public class Generic03 {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("jack", new BigDecimal("12004.5"), new MyDate(2022, 8, 4)));

        list.add(new Employee("tom", new BigDecimal("10000.5"), new MyDate(2022, 8, 5)));

        list.add(new Employee("jack", new BigDecimal("12004.5"), new MyDate(2022, 8, 6)));


        list.sort(Comparator.comparing(Employee::getName).thenComparing(Employee::getBirthday));

        for (Employee e : list) {
            System.out.println(e.toString());
        }
    }


}

class Employee {
    private String name;

    private BigDecimal sal;

    private MyDate birthday;

    public Employee(String name, BigDecimal sal, MyDate birthday) {
        this.name = name;
        this.sal = sal;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSal() {
        return sal;
    }

    public void setSal(BigDecimal sal) {
        this.sal = sal;
    }

    public MyDate getBirthday() {
        return birthday;
    }

    public void setBirthday(MyDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", sal=" + sal +
                ", birthday=" + birthday +
                '}';
    }
}

class MyDate implements Comparable<MyDate>{

    private Integer year;

    private Integer month;

    private Integer day;

    public MyDate(Integer year, Integer month, Integer day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    @Override
    public int compareTo(MyDate o) {
        int year = this.getYear() - o.getYear();
        if (year != 0) {
            return year > 0 ? 1 : -1;
        }
        int month = this.getMonth() - o.getMonth();
        if (month != 0) {
            return month > 0 ? 1 : -1;
        }
        int day = this.getDay() - o.getDay();
        if (day != 0) {
            return day > 0 ? 1 : -1;
        }
        return 0;
    }
}
