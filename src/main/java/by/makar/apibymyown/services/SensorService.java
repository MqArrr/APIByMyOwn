package by.makar.apibymyown.services;

import by.makar.apibymyown.models.Sensor;
import by.makar.apibymyown.repos.SensorRepo;
import by.makar.apibymyown.util.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepo sensorRepo;

    @Autowired
    public SensorService(SensorRepo sensorRepo) {
        this.sensorRepo = sensorRepo;
    }
    @Transactional(readOnly = true)
    public List<Sensor> findAll(){
        return sensorRepo.findAll();
    }
    @Transactional(readOnly = true)
    public Sensor findOne(int id){
        return sensorRepo.findById(id).orElseThrow(SensorNotFoundException::new);
    }
    @Transactional
    public void save(Sensor sensor){
        sensor.setRegistration(LocalDateTime.now());
        sensorRepo.save(sensor);
    }
    @Transactional(readOnly = true)
    public Optional<Sensor> getSensorByName(String name){
        return sensorRepo.getSensorByName(name);
    }
}
