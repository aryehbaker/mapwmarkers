package com.sample.aryeh.map;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
//import com.yelp.fusion.client.models.AccessToken;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sample.aryeh.map.R.id.map;

public class Maps extends Fragment
        implements GoogleMap.OnCameraIdleListener, OnMapReadyCallback {

    private GoogleMap mMap;
    double lat = 40.613490;
    double longi = -73.951779;
    LatLng focus = new LatLng(lat, longi);
    LatLngBounds b = new LatLngBounds(focus,focus);
    LatLngBounds older = new LatLngBounds(focus,focus);
    String accessToken = "e90YvY9xvIoGps-jdtGGiEMQGh0vVisqIfA3GdyUY7pRs8qyKIbL6wIAc02MlAUNRJDKdtUukssMj4l3QxwKaClATayNwDd_uDpo-YMk8CimBKX50qs5tJBgPb7iWHYx";
    ArrayList<Business> businesses;
    int mMarker = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_maps, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ((SupportMapFragment)getFragmentManager().findFragmentById(R.id.Maps));
        mapFragment.getMapAsync(this);
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

        getYelpMarkers();

        // Add a marker by me.

        mMap.addMarker(new MarkerOptions().position(focus).title("Marker by me"));


       }
    @Override
    public void onCameraIdle(){

    }

    public void getYelpMarkers(){
        YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
        try {
            YelpFusionApi yelpFusionApi = apiFactory.createAPI(accessToken);
            Map<String, String> params = new HashMap<>();
            // general params
            params.put("term", "restaurant");
            params.put("latitude", String.valueOf(lat));
            params.put("longitude", String.valueOf(longi));
            Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);
            Callback<SearchResponse> callback = new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    SearchResponse searchResponse = response.body();
                    int totalNumberOfResult = searchResponse.getTotal();
                    double lowlat = lat, hilat = lat,blat = lat, lowlongi = longi, hilongi = longi, blongi = longi;
                    businesses = searchResponse.getBusinesses();
                    LatLng y;
                    Double rating;
                    String businessName, bphone;
                    Marker m[] = new Marker[mMarker];
                    for (int i = 0; i < mMarker; i++) {
                        blat = businesses.get(i).getCoordinates().getLatitude();
                        blongi = businesses.get(i).getCoordinates().getLongitude();
                        if(blat > hilat)hilat = blat;
                        else if (blat < lowlat)lowlat = blat;
                        if (blongi > hilongi)hilongi = blongi;
                        else if (blongi < lowlongi)lowlongi = blongi;
                        y = new LatLng(blat, blongi);
                        businessName = businesses.get(i).getName();
                        bphone = businesses.get(i).getPhone();
                        rating = businesses.get(i).getRating();
                        m[i] = mMap.addMarker(new MarkerOptions()
                                .position(y)
                                .title(
                                        businessName +
                                        " phone#:" +
                                        bphone +
                                        " rating:"+
                                        rating
                                )
                                .snippet(i + " of " + totalNumberOfResult)
                        );
                        m[i].setTag(i);

                    }
                    LatLng llhigh = new LatLng(hilat, hilongi);
                    LatLng lllow = new LatLng(lowlat, lowlongi);
                    //b = new LatLngBounds(llhigh, lllow);

                    // Set the camera to the greatest possible zoom level that includes the
                    // bounds
                }
                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    Context context = getActivity().getApplicationContext();
                    String text = t.getLocalizedMessage()+" on failure";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();// HTTP error happened, do something to handle it.
                }
            };
            call.enqueue(callback);
        }catch (java.io.IOException t){
            Context context = getActivity().getApplicationContext();
            String text = t.getLocalizedMessage()+" on exception";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();// HTTP error happened, do something to handle it.
        }

    }
}

