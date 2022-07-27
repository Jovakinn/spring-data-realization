package com.spark.jovakinn.starter.realizations;

import com.spark.jovakinn.starter.contracts.DataExtractor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.ConfigurableApplicationContext;

public class JsonDataExtractor implements DataExtractor {
    @Override
    public Dataset<Row> load(String pathToData, ConfigurableApplicationContext context) {
        return context.getBean(SparkSession.class).read().json(pathToData);
    }
}
