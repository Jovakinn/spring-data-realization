package com.spark.jovakinn.sparkdata.domain;

import com.spark.jovakinn.unsafe_starter.annotations.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/orders.csv")
public class Order {
    private String name;
    private String description;
    private int price;
    private long criminalId;
}
