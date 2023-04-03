package com.services;

import com.model.*;
import com.payload.FinPoolResult;
import com.payload.LoginPayload;
import com.payload.OwnerUpdatePayload;
import com.payload.ReqDecPayload;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLData;
import java.sql.Timestamp;
import java.util.*;

@Service
public class  OwnerServiceImpl implements OwnerService{


    @Autowired
    private RideService rideService;
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CityServices cityServices;

    @Autowired
    private CarServices carServices;
    public List<Owner> getAllOwners() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Owner> ownerList = session.createQuery("from Owner", Owner.class).list();
        transaction.commit();
        session.close();
        return ownerList;
    }

    public Owner createOwner(Owner owner) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "select * from owner where owner_username = '"+owner.getUserName()+
                "' or owner_mob = '"+owner.getOwnerMob()+"' owner_email = '"+owner.getOwnerEmail()+"' ";
        SQLQuery query = session.createSQLQuery(sql);
        Owner o = (Owner) query.uniqueResult();
        if(o != null) new Pooler();
        session.save(owner);
        sql = "select * from owner where owner_username = '"+owner.getUserName()+
                "' or owner_mob = '"+owner.getOwnerMob()+"' owner_email = '"+owner.getOwnerEmail()+"' ";
        query = session.createSQLQuery(sql);
        Owner owner1 = (Owner) query.uniqueResult();
        transaction.commit();
        session.close();
        return owner1;
    }

    public Owner getOwnerById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.get(Owner.class, id);
        transaction.commit();
        session.close();
        return owner;
    }

    public Owner updateOwner(Owner owner) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(owner);
        transaction.commit();
        session.close();
        return owner;
    }

    public Owner deleteOwnerById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.get(Owner.class, id);
        session.delete(owner);
        transaction.commit();
        session.close();
        return owner;
    }

    @Override
    public Owner isOwner(LoginPayload loginPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "select * from owner where owner_username = '"+loginPayload.getUsername() +
                "' and owner_password = '"+loginPayload.getPassword()+"'";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Owner.class);
        Owner owner = (Owner) query.uniqueResult();
        if(owner == null) return new Owner();
        return owner;
    }

    @Override
    public Owner updateOwner(OwnerUpdatePayload ownerUpdatePayload, int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.get(Owner.class, id);
        owner.setOwnerMob(ownerUpdatePayload.getOwnerMob());
        owner.setOwnerName(ownerUpdatePayload.getOwnerName());
        owner.setOwnerEmail(ownerUpdatePayload.getOwnerEmail());
        owner.setUserName(ownerUpdatePayload.getUserName());
        session.saveOrUpdate(owner);
        transaction.commit();
        session.close();
        return owner;
    }


    @Override
    public List<FinPoolResult> getAllPrevRidesByOwnerId(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        date = new java.sql.Date(cal.getTime().getTime());
        List<String> allcities = cityServices.getAllCities();
        HashMap<Integer, String> cityMap = new HashMap<>();
        HashMap<String, Integer> cityMapR = new HashMap<>();
        HashMap<Integer, Integer> distMap = new HashMap<>();
        int i = 1;
        for(String city : allcities) {
            cityMap.put(i, city);
            cityMapR.put(city, i);
            i++;
        }

        String sql = "SELECT r.ride_id, r.owner_id as ride_owner_id, r.car_id as ride_car_id, r.no_of_seats, r.is_active, r.ride_datetime, c.*, o.*, GROUP_CONCAT(rc.city_id SEPARATOR ',') AS ride_cities " +
                "FROM ride AS r " +
                "JOIN car AS c ON r.car_id = c.car_id " +
                "JOIN owner AS o ON r.owner_id = o.owner_id " +
                "JOIN ride_cities AS rc ON r.ride_id = rc.ride_id " +
                "WHERE  r.owner_id = "+id+" " +
                "AND r.ride_id NOT IN (SELECT ride_id FROM deletedride) "+
                "AND (r.ride_datetime  < '"+date+"' " +
                "OR r.is_active = 0) "+
                "GROUP BY r.ride_id, c.car_id, o.owner_id, r.ride_datetime " +
                "ORDER BY r.ride_datetime ASC";
        SQLQuery query = session.createSQLQuery(sql);
        List<Object[]> result = query.list();
        List<FinPoolResult> finPoolResults = new ArrayList<>();
        for (Object[] row : result) {
            FinPoolResult finPoolResult = new FinPoolResult();
            Ride ride = new Ride();
            Owner owner = new Owner();
            Car car = new Car();
            ride.setRideId(Integer.parseInt(row[0].toString()));
            ride.setRideDate(Timestamp.valueOf(row[5].toString()));
            ride.setOwnerId(Integer.parseInt(row[1].toString()));
            ride.setNoOfSeats(Integer.parseInt(row[3].toString()));
            ride.setCarId(Integer.parseInt(row[2].toString()));


            owner.setOwnerId(Integer.parseInt(row[10].toString()));
            owner.setOwnerName(row[11].toString());
            owner.setOwnerEmail(row[12].toString());
            owner.setOwnerMob(row[13].toString());

            car.setCarName(row[7].toString());
            car.setCarColor(row[8].toString());
            car.setCarNumber(row[9].toString());
            car.setCarId(Integer.parseInt(row[6].toString()));
            finPoolResult.setRide(ride);
            finPoolResult.setCar(car);
            finPoolResult.setOwner(owner);
            List<String> cities = new ArrayList<>();
            String city = row[16].toString();
            for(i = 0; i< city.length(); i+=2) {
                int cId = Character.getNumericValue(city.charAt(i));
                cities.add(cityMap.get(cId));
            }
            finPoolResult.setCitiesOfRide(cities);
            finPoolResults.add(finPoolResult);
        }

        Collections.sort(finPoolResults, new Comparator<FinPoolResult>() {
            public int compare(FinPoolResult o1, FinPoolResult o2) {
                return o1.getRide().getRideDate().compareTo(o2.getRide().getRideDate());
            }
        });
        transaction.commit();
        session.close();
        return finPoolResults;
    }

    @Override
    public List<FinPoolResult> getAllUpRides(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        date = new java.sql.Date(cal.getTime().getTime());
        List<String> allcities = cityServices.getAllCities();
        HashMap<Integer, String> cityMap = new HashMap<>();
        HashMap<String, Integer> cityMapR = new HashMap<>();
        HashMap<Integer, Integer> distMap = new HashMap<>();
        int i = 1;
        for(String city : allcities) {
            cityMap.put(i, city);
            cityMapR.put(city, i);
            i++;
        }

        String sql = "SELECT r.ride_id, r.owner_id as ride_owner_id, r.car_id as ride_car_id, r.no_of_seats, r.is_active, r.ride_datetime, c.*, o.*, GROUP_CONCAT(rc.city_id SEPARATOR ',') AS ride_cities " +
                "FROM ride AS r " +
                "JOIN car AS c ON r.car_id = c.car_id " +
                "JOIN owner AS o ON r.owner_id = o.owner_id " +
                "JOIN ride_cities AS rc ON r.ride_id = rc.ride_id " +
                "WHERE  r.owner_id = "+id+" " +
                "AND r.ride_datetime  >= '"+date+"' " +
                "AND r.is_active = 1 "+
                "GROUP BY r.ride_id, c.car_id, o.owner_id, r.ride_datetime " +
                "ORDER BY r.ride_datetime ASC";
        SQLQuery query = session.createSQLQuery(sql);
        List<Object[]> result = query.list();
        List<FinPoolResult> finPoolResults = new ArrayList<>();
        for (Object[] row : result) {
            FinPoolResult finPoolResult = new FinPoolResult();
            Ride ride = new Ride();
            Owner owner = new Owner();
            Car car = new Car();
            ride.setRideId(Integer.parseInt(row[0].toString()));
            ride.setRideDate(Timestamp.valueOf(row[5].toString()));
            ride.setOwnerId(Integer.parseInt(row[1].toString()));
            ride.setNoOfSeats(Integer.parseInt(row[3].toString()));
            ride.setCarId(Integer.parseInt(row[2].toString()));


            owner.setOwnerId(Integer.parseInt(row[10].toString()));
            owner.setOwnerName(row[11].toString());
            owner.setOwnerEmail(row[12].toString());
            owner.setOwnerMob(row[13].toString());

            car.setCarName(row[7].toString());
            car.setCarColor(row[8].toString());
            car.setCarNumber(row[9].toString());
            car.setCarId(Integer.parseInt(row[6].toString()));
            finPoolResult.setRide(ride);
            finPoolResult.setCar(car);
            finPoolResult.setOwner(owner);
            List<String> cities = new ArrayList<>();
            String city = row[16].toString();
            for(i = 0; i< city.length(); i+=2) {
                int cId = Character.getNumericValue(city.charAt(i));
                cities.add(cityMap.get(cId));
            }
            finPoolResult.setCitiesOfRide(cities);
            finPoolResults.add(finPoolResult);
        }

        Collections.sort(finPoolResults, new Comparator<FinPoolResult>() {
            public int compare(FinPoolResult o1, FinPoolResult o2) {
                return o1.getRide().getRideDate().compareTo(o2.getRide().getRideDate());
            }
        });
        transaction.commit();
        session.close();
        return finPoolResults;
    }

    @Override
    public RidePooler approvePoolRequest(ReqDecPayload reqDecPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String poolerMob = reqDecPayload.getPoolerMob();
        int rideId = reqDecPayload.getRideId();
        Ride ride1 = session.get(Ride.class, rideId);
        int ownerId = ride1.getOwnerId();
        int approved = reqDecPayload.getApproved();
        String usql = "select * from pooler where pooler_mob = "+poolerMob;
        SQLQuery query = session.createSQLQuery(usql);
        query.addEntity(Pooler.class);
        Pooler currPooler = (Pooler) query.uniqueResult();
        int poolerId = currPooler.getPoolerId();
        List<PoolRequest> poolRequests = session.createQuery("from PoolRequest PR where PR.isSeen = false", PoolRequest.class).list();
        for(PoolRequest poolRequest : poolRequests) {
            if(!poolRequest.getSeen()) {
                RidePooler ridePooler = session.get(RidePooler.class, poolRequest.getRidePoolerId());
                Ride ride = session.get(Ride.class, ridePooler.getRideId());
                Pooler pooler = session.get(Pooler.class, ridePooler.getPoolerId());
                Owner owner = session.get(Owner.class, ride.getOwnerId());
                if(pooler.getPoolerId() == poolerId && owner.getOwnerId() == ownerId && ride.getRideId() == rideId){
                    poolRequest.setSeen(true);
                    if(approved == 1) {
                        ridePooler.setActive(true);
                        ridePooler.setSeatNo(ride.getNoOfSeats()+1);
                        ride.setNoOfSeats(ride.getNoOfSeats()-1);
                        poolRequest.setApproved(true);
                    }else {
                        ridePooler.setActive(false);
                        ridePooler.setSeatNo(-1);
                        poolRequest.setApproved(false);
                    }
                    session.saveOrUpdate(ridePooler);
                    session.saveOrUpdate(ride);
                    session.saveOrUpdate(poolRequest);
                    PoolerNotification poolerNotification = new PoolerNotification();
                    poolerNotification.setPoolerId(poolerId);
                    String res = "rejected";
                    if(approved == 1) res = "approved";
                    String msg = "Your Request for ride no " + rideId+ " from "+
                            cityServices.getCityById(ridePooler.getStartCityId()).getCityName()+ " to "+
                            cityServices.getCityById(ridePooler.getEndCityId()).getCityName()+ " has been "+res+" by "+
                            owner.getOwnerName()+ " having mobile no " + owner.getOwnerMob();
                    poolerNotification.setMessage(msg);
                    poolerNotification.setRead(false);
                    session.save(poolerNotification);
                    transaction.commit();
                    session.close();
                    return ridePooler;
                }
            }
        }
        return new RidePooler();
    }

    @Override
    public List<RidePooler> allPoolRequests(int ownerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<PoolRequest> poolRequests = session.createQuery("from PoolRequest PR where PR.isSeen = false", PoolRequest.class).list();
        List<RidePooler> requiredList = new ArrayList<>();
        for(PoolRequest poolRequest : poolRequests) {
            if(!poolRequest.getSeen()){
                RidePooler ridePooler = session.get(RidePooler.class, poolRequest.getRidePoolerId());
                Ride ride = session.get(Ride.class, ridePooler.getRideId());
                Owner owner = session.get(Owner.class, ride.getOwnerId());
                if(owner.getOwnerId() == ownerId ){
                    requiredList.add(ridePooler);
                }
            }
        }
        transaction.commit();
        session.close();
        return requiredList;
    }

    @Override
    public List<Ride> getAllRidesoFOwner(int ownerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String  sql = "select * from ride where owner_id = " + ownerId;
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Ride.class);
        List result = query.list();
        Collections.sort(result, new Comparator<Ride>() {
            public int compare(Ride o1, Ride o2) {
                return o1.getRideDate().compareTo(o2.getRideDate());
            }
        });
        transaction.commit();
        session.close();
        return result;
    }

    @Override
    public RidePooler declineRidePooler(int ridePoolId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RidePooler ridePooler = session.get(RidePooler.class, ridePoolId);
        Ride ride = session.get(Ride.class, ridePooler.getRideId());
        Owner owner = session.get(Owner.class, ride.getOwnerId());
        ridePooler.setActive(false);
        session.saveOrUpdate(ridePooler);
        if(ridePooler.getActive())
            ride.setNoOfSeats(ride.getNoOfSeats()+1);
        session.saveOrUpdate(ride);
        PoolerNotification poolerNotification = new PoolerNotification();
        String  msg = "Hey "+owner.getOwnerName()+" has declined the ride with ride no "+ride.getRideId()+" with you from " +
                cityServices.getCityById(ridePooler.getStartCityId()).getCityName() + " to "+ cityServices.getCityById(ridePooler.getEndCityId()).getCityName() + " on " + ride.getRideDate();
        poolerNotification.setMessage(msg);
        poolerNotification.setRead(false);
        poolerNotification.setPoolerId(ridePooler.getPoolerId());
        session.save(poolerNotification);
        transaction.commit();
        session.close();
        return ridePooler;
    }

    @Override
    public List<OwnerNotification> allActiveNotificationsofOwner(int ownerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "select * from ownernotification where owner_id = "+ownerId + " and is_read = false";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(OwnerNotification.class);
        List result = query.list();
        transaction.commit();
        session.close();
        return result;
    }

    @Override
    public OwnerNotification readNotification(int notifId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        OwnerNotification ownerNotification = session.get(OwnerNotification.class, notifId);
        ownerNotification.setRead(true);
        session.saveOrUpdate(ownerNotification);
        transaction.commit();
        session.close();
        return ownerNotification;
    }

}
