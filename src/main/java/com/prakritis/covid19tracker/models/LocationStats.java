package com.prakritis.covid19tracker.models;

public class LocationStats {

    private String country;
    private String state;
    private Integer latestCaseCounts;
    private Integer diffFromPrevDay;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getLatestCaseCounts() {
        return latestCaseCounts;
    }

    public void setLatestCaseCounts(Integer latestCaseCounts) {
        this.latestCaseCounts = latestCaseCounts;
    }

    public Integer getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(Integer diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }
}
