package com.services;

import com.model.Car;
import com.model.Owner;
import com.model.OwnerCar;
import com.model.RidePooler;
import com.payload.OwnerCarPayload;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CarServicesImpl implements CarServices {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Car> getAllCars() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Car> carList = session.createQuery("from Owner", Car.class).list();
        transaction.commit();
        session.close();
        return carList;
    }

    @Override
    public List<Car> getAllCarsOfUserById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<OwnerCar> ownerCarList = session.createQuery("from OwnerCar", OwnerCar.class).list();
        List<Car> carList = new ArrayList<>();
        for(OwnerCar ownerCar : ownerCarList) {
            if(ownerCar.getOwnerId() == id) {
                Car car = session.get(Car.class, ownerCar.getCarId());
                carList.add(car);
            }
        }
        return carList;
    }

    @Override
    public Car addCar(OwnerCarPayload ownerCarPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String carName = ownerCarPayload.getCarName();
        String carColor = ownerCarPayload.getCarColor();
        String carNumber = ownerCarPayload.getCarNumber();
        if(carName == null) return null;
        Car car = new Car();
        car.setCarName(carName);
        car.setCarColor(carColor);
        car.setCarNumber(carNumber);
        session.save(car);
        List<Car> carList = session.createQuery("from Car", Car.class).list();
        int idx = carList.size();
        Car car1 = carList.get(idx-1);
        OwnerCar ownerCar = new OwnerCar();
        ownerCar.setCarId(car1.getCarId());
        int ownerId = ownerCarPayload.getOwnerId();
        ownerCar.setOwnerId(ownerId);
        session.save(ownerCar);
        transaction.commit();
        session.close();
        return car1;
    }

    @Override
    public Car getCarById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Car car = session.get(Car.class, id);
        transaction.commit();
        session.close();
        return car;
    }

    @Override
    public Car addCar(Car car) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
        session.close();
        return car;
    }
}
