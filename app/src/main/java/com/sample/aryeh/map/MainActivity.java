package com.sample.aryeh.map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sample.aryeh.map.MyServer.restaurantgetter;
import com.sample.aryeh.map.MyServer.Restaurant.Restaurant;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity
        implements GoogleMap.OnCameraIdleListener, OnMapReadyCallback {
    private GoogleMap mMap;
    double lat = 40.613490;
    double longi = -73.951779;
    LatLng focus = new LatLng(lat, longi);
    LatLngBounds b = new LatLngBounds(focus,focus);
    LatLngBounds older = new LatLngBounds(focus,focus);
    String accessToken = "e90YvY9xvIoGps-jdtGGiEMQGh0vVisqIfA3GdyUY7pRs8qyKIbL6wIAc02MlAUNRJDKdtUukssMj4l3QxwKaClATayNwDd_uDpo-YMk8CimBKX50qs5tJBgPb7iWHYx";
     int mMarker = 90;
    RecyclerView rece;
    CardPlaceAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new Maps();fragmentManager.findFragmentById(R.id.Maps);

        if (fragment == null) {
            fragment = new Maps();
            fragmentManager.beginTransaction()
                    .add(R.id.Maps, fragment)
                    .commit();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.Maps));
        mapFragment.getMapAsync(this);

        rece = (RecyclerView)findViewById(R.id.rec);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
       // rece.setHasFixedSize(true);

         // Attach the adapter to the recyclerview to populate items
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rece.setLayoutManager(layoutManager);

        //PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
        //        getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Me.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(focus));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        combineServerResponse().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<combinedSource>() {
                    @Override
                    public void onNext(combinedSource CSR) {
                        MainActivity.this.putMarkers(CSR);
                    }//Handle logic


                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // Add a marker by me.

        mMap.addMarker(new MarkerOptions().position(focus).title("Marker by me"));


    }
    @Override
    public void onCameraIdle(){

    }

    public Observable<SearchResponse> getYelpResponse() {
        Map<String, String> params = new HashMap<>();
        // general params
        params.put("term", "restaurant");
        params.put("latitude", String.valueOf(lat));
        params.put("longitude", String.valueOf(longi));
        params.put("sort_by", "distance");
        com.sample.aryeh.map.YelpFusionApi.YelpFusionApi yelpFusionApi = null;

        com.sample.aryeh.map.YelpFusionApi.YelpFusionApiFactory apiFactory
                = new com.sample.aryeh.map.YelpFusionApi.YelpFusionApiFactory();
         try {
             yelpFusionApi = apiFactory.createAPI(accessToken);
         }catch(java.io.IOException t) {
             Context context = getApplicationContext();
             String text = t.getLocalizedMessage() + " on failure accesstoken fusion";
             int duration = Toast.LENGTH_LONG;

             Toast toast = Toast.makeText(context, text, duration);
             toast.show();
         }// HTTP error happened, do something to handle it.}
        return yelpFusionApi.getBusinessSearch(params)
                .subscribeOn(Schedulers.newThread());
    }


    public Observable <List<Restaurant>> getMyServer() {
        String My_Server_API_BASE_URL = "http://34.227.12.12:3000";
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(My_Server_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restaurantgetter client = retrofit.create(restaurantgetter.class);
        return client.getRestaurants(lat, longi);




}
    public Observable <combinedSource> combineServerResponse() {
        return Observable.zip(
                getYelpResponse(),
                getMyServer(),
                (search, rester) -> {
                    ArrayList<Business> b = search.getBusinesses();
                    return new combinedSource(b, rester,lat,longi);
                }
        );
    }
    public void putMarkers(combinedSource cs){
        double lowlat = lat, hilat = lat, blat = lat, lowlongi = longi, hilongi = longi, blongi = longi;
        LatLng y;
        int Rating = 0;
        int bussinessIterator = cs.getBussiness().size(),
            restaurantIterator = cs.getRestaurants().size();
        if(bussinessIterator > mMarker)bussinessIterator = mMarker;
        if(restaurantIterator > mMarker)restaurantIterator = mMarker;
        Double rating;
        String businessName;


        Marker m2[] = new Marker[bussinessIterator+restaurantIterator];
        for (int i = 0; i < restaurantIterator; i++) {
            blat = cs.getRestaurants().get(i).getLoc().getCoordinates().get(1);
            blongi = cs.getRestaurants().get(i).getLoc().getCoordinates().get(0);

            if (blat > hilat) hilat = blat;
            else if (blat < lowlat) lowlat = blat;
            if (blongi > hilongi) hilongi = blongi;
            else if (blongi < lowlongi) lowlongi = blongi;
            y = new LatLng(blat, blongi);
            businessName = cs.getRestaurants().get(i).getName();
            //Rating = cs.getRestaurants().get(i).getGrades().get(0).getScore();
            m2[i] = mMap.addMarker(new MarkerOptions()
                    .position(y)
                    .title(
                            businessName +
                                    " rating:"// +
                                 //   Rating
                    )
                    .snippet("number "+i + " out of " + cs.getRestaurants().size() )
            );
            m2[i].setTag(i);

        }

        for (int i = 0; i < bussinessIterator; i++) {
              blat = cs.getBussiness().get(i).getCoordinates().getLatitude();
              blongi = cs.getBussiness().get(i).getCoordinates().getLongitude();

               if (blat > hilat) hilat = blat;
              else if (blat < lowlat) lowlat = blat;
              if (blongi > hilongi) hilongi = blongi;
              else if (blongi < lowlongi) lowlongi = blongi;
              y = new LatLng(blat, blongi);
              businessName = cs.getBussiness().get(i).getName();
              rating = cs.getBussiness().get(i).getRating();
              m2[i + restaurantIterator] = mMap.addMarker(new MarkerOptions()
                         .position(y)
                         .title(
                            businessName +
                            " rating:" +
                            rating
                         )
                         .snippet("number "+i + " out of " + cs.getBussiness().size())
              );
              m2[i + restaurantIterator].setTag(i + restaurantIterator);

           }
           adapter = new CardPlaceAdapter(cs);
            rece.setAdapter(adapter);
    }


    }





