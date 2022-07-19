package com.pvdong.ls3mongodb.controller;

import com.pvdong.ls3mongodb.dto.TrainerDto;
import com.pvdong.ls3mongodb.entity.Trainer;
import com.pvdong.ls3mongodb.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @PostMapping(path = "/trainer")
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        return new ResponseEntity<>(trainerService.createTrainer(trainer), HttpStatus.CREATED);
    }

    @GetMapping(path = "/trainer/{id}")
    public ResponseEntity<Trainer> getTrainer(@PathVariable String id) {
        return new ResponseEntity<>(trainerService.findTrainerById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/trainer/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable String id, @RequestBody TrainerDto trainerDto) {
        return new ResponseEntity<>(trainerService.updateTrainerById(id, trainerDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/trainer/{id}")
    public ResponseEntity<HttpStatus> deleteTrainer(@PathVariable String id) {
        trainerService.deleteTrainerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
