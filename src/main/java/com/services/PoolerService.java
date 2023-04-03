package com.services;

import com.model.Pooler;
import com.model.PoolerNotification;
import com.model.RidePooler;
import com.payload.LoginPayload;
import com.payload.PoolerUpdatePayload;
import com.payload.UpRidePoolerDet;

import java.util.List;

public interface PoolerService {

    List<Pooler> getAllPooler();
    Pooler getPoolerById(int id);

    Pooler createPooler(Pooler pooler);

    Pooler updatePooler(Pooler pooler);

    Pooler deletePoolerById(int id);

    Pooler isPooler(LoginPayload loginPayload);

    Pooler updatePooler(PoolerUpdatePayload poolerUpdatePayload, int id);

    List<UpRidePoolerDet> allUpRideByPoolerId(int id);
    List<UpRidePoolerDet> allPrevRideByPoolerId(int id);

    RidePooler deleteRidePooler(int rideId, int poolerId);

    List<PoolerNotification> allNotifOfPoolerById(int poolerId);

    PoolerNotification readNotificationByPooler(int notifId);

}
