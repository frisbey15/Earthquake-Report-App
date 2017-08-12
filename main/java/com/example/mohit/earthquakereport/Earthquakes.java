package com.example.mohit.earthquakereport;

import java.util.Date;

/**
 * Created by mohit on 17/5/17.
 */

public class Earthquakes {

    private String mMagnitude;
    private String mPlace;
    private String mQuakeDate;
    private String mQuakeTime;
    private String mPageUrl;

    public Earthquakes(String magnitude, String place, String quakeDate, String quakeTime,String pageurl){
        mMagnitude=magnitude;
        mPlace=place;
        mQuakeDate=quakeDate;
        mQuakeTime=quakeTime;
        mPageUrl=pageurl;
    }
    public String getMagnitude(){
        return mMagnitude;
    }
    public String getPlace(){
        return mPlace;
    }
    public String getQuakeDate(){
        return  mQuakeDate;
    }
    public String getQuakeTime(){return mQuakeTime; }
    public String getPageUrl(){return mPageUrl;    }
}
