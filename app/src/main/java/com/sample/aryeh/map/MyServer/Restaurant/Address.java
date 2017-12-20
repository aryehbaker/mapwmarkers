package com.sample.aryeh.map.MyServer.Restaurant;


/**
 * Created by aryeh on 7/14/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class Address {

        @SerializedName("building")
        @Expose
        private Integer building;
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("zipcode")
        @Expose
        private Integer zipcode;
        @SerializedName("coord")
        @Expose
        private List<Double> coord = null;

        public Integer getBuilding() {
            return building;
        }

        public void setBuilding(Integer building) {
            this.building = building;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public Integer getZipcode() {
            return zipcode;
        }

        public void setZipcode(Integer zipcode) {
            this.zipcode = zipcode;
        }

        public List<Double> getCoord() {
            return coord;
        }

        public void setCoord(List<Double> coord) {
            this.coord = coord;
        }

    }

