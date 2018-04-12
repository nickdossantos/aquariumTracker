package com.dossantos.aquariumtracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DosSantos on 4/30/17.
 */

public class Reading {
    public float alkalinity,ammonia, calcium, iodine, magnesium, nitrate, nitrite, phosphate, ph, strontium, specificGravity, temperature;
    public String date;
    public static Reading temp;

    public Reading(){

    }
    /**
     * Determines the date and returns the String So it can be added to a Reading
     */
    public String addReadingDate(){
        Calendar c = Calendar.getInstance();
        //Add hour and min to get multiple readings in a day.
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy hh:mm a");
        final String formattedDate = df.format(c.getTime());
        System.out.println(formattedDate);
        return (String) formattedDate;
    }

    /**
     * temp reading is the reading the user selects so it can be graphed
     * @return
     */
    public Reading getTempReading(){
        return temp;
    }

    /**
     * this sets the selected reading(r) from PickGraphActivity to be the temp reading
     * @param r
     */
    public void setTempReading(Reading r){
        temp = r;
    }
}
