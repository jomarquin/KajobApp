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
import com.kajoba.app.Adapters.BusinessmanAdapter;
import com.kajoba.app.ConnectionSQLiteHelper;
import com.kajoba.app.Entities.Businessman;
import com.kajoba.app.R;
import com.kajoba.app.Utilities.Utilities;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessmanFragment extends Fragment {

    private RecyclerView recyclerListBusinessman;
    private ArrayList<Businessman> listBusinessman;

    private ConnectionSQLiteHelper conn;

    public BusinessmanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_businessman, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Database instance
        conn = new ConnectionSQLiteHelper(getContext(), "DB_KAJOBAPP", null, 1);
        //Initializing all views
        setupViews(view);
        //Load clients from database
        listBusinessmanConsult();

        FloatingActionButton fab = view.findViewById(R.id.fabNetwork);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_networkFragment_to_newNetworkFragment);
            }
        });
    }

    private void listBusinessmanConsult() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Businessman businessman=null;
        //Consult database
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilities.TABLE_BUSINESSMAN+ " ORDER BY "+Utilities.FIELD_BUSINESSMAN_NAME, null);

        while (cursor.moveToNext()){
            businessman = new Businessman();
            businessman.setId(cursor.getInt(0));
            businessman.setName(cursor.getString(1));
            businessman.setIdentification(cursor.getString(2));
            businessman.setCodBusiness(cursor.getString(3));
            businessman.setPassBusiness(cursor.getString(4));
            businessman.setPhone(cursor.getString(5));
            businessman.setEmail(cursor.getString(6));
            businessman.setPassEmail(cursor.getString(7));
            businessman.setLocation(cursor.getString(8));
            businessman.setBirthDate(cursor.getString(9));

            listBusinessman.add(businessman);
        }
        db.close();

        BusinessmanAdapter adapter = new BusinessmanAdapter(listBusinessman);
        recyclerListBusinessman.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Evento para llamar al fragment DetailFoundFragment pasandole un objeto tipo bundle
                Bundle bundle = new Bundle(); //Creamos el bundle para transportar al objeto
                bundle.putSerializable("object", listBusinessman.get(recyclerListBusinessman.getChildAdapterPosition(view))); //Pasamos al bundle el objeto especifico
                findNavController(view).navigate(R.id.action_networkFragment_to_detailBusinessmanFragment, bundle); //Ejecutamos el action junto con el bundle
                //Toast.makeText(getContext(), "Mostrar Detalle", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupViews(View view) {
        recyclerListBusinessman =view.findViewById(R.id.recyclerNetwork);
        recyclerListBusinessman.setLayoutManager(new LinearLayoutManager(getContext()));
        listBusinessman =new ArrayList<>();

    }
}
