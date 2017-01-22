package com.training;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqiuyi on 17/1/11.
 */

public class EChartsTestActivity extends AppCompatActivity {
    @BindView(R.id.pie)
    PieChart pie;
    @BindView(R.id.line)
    LineChart line;

    private PieData pieData;
    private PieDataSet pieDataSet;

    private LineDataSet lineData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echarts);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(1, "疲劳用眼"));
        pieEntries.add(new PieEntry(1, "健康用眼"));

        pieDataSet = new PieDataSet(pieEntries, "疲劳用眼时长");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        // 饼图颜色
        colors.add(0xffe7ea87);
        colors.add(0xff14adab);
        pieDataSet.setColors(colors);

        pieData = new PieData(pieDataSet);
        pieData.setDrawValues(false);

        pie.setData(pieData);
        pie.getDescription().setEnabled(false);
        pie.setUsePercentValues(false);
        pie.valuesToHighlight();
        pie.setDrawHoleEnabled(false);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1,1));
        entries.add(new Entry(2,2));
        entries.add(new Entry(3,3));
        entries.add(new Entry(4,4));
        entries.add(new Entry(5,5));
        entries.add(new Entry(6,6));
        LineDataSet lineDataSet = new LineDataSet(entries,"疲劳用眼时长");
        LineData lineData = new LineData(lineDataSet);
        line.setData(lineData);
    }
}
