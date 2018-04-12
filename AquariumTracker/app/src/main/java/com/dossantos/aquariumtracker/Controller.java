package com.dossantos.aquariumtracker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;

/**
 *     Created by DosSantos on 4/23/17.
 */
public class Controller {
    private Tank tank = Tank.getInstance();

    /**
     * Used to make a instance of tank
     * takes in a name and string and will set make tank input equal to name and type of tank
     * @param name
     * @param type
     */
    public void makeTank(String name, String type){
        tank.name = name;
        tank.type = type;
    }

    /**
     * Simply adds livestock to Tank's arraylist of Livestock
     * @param liveStock
     */
    public void addLivetockToArray(LiveStock liveStock){
        tank.liveStockArrayList.add(liveStock);
    }
    public void printLivestock(){
        for(int i = 0; i < tank.liveStockArrayList.size(); i++){
            tank.liveStockArrayList.get(i).toString();
            System.out.println(tank.liveStockArrayList.get(i).name);
        }
    }

    public ArrayList getReadingKeys(){
        ArrayList<String> temp = new ArrayList<>();
        Set<String> keys = tank.readingsMap.keySet();
        for(String key: keys){
            System.out.println(key);
            temp.add(key);
        }
        return temp;
    }

    /**
     * When this function is called it will populate the liveStock array From Firebase
     */
    public void populateLiveStockArray(){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("LiveStock");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<LiveStock>newList = new ArrayList<LiveStock>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    LiveStock liveStock = new LiveStock();
                    liveStock.date= (String) snapshot.child("date").getValue();
                    liveStock.type = (String) snapshot.child("type").getValue();
                    liveStock.name = (String) snapshot.child("name").getValue();

                    System.out.println(liveStock.name);
                    newList.add(liveStock);
                }
                tank.liveStockArrayList = newList;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
