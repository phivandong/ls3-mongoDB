package com.pvdong.ls3mongodb.service;

import com.pvdong.ls3mongodb.dto.TrainerDto;
import com.pvdong.ls3mongodb.entity.Trainer;
import com.pvdong.ls3mongodb.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    TrainerRepository trainerRepository;

    @Override
    public Trainer createTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
        return trainer;
    }

    @Override
    public Trainer findTrainerById(String id) {
        if (trainerRepository.findById(id).isPresent()) {
            return trainerRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void deleteTrainerById(String id) {
        trainerRepository.deleteById(id);
    }

    @Override
    public Trainer updateTrainerById(String id, TrainerDto trainerDto) {
        Trainer foundTrainer = findTrainerById(id);
        foundTrainer.setName(trainerDto.getName());
        return trainerRepository.save(foundTrainer);
    }
}
