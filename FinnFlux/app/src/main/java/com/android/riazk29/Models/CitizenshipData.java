package com.android.riazk29.Models;
/**
 * Represents citizenship data with region, year, gender, and count.
 */
public class CitizenshipData {
    private String region; // The region of the citizenship data
    private int year; // The year of the citizenship data
    private String gender; // The gender of the citizenship data
    private int count; // The count of citizenship

    /**
     * Constructs a new CitizenshipData object with the specified region, year, gender, and count.
     *
     * @param region The region of the citizenship data.
     * @param year   The year of the citizenship data.
     * @param gender The gender of the citizenship data.
     * @param count  The count of citizenship.
     */
    public CitizenshipData(String region, int year, String gender, int count) {
        this.region = region;
        this.year = year;
        this.gender = gender;
        this.count = count;
    }

    /**
     * Gets the region of the citizenship data.
     *
     * @return The region of the citizenship data.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region of the citizenship data.
     *
     * @param region The region to set.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the year of the citizenship data.
     *
     * @return The year of the citizenship data.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the citizenship data.
     *
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the gender of the citizenship data.
     *
     * @return The gender of the citizenship data.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the citizenship data.
     *
     * @param gender The gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


