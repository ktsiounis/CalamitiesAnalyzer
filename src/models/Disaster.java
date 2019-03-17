package models;

public class Disaster {

    private int id;
    private Country country;
    private Year year;
    private DisasterType disaster;
    private int affected;
    private int deaths;

    public Disaster(int id, Country country, Year year, DisasterType disaster, int affected, int deaths) {
        this.id = id;
        this.country = country;
        this.year = year;
        this.disaster = disaster;
        this.affected = affected;
        this.deaths = deaths;
    }

    public int getId() { return id; }

    public Country getCountry() { return country; }

    public Year getYear() { return year; }

    public DisasterType getDisaster() { return disaster; }

    public int getAffected() { return affected; }

    public int getDeaths() { return deaths; }

    @Override
    public String toString() {
        return "Disaster{" +
                "id=" + id +
                ", country=" + country +
                ", year=" + year +
                ", disaster type=" + disaster +
                ", affected=" + affected +
                ", deaths=" + deaths +
                '}';
    }
}
