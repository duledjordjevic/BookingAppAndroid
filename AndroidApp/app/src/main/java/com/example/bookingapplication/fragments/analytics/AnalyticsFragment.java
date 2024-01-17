package com.example.bookingapplication.fragments.analytics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentAnalyticsBinding;
import com.example.bookingapplication.helpers.DatePickerDialogHelper;
import com.example.bookingapplication.model.DateRange;
import com.example.bookingapplication.util.Charts;
import com.example.bookingapplication.helpers.PdfExporter;
import com.example.bookingapplication.model.Analytics;
import com.example.bookingapplication.model.AnnualAnalytics;
import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Button startDateBtn;
    private Button endDateBtn;
    private DateRange dateRange;
    private Button filterAllAnalyticBtn;
    private PieChart pieAllAnalyticChartEarnings;
    private PieChart pieAllAnalyticChartReservations;
    private Button exportAllAnalyticBtn;
    private Charts charts;
    private DatePickerDialogHelper datePickerDialogHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accommodationIds = new HashMap<>();
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.charts = new Charts();
        this.dateRange = new DateRange();

        accommodationsTextView = binding.accommodationsTextView;
        yearsTextView = binding.yearsTextView;
        filterAnnualBtn = binding.filterAnnualBtn;
        exportAnnualBtn = binding.exportAnnualBtn;
        exportAllAnalyticBtn = binding.exportAllAnalyticBtn;
        lineAnnualChartEarnings = binding.lineAnnualChartEarnings;
        lineAnnualChartReservations = binding.lineAnnualChartReservations;
        startDateBtn = binding.startDateBtn;
        endDateBtn = binding.endDateBtn;
        filterAllAnalyticBtn = binding.filterAllAnalyticBtn;
        pieAllAnalyticChartEarnings = binding.pieAllAnalyticChartEarnings;
        pieAllAnalyticChartReservations = binding.pieAllAnalyticChartReservations;

        setYears();

        datePickerDialogHelper = new DatePickerDialogHelper(startDateBtn, endDateBtn, dateRange);
        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogHelper.showDatePickerDialog(getContext(), "startDate");
            }
        });
        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogHelper.showDatePickerDialog(getContext(), "endDate");
            }
        });

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
                saveCharts(lineAnnualChartEarnings, lineAnnualChartReservations, "Annual earnings and reservations");
            }
        });
        exportAllAnalyticBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCharts(pieAllAnalyticChartEarnings, pieAllAnalyticChartReservations, "All accommodations earnings and reservation");
            }
        }));

        filterAllAnalyticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAllAnalyticChart();
            }
        });

        getAccommodationsForHost();

        charts.setupLineAnnualChart(lineAnnualChartEarnings);
        charts.setupLineAnnualChart(lineAnnualChartReservations);

        LocalDate startDate = LocalDate.now().minusYears(20);
        LocalDate endDate = LocalDate.now().plusYears(20);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("startDate", startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        queryParams.put("endDate", endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        getAnalyticsForAll(queryParams);
        return root;
    }

    private void filterAllAnalyticChart(){
        Map<String, String> queryParams = new HashMap<>();

        if(dateRange.getStartDate() != null){
            if(dateRange.getEndDate() != null){
                if (dateRange.getStartDate().isBefore(dateRange.getEndDate()) || dateRange.getStartDate().isEqual(dateRange.getEndDate())){
                    queryParams.put("startDate", dateRange.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    queryParams.put("endDate", dateRange.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }else{
                    Toast.makeText(getActivity(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
                    return;
                }
            }else{
                Toast.makeText(getActivity(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
                return;
            }
        }else if(dateRange.getEndDate() != null){
            Toast.makeText(getActivity(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
            return;
        }else{
            Toast.makeText(getActivity(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
            return;
        }
        getAnalyticsForAll(queryParams);
    }

    private void saveCharts(Chart chartEarnings, Chart chartReservations, String fileName) {
        Bitmap chartBitmap1 = getChartBitmap(chartEarnings);
        Bitmap chartBitmap2 = getChartBitmap(chartReservations);
        PdfExporter.exportToPdf(requireContext(), chartBitmap1, chartBitmap2, fileName);
    }

    private Bitmap getChartBitmap(Chart chart) {
        int width = chart.getWidth();
        int height = chart.getHeight();
        Bitmap chartBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(chartBitmap);
        chart.draw(canvas);
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



    private void getAnnualAnalytics(int year, Long accommodationId){
        Call<AnnualAnalytics> call = ClientUtils.analyticsService.getAnnualAnalytics(2024, accommodationId,SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId());
        call.enqueue(new Callback<AnnualAnalytics>() {
            @Override
            public void onResponse(Call<AnnualAnalytics> call, Response<AnnualAnalytics> response) {
                Log.d("Response", String.valueOf(response.code()));

                if(response.code() == 200){
                    AnnualAnalytics annualAnalytics = response.body();
                    if (annualAnalytics != null){
                        charts.setAnnualData(lineAnnualChartEarnings, lineAnnualChartReservations, annualAnalytics);
                    }
                }
            }

            @Override
            public void onFailure(Call<AnnualAnalytics> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private void getAnalyticsForAll(Map<String, String> queryParams){
        Call<Collection<Analytics>> call = ClientUtils.analyticsService.getAnalyticsForAll(SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId(), queryParams);
        call.enqueue(new Callback<Collection<Analytics>>() {
            @Override
            public void onResponse(Call<Collection<Analytics>> call, Response<Collection<Analytics>> response) {
                Log.d("Response", String.valueOf(response.code()));

                if(response.code() == 200){
                    if(response.body() != null ){
                        List<Float> dataEarnings = new ArrayList<>();
                        List<Float> dataReservations = new ArrayList<>();
                        List<String> labels = new ArrayList<>();
                        for(Analytics analytic: response.body()){
                            dataEarnings.add((float) analytic.getTotalEarnings());
                            dataReservations.add(analytic.getTotalReservations().floatValue());
                            labels.add(analytic.getName());
                        }
                        charts.setAnalyticsForAll(pieAllAnalyticChartEarnings, dataEarnings, labels, "Earnings");
                        charts.setAnalyticsForAll(pieAllAnalyticChartReservations, dataReservations, labels, "Reservations");
                    }


                }
            }

            @Override
            public void onFailure(Call<Collection<Analytics>> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }


    private void getAccommodationsForHost(){
        Call<List<Card>> call = ClientUtils.accommodationService.getAccommodationsForHosts(SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId());
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



    public static AnalyticsFragment newInstance() {

        Bundle args = new Bundle();

        AnalyticsFragment fragment = new AnalyticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}