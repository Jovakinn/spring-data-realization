package com.spark.jovakinn.starter.config;

import com.spark.jovakinn.unsafe_starter.aspects.LazyCollectionAspectHandler;
import com.spark.jovakinn.unsafe_starter.realizations.FirstLevelCacheService;
import com.spark.jovakinn.unsafe_starter.realizations.LazySparkList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class StartConf {

    @Bean
    @Scope("prototype")
    public LazySparkList lazySparkList() {
        return new LazySparkList();
    }

    @Bean
    public FirstLevelCacheService firstLevelCacheService() {
        return new FirstLevelCacheService();
    }

    @Bean
    public LazyCollectionAspectHandler lazyCollectionAspectHandler() {
        return new LazyCollectionAspectHandler();
    }
}
