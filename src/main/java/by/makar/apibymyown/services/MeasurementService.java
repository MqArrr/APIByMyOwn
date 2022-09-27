package by.makar.apibymyown.services;

import by.makar.apibymyown.models.Measurement;
import by.makar.apibymyown.repos.MeasurementRepo;
import by.makar.apibymyown.util.MeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepo measurementRepo;

    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo) {
        this.measurementRepo = measurementRepo;
    }
    @Transactional(readOnly = true)
    public Measurement findOne(int id){
        return measurementRepo.findById(id).orElseThrow(MeasurementNotFoundException::new);
    }
    @Transactional(readOnly = true)
    public List<Measurement> findAll(){
        return measurementRepo.findAll();
    }
    @Transactional(readOnly = true)
    public List<Measurement> getRainyDays(){
        return measurementRepo.getMeasurementByRaining(true);
    }
    @Transactional
    public void save(Measurement measurement){
        measurementRepo.save(measurement);
    }
    @Transactional(readOnly = true)
    public List<Measurement> getMeasurementBySensorName(String name){
        return measurementRepo.getMeasurementBySensorName(name);
    }
}
