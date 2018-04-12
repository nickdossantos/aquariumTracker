package com.dossantos.aquariumtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button settingsBtn, livestockBtn, logBtn, graphs;
    Tank tank = Tank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsBtn = (Button) findViewById(R.id.settingsBtn);
        livestockBtn = (Button) findViewById(R.id.livestockBtn);
        logBtn = (Button) findViewById(R.id.logBtn);
        graphs = (Button) findViewById(R.id.graphsBtn);

        checkTank();
        checkReadings();
        checkLiveStock();

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogActivity.class);
                startActivity(intent);
            }
        });
        livestockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LivestockActivity.class);
                startActivity(intent);
            }
        });
        graphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickGraphActivity.class);
                startActivity(intent);
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Used in Tank.Class
     * Used to call change Screen because Tank.class can not start a intent
     *If there is no tank in teh DB This function will receive False from tank
     * @param x
     */
    public void validateTank(boolean x ){
        if(x == false){
        changeScreen();
        }
    }

    /**
     * This will change the the Screen to AddTank if there is no Tank made yet.
     */
    private void changeScreen(){
        Intent intent = new Intent(getApplicationContext(), AddTankActivity.class);
        startActivity(intent);
    }

    /**
     * Checks to see if Tank has a child named "name"
     * If it is true this function will get the attributes
     * If not the function will go to another class to get them,
     */
    public void checkTank(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Tank");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("name")){
                    tank.getTank();
                }else{
                    Intent intent = new Intent(getApplicationContext(), AddTankActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Checks to see if the DB has a Child called Readings.
     * If so we know the DB is populated
     * If not we don't need to get Values from the DB
     */
    public void checkReadings(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Readings")){
                    tank.getReadingMapFromDB();
                }else{
                    System.out.println("THERE ARE ALREADY READINGS");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Checks to see if there are any Values in the "LiveStock" subclass
     * If the BD has it, it will get the value
     * If not it wont get the data
     */
    public void checkLiveStock(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final Controller controller = new Controller();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("LiveStock")){
                    controller.populateLiveStockArray();
                }else{
                    System.out.println("THERE ARE ALREADY READINGS");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}