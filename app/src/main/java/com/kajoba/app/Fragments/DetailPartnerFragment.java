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
import com.kajoba.app.Entities.Partner;
import com.kajoba.app.R;
import com.kajoba.app.Utilities.Utilities;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPartnerFragment extends Fragment {

    private TextInputLayout inputName, inputPhone;
    private TextView txtName, txtIdentification, txtPhone, txtEmail, txtLocation, txtBirthdate;
    private RelativeLayout layoutUpdate;
    private Button btnEdit, btnUpdate, btnCancel;
    private ImageButton btnCall, btnDelete;
    private Partner partner;
    private ConnectionSQLiteHelper conn;

    public DetailPartnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_partner, container, false);
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
                editInfo();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelUpdate();
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
                deletePartner();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPartner();
            }
        });
    }
    //Methods

    private void callPartner() {
    }


    private void deletePartner() {
        final CharSequence[] opciones={"Aceptar","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("¿Desea eliminar este socio?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Aceptar")){
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parameters = {partner.getId().toString()};
                    db.delete(Utilities.TABLE_PARTNER, Utilities.FIELD_PARTNER_ID+"=?", parameters);
                    cleanFields();
                    btnEdit.setEnabled(false);
                    btnCall.setEnabled(false);
                    btnDelete.setEnabled(false);
                    db.close();
                    Toast.makeText(getContext(), "Socio eliminado", Toast.LENGTH_LONG).show();
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();

    }

    private void editInfo() {
        btnEdit.setVisibility(View.GONE);
        layoutUpdate.setVisibility(View.VISIBLE);
        setFocusableViewsTrue();
    }

    private void cancelUpdate() {
        btnEdit.setVisibility(View.VISIBLE);
        layoutUpdate.setVisibility(View.GONE);
        setFocusableViewsFalse();
    }

    private void updateInfo() {

        // Validates that the form has all the required fields
        if (!validateForm()) {
            //progressDialog.dismiss();
            return;
        }

        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        String[] parameters = {partner.getId().toString()};
        values.put(Utilities.FIELD_PARTNER_NAME, txtName.getText().toString());
        values.put(Utilities.FIELD_PARTNER_IDENTIFICATION, txtIdentification.getText().toString());
        values.put(Utilities.FIELD_PARTNER_PHONE, txtPhone.getText().toString());
        values.put(Utilities.FIELD_PARTNER_EMAIL, txtEmail.getText().toString());
        values.put(Utilities.FIELD_PARTNER_LOCATION, txtLocation.getText().toString());
        values.put(Utilities.FIELD_PARTNER_BIRTHDATE, txtBirthdate.getText().toString());
        db.update(Utilities.TABLE_PARTNER, values, Utilities.FIELD_PARTNER_ID+"=?", parameters);
        Toast.makeText(getContext(), "Socio actualizado", Toast.LENGTH_LONG).show();
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
            partner = (Partner) objectFound.getSerializable("object");

            //Llenamos los campos del detalle con la información del objeto traido desde la lista de mascotas encontradas
            assert partner != null;
            txtName.setText(partner.getName());
            txtIdentification.setText(partner.getIdentification());
            txtPhone.setText(partner.getPhone());
            txtEmail.setText(partner.getEmail());
            txtLocation.setText(partner.getLocation());
            txtBirthdate.setText(partner.getBirthDate());
        }
        setFocusableViewsFalse();
    }

    private void setupViews(View view) {
        txtName = view.findViewById(R.id.infoPartnerName);
        txtIdentification = view.findViewById(R.id.infoPartnerIdentification);
        txtPhone = view.findViewById(R.id.infoPartnerPhone);
        txtEmail = view.findViewById(R.id.infoPartnerEmail);
        txtLocation = view.findViewById(R.id.infoPartnerLocation);
        txtBirthdate = view.findViewById(R.id.infoPartnerBirthdate);
        inputName = view.findViewById(R.id.textFieldInfoPartnerName);
        inputPhone = view.findViewById(R.id.textFieldInfoPartnerPhone);
        layoutUpdate = view.findViewById(R.id.layoutBtnUpdatePartner);
        btnEdit = view.findViewById(R.id.btnEditPartner);
        btnCancel = view.findViewById(R.id.btnCancelUpdatePartner);
        btnUpdate = view.findViewById(R.id.btnUpdatePartner);
        btnCall = view.findViewById(R.id.btnCallPartner);
        btnDelete = view.findViewById(R.id.btnDeletePartner);
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
