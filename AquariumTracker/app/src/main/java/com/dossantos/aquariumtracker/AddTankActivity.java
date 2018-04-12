package com.dossantos.aquariumtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class AddTankActivity extends AppCompatActivity {
    Controller controller = new Controller();
    private Button doneBtn, resetBtn;
    private CheckBox foCheckBox, fowlrCheckBox, reefCheckBox;
    private EditText tankNameArea;
    Tank tank = Tank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tank);

        doneBtn = (Button) findViewById(R.id.doneBtnMain);
        resetBtn = (Button) findViewById(R.id.resetButton);
        foCheckBox = (CheckBox) findViewById(R.id.foCheckBox);
        fowlrCheckBox = (CheckBox) findViewById(R.id.fowlrCheckbox);
        reefCheckBox = (CheckBox) findViewById(R.id.reefTankCheckBox);
        tankNameArea = (EditText) findViewById(R.id.tankNameArea);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTankName = tankNameArea.getText().toString();
                System.out.println(newTankName);
                if (newTankName != "") {
                    if (reefCheckBox.isChecked() == true) {
                        controller.makeTank(newTankName, "Reef");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else if (foCheckBox.isChecked() == true) {
                        controller.makeTank(newTankName, "FishOnly");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else if (fowlrCheckBox.isChecked() == true) {
                        controller.makeTank(newTankName, "Fowlr");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {

                    }

                    getSupportActionBar().setTitle(tank.name);
                    tank.sycTankToDB(); // sets the name and type to the database
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        foCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reefCheckBox.setEnabled(false);
                fowlrCheckBox.setEnabled(false);
            }
        });
        fowlrCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reefCheckBox.setEnabled(false);
                foCheckBox.setEnabled(false);
            }
        });
        reefCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foCheckBox.setEnabled(false);
                fowlrCheckBox.setEnabled(false);
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foCheckBox.setEnabled(true);
                foCheckBox.setChecked(false);
                fowlrCheckBox.setEnabled(true);
                fowlrCheckBox.setChecked(false);
                reefCheckBox.setEnabled(true);
                reefCheckBox.setChecked(false);
            }
        });

    }
    public void getValue(String x, String y){

    }
}
