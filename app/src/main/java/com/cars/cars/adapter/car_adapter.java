package com.cars.cars.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cars.cars.R;
import com.cars.cars.models.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class car_adapter extends RecyclerView.Adapter<car_adapter.MyViewHolder>  {
    AlertDialog alertDialog;
Context mContext;
    String type="",price="",userid="",typeview="";
  List<Car> carList;
  int pos =0;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type_car,price_car,typeview_car;
        public ImageView car_img;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            type_car = (TextView) view.findViewById(R.id.type_car);
            price_car = (TextView) view.findViewById(R.id.car_price);
            typeview_car = (TextView) view.findViewById(R.id.car_typeview);
            car_img = (ImageView) view.findViewById(R.id.img);
            cardView =(CardView)view.findViewById(R.id.cv);
        }
    }


    public car_adapter(Context mContext, List<Car> carList) {
        this.mContext = mContext;
        this.carList = carList;
        //this.orig=studentsList;
    }

    @Override
    public car_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_company_row_item, parent, false);

        return new car_adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final car_adapter.MyViewHolder holder, final int position) {
        Car c = carList.get(position);

        holder.type_car.setText(c.getType());
        holder.price_car.setText(c.getPrice());
        holder.typeview_car.setText(c.getTypeView());
        Glide.with(mContext).load(c.getImageURL()).into(holder.car_img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }





        @Override
        public int getItemCount () {
            return carList.size();

    }











}
