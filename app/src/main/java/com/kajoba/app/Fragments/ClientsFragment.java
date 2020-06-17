package com.kajoba.app.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kajoba.app.Adapters.ClientAdapter;
import com.kajoba.app.ConnectionSQLiteHelper;
import com.kajoba.app.Entities.Client;
import com.kajoba.app.R;
import com.kajoba.app.Utilities.Utilities;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsFragment extends Fragment {

    private RecyclerView recyclerListClients;
    private ArrayList<Client> listClients;

    private ConnectionSQLiteHelper conn;

    public ClientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Database instance
        conn = new ConnectionSQLiteHelper(getContext(), "DB_KAJOBAPP", null, 1);
        //Initializing all views
        setupViews(view);
        //Load clients from database
        listClientsConsult();

        FloatingActionButton fab = view.findViewById(R.id.fabClients);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_clientsFragment_to_newClientFragment);
            }
        });
    }

    private void listClientsConsult() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Client client=null;
        //Consult database
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilities.TABLE_CLIENT+ " ORDER BY "+Utilities.FIELD_CLIENT_NAME, null);

        while (cursor.moveToNext()){
            client = new Client();
            client.setId(cursor.getInt(0));
            client.setName(cursor.getString(1));
            client.setIdentification(cursor.getString(2));
            client.setPhone(cursor.getString(3));
            client.setEmail(cursor.getString(4));
            client.setLocation(cursor.getString(5));
            client.setBirthDate(cursor.getString(6));

            listClients.add(client);
        }
        db.close();

        ClientAdapter adapter = new ClientAdapter(listClients);
        recyclerListClients.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Evento para llamar al fragment DetailFoundFragment pasandole un objeto tipo bundle
                Bundle bundle = new Bundle(); //Creamos el bundle para transportar al objeto
                bundle.putSerializable("object", listClients.get(recyclerListClients.getChildAdapterPosition(view))); //Pasamos al bundle el objeto especifico
                findNavController(view).navigate(R.id.action_clientsFragment_to_detailClientFragment, bundle); //Ejecutamos el action junto con el bundle
                //Toast.makeText(getContext(), "Mostrar Detalle", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void setupViews(View view) {
        recyclerListClients =view.findViewById(R.id.recyclerClients);
        recyclerListClients.setLayoutManager(new LinearLayoutManager(getContext()));
        listClients=new ArrayList<>();

    }
}
