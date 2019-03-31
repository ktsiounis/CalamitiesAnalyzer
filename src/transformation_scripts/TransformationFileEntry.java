/*
 * ChainProof
 * Tsiounis Konstantinos 2630
 * Polyzos Alexios 2338
 * Copyright (c) 2019.
 */

package transformation_scripts;

public class TransformationFileEntry {

    private String countryId;
    private String yearId;
    private String affected;
    private String deaths;

    public TransformationFileEntry(String countryId, String yearId, String affected, String deaths) {
        this.countryId = countryId;
        this.yearId = yearId;
        this.affected = affected;
        this.deaths = deaths;
    }

    public TransformationFileEntry() {

    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getYearId() {
        return yearId;
    }

    public void setYearId(String yearId) {
        this.yearId = yearId;
    }

    public String getAffected() {
        return affected;
    }

    public void setAffected(String affected) {
        this.affected = affected;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return countryId + "," + yearId + "," + affected + "," + deaths;
    }
}
