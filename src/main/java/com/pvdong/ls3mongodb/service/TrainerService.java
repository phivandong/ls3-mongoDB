package com.pvdong.ls3mongodb.service;

import com.pvdong.ls3mongodb.dto.TrainerDto;
import com.pvdong.ls3mongodb.entity.Trainer;

public interface TrainerService {
    Trainer createTrainer(Trainer trainer);
    Trainer findTrainerById(String id);
    void deleteTrainerById(String id);
    Trainer updateTrainerById(String id, TrainerDto trainerDto);
}
