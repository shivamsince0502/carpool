package com.services;

import com.model.City;
import com.model.Ride;
import com.model.RideCities;
import com.payload.BookRequestPayload;
import com.payload.NewRidePayload;
import com.payload.OwnerRidePayload;
import com.payload.PoolerJourneyPayload;

import java.util.List;

public interface RideService {
    List<Ride> getAllActiveRides();
    List<Ride> getAllRides();

    List<Ride> findPoolCars(PoolerJourneyPayload poolerJourneyPayload);

    Ride createRide(NewRidePayload newRidePayload);

    List<String> allCitiesOfRide(int id);

    Ride bookingRequest(BookRequestPayload bookRequestPayload);

    Ride createRide(OwnerRidePayload ownerRidePayload);
}
