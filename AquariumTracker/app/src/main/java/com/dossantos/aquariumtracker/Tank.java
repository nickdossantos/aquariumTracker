package com.dossantos.aquariumtracker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Tank will hold Tank's type and Name
 * A hashmap of Readings, ArrayList of Livestock and a Arraylist of Best Reading based on which type of Tank it is.
 * Created by DosSantos on 4/30/17.
 */

//have the tank class hold the hashmap of reading and the array of livestock
    
public class Tank {
    private static Tank tank = new Tank();
    public static String name;
    public static String type; //fo, fowler, coral
    public static HashMap<String, Reading> readingsMap = new HashMap<>();
    public static ArrayList<LiveStock> liveStockArrayList = new ArrayList<>();
    private static ArrayList<Float>bestReadingArrayList = new ArrayList<Float>();

    private Tank(){

    }

    public static Tank getInstance(){
        return tank;
    }

    /**
     * Tankes in an element to be deleted
     * Seaches the livestockArraylist and when a match is found deletes it and returns
     * @param element
     */
    public void removeTankElement(String element) {
        for(int i = 0; i < liveStockArrayList.size(); i++){
            System.out.println("Looking at " + liveStockArrayList.get(i).name);
            System.out.println("Comparing to " + element);
            if(liveStockArrayList.get(i).name.equals(element)){
                System.out.println("Removing " + liveStockArrayList.get(i).name);
                liveStockArrayList.remove(i);
                return;
            }
        }
    }

    /**
     * Checks to see what type of tank there is and changes the static array list so when graphing parameters you will see a change
     * Reef is Different from FishOnly and Fowlr
     * And Fishonly and Fowlr are the same
     */
    public void syncBestReadying(){
        bestReadingArrayList.clear();
        if(tank.type == "Reef") {
            bestReadingArrayList.add(10.0f); //Alk DKH
            bestReadingArrayList.add(0.0f);   //Ammonia
            bestReadingArrayList.add(400f / 1000);//Calcium
            bestReadingArrayList.add(.10f);//Iodine
            bestReadingArrayList.add(1300f / 1000); //magnesium
            bestReadingArrayList.add(1.0f);//nitrate
            bestReadingArrayList.add(0.0f); //nitrite
            bestReadingArrayList.add(8.4f); //ph
            bestReadingArrayList.add(.2f);//phosphate
            bestReadingArrayList.add(1.023f);//SG
            bestReadingArrayList.add(10f); //strontium
            bestReadingArrayList.add(79f); //temp
        }else{
            bestReadingArrayList.add(10.0f); //Alk DKH
            bestReadingArrayList.add(0.0f);   //Ammonia
            bestReadingArrayList.add(400f / 1000);//Calcium
            bestReadingArrayList.add(.5f);//Iodine
            bestReadingArrayList.add(1200f / 1000); //magnesium
            bestReadingArrayList.add(30f);//nitrate
            bestReadingArrayList.add(0.0f); //nitrite
            bestReadingArrayList.add(8.1f); //ph
            bestReadingArrayList.add(.1f);//phosphate
            bestReadingArrayList.add(1.020f);//SG
            bestReadingArrayList.add(5f); //strontium
            bestReadingArrayList.add(77f); //temp
        }
    }

    /**
     * Gets the private bestReading Arraylist to be used
     * @return
     */
    public ArrayList getBestReadingVal(){
        return bestReadingArrayList;

    }
    /**
     * Takes the current tank.name and tank.type and sends them to Frirebase
     */
    public void sycTankToDB(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Tank");
        database.child("name").setValue(tank.name);
        database.child("type").setValue(tank.type);
    }
    public void sendReadingsToDB(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Readings");
        database.setValue(readingsMap);
    }

    /**
     * When needed this function will resend the whole liveStock Arraylist to the Database so the values will be the same each time.
     */
    public void sendLiveStockToDB(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("LiveStock");
        database.setValue(liveStockArrayList);
    }

    /**
     * Helper function that takes in a key finds it in the ReadingMap and removes the values then updates the Readings in FB.
     * @param key
     */
    public void removeValFromReadingMap(String key){
        if(key == readingsMap.get(key).toString()){
            readingsMap.remove(key);
            sendReadingsToDB();
        }
    }
    /**
     * this function will get the Map of Readings from firebase initially
     */
    public void getReadingMapFromDB(){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Readings");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Reading> tempMap = (HashMap)dataSnapshot.getValue();
//                ArrayList<String>dbKeys = new ArrayList<String>();
                Set<String>keys = tempMap.keySet();
                for (String key: keys){
                    Reading reading = new Reading();
                    System.out.println(key);
//                    dbKeys.add(key);
                    reading.alkalinity = Float.parseFloat(dataSnapshot.child(key).child("alkalinity").getValue().toString());
                    reading.ammonia = Float.parseFloat(dataSnapshot.child(key).child("ammonia").getValue().toString());
                    reading.calcium = Float.parseFloat(dataSnapshot.child(key).child("calcium").getValue().toString());
                    reading.iodine = Float.parseFloat(dataSnapshot.child(key).child("iodine").getValue().toString());
                    reading.magnesium = Float.parseFloat(dataSnapshot.child(key).child("magnesium").getValue().toString());
                    reading.nitrate = Float.parseFloat(dataSnapshot.child(key).child("nitrate").getValue().toString());
                    reading.nitrite = Float.parseFloat(dataSnapshot.child(key).child("nitrite").getValue().toString());
                    reading.ph = Float.parseFloat(dataSnapshot.child(key).child("ph").getValue().toString());
                    reading.phosphate = Float.parseFloat(dataSnapshot.child(key).child("phosphate").getValue().toString());
                    reading.specificGravity = Float.parseFloat(dataSnapshot.child(key).child("specificGravity").getValue().toString());
                    reading.strontium = Float.parseFloat(dataSnapshot.child(key).child("strontium").getValue().toString());
                    reading.specificGravity = Float.parseFloat(dataSnapshot.child(key).child("specificGravity").getValue().toString());
                    reading.temperature = Float.parseFloat(dataSnapshot.child(key).child("temperature").getValue().toString());
                    reading.date = dataSnapshot.child(key).child("date").getValue().toString();

                    readingsMap.put(reading.date,reading);

                    System.out.println(reading.alkalinity + "This is reading.alk");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * This fuction gets the Tanks Type and Name from the database and sents name and type to its values
     * so they can be referenced throughout the program
     */
    public void getTank(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Tank");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("name") && dataSnapshot.hasChild("type")) {
                    System.out.println(dataSnapshot.child("name").getValue().toString() + " THIS IS THE VALUE");
                    name = dataSnapshot.child("name").getValue().toString();
                    type = dataSnapshot.child("type").getValue().toString();
                }else{
                    System.out.println("No Values");
                    MainActivity m = new MainActivity();
                    m.validateTank(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
