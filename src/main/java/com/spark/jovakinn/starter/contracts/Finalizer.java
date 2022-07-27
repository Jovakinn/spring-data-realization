package com.spark.jovakinn.starter.contracts;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface Finalizer {
    Object doAction(Dataset<Row> dataset);
}
