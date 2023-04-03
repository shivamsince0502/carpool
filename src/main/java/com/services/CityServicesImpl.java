package com.services;

import com.model.City;
import org.hibernate.SQLQuery;
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
    public List<String> getAllCities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "select city.city_name from city";
        SQLQuery query = session.createSQLQuery(sql);
        List<String> cities = query.getResultList();
        transaction.commit();
        session.close();
        return cities;
    }

    @Override
    public City getCityById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        City city = session.get(City.class, id);
        transaction.commit();
        session.close();
        return city;
    }

    @Override
    public List<String> getAllCitiesByNames(String cityName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT city_name FROM city WHERE city_name LIKE '"+cityName+"%'";
        SQLQuery query = session.createSQLQuery(sql);
        List<String> cities = query.list();
        transaction.commit();
        session.close();
        return cities;
    }

}
