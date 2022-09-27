package by.makar.apibymyown.util;

import by.makar.apibymyown.dto.MeasurementDTO;

import java.util.List;

public class MeasurementResponse {
    private List<MeasurementDTO> measurements;

    public MeasurementResponse() {
    }

    public MeasurementResponse(List<MeasurementDTO> measurementDTOS) {
        this.measurements = measurementDTOS;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurementDTOS) {
        this.measurements = measurementDTOS;
    }
}
