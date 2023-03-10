package com.services;

import com.model.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServicesImpl implements CityServices{

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public City addCity(City city) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(city);
        transaction.commit();
        session.close();
        return city;
    }

    @Override
    public List<City> getAllCities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<City> cities = session.createQuery("from City", City.class).list();
        transaction.commit();
        session.close();
        return cities;
    }
}
