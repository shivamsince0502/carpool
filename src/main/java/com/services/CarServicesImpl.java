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

import javax.management.Query;
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
        List<Car> carList = session.createQuery("from Car", Car.class).list();
        transaction.commit();
        session.close();
        return carList;
    }

    @Override
    public List<Car> getAllCarsOfUserById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT car.car_id as carId, car.car_name as carName," +
                " car.car_color as carColor, car.car_number as carNumber  " +
                "FROM car INNER JOIN owner_cars ON car.car_id = owner_cars.car_id WHERE owner_cars.owner_id ="+id;
        SQLQuery query = session.createSQLQuery(sql);
        List<Car> reqList = new ArrayList<>();
        List<Object[]> carList = query.list();
        for (Object[] row : carList) {
            Car car = new Car();
            car.setCarId(Integer.parseInt(row[0].toString()));
            car.setCarName(row[1].toString());
            car.setCarColor(row[2].toString());
            car.setCarNumber(row[3].toString());
            reqList.add(car);
        }
        return reqList;
    }

    @Override
    public Car addCar(OwnerCarPayload ownerCarPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String carName = ownerCarPayload.getCarName();
        String carColor = ownerCarPayload.getCarColor();
        String carNumber = ownerCarPayload.getCarNumber();
        if(carName.equals("")) return null;
        String insql = "insert into car (car_name, car_color, car_number) values ('" + carName+ "', '"+ carColor +"', '"+carNumber+"')";
        SQLQuery inquery = session.createSQLQuery(insql);
        inquery.executeUpdate();
        String sql = "select * from car where car_number = '"+carNumber+"'";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Car.class);
        Car car1 = (Car) query.uniqueResult();
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
        String sql = "select * from car where car_id = "+id;
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Car.class);
        Car car = (Car) query.uniqueResult();
        transaction.commit();
        session.close();
        return car;
    }


}
