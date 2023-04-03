package com.services;

import com.model.City;
import com.model.Ride;
import com.model.RideCities;
import com.model.RidePooler;
import com.payload.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.List;

public interface RideService {

    List<Ride> getAllActiveRides();

    List<FinPoolResult> findPoolCars(PoolerJourneyPayload poolerJourneyPayload);

    List<String> allCitiesOfRide(int id);

    RidePooler bookingRequest(BookRequestPayload bookRequestPayload);

    FinPoolResult createRide(OwnerRidePayload ownerRidePayload);
    Ride deleteRideById(int id);

    Ride getRideByRideId(int id);

    Ride finishRideById(int id);

    Ride updateRide(UpdateRidePayload updateRidePayload);

    RidePooler finishRideForPooler(int rideId, int poolerId);
    RidePooler unBookRide(int rideId, int poolerId);

    List<Timestamp> getCurrDate();

    List<RidePooler> allPoolersOfRide(int rideId);

}
