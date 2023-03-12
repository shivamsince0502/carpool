package com.services;

import com.model.*;
import com.payload.LoginPayload;
import com.payload.PoolerUpdatePayload;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class PoolerServiceImpl implements PoolerService {

    @Autowired
    private SessionFactory sessionFactory;
    public List<Pooler> getAllPooler() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Pooler> poolersList = session.createQuery("from Pooler", Pooler.class).list();
        transaction.commit();
        session.close();
        return poolersList;
    }

    public Pooler getPoolerById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Pooler pooler = session.get(Pooler.class, id);
        transaction.commit();
        session.close();
        return pooler;
    }

    public Pooler createPooler(Pooler pooler) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Pooler> poolerList = getAllPooler();
        for(Pooler pooler1 : poolerList) {
            if(pooler1.getUserName().equals(pooler.getUserName()) || pooler1.getPoolerEmail().equals(pooler.getPoolerEmail()) || pooler1.getPoolerMob().equals(pooler.getPoolerMob()))
                return new Pooler();
        }
        session.save(pooler);
        List<Pooler> poolerList1 = getAllPooler();
        int len = poolerList1.size();
        Pooler pooler1 = poolerList1.get(len-1);
        transaction.commit();
        session.close();
        return pooler1;
    }

    public Pooler updatePooler(Pooler pooler) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(pooler);
        transaction.commit();
        session.close();
        return pooler;
    }

    public Pooler deletePoolerById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Pooler pooler = session.get(Pooler.class, id);
        session.delete(pooler);
        transaction.commit();
        session.close();
        return pooler;
    }

    @Override
    public Pooler isPooler(LoginPayload loginPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Pooler> poolerList = session.createQuery("from Pooler", Pooler.class).list();
        Pooler pooler1 = new Pooler();
        for(Pooler pooler : poolerList) {
            String un = pooler.getUserName();
            String pw = pooler.getPassword();
            if(un.equals(loginPayload.getUsername()) && pw.equals(loginPayload.getPassword())){
                pooler1.setPoolerId(pooler.getPoolerId());
                pooler1.setPoolerEmail(pooler.getPoolerEmail());
                pooler1.setUserName(pooler.getUserName());
                pooler1.setPoolerName(pooler.getPoolerName());
                pooler1.setPoolerMob(pooler.getPoolerMob());
                break;
            }
        }
        return pooler1;
    }

    @Override
    public Pooler updatePooler(PoolerUpdatePayload poolerUpdatePayload, int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Pooler pooler = session.get(Pooler.class, id);
        pooler.setPoolerMob(poolerUpdatePayload.getPoolerMob());
        pooler.setPoolerName(poolerUpdatePayload.getPoolerName());
        pooler.setPoolerEmail(poolerUpdatePayload.getPoolerEmail());
        pooler.setUserName(poolerUpdatePayload.getUserName());
        session.saveOrUpdate(pooler);
        transaction.commit();
        session.close();
        return pooler;
    }

    @Override
    public List<RidePooler> allUpRideByPoolerId(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<RidePooler> ridePoolerList = session.createQuery("from RidePooler", RidePooler.class).list();
        List<RidePooler> requiredList = new ArrayList<>();
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        for(RidePooler ridePooler : ridePoolerList) {
            int rideId = ridePooler.getRideId();
            int poolerId = ridePooler.getPoolerId();
            Ride ride = session.get(Ride.class, rideId);
            if(poolerId == id && ridePooler.getActive() && ride.getRideDate().compareTo(date) >= 0) {
                requiredList.add(ridePooler);
            }
        }
        transaction.commit();
        session.close();
        return requiredList;
    }

    @Override
    public List<RidePooler> allPrevRideByPoolerId(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<RidePooler> ridePoolerList = session.createQuery("from RidePooler", RidePooler.class).list();
        List<RidePooler> requiredList = new ArrayList<>();
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        ArrayList<Integer> delRidePool = new ArrayList<>();
        List<DeletePoolerRide> deletePoolerRides = session.createQuery("from DeletePoolerRide", DeletePoolerRide.class).list();
        for(DeletePoolerRide deletePoolerRide : deletePoolerRides) {
            delRidePool.add(deletePoolerRide.getRidePoolerId());
        }

        for(RidePooler ridePooler : ridePoolerList) {
            Ride ride = session.get(Ride.class, ridePooler.getRideId());
            if(ridePooler.getPoolerId() == id && delRidePool.contains(ridePooler.getRidePoolerId()) == false && (ride.getRideDate().compareTo(date) < 0 || !ride.getActive())) {
                requiredList.add(ridePooler);
            }
        }
        transaction.commit();
        session.close();
        return requiredList;
    }

    @Override
    public RidePooler deleteRidePooler(int rideId, int poolerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<RidePooler> ridePoolerList = session.createQuery("from RidePooler", RidePooler.class).list();
        for(RidePooler ridePooler : ridePoolerList) {
            if(ridePooler.getPoolerId() == poolerId && ridePooler.getRideId() == rideId){
                DeletePoolerRide deletePoolerRide = new DeletePoolerRide();
                deletePoolerRide.setRidePoolerId(ridePooler.getRidePoolerId());
                session.save(deletePoolerRide);
                transaction.commit();
                session.close();
                return ridePooler;
            }
        }
        return null;
    }
}
