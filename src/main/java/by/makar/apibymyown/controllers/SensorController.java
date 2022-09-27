package by.makar.apibymyown.controllers;

import by.makar.apibymyown.dto.SensorDTO;
import by.makar.apibymyown.models.Sensor;
import by.makar.apibymyown.services.SensorService;
import by.makar.apibymyown.util.ErrorResponse;
import by.makar.apibymyown.util.SensorIsNotCreatedException;
import by.makar.apibymyown.util.SensorNotFoundException;
import by.makar.apibymyown.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public List<SensorDTO> getSensors(){
        return sensorService.findAll().stream().map(this::convertToSensorDTO).toList();
    }

    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable int id){
        return convertToSensorDTO(sensorService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            List<FieldError> errorsList = bindingResult.getFieldErrors();
            for(FieldError error : errorsList)
                errors.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            throw new SensorIsNotCreatedException(errors.toString());
        }
        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> notFound(SensorNotFoundException e){
        return new ResponseEntity<>(new ErrorResponse("Сенсор не найден.", System.currentTimeMillis()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> notCreated(SensorIsNotCreatedException e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()),HttpStatus.BAD_REQUEST);
    }
}
