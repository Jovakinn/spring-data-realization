package com.spark.jovakinn.sparkdata.repos;

import com.spark.jovakinn.sparkdata.domain.Speaker;
import com.spark.jovakinn.unsafe_starter.contracts.SparkRepository;

import java.util.List;

public interface SpeakerRepo extends SparkRepository<Speaker> {
    List<Speaker> findByAgeBetween(int min, int max);
    long findByAgeGreaterThanCount(int min);
}
