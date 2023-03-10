package com.services;

import com.model.RideCities;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideCitiesServiceImpl implements RideCitiesService{

    @Autowired
    SessionFactory sessionFactory;
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
}
