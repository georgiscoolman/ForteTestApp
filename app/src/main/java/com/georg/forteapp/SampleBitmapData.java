package com.georg.forteapp;

/**
 * Created by Georg on 14.04.2016.
 */
public class SampleBitmapData {

    private String filePath;
    private int reqWidth;
    private int reqHeight;

    public SampleBitmapData(String filePath, int reqWidth, int reqHeight) {
        this.filePath = filePath;
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getReqWidth() {
        return reqWidth;
    }

    public int getReqHeight() {
        return reqHeight;
    }
}
