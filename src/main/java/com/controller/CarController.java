package com.controller;

import com.model.Car;
import com.payload.OwnerCarPayload;
import com.services.CarServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarServices carServices;

    @GetMapping("/getcar{id}")
    Car getCarById(@PathVariable int id){
        return carServices.getCarById(id);
    }

    @GetMapping("/getallcarsofowner/{id}")
    List<Car> getAllCarsByOwner(@PathVariable int id) {
        return carServices.getAllCarsOfUserById(id);
    }

    @PostMapping("/createcar")
    Car createCar(@RequestBody OwnerCarPayload ownerCarPayload) {
        return carServices.addCar(ownerCarPayload);
    }

}
