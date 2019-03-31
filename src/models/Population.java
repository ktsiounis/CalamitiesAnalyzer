/*
 * ChainProof
 * Tsiounis Konstantinos 2630
 * Polyzos Alexios 2338
 * Copyright (c) 2019.
 */

package models;

public class Population {

    private int id;
    private Country country;
    private Year year;
    private int population;

    public Population(int id, Country country, Year year, int population) {
        this.id = id;
        this.country = country;
        this.year = year;
        this.population = population;
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

    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return "Population{" +
                "id=" + id +
                ", country=" + country +
                ", year=" + year +
                ", population=" + population +
                '}';
    }
}
