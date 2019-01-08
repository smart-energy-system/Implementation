package com.github.smartenergysystem.price.collector.priceCollector;

import java.util.Date;

public class DayAheadPricePoint {
    private Date time;
    private double priceInCentPerWatt;

    public DayAheadPricePoint(Date time, double priceInCentPerWatt) {
        this.time = time;
        this.priceInCentPerWatt = priceInCentPerWatt;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getPriceInCentPerWatt() {
        return priceInCentPerWatt;
    }

    public void setPriceInCentPerWatt(double priceInCentPerWatt) {
        this.priceInCentPerWatt = priceInCentPerWatt;
    }

    @Override
    public String toString() {
        return "DayAheadPricePoint{" +
                "time=" + time +
                ", priceInCentPerWatt=" + priceInCentPerWatt +
                '}';
    }
}
