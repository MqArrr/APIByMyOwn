package by.makar.apibymyown.repos;

import by.makar.apibymyown.models.Measurement;
import by.makar.apibymyown.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface SensorRepo extends JpaRepository<Sensor, Integer> {
    public Optional<Sensor> getSensorByName(String name);
}
