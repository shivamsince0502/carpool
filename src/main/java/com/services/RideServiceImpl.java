package com.services;

import com.model.*;
import com.payload.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Timestamp;


@Service
public class RideServiceImpl implements RideService{

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    RideCitiesService rideCitiesService;

    @Autowired
    CityServices cityServices;

    @Autowired
    OwnerService ownerService;

    @Autowired
    CarServices carServices;

    @Autowired
    PoolerService poolerService;


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



        long millis=System.currentTimeMillis();
        Timestamp date=new Timestamp(millis);
        Timestamp edate=new Timestamp(millis);
        if(!(poolerJourneyPayload.getDateOfJourney() == 0))
            date = new Timestamp(poolerJourneyPayload.getDateOfJourney());
        if(!(poolerJourneyPayload.getEndDateOfJourney() == 0))
            edate = new Timestamp(poolerJourneyPayload.getEndDateOfJourney());

        HashMap<Integer, Integer> distMap = new HashMap<>();

        for(Ride ride : activeRides) {
            int rideId = ride.getRideId();
            if(rideCities.containsKey(rideId)){
                List<String> cities = rideCities.get(rideId);
                if(ride.getRideDate().compareTo(date) >= 0 && ride.getRideDate().compareTo(edate) <= 0) {
                    if (cities.contains(start) && cities.contains(end)) {
                        if (cities.indexOf(start) < cities.indexOf(end)) {
                            distMap.put(rideId ,cities.indexOf(start) - cities.indexOf(end));
                            requiredRides.add(ride);
                        }
                    }
                }
            }
        }
        Collections.sort(requiredRides, new Comparator<Ride>() {
            public int compare(Ride o1, Ride o2) {
                return (distMap.get(o2.getRideId()) - distMap.get(o1.getRideId()));
            }
        });
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
    public RidePooler bookingRequest(BookRequestPayload bookRequestPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        java.sql.Timestamp reqDate = java.sql.Timestamp.valueOf(bookRequestPayload.getDateOfJourney().replace("T"," "));
        int rideId = bookRequestPayload.getRideId();
        int poolerId = bookRequestPayload.getPoolerId();
        List<RidePooler> ridePoolerList1 = poolerService.allUpRideByPoolerId(poolerId);
        for(RidePooler ridePooler : ridePoolerList1) {
            Ride ride = session.get(Ride.class, ridePooler.getRideId());
            if(ridePooler.getRideId() == rideId && ridePooler.getActive() && ride.getRideDate().compareTo(reqDate) == 0) {
                return new RidePooler();
            }
        }
        Ride ride = session.get(Ride.class, rideId);
        int seatNo = -1;
        session.saveOrUpdate(ride);

        List<City> cities = cityServices.getAllCities();
        int startId = 0, endId = 0;
        for(City city : cities) {
            if(city.getCityName().equals(bookRequestPayload.getStart()))
                startId = city.getCityId();

            if(city.getCityName().equals(bookRequestPayload.getEnd()))
                endId = city.getCityId();
            if(startId != 0 && endId != 0) break;;
        }
        String sql = "insert into ride_pooler(ride_id, pooler_id, start_id, end_id, seat_no, is_active) values("+rideId+", "+poolerId+", "+startId+","+endId+","+seatNo+", false)";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(RidePooler.class);
        query.executeUpdate();
        List<RidePooler> ridePoolerList = session.createQuery("from RidePooler", RidePooler.class).list();
        RidePooler ridePooler1 = ridePoolerList.get(ridePoolerList.size()-1);
        PoolRequest poolRequest = new PoolRequest();
        poolRequest.setRidePoolerId(ridePooler1.getRidePoolerId());
        poolRequest.setApproved(false);
        poolRequest.setSeen(false);
        session.save(poolRequest);
        transaction.commit();
        session.close();
        return ridePooler1;
    }

    @Override
    public Ride createRide(OwnerRidePayload ownerRidePayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        long millis=System.currentTimeMillis();
        Timestamp date=new Timestamp(millis);
        if(!(ownerRidePayload.getDateOfJourney() == 0))
            date = new Timestamp(ownerRidePayload.getDateOfJourney());

        List<Ride> allRidesOfOwner = ownerService.getAllUpRides(ownerRidePayload.getOwnerId());
        for(Ride ride : allRidesOfOwner) {
            Car car = session.get(Car.class, ride.getCarId());
            if(car.getCarName().equals(ownerRidePayload.getCarName()) && ride.getRideDate().compareTo(date) == 0) {
                return  new Ride();
            }
        }
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
        List<String> citiesList = ownerRidePayload.getCitiesList();
        for(String cityName : citiesList) {
            int cityId = citiesMap.get(cityName);
            String sql = "insert into ride_cities(ride_id, city_id) values("+rideId+","+cityId+")";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(RideCities.class);
            query.executeUpdate();
        }
        transaction.commit();
        session.close();
        return ride1;
    }

    @Override
    public Ride deleteRideById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DeleteRide deleteRide = new DeleteRide();
        deleteRide.setRideId(id);
        session.save(deleteRide);
        Ride ride = session.get(Ride.class, id);
        transaction.commit();
        session.close();
        return ride;
    }

    @Override
    public Ride getRideByRideId(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Ride ride = session.get(Ride.class, id);
        transaction.commit();
        session.close();
        return ride;
    }

    @Override
    public Ride finishRideById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Ride ride = session.get(Ride.class, id);
        ride.setNoOfSeats(ride.getNoOfSeats()+1);
        ride.setActive(false);
        session.save(ride);
        transaction.commit();
        session.close();
        return ride;
    }

    @Override
    public Ride updateRide(UpdateRidePayload updateRidePayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Ride ride = session.get(Ride.class, updateRidePayload.getRideId());
        Car car = session.get(Car.class, ride.getCarId());
        if(!car.getCarName().equals(updateRidePayload.getCarName())){
            List<OwnerCar> ownerCarList = session.createQuery("from OwnerCar", OwnerCar.class).list();
            for(OwnerCar ownerCar : ownerCarList) {
                Car currCar = session.get(Car.class, ownerCar.getCarId());
                if(ownerCar.getOwnerId() == ride.getOwnerId() && currCar.getCarName().equals(updateRidePayload.getCarName())){
                    ride.setCarId(currCar.getCarId());
                    break;
                }
            }
        }
        long upDate = updateRidePayload.getDateOfJourney();
        if(!(upDate == 0)){
            Timestamp date = new Timestamp(updateRidePayload.getDateOfJourney());
            ride.setRideDate(date);
        }

        if(ride.getNoOfSeats() != updateRidePayload.getNoOfSeats()){
            ride.setNoOfSeats(updateRidePayload.getNoOfSeats());
        }
        session.saveOrUpdate(ride);
        transaction.commit();
        session.close();
        return ride;
    }

    @Override
    public RidePooler findRideForPooler(int rideId, int poolerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<RidePooler> ridePoolerList = session.createQuery("from RidePooler", RidePooler.class).list();
        Ride ride = session.get(Ride.class, rideId);
        ride.setNoOfSeats(ride.getNoOfSeats()+1);
        session.saveOrUpdate(ride);
        for(RidePooler ridePooler : ridePoolerList) {
            if(ridePooler.getPoolerId() == poolerId && ridePooler.getRideId() == rideId && ridePooler.getActive()){
                ridePooler.setActive(false);
                session.saveOrUpdate(ridePooler);
                transaction.commit();
                session.close();
                return ridePooler;
            }
        }
        return null;
    }

    @Override
    public RidePooler unBookRide(int rideId, int poolerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Pooler pooler = session.get(Pooler.class, poolerId);
        List<RidePooler> ridePoolerList = session.createQuery("from RidePooler", RidePooler.class).list();
        Ride ride = session.get(Ride.class, rideId);
        ride.setNoOfSeats(ride.getNoOfSeats()+1);
        session.saveOrUpdate(ride);
        for(RidePooler ridePooler : ridePoolerList) {
            if(ridePooler.getPoolerId() == poolerId && ridePooler.getRideId() == rideId && ridePooler.getActive()){
                ridePooler.setActive(false);
                session.saveOrUpdate(ridePooler);
                OwnerNotification ownerNotification = new OwnerNotification();

                String msg = "Hey Owner "+ pooler.getPoolerName() +" has unbooked the ride ride no "+ rideId + " from "+
                        cityServices.getCityById(ridePooler.getStartCityId()).getCityName() + " to " +
                        cityServices.getCityById(ridePooler.getEndCityId()).getCityName() + " on " + ride.getRideDate();
                ownerNotification.setMessage(msg);
                ownerNotification.setOwnerId(ride.getOwnerId());
                ownerNotification.setRead(false);
                session.save(ownerNotification);

                transaction.commit();
                session.close();
                return ridePooler;
            }
        }
        return null;
    }

    @Override
    public List<Timestamp> getCurrDate() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        long millis=System.currentTimeMillis();
        java.sql.Timestamp dateNow=new java.sql.Timestamp(millis);
        Ride ride = session.get(Ride.class, 2);
        List<Timestamp> dates = new ArrayList<>();
        dates.add(ride.getRideDate());
        dates.add(dateNow);
        return dates;
    }

    @Override
    public List<RidePooler> allPoolersOfRide(int rideId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "select * from ride_pooler where ride_id = " +rideId+ " and seat_no != -1";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(RidePooler.class);
        List result = query.list();
        transaction.commit();
        session.close();
        return result;
    }


}
