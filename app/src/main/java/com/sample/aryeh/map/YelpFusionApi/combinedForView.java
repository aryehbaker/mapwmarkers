package com.sample.aryeh.map.YelpFusionApi;

import java.util.ArrayList;

/**
 * Created by aryeh on 1/1/2018.
 */

public class combinedForView {
    int position, type, typePlace;


    double distance;

    public combinedForView(int position, int type, int typePlace, double distance) {
        this.position = position;
        this.type = type;
        this.typePlace = typePlace;
        this.distance = distance;
    }

    public combinedForView(int position, int type, int typePlace) {
        this.position = position;
        this.type = type;
        this.typePlace = typePlace;
    }
    public double getDistance() {
        return distance;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(int typePlace) {
        this.typePlace = typePlace;
    }
}
