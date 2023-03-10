package com.services;

import com.model.*;
import com.payload.BookRequestPayload;
import com.payload.NewRidePayload;
import com.payload.OwnerRidePayload;
import com.payload.PoolerJourneyPayload;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RideServiceImpl implements RideService{

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    RideCitiesService rideCitiesService;

    @Autowired
    CityServices cityServices;

    @Autowired
    CarServices carServices;
    private static final Logger logger = LoggerFactory.getLogger(RideServiceImpl.class);
    @Override
    public List<Ride> getAllActiveRides() {
        List<Ride> allRides = getAllRides();
        List<Ride> allActiveRides = new ArrayList<>();
        for(Ride ride : allRides) {
            if(ride.getActive()) allActiveRides.add(ride);
        }
        return allActiveRides;
    }

    @Override
    public List<Ride> getAllRides() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Ride> allRides = session.createQuery("from Ride", Ride.class).list();
        transaction.commit();
        session.close();
        return allRides;
    }

    @Override
    public List<Ride> findPoolCars(PoolerJourneyPayload poolerJourneyPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String start = poolerJourneyPayload.getStart();
        String end = poolerJourneyPayload.getEnd();
        List<Ride> activeRides = getAllActiveRides();
        List<Ride> requiredRides = new ArrayList<>();
        List<RideCities> allRideCities = rideCitiesService.getAllRideCities();
        List<City> allCities = cityServices.getAllCities();
        HashMap<Integer, String> cityMap = new HashMap<>();
        for(City city : allCities) {
            cityMap.put(city.getCityId(), city.getCityName());
        }
        Map<Integer, List<String>> rideCities = new HashMap<>();
        for(RideCities rCities : allRideCities) {
            int rideNo = rCities.getRideId();
            int cityNo = rCities.getCityId();
            if(rideCities.containsKey(rideNo)){
                List<String> cityList = rideCities.get(rideNo);
                cityList.add(cityMap.get(cityNo));
                rideCities.put(rideNo, cityList);
            }else{
                List<String> cityList = new ArrayList<>();
                cityList.add(cityMap.get(cityNo));
                rideCities.put(rideNo, cityList);
            }
        }
        for(Ride ride : activeRides) {
            int rideId = ride.getRideId();
            if(rideCities.containsKey(rideId)){
                List<String> cities = rideCities.get(rideId);
                if(cities.contains(start) && cities.contains(end)) {
                    if(cities.indexOf(start) < cities.indexOf(end))
                        requiredRides.add(ride);
                }
            }
        }
        transaction.commit();
        session.close();
        return requiredRides;
    }



    @Override
    public Ride createRide(NewRidePayload newRidePayload) {
        return null;
    }

    @Override
    public List<String> allCitiesOfRide(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Ride> activeRides = getAllActiveRides();
        List<RideCities> allRideCities = rideCitiesService.getAllRideCities();
        List<String> citiesList = new ArrayList<>();
        for(RideCities rideCities : allRideCities) {
            if(rideCities.getRideId() == id) {
                City city = session.get(City.class, rideCities.getCityId());
                citiesList.add(city.getCityName());
            }
        }
        return citiesList;
    }

    @Override
    public Ride bookingRequest(BookRequestPayload bookRequestPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int rideId = bookRequestPayload.getRideId();
        int poolerId = bookRequestPayload.getPoolerId();
        Ride ride = session.get(Ride.class, rideId);
        ride.setNoOfSeats(ride.getNoOfSeats()-1);
        session.saveOrUpdate(ride);
        String sql = "insert into ride_pooler(ride_id, pooler_id, seat_no, is_active) values("+rideId+","+poolerId+", 3, true)";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(RidePooler.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
        return ride;
    }

    @Override
    public Ride createRide(OwnerRidePayload ownerRidePayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        long millis=System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Ride ride = new Ride();
        ride.setNoOfSeats(ownerRidePayload.getNoOfSeats());
        ride.setRideDate(date);
        ride.setActive(true);
        List<OwnerCar> ownerCarList = session.createQuery("from OwnerCar", OwnerCar.class).list();
        for(OwnerCar ownerCar : ownerCarList) {
            Car car = session.get(Car.class, ownerCar.getCarId());
            if(car.getCarName().equals(ownerRidePayload.getCarName())){
                ride.setCarId(car.getCarId());
                break;
            }
        }
        ride.setOwnerId(ownerRidePayload.getOwnerId());
        session.save(ride);
        List<City> cities = session.createQuery("from City", City.class).list();
        HashMap<String, Integer> citiesMap = new HashMap<>();
        for(City city : cities) {
            citiesMap.put(city.getCityName(), city.getCityId());
        }
        List<Ride> rides = session.createQuery("from Ride", Ride.class).list();
        Integer len = rides.size();
        Ride ride1 = rides.get(len-1);
        int rideId = ride1.getRideId();
        int sp = 0;
        if(ownerRidePayload.getStartPoint() != "")
            sp = citiesMap.get(ownerRidePayload.getStartPoint());
        int rp1 = 0;
        if(ownerRidePayload.getInter1Point() != "")
                rp1 = citiesMap.get(ownerRidePayload.getInter1Point());
        int rp2 = 0;
        if(ownerRidePayload.getInter2Point() != "")
            rp2 = citiesMap.get(ownerRidePayload.getInter2Point());
        int rp3 = 0;
        if(ownerRidePayload.getInter3Point() != "")
            rp3 = citiesMap.get(ownerRidePayload.getInter3Point());

        int rp4 = 0;
        if(ownerRidePayload.getInter4Point() != "")
        rp4 = citiesMap.get(ownerRidePayload.getInter4Point());

        int ep = 0;
        if(ownerRidePayload.getEndPoint() != "")
            ep = citiesMap.get(ownerRidePayload.getEndPoint());

        ArrayList<Integer> citiesId = new ArrayList<>();
        citiesId.add(sp);
        citiesId.add(rp1);
        citiesId.add(rp2);
        citiesId.add(rp3);
        citiesId.add(rp4);
        citiesId.add(ep);
        for(int i = 0; i < citiesId.size(); i++) {

            int cId = citiesId.get(i);
            if(cId > 0) {
                String sql = "insert into ride_cities (ride_id, city_id) values (" + rideId + ", " + cId + ")";
                SQLQuery query = session.createSQLQuery(sql);
                query.executeUpdate();
            }
        }
        transaction.commit();
        session.close();
        return ride1;
    }
}
