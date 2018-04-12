package com.dossantos.aquariumtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class removeLivestock extends AppCompatActivity {
    String removeVal;
    Button remove;
    EditText removeTxt;
    private ListView livestockRemoveList;
    Tank tank = Tank.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_livestock);
        remove = (Button)findViewById(R.id.removeBtnRemove);
        removeTxt = (EditText)findViewById(R.id.removeEditText);
        livestockRemoveList = (ListView) findViewById(R.id.liveViewRemoved);
        final ArrayAdapter<LiveStock> livestockArrayAdapter2 = new ArrayAdapter<LiveStock>(this, android.R.layout.simple_list_item_1, tank.liveStockArrayList);
        livestockRemoveList.setAdapter(livestockArrayAdapter2);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeVal = removeTxt.getText().toString();
                tank.removeTankElement(removeVal);
                Intent intent = new Intent(getApplicationContext(),LivestockActivity.class);
                startActivity(intent);
            }
        });
    }

}
