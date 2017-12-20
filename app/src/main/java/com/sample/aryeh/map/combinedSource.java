package com.sample.aryeh.map;

import com.sample.aryeh.map.MyServer.Restaurant.Restaurant;
import com.yelp.fusion.client.models.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aryeh on 8/8/2017.
 */

public class combinedSource {
    public combinedSource(ArrayList <Business> a,List<Restaurant> b){this.a = a;this.b=b;}
    ArrayList <Business> a;
    List<Restaurant> b;

    public ArrayList<Business> getBussiness() {return a; }
    public List<Restaurant> getRestaurants(){return b;}
}
