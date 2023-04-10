package com.netsprogram.covidstatistic.ui.dashboard;

public class ContinentCovid {
    String mContinent;
    String mCases;
    String mDeaths;
    String mRecovered;

    public String getmContinent() {
        return mContinent;
    }

    public void setmContinent(String mContinent) {
        this.mContinent = mContinent;
    }

    public String getmCases() {
        return mCases;
    }

    public void setmCases(String mCases) {
        this.mCases = mCases;
    }

    public String getmDeaths() {
        return mDeaths;
    }

    public void setmDeaths(String mDeaths) {
        this.mDeaths = mDeaths;
    }

    public String getmRecovered() {
        return mRecovered;
    }

    public void setmRecovered(String mRecovered) {
        this.mRecovered = mRecovered;
    }

    public ContinentCovid(String mContinent, String mCases, String mDeaths, String mRecovered) {
        this.mContinent = mContinent;
        this.mCases = mCases;
        this.mDeaths = mDeaths;
        this.mRecovered = mRecovered;
    }
}