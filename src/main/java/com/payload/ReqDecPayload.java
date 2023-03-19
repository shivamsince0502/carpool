package com.payload;


//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@PostMapping("requestdecisionbyowner/{poolemob}/{rideId}/{ownerId}/{approved}")
//    RidePooler decisionOfRequestByOwner(@PathVariable("poolermob") String mob, @PathVariable("rideID") int rideId,
//@PathVariable("ownerId") int ownerId,@PathVariable("approved") int approved) {
//        return ownerService.approvePoolRequest(mob, rideId, ownerId, approved);
//        }
public class ReqDecPayload {
    private String poolerMob;
    private int rideId;
    private int approved;

    public ReqDecPayload(String poolerMob, int rideId, int approved) {
        this.poolerMob = poolerMob;
        this.rideId = rideId;
        this.approved = approved;
    }

    public String getPoolerMob() {
        return poolerMob;
    }

    public void setPoolerMob(String poolerMob) {
        this.poolerMob = poolerMob;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }


    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }
}
