package com.android.riazk29.Models;
/**
 * Represents an area model with a code and an area name.
 */
public class AreaModel {
    private String code; // The code representing the area
    private String area; // The name of the area

    /**
     * Constructs a new AreaModel with the specified code and area name.
     *
     * @param code The code representing the area.
     * @param area The name of the area.
     */
    public AreaModel(String code, String area) {
        this.code = code;
        this.area = area;
    }

    /**
     * Gets the code representing the area.
     *
     * @return The code representing the area.
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the name of the area.
     *
     * @return The name of the area.
     */
    public String getArea() {
        return area;
    }
}
