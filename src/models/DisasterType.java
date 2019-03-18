package models;

public enum DisasterType {
    DROUGHT("Droughts"),
    EARTHQUAKE("Earthquakes"),
    EPIDEMIC("Epidemics"),
    EXTREME_TEMPERATURE("Extreme Temperatures"),
    FLOOD("Floods"),
    PLANE_CRASH("Plane Crashes"),
    STORM("Storms"),
    TSUNAMI("Tsunamis");

    private String name;

    DisasterType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public String getTableName() {
        return  name.toLowerCase().replace(" ", "_");
    }
}