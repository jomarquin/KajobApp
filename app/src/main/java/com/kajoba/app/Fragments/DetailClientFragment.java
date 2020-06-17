package com.kajoba.app.Fragments;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.kajoba.app.ConnectionSQLiteHelper;
import com.kajoba.app.Entities.Client;
import com.kajoba.app.Fragments.DatePickerFragment;
import com.kajoba.app.R;
import com.kajoba.app.Utilities.Utilities;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailClientFragment extends Fragment {

    private TextInputLayout inputName, inputPhone;
    private TextView txtName, txtIdentification, txtPhone, txtEmail, txtLocation, txtBirthdate;
    private RelativeLayout layoutUpdate;
    private Button btnEdit, btnUpdate, btnCancel;
    private ImageButton btnCall, btnDelete;
    private Client client;
    private ConnectionSQLiteHelper conn;

    public DetailClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_client, container, false);
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
                editInfoClient();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfoClient();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelUpdateClient();
            }
        });
        txtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClient();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callClient();
            }
        });
    }

    //Methods
    private void callClient() {
    }


    private void deleteClient() {
        final CharSequence[] opciones={"Aceptar","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("¿Desea eliminar este cliente?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Aceptar")){
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parameters = {client.getId().toString()};
                    db.delete(Utilities.TABLE_CLIENT, Utilities.FIELD_CLIENT_ID+"=?", parameters);
                    cleanFields();
                    btnEdit.setEnabled(false);
                    btnCall.setEnabled(false);
                    btnDelete.setEnabled(false);
                    db.close();
                    Toast.makeText(getContext(), "Cliente eliminado", Toast.LENGTH_LONG).show();
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }


    private void editInfoClient() {
        btnEdit.setVisibility(View.GONE);
        layoutUpdate.setVisibility(View.VISIBLE);
        setFocusableViewsTrue();
    }

    private void cancelUpdateClient() {
        btnEdit.setVisibility(View.VISIBLE);
        layoutUpdate.setVisibility(View.GONE);
        setFocusableViewsFalse();
    }

    private void updateInfoClient() {

        // Validates that the form has all the required fields
        if (!validateForm()) {
            //progressDialog.dismiss();
            return;
        }
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        String[] parameters = {client.getId().toString()};
        values.put(Utilities.FIELD_CLIENT_NAME, txtName.getText().toString());
        values.put(Utilities.FIELD_CLIENT_IDENTIFICATION, txtIdentification.getText().toString());
        values.put(Utilities.FIELD_CLIENT_PHONE, txtPhone.getText().toString());
        values.put(Utilities.FIELD_CLIENT_EMAIL, txtEmail.getText().toString());
        values.put(Utilities.FIELD_CLIENT_LOCATION, txtLocation.getText().toString());
        values.put(Utilities.FIELD_CLIENT_BIRTHDATE, txtBirthdate.getText().toString());
        db.update(Utilities.TABLE_CLIENT, values, Utilities.FIELD_CLIENT_ID+"=?", parameters);
        Toast.makeText(getContext(), "Cliente actualizado", Toast.LENGTH_LONG).show();
        db.close();

        btnEdit.setVisibility(View.VISIBLE);
        layoutUpdate.setVisibility(View.GONE);
        setFocusableViewsFalse();
    }

    private void setFocusableViewsTrue() {
        txtName.setFocusableInTouchMode(true);
        txtIdentification.setFocusableInTouchMode(true);
        txtPhone.setFocusableInTouchMode(true);
        txtEmail.setFocusableInTouchMode(true);
        txtLocation.setFocusableInTouchMode(true);
        txtBirthdate.setEnabled(true);
    }
    private void setFocusableViewsFalse() {
        txtName.setFocusable(false);
        txtIdentification.setFocusable(false);
        txtPhone.setFocusable(false);
        txtEmail.setFocusable(false);
        txtLocation.setFocusable(false);
        txtBirthdate.setFocusable(false);
        txtBirthdate.setEnabled(false);
        txtBirthdate.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                String selectedDate;
                String sDay, sMonth;
                if (day<=9){sDay = "0"+day;} else {sDay = String.valueOf(day);}
                if (month<9){sMonth = "0"+(month+1);} else {sMonth = String.valueOf(month+1);}
                selectedDate = year + "-" + sMonth + "-" + sDay;
                txtBirthdate.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void cleanFields() {
        txtName.setText("");
        txtIdentification.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtLocation.setText("");
        txtBirthdate.setText("");
    }

    private void loadInformation() {
        Bundle objectFound=getArguments();
        //Client client;
        if (objectFound != null){
            client = (Client) objectFound.getSerializable("object");

            //Llenamos los campos del detalle con la información del objeto traido desde la lista de mascotas encontradas
            assert client != null;
            txtName.setText(client.getName());
            txtIdentification.setText(client.getIdentification());
            txtPhone.setText(client.getPhone());
            txtEmail.setText(client.getEmail());
            txtLocation.setText(client.getLocation());
            txtBirthdate.setText(client.getBirthDate());
        }
        setFocusableViewsFalse();
    }

    private void setupViews(View view) {
        txtName = view.findViewById(R.id.infoClientName);
        txtIdentification = view.findViewById(R.id.infoClientIdentification);
        txtPhone = view.findViewById(R.id.infoClientPhone);
        txtEmail = view.findViewById(R.id.infoClientEmail);
        txtLocation = view.findViewById(R.id.infoClientLocation);
        txtBirthdate = view.findViewById(R.id.infoClientBirthdate);
        inputName = view.findViewById(R.id.textFieldInfoClientName);
        inputPhone = view.findViewById(R.id.textFieldInfoClientPhone);
        layoutUpdate = view.findViewById(R.id.layoutBtnUpdate);
        btnEdit = view.findViewById(R.id.btnEditClient);
        btnCancel = view.findViewById(R.id.btnCancelUpdateClient);
        btnUpdate = view.findViewById(R.id.btnUpdateClient);
        btnCall = view.findViewById(R.id.btnCallClient);
        btnDelete = view.findViewById(R.id.btnDeleteClient);
    }

    private boolean validateForm() {
        boolean validate = true;

        String name = txtName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            inputName.setError("El nombre es obligatorio");
            validate = false;
        } else {
            inputName.setError(null);
        }
        String phone = txtPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            inputPhone.setError("El teléfono es obligatorio");
            validate = false;
        } else {
            inputPhone.setError(null);
        }
        return validate;
    }
}
