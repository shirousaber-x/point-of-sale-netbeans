package com.netsprogram.covidstatistic.ui.dashboard;

public class CountryCovid {
    String mFlag;
    String mCountry;
    String mCases;
    String mDeaths;
    String mRecovered;

    public String getmFlag() {
        return mFlag;
    }

    public void setmFlag(String mFlag) {
        this.mFlag = mFlag;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
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

    public CountryCovid(String mFlag, String mCountry, String mCases, String mDeaths, String mRecovered) {
        this.mFlag = mFlag;
        this.mCountry = mCountry;
        this.mCases = mCases;
        this.mDeaths = mDeaths;
        this.mRecovered = mRecovered;
    }
}
