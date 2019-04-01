/*
 * ChainProof
 * Tsiounis Konstantinos 2630
 * Polyzos Alexios 2338
 * Copyright (c) 2019.
 */

package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Population;
import models.Year;
import utils.DBService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopulationDAO {

    private static ObservableList<Population> populationList = FXCollections.observableArrayList();

    public static ObservableList<Population> getPopulationForYear(final int populationYear) {

        try {
            String sql = buildQuery().append("AND y.year = ?;")
                                     .toString();

            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setInt(1, populationYear);

            iterateResultSet(pstm.executeQuery());

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return populationList;
    }

    public static ObservableList<Population> getPopulationForYearRange(final int from, final int to) {

        try {
            String sql = buildQuery().append("AND y.year BETWEEN ? AND ?;")
                                     .toString();

            PreparedStatement pstm = DBService.getConnection()
                                              .prepareStatement(sql);

            pstm.setInt(1, from);
            pstm.setInt(2, to);

            iterateResultSet(pstm.executeQuery());

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return populationList;
    }

    public static ObservableList<Population> getPopulationForCountry(final String name) {

        try {
            String sql = buildQuery().append("AND c.name LIKE ?;")
                                     .toString();

            PreparedStatement pstm = DBService.getConnection()
                                              .prepareStatement(sql);

            pstm.setString(1, "%" + name + "%");

            iterateResultSet(pstm.executeQuery());

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return populationList;
    }

    public static ObservableList<Population> getPopulationForCountryInAPeriod(final int from,
                                                                              final int to,
                                                                              final String name) {
        try {
            String sql = buildQuery().append("AND c.name LIKE ?\n")
                                     .append("AND y.year BETWEEN ? AND ?;")
                                     .toString();

            PreparedStatement pstm = DBService.getConnection()
                                              .prepareStatement(sql);

            pstm.setString(1, name);
            pstm.setInt(2, from);
            pstm.setInt(3, to);

            iterateResultSet(pstm.executeQuery());

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return populationList;
    }

    private static void iterateResultSet(final ResultSet rs) throws SQLException {
        populationList.clear();
        try {
            while (rs.next()) {
                populationList.add(new Population(rs.getInt("id"),
                                   new Country(rs.getInt("country_id"), rs.getString("name")),
                                   new Year(rs.getInt("year_id"), rs.getInt("year")),
                                   rs.getInt("population")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            rs.close();
        }
    }

    private static StringBuilder buildQuery() {
        return new StringBuilder()
                .append("SELECT p.id, p.country_id, p.year_id, p.population, y.year, c.name\n")
                .append("FROM population as p, years as y, countries as c\n")
                .append("WHERE y.id = p.year_id\n")
                .append("AND p.country_id = c.id\n");
    }

    public static void main(String[] args) {
        ObservableList<Population> population = getPopulationForCountry("AF");
        population.forEach(pop -> {
            System.out.println(pop.getCountry().getName());
            System.out.println(pop);
        });
    }
}

