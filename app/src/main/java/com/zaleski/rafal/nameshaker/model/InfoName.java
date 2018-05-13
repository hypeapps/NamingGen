package com.zaleski.rafal.nameshaker.model;

import java.util.Objects;

public class InfoName {

    private String name;

    private String voivodeship;

    private Integer year;

    public InfoName(String name, String voivodeship, int year) {
        this.name = name;
        this.voivodeship = voivodeship;
        this.year = year;
    }

    public InfoName() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoName infoName = (InfoName) o;
        return Objects.equals(name, infoName.name) &&
                Objects.equals(voivodeship, infoName.voivodeship) &&
                Objects.equals(year, infoName.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, voivodeship, year);
    }
}
