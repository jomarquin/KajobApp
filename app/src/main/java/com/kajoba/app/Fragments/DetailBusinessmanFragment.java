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
import com.kajoba.app.Entities.Businessman;
import com.kajoba.app.R;
import com.kajoba.app.Utilities.Utilities;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBusinessmanFragment extends Fragment {

    private TextInputLayout inputName, inputIdentification, inputCodemp, inputPhone;
    private TextView txtName, txtIdentification, txtCodemp, txtPass, txtPhone, txtEmail, txtPassEmail, txtLocation, txtBirthdate;
    private RelativeLayout layoutUpdate;
    private Button btnEdit, btnUpdate, btnCancel;
    private ImageButton btnCall, btnDelete;
    private Businessman businessman;
    private ConnectionSQLiteHelper conn;

    public DetailBusinessmanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_businessman, container, false);
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
                deleteBusinessman();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBusinessman();
            }
        });
    }
    //Methods
    private void callBusinessman() {
    }


    private void deleteBusinessman() {
        final CharSequence[] opciones={"Aceptar","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("¿Desea eliminar este empresario?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Aceptar")){
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parameters = {businessman.getId().toString()};
                    db.delete(Utilities.TABLE_BUSINESSMAN, Utilities.FIELD_BUSINESSMAN_ID+"=?", parameters);
                    cleanFields();
                    btnEdit.setEnabled(false);
                    btnCall.setEnabled(false);
                    btnDelete.setEnabled(false);
                    db.close();
                    Toast.makeText(getContext(), "Empresario eliminado", Toast.LENGTH_LONG).show();
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
        String[] parameters = {businessman.getId().toString()};
        values.put(Utilities.FIELD_BUSINESSMAN_NAME, txtName.getText().toString());
        values.put(Utilities.FIELD_BUSINESSMAN_IDENTIFICATION, txtIdentification.getText().toString());
        values.put(Utilities.FIELD_BUSINESSMAN_CODBUSINESS, txtCodemp.getText().toString());
        values.put(Utilities.FIELD_BUSINESSMAN_PASSBUSINESS, txtPass.getText().toString());
        values.put(Utilities.FIELD_BUSINESSMAN_PHONE, txtPhone.getText().toString());
        values.put(Utilities.FIELD_BUSINESSMAN_EMAIL, txtEmail.getText().toString());
        values.put(Utilities.FIELD_BUSINESSMAN_PASSEMAIL, txtPassEmail.getText().toString());
        values.put(Utilities.FIELD_BUSINESSMAN_LOCATION, txtLocation.getText().toString());
        values.put(Utilities.FIELD_BUSINESSMAN_BIRTHDATE, txtBirthdate.getText().toString());
        db.update(Utilities.TABLE_BUSINESSMAN, values, Utilities.FIELD_BUSINESSMAN_ID+"=?", parameters);
        Toast.makeText(getContext(), "Empresario actualizado", Toast.LENGTH_LONG).show();
        db.close();

        btnEdit.setVisibility(View.VISIBLE);
        layoutUpdate.setVisibility(View.GONE);
        setFocusableViewsFalse();
    }

    private void setFocusableViewsTrue() {
        txtName.setFocusableInTouchMode(true);
        txtIdentification.setFocusableInTouchMode(true);
        txtCodemp.setFocusableInTouchMode(true);
        txtPass.setFocusableInTouchMode(true);
        txtPhone.setFocusableInTouchMode(true);
        txtEmail.setFocusableInTouchMode(true);
        txtPassEmail.setFocusableInTouchMode(true);
        txtLocation.setFocusableInTouchMode(true);
        txtBirthdate.setEnabled(true);
    }
    private void setFocusableViewsFalse() {
        txtName.setFocusable(false);
        txtIdentification.setFocusable(false);
        txtCodemp.setFocusable(false);
        txtPass.setFocusable(false);
        txtPhone.setFocusable(false);
        txtEmail.setFocusable(false);
        txtPassEmail.setFocusable(false);
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
        txtCodemp.setText("");
        txtPass.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtPassEmail.setText("");
        txtLocation.setText("");
        txtBirthdate.setText("");
    }

    private void loadInformation() {
        Bundle objectFound=getArguments();
        //Client client;
        if (objectFound != null){
            businessman = (Businessman) objectFound.getSerializable("object");

            //Llenamos los campos del detalle con la información del objeto traido desde la lista
            assert businessman != null;
            txtName.setText(businessman.getName());
            txtIdentification.setText(businessman.getIdentification());
            txtCodemp.setText(businessman.getCodBusiness());
            txtPass.setText(businessman.getPassBusiness());
            txtPhone.setText(businessman.getPhone());
            txtEmail.setText(businessman.getEmail());
            txtPassEmail.setText(businessman.getPassEmail());
            txtLocation.setText(businessman.getLocation());
            txtBirthdate.setText(businessman.getBirthDate());
        }
        setFocusableViewsFalse();
    }

    private void setupViews(View view) {
        txtName = view.findViewById(R.id.infoBusinessmanName);
        txtIdentification = view.findViewById(R.id.infoBusinessmanIdentification);
        txtCodemp = view.findViewById(R.id.infoBusinessmanCod);
        txtPass = view.findViewById(R.id.infoBusinessmanPassCod);
        txtPhone = view.findViewById(R.id.infoBusinessmanPhone);
        txtEmail = view.findViewById(R.id.infoBusinessmanEmail);
        txtPassEmail = view.findViewById(R.id.infoBusinessmanPassEmail);
        txtLocation = view.findViewById(R.id.infoBusinessmanLocation);
        txtBirthdate = view.findViewById(R.id.infoBusinessmanBirth);
        inputName = view.findViewById(R.id.textFieldInfoBusinessmanName);
        inputIdentification = view.findViewById(R.id.textFieldInfoBusinessmanIdentification);
        inputCodemp = view.findViewById(R.id.textFieldInfoBusinessmanCod);
        inputPhone = view.findViewById(R.id.textFieldInfoBusinessmanPhone);
        layoutUpdate = view.findViewById(R.id.layoutBtnUpdateBusiness);
        btnEdit = view.findViewById(R.id.btnEditBusinessman);
        btnCancel = view.findViewById(R.id.btnCancelUpdateBusiness);
        btnUpdate = view.findViewById(R.id.btnUpdateBusiness);
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
        String identification = txtIdentification.getText().toString();
        if (TextUtils.isEmpty(identification)) {
            inputIdentification.setError("El número de cédula es obligatorio");
            validate = false;
        } else {
            inputIdentification.setError(null);
        }
        String phone = txtPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            inputPhone.setError("El teléfono es obligatorio");
            validate = false;
        } else {
            inputPhone.setError(null);
        }
        String codEmp = txtCodemp.getText().toString();
        if (TextUtils.isEmpty(codEmp)) {
            inputCodemp.setError("El nombre es obligatorio");
            validate = false;
        } else {
            inputCodemp.setError(null);
        }
        return validate;
    }
}
