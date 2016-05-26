package fo.looknorth.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fo.looknorth.app.app.R;
import fo.looknorth.logic.Logic;
import fo.looknorth.model.OilConsumption;
import fo.looknorth.model.OilConsumptionEntry;

public class OilConsumptionContentFragment extends Fragment implements Runnable
{
    Logic l = Logic.instance;
    // tab index in the viewpager
    public int tabIndex;

    //MP Chart
    private LineChart lineChart;
    private int[] colors = new int[] {
            ColorTemplate.COLORFUL_COLORS[0],
            ColorTemplate.COLORFUL_COLORS[1],
            ColorTemplate.COLORFUL_COLORS[2],
            ColorTemplate.COLORFUL_COLORS[3],
            ColorTemplate.COLORFUL_COLORS[4]
    };
    private LineData data;
    private ListView oilConsumptionList;
    Handler handler = new Handler();
    Typeface t = Typeface.create("casual", Typeface.ITALIC);

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OilConsumptionContentFragment newInstance(int currentTab) {

        OilConsumptionContentFragment fragment = new OilConsumptionContentFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("tabIndex", currentTab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_oil_consumption, container, false);

        Logic.instance.observers.add(this);

        tabIndex = getArguments().getInt("tabIndex");

        //MP CHART
        lineChart = (LineChart) rootView.findViewById(R.id.line_chart);
        lineChart.setDescription(getResources().getString(R.string.oil_chart_description));
        lineChart.setDescriptionTextSize(18f);
        lineChart.setTouchEnabled(true);
        lineChart.setHighlightEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setPinchZoom(false);
        lineChart.setDescriptionTypeface(t);

        Legend l = lineChart.getLegend();
        l.setTypeface(t);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextColor(ColorTemplate.getHoloBlue());
        l.setTextSize(16f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setAdjustXLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(16f);
        xAxis.setTypeface(t);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setTextColor(ColorTemplate.getHoloBlue());
        yAxisLeft.setAxisMaxValue(1.5f);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setTextSize(16f);
        yAxisLeft.setTypeface(t);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawLabels(false);

        //add datasets for x values and actual and recommended consumption.
        initDataForChart();

        oilConsumptionList = (ListView) rootView.findViewById(R.id.oilConsumptionListView);

        //list that holds only 10 objects
        String[] recordingListData = new String[10];

        String recorded1 = "10:23 21-07-16";

        String liters1 = "1";

        final String[] listersListData = new String[10];

        for (int i = 0; i < 10; i++) {
            recordingListData[i] = i + i + ":" + i + i;
            listersListData[i] = ""+i;
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.listview_layout, R.id.recordedTextView, recordingListData) {
          @Override
          public View getView(int position, View cachedView, ViewGroup parent) {
            View view = super.getView(position, cachedView, parent);
            TextView litersTextView = (TextView) view.findViewById(R.id.litersTextView);

            litersTextView.setText(listersListData[position]);

            return view;
            }
        };


        oilConsumptionList.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logic.instance.observers.remove(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        Iterator i = Logic.instance.oilUsageLinePoints.get(tabIndex).iterator();

        while (i.hasNext()) {
            OilConsumptionEntry o = (OilConsumptionEntry) i.next();

            addEntry(o.time, o.actualUsage, o.recommendedUsage);
        }
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
        LineDataSet currentUsageDataSet = new LineDataSet(currentUsageValues, getResources().getString(R.string.actual_consumption));

        // set the color to green
        int currentUsageColor = colors[3];

        currentUsageDataSet.setColor(currentUsageColor);
        currentUsageDataSet.setCircleColor(currentUsageColor);
        currentUsageDataSet.setLineWidth(2.5f);
        currentUsageDataSet.setValueTypeface(t);

        // add currentUsage to main dataset
        yValues.add(currentUsageDataSet);

        ArrayList<Entry> recommendedUsageValues = new ArrayList<>();
        recommendedUsageValues.add(new Entry(0, 0));

        LineDataSet recommendedUsageDataSet = new LineDataSet(recommendedUsageValues, getResources().getString(R.string.recommended_consumption));
        recommendedUsageDataSet.setDrawValues(false);
        recommendedUsageDataSet.setDrawCircles(false);

        // red color
        int redColor = colors[1];
        recommendedUsageDataSet.setColor(redColor);
        recommendedUsageDataSet.setLineWidth(2.5f);
        recommendedUsageDataSet.setLabel(getResources().getString(R.string.recommended_consumption));

        // add recommended to main dataset
        yValues.add(recommendedUsageDataSet);

        // add to line data
        data = new LineData(xValues, yValues);

        //add to chart
        lineChart.setData(data);

        //update
        lineChart.invalidate();
    }

    @Override
    public void run() {
        addEntry(l.getTime(tabIndex), l.getActualUsage(tabIndex), l.getRecommendedUsage(tabIndex));
    }
}
