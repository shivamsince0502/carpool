package com.services;

import com.model.Pooler;
import com.model.RidePooler;
import com.payload.LoginPayload;
import com.payload.PoolerUpdatePayload;

import java.util.List;

public interface PoolerService {

    List<Pooler> getAllPooler();
    Pooler getPoolerById(int id);

    Pooler createPooler(Pooler pooler);

    Pooler updatePooler(Pooler pooler);

    Pooler deletePoolerById(int id);

    Pooler isPooler(LoginPayload loginPayload);

    Pooler updatePooler(PoolerUpdatePayload poolerUpdatePayload, int id);

    List<RidePooler> allUpRideByPoolerId(int id);
    List<RidePooler> allPrevRideByPoolerId(int id);

    RidePooler deleteRidePooler(int rideId, int poolerId);
}
