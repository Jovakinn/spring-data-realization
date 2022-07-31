package com.spark.jovakinn.unsafe_starter.realizations;

import com.spark.jovakinn.unsafe_starter.contracts.SparkTransformation;
import com.spark.jovakinn.unsafe_starter.contracts.TransformationSpider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component("orderBy")
public class OrderTransformationSpider implements TransformationSpider {
    @Override
    public SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNames) {
        return null;
    }
}
