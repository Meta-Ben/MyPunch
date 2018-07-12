package com.esgi.mypunch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.esgi.mypunch.data.dtos.BoxingSession;
import com.esgi.mypunch.punchlist.PunchListFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PunchDetailsActivity extends BaseActivity {

    public static final String TAG = "PunchDetailsActivity";
    private BoxingSession bSession;
    private LineChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;

    @BindView(R.id.day) TextView dayTv;
    @BindView(R.id.start) TextView startTv;
    @BindView(R.id.end) TextView endTv;
    @BindView(R.id.nbPunches) TextView nbPunchesTv;
    @BindView(R.id.minPower) TextView minPowerTv;
    @BindView(R.id.avgPower) TextView avgPowerTv;
    @BindView(R.id.maxPower) TextView maxPowerTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_details);
        ButterKnife.bind(this);
        LineChart chart = (LineChart) findViewById(R.id.chart);

        int[] dataObjects = {0, 1, 2, 3};

        List<Entry> entries = new ArrayList<Entry>();

        for (int data : dataObjects) {

            // turn your data into Entry objects
            entries.add(new Entry(data, data));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();


        Serializable content = getIntent().getSerializableExtra(PunchListFragment.SESSION_KEY);
        if (content != null) {
            bSession = (BoxingSession) content;
            Log.d(TAG, bSession.toString());
            renderBoxingSession(bSession);
        } else {
            Log.e(TAG, "couldn't get session");
        }
    }

    private void renderBoxingSession(BoxingSession bSession) {
        String nbPunches = bSession.getNbPunches() + "";
        String minPower = bSession.getMin_power() + "";
        String avgPower = bSession.getAverage_power() + "";
        String maxPower = bSession.getMax_power() + "";

        Date startDate = bSession.getStart();
        Date endDate = bSession.getEnd();
        DateFormat dayFormat = SimpleDateFormat.getDateInstance();
        DateFormat hourFormat = SimpleDateFormat.getTimeInstance();

        nbPunchesTv.setText(nbPunches);
        minPowerTv.setText(minPower);
        avgPowerTv.setText(avgPower);
        maxPowerTv.setText(maxPower);
        dayTv.setText(dayFormat.format(startDate));
        startTv.setText(hourFormat.format(startDate));
        endTv.setText(hourFormat.format(endDate));

        String title = dayFormat.format(startDate) + " " + hourFormat.format(startDate);
        setTitle(title);
    }
}
