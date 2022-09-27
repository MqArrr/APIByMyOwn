package by.makar.apibymyown.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {
   @NotNull(message = "Не указан сенсор.")
    private SensorDTO sensor;
    @NotNull(message = "Не указан результат измерений.")
    @Min(value = -100,message = "Не может быть меньше -100.")
    @Max(value = 100,message = "Не может быть больше 100.")
    private Float value;
    @NotNull(message = "Не указан результат измерений.")
    private Boolean raining;


    public MeasurementDTO() {
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }
}
