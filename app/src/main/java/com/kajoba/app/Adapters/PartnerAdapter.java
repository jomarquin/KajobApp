package com.kajoba.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kajoba.app.Entities.Client;
import com.kajoba.app.Entities.Partner;
import com.kajoba.app.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.PartnerViewHolder> implements View.OnClickListener {

    private ArrayList<Partner> listPartners;
    private View.OnClickListener listener;

    public PartnerAdapter(ArrayList<Partner> listPartners) {
        this.listPartners = listPartners;
    }



    @NonNull
    @Override
    public PartnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.item_list_users, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);
        return new PartnerAdapter.PartnerViewHolder(view);
    }

    public class PartnerViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone;

        public PartnerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameUser);
            tvPhone = itemView.findViewById(R.id.phoneUser);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PartnerViewHolder holder, int position) {
        holder.tvName.setText(listPartners.get(position).getName()); //para traer un entero colocar .toString
        holder.tvPhone.setText(listPartners.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return listPartners.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }



}
