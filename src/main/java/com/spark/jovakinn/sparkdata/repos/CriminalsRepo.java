package com.spark.jovakinn.sparkdata.repos;

import com.spark.jovakinn.sparkdata.domain.Criminal;
import com.spark.jovakinn.unsafe_starter.contracts.SparkRepository;

import java.util.List;

public interface CriminalsRepo extends SparkRepository<Criminal> {
    List<Criminal> findByNumberGreaterThanOrderByNumber(int min);
}
