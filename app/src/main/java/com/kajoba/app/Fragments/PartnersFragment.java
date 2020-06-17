package com.kajoba.app.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kajoba.app.Adapters.ClientAdapter;
import com.kajoba.app.Adapters.PartnerAdapter;
import com.kajoba.app.ConnectionSQLiteHelper;
import com.kajoba.app.Entities.Client;
import com.kajoba.app.Entities.Partner;
import com.kajoba.app.R;
import com.kajoba.app.Utilities.Utilities;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartnersFragment extends Fragment {

    private RecyclerView recyclerListPartners;
    private ArrayList<Partner> listPartners;

    private ConnectionSQLiteHelper conn;

    public PartnersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partners, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Database instance
        conn = new ConnectionSQLiteHelper(getContext(), "DB_KAJOBAPP", null, 1);
        //Initializing all views
        setupViews(view);
        //Load clients from database
        listPartnersConsult();

        FloatingActionButton fab = view.findViewById(R.id.fabPartner);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_partnersFragment_to_newPartnerFragment);
            }
        });
    }

    private void listPartnersConsult() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Partner partner=null;
        //Consult database
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilities.TABLE_PARTNER+ " ORDER BY "+Utilities.FIELD_PARTNER_NAME, null);

        while (cursor.moveToNext()){
            partner = new Partner();
            partner.setId(cursor.getInt(0));
            partner.setName(cursor.getString(1));
            partner.setIdentification(cursor.getString(2));
            partner.setPhone(cursor.getString(3));
            partner.setEmail(cursor.getString(4));
            partner.setLocation(cursor.getString(5));
            partner.setBirthDate(cursor.getString(6));

            listPartners.add(partner);
        }
        db.close();

        PartnerAdapter adapter = new PartnerAdapter(listPartners);
        recyclerListPartners.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Evento para llamar al fragment DetailFoundFragment pasandole un objeto tipo bundle
                Bundle bundle = new Bundle(); //Creamos el bundle para transportar al objeto
                bundle.putSerializable("object", listPartners.get(recyclerListPartners.getChildAdapterPosition(view))); //Pasamos al bundle el objeto especifico
                findNavController(view).navigate(R.id.action_partnersFragment_to_detailPartnerFragment, bundle); //Ejecutamos el action junto con el bundle
                //Toast.makeText(getContext(), "Mostrar Detalle", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void setupViews(View view) {
        recyclerListPartners =view.findViewById(R.id.recyclerPartners);
        recyclerListPartners.setLayoutManager(new LinearLayoutManager(getContext()));
        listPartners=new ArrayList<>();
    }
}
