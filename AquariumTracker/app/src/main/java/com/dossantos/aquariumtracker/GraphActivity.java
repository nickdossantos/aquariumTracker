package com.dossantos.aquariumtracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    private Button doneBtnGraph;
    private CombinedChart combinedChart;
    private Tank tank = Tank.getInstance();
    private Reading reading = new Reading();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        getSupportActionBar().setTitle("Name: " + tank.name + "     Type: "+ tank.type);

        doneBtnGraph = (Button)findViewById(R.id.doneBtnGraph);

        combinedChart = (CombinedChart) findViewById(R.id.combinedChart);
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setBackgroundColor(Color.WHITE);
        combinedChart.setDrawGridBackground(false);
        combinedChart.setDrawBarShadow(false);
        combinedChart.setHighlightFullBarEnabled(false);

        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER
        });

        //got some of the following code from MP Android Documentation examples https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/CombinedChartActivity.java
        Legend l = combinedChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);


        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(12);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        CombinedData data = new CombinedData(); //Instance of the Combined Data

        data.setData(generateLineData());
        data.setData(genterateBarData());

        combinedChart.setData(data);

        doneBtnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Generates the line graph data
     * Uses the tank.getBestReaingVal to get the bestReadingArraylist so those values can be used to make the line graphs points
     * Modified thise code From the MP Android Wiki Docs to meet my needs https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/CombinedChartActivity.java
     * These functions Generate Line Data and Bar data. The lineGraph is on top of the Bar so it can be seen.
     * The line Graph is populated from the BESTREADINGS from Tank class
     * Bar Graph is populated by the Selection the user makes from PickGraph
     * @return
     */
    private LineData generateLineData() {

        float slot = 0;

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        tank.syncBestReadying();
        for (int i = 0; i < tank.getBestReadingVal().size(); i++){
            Float x = Float.parseFloat(tank.getBestReadingVal().get(i).toString());
            entries.add(new Entry(slot, x));
            slot ++;
        }


        LineDataSet set = new LineDataSet(entries, "Goal");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(12f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData genterateBarData(){
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        entries.add(new BarEntry(0, reading.getTempReading().alkalinity));
        entries.add(new BarEntry(1, reading.getTempReading().ammonia));
        entries.add(new BarEntry(2, reading.getTempReading().calcium));
        entries.add(new BarEntry(3, reading.getTempReading().iodine));
        entries.add(new BarEntry(4, reading.getTempReading().magnesium));
        entries.add(new BarEntry(5, reading.getTempReading().nitrate));
        entries.add(new BarEntry(6, reading.getTempReading().nitrite));
        entries.add(new BarEntry(7, reading.getTempReading().ph));
        entries.add(new BarEntry(8, reading.getTempReading().phosphate));
        entries.add(new BarEntry(9, reading.getTempReading().specificGravity));
        entries.add(new BarEntry(10, reading.getTempReading().strontium));
        entries.add(new BarEntry(11, reading.getTempReading().temperature));

        BarDataSet set1 = new BarDataSet(entries, "Reading for: " + reading.getTempReading().date);
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(12f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.45f;

        BarData d = new BarData(set1);

        d.setBarWidth(barWidth);
        return d;
    }
}
