package com.example.earthquakes;

public class Earthquake {

    private final double mMag;
    private final long mTime;
    private final String mPlace;
    private final String mUrl;

    public Earthquake(double Mag, long Time, String Place, String Url) {
        mMag = Mag;
        mTime = Time;
        mPlace = Place;
        mUrl = Url;
    }

    public double getMag() {
        return mMag;
    }

    public long getTime() {
        return mTime;
    }

    public String getPlace() {
        return mPlace;
    }

    public String getUrl() {
        return mUrl;
    }
}
