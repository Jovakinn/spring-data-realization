package com.spark.jovakinn.unsafe_starter.realizations;

import com.spark.jovakinn.unsafe_starter.contracts.DataExtractor;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FirstLevelCacheService {

    private final Map<Class<?>, Dataset<Row>> model2Dataset = new HashMap<>();
    @Autowired
    private  DataExtractorResolver extractorResolver;


    public List<?> readDataFor(long ownerId, Class<?> modelClass,
                                    String pathToSource, String foreignKey,
                                    ConfigurableApplicationContext context) {
        if (!model2Dataset.containsKey(modelClass)) {
            DataExtractor extractor = extractorResolver.resolve(pathToSource);
            Dataset<Row> dataset = extractor.load(pathToSource, context);
            dataset.persist();
            model2Dataset.put(modelClass, dataset);
        }
        Encoder<?> encoder = Encoders.bean(modelClass);
        return model2Dataset.get(modelClass).filter(functions.col(foreignKey).equalTo(ownerId))
                .as(encoder).collectAsList();
    }
}
