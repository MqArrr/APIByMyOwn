package by.makar.apibymyown.controllers;

import by.makar.apibymyown.dto.MeasurementDTO;
import by.makar.apibymyown.models.Measurement;
import by.makar.apibymyown.services.MeasurementService;
import by.makar.apibymyown.services.SensorService;
import by.makar.apibymyown.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorService sensorService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping("/{id}")
    public MeasurementDTO findOne(@PathVariable int id){
        return convertToMeasurementDTO(measurementService.findOne(id));
    }

    @GetMapping
    public MeasurementResponse findAll(){
        return new MeasurementResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO).toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> save(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            List<FieldError> errorsList = bindingResult.getFieldErrors();
            for(FieldError error : errorsList)
                errors.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            throw new MeasurementIsNotCreatedException(errors.toString());
        }
        enrichMeasurement(measurement);
        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        return measurementService.findAll().stream().filter(Measurement::getRaining).count();
    }
    @GetMapping("/getBySensor/{name}")
    public List<MeasurementDTO> getMeasurementsByName(@PathVariable String name){
        return measurementService.getMeasurementBySensorName(name).stream().map(this::convertToMeasurementDTO).toList();
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> notFound(MeasurementNotFoundException e){
        return new ResponseEntity<>(new ErrorResponse("Измерение не найдено.", System.currentTimeMillis()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> notCreated(MeasurementIsNotCreatedException e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()),HttpStatus.BAD_REQUEST);
    }
    private void enrichMeasurement(Measurement measurement){
        measurement.setSensor(sensorService.getSensorByName(measurement.getSensor().getName()).get());
        measurement.setTime(LocalDateTime.now());
    }
}
