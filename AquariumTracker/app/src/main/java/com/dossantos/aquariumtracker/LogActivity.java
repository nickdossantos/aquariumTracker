package com.dossantos.aquariumtracker;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class LogActivity extends AppCompatActivity {
    private EditText alk,ammonia,calcium,iodine, magnesium, nitrite, nitrate, ph, phosphate, specificGravity, strontium, temperature;
    private Button addBtn;
    Tank tank = Tank.getInstance();
    Reading reading = new Reading();
    ArrayList<String> errorArray = new ArrayList<>(); //Hold data specific to this activity.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        getSupportActionBar().setTitle("Name: " + tank.name + "     Type: "+ tank.type);

        alk = (EditText)findViewById(R.id.alkalinityText);
        ammonia = (EditText)findViewById(R.id.ammoniaText);
        calcium = (EditText)findViewById(R.id.calciumText);
        iodine = (EditText)findViewById(R.id.iodineText);
        magnesium = (EditText)findViewById(R.id.magnesiumText);
        nitrite = (EditText)findViewById(R.id.nitriteText);
        nitrate = (EditText)findViewById(R.id.nitrateText);
        ph = (EditText)findViewById(R.id.phText);
        phosphate = (EditText)findViewById(R.id.phosphateText);
        specificGravity = (EditText)findViewById(R.id.specificgravityText);
        strontium = (EditText)findViewById(R.id.strontiumText);
        temperature = (EditText)findViewById(R.id.temperatureText);

        alk.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        ammonia.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        calcium.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        iodine.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        magnesium.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        nitrite.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        nitrate.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        ph.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        phosphate.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        specificGravity.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        strontium.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);
        temperature.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_ATOP);


        addBtn = (Button)findViewById(R.id.addBtnLog);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorArray.clear();
                getReadingVals();
                reading.date = reading.addReadingDate();
                validateReadingVals();
            }
        });

    }

    /**
     * Gets a value the user entered and checks to see if the user has filled all the EditTexts
     * Will print a error message asking the user to complete the Reading
     */
    public void getReadingVals(){
        try {
            reading.alkalinity = Float.parseFloat(alk.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Alk");
            alk.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.ammonia = Float.parseFloat(ammonia.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Ammonia");
            ammonia.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.calcium = Float.parseFloat(calcium.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Calcium");
            calcium.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.iodine = Float.parseFloat(iodine.getText().toString());
        } catch (Exception e) {
            iodine.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            errorArray.add(" Iodine");
        }
        try {
            reading.magnesium = Float.parseFloat(magnesium.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Magnesium");
            magnesium.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.nitrite = Float.parseFloat(nitrite.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Nitrite");
            nitrite.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.nitrate = Float.parseFloat(nitrate.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Nitrate");
            nitrate.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.ph = Float.parseFloat(ph.getText().toString());
        } catch (Exception e) {
            errorArray.add(" PH");
            ph.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.phosphate = Float.parseFloat(phosphate.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Phosphate");
            phosphate.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.specificGravity = Float.parseFloat(specificGravity.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Specific Gravity");
            specificGravity.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.strontium = Float.parseFloat(strontium.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Strontium");
            strontium.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            reading.temperature = Float.parseFloat(temperature.getText().toString());
        } catch (Exception e) {
            errorArray.add(" Temperature");
            temperature.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }

    }

    /**
     * Checks the size of the error array list
     * If there is no errors the data will be submitted and the screen will change.
     */
    public void validateReadingVals(){
        if (errorArray.size() == 0) {
            tank.readingsMap.put(reading.date, reading);
            Toast toast = Toast.makeText(getApplicationContext(), "Your Parameters Have Been Added", Toast.LENGTH_SHORT);
            toast.show();
            tank.sendReadingsToDB();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter the following values: " + printErrorVals(), Toast.LENGTH_LONG);
            toast.show();
            errorArray.clear();
        }
    }

    /**
     * Print the error array as a string so the user can see the errors in their input
     *
     * @return
     */
    public String printErrorVals(){
        String errorvals = "";
        for(int i = 0; i < errorArray.size(); i++){
            System.out.println(errorArray.get(i).toString());
            errorvals = errorvals + errorArray.get(i).toString();
        }
        return errorvals;
    }


}