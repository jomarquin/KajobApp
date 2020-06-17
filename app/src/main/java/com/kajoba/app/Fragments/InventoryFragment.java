package com.kajoba.app.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.kajoba.app.Adapters.ClientAdapter;
import com.kajoba.app.ConnectionSQLiteHelper;
import com.kajoba.app.Entities.Client;
import com.kajoba.app.R;
import com.kajoba.app.Utilities.TableDynamic;
import com.kajoba.app.Utilities.Utilities;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment{

    /////////////////////////////////////
    //private TableLayout tableLayout;
    private Context context;
    //private String[] header;
    private ArrayList<String[]>data;
    private TableRow tableRow;
    private TextView txtCell;
    private int indexCell;
    private int indexRow;
    //////////////////////////////////////

    private TableLayout tableLayout;
    private String[]header = {"SKU","Nombre","Categoría","Descripción","Cantidad","Costo","Precio"};
    private ArrayList<String[]> rows = new ArrayList<>();

    private ConnectionSQLiteHelper conn;

    public InventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Database instance
        conn = new ConnectionSQLiteHelper(getContext(), "DB_KAJOBAPP", null, 1);
        tableLayout = view.findViewById(R.id.tableInventory);

//        TableDynamic tableDynamic = new TableDynamic(tableLayout, getContext());
//        tableDynamic.addHeader(header);
//        tableDynamic.addData(getInventory());
        addHeader(header);
        addData(getInventory());

    }

    private ArrayList<String[]> getInventory() {

        rows.clear();
        SQLiteDatabase db = conn.getReadableDatabase();
        //Client client=null;
        //Consult database
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilities.TABLE_PRODUCT+ " ORDER BY "+Utilities.FIELD_PRODUCT_NAME, null);

        while (cursor.moveToNext()){
            String id = (cursor.getString(0));
            String name = (cursor.getString(1));
            String category = (cursor.getString(2));
            String description = (cursor.getString(3));
            String quantity = (cursor.getString(4));
            String cost = (cursor.getString(5));
            String price = (cursor.getString(6));

            rows.add(new String[]{id,name,category,description,quantity,cost,price});
        }
        db.close();

        return rows;
    }

    //////////////////////////////////////////

//    /**
//     * Constructor de la clase
//     * @param tableLayout
//     * @param context
//     */
//    public TableDynamic(TableLayout tableLayout, Context context) {
//        this.tableLayout=tableLayout;
//        this.context=context;
//    }

    /**
     * Metodo para agregar el encabezado de la tabla
     * @param header
     */
    public void addHeader(String[] header){
        this.header=header;
        createHeader();
    }

    /**
     * Metodo para agregar la data de la tabla
     * @param data
     */
    public void addData(ArrayList<String[]>data){
        this.data=data;
        createDataTable();
    }

    /**
     * Metodo para agregar una fila de la tabla
     */
    private void newRow(){
        tableRow = new TableRow(getContext());

    }

    /**
     * Metodo para agregar una celda en una fila de la tabla
     */
    private void newCell(){
        txtCell = new TextView(getContext());
        txtCell.setPadding(2,2,2,2);
        txtCell.setTextSize(12);
    }

    /**
     * Metodo para llenar el encabezado de la tabla
     */
    private void createHeader(){
        indexCell=0;
        newRow();
        while (indexCell<header.length){
            newCell();
            txtCell.setText(header[indexCell++]);
            txtCell.setTypeface(null, Typeface.BOLD);
            tableRow.addView(txtCell, newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }

    private void createDataTable(){
        //tableLayout.removeAllViews();
        String info;
        for (indexRow=1;indexRow<=data.size();indexRow++){
            newRow();
            for (indexCell=0;indexCell<header.length;indexCell++){
                newCell();
                String[] row = data.get(indexRow-1);
                info=(indexCell<row.length)?row[indexCell]:"";
                txtCell.setText(info);
                if (indexCell==0){
                    final int ids = Integer.parseInt(info);
                    tableRow.setId(ids);
                    tableRow.setClickable(true);
                    final String n = info;
                    tableRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "SKU producto: "+ n, Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle(); //Creamos el bundle para transportar al objeto
                            bundle.putString("object", n); //Pasamos al bundle el objeto especifico
                            findNavController(view).navigate(R.id.action_inventoryFragment_to_detailProductFragment, bundle);
                        }
                    });
                }
                tableRow.addView(txtCell, newTableRowParams());
                if (indexCell==4 || indexCell==5 || indexCell==6){
                    txtCell.setGravity(Gravity.CENTER);
                }
            }
            tableLayout.addView(tableRow);
        }
    }

    /**
     * Metodo para agregar parametros al tableLayout --> divisiones
     * @return
     */
    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(2,2,2,2);
        params.weight=1;
        return params;
    }

//    @Override
//    public void onClick(View v) {
//        int idView = v.getId();
//        Toast.makeText(getContext(), "SKU producto: "+ idView, Toast.LENGTH_SHORT).show();
//        //navController.navigate(R.id.action_navigation_home_to_reportsFragment);
//        ReportsFragment miFragment = new ReportsFragment();
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.nav_host_fragment, miFragment).addToBackStack(null).commit();
//    }
}
