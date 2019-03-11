package models;

public class Year {

    private int id;
    private int year;

    public Year(int id, int year) {
        this.id = id;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Year{" +
                "id=" + id +
                ", year=" + year +
                '}';
    }
}
