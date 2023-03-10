package com.services;

import com.model.RideCities;

import java.util.List;

public interface RideCitiesService {
    List<RideCities> getAllRideCities();
    RideCities createRideCity(int rideId, int cityId);
}
