package com.dossantos.aquariumtracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DosSantos on 5/1/17.
 */

public class LiveStock {
    public String type; //Fish Coral or other
    public String name;
    public String date;

    public LiveStock(){

    }
    //define a tostring method returns a string
    public String toString(){
        return this.date + this.type + this.name;
    }

    /**
     * When used returns the current date
     * @return
     */
    public String addDateLiveStock(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy ");
        final String formattedDate = df.format(c.getTime());
        System.out.println(formattedDate);
        return (String) formattedDate;
    }

}
