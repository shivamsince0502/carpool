package com.services;

import com.model.Car;
import com.model.City;

import java.util.List;

public interface CityServices {
    City addCity(City city);
    List<City> getAllCities();
}
