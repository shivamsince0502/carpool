package com.controller;

import com.model.City;
import com.services.CityServices;
import com.services.PoolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    CityServices cityServices;

    @GetMapping("/allcities")
    List<City> getAllCities() {
        return cityServices.getAllCities();
    }



}
