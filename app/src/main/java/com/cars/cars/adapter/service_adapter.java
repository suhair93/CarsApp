package com.cars.cars.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cars.cars.R;
import com.cars.cars.company.details_car;
import com.cars.cars.company.details_part;
import com.cars.cars.models.service;

import java.util.List;


public class service_adapter extends RecyclerView.Adapter<service_adapter.MyViewHolder>  {
    AlertDialog alertDialog;
Context mContext;
    String typeC="",priceC="",modelC="",detailsC="",typeViewC="",imageC="",type_service="",number="";
  List<service> serviceList;
  int pos =0;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type,price,typeview,typeservic,model,details,number,userid,url_image;
        public ImageView img;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            type = (TextView) view.findViewById(R.id.type_car);
            price = (TextView) view.findViewById(R.id.car_price);
            typeview = (TextView) view.findViewById(R.id.car_typeview);
            typeservic = (TextView) view.findViewById(R.id.typeService);
            model = (TextView) view.findViewById(R.id.model);
            details = (TextView) view.findViewById(R.id.details);
            number = (TextView) view.findViewById(R.id.number);
            userid = (TextView) view.findViewById(R.id.userid);
            url_image = (TextView) view.findViewById(R.id.url_img);
            img = (ImageView) view.findViewById(R.id.img);

            cardView =(CardView)view.findViewById(R.id.cv);
            img.buildDrawingCache();
            final Bitmap bitmap = img.getDrawingCache();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (typeservic.getText().toString().equals("car")) {
                        Intent i = new Intent(mContext, details_car.class);
                            i.putExtra("type_car", type.getText().toString());
                            i.putExtra("model_car", model.getText().toString());
                            i.putExtra("price_car", price.getText().toString());
                            i.putExtra("details_car", details.getText().toString());
                            i.putExtra("image_car", url_image.getText().toString());
                            i.putExtra("typeview_car", typeview.getText().toString());
                            i.putExtra("userid", userid.getText().toString());
                            mContext.startActivity(i);

                    } else {
                        Intent i2 = new Intent(mContext, details_part.class);
                            i2.putExtra("type", type.getText().toString());
                            i2.putExtra("model", model.getText().toString());
                            i2.putExtra("price", price.getText().toString());
                            i2.putExtra("details",  details.getText().toString());
                            i2.putExtra("image", url_image.getText().toString());
                            i2.putExtra("number", number.getText().toString());
                            i2.putExtra("typeview", typeview.getText().toString());
                            i2.putExtra("userid", userid.getText().toString());
                            mContext.startActivity(i2);


                    }

                }
            });
        }
    }


    public service_adapter(Context mContext, List<service> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
        //this.orig=studentsList;
    }

    @Override
    public service_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_company_row_item, parent, false);

        return new service_adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final service_adapter.MyViewHolder holder, final int position) {
      final service c = serviceList.get(position);

        holder.type.setText(c.getName_or_type());
        holder.price.setText(c.getPrice());
        holder.typeview.setText(c.getTypeView());
        holder.typeservic.setText(c.getType_service());
        Glide.with(mContext).load(c.getImageURL()).into(holder.img);
        holder.details.setText(c.getDetails());
        holder.model.setText(c.getModel());
        holder.number.setText(c.getNumber());
        holder.url_image.setText(c.getImageURL());
        holder.userid.setText(c.getUserid());

    }





        @Override
        public int getItemCount () {
            return serviceList.size();

    }











}
