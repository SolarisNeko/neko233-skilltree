package com.neko233.skilltree.ioc;

import com.neko233.skilltree.commons.core.base.InterfaceUtils233;
import com.neko233.skilltree.commons.core.base.StringUtils233;
import com.neko233.skilltree.commons.core.scanner.PackageScanner;
import com.neko233.skilltree.ioc.annotation.Provider;
import com.neko233.skilltree.ioc.annotation.Inject;
import com.neko233.skilltree.ioc.constant.ProvideType;
import com.neko233.skilltree.ioc.object_factory.ObjectFactory;
import com.neko233.skilltree.ioc.object_factory.PrototypeObjectFactory;
import com.neko233.skilltree.ioc.object_factory.SingletonObjectFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * IOC 容器
 *
 * @author SolarisNeko
 */
@Slf4j
public class IocContainer233 {


    private final Map<Class<?>, ObjectFactory> classToObjectFactoryMap = new ConcurrentHashMap<>();


//    /**
//     * SpringBoot get bean
//     *
//     * @param packageName
//     * @throws Exception
//     */
//    public void scanBySpring(String packageName) throws Exception {
//        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
//        AnnotationTypeFilter includeFilter = new AnnotationTypeFilter(Component.class);
//        scanner.addIncludeFilter(includeFilter);
//
//        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(packageName);
//
//        List<String> classNameList = candidateComponents.stream()
//                .map(BeanDefinition::getBeanClassName)
//                .collect(Collectors.toList());
//
//        handle(classNameList);
//    }

    public void scan(Class<?> appClass) throws Exception {
        scan(appClass.getPackage().getName());
    }

    public void scan(String packageName) throws Exception {
        scan(packageName, true);
    }

    /**
     * 扫描包
     *
     * @param packageName 包名
     * @throws Exception any 异常
     */
    public void scan(String packageName,
                     boolean recursive) throws Exception {
        List<String> classNameList = PackageScanner.scanClass(packageName, recursive)
                .stream()
                .map(Class::getName)
                .collect(Collectors.toList());

        handle(classNameList);
    }

    public void handle(List<String> classNameList) throws Exception {
        provideComponent(classNameList);

        inject();
    }

    /**
     * 注入 by 容器中的 object
     *
     * @throws Exception 任何异常
     */
    private void inject() throws Exception {
        // 构造后的实例
        Collection<ObjectFactory> values = classToObjectFactoryMap.values();
        for (ObjectFactory objectFactory : values) {

            Class<?> targetClass = objectFactory.getTargetClass();
            if (targetClass == null) {
                String errLog = StringUtils233.format("objectFactory target Class get error! object factory = {}",
                        objectFactory);
                throw new IllegalArgumentException(errLog);
            }
            Field[] instanceField = targetClass.getDeclaredFields();


            // @Inject Field field
            List<Field> toInjectFieldList = Arrays.stream(instanceField)
                    .filter(field -> field.isAnnotationPresent(Inject.class))
                    .collect(Collectors.toList());

            // 不需要注入, 避免性能开销
            if (CollectionUtils.isEmpty(toInjectFieldList)) {
                continue;
            }

            // first init
            Object instance = objectFactory.getOrCreate();

            for (Field field : toInjectFieldList) {

                Class<?> fieldClass = field.getType();
                ObjectFactory targetObjectFactory = classToObjectFactoryMap.get(fieldClass);
                if (targetObjectFactory == null) {
                    throw new RuntimeException("No instance found for dependency class: " + fieldClass.getName());
                }
                field.setAccessible(true);

                Object dependencyObj = targetObjectFactory.getOrCreate();
                field.set(instance, dependencyObj);
            }
        }
    }

    /**
     * 提供 Class 给 ioc 管理
     *
     * @param classNameList 类名
     * @throws Exception 异常
     */
    private void provideComponent(List<String> classNameList) throws Exception {
        // 全类名
        for (String beanClassName : classNameList) {
            Class<?> clazz = Class.forName(beanClassName);

            Provider annotation = clazz.getAnnotation(Provider.class);
            if (annotation == null) {
                continue;
            }
            ProvideType provideType = annotation.type();
            ObjectFactory objectFactory = null;
            if (provideType == ProvideType.SINGLETON) {
                objectFactory = new SingletonObjectFactory(clazz);
            }
            if (provideType == ProvideType.PROTOTYPE) {
                objectFactory = new PrototypeObjectFactory(clazz);
            }

            // default
            if (objectFactory == null) {
                objectFactory = new SingletonObjectFactory(clazz);
            }

            classToObjectFactoryMap.put(clazz, objectFactory);

            Set<Class<?>> allInterfaces = InterfaceUtils233.getAllInterfaces(clazz);
            for (Class<?> interfaceClass : allInterfaces) {
                classToObjectFactoryMap.put(interfaceClass, objectFactory);
            }


        }
    }

    /**
     * 获取对象 (spring 叫 getBean)
     *
     * @param clazz 类
     * @param <T>
     * @return 对象
     */
    public <T> T getObject(Class<T> clazz) {
        ObjectFactory objectFactory = classToObjectFactoryMap.get(clazz);
        if (objectFactory == null) {
            throw new RuntimeException("No instance found for class: " + clazz);
        }
        Object instance = null;
        try {
            instance = objectFactory.getOrCreate();
        } catch (Exception e) {
            log.error("get objectFactory success, but getOrCreate error! className = {}", clazz.getName());
            return null;
        }
        return clazz.cast(instance);
    }
}