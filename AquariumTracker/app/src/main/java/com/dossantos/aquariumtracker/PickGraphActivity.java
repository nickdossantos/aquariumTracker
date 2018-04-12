package com.dossantos.aquariumtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class PickGraphActivity extends AppCompatActivity {
    ListView logDateListView;
    Controller controller = new Controller();
    Tank tank = Tank.getInstance();
    Reading reading = new Reading ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_graph);
        getSupportActionBar().setTitle("Name: " + tank.name + "     Type: "+ tank.type);


        logDateListView = (ListView)findViewById(R.id.readingDateListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, controller.getReadingKeys());
        logDateListView.setAdapter(arrayAdapter);
        logDateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mKey = controller.getReadingKeys().get(position).toString();
                Reading temp = tank.readingsMap.get(mKey);
                reading.setTempReading(temp);//sets temp to be the selected reading in the Reading Class
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivity(intent);
            }
        });
    }

}
