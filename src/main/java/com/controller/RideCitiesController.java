package com.controller;

import com.model.City;
import com.services.RideCitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/ridecities")
public class RideCitiesController {
    @Autowired
    private RideCitiesService rideCitiesService;

    @GetMapping("/getallcitiesbyride/{id}")
    public List<City> allCitiesByRideId(@PathVariable int id) {
        return rideCitiesService.citiesListOfRide(id);
    }
}
