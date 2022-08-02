package com.spark.jovakinn.sparkdata.domain;

import com.spark.jovakinn.unsafe_starter.annotations.ForeignKey;
import com.spark.jovakinn.unsafe_starter.annotations.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/criminals.csv")
@Slf4j
public class Criminal {
    private long id;
    private String name;
    private int number;
    @ForeignKey("criminalId")
    private List<Order> orders;

    public void printAllOrders() {
        orders.forEach(order -> log.info(String.valueOf(order)));
    }
}
