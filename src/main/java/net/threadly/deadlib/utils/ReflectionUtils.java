package net.threadly.deadlib.utils;

import net.minecraftforge.fml.RegistryObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionUtils {
    public static class RegistryObjectUtils {
        public static Optional<RegistryObject<?>> filterResourceName(String name, Collection<RegistryObject<?>> registeries) {
            return registeries.stream()
                    .filter(registryObject -> reflectResourceName(registryObject).equalsIgnoreCase(name))
                    .findFirst();
        }

        public static <T> Collection<RegistryObject<?>> filterGenericParam(Collection<RegistryObject<?>> registries, Class<T> genericParamClass) {
            return registries.stream().filter(registryObject -> {
                Class<?> persistentClass = (Class<?>) ((ParameterizedType) genericParamClass.getGenericSuperclass()).getActualTypeArguments()[0];
                return persistentClass.equals(genericParamClass);
            }).collect(Collectors.toSet());
        }

        public static String reflectResourceName(RegistryObject<?> registry) {
            try {
                Class<?> resourceLocationClass = Class.forName("net.minecraft.util.ResourceLocation");
                Field resourceLocationField = registry.getClass().getDeclaredField("name");
                Field resourceNameField = resourceLocationClass.getDeclaredField("resourcePath");
                resourceLocationField.setAccessible(true);
                resourceNameField.setAccessible(true);
                Object resourceLocationInstance = resourceLocationField.get(registry);
                String reflectedResourceName = (String) resourceLocationField.get(resourceLocationInstance);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
            return "";
        }

        public static <T> Optional<T> reflectRegistryValue(Class<T> type, RegistryObject<?> registryObject) {
            try {
                Class<?> registryObjectClass = Class.forName("net.minecraft.fml.RegistryObject");
                return reflectFieldAs(registryObjectClass, "value", registryObject, type);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            return Optional.empty();
        }
    }

    public static <T> Optional<T> reflectFieldAs(Class<?> reflectClass, String fieldName, Object instance, Class<T> type) {
        try{
            Field field = reflectClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return Optional.ofNullable((T) field.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public static <T> Collection<Field> reflectAllFieldsOfType(Class<?> reflectedClass, Class<T> type) {
        return Stream.of(reflectedClass.getDeclaredFields()).filter(field -> type.isAssignableFrom(field.getType())).collect(Collectors.toSet());
    }
}
