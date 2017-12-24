package com.sample.aryeh.map;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.aryeh.map.MyServer.Restaurant.Restaurant;
import com.yelp.fusion.client.models.Business;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by aryeh on 5/22/2017.
 */

class CardPlaceAdapter extends RecyclerView.Adapter<CardPlaceAdapter.ViewHolder>{
    ArrayList<Business> b;
    int sizeL;
    List<Restaurant> c;
    int sizeL2;
    public CardPlaceAdapter (ArrayList<Business> b, int sizeL){this.b=b;this.sizeL=sizeL;}
    public CardPlaceAdapter (combinedSource cs){
        this.b=cs.getBussiness();
        this.sizeL=cs.getBussiness().size();
        this.c=cs.getRestaurants();
        this.sizeL2 = cs.getRestaurants().size();
    }

    //Provide a reference to the views used in the recycler view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    @Override
    public CardPlaceAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place, parent, false);
        return new ViewHolder(cv);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
//Set the values inside the given view
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.imageButton);
        imageView.setImageResource(R.drawable.yelp_trademark);
        TextView textView = (TextView)cardView.findViewById(R.id.name);
        textView.setText(b.get(position).getName());
        textView = (TextView)cardView.findViewById(R.id.address);
        textView.setText(b.get(position).getLocation().getAddress1());
        textView.append(" ");
        //textView.append(b.get(position).getLocation().getCity());
        //textView.append(", ");
        //textView.append(b.get(position).getLocation().getState());
        //textView.append(" ");
        textView.append(b.get(position).getLocation().getZipCode());
        textView = (TextView)cardView.findViewById(R.id.phone);
        textView.setText(b.get(position).getPhone());
    }
    @Override
    public int getItemCount(){
//Return the number of items in the data set
        return sizeL;
    }
}
