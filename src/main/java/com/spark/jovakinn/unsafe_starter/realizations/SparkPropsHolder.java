package com.spark.jovakinn.unsafe_starter.realizations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spark")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparkPropsHolder {
    private String appName;
    private String packagesToScan;
}
