package life.tz.JavaGuide.generic;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用泛型封装测试
 */
public class Generic06 {

    public static void main(String[] args) {
        UserVo he = new UserVo("he", "123", "12", "key1");
        User user = CopyUtil.copyClass(he, User.class);
        System.out.println(user.toString());

        List<UserVo> list = new ArrayList<>();
        list.add(new UserVo("1", "1", "1", "key1"));
        list.add(new UserVo("2", "2", "2", "key2"));
        list.add(new UserVo("3", "3", "3", "key3"));

        List<User> users = CopyUtil.copyList(list, User.class);
        System.out.println(users);

    }
}

class CopyUtil{

    public static <T, K> T copyClass(K source, Class<T> tClass) {
        T res = null;
        try {
            res = tClass.newInstance();
            BeanUtil.copyProperties(source, res);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static <T> List<T> copyList(List<? extends InfoVo> source, Class<T> tClass) {
        if (source == null) throw new NullPointerException("source list is null");
        if (CollectionUtils.isEmpty(source)) return null;
        List<T> res = new ArrayList<>();
        for (Object o : source) {
            T t = BeanUtil.copyProperties(o, tClass);
            InitUtil.InitDefaultValue(t);
            res.add(t);
        }
        return res;
    }

}

class InitUtil{

    /**
     * this init value is just for class User,
     * if you want work for another class, you should match more class type
     * in the judgement of o == null
     * @param source
     * @param <T>
     */
    public static <T> void InitDefaultValue(T source) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field f : fields) {
            boolean isStatic = Modifier.isStatic(f.getModifiers());
            if (isStatic) continue;

            f.setAccessible(true);
            try {
                Object o = f.get(source);
                Class<?> type = f.getType();

                if (o == null) {
                    if (type == Integer.class) f.set(source, 0);
                    else if (type == String.class) f.set(source, "default value");
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class User{
    private String name;
    private String description;
    private Integer age;
    private String phone;
    private String password;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class UserVo implements InfoVo{
    private String name;
    private String phone;
    private String password;
    private String key;
}

interface InfoVo{}
