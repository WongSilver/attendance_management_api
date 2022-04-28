package edu.wong.attendance_management_api.util;

import java.lang.reflect.Field;

/**
 * 判断传入对象的指定属性值是否为空
 */
public class CheckFieldIsNullUtil {
    //利用反射，取出属性名
    public static boolean isNull(Object obj, String[] fieldNames) throws IllegalAccessException {

        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            for (String fieldName : fieldNames) {
                if (f.getName().equals(fieldName) && f.get(obj) == null) {
                    return true;
                }
            }
        }
        return false;
    }
}
