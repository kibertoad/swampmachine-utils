package net.kiberion.swampmachine.utils.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.Validate;

public class ReflectionUtils {

    public static <T> T buildObject(String[] constructorProperties, Class<T> clazz,
            Map<String, Object> sourceProperties) {

        List<Class<?>> constructorValueClasses = new ArrayList<>();
        List<Object> constructorPropertiesToSet = new ArrayList<>();

        for (String constructorProperty : constructorProperties) {
            Object value = sourceProperties.get(constructorProperty);
            Validate.notNull(value, "Null value for constructor property " + constructorProperty);
            constructorPropertiesToSet.add(value);
            constructorValueClasses.add(value.getClass());
        }

        try {
            if (constructorProperties.length == 0) {
                return clazz.newInstance();
            } else {
                Constructor<? extends T> constructor = clazz
                        .getConstructor(constructorValueClasses.toArray(new Class<?>[constructorValueClasses.size()]));
                return constructor.newInstance(constructorPropertiesToSet.toArray());
            }
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException
                | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

}
