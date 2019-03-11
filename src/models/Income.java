package models;

public class Income {

    private int id;
    private Country country;
    private Year year;
    private int income;

    public Income(int id, Country country, Year year, int income) {
        this.id = id;
        this.country = country;
        this.year = year;
        this.income = income;
    }

    public int getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public Year getYear() {
        return year;
    }

    public int getIncome() {
        return income;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", country=" + country +
                ", year=" + year +
                ", income=" + income +
                '}';
    }
}
