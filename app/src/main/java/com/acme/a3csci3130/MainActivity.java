package com.acme.a3csci3130;

/**
 * This app does CRUD on a firebase db
 *
 *
 *
 *
 * @author  Keith MacInnis
 * @since   2018-06-30
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {


    private ListView contactListView;
    private FirebaseListAdapter<Contact> firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the app wide shared variables
        MyApplicationData appData = (MyApplicationData)getApplication();

        //Set-up Firebase
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("companies");

        //Get the reference to the UI contents
        contactListView = (ListView) findViewById(R.id.listView);

        //Set up the List View
       firebaseAdapter = new FirebaseListAdapter<Contact>(this, Contact.class,
                android.R.layout.simple_list_item_1, appData.firebaseReference) {
            @Override
            protected void populateView(View v, Contact model, int position) {
                TextView contactName = (TextView)v.findViewById(android.R.id.text1);
                contactName.setText(model.name);
            }
        };
        contactListView.setAdapter(firebaseAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // onItemClick method is called everytime a user clicks an item on the list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact person = (Contact) firebaseAdapter.getItem(position);
                showDetailView(person);
            }
        });
    }

    public void createContactButton(View v)
    {
        Intent intent=new Intent(this, CreateContactAcitivity.class);
        //intent.putExtra("reponseCode","");
        startActivityForResult(intent,70);
    }

    private void showDetailView(Contact person)
    {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("Contact", person);
        startActivityForResult(intent,71);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to   //(Google Docs)
        if (requestCode == 70 || requestCode == 71  ) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), data.getStringExtra("response"), Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }
        }
    }
}
