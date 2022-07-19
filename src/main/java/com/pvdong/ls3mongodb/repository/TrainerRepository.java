package com.pvdong.ls3mongodb.repository;

import com.pvdong.ls3mongodb.entity.Trainer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends MongoRepository<Trainer, String> {
}
