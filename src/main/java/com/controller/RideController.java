package com.controller;

import com.model.Ride;
import com.model.RideCities;
import com.model.RidePooler;
import com.payload.BookRequestPayload;
import com.payload.OwnerRidePayload;
import com.payload.PoolerJourneyPayload;
import com.payload.UpdateRidePayload;
import com.services.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    List<Ride> getAllRequiredPools(@RequestBody PoolerJourneyPayload poolerJourneyPayload){
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
    Ride createRide(@RequestBody OwnerRidePayload ownerRidePayload) {
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
        return rideService.findRideForPooler(rideId, poolerId);
    }

    @DeleteMapping("unbookride/{rid}/{pid}")
    RidePooler unBookRideForPooler(@PathVariable("rid") int rideId, @PathVariable("pid") int poolerId){
        return rideService.unBookRide(rideId, poolerId);
    }

}
