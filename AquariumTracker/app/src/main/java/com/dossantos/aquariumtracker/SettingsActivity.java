package com.dossantos.aquariumtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {
    Button makeChangesBtn, settingsResetBtn;
    EditText tankNameArea;
    CheckBox settingsFoCB, settingsFowlrCB, settingsReefCB;
    Tank tank = Tank.getInstance();
    Controller controller = new Controller();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Tank");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Name: " + tank.name + "     Type: "+ tank.type);

        makeChangesBtn = (Button)findViewById(R.id.makeChangesBtn);
        tankNameArea = (EditText)findViewById(R.id.tankNameArea);
        settingsReefCB = (CheckBox)findViewById(R.id.settingsReefCB);
        settingsFowlrCB = (CheckBox)findViewById(R.id.settingsFowlrCB);
        settingsFoCB = (CheckBox)findViewById(R.id.settingsFishOnlyCB);
        settingsResetBtn = (Button)findViewById(R.id.settingResetBtn);

        makeChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeChanges();
                Toast toast = Toast.makeText(getApplicationContext(), "Your Changes Have Been Saved", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class );
                startActivity(intent);
            }
        });
        
        settingsReefCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsFoCB.setEnabled(false);
                settingsFowlrCB.setEnabled(false);
            }
        });
        settingsFoCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsReefCB.setEnabled(false);
                settingsFowlrCB.setEnabled(false);
            }
        });
        settingsFowlrCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsReefCB.setEnabled(false);
                settingsFoCB.setEnabled(false);
            }
        });
        settingsResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsReefCB.setEnabled(true);
                settingsReefCB.setChecked(false);
                settingsFoCB.setEnabled(true);
                settingsFoCB.setChecked(false);
                settingsFowlrCB.setEnabled(true);
                settingsFowlrCB.setChecked(false);
            }
        });


    }

    /**
     * Makes the changes specified by the user
     * Validates button state and checks the Name input
     */
    public void makeChanges(){
        String changedName = tankNameArea.getText().toString();
        if(changedName.matches("")){
            if(settingsReefCB.isChecked() == true){
                tank.type = "Reef";
                database.child("type").setValue("Reef");

            }else if(settingsFoCB.isChecked() == true){
                tank.type = "FishOnly";
                database.child("type").setValue("FishOnly");
            }
            else if(settingsFowlrCB.isChecked() == true){
                tank.type = "Fowlr";
                database.child("type").setValue("Fowlr");
            }
        } else {
            if (settingsReefCB.isChecked() == true) {
                controller.makeTank(changedName, "Reef");
                database.child("name").setValue(changedName);
                database.child("type").setValue("Reef");
            } else if (settingsFoCB.isChecked() == true) {
                controller.makeTank(changedName, "FishOnly");
                database.child("name").setValue(changedName);
                database.child("type").setValue("FishOnly");
            } else if (settingsFowlrCB.isChecked() == true) {
                controller.makeTank(changedName, "Fowlr");
                database.child("name").setValue(changedName);
                database.child("type").setValue("Fowlr");
            } else {
                tank.name = changedName;
                database.child("name").setValue(changedName);
            }
        }
    }

}
