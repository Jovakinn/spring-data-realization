package com.spark.jovakinn.unsafe_starter.realizations;

import com.spark.jovakinn.unsafe_starter.contracts.FilterTransformation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("contains")
public class FilterContains implements FilterTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedBag<Object> args) {
        return dataset.filter(functions.col(columnNames.get(0)).contains(args.takeAndRemove()));
    }
}
