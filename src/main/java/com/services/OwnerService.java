package com.services;

import com.model.Owner;
import com.model.OwnerNotification;
import com.model.Ride;
import com.model.RidePooler;
import com.payload.LoginPayload;
import com.payload.OwnerUpdatePayload;
import com.payload.ReqDecPayload;

import java.util.List;

public interface OwnerService {
    List<Owner> getAllOwners();

    Owner createOwner(Owner owner);
    Owner getOwnerById(int id) ;

    Owner updateOwner(Owner owner);

    Owner deleteOwnerById(int id);

    Owner isOwner(LoginPayload loginPayload);

    Owner updateOwner(OwnerUpdatePayload ownerUpdatePayload, int id);

    List<Ride> getAllPrevRidesByOwnerId(int id);

    List<Ride> getAllUpRides(int id);

    RidePooler approvePoolRequest(ReqDecPayload reqDecPayload);

    List<RidePooler> allPoolRequests(int ownerId);

    List<Ride> getAllRidesoFOwner(int ownerId);

    RidePooler declineRidePooler(int ridePoolId);

    List<OwnerNotification> allActiveNotificationsofOwner(int ownerId);

    OwnerNotification readNotification(int notifId);

}
