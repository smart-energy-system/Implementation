package com.github.smartenergysystem.price.collector.priceCollector;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "DAY_AHEAD_PRICE_POINT")
public class DayAheadPricePoint {




    public DayAheadPricePoint() {
    }

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mmZ", timezone= "UTC")
    @Id
    private Date time;
    private double priceInEuroPerMWh;

    @JsonInclude()
    @Transient
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
