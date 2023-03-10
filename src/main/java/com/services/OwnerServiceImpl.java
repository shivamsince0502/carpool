package com.services;

import com.model.Car;
import com.model.Owner;
import com.model.Pooler;
import com.payload.LoginPayload;
import com.payload.OwnerUpdatePayload;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLData;
import java.util.List;
@Service
public class OwnerServiceImpl implements OwnerService{


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CarServices carServices;
    public List<Owner> getAllOwners() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Owner> ownerList = session.createQuery("from Owner", Owner.class).list();
        transaction.commit();
        session.close();
        return ownerList;
    }

    public Owner createOwner(Owner owner) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Owner> ownerList = getAllOwners();
        for(Owner owner1 : ownerList) {
            if(owner1.getUserName().equals(owner.getUserName()) || owner1.getOwnerEmail().equals(owner.getOwnerEmail()) || owner1.getOwnerMob().equals(owner.getOwnerMob()))
                return new Owner();
        }
        session.save(owner);
        List<Owner> ownerList1 = getAllOwners();
        int len = ownerList1.size();
        Owner owner1 = ownerList1.get(len-1);
        Car car = new Car();
        car.setCarName("Select");
        car.setCarNumber("xxxxxx");
        car.setCarColor("xxxx");
        carServices.addCar(car);
        transaction.commit();
        session.close();
        return owner1;
    }

    public Owner getOwnerById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.get(Owner.class, id);
        transaction.commit();
        session.close();
        return owner;
    }

    public Owner updateOwner(Owner owner) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(owner);
        transaction.commit();
        session.close();
        return owner;
    }

    public Owner deleteOwnerById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.get(Owner.class, id);
        session.delete(owner);
        transaction.commit();
        session.close();
        return owner;
    }

    @Override
    public Owner isOwner(LoginPayload loginPayload) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Owner> ownerList = session.createQuery("from Owner", Owner.class).list();
        Owner owner1 = new Owner();
        for(Owner owner : ownerList) {
            String un = owner.getUserName();
            String pw = owner.getPassword();
            if(un.equals(loginPayload.getUsername()) && pw.equals(loginPayload.getPassword())){
                owner1.setOwnerId(owner.getOwnerId());
                owner1.setOwnerEmail(owner.getOwnerEmail());
                owner1.setUserName(owner.getUserName());
                owner1.setOwnerName(owner.getOwnerName());
                owner1.setOwnerMob(owner.getOwnerMob());
                break;
            }
        }
        return owner1;
    }

    @Override
    public Owner updateOwner(OwnerUpdatePayload ownerUpdatePayload, int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.get(Owner.class, id);
        owner.setOwnerMob(ownerUpdatePayload.getOwnerName());
        owner.setOwnerName(ownerUpdatePayload.getOwnerName());
        owner.setOwnerEmail(ownerUpdatePayload.getOwnerEmail());
        owner.setUserName(ownerUpdatePayload.getUserName());
        session.saveOrUpdate(owner);
        transaction.commit();
        session.close();
        return owner;
    }


}
