package com.kajoba.app.Fragments;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kajoba.app.ConnectionSQLiteHelper;
import com.kajoba.app.R;
import com.kajoba.app.Utilities.Utilities;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewClientFragment extends Fragment {

    private TextInputLayout inputName, inputPhone;
    private TextInputEditText txtName, txtIdentification, txtPhone, txtEmail, txtLocation, txtBirthdate;
    private MaterialButton btnSave;

    public NewClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViews(view);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewClient();
            }
        });
        txtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

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

    private void registerNewClient() {

        // Validates that the form has all the required fields
        if (!validateForm()) {
            //progressDialog.dismiss();
            return;
        }

        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(getContext(), "DB_KAJOBAPP", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Utilities.FIELD_CLIENT_NAME, txtName.getText().toString());
        values.put(Utilities.FIELD_CLIENT_IDENTIFICATION, txtIdentification.getText().toString());
        values.put(Utilities.FIELD_CLIENT_PHONE, txtPhone.getText().toString());
        values.put(Utilities.FIELD_CLIENT_EMAIL, txtEmail.getText().toString());
        values.put(Utilities.FIELD_CLIENT_LOCATION, txtLocation.getText().toString());
        values.put(Utilities.FIELD_CLIENT_BIRTHDATE, txtBirthdate.getText().toString());

        Long nameSave=db.insert(Utilities.TABLE_CLIENT,Utilities.FIELD_CLIENT_NAME,values);
        cleanFields();
        Toast.makeText(getContext(),"Registro correcto nro: "+nameSave.toString(),Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void setupViews(View view) {
        txtName = view.findViewById(R.id.editTextNameClient);
        txtIdentification = view.findViewById(R.id.editTextIdentificationClient);
        txtPhone = view.findViewById(R.id.editTextPhoneClient);
        txtEmail = view.findViewById(R.id.editTextMailClient);
        txtLocation = view.findViewById(R.id.editTextLocationClient);
        txtBirthdate = view.findViewById(R.id.editTextBirthdateClient);
        btnSave = view.findViewById(R.id.btnRegisterClient);
        inputName = view.findViewById(R.id.textFieldNameClient);
        inputPhone = view.findViewById(R.id.textFieldPhoneClient);
    }

    private void cleanFields() {
        txtName.setText("");
        txtIdentification.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtLocation.setText("");
        txtBirthdate.setText("");
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
