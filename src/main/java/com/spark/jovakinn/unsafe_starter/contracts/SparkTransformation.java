package com.spark.jovakinn.unsafe_starter.contracts;

import com.spark.jovakinn.unsafe_starter.realizations.OrderedBag;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

public interface SparkTransformation {
    Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedBag<Object> args);
}
