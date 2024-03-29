/*
 * ChainProof
 * Tsiounis Konstantinos 2630
 * Polyzos Alexios 2338
 * Copyright (c) 2019.
 */

package transformation_scripts;

public class TransformationIncomeAndPopulationFileEntry {

    private String countryId;
    private String yearId;
    private String value;

    public TransformationIncomeAndPopulationFileEntry(String countryId, String yearId, String value) {
        this.countryId = countryId;
        this.yearId = yearId;
        this.value = value;
    }

    public TransformationIncomeAndPopulationFileEntry() {

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return countryId + "," + yearId + "," + value;
    }
}
