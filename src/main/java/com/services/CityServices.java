package com.services;

import com.model.Car;
import com.model.City;

import java.util.List;

public interface CityServices {
    City addCity(City city);
    List<String> getAllCities();

    City getCityById(int id);
    List<String> getAllCitiesByNames(String cityName);
}
