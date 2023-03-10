package com.controller;

import com.model.Owner;
import com.model.Pooler;
import com.payload.LoginPayload;
import com.payload.OwnerUpdatePayload;
import com.payload.PoolerUpdatePayload;
import com.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/owner")
public class OwnerController {
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
    Owner updateOwner(@RequestBody OwnerUpdatePayload ownerUpdatePayload, @PathVariable int id){
        return ownerService.updateOwner(ownerUpdatePayload, id);
    }

}
