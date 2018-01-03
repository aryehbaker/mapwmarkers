package com.sample.aryeh.map;

import com.sample.aryeh.map.MyServer.Restaurant.Restaurant;
import com.sample.aryeh.map.YelpFusionApi.combinedForView;
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
    ArrayList<combinedForView>c;

    double latitude, longitude;

    public combinedSource(ArrayList<Business> a, List<Restaurant> b, double latitude, double longitude) {
        this.a = a;
        this.b = b;
        this.latitude = latitude;
        this.longitude = longitude;
        combinedForViewSetter();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    public ArrayList<Business> getBussiness() {return a; }
    public List<Restaurant> getRestaurants(){return b;}
    private void combinedForViewSetter(){
        int aTraverse = 0,aSize = a.size(), bTraverse =0,bSize = b.size(), cTraverse=0;
        c=new ArrayList<combinedForView>();
        c.ensureCapacity(aSize+bSize);
        double aDistance,bDistance;
        aDistance = distance(latitude,longitude,a.get(aTraverse).getCoordinates().getLatitude(),
                        a.get(aTraverse).getCoordinates().getLongitude());
        bDistance = distance(latitude,longitude,b.get(bTraverse).getLoc().getCoordinates().get(1),
                        b.get(bTraverse).getLoc().getCoordinates().get(0));
        if (aSize >=1 && bSize >=1){
            for (int i = 0; i<aSize+bSize;i++){
                if( aDistance <= bDistance){
                    combinedForView e = new combinedForView(cTraverse,0,aTraverse,aDistance);
                    c.add(e);
                    aTraverse++;
                    if(aTraverse < aSize)aDistance = distance(latitude,longitude,a.get(aTraverse).getCoordinates().getLatitude(),
                            a.get(aTraverse).getCoordinates().getLongitude());
                    cTraverse++;
                    if (aTraverse >= aSize){
                        while (i < aSize+bSize && bTraverse < bSize){
                            e = new combinedForView(cTraverse,2,bTraverse,bDistance);
                            c.add(e);
                            bTraverse++;
                            cTraverse++;
                            i++;
                            if(bTraverse<bSize)bDistance = distance(latitude,longitude,b.get(bTraverse).getLoc().getCoordinates().get(1),
                                b.get(bTraverse).getLoc().getCoordinates().get(0));
                        }
                    }
                }else{
                    combinedForView e = new combinedForView(cTraverse,2,bTraverse,bDistance);
                    c.add(e);
                    bTraverse++;
                    if (bTraverse < bSize)bDistance = distance(
                            latitude,longitude,b.get(bTraverse).getLoc().getCoordinates().get(1),
                            b.get(bTraverse).getLoc().getCoordinates().get(0)
                    );

                    cTraverse++;
                    if (bTraverse >= bSize) {
                        while (i < aSize+bSize && aTraverse < aSize) {
                            e = new combinedForView(cTraverse, 0, aTraverse, aDistance);
                            c.add(e);
                            aTraverse++;
                            cTraverse++;
                            i++;
                            if(aTraverse<aSize)aDistance = distance(latitude, longitude, a.get(aTraverse).getCoordinates().getLatitude(),
                                 a.get(aTraverse).getCoordinates().getLongitude());
                        }
                    }
                }

            }
        }
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
