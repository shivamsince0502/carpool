package com.services;

import com.model.Car;
import com.payload.OwnerCarPayload;

import java.util.List;

public interface CarServices {

    List<Car> getAllCars();
    List<Car> getAllCarsOfUserById(int id);
    Car addCar(OwnerCarPayload ownerCarPayload);

    Car getCarById(int id);

    Car addCar(Car car);

}
