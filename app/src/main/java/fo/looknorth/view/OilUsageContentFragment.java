package fo.looknorth.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import fo.looknorth.app.app.R;
import fo.looknorth.logik.Logik;
import fo.looknorth.model.OilUsageEntry;

public class OilUsageContentFragment extends Fragment
{
    // tab index in the viewpager
    public int tabIndex;

    //MP Chart
    private LineChart lineChart;
    private int[] mColors = new int[] {
            ColorTemplate.COLORFUL_COLORS[0],
            ColorTemplate.COLORFUL_COLORS[1],
            ColorTemplate.COLORFUL_COLORS[2],
            ColorTemplate.COLORFUL_COLORS[3],
            ColorTemplate.COLORFUL_COLORS[4]
    };
    private LineData data;

    Handler handler = new Handler();

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OilUsageContentFragment newInstance(int currentTab) {

        OilUsageContentFragment fragment = new OilUsageContentFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("tabIndex", currentTab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_oil_usage, container, false);

        tabIndex = getArguments().getInt("tabIndex");

        //MP CHART
        lineChart = (LineChart) rootView.findViewById(R.id.line_chart);
        lineChart.setDescription("Oil usage");
        lineChart.setDescriptionTextSize(18f);
        lineChart.setTouchEnabled(true);
        lineChart.setHighlightEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setPinchZoom(false);

        Legend l = lineChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextColor(ColorTemplate.getHoloBlue());
        l.setTextSize(16f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setAdjustXLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(16f);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setTextColor(ColorTemplate.getHoloBlue());
        yAxisLeft.setAxisMaxValue(1.5f);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setTextSize(16f);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawLabels(false);

        //add datasets for x values and actual and recommended usage.
        initDataForChart();

        return rootView;
    }

    // Define the code block to be executed
    private Runnable updateLineChart = new Runnable() {
        @Override
        public void run() {
            addEntry(getTime(), getActualUsage(), getRecommendedUsage());
            handler.postDelayed(updateLineChart, 1000); // run every second.
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        handler.post(updateLineChart);
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(updateLineChart);
        super.onPause();
    }

    private void addEntry(String xValue, float yValueCurrent, float yValueRecommended) {
        data.addXValue(xValue);
        LineDataSet current = data.getDataSetByIndex(0);
        LineDataSet recommended = data.getDataSetByIndex(1);
        current.addEntry(new Entry(yValueCurrent, data.getXValCount()));
        recommended.addEntry(new Entry(yValueRecommended, data.getXValCount()));

        // allow 10 points to be present at a time.
        updateChart(10);
    }

    private void updateChart(int xRange) {
        // let the chart know it's data has changed
        lineChart.notifyDataSetChanged();

        // limit the number of visible entries
        lineChart.setVisibleXRange(xRange);

        int lastEntry = xRange - (xRange+1);

        // move to the latest entry
        lineChart.moveViewToX(data.getXValCount() - lastEntry);
    }

    private void initDataForChart() {
        ArrayList<String> xValues = new ArrayList<>();

        // adding an item to the list to allow instantiation for the other lists.
        xValues.add("");

        // list of linedataset, to carry data for y-values
        ArrayList<LineDataSet> yValues = new ArrayList<>();

        // setting currentusage entry list.
        ArrayList<Entry> currentUsageValues = new ArrayList<>();

        // add an empty entry to allow instatiation of linedataset.
        currentUsageValues.add(new Entry(0, 0));

        // LineDataSet needs a list of entries to instatiate
        LineDataSet currentUsageDataSet = new LineDataSet(currentUsageValues, "Current oil usage in liters");

        // set the color to green
        int currentUsageColor = mColors[3];

        currentUsageDataSet.setColor(currentUsageColor);
        currentUsageDataSet.setCircleColor(currentUsageColor);
        currentUsageDataSet.setLineWidth(2.5f);

        // add currentUsage to main dataset
        yValues.add(currentUsageDataSet);

        ArrayList<Entry> recommendedUsageValues = new ArrayList<>();
        recommendedUsageValues.add(new Entry(0, 0));

        LineDataSet recommendedUsageDataSet = new LineDataSet(recommendedUsageValues, "Recommended");
        recommendedUsageDataSet.setDrawValues(false);
        recommendedUsageDataSet.setDrawCircles(false);

        // red color
        int rColor = mColors[1];
        recommendedUsageDataSet.setColor(rColor);
        recommendedUsageDataSet.setLineWidth(2.5f);
        recommendedUsageDataSet.setLabel("Recommeded");

        // add recommended to main dataset
        yValues.add(recommendedUsageDataSet);

        // add to line data
        data = new LineData(xValues, yValues);

        //add to chart
        lineChart.setData(data);

        //update
        lineChart.invalidate();
    }

    private String getTime() {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//            return simpleDateFormat.format(Calendar.getInstance().getTime());
        OilUsageEntry[] entries = Logik.instance.oilUsageLinePoints.get(tabIndex);
        int last = entries.length - 1;
        return entries[last].time;
    }

    private float getRecommendedUsage() {
        OilUsageEntry[] entries = Logik.instance.oilUsageLinePoints.get(tabIndex);
        int last = entries.length - 1;
        return entries[last].recommendedUsage;
    }

    private float getActualUsage() {
        OilUsageEntry[] entries = Logik.instance.oilUsageLinePoints.get(tabIndex);
        int last = entries.length - 1;
        return entries[last].actualUsage;
    }
}
