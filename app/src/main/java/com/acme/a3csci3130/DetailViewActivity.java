package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailViewActivity extends Activity {
    /**
     * This class is used to edit/delete a business contact . This is
     * @param appState This is used to access the firebase
     * @param receivedPersonInfo This is used to display the info on the business selected from the previous view.
     */
   /* public String uid;
    public String businessNumber;
    public String name;
    public String primaryBusiness;
    public String address;
    public String province;
*/
    private EditText nameField, businessNumberField,primaryBusinessField,addressField,provinceField;
    Contact receivedPersonInfo;
    private MyApplicationData appState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        appState = ((MyApplicationData) getApplicationContext());
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");
        nameField = (EditText) findViewById(R.id.name);
        businessNumberField = (EditText) findViewById(R.id.businessNumber);
        primaryBusinessField = (EditText) findViewById(R.id.primaryBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (EditText) findViewById(R.id.province);

        if(receivedPersonInfo != null){
            nameField.setText(receivedPersonInfo.name);
            businessNumberField.setText(receivedPersonInfo.businessNumber);
            primaryBusinessField.setText(receivedPersonInfo.primaryBusiness);
            addressField.setText(receivedPersonInfo.address);
            provinceField.setText(receivedPersonInfo.province);
        }
    }

    public void updateContact(View v){
        //TODO: Update contact funcionality
        //mDatabase.child("users").child(userId).child("username").setValue(name);
        String personID = receivedPersonInfo.uid;
        receivedPersonInfo.name= String.valueOf(nameField.getText());
        receivedPersonInfo.province= String.valueOf(provinceField.getText());
        receivedPersonInfo.address= String.valueOf(addressField.getText());
        receivedPersonInfo.primaryBusiness= String.valueOf(primaryBusinessField.getText());
        receivedPersonInfo.businessNumber= String.valueOf(businessNumberField.getText());
/*
        receivedPersonInfo.name= nameField.getText().toString();
        receivedPersonInfo.province= provinceField.getText().toString();
        receivedPersonInfo.address= addressField.getText().toString();
        receivedPersonInfo.primaryBusiness= primaryBusinessField.getText().toString();
        receivedPersonInfo.businessNumber= businessNumberField.getText().toString();
*/
        appState.firebaseReference.child(personID).setValue(receivedPersonInfo);

        final Intent resesponsetIntent = new Intent();
        finish();

    }

    public void eraseContact(View v)
    {
        //TODO: Erase contact functionality
        String personID = receivedPersonInfo.uid;
        appState.firebaseReference.child(personID).removeValue();
        final Intent resesponsetIntent = new Intent();

        finish();
    }
}
