package com.spark.jovakinn.unsafe_starter.aspects;

import com.spark.jovakinn.unsafe_starter.realizations.FirstLevelCacheService;
import com.spark.jovakinn.unsafe_starter.realizations.LazySparkList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@Aspect
public class LazyCollectionAspectHandler {
    @Autowired
    private FirstLevelCacheService firstLevelCacheService;
    @Autowired
    private ConfigurableApplicationContext context;

    @Before("execution(* com.spark.jovakinn.unsafe_starter.realizations.LazySparkList.*(..)) &&" +
            "execution(* java.util.List.*(..)) ")
    public void setLazyCollections(JoinPoint jp) {
        LazySparkList lazyList =(LazySparkList) jp.getTarget();
        if (!lazyList.initialized()) {
            List content = firstLevelCacheService.readDataFor(lazyList.getOwnerId(), lazyList.getModelClass(),
                    lazyList.getPathToSource(), lazyList.getForeignKeyName(), context);
            lazyList.setContent(content);
        }
    }
}
