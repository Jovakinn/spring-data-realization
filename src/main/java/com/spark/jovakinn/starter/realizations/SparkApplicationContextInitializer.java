package com.spark.jovakinn.starter.realizations;

import com.spark.jovakinn.starter.contracts.SparkRepository;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.beans.Introspector;
import java.lang.reflect.Proxy;
import java.util.Set;

public class SparkApplicationContextInitializer implements ApplicationContextInitializer {
    private SparkInvocationHandlerFactory sparkInvocationHandlerFactory;

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext context) {
        registerSparkBeans(context);
        String packagesToScan = context.getEnvironment().getProperty("spark.packages-to-scan");
        Reflections scanner = new Reflections(packagesToScan);
        Set<Class<? extends SparkRepository>> sparkRepoInterfaces = scanner.getSubTypesOf(SparkRepository.class);
        sparkRepoInterfaces.forEach(sparkRepoInterface -> {
            Object superObject = Proxy.newProxyInstance(sparkRepoInterface.getClassLoader(),
                    new Class[]{sparkRepoInterface},
                    sparkInvocationHandlerFactory.create(sparkRepoInterface));
            context.getBeanFactory()
                    .registerSingleton(Introspector.decapitalize(sparkRepoInterface.getSimpleName()), superObject);
        });
    }

    private void registerSparkBeans(ConfigurableApplicationContext context) {
        String appName = context.getEnvironment().getProperty("spark.app-name");
        SparkSession sparkSession = SparkSession.builder().appName(appName).master("local[*]").getOrCreate();
        JavaSparkContext sparkContext = new JavaSparkContext(sparkSession.sparkContext());
        context.getBeanFactory().registerSingleton("sparkContext", sparkContext);
        context.getBeanFactory().registerSingleton("sparkSession", sparkSession);
    }
}
