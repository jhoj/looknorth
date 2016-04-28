package fo.looknorth.view;

import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Arrays;

import fo.looknorth.app.app.R;
import fo.looknorth.logik.Logik;
import fo.looknorth.model.Machine;

import fo.looknorth.model.ProductionCounter;

public class ProductionContentFragment extends Fragment implements OnChartValueSelectedListener {

    public int tabIndex;
    private BarChart barChart;
    private PieChart mChart;

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

        tabIndex = getArguments().getInt("tabIndex");

        TextView dateText = (TextView) rootView.findViewById(R.id.dateText);
        dateText.setText(Logik.instance.getDate());

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
        ArrayList<Machine> machines = new ArrayList<>(Arrays.asList(Logik.instance.machines));
        Machine machine = machines.get(tabIndex);  //this is the total section

        for (int i = 0; i < machine.productionCounterList.size(); i++)
        {
            //it has 2 entries
            //two names added to pie chart.
            xVals.add(machine.productionCounterList.get(i).product.name);
            //shows the product percentage of the whole production
            float pieTotal = machine.getTotalProducedItems();
            float productionQuantity = machine.productionCounterList.get(i).quantityProduced;
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
        Machine m = Logik.instance.machines[tabIndex];
        ArrayList<String> xVals = new ArrayList<>();

        for (ProductionCounter p: m.productionCounterList) {
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

        for (int i = 0; i < m.productionCounterList.size(); i++) {
            ArrayList<BarEntry> list = new ArrayList<>();
            list.add(new BarEntry(m.productionCounterList.get(i).quantityProduced, i));
            set = new BarDataSet(list, m.productionCounterList.get(i).product.name);
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
