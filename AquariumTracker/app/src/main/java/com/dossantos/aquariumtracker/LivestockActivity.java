package com.dossantos.aquariumtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.util.ArrayList;

public class LivestockActivity extends AppCompatActivity {
    private ArrayList<String> livestockArray;
    private ListView livestockLV;
    Tank tank = Tank.getInstance();

    Button addBtn,doneBtnLivestock;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livestock);

        getSupportActionBar().setTitle("Name: " + tank.name + "     Type: "+ tank.type);

        if(tank.liveStockArrayList.size() > 0){
            tank.sendLiveStockToDB();
            System.out.println("List was sent To DB!");
        }

        livestockArray = new ArrayList<>();
        addBtn = (Button)findViewById(R.id.addAnimalBtn);
        doneBtnLivestock = (Button)findViewById(R.id.doneBtnLiveStock);
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        livestockLV = (ListView) findViewById(R.id.liveStockListView);

        final ArrayAdapter<LiveStock> livestockArrayAdapter = new ArrayAdapter<LiveStock>(this, android.R.layout.simple_list_item_1, tank.liveStockArrayList);
        livestockLV.setAdapter(livestockArrayAdapter);
        livestockArrayAdapter.notifyDataSetChanged();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddLivestockActivity.class);
                startActivity(intent);
            }
        });
        doneBtnLivestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton.isChecked()){
                    System.out.println("I will now remove");
                    Toast toast = Toast.makeText(getApplicationContext(), "WARNING: By clicking a value in the table will remove it permanently ", Toast.LENGTH_SHORT);
                    toast.show();
                    livestockLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            tank.liveStockArrayList.remove(tank.liveStockArrayList.get(position));
                            tank.sendLiveStockToDB();
                            Intent intent = new Intent(getApplicationContext(), LivestockActivity.class); //This intent is used to refresh the page when a value is removed.
                            startActivity(intent);
                        }
                    });
                }else{
                    System.out.println("Will not remove now");
                    Toast toast = Toast.makeText(getApplicationContext(), "REMOVE has been turned off.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
