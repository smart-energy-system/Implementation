package com.github.smartenergysystem.services;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DayAheadPricePoint {

    public DayAheadPricePoint() {
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmZ", timezone = "UTC")
    private Date time;
    private double priceInEuroPerMWh;

    private boolean isOldValue = false;

    public boolean isOldValue() {
        return isOldValue;
    }

    public void setOldValue(boolean oldValue) {
        isOldValue = oldValue;
    }

    public DayAheadPricePoint(Date time, double priceInEuroPerMWh) {
        this.time = time;
        this.priceInEuroPerMWh = priceInEuroPerMWh;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getPriceInEuroPerMWh() {
        return priceInEuroPerMWh;
    }

    public void setPriceInEuroPerMWh(double priceInEuroPerMWh) {
        this.priceInEuroPerMWh = priceInEuroPerMWh;
    }

    @Override
    public String toString() {
        return "DayAheadPricePoint{" +
                "time=" + time +
                ", priceInEuroPerMWh=" + priceInEuroPerMWh +
                ", isOldValue=" + isOldValue +
                '}';
    }
}
