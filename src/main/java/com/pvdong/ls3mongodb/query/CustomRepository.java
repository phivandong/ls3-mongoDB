package com.pvdong.ls3mongodb.query;

import com.pvdong.ls3mongodb.entity.Horse;

import java.util.List;

public interface CustomRepository {
    List<Horse> getListHorse(String trainerId, Integer year);
}
