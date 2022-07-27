package com.spark.jovakinn.starter.realizations;

import com.spark.jovakinn.starter.annotations.Source;
import com.spark.jovakinn.starter.annotations.Transient;
import com.spark.jovakinn.starter.contracts.*;
import com.spark.jovakinn.starter.utils.WordsMatcher;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

public class SparkInvocationHandlerFactory {
    private DataExtractorResolver resolver;
    private Map<String, TransformationSpider> spiderMap;
    private Map<String, Finalizer> finalizerMap;
    private ConfigurableApplicationContext context;

    public SparkInvocationHandler create(Class<? extends SparkRepository> sparkRepoInterface) {
        Class<?> modelClass = getModelClass(sparkRepoInterface);
        String pathToData = modelClass.getAnnotation(Source.class).value();
        Set<String> fieldNames = getFieldNames(modelClass);
        DataExtractor dataExtractor = resolver.resolve(pathToData);
        Map<Method, List<SparkTransformation>> transformationChain = new HashMap<>();
        Map<Method, Finalizer> method2Finalizer = new HashMap<>();

        Method[] methods = sparkRepoInterface.getMethods();
        for (Method method : methods) {
            TransformationSpider currentSpider = null;
            List<String> methodWords = WordsMatcher.toWordsByJavaConvention(method.getName());
            List<SparkTransformation> transformations = new ArrayList<>();
            while (methodWords.size() > 1) {
                String spiderName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(spiderMap.keySet(), methodWords);
                if (!spiderName.isEmpty()) {
                    currentSpider = spiderMap.get(spiderName);
                }
                transformations.add(Objects.requireNonNull(currentSpider).getTransformation(methods));
            }
            transformationChain.put(method, transformations);
            String finalizerName = "collect";
            if (methodWords.size() == 1) {
                finalizerName = methodWords.get(0);
            }
            method2Finalizer.put(method, finalizerMap.get(finalizerName));
        }

        return SparkInvocationHandler.builder()
                .modelClass(modelClass)
                .pathToData(pathToData)
                .dataExtractor(dataExtractor)
                .transformationChain(transformationChain)
                .finalizerMap(method2Finalizer)
                .context(context)
                .build();
    }

    private Class<?> getModelClass(Class<? extends SparkRepository> repoInterface) {
        ParameterizedType genericInterface = (ParameterizedType) repoInterface.getGenericInterfaces()[0];
        return (Class<?>) genericInterface.getActualTypeArguments()[0];
    }

    private Set<String> getFieldNames(Class<?> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !Collection.class.isAssignableFrom(field.getType()))
                .map(Field::getName)
                .collect(Collectors.toSet());
    }
}
