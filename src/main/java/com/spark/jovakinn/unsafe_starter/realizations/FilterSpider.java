package com.spark.jovakinn.unsafe_starter.realizations;

import com.spark.jovakinn.unsafe_starter.contracts.FilterTransformation;
import com.spark.jovakinn.unsafe_starter.contracts.SparkTransformation;
import com.spark.jovakinn.unsafe_starter.contracts.TransformationSpider;
import com.spark.jovakinn.unsafe_starter.utils.WordsMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("findBy")
public class FilterSpider implements TransformationSpider {
    private Map<String, FilterTransformation> filterTransformationMap;
    @Override
    public SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNames) {
        String fieldName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNames, methodWords);
        String filterName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(filterTransformationMap.keySet(), methodWords);
        return filterTransformationMap.get(filterName);
    }
}
