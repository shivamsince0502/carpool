package com.services;

import com.model.*;
import com.payload.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.hql.internal.ast.SqlASTFactory;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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




    Boolean isMatch(String route, int st, int ed, int rideId,  List<Integer> inList, HashMap<Integer, Integer> distMap) {
        for(int i = 0; i< route.length(); i+=2) {
            int cId = Character.getNumericValue(route.charAt(i));
            inList.add(cId);
        }
        distMap.put(rideId, inList.indexOf(st) - inList.indexOf(ed));
        return inList.indexOf(st) < inList.indexOf(ed);
    }

    @Override
    public List<Ride> getAllActiveRides() {
        return null;
    }

    @Override
    public List<FinPoolResult> findPoolCars(PoolerJourneyPayload poolerJourneyPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String start = poolerJourneyPayload.getStart();
        String end = poolerJourneyPayload.getEnd();
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
        int st = cityMapR.get(start);
        int ed = cityMapR.get(end);
        List<Ride> requiredRides = new ArrayList<>();
        long millis=System.currentTimeMillis();
        Timestamp date=new Timestamp(millis);
        Timestamp edate=new Timestamp(millis);
        if(!(poolerJourneyPayload.getDateOfJourney() == 0))
            date = new Timestamp(poolerJourneyPayload.getDateOfJourney());
        if(!(poolerJourneyPayload.getEndDateOfJourney() == 0))
            edate = new Timestamp(poolerJourneyPayload.getEndDateOfJourney());
        List<FinPoolResult> reqList = new ArrayList<>();
        String sql = "SELECT r.ride_id, r.ride_datetime, r.owner_id, r.no_of_seats, o.owner_name, o.owner_email , o.owner_mob, car.car_name, car.car_color , car.car_number , car.car_id, GROUP_CONCAT(rc.city_id) AS cities " +
                "FROM ride r " +
                "JOIN ride_cities rc ON r.ride_id = rc.ride_id " +
                "join owner o on o.owner_id = r.owner_id " +
                "JOIN car on car.car_id = r.car_id  "+
                "WHERE r.is_active = 1 " +
                "AND r.ride_datetime BETWEEN '"+date+"' AND '"+edate+"' " +
                "GROUP BY r.ride_id ";
        SQLQuery query = session.createSQLQuery(sql);
        List<Object[]> result = query.list();
        for (Object[] row : result) {
            FinPoolResult finPoolResult = new FinPoolResult();
            Ride ride = new Ride();
            Owner owner = new Owner();
            Car car = new Car();
            ride.setRideId(Integer.parseInt(row[0].toString()));
            ride.setRideDate(Timestamp.valueOf(row[1].toString()));
            ride.setOwnerId(Integer.parseInt(row[2].toString()));
            ride.setNoOfSeats(Integer.parseInt(row[3].toString()));

            owner.setOwnerId(Integer.parseInt(row[2].toString()));
            owner.setOwnerName(row[4].toString());
            owner.setOwnerEmail(row[5].toString());
            owner.setOwnerMob(row[6].toString());

            car.setCarName(row[7].toString());
            car.setCarColor(row[8].toString());
            car.setCarNumber(row[9].toString());
            car.setCarId(Integer.parseInt(row[10].toString()));
            finPoolResult.setRide(ride);
            finPoolResult.setCar(car);
            finPoolResult.setOwner(owner);
            List<Integer> inList = new ArrayList<>();
            if(isMatch(row[11].toString(), st, ed, ride.getRideId(), inList, distMap)){
                List<String> cityNames = new ArrayList<>();
                for(int cid : inList) {
                    cityNames.add(cityMap.get(cid));
                }
                finPoolResult.setCitiesOfRide(cityNames);
                reqList.add(finPoolResult);
            }
        }

        Collections.sort(reqList, new Comparator<FinPoolResult>() {
            public int compare(FinPoolResult o1, FinPoolResult o2) {
                return (distMap.get(o2.getRide().getRideId()) - distMap.get(o1.getRide().getRideId()));
            }
        });
        Collections.sort(reqList, new Comparator<FinPoolResult>() {
            public int compare(FinPoolResult o1, FinPoolResult o2) {
                return o1.getRide().getRideDate().compareTo(o2.getRide().getRideDate());
            }
        });

        transaction.commit();
        session.close();
        return reqList;
    }

    @Override
    public List<String> allCitiesOfRide(int id) {
        return null;
    }


    @Override
    public RidePooler bookingRequest(BookRequestPayload bookRequestPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Timestamp reqDate = new Timestamp(bookRequestPayload.getDateOfJourney());
        int rideId = bookRequestPayload.getRideId();
        int poolerId = bookRequestPayload.getPoolerId();
        String checksql = "select * from ride_pooler where ride_date =" +
                " '"+reqDate+"' and ride_id = "+rideId+" and is_active = true";
        SQLQuery query = session.createSQLQuery(checksql);
        query.addEntity(RidePooler.class);
        RidePooler res = (RidePooler)query.uniqueResult();
        if(res != null) return new RidePooler();

        int seatNo = -1;
        List<String> cities = cityServices.getAllCities();
        int startId = cities.indexOf(bookRequestPayload.getStart())+1;
        int endId = cities.indexOf(bookRequestPayload.getEnd())+1;

        String sql = "insert into ride_pooler( ride_id, pooler_id, start_id, end_id, seat_no," +
                " is_active, ride_date ) values( "+rideId+", "+poolerId+"," +
                " "+startId+", "+endId+", "+seatNo+", false, '"+ reqDate+ "' )";
        SQLQuery query1 = session.createSQLQuery(sql);
        query1.addEntity(RidePooler.class);
        query1.executeUpdate();

        String nsql = "select * from ride_pooler where ride_id = "+ rideId + " " +
                "and pooler_id = "+poolerId+" and start_id = "+startId+" and end_id = "+endId+
                " and is_active = false and ride_date = '"+reqDate+"'";
        SQLQuery query2 = session.createSQLQuery(nsql);
        query2.addEntity(RidePooler.class);
        RidePooler ridePooler = (RidePooler)query2.uniqueResult();
        PoolRequest poolRequest = new PoolRequest();
        poolRequest.setRidePoolerId(ridePooler.getRidePoolerId());
        poolRequest.setApproved(false);
        poolRequest.setSeen(false);
        session.save(poolRequest);
        transaction.commit();
        session.close();
        return ridePooler;
    }

    @Override
    public FinPoolResult createRide(OwnerRidePayload ownerRidePayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        long millis=System.currentTimeMillis();
        Timestamp date=new Timestamp(millis);
        if(!(ownerRidePayload.getDateOfJourney() == 0))
            date = new Timestamp(ownerRidePayload.getDateOfJourney());


        String sql = "SELECT r.* " +
                "FROM ride r " +
                "INNER JOIN car c ON r.car_id = c.car_id " +
                " WHERE r.owner_id = "+ownerRidePayload.getOwnerId()+
                " AND c.car_number = '"+ownerRidePayload.getCarNumber()+"'"+
                " AND r.is_active = true "+
                " AND DATE(r.ride_datetime) = DATE('"+date+"') ";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Ride.class);
        List<Ride> rides = query.list();
        if(rides.size() != 0) return new FinPoolResult();
        List<City> cities = session.createQuery("from City", City.class).list();
        HashMap<String, Integer> citiesMap = new HashMap<>();
        for(City city : cities) {
            citiesMap.put(city.getCityName(), city.getCityId());
        }

        List<String> citiesList = ownerRidePayload.getCitiesList();

        String nsql = "INSERT INTO ride (car_id, owner_id, ride_datetime, no_of_seats, is_active) " +
                "VALUES ((select car_id from car where car_number = '"+ownerRidePayload.getCarNumber()+"'), "+
                ownerRidePayload.getOwnerId()+", '"+date+"' , "+ownerRidePayload.getNoOfSeats()+", true)";
        SQLQuery nquery = session.createSQLQuery(nsql);
        nquery.executeUpdate();
        Long lastInsertId = null;
        SQLQuery selectQuery = session.createSQLQuery("SELECT LAST_INSERT_ID()");
        List<Object> results = selectQuery.getResultList();

        if (results != null && !results.isEmpty()) {
            lastInsertId = ((BigInteger) results.get(0)).longValue();
        }

        String psql = "INSERT INTO ride_cities (ride_id, city_id) VALUES ";
        for(int i = 0; i < citiesList.size(); i++) {
            String cityName = citiesList.get(i);
            int cityId = citiesMap.get(cityName);
            if(i == citiesList.size()-1)
                psql += "(LAST_INSERT_ID(), "+cityId+");";
            else  psql += "(LAST_INSERT_ID(), "+cityId+"),";

        }
        SQLQuery pquery = session.createSQLQuery(psql);
        pquery.executeUpdate();

        String tsql = "SELECT r.ride_id, r.ride_datetime, r.owner_id, r.no_of_seats, o.owner_name, o.owner_email , o.owner_mob, c.car_name, c.car_color , c.car_number , c.car_id, GROUP_CONCAT(rc.city_id) AS cities " +
                "FROM ride r " +
                "JOIN ride_cities rc ON r.ride_id = rc.ride_id " +
                "join owner o on o.owner_id = r.owner_id " +
                "JOIN car c on c.car_id = r.car_id  "+
                "WHERE r.ride_id = "+lastInsertId+" "+
                "GROUP BY r.ride_id ";

        List<String> allcities = cityServices.getAllCities();
        HashMap<Integer, String> cityMap = new HashMap<>();
        HashMap<String, Integer> cityMapR = new HashMap<>();
        int i = 1;
        for(String city : allcities) {
            cityMap.put(i, city);
            cityMapR.put(city, i);
            i++;
        }

        SQLQuery tquery = session.createSQLQuery(tsql);
        List<Object[]> result = tquery.list();
        List<FinPoolResult> reqlist = new ArrayList<>();
        for (Object[] row : result) {
            FinPoolResult finPoolResult = new FinPoolResult();
            Ride ride = new Ride();
            Owner owner = new Owner();
            Car car = new Car();
            ride.setRideId(Integer.parseInt(row[0].toString()));
            ride.setRideDate(Timestamp.valueOf(row[1].toString()));
            ride.setOwnerId(Integer.parseInt(row[2].toString()));
            ride.setNoOfSeats(Integer.parseInt(row[3].toString()));

            owner.setOwnerId(Integer.parseInt(row[2].toString()));
            owner.setOwnerName(row[4].toString());
            owner.setOwnerEmail(row[5].toString());
            owner.setOwnerMob(row[6].toString());

            car.setCarName(row[7].toString());
            car.setCarColor(row[8].toString());
            car.setCarNumber(row[9].toString());
            car.setCarId(Integer.parseInt(row[10].toString()));
            finPoolResult.setRide(ride);
            finPoolResult.setCar(car);
            finPoolResult.setOwner(owner);
            List<Integer> inList = new ArrayList<>();
            String route = row[11].toString();
            for(i = 0; i< route.length(); i+=2) {
                int cId = Character.getNumericValue(route.charAt(i));
                inList.add(cId);
            }
                List<String> cityNames = new ArrayList<>();
                for(int cid : inList) {
                    cityNames.add(cityMap.get(cid));
                }
                finPoolResult.setCitiesOfRide(cityNames);
                reqlist.add(finPoolResult);
        }
        transaction.commit();
        session.close();
        return reqlist.get(0);
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

        if(!car.getCarNumber().equals(updateRidePayload.getCarNumber())){
            String sql = "update ride set car_id = (select car_id from car where car_number = '"+updateRidePayload.getCarNumber()+"') where ride_id = "+updateRidePayload.getRideId();
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Ride.class);
            query.executeUpdate();

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
    public RidePooler finishRideForPooler(int rideId, int poolerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<RidePooler> ridePoolerList = session.createQuery("from RidePooler RP where RP.rideId = "+rideId+" and RP.poolerId = "+poolerId+" and RP.isActive = true", RidePooler.class).list();
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
        List<RidePooler> ridePoolerList = session.createQuery("from RidePooler RP where RP.poolerId = "+poolerId+" and RP.rideId = "+rideId+" and RP.isActive = true", RidePooler.class).list();
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
