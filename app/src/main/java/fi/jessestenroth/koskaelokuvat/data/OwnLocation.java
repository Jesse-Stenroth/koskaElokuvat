package fi.jessestenroth.koskaelokuvat.data;

/**
 * This class hold basic information of location
 * @author Jesse Stenroth
 */
public class OwnLocation {
    private double longitude;
    private double latitude;
    private String name;
    private String code;

    /**
     * This constructor set data of location and area information
     * @param longitude longitude information
     * @param latitude latitude information
     * @param name name of area
     * @param code id code of area
     */
    public OwnLocation(double longitude, double latitude, String name, String code) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.code = code;
    }

    /**
     * This method return longitude
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * This method return latitude
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * This method return area name
     * @return name of area
     */
    public String getName() {
        return name;
    }

    /**
     * This method return area id code
     * @return id code of area
     */
    public String getCode() {
        return code;
    }
}
