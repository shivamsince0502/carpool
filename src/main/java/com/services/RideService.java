package com.services;

import com.model.City;
import com.model.Ride;
import com.model.RideCities;
import com.model.RidePooler;
import com.payload.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RideService {
    List<Ride> getAllActiveRides();
    List<Ride> getAllRides();

    List<Ride> findPoolCars(PoolerJourneyPayload poolerJourneyPayload);

    Ride createRide(NewRidePayload newRidePayload);

    List<String> allCitiesOfRide(int id);

    RidePooler bookingRequest(BookRequestPayload bookRequestPayload);

    Ride createRide(OwnerRidePayload ownerRidePayload);
    Ride deleteRideById(int id);

    Ride getRideByRideId(int id);

    Ride finishRideById(int id);

    Ride updateRide(UpdateRidePayload updateRidePayload);

    RidePooler findRideForPooler(int rideId, int poolerId);
    RidePooler unBookRide(int rideId, int poolerId);
}
