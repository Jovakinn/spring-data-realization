package com.spark.jovakinn.unsafe_starter.realizations;

import com.spark.jovakinn.unsafe_starter.contracts.Finalizer;
import lombok.SneakyThrows;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

@Component("collect")
public class CollectFinalizer implements Finalizer {
    @SneakyThrows
    @Override
    public Object doAction(Dataset<Row> dataset, Class<?> modelClass) {
        Encoder<?> encoder = Encoders.bean(modelClass);
        List<String> listFieldsNames = Arrays.stream(encoder.schema().fields())
                .filter(structField -> structField.dataType() instanceof ArrayType)
                .map(StructField::name)
                .toList();
        for (String fieldName : listFieldsNames) {
            ParameterizedType genericType = (ParameterizedType) modelClass.getDeclaredField(fieldName).getGenericType();
            var c = (Class<?>) genericType.getActualTypeArguments()[0];
            dataset = dataset.withColumn(fieldName, functions
                    .lit(null).cast(DataTypes.createStructType(Encoders.bean(c).schema().fields())));
        }
        return dataset.as(encoder).collectAsList();
    }
}
