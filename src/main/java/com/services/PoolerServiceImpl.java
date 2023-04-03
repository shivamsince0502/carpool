package com.services;

import com.model.*;
import com.payload.LoginPayload;
import com.payload.PoolerUpdatePayload;
import com.payload.UpRidePoolerDet;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.*;

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
        String usql = "select * from pooler where pooler_id = "+id;
        SQLQuery query = session.createSQLQuery(usql);
        query.addEntity(Pooler.class);
        Pooler pooler = (Pooler) query.uniqueResult();
        transaction.commit();
        session.close();
        return pooler;
    }

    public Pooler createPooler(Pooler pooler) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "select * from pooler where pooler_username = '"+pooler.getUserName()+
                "' or pooler_mob = '"+pooler.getPoolerMob()+"' pooler_email = '"+pooler.getPoolerEmail()+"' ";
        SQLQuery query = session.createSQLQuery(sql);
        Pooler p = (Pooler)query.uniqueResult();
        if(p != null) new Pooler();
        session.save(pooler);
        sql = "select * from pooler where pooler_username = '"+pooler.getUserName()+
                "' or pooler_mob = '"+pooler.getPoolerMob()+"' pooler_email = '"+pooler.getPoolerEmail()+"' ";
        query = session.createSQLQuery(sql);
        Pooler pooler1 = (Pooler)query.uniqueResult();
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
        String sql = "select * from pooler where pooler_username = '"+loginPayload.getUsername() + "' and pooler_password = '"+loginPayload.getPassword()+"'";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Pooler.class);
        Pooler pooler = (Pooler) query.uniqueResult();
        if(pooler == null) return new Pooler();
        return pooler;
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
    public List<UpRidePoolerDet> allUpRideByPoolerId(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<UpRidePoolerDet> requiredList = new ArrayList<>();

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        date = new java.sql.Date(cal.getTime().getTime());
        String sql = "SELECT rp.ride_id, c.car_name , o.owner_name, o.owner_mob ,sc.city_name AS startcity, ec.city_name AS endcity, rp.ride_date, rp.pooler_id " +
                "FROM ride_pooler rp " +
                "JOIN ride r ON rp.ride_id = r.ride_id " +
                "JOIN owner o ON r.owner_id = o.owner_id " +
                "JOIN car c ON r.car_id = c.car_id " +
                "JOIN city sc ON rp.start_id = sc.city_id " +
                "JOIN city ec ON rp.end_id = ec.city_id " +
                "WHERE rp.ride_pooler_id NOT IN (SELECT ride_pooler_id FROM deletepoolerride) " +
                "AND rp.ride_date > '"+date+"' " +
                "AND rp.is_active = 1 " +
                "ORDER BY rp.ride_date ";

        SQLQuery query = session.createSQLQuery(sql);
        List<Object[]> alllist = session.createSQLQuery(sql).list();
        for(Object[] row : alllist) {
            UpRidePoolerDet res = new UpRidePoolerDet(Integer.parseInt(row[0].toString()),
                    row[1].toString(), row[2].toString(),
                    row[3].toString(), row[4].toString(),
                    row[5].toString(), Timestamp.valueOf(row[6].toString()),
                    Integer.parseInt(row[7].toString()));
            requiredList.add(res);

        }
        transaction.commit();
        session.close();
        return requiredList;
    }

    @Override
    public List<UpRidePoolerDet> allPrevRideByPoolerId(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<UpRidePoolerDet> requiredList = new ArrayList<>();

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        date = new java.sql.Date(cal.getTime().getTime());
        String sql = "SELECT rp.ride_id, c.car_name , o.owner_name, o.owner_mob ,sc.city_name AS startcity, ec.city_name AS endcity, rp.ride_date, rp.pooler_id " +
                "FROM ride_pooler rp " +
                "JOIN ride r ON rp.ride_id = r.ride_id " +
                "JOIN owner o ON r.owner_id = o.owner_id " +
                "JOIN car c ON r.car_id = c.car_id " +
                "JOIN city sc ON rp.start_id = sc.city_id " +
                "JOIN city ec ON rp.end_id = ec.city_id " +
                "WHERE rp.ride_pooler_id NOT IN (SELECT ride_pooler_id FROM deletepoolerride) " +
                "AND rp.ride_date <= '"+date+"' " +
                "AND rp.is_active = 0 " +
                "ORDER BY rp.ride_date ";

        SQLQuery query = session.createSQLQuery(sql);
        List<Object[]> alllist = session.createSQLQuery(sql).list();
        for(Object[] row : alllist) {
            UpRidePoolerDet res = new UpRidePoolerDet(Integer.parseInt(row[0].toString()),
                    row[1].toString(), row[2].toString(),
                    row[3].toString(), row[4].toString(),
                    row[5].toString(), Timestamp.valueOf(row[6].toString()),
                    Integer.parseInt(row[7].toString()));
            requiredList.add(res);

        }
        transaction.commit();
        session.close();
        return requiredList;
    }

    @Override
    public RidePooler deleteRidePooler(int rideId, int poolerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "INSERT INTO deleteridepooler (ride_pooler_id) " +
                "SELECT ride_pooler.ride_pooler_id\n" +
                "FROM ride+pooler " +
                "WHERE ride_pooler.ride_id =" +rideId + " "+
                "  AND ridepooler.pooler_id =" + poolerId + " "+
                "  AND ridepooler.is_active = false";
        SQLQuery query = session.createSQLQuery(sql);
        query.executeUpdate();
        RidePooler rp = new RidePooler();
        rp.setSeatNo(13245);
        return rp;
    }

    @Override
    public List<PoolerNotification> allNotifOfPoolerById(int poolerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "select * from poolernotification where pooler_id = " +poolerId +" and is_read = false";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(PoolerNotification.class);
        List result = query.list();
        transaction.commit();
        session.close();
        return result;
    }

    @Override
    public PoolerNotification readNotificationByPooler(int notifId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        PoolerNotification poolerNotification = session.get(PoolerNotification.class, notifId);
        poolerNotification.setRead(true);
        session.saveOrUpdate(poolerNotification);
        transaction.commit();
        session.close();
        return poolerNotification;
    }
}
