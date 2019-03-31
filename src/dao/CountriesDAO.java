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
import utils.DBService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDAO {

    private static ObservableList<Country> countries = FXCollections.observableArrayList();

    public static ObservableList<Country> getAllCountries(final boolean orderDesc) {
        try {
            String sql = buildQuery().toString();
            sql += orderDesc ? "ORDER BY name DESC;" : ";";

            iterateResultSet(DBService.dbExecuteQuery(sql));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;

    }

    public static ObservableList<Country> getCountries(final String country) {
        try {
            String sql = buildQuery().append("WHERE name LIKE ?;")
                                     .toString();

            PreparedStatement pstm = DBService.getConnection()
                                              .prepareStatement(sql);

            pstm.setString(1, "%" + country + "%");

            iterateResultSet(pstm.executeQuery());

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;
    }

    private static void iterateResultSet(final ResultSet rs) throws SQLException {
        countries.clear();
        try {
            while (rs.next()) {
                countries.add(new Country(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            rs.close();
        }
    }

    private static StringBuilder buildQuery() {
        return new StringBuilder()
                .append("SELECT *\n")
                .append("FROM countries\n");
    }

    public static void main(String[] args) { ObservableList<Country> countries = getCountries("af"); }
}
