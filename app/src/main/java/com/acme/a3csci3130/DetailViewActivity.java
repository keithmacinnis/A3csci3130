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

public class DetailViewActivity extends Activity {
    /**
     * This class is used to edit/delete a business contact . This is
     * @param appState This is used to access the firebase
     * @param receivedPersonInfo This is used to display the info on the business selected from the previous view.
     */

    private Spinner primaryBusinessField,provinceField;
    private EditText nameField, businessNumberField,addressField;
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
        primaryBusinessField = (Spinner) findViewById(R.id.primaryBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (Spinner) findViewById(R.id.province);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pronvice_array, android.R.layout.simple_spinner_item);
        provinceField.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.businesses_array, android.R.layout.simple_spinner_item);
        primaryBusinessField.setAdapter(adapter);

        if(receivedPersonInfo != null){
            nameField.setText(receivedPersonInfo.name);
            businessNumberField.setText(String.valueOf(receivedPersonInfo.businessNumber));
            primaryBusinessField.setSelection(getIndex(primaryBusinessField, receivedPersonInfo.primaryBusiness));
            addressField.setText(receivedPersonInfo.address);
            provinceField.setSelection(getIndex(provinceField,receivedPersonInfo.province));
        }
    }

    private int getIndex(Spinner spinner, String myString){
        /**
         * This method is used to find the spinner index of a province/business.
         * This is adopted from https://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position
         * @param spinner This is the spinner to be updated
         * @param myString This is the stringVariable from the db
         */

        for (int i=0;i<spinner.getCount();i++)
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString))
                return i;
        return -1;
    }
    public void updateContact(View v){

        String personID = receivedPersonInfo.uid;
        receivedPersonInfo.name= String.valueOf(nameField.getText());
        receivedPersonInfo.province= provinceField.getSelectedItem().toString();
        receivedPersonInfo.address= String.valueOf(addressField.getText());
        receivedPersonInfo.primaryBusiness= primaryBusinessField.getSelectedItem().toString();
        try {
            receivedPersonInfo.businessNumber = Integer.parseInt(String.valueOf(businessNumberField.getText()));
        } catch (NumberFormatException e){
            receivedPersonInfo.businessNumber=-1;
        }
        final Intent resesponsetIntent = new Intent();

        appState.firebaseReference.child(personID).setValue(receivedPersonInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        resesponsetIntent.putExtra("response", "Success");
                        setResult(Activity.RESULT_OK, resesponsetIntent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        resesponsetIntent.putExtra("response", e.getMessage());
                        setResult(Activity.RESULT_OK, resesponsetIntent);
                        finish();
                    }
                });
    }

    public void eraseContact(View v)
    {
        String personID = receivedPersonInfo.uid;
        final Intent resesponsetIntent = new Intent();

        appState.firebaseReference.child(personID).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        resesponsetIntent.putExtra("response", "Success");
                        setResult(Activity.RESULT_OK, resesponsetIntent);
                        finish();
                    }
                })
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
