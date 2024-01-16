package com.example.bookingapplication.fragments.analyticsAnnual;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentAnalyticsBinding;
import com.example.bookingapplication.helpers.PdfExporter;
import com.example.bookingapplication.model.AnnualAnalytics;
import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.data.LineData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private LineChart lineAnnualChartEarnings;
    private LineChart lineAnnualChartReservations;
    private AutoCompleteTextView accommodationsTextView;
    private AutoCompleteTextView yearsTextView;
    private Button filterAnnualBtn;
    private Button exportAnnualBtn;
    private HashMap<String, Long> accommodationIds;
    private static final int STORAGE_PERMISSION_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accommodationIds = new HashMap<>();
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        accommodationsTextView = binding.accommodationsTextView;
        yearsTextView = binding.yearsTextView;
        filterAnnualBtn = binding.filterAnnualBtn;
        exportAnnualBtn = binding.exportAnnualBtn;
        lineAnnualChartEarnings = binding.lineAnnualChartEarnings;
        lineAnnualChartReservations = binding.lineAnnualChartReservations;

        setYears();

        filterAnnualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!accommodationsTextView.getText().toString().isEmpty() && !yearsTextView.getText().toString().isEmpty()){
                    getAnnualAnalytics(Integer.parseInt(yearsTextView.getText().toString()), accommodationIds.get(accommodationsTextView.getText().toString()));
                }
            }
        });
        exportAnnualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCharts(lineAnnualChartEarnings, lineAnnualChartReservations, "Annual earnings");
            }
        });

        getAccommodationsForHost();

        setupLineAnnualChart(lineAnnualChartEarnings);
        setupLineAnnualChart(lineAnnualChartReservations);

        return root;
    }

    private void saveCharts(LineChart lineChartEarnings, LineChart lineChartReservations, String fileName) {
        Bitmap chartBitmap1 = getChartBitmap(lineChartEarnings);
        Bitmap chartBitmap2 = getChartBitmap(lineChartReservations);
        PdfExporter.exportToPdf(requireContext(), chartBitmap1, chartBitmap2, fileName);
    }

    private Bitmap getChartBitmap(LineChart lineChart) {
        int width = lineChart.getWidth();
        int height = lineChart.getHeight();
        Bitmap chartBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(chartBitmap);
        lineChart.draw(canvas);
        return chartBitmap;
    }
    private void setYears(){
        yearsTextView.setText(String.valueOf(LocalDate.now().getYear()));
        List<String> years = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            years.add(String.valueOf(LocalDate.now().getYear() - i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, years);
        yearsTextView.setAdapter(adapter);
    }

    private void setupLineAnnualChart(LineChart lineChart) {
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MonthAxisValueFormatter());
    }

    private void setAnnualData(AnnualAnalytics annualAnalytics) {
        List<Entry> entriesEarnings = new ArrayList<>();

        for(int i = 0; i < annualAnalytics.getEarningsPerMonth().length; i++){
            entriesEarnings.add(new Entry((float) i, (float) annualAnalytics.getEarningsPerMonth()[i]));
        }

        LineDataSet dataSetEarnings = new LineDataSet(entriesEarnings, "Annual earnings");
        dataSetEarnings.setColor(getResources().getColor(R.color.dark_blue));
        dataSetEarnings.setValueTextColor(getResources().getColor(R.color.black));

        LineData lineData = new LineData(dataSetEarnings);
        lineAnnualChartEarnings.setData(lineData);

        List<Entry> entriesReservations= new ArrayList<>();
        for(int i = 0; i < annualAnalytics.getReservationsPerMonth().length; i++){
            entriesReservations.add(new Entry((float) i, (float) annualAnalytics.getReservationsPerMonth()[i]));
        }
        LineDataSet dataSetReservations = new LineDataSet(entriesReservations, "Annual reservations");
        dataSetReservations.setColor(getResources().getColor(R.color.dark_blue));
        dataSetReservations.setValueTextColor(getResources().getColor(R.color.black));

        LineData lineDataReservations = new LineData(dataSetReservations);
        lineAnnualChartReservations.setData(lineDataReservations);
        Log.d("HELLO", "USAO");
    }

    private void getAnnualAnalytics(int year, Long accommodationId){
        Call<AnnualAnalytics> call = ClientUtils.analyticsService.getAnnualAnalytics(2024, accommodationId,SharedPreferencesManager.getUserInfo(getContext()).getId());
        call.enqueue(new Callback<AnnualAnalytics>() {
            @Override
            public void onResponse(Call<AnnualAnalytics> call, Response<AnnualAnalytics> response) {
                Log.d("Response", String.valueOf(response.code()));

                if(response.code() == 200){
                    AnnualAnalytics annualAnalytics = response.body();
                    if (annualAnalytics != null){
                        setAnnualData(annualAnalytics);
                    }
                }
            }

            @Override
            public void onFailure(Call<AnnualAnalytics> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private void getAccommodationsForHost(){
        Call<List<Card>> call = ClientUtils.accommodationService.getAccommodationsForHosts(SharedPreferencesManager.getUserInfo(getContext()).getId());
        call.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                Log.d("Response", String.valueOf(response.code()));

                if(response.code() == 200){
                    if(response.body() != null && response.body().size() != 0){
                        accommodationsTextView.setText(response.body().get(0).getTitle());

                        for(Card card: response.body()){
                            accommodationIds.put(card.getTitle(), card.getId());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, new ArrayList<>(accommodationIds.keySet()));
                        accommodationsTextView.setAdapter(adapter);
                        getAnnualAnalytics(LocalDate.now().getYear(), accommodationIds.get(accommodationsTextView.getText().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}