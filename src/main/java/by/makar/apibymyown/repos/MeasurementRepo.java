package by.makar.apibymyown.repos;

import by.makar.apibymyown.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepo extends JpaRepository<Measurement, Integer> {
    public List<Measurement> getMeasurementByRaining(boolean raining);
    public List<Measurement> getMeasurementBySensorName(String name);
}
