package com.kajoba.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kajoba.app.Entities.Client;
import com.kajoba.app.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> implements View.OnClickListener{

    private ArrayList<Client> listClients;
    private View.OnClickListener listener;

    public ClientAdapter(ArrayList<Client> listClients) {
        this.listClients = listClients;
    }



    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.item_list_users, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);
        return new ClientViewHolder(view);
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameUser);
            tvPhone = itemView.findViewById(R.id.phoneUser);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        holder.tvName.setText(listClients.get(position).getName()); //para traer un entero colocar .toString
        holder.tvPhone.setText(listClients.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return listClients.size();
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
