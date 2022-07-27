package com.spark.jovakinn.starter.realizations;

import com.spark.jovakinn.starter.contracts.DataExtractor;

import java.util.Map;

public class DataExtractorResolver {

    private Map<String, DataExtractor> extractorMap;

    DataExtractor resolve(String pathToData) {
        String fileExtension = pathToData.split("\\.")[1];
        return extractorMap.get(fileExtension);
    }
}
