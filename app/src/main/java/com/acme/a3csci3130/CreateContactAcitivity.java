package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateContactAcitivity extends Activity {

    private Button submitButton;
    private EditText nameField, emailField, businessNumberField;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        emailField = (EditText) findViewById(R.id.email);
        //businessNumberField = (EditText) findViewById(R.id.bus);
      //  emailField = (EditText) findViewById(R.id.email);

    }

    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String personID = appState.firebaseReference.push().getKey();
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String primaryBusiness = nameField.getText().toString();
        String address = emailField.getText().toString();
        String province = nameField.getText().toString();
        String businessNumber = emailField.getText().toString();
        Contact person = new Contact(personID, name, primaryBusiness, address, province, businessNumber );

        appState.firebaseReference.child(personID).setValue(person);

        finish();

    }
}
