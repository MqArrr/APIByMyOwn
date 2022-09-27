package by.makar.apibymyown.util;

import by.makar.apibymyown.models.Measurement;
import by.makar.apibymyown.models.Sensor;
import by.makar.apibymyown.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if(measurement.getSensor() == null) {
            return;
        }
        if(sensorService.getSensorByName(measurement.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "", "Сенсора с этим именем не существует.");
    }
}
