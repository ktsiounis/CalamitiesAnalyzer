/*
 * ChainProof
 * Tsiounis Konstantinos 2630
 * Polyzos Alexios 2338
 * Copyright (c) 2019.
 */

package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Year;
import utils.DBService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YearsDAO {

    private static ObservableList<Year> years = FXCollections.observableArrayList();

    public static ObservableList<Year> getAllYears(final boolean orderDesc) {
        try {
            String sql = buildQuery().toString();
            sql += orderDesc ? "ORDER BY year DESC;" : ";";

            iterateResultSet(DBService.dbExecuteQuery(sql));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return years;

    }

    public static ObservableList<Year> getYearsRange(final int from, final int to) {
        try {
            String sql = buildQuery().append("WHERE year BETWEEN ? AND ?;")
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

        return years;
    }

    private static StringBuilder buildQuery() {
        return new StringBuilder()
                .append("SELECT *\n")
                .append("FROM years\n");
    }

    private static void iterateResultSet(final ResultSet rs) throws SQLException {
        years.clear();
        try {
            while (rs.next()) {
                years.add(new Year(rs.getInt("id"), rs.getInt("year")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            rs.close();
        }
    }

    public static void main(String[] args) {
        ObservableList<Year> years = getYearsRange(1900, 1915);
    }

}
