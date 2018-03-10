package com.wb.tracun.braziliextest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        showGraph();
    }

    //ResponsePrivateAPI Graph
    void showGraph(){
        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.coinChart);

        mCubicValueLineChart.setLineStroke(0.2f);
        ValueLineSeries series = new ValueLineSeries();

        series.setColor(0xFFFF3366);

        series.addPoint(new ValueLinePoint("", 0f));
        series.addPoint(new ValueLinePoint("Dom", 2.4f));
        series.addPoint(new ValueLinePoint("Seg", 3.4f));
        series.addPoint(new ValueLinePoint("Ter", 0.4f));
        series.addPoint(new ValueLinePoint("Qua", 1.2f));
        series.addPoint(new ValueLinePoint("Qui", 10.6f));
        series.addPoint(new ValueLinePoint("Sex", 1.0f));
        series.addPoint(new ValueLinePoint("Sab", 3.5f));

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }
}
