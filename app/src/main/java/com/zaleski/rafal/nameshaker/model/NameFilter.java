package com.zaleski.rafal.nameshaker.model;

public class NameFilter {

    private boolean isMale;

    private boolean isFemale;

    private String voivodeship;

    public NameFilter() {
    }

    public NameFilter(Boolean isFemale, Boolean isMale) {
        this.isFemale = isFemale;
        this.isMale = isMale;
    }

    public NameFilter(Boolean isFemale, Boolean isMale, String voivodeship) {
        this(isFemale, isMale);
        this.voivodeship = voivodeship;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public void setFemale(boolean isFemale) {
        isFemale = isFemale;
    }

    public void setMale(boolean isMale) {
        isMale = isMale;
    }

    public Boolean isFemale() {
        return isFemale;
    }

    public Boolean isMale() {
        return isMale;
    }

    public Boolean isEachSex() {
        return isFemale && isMale;
    }
}
