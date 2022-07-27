package com.spark.jovakinn.starter.realizations;

import com.spark.jovakinn.starter.contracts.DataExtractor;
import com.spark.jovakinn.starter.contracts.Finalizer;
import com.spark.jovakinn.starter.contracts.SparkTransformation;
import lombok.Builder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Builder
public class SparkInvocationHandler implements InvocationHandler {

    private Class<?> modelClass;
    private String pathToData;
    private DataExtractor dataExtractor;
    private Map<Method, List<SparkTransformation>> transformationChain;
    private Map<Method, Finalizer> finalizerMap;
    private ConfigurableApplicationContext context;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Dataset<Row> dataset = dataExtractor.load(pathToData, context);
        List<SparkTransformation> transformations = transformationChain.get(method);
        for (SparkTransformation transformation : transformations) {
            dataset = transformation.transform(dataset);
        }
        Finalizer finalizer = finalizerMap.get(method);
        return finalizer.doAction(dataset);
    }
}
