package com.pvdong.ls3mongodb.repository;

import com.pvdong.ls3mongodb.entity.Horse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorseRepository extends MongoRepository<Horse, String> {
}
