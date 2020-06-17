package com.kajoba.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kajoba.app.Entities.Businessman;
import com.kajoba.app.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BusinessmanAdapter extends RecyclerView.Adapter<BusinessmanAdapter.BusinessmanViewHolder> implements View.OnClickListener {

    private ArrayList<Businessman> listBusinessman;
    private View.OnClickListener listener;

    public BusinessmanAdapter(ArrayList<Businessman> listBusinessman) {
        this.listBusinessman = listBusinessman;
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

    @NonNull
    @Override
    public BusinessmanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.item_list_users, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);
        return new BusinessmanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessmanViewHolder holder, int position) {
        holder.tvName.setText(listBusinessman.get(position).getName()); //para traer un entero colocar .toString
        holder.tvPhone.setText(listBusinessman.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return listBusinessman.size();
    }

    public class BusinessmanViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone;

        public BusinessmanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameUser);
            tvPhone = itemView.findViewById(R.id.phoneUser);
        }
    }
}
