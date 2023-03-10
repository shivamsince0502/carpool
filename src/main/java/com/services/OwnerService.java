package com.services;

import com.model.Owner;
import com.payload.LoginPayload;
import com.payload.OwnerUpdatePayload;

import java.util.List;

public interface OwnerService {
    List<Owner> getAllOwners();

    Owner createOwner(Owner owner);
    Owner getOwnerById(int id) ;

    Owner updateOwner(Owner owner);

    Owner deleteOwnerById(int id);

    Owner isOwner(LoginPayload loginPayload);

    Owner updateOwner(OwnerUpdatePayload ownerUpdatePayload, int id);

}
