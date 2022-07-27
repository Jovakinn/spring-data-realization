package com.spark.jovakinn.starter.contracts;

import java.lang.reflect.Method;

public interface TransformationSpider {
    SparkTransformation getTransformation(Method[] methods);
}
