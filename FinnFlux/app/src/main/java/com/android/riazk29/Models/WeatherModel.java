package com.android.riazk29.Models;
public class WeatherModel {
    String statusIcon;
    String otherDetails;

    public WeatherModel(String statusIcon, String otherDetails) {
        this.statusIcon = statusIcon;
        this.otherDetails = otherDetails;
    }

    public String getStatusIcon() {
        return statusIcon;
    }

    public void setStatusIcon(String statusIcon) {
        this.statusIcon = statusIcon;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }
}
