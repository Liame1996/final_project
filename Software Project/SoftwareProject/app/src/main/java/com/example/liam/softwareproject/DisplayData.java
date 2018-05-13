package com.example.liam.softwareproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class DisplayData extends AppCompatActivity {

    private ListView database;

    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        database = (ListView) findViewById(R.id.database);

        firebase = new Firebase("https://software-project-4ae0c.firebaseio.com/distant/-L-6pDblwvOd1eJaorR2/value");

        //DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Distant");



        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TAG", dataSnapshot.getChildren().toString());
                String value = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}