package com.cars.cars.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cars.cars.R;
import com.cars.cars.models.leasing_request;
import com.cars.cars.models.maintenance;

import java.util.List;


public class maintenance_adapter extends RecyclerView.Adapter<maintenance_adapter.MyViewHolder>  {
    AlertDialog alertDialog;
Context mContext;
    String typeC="",priceC="",modelC="",detailsC="",typeViewC="",imageC="",type_service="",number="";
  List<maintenance> serviceList;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView details,street,city,name_customer,phone;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            details = (TextView) view.findViewById(R.id.details);
            name_customer = (TextView) view.findViewById(R.id.name_customer);
            street = (TextView) view.findViewById(R.id.street);
            city = (TextView) view.findViewById(R.id.city);
            phone = (TextView) view.findViewById(R.id.phone);

            cardView =(CardView)view.findViewById(R.id.cv);





        }
    }


    public maintenance_adapter(Context mContext, List<maintenance> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
        //this.orig=studentsList;
    }

    @Override
    public maintenance_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.maintenance_row_item, parent, false);

        return new maintenance_adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final maintenance_adapter.MyViewHolder holder, final int position) {
      final maintenance c = serviceList.get(position);

        holder.city.setText(c.getCity());
        holder.street.setText(c.getStreet());
        holder.details.setText(c.getDetails());
        holder.phone.setText(c.getPhone());
        holder.name_customer.setText(c.getName());

    }





        @Override
        public int getItemCount () {
            return serviceList.size();

    }











}
