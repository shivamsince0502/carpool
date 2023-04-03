package com.controller;

import com.model.City;
import com.services.CityServices;
import com.services.PoolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    CityServices cityServices;

    @GetMapping("/allcities")
    List<String> getAllCities() {
        return cityServices.getAllCities();
    }

    @GetMapping("getcitybyid/{id}")
    City getCityById(@PathVariable int id){
        return cityServices.getCityById(id);
    }

    @GetMapping("/getcitybyname/{name}")
    List<String> geallCitiesByName(@PathVariable("name") String cityName) {
        return cityServices.getAllCitiesByNames(cityName);
    }

}
