package com.byk.chartdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.byk.chart.adapter.ChartAdapter;
import com.byk.chart.bean.CandleEntry;
import com.byk.chart.bean.Entry;
import com.byk.chart.bean.LineEntry;
import com.byk.chart.data.CandleDataSet;
import com.byk.chart.data.LineDataSet;
import com.byk.chart.draw.AxisDrawer;
import com.byk.chart.draw.XAxisDrawer;
import com.byk.chart.draw.YAxisDrawer;
import com.byk.chart.format.DateFormatterUtils;
import com.byk.chart.listener.OnChartChangeListener;
import com.byk.chart.utils.DensityUtil;
import com.byk.chart.view.ChartView;
import com.byk.chart.view.GroupChartView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GroupChartView chartView;
    ChartView bcv_test;
    ChartView bcv_test2;
    ChartView bcv_test3;

    ChartAdapter chartAdapter;
    ChartAdapter chartAdapter2;
    ChartAdapter chartAdapter3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH);

        chartView =findViewById(R.id.chartView);
        bcv_test = findViewById(R.id.bcv_test);
        bcv_test2 = findViewById(R.id.bcv_test2);
        bcv_test3 = findViewById(R.id.bcv_test3);

        chartView.setOnChartChangeListener(new OnChartChangeListener() {
            @Override
            public void onLongPress(int position) {
                System.out.println("---------------------------position:" + position);
            }

            @Override
            public void onDisplayChange(int start, int end) {

            }
        });

        List<LineEntry> lineEntries = new ArrayList<>();
        List<CandleEntry> candleEntries = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            calendar.add(Calendar.DAY_OF_MONTH,-i);
            lineEntries.add(new LineEntry(DateFormatterUtils.format(calendar.getTime(),DateFormatterUtils.FORMAT_YMD),random.nextInt(50)+70));

            int high = random.nextInt(50)+50;
            int open = random.nextInt(50)+50;
            int close = random.nextInt(50)+50;
            int low = random.nextInt(50)+50;

            int temp = high;
            high = Math.max(Math.max(Math.max(high,open),close),low);
            low = Math.min(Math.min(Math.min(temp,open),close),low);

            candleEntries.add(new CandleEntry(DateFormatterUtils.format(calendar.getTime(),DateFormatterUtils.FORMAT_YMD),high,low,open,close));
        }

        LineDataSet<Entry> dataSet = new LineDataSet<Entry>(lineEntries);
        dataSet.setColor(0xffdddddd);
        dataSet.setStrokeWidth(4);
        dataSet.setCalcuLimit(false);

        CandleDataSet<Entry> dataSet1 = new CandleDataSet<>(candleEntries);
        dataSet1.setGreenColor(0xff00ff00);
        dataSet1.setGrayColor(0xffeeeeee);
        dataSet1.setRedColor(0xffff0000);
        dataSet1.setOfffsetRight(DensityUtil.dip2px(this,2));
        dataSet1.setOffsetLeft(DensityUtil.dip2px(this,2));
        dataSet1.setStrokeWidth(4);

        chartAdapter = new ChartAdapter(this,dataSet,dataSet1);

        YAxisDrawer yAxisDrawer = chartAdapter.getYAxisDrawer();
        yAxisDrawer.setGridLinesCount(5);
        yAxisDrawer.setGridLinesWidth(4f);
        yAxisDrawer.setGridLinesColor(0xff00ff00);

        XAxisDrawer xAxisDrawer = chartAdapter.getXAxisDrawer();
        xAxisDrawer.setGridLinesCount(5);
        xAxisDrawer.setGridLinesWidth(4f);
        xAxisDrawer.setGridLinesColor(0xff00ff00);

        chartAdapter.getXAxisDrawer().setOutLine(AxisDrawer.OutLine.NONE);
        bcv_test.setAdapter(chartAdapter);


        List<LineEntry> lineEntries2 = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            calendar.add(Calendar.DAY_OF_MONTH,-i);
            lineEntries2.add(new LineEntry(DateFormatterUtils.format(calendar.getTime(),DateFormatterUtils.FORMAT_YMD),random.nextInt(50)+100));
        }

        LineDataSet<Entry> dataSet2 = new LineDataSet<Entry>(lineEntries);
        dataSet2.setColor(0xffff0000);
        dataSet2.setStrokeWidth(4);

        chartAdapter2 = new ChartAdapter(this,dataSet2);

        YAxisDrawer yAxisDrawer2 = chartAdapter2.getYAxisDrawer();
        yAxisDrawer2.setGridLinesCount(5);
        yAxisDrawer2.setGridLinesWidth(4f);
        yAxisDrawer2.setGridLinesColor(0xff00ff00);

        XAxisDrawer xAxisDrawer2 = chartAdapter2.getXAxisDrawer();
        xAxisDrawer2.setGridLinesCount(5);
        xAxisDrawer2.setGridLinesWidth(4f);
        xAxisDrawer2.setGridLinesColor(0xff00ff00);

        bcv_test2.setAdapter(chartAdapter2);



        List<LineEntry> lineEntries3 = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            calendar.add(Calendar.DAY_OF_MONTH,-i);
            lineEntries3.add(new LineEntry(DateFormatterUtils.format(calendar.getTime(),DateFormatterUtils.FORMAT_YMD),random.nextInt(50)+100));
        }

        LineDataSet<Entry> dataSet3 = new LineDataSet<Entry>(lineEntries3);
        dataSet3.setColor(0xffff0000);
        dataSet3.setStrokeWidth(4);

        chartAdapter3 = new ChartAdapter(this,dataSet3);

        YAxisDrawer yAxisDrawer3 = chartAdapter3.getYAxisDrawer();
        yAxisDrawer3.setGridLinesCount(5);
        yAxisDrawer3.setGridLinesWidth(4f);
        yAxisDrawer3.setGridLinesColor(0xff00ff00);

        XAxisDrawer xAxisDrawer3 = chartAdapter3.getXAxisDrawer();
        xAxisDrawer3.setGridLinesCount(5);
        xAxisDrawer3.setGridLinesWidth(4f);
        xAxisDrawer3.setGridLinesColor(0xff00ff00);

        bcv_test3.setAdapter(chartAdapter3);

        bcv_test.addSyncChartView(bcv_test2,bcv_test3);
        bcv_test2.addSyncChartView(bcv_test,bcv_test3);
        bcv_test3.addSyncChartView(bcv_test,bcv_test2);
    }
}
