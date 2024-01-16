package com.example.bookingapplication.util;

import android.graphics.Color;

import com.example.bookingapplication.model.AnnualAnalytics;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Charts {
    private void setupPieChart(PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setRotationEnabled(true);
        pieChart.setRotationAngle(0);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);
    }
    private void setPieChartData(PieChart pieChart, List<PieEntry> entries, String chartLabel) {
        PieDataSet dataSet = new PieDataSet(entries, chartLabel);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            colors.add(getRandomColor());
        }

        dataSet.setColors(colors);

        dataSet.setDrawValues(true);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.BLACK);

        CustomValueFormatter customValueFormatter = new CustomValueFormatter();
        pieData.setValueFormatter(customValueFormatter);

        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    public class CustomValueFormatter extends PercentFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return super.getFormattedValue(value, entry, dataSetIndex, viewPortHandler) + " - " + (int) entry.getY();
        }
    }

    private List<PieEntry> preparePieEntries(List<Float> data, List<String> labels) {
        List<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            entries.add(new PieEntry(data.get(i), labels.get(i)));
        }

        return entries;
    }
    public void setAnalyticsForAll(PieChart pieChart, List<Float> apartmentData, List<String> apartmentLabels, String chartName) {
        setupPieChart(pieChart);

        List<PieEntry> entries = preparePieEntries(apartmentData, apartmentLabels);
        setPieChartData(pieChart, entries, chartName);
    }
    private int getRandomColor() {
        Random rnd = new Random();
        int r = 150 + rnd.nextInt(56);
        int g = 150 + rnd.nextInt(56);
        int b = 150 + rnd.nextInt(56);
        return Color.rgb(r, g, b);
    }

    public void setupLineAnnualChart(LineChart lineChart) {
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MonthAxisValueFormatter());
    }

    public void setAnnualData(LineChart lineAnnualChartEarnings, LineChart lineAnnualChartReservations, AnnualAnalytics annualAnalytics) {
        List<Entry> entriesEarnings = new ArrayList<>();

        for(int i = 0; i < annualAnalytics.getEarningsPerMonth().length; i++){
            entriesEarnings.add(new Entry((float) i, (float) annualAnalytics.getEarningsPerMonth()[i]));
        }

        LineDataSet dataSetEarnings = new LineDataSet(entriesEarnings, "Annual earnings");
        dataSetEarnings.setColor(getRandomColor());
        dataSetEarnings.setValueTextColor(getRandomColor());

        LineData lineData = new LineData(dataSetEarnings);
        lineAnnualChartEarnings.setData(lineData);

        List<Entry> entriesReservations= new ArrayList<>();
        for(int i = 0; i < annualAnalytics.getReservationsPerMonth().length; i++){
            entriesReservations.add(new Entry((float) i, (float) annualAnalytics.getReservationsPerMonth()[i]));
        }
        LineDataSet dataSetReservations = new LineDataSet(entriesReservations, "Annual reservations");
        dataSetReservations.setColor(getRandomColor());
        dataSetReservations.setValueTextColor(getRandomColor());

        LineData lineDataReservations = new LineData(dataSetReservations);
        lineAnnualChartReservations.setData(lineDataReservations);
    }

    private static class MonthAxisValueFormatter implements IAxisValueFormatter {
        private final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int index = (int) value;
            if (index >= 0 && index < months.length) {
                return months[index];
            }
            return "";
        }
    }
}
