package com.rohankar.mapservice.bean;

import java.math.BigDecimal;

import com.google.common.base.Objects;

/**
 * @author Sagar Rohankar
 */
public class Poi {

    private String id;
    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(final BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(final BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .omitNullValues()
            .add("id", getId())
            .add("name", getName())
            .add("lat", getLatitude())
            .add("long", getLongitude())
            .toString();
    }
}
