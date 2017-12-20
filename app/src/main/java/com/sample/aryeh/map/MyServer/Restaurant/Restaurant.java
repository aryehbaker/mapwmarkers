package com.sample.aryeh.map.MyServer.Restaurant;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sample.aryeh.map.MyServer.Restaurant.Address;
import com.sample.aryeh.map.MyServer.Restaurant.Grade;
import com.sample.aryeh.map.MyServer.Restaurant.Loc;

public class Restaurant {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("borough")
    @Expose
    private String borough;
    @SerializedName("cuisine")
    @Expose
    private String cuisine;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("modified")
    @Expose
    private Object modified;
    @SerializedName("created")
    @Expose
    private Object created;
    @SerializedName("loc")
    @Expose
    private Loc loc;
    @SerializedName("grades")
    @Expose
    private List<Grade> grades = null;
    @SerializedName("address")
    @Expose
    private Address address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Object getModified() {
        return modified;
    }

    public void setModified(Object modified) {
        this.modified = modified;
    }

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
