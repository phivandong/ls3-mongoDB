package com.pvdong.ls3mongodb.repository;

import com.pvdong.ls3mongodb.entity.HorseAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HorseAccountRepository extends MongoRepository<HorseAccount, String> {
    @Query("{'horse_id' : ?0, 'account_id' : ?1}")
    HorseAccount findWithHorseIdAndAccountId(String horseId, String accountId);
}
