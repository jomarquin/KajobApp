package com.kajoba.app;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kajoba.app.Utilities.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    ConnectionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        conn = new ConnectionSQLiteHelper(this, "DB_KAJOBAPP", null, 1);

        createProducts();
    }

    /**
     * Method to activate arrow-back into toolbar
     * @return Boolean True
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Este método se encarga de llenar la tabla 'products' con la información inicial
     */
    private void createProducts(){
        if (countRecords()==0){
            String[] text= readFileProducts();
            SQLiteDatabase db = conn.getWritableDatabase();
            db.beginTransaction();

            for (int i=0;i<text.length;i++){
                String[] line = text[i].split(";");
                ContentValues values = new ContentValues();
                values.put(Utilities.FIELD_PRODUCT_ID, line[0]);
                values.put(Utilities.FIELD_PRODUCT_NAME, line[1]);
                values.put(Utilities.FIELD_PRODUCT_CATEGORY, line[2]);
                values.put(Utilities.FIELD_PRODUCT_DESCRIPTION, line[3]);
                values.put(Utilities.FIELD_PRODUCT_QUANTITY, line[4]);
                values.put(Utilities.FIELD_PRODUCT_COST, line[5]);
                values.put(Utilities.FIELD_PRODUCT_PRICE, line[6]);
                db.insert(Utilities.TABLE_PRODUCT, null, values);
            }
            Toast.makeText(getApplicationContext(), "Productos registrados: "+text.length, Toast.LENGTH_SHORT).show();
            db.setTransactionSuccessful();
            db.endTransaction();
        }else{
            //Toast.makeText(getApplicationContext(), "Base de datos actualizada!!", Toast.LENGTH_SHORT).show();
        }
    }//Fin Init

    /**
     * Este método se encarga de contar cuantos registros hay en la tabla 'products' para determinar si se debe cargar
     * la información inicial
     * @return retorna un long con la cantidad de registros que tiene la tabla
     */
    private long countRecords(){
        //ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this, "DB_KAJOBAPP", null, 1 );
        SQLiteDatabase db = conn.getReadableDatabase();
        long cn = DatabaseUtils.queryNumEntries(db, Utilities.TABLE_PRODUCT);
        db.close();
        return cn;
    }

    /**
     * Este método lee el archivo donde esta la información a cargar en la tabla 'products'
     * @return Retorna un arreglo string con la información inicial de productos
     */
    private String[] readFileProducts(){
        InputStream inputStream = getResources().openRawResource(R.raw.products);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while (i != -1){
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");
    }

}
