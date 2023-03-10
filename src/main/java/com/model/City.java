package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City {
    @Id
    @Column(name = "city_id")
    private int cityId;

    @Column(name = "city_name")
    private String cityName;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public City(){}

    public City(int cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }
}
