package utils;

import models.DisasterType;

public class Misc {

    public static String getTableName(DisasterType type) {
        switch (type) {
            case FLOOD:
                return "floods";
            case STORM:
                return "storms";
            case DROUGHT:
                return "droughts";
            case TSUNAMI:
                return "tsunamis";
            case EPIDEMIC:
                return "epidemics";
            case EARTHQUAKE:
                return "earthquakes";
            case PLANE_CRASH:
                return "plane_crashes";
            case EXTREME_TEMPERATURE:
                return "extreme_temperatures";
            default:
                return "floods";
        }
    }
}
