package com.spark.jovakinn.unsafe_starter.realizations;

import com.spark.jovakinn.unsafe_starter.annotations.ForeignKey;
import com.spark.jovakinn.unsafe_starter.annotations.Source;
import com.spark.jovakinn.unsafe_starter.contracts.FinalizerPostFinalizer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class LazyCollectionInjectorFinalizerPostFinalizer implements FinalizerPostFinalizer {
    private final ConfigurableApplicationContext context;

    @SneakyThrows
    @Override
    public Object postFinalize(Object retVal) {
        if (Collection.class.isAssignableFrom(retVal.getClass())) {
            for (Object model : (List) retVal) {

                Field idField = model.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                long ownerId = idField.getLong(model);
                Field[] fields = model.getClass().getDeclaredFields();

                for (Field field : fields) {
                    if (List.class.isAssignableFrom(field.getType())) {
                        LazySparkList lazySparkList = context.getBean(LazySparkList.class);
                        lazySparkList.setOwnerId(ownerId);
                        String columnName = field.getAnnotation(ForeignKey.class).value();
                        lazySparkList.setForeignKeyName(columnName);
                        Class<?> embeddedModel = getEmbeddedModel(field);
                        lazySparkList.setModelClass(embeddedModel);
                        String pathToData = embeddedModel.getAnnotation(Source.class).value();
                        lazySparkList.setPathToSource(pathToData);

                        field.setAccessible(true);
                        field.set(model, lazySparkList);
                    }
                }
            }
        }
        return retVal;
    }

    private Class<?> getEmbeddedModel(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        return (Class<?>) genericType.getActualTypeArguments()[0];
    }
}
