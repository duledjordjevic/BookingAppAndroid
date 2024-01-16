package com.example.bookingapplication.model;

import java.util.Arrays;

public class AnnualAnalytics extends  Analytics{

    public Long[] reservationsPerMonth;
    public double[] earningsPerMonth;

    public AnnualAnalytics(){
        reservationsPerMonth = new Long[12];
        Arrays.fill(reservationsPerMonth, 0L);

        earningsPerMonth = new double[12];
        Arrays.fill(earningsPerMonth, 0);
    }

    public AnnualAnalytics(Long[] reservationsPerMonth, double[] earningsPerMonth) {
        this.reservationsPerMonth = reservationsPerMonth;
        this.earningsPerMonth = earningsPerMonth;
    }

    public AnnualAnalytics(Long accommodationId, String name, Long totalReservations, double totalEarnings, Long[] reservationsPerMonth, double[] earningsPerMonth) {
        super(accommodationId, name, totalReservations, totalEarnings);
        this.reservationsPerMonth = reservationsPerMonth;
        this.earningsPerMonth = earningsPerMonth;
    }

    public Long[] getReservationsPerMonth() {
        return reservationsPerMonth;
    }

    public void setReservationsPerMonth(Long[] reservationsPerMonth) {
        this.reservationsPerMonth = reservationsPerMonth;
    }

    public double[] getEarningsPerMonth() {
        return earningsPerMonth;
    }

    public void setEarningsPerMonth(double[] earningsPerMonth) {
        this.earningsPerMonth = earningsPerMonth;
    }
}