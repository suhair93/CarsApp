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

import java.util.List;


public class maintenance_adapter extends RecyclerView.Adapter<maintenance_adapter.MyViewHolder>  {
    AlertDialog alertDialog;
Context mContext;
    String typeC="",priceC="",modelC="",detailsC="",typeViewC="",imageC="",type_service="",number="";
  List<leasing_request> serviceList;
  int pos =0;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type,typeview,model,name_customer;
        public ImageView img;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            type = (TextView) view.findViewById(R.id.type_car);
            name_customer = (TextView) view.findViewById(R.id.name_customer);
            typeview = (TextView) view.findViewById(R.id.typeview);
            model = (TextView) view.findViewById(R.id.model);

            cardView =(CardView)view.findViewById(R.id.cv);





        }
    }


    public maintenance_adapter(Context mContext, List<leasing_request> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
        //this.orig=studentsList;
    }

    @Override
    public maintenance_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buy_row_item, parent, false);

        return new maintenance_adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final maintenance_adapter.MyViewHolder holder, final int position) {
      final leasing_request c = serviceList.get(position);

        holder.type.setText(c.getType_service());
        holder.model.setText(c.getModel_service());
        holder.typeview.setText(c.getTypeview());
        holder.name_customer.setText(c.getName_customer());

    }





        @Override
        public int getItemCount () {
            return serviceList.size();

    }











}
