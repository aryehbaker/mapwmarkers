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
    int sizeL, bIterator = 0;
    List<Restaurant> c;
    int sizeL2, cIterator = 0;

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
    public int getItemViewType(int position) {
        return 0;
    }
    @Override
    public CardPlaceAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = null;
        switch (viewType){
            case 0:{
             cv = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_place, parent, false);
             return new ViewHolder(cv);

            }
            case 2:{
             cv = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_place2, parent, false);
             return new ViewHolder(cv);

            }
        }
        return null;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        switch (holder.getItemViewType()) {
            case 0:
//Set the values inside the given view
                CardView cardView = holder.cardView;
                ImageView imageView = (ImageView) cardView.findViewById(R.id.imageButton);
                imageView.setImageResource(R.drawable.yelp_trademark);
                TextView textView = (TextView) cardView.findViewById(R.id.name);
                textView.setText(b.get(position).getName());
                textView = (TextView) cardView.findViewById(R.id.address);
                textView.setText(b.get(position).getLocation().getAddress1());
                textView.append(" ");
                //textView.append(b.get(position).getLocation().getCity());
                //textView.append(", ");
                //textView.append(b.get(position).getLocation().getState());
                //textView.append(" ");
                textView.append(b.get(position).getLocation().getZipCode());
                textView = (TextView) cardView.findViewById(R.id.phone);
                textView.setText(b.get(position).getPhone());
                break;
            case 2:
                CardView cardView2 = holder.cardView;
                TextView textView2 = (TextView) cardView2.findViewById(R.id.name);
                textView2.setText(c.get(position).getName());
                textView = (TextView) cardView2.findViewById(R.id.address);
                textView.setText(c.get(position).getAddress().getBuilding() + " "
                        +c.get(position).getAddress().getStreet()+ " "
                        +c.get(position).getAddress().getZipcode());
                textView.append(" ");
                textView = (TextView) cardView2.findViewById(R.id.phone);
                textView.setText(c.get(position).getId());
                break;
        }
    }
    @Override
    public int getItemCount(){
//Return the number of items in the data set
        return sizeL;
    }
}
