package com.dossantos.aquariumtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddLivestockActivity extends AppCompatActivity {

    private EditText fishET, coralET, otherET;
    Button addBtn2;
    Tank tank = Tank.getInstance();
    Controller controller = new Controller();
    LiveStock fishLiveStock = new LiveStock();
    LiveStock coralLiveStock = new LiveStock();
    LiveStock otherLiveStock = new LiveStock();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("LiveStock");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_livestock);
        getSupportActionBar().setTitle("Name: " + tank.name + "     Type: "+ tank.type);
        fishET = (EditText)findViewById(R.id.fishEditText);
        coralET = (EditText)findViewById(R.id.coralEditText);
        otherET = (EditText)findViewById(R.id.otherEditText);
        addBtn2 = (Button)findViewById(R.id.addBtnLivestock);
        if(tank.type.matches("FishOnly") || tank.type.matches("Fowlr")){
            coralET.setEnabled(false);
        }

        addBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coral,fish,other;
                coral = coralET.getText().toString();
                fish = fishET.getText().toString();
                other = otherET.getText().toString();

                if(fish.matches("")){

                } else {
                    fishLiveStock.type = "Fish: ";
                    fishLiveStock.name = fish;
                    fishLiveStock.date = fishLiveStock.addDateLiveStock();
                    controller.addLivetockToArray(fishLiveStock);
//                    database.push().setValue(fishLiveStock);
                }
                if(coral.matches("")) {

                }else{
                    coralLiveStock.type = "Coral: ";
                    coralLiveStock.name = coral;
                    coralLiveStock.date = coralLiveStock.addDateLiveStock();
                    controller.addLivetockToArray(coralLiveStock);
//                    database.push().setValue(coralLiveStock);
                }
                if(other.matches("")){

                }else{
                    otherLiveStock.type = "Other: ";
                    otherLiveStock.name =  other;
                    otherLiveStock.date = otherLiveStock.addDateLiveStock();
                    controller.addLivetockToArray(otherLiveStock);
//                    database.push().setValue(otherLiveStock);
                }
                Intent intent = new Intent(getApplicationContext(), LivestockActivity.class);
                startActivity(intent);
                controller.printLivestock();
            }
        });

    }
}
