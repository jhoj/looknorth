package fo.looknorth.prodcutionapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import model.Logic;
import model.Machine;
import model.Production;

/**
 * Created by jakup on 3/16/16.
 */
public class ProductionFragement extends Fragment {

    private CurrentContextPagerAdapter currentContextPagerAdapter;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_layout, container, false);

        // Create the adapter that will return the fragments
        currentContextPagerAdapter = new CurrentContextPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.container);
        viewPager.setAdapter(currentContextPagerAdapter);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);

        Toast.makeText(getActivity(), "Production", Toast.LENGTH_SHORT).show();
        getActivity().setTitle("Production");
        return view;
    }

    public static class CurrentContextFragment extends Fragment implements OnChartValueSelectedListener {
        /**
         * The fragment argument representing the number for this
         * fragment.
         */
        private static final String FRAGMENT_POSITION = "FRAGMENT_POSITION";

        private BarChart barChart;
        private PieChart mChart;

        public CurrentContextFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CurrentContextFragment newInstance()
        {
            return new CurrentContextFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_production, container, false);

            TextView dateText = (TextView) rootView.findViewById(R.id.dateText);
            dateText.setText(Logic.instance.getDate());

            barChart = (BarChart) rootView.findViewById(R.id.bar_chart);
            barChart.setOnChartValueSelectedListener(this);
            barChart.setDrawBarShadow(false);
            barChart.setDrawValueAboveBar(true);
            barChart.setDescription("Today's Production");
            barChart.setPinchZoom(false);

            barChart.setDrawGridBackground(false);
            // barChart.setDrawYLabels(false);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setSpaceBetweenLabels(5);

            YAxis leftAxis = barChart.getAxisLeft();
            leftAxis.setLabelCount(8);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);
            leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

            YAxis rightAxis = barChart.getAxisRight();
            rightAxis.setEnabled(false);

            Legend l = barChart.getLegend();
            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setFormSize(9f);
            l.setTextSize(11f);
            l.setXEntrySpace(4f);

            setBarData();

            mChart = (PieChart) rootView.findViewById(R.id.pie_chart);
            mChart.setUsePercentValues(true);

            mChart.setCenterText("Daily Production");
            mChart.setDescription("");
            mChart.setDrawHoleEnabled(true);
            mChart.setHoleColor(Color.WHITE);

            mChart.setHoleRadius(58f);
            mChart.setTransparentCircleRadius(61f);

            mChart.setDrawCenterText(true);

            mChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            mChart.setRotationEnabled(true);
            // add a selection listener
            mChart.setOnChartValueSelectedListener(this);

            setPieData();

            Legend pieLegend = mChart.getLegend();
            pieLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            pieLegend.setXEntrySpace(7f);
            pieLegend.setYEntrySpace(0f);
            pieLegend.setYOffset(0f);

            return rootView;
        }

        private void setPieData() {
            //todo add animation to the pie
            ArrayList<String> xVals = new ArrayList<>();
            ArrayList<Entry> yVals = new ArrayList<Entry>();
            ArrayList<Machine> machines = (ArrayList<Machine>) Logic.instance.machines;
            Machine machine0 = machines.get(0);  //this is the total section

            for (int i = 0; i < machine0.productionList.size(); i++)
            {
                //it has 2 entries
                //two names added to pie chart.
                xVals.add(machine0.productionList.get(i).product.name);
                //shows the product percentage of the whole production
                float pieTotal = machine0.getTotalProducedItems();
                float productionQuantity = machine0.productionList.get(i).quantityProduced;
                float fraction = productionQuantity / pieTotal;
                float percentage = fraction * 100;

                yVals.add(new Entry(percentage, i));
            }

            PieDataSet dataSet = new PieDataSet(yVals, "");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);

            // add a lot of colors

            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);

            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.WHITE);
            mChart.setData(data);

            mChart.invalidate();
        }

        private void setBarData() {
            //todo add animation to the bars
            Machine m = Logic.instance.machines.get(0);
            ArrayList<String> xVals = new ArrayList<>();

            for (Production p: m.productionList) {
                xVals.add(p.product.name);
            }

            ArrayList<Integer> colors = new ArrayList<>();
            for (int i: ColorTemplate.COLORFUL_COLORS) {
                colors.add(i);
            }
            for (int i: ColorTemplate.LIBERTY_COLORS) {
                colors.add(i);
            }
            for (int i: ColorTemplate.PASTEL_COLORS) {
                colors.add(i);
            }


            BarDataSet set;
            ArrayList<BarDataSet> dataSets = new ArrayList<>();

            for (int i = 0; i < m.productionList.size(); i++) {
               ArrayList<BarEntry> list = new ArrayList<>();
               list.add(new BarEntry(m.productionList.get(i).quantityProduced, i));
                set = new BarDataSet(list, m.productionList.get(i).product.name);
                set.setColor(colors.get(i));
                set.setBarSpacePercent(35f);
                dataSets.add(set);
            }

            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(10f);
            barChart.setData(data);
        }

        @Override
        public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        }

        @Override
        public void onNothingSelected() {

        }
    }

    public class CurrentContextPagerAdapter extends FragmentPagerAdapter {

        public CurrentContextPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CurrentContextFragment.newInstance();
        }

        @Override
        public int getCount() {
            //total and all machines.
            return Logic.instance.machines.size() + 1;
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
