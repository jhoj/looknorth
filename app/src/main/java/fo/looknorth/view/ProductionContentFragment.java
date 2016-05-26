package fo.looknorth.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.github.mikephil.charting.animation.AnimationEasing;

import java.util.ArrayList;

import fo.looknorth.app.app.R;
import fo.looknorth.logic.Logic;
import fo.looknorth.model.Machine;

import fo.looknorth.model.Product;

public class ProductionContentFragment extends Fragment implements OnChartValueSelectedListener, Runnable {

    public int tabIndex;
    private BarChart barChart;
    private PieChart pieChart;
    Typeface t = Typeface.create("casual", Typeface.ITALIC);

    public ProductionContentFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProductionContentFragment newInstance(int tabIndex)
    {
        ProductionContentFragment fragment = new ProductionContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabIndex", tabIndex);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_production, container, false);

        Logic.instance.observers.add(this);
        tabIndex = getArguments().getInt("tabIndex");

        TextView dateText = (TextView) rootView.findViewById(R.id.dateText);
        dateText.setText(Logic.instance.getDate());

        // find barchart defined in xml
        barChart = (BarChart) rootView.findViewById(R.id.bar_chart);

        // add a listener for click events
        barChart.setOnChartValueSelectedListener(this);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription(getResources().getString(R.string.production_chart_description));
        barChart.setPinchZoom(false);
        barChart.setDescriptionTypeface(t);

        barChart.setDrawGridBackground(false);
        // barChart.setDrawYLabels(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(5);
        xAxis.setTypeface(t);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        leftAxis.setTypeface(t);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setTypeface(t);

        setBarData();


        pieChart = (PieChart) rootView.findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(true);
        pieChart.setDescriptionTypeface(t);

        pieChart.setCenterText(getResources().getString(R.string.pie_chart_description));
        pieChart.setCenterTextTypeface(t);
        pieChart.setDescription("");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        // add a selection listener
        pieChart.setOnChartValueSelectedListener(this);

        setPieData();

        Legend pieLegend = pieChart.getLegend();
        pieLegend.setTypeface(t);
        pieLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        pieLegend.setXEntrySpace(7f);
        pieLegend.setYEntrySpace(0f);
        pieLegend.setYOffset(0f);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logic.instance.observers.remove(this);
    }

    private void setPieData() {
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<Machine> machines = (ArrayList<Machine>) Logic.instance.machines;
        Machine machine = machines.get(tabIndex);  //this is the total section

        if (!machine.productionEntry.items.isEmpty()) {
            for (int i = 0; i < machine.productionEntry.items.size(); i++) {
                xVals.add(machine.productionEntry.items.get(i).name);

                // shows the product percentage of the whole production
                float pieTotal = machine.productionEntry.getItemsProducedByMachine();
                float productionQuantity = machine.productionEntry.items.get(i).getTotal();
                float fraction = productionQuantity / pieTotal;
                float percentage = fraction * 100;

                yVals.add(new Entry(percentage, i));
            }

            PieDataSet dataSet = new PieDataSet(yVals, "");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setValueTypeface(t);

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
            data.setValueTypeface(t);
            pieChart.setData(data);
            pieChart.getData().notifyDataChanged();
            pieChart.notifyDataSetChanged();
            pieChart.animateX(700, AnimationEasing.EasingOption.EaseInCirc);

            pieChart.invalidate();
        }
    }

    private void setBarData() {
        Machine m = Logic.instance.machines.get(tabIndex);
        ArrayList<String> xVals = new ArrayList<>();

        if (!m.productionEntry.items.isEmpty()) {
            for (Product product : m.productionEntry.items) {
                xVals.add(product.name);
            }


            ArrayList<Integer> colors = new ArrayList<>();
            for (int i : ColorTemplate.COLORFUL_COLORS) {
                colors.add(i);
            }
            for (int i : ColorTemplate.LIBERTY_COLORS) {
                colors.add(i);
            }
            for (int i : ColorTemplate.PASTEL_COLORS) {
                colors.add(i);
            }


            BarDataSet set;
            ArrayList<BarDataSet> dataSets = new ArrayList<>();

            for (int i = 0; i < m.productionEntry.items.size(); i++) {
                ArrayList<BarEntry> list = new ArrayList<>();
                list.add(new BarEntry(m.productionEntry.items.get(i).getTotal(), i));
                set = new BarDataSet(list, m.productionEntry.items.get(i).name);
                set.setColor(colors.get(i));
                set.setBarSpacePercent(35f);
                dataSets.add(set);
            }

            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(t);
            barChart.setData(data);

            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
            barChart.animateY(700, AnimationEasing.EasingOption.EaseInCubic);

            barChart.invalidate();
        }
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void run() {
        setBarData();
        setPieData();
    }
}
