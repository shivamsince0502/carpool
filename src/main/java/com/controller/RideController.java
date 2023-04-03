package com.controller;

import com.model.PoolerNotification;
import com.model.Ride;
import com.model.RideCities;
import com.model.RidePooler;
import com.payload.*;
import com.services.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/ride")
public class RideController {

    @Autowired
    RideService rideService;
    @GetMapping("/allactiverides")
    List<Ride> allActiveRide() {
        return rideService.getAllActiveRides();
    }

    @PostMapping("/findcars")
    List<FinPoolResult> getAllRequiredPools(@RequestBody PoolerJourneyPayload poolerJourneyPayload){
        return rideService.findPoolCars(poolerJourneyPayload);
    }

    @GetMapping("/allcities/{id}")
    List<String> getAllCities(@PathVariable int id) {
        return rideService.allCitiesOfRide(id);
    }

    @PostMapping("/bookrequest")
    RidePooler bookRequest(@RequestBody BookRequestPayload bookRequestPayload) {
        return rideService.bookingRequest(bookRequestPayload);
    }

    @PostMapping("/createride")
    FinPoolResult createRide(@RequestBody OwnerRidePayload ownerRidePayload) {
        return rideService.createRide(ownerRidePayload);
    }

    @PostMapping("/deleteride/{id}")
    Ride deleteRideById(@PathVariable int id) {
        return rideService.deleteRideById(id);
    }

    @GetMapping("getridebyrideid/{id}")
    Ride getRideById(@PathVariable int id) {
        return rideService.getRideByRideId(id);
    }

    @PostMapping("finishride/{id}")
    Ride finishRideId(@PathVariable int id) {
        return rideService.finishRideById(id);
    }

    @PostMapping("updateride")
    Ride updateRide(@RequestBody UpdateRidePayload updateRidePayload) {
        return rideService.updateRide(updateRidePayload);
    }

    @PostMapping("finishrideforpooler/{rid}/{pid}")
    RidePooler finishRideforPooler(@PathVariable("rid") int rideId, @PathVariable("pid") int poolerId){
        return rideService.finishRideForPooler(rideId, poolerId);
    }

    @PostMapping("unbookride/{rid}/{pid}")
    RidePooler unBookRideForPooler(@PathVariable("rid") int rideId, @PathVariable("pid") int poolerId){
        return rideService.unBookRide(rideId, poolerId);
    }

    @GetMapping("getcurrdate")
    List<Timestamp> getCurrDate() {
        return rideService.getCurrDate();
    }

    @GetMapping("allpoolersinride/{id}")
    List<RidePooler> allPoolersOfRide(@PathVariable("id") int rideId) {
        return rideService.allPoolersOfRide(rideId);
    }

}
