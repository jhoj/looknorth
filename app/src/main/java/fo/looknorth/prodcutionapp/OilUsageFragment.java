package fo.looknorth.prodcutionapp;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
            l.setForm(Legend.LegendForm.LINE);
            l.setTextColor(ColorTemplate.getHoloBlue());
            l.setTextSize(16f);

            XAxis x1 = lineChart.getXAxis();
            x1.setDrawGridLines(false);
            x1.setAvoidFirstLastClipping(true);
            x1.setAdjustXLabels(true);
            x1.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
            x1.setTextSize(16f);

            YAxis y1 = lineChart.getAxisLeft();
            y1.setTextColor(ColorTemplate.getHoloBlue());
            y1.setAxisMaxValue(2f);
            y1.setDrawGridLines(false);
            y1.setTextSize(16f);

            lineChart.getAxisRight().setEnabled(false);

            addLines();

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();

            new Thread(new Runnable() {

                @Override
                public void run() {

                    for (int i = 0; i < 100; i++) {

                        //TODO will crash when screen orientation is changed.

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                String time = simpleDateFormat.format(Calendar.getInstance().getTime());

                                addEntry(time, getCurrentUsage(), getRecommendedUsage());
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        @Override
        public void onPause() {
            Toast.makeText(getActivity(), "onPause", Toast.LENGTH_SHORT).show();
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
            ArrayList<String> xVals = new ArrayList<>();
            xVals.add("");
            ArrayList<LineDataSet> dataSets = new ArrayList<>();
            //setting currentusage entry list.
            //add an empty entry for each dataset
            //to be able to instatiate
            ArrayList<Entry> cValues = new ArrayList<>();
            cValues.add(new Entry(0, 0));
            LineDataSet current = new LineDataSet(cValues, "Current oil usage in liters");
            int cColor = mColors[3];
            current.setColor(cColor);
            current.setCircleColor(cColor);
            current.setLineWidth(2.5f);
            dataSets.add(current);
            //setting recommended usage entry list.
            //add an empty entry for each dataset
            //to be able to instatiate
            ArrayList<Entry> values = new ArrayList<>();
            values.add(new Entry(0, 0));
            LineDataSet recommended = new LineDataSet(values, "Recommended");
            recommended.setDrawValues(false);
            recommended.setDrawCircles(false);
            int rColor = mColors[1];
            recommended.setColor(rColor);
            recommended.setLineWidth(2.5f);
            recommended.setLabel("Recommeded");
            dataSets.add(recommended);
            // add to line data
            data = new LineData(xVals, dataSets);
            //add to chart
            lineChart.setData(data);
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
            //TODO the count should be able to scale.
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
