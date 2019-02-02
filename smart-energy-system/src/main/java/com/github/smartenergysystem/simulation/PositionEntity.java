package com.github.smartenergysystem.simulation;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PositionEntity {

    @Column(name = "lat")
    @ApiModelProperty(required = true, example = "31.230391")
    protected double latitude;

    @Column(name = "lon")
    @ApiModelProperty(required = true, example = "121.473701")
    protected double longitude;


    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
