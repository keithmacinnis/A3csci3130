package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class CreateContactAcitivity extends Activity {
    /**
     * This class is used to add create a business contact . This is
     * @param appState This is used to access the firebase
     * Error and success messages are returned to MainActivity
     */
    private Button submitButton;
    private EditText nameField, businessNumberField,primaryBusinessField,addressField,provinceField;
    private MyApplicationData appState;
   // private CoordinatorLayout corrdinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables

        appState = ((MyApplicationData) getApplicationContext());
        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        businessNumberField = (EditText) findViewById(R.id.businessNumber);
        primaryBusinessField = (EditText) findViewById(R.id.primaryBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (EditText) findViewById(R.id.province);

    }

    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String personID = appState.firebaseReference.push().getKey();
        String name = nameField.getText().toString();
        String primaryBusiness = primaryBusinessField.getText().toString();
        String address = addressField.getText().toString();
        String province = provinceField.getText().toString();
        String businessNumber = businessNumberField.getText().toString();
        Contact person = new Contact(personID, name, primaryBusiness, address, province, businessNumber );
        final Intent resesponsetIntent = new Intent();

       appState.firebaseReference.child(personID).setValue(person)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                     // Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.success_message, Snackbar.LENGTH_LONG);
                         //GREAT
                      resesponsetIntent.putExtra("response", "Success");
                      //mySnackbar.show();
                      setResult(Activity.RESULT_OK, resesponsetIntent);
                      finish();
                     }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), e.getMessage(), Snackbar.LENGTH_SHORT);
                        resesponsetIntent.putExtra("response", e.getMessage());
                        setResult(Activity.RESULT_OK, resesponsetIntent);
                        finish();
                        //mySnackbar.show();
                    }
        });

        appState.firebaseReference.child(personID).setValue(person);



    }
}
