package com.spark.jovakinn.unsafe_starter.realizations;

import com.spark.jovakinn.unsafe_starter.contracts.SparkTransformation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import java.util.List;

public class SortTransformation implements SparkTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset,
                                  List<String> columnNames,
                                  OrderedBag<Object> args) {
        return dataset.orderBy(columnNames.get(0), columnNames.stream()
                .skip(1).toArray(String[]::new));
    }
}
