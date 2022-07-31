package com.spark.jovakinn.unsafe_starter.realizations;

import com.spark.jovakinn.unsafe_starter.contracts.DataExtractor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataExtractorResolver {

    private final Map<String, DataExtractor> extractorMap;

    public DataExtractorResolver(Map<String, DataExtractor> extractorMap) {
        this.extractorMap = extractorMap;
    }

    DataExtractor resolve(String pathToData) {
        String fileExtension = pathToData.split("\\.")[1];
        return extractorMap.get(fileExtension);
    }
}
