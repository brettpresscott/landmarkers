package com.example.maplication;

public class AppliedSettiings {
    String darkMode;
    String distanceScale;
    String preferredLandmarks;

    public AppliedSettiings(String darkMode, String distanceScale, String preferredLandmarks){
        this.darkMode = darkMode;
        this.distanceScale = distanceScale;
        this.preferredLandmarks = preferredLandmarks;
    }

    public String getDarkMode() {
        return darkMode;
    }

    public void setDarkMode(String darkMode) {
        this.darkMode = darkMode;
    }

    public String getDistanceScale() {
        return distanceScale;
    }

    public void setDistanceScale(String distanceScale) {
        this.distanceScale = distanceScale;
    }

    public String getPreferredLandmarks() {
        return preferredLandmarks;
    }

    public void setPreferredLandmarks(String preferredLandmarks) {
        this.preferredLandmarks = preferredLandmarks;
    }
}
