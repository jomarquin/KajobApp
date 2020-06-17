package com.kajoba.app.Fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.kajoba.app.ConnectionSQLiteHelper;
import com.kajoba.app.Entities.Product;
import com.kajoba.app.R;
import com.kajoba.app.Utilities.Utilities;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProductFragment extends Fragment {

    private TextInputLayout inputName, inputPhone;
    private TextView txtSku, txtName, txtCategory, txtDescription, txtQuantity, txtCost, txtPrice, txtPriceInventory;
    private RelativeLayout layoutUpdate;
    private Button btnEdit, btnUpdate, btnCancel;
    private ImageButton btnAdd, btnDelete;
    private Product product;
    private ConnectionSQLiteHelper conn;
    private String sku;

    public DetailProductFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViews(view);
        conn = new ConnectionSQLiteHelper(getContext(), "DB_KAJOBAPP", null, 1);

        loadInformation();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProduct();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelUpdate();
            }
        });
//        txtBirthdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog();
//            }
//        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] opciones={"Aceptar","Cancelar"};
                final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
                alertOpciones.setTitle("¿Desea agregar un nuevo Producto?");
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (opciones[i].equals("Aceptar")){
//                            SQLiteDatabase db = conn.getWritableDatabase();
//                            String[] parameters = {product.getId()};
//                            db.delete(Utilities.TABLE_PRODUCT, Utilities.FIELD_PRODUCT_ID+"=?", parameters);
                            cleanFields();
                            btnEdit.setEnabled(false);
                            btnAdd.setEnabled(false);
                            btnDelete.setEnabled(false);
                            //db.close();
                            //Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_LONG).show();
                        }else{
                            dialogInterface.dismiss();
                        }
                    }
                });
                alertOpciones.show();
            }
        });
        txtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String cant = txtQuantity.getText().toString();
                if (TextUtils.isEmpty(cant)) {

                } else {
                    int totalInventory = Integer.parseInt(txtPrice.getText().toString())*Integer.parseInt(txtQuantity.getText().toString());
                    txtPriceInventory.setText(String.valueOf(totalInventory));
                }
            }
        });
        txtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String price = txtPrice.getText().toString();
                if (TextUtils.isEmpty(price)) {

                } else {
                    int costProduct = (Integer.parseInt(txtPrice.getText().toString())*70)/100;
                    txtCost.setText(String.valueOf(costProduct));
                    int totalInventory = Integer.parseInt(txtPrice.getText().toString())*Integer.parseInt(txtQuantity.getText().toString());
                    txtPriceInventory.setText(String.valueOf(totalInventory));
                }

            }
        });
    }

    private void deleteProduct() {
        final CharSequence[] opciones={"Aceptar","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("¿Desea eliminar este Producto?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Aceptar")){
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parameters = {product.getId()};
                    db.delete(Utilities.TABLE_PRODUCT, Utilities.FIELD_PRODUCT_ID+"=?", parameters);
                    cleanFields();
                    btnEdit.setEnabled(false);
                    btnAdd.setEnabled(false);
                    btnDelete.setEnabled(false);
                    db.close();
                    Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_LONG).show();
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }


    private void editProduct() {
        btnEdit.setVisibility(View.GONE);
        layoutUpdate.setVisibility(View.VISIBLE);
        setFocusableViewsTrue();
    }

    private void cancelUpdate() {
        btnEdit.setVisibility(View.VISIBLE);
        layoutUpdate.setVisibility(View.GONE);
        setFocusableViewsFalse();
    }

    private void update() {

        // Validates that the form has all the required fields
        if (!validateForm()) {
            //progressDialog.dismiss();
            return;
        }
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        String[] parameters = {product.getId()};
        values.put(Utilities.FIELD_PRODUCT_ID, txtSku.getText().toString());
        values.put(Utilities.FIELD_PRODUCT_NAME, txtName.getText().toString());
        values.put(Utilities.FIELD_PRODUCT_CATEGORY, txtCategory.getText().toString());
        values.put(Utilities.FIELD_PRODUCT_DESCRIPTION, txtDescription.getText().toString());
        values.put(Utilities.FIELD_PRODUCT_QUANTITY, txtQuantity.getText().toString());
        values.put(Utilities.FIELD_PRODUCT_COST, txtCost.getText().toString());
        values.put(Utilities.FIELD_PRODUCT_PRICE, txtPrice.getText().toString());

        db.update(Utilities.TABLE_PRODUCT, values, Utilities.FIELD_PRODUCT_ID+"=?", parameters);
        Toast.makeText(getContext(), "Producto actualizado", Toast.LENGTH_LONG).show();
        db.close();

        btnEdit.setVisibility(View.VISIBLE);
        layoutUpdate.setVisibility(View.GONE);
        setFocusableViewsFalse();
    }

    private void setFocusableViewsTrue() {
        txtSku.setFocusableInTouchMode(true);
        txtName.setFocusableInTouchMode(true);
        txtCategory.setFocusableInTouchMode(true);
        txtDescription.setFocusableInTouchMode(true);
        txtQuantity.setFocusableInTouchMode(true);
        //txtCost.setFocusableInTouchMode(true);
        txtPrice.setFocusableInTouchMode(true);
        //txtBirthdate.setEnabled(true);
    }
    private void setFocusableViewsFalse() {
        txtSku.setFocusable(false);
        txtName.setFocusable(false);
        txtCategory.setFocusable(false);
        txtDescription.setFocusable(false);
        txtQuantity.setFocusable(false);
        txtCost.setFocusable(false);
        txtPrice.setFocusable(false);
        txtPriceInventory.setFocusable(false);
//        txtBirthdate.setFocusable(false);
//        txtBirthdate.setEnabled(false);
//        txtBirthdate.setTextColor(getResources().getColor(R.color.colorBlack));
    }

//    private void showDatePickerDialog() {
//        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                // +1 because January is zero
//                String selectedDate;
//                String sDay, sMonth;
//                if (day<=9){sDay = "0"+day;} else {sDay = String.valueOf(day);}
//                if (month<9){sMonth = "0"+(month+1);} else {sMonth = String.valueOf(month+1);}
//                selectedDate = year + "-" + sMonth + "-" + sDay;
//                txtBirthdate.setText(selectedDate);
//            }
//        });
//        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
//    }

    private void cleanFields() {
        txtSku.setText("");
        txtName.setText("");
        txtCategory.setText("");
        txtDescription.setText("");
        txtQuantity.setText("");
        txtCost.setText("");
        txtPrice.setText("");
        txtPriceInventory.setText("");
    }

    private void loadInformation() {
        Bundle objectFound=getArguments();

        if (objectFound != null){
            sku = objectFound.getString("object");
            SQLiteDatabase db=conn.getReadableDatabase();
            String[] parameters={sku};

            try {
                //select nombre,telefono from usuario where codigo=?
                Cursor cursor=db.rawQuery("SELECT * FROM "+Utilities.TABLE_PRODUCT+" WHERE "+Utilities.FIELD_PRODUCT_ID+"=? ",parameters);

                product = new Product();
                cursor.moveToFirst();
                txtSku.setText(cursor.getString(0));
                product.setId(cursor.getString(0));
                txtName.setText(cursor.getString(1));
                product.setName(cursor.getString(1));
                txtCategory.setText(cursor.getString(2));
                product.setCategory(cursor.getString(2));
                txtDescription.setText(cursor.getString(3));
                product.setDescription(cursor.getString(3));
                txtQuantity.setText(cursor.getString(4));
                product.setQuantity(cursor.getInt(4));
                txtCost.setText(cursor.getString(5));
                product.setCost(cursor.getInt(5));
                txtPrice.setText(cursor.getString(6));
                product.setPrice(cursor.getInt(6));
                int totalInventory = product.getQuantity()*product.getPrice();
                txtPriceInventory.setText(String.valueOf(totalInventory));

            }catch (Exception e){
                Toast.makeText(getContext(),"El producto no existe",Toast.LENGTH_SHORT).show();
                cleanFields();
            }
            db.close();
        }
        setFocusableViewsFalse();
    }

    private void setupViews(View view) {
        txtSku = view.findViewById(R.id.txtProductSku);
        txtName = view.findViewById(R.id.txtProductName);
        txtCategory = view.findViewById(R.id.txtProductCategory);
        txtDescription = view.findViewById(R.id.txtProductDescription);
        txtQuantity = view.findViewById(R.id.txtProductQuantity);
        txtCost = view.findViewById(R.id.txtProductCost);
        txtPrice = view.findViewById(R.id.txtProductPrice);
        txtPriceInventory = view.findViewById(R.id.txtProductPriceInventory);
        inputName = view.findViewById(R.id.textFieldProductName);
        inputPhone = view.findViewById(R.id.textFieldProductSku);
        layoutUpdate = view.findViewById(R.id.layoutBtnUpdateProduct);
        btnEdit = view.findViewById(R.id.btnEditProduct);
        btnCancel = view.findViewById(R.id.btnCancelUpdateProduct);
        btnUpdate = view.findViewById(R.id.btnUpdateProduct);
        btnAdd = view.findViewById(R.id.btnAddProduct);
        btnDelete = view.findViewById(R.id.btnDeleteProduct);
    }

    private boolean validateForm() {
        boolean validate = true;

        String name = txtSku.getText().toString();
        if (TextUtils.isEmpty(name)) {
            inputName.setError("El nombre es obligatorio");
            validate = false;
        } else {
            inputName.setError(null);
        }
        String phone = txtName.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            inputPhone.setError("El teléfono es obligatorio");
            validate = false;
        } else {
            inputPhone.setError(null);
        }
        return validate;
    }
}
