package com.services;

import com.model.City;
import com.model.RideCities;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideCitiesServiceImpl implements RideCitiesService{

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    CityServices cityServices;
    @Override
    public List<RideCities> getAllRideCities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "SELECT * FROM ride_cities";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(RideCities.class);
        List results = query.list();
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public RideCities createRideCity(int rideId, int cityId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RideCities rideCities = new RideCities();
        rideCities.setRideId(rideId);
        rideCities.setCityId(cityId);
        session.save(rideCities);
        transaction.commit();
        session.close();
        return rideCities;
    }

    @Override
    public List<City> citiesListOfRide(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<City>cities = new ArrayList<>();
        List<RideCities> rideCitiesList = session.createQuery("from RideCities", RideCities.class).list();
        for(RideCities rideCities : rideCitiesList){
            int rideId = rideCities.getRideId();
            if(rideId == id) {
                int cityId = rideCities.getCityId();
                City city = cityServices.getCityById(cityId);
                cities.add(city);
            }
        }
        return cities;
    }
}
