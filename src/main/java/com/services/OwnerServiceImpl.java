package com.services;

import com.model.*;
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
        List<Owner> ownerList = getAllOwners();
        for(Owner owner1 : ownerList) {
            if(owner1.getUserName().equals(owner.getUserName()) || owner1.getOwnerEmail().equals(owner.getOwnerEmail()) || owner1.getOwnerMob().equals(owner.getOwnerMob()))
                return new Owner();
        }
        session.save(owner);
        transaction.commit();
        session.close();
        List<Owner> ownerList1 = getAllOwners();
        int len = ownerList1.size();
        Owner owner1 = ownerList1.get(len-1);
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
        List<Owner> ownerList = session.createQuery("from Owner", Owner.class).list();
        for(Owner owner : ownerList) {
            String un = owner.getUserName();
            String pw = owner.getPassword();
            if(un.equals(loginPayload.getUsername()) && pw.equals(loginPayload.getPassword())){
                return owner;
            }
        }
        return null;
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
    public List<Ride> getAllPrevRidesByOwnerId(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Ride> rideList = rideService.getAllRides();

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        date = new java.sql.Date(cal.getTime().getTime());

        List<DeleteRide> deleteRideList = session.createQuery("from DeleteRide", DeleteRide.class).list();
        ArrayList<Integer> delRides = new ArrayList<>();
        for(DeleteRide deleteRide : deleteRideList){
           delRides.add(deleteRide.getRideId());
        }
        List<Ride> requiredRides = new ArrayList<>();
        for(Ride ride : rideList) {
            if(ride.getOwnerId() == id && !delRides.contains(ride.getRideId()) && (!ride.getActive() || ride.getRideDate().compareTo(date) < 0)) {
                requiredRides.add(ride);
            }
        }
        Collections.sort(requiredRides, new Comparator<Ride>() {
            public int compare(Ride o1, Ride o2) {
                return o1.getRideDate().compareTo(o2.getRideDate());
            }
        });
        transaction.commit();
        session.close();
        return requiredRides;
    }

    @Override
    public List<Ride> getAllUpRides(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Ride> rides= rideService.getAllRides();
        List<Ride> requiredList = new ArrayList<>();
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        date = new java.sql.Date(cal.getTime().getTime());
        for(Ride ride : rides) {
            if(ride.getActive() && ride.getRideDate().compareTo(date) >= 0 && ride.getOwnerId() == id) {
                requiredList.add(ride);
            }
        }
        Collections.sort(requiredList, new Comparator<Ride>() {
            public int compare(Ride o1, Ride o2) {
                return o1.getRideDate().compareTo(o2.getRideDate());
            }
        });
        transaction.commit();
        session.close();
        return requiredList;
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
        List<PoolRequest> poolRequests = session.createQuery("from PoolRequest", PoolRequest.class).list();
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
                        ridePooler.setSeatNo(ride.getNoOfSeats()+1);
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
        List<PoolRequest> poolRequests = session.createQuery("from PoolRequest", PoolRequest.class).list();
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
        String  sql = "select * from ride where owner_id = " +ownerId;
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
