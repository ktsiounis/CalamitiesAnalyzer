package transformation_scripts;

public class TranformationIncomeAndPopulationFileEntry {

    private String countryId;
    private String yearId;
    private String value;

    public TranformationIncomeAndPopulationFileEntry(String countryId, String yearId, String value) {
        this.countryId = countryId;
        this.yearId = yearId;
        this.value = value;
    }

    public TranformationIncomeAndPopulationFileEntry() {

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
