package com.controller;

import com.model.*;
import com.payload.*;
import com.services.OwnerService;
import com.services.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    RideService rideService;
    @Autowired
    OwnerService ownerService;
    @GetMapping("/getAllOwners")
    List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @PostMapping("/create")
    public Owner createOwner(@RequestBody Owner owner) {
        return ownerService.createOwner(owner);
    }

    @GetMapping("/owner:{id}")
    public Owner getOwnerById(@PathVariable("id") int id) {

        return ownerService.getOwnerById(id);
    }

    @PutMapping("/update")
    public Owner updateOwner(@RequestBody Owner owner) {return ownerService.updateOwner(owner);}

    @DeleteMapping("/{id}")
    public Owner deleteOwnerById(@PathVariable("id") int id) {return ownerService.deleteOwnerById(id);}

    @PostMapping("/loginowner")
    Owner isOwner(@RequestBody LoginPayload loginPayload) {
        return ownerService.isOwner(loginPayload);
    }

    @PostMapping("/updateowner/{id}")
    Owner updateOwner(@RequestBody OwnerUpdatePayload ownerUpdatePayload, @PathVariable("id") int id){
        return ownerService.updateOwner(ownerUpdatePayload, id);
    }

    @GetMapping("/getallprevrides/{id}")
    List<FinPoolResult> getAllPrevRidesByOwner(@PathVariable("id") int id) {
        return ownerService.getAllPrevRidesByOwnerId(id);
    }

    @GetMapping("/getAllUpRides/{id}")
    List<FinPoolResult> getAllUpRides(@PathVariable int id){
        return ownerService.getAllUpRides(id);
    }

    @GetMapping("allpoolrequest/{oId}")
    List<RidePooler> allPoolRequests(@PathVariable("oId") int ownerId) {
        return ownerService.allPoolRequests(ownerId);
    }

    @PostMapping("/requestdecisionbyowner")
    RidePooler decisionOfRequestByOwner(@RequestBody ReqDecPayload reqDecPayload) {
        return ownerService.approvePoolRequest(reqDecPayload);
    }

    @GetMapping("allridesofowner/{id}")
    List<Ride> getAllRidesOfOwner(@PathVariable("id") int ownerId) {
        return ownerService.getAllRidesoFOwner(ownerId);
    }

    @PostMapping("removepoolerfromrride{id}")
    RidePooler removePoolerFromRide(@PathVariable("id") int ridePoolerId) {
        return ownerService.declineRidePooler(ridePoolerId);
    }


    @GetMapping("allactivenotifofowner/{id}")
    List<OwnerNotification> allActiveNotification(@PathVariable("id") int ownerId) {
        return ownerService.allActiveNotificationsofOwner(ownerId);
    }

    @PostMapping("readnotif/{notifid}")
    OwnerNotification readNotif(@PathVariable("notifid") int notifId){
        return ownerService.readNotification(notifId);
    }

}
