package com.pvdong.ls3mongodb.controller;

import com.pvdong.ls3mongodb.dto.HorseDto;
import com.pvdong.ls3mongodb.entity.Horse;
import com.pvdong.ls3mongodb.service.HorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class HorseController {

    @Autowired
    private HorseService horseService;

    @GetMapping(path = "/horse/all")
    public ResponseEntity<List<Horse>> getAllHorses() {
        return new ResponseEntity<>(horseService.findAllHorses(), HttpStatus.OK);
    }

    @GetMapping(path = "/horse")
    public ResponseEntity<List<Horse>> getHorsesByIdAndYear(@RequestParam("trainer_id") String id, @RequestParam("year") Integer year) {
        return new ResponseEntity<>(horseService.findHorseList(id, year), HttpStatus.OK);
    }

    @PostMapping(path = "/horse")
    public ResponseEntity<Horse> createHorse(@RequestBody Horse horse) {
        return new ResponseEntity<>(horseService.saveHorse(horse), HttpStatus.CREATED);
    }

    @PutMapping(path = "/horse/{id}")
    public ResponseEntity<Horse> updateHorse(@PathVariable String id, @RequestBody HorseDto horseDto) {
        return new ResponseEntity<>(horseService.update(id, horseDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/horse/{id}")
    public ResponseEntity<HttpStatus> deleteHorse(@PathVariable String id) {
        horseService.deleteHorseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
