package com.spark.jovakinn.unsafe_starter.realizations;

import com.spark.jovakinn.unsafe_starter.contracts.FilterTransformation;
import com.spark.jovakinn.unsafe_starter.contracts.SparkTransformation;
import com.spark.jovakinn.unsafe_starter.contracts.TransformationSpider;
import com.spark.jovakinn.unsafe_starter.utils.WordsMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("findBy")
@RequiredArgsConstructor
public class FilterSpider implements TransformationSpider {
    private final Map<String, FilterTransformation> filterTransformationMap;

    @Override
    public Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNames) {
        List<String> columnNames = List.of(WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNames, methodWords));
        String filterName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(filterTransformationMap.keySet(), methodWords);
        return new Tuple2<>(filterTransformationMap.get(filterName), columnNames);
    }
}
