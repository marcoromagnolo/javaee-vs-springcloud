package jeevsspring.wildfly.poker.manager.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * @author Marco Romagnolo
 */
public class JsonString {

    public static String JsonToString(Object obj) {
        final StringBuffer sb = new StringBuffer(obj.getClass() + " : {");
        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                if (!Modifier.toString(field.getModifiers()).contains("static") || !Modifier.toString(field.getModifiers()).contains("final")) {
                    sb.append("\"" + field.getName() + "\":")
                            .append("\"" + obj.getClass().getDeclaredMethod("get"
                                    + field.getName().substring(0, 1).toUpperCase()
                                    + field.getName().substring(1)).invoke(obj) + "\",");
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                //ignore
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1) + "}";
    }
}
