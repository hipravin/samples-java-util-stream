package com.hipravin.post.persist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POST_INDEX")
public class PostIndexEntity {

    @Id
    @Column(name = "IDX")
    private String index;

    @Column(name = "NAME")
    private String name;

    @Column(name = "REGION")
    private String region;

    @Column(name = "AUTONOM")
    private String autonom;

    @Column(name = "AREA")
    private String area;

    @Column(name = "CITY")
    private String city;

    @Column(name = "CITY1")
    private String city1;


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAutonom() {
        return autonom;
    }

    public void setAutonom(String autonom) {
        this.autonom = autonom;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }
}


