package com.kajoba.app.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.kajoba.app.R;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class TableDynamic {

    private TableLayout tableLayout;
    private Context context;
    private String[] header;
    private ArrayList<String[]>data;
    private TableRow tableRow;
    private TextView txtCell;
    private int indexCell;
    private int indexRow;

    /**
     * Constructor de la clase
     * @param tableLayout
     * @param context
     */
    public TableDynamic(TableLayout tableLayout, Context context) {
        this.tableLayout=tableLayout;
        this.context=context;
    }

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
        tableRow = new TableRow(context);
    }

    /**
     * Metodo para agregar una celda en una fila de la tabla
     */
    private void newCell(){
        txtCell = new TextView(context);
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
            txtCell.setTypeface(null,Typeface.BOLD);
            tableRow.addView(txtCell, newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }

    private void createDataTable(){
        String info;
        for (indexRow=1;indexRow<=data.size();indexRow++){
            newRow();
            for (indexCell=0;indexCell<header.length;indexCell++){
                newCell();
                String[] row = data.get(indexRow-1);
                info=(indexCell<row.length)?row[indexCell]:"";
                txtCell.setText(info);
                if (indexCell==0){
                    tableRow.setClickable(true);
                    final String n = info;
                    tableRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "SKU producto: "+ n, Toast.LENGTH_SHORT).show();

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


}
