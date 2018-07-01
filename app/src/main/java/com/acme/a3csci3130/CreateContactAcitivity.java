package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class CreateContactAcitivity extends Activity {
    /**
     * This class is used to add create a business contact . This is
     * @param appState This is used to access the firebase
     * Error and success messages are returned to MainActivity
     */
    private Button submitButton;
    private Spinner primaryBusinessField,provinceField;
    private EditText nameField, businessNumberField,addressField;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        appState = ((MyApplicationData) getApplicationContext());
        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        businessNumberField = (EditText) findViewById(R.id.businessNumber);
        primaryBusinessField = (Spinner) findViewById(R.id.primaryBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (Spinner) findViewById(R.id.province);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pronvice_array, android.R.layout.simple_spinner_item);
        provinceField.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.businesses_array, android.R.layout.simple_spinner_item);
        primaryBusinessField.setAdapter(adapter);

    }

    public void submitInfoButton(View v) {
        String personID = appState.firebaseReference.push().getKey();
        String name = nameField.getText().toString();
        String primaryBusiness = primaryBusinessField.getSelectedItem().toString();
        String address = addressField.getText().toString();
        String province = provinceField.getSelectedItem().toString();
        int businessNumber=-1;
        try {
            businessNumber = Integer.parseInt(businessNumberField.getText().toString());
        }catch (NumberFormatException e) {}
        Contact person = new Contact(personID, name, primaryBusiness, address, province, businessNumber );
        final Intent resesponsetIntent = new Intent();

       appState.firebaseReference.child(personID).setValue(person)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      resesponsetIntent.putExtra("response", "Success");
                      setResult(Activity.RESULT_OK, resesponsetIntent);
                      finish();
                     }
        })  //
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        resesponsetIntent.putExtra("response", e.getMessage());
                        setResult(Activity.RESULT_OK, resesponsetIntent);
                        finish();
                    }
        });
    }
}
