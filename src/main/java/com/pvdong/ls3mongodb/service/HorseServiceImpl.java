package com.pvdong.ls3mongodb.service;

import com.pvdong.ls3mongodb.dto.HorseDto;
import com.pvdong.ls3mongodb.entity.Horse;
import com.pvdong.ls3mongodb.entity.HorseAccount;
import com.pvdong.ls3mongodb.entity.Trainer;
import com.pvdong.ls3mongodb.query.CustomRepository;
import com.pvdong.ls3mongodb.repository.HorseAccountRepository;
import com.pvdong.ls3mongodb.repository.HorseRepository;
import com.pvdong.ls3mongodb.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorseServiceImpl implements HorseService {

    @Autowired
    HorseRepository horseRepository;

    @Autowired
    HorseAccountRepository horseAccountRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    CustomRepository customRepository;

    private List<Horse> filterByYear(Integer yearInt) {
        String yearString = yearInt.toString();
        String pattern = "yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        List<Horse> horses = horseRepository.findAll();
        return horses.parallelStream().filter(horse -> simpleDateFormat.format(horse.getFoaled()).equals(yearString)).collect(Collectors.toList());
    }

    private Trainer filterTrainerById(String id) {
        return trainerRepository.findById(id).get();
    }

    @Override
    public List<Horse> findHorseList(String id, Integer year) {
        return customRepository.getListHorse(id, year);
    }

    @Override
    public List<Horse> findByTrainerIdAndYear(String id, Integer year) {
        Trainer trainer = filterTrainerById(id);
        List<Horse> horses = filterByYear(year);
        List<HorseAccount> horseAccounts = new ArrayList<>();
        horses.forEach(horse -> {
            HorseAccount horseAccount = horseAccountRepository.findWithHorseIdAndAccountId(horse.getId(), trainer.getAccount_id());
            if (horseAccount != null) {
                horseAccounts.add(horseAccount);
            }
        });
        List<String> listHorseIds = new ArrayList<>();
        horseAccounts.forEach(horseAccount -> listHorseIds.add(horseAccount.getHorse_id()));
        return (List<Horse>) horseRepository.findAllById(listHorseIds);
    }

    @Override
    public List<Horse> findAllHorses() {
        return horseRepository.findAll();
    }

    @Override
    public Horse saveHorse(Horse horse) {
        horseRepository.save(horse);
        return horse;
    }

    @Override
    public Horse findHorseById(String id) {
        if (horseRepository.findById(id).isPresent()) {
            return horseRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Horse update(String id, HorseDto horseDto) {
        Horse foundHorse = findHorseById(id);
        foundHorse.setName(horseDto.getName());
        foundHorse.setFoaled(horseDto.getFoaled());
        horseRepository.save(foundHorse);
        return foundHorse;
    }

    @Override
    public void deleteHorseById(String id) {
        horseRepository.deleteById(id);
    }
}
