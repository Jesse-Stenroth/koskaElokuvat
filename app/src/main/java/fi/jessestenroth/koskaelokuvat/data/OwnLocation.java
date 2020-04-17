package fi.jessestenroth.koskaelokuvat.data;

public class OwnLocation {
    private double longitude;
    private double latitude;
    private String name;
    private String code;

    public OwnLocation(double longitude, double latitude, String name, String code) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.code = code;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
