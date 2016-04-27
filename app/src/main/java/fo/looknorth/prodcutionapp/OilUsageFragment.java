package fo.looknorth.prodcutionapp;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by jakup on 3/16/16.
 */
public class OilUsageFragment extends Fragment {

    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_layout, container, false);

        // Create the adapter that will return the fragments
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.container);
        viewPager.setAdapter(myFragmentPagerAdapter);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);
        Toast.makeText(getActivity(), "Oil Usage", Toast.LENGTH_SHORT).show();
        getActivity().setTitle("Oil Usage");
        return view;
    }

    public static class ContentFragment extends Fragment
    {
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_oil_usage, container, false);

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
            yAxisLeft.setAxisMaxValue(2f);
            yAxisLeft.setDrawGridLines(false);
            yAxisLeft.setTextSize(16f);

            YAxis yAxisRight = lineChart.getAxisRight();
            yAxisRight.setEnabled(true);
            yAxisRight.setDrawGridLines(false);

            addLines();

            return rootView;
        }

        // Define the code block to be executed
        private Runnable updateLineChart = new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String time = simpleDateFormat.format(Calendar.getInstance().getTime());
                addEntry(time, getCurrentUsage(), getRecommendedUsage());
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

        private void addLines() {
            //setup the
            ArrayList<String> xValues = new ArrayList<>();

            //adding an item to the list to allow instantiation for the other lists.
            xValues.add("");

            //list of linedataset, to carry data for y-values
            ArrayList<LineDataSet> yValues = new ArrayList<>();

            //setting currentusage entry list.
            ArrayList<Entry> currentUsageValues = new ArrayList<>();

            //add an empty entry to allow instatiation of linedataset.
            currentUsageValues.add(new Entry(0, 0));

            //LineDataSet needs a list of entries to instatiate
            LineDataSet currentUsageDataSet = new LineDataSet(currentUsageValues, "Current oil usage in liters");

            //set the color to green
            int currentUsageColor = mColors[3];

            currentUsageDataSet.setColor(currentUsageColor);
            currentUsageDataSet.setCircleColor(currentUsageColor);
            currentUsageDataSet.setLineWidth(2.5f);

            //add current
            yValues.add(currentUsageDataSet);

            ArrayList<Entry> recommendedUsageValues = new ArrayList<>();
            recommendedUsageValues.add(new Entry(0, 0));

            LineDataSet recommendedUsageDataSet = new LineDataSet(recommendedUsageValues, "Recommended");
            recommendedUsageDataSet.setDrawValues(false);
            recommendedUsageDataSet.setDrawCircles(false);

            int rColor = mColors[1];
            recommendedUsageDataSet.setColor(rColor);
            recommendedUsageDataSet.setLineWidth(2.5f);
            recommendedUsageDataSet.setLabel("Recommeded");

            yValues.add(recommendedUsageDataSet);

            // add to line data
            data = new LineData(xValues, yValues);

            //add to chart
            lineChart.setData(data);

            //update
            lineChart.invalidate();
        }

        private float getRecommendedUsage() {
            return 0.9f;
        }

        private float getCurrentUsage() {
            return new Random().nextFloat();
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ContentFragment();
        }

        @Override
        public int getCount() {
            //will show 6 pages
            //total and all machines.
            //TODO the count should be able to scale. i.e. use amount of machines to set count
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Total";
                default:
                    return "Machine " + position;
            }
        }
    }

}
