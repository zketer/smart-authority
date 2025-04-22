package smart.authority.common;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Bean拷贝工具类
 */
public class BeanCopyUtils {

    /**
     * 单个对象拷贝
     */
    public static <T> T copyBean(Object source, Class<T> clazz) {
        T result = null;
        try {
            result = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 单个对象拷贝（使用Supplier）
     */
    public static <T> T copyBean(Object source, Supplier<T> targetSupplier) {
        T result = targetSupplier.get();
        BeanUtils.copyProperties(source, result);
        return result;
    }

    /**
     * 集合拷贝
     */
    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        if (sourceList != null && sourceList.size() > 0) {
            for (S source : sourceList) {
                T target = copyBean(source, targetClass);
                targetList.add(target);
            }
        }
        return targetList;
    }

    /**
     * 集合拷贝（使用Supplier）
     */
    public static <S, T> List<T> copyList(List<S> sourceList, Supplier<T> targetSupplier) {
        List<T> targetList = new ArrayList<>();
        if (sourceList != null && sourceList.size() > 0) {
            for (S source : sourceList) {
                T target = copyBean(source, targetSupplier);
                targetList.add(target);
            }
        }
        return targetList;
    }
} 