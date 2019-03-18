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

    public static ObservableList<Year> getAllYears(boolean orderDesc) {

//        ObservableList<Year> years = FXCollections.observableArrayList();

        try {
            StringBuilder stringBuilder = buildQuery();
            String sql = stringBuilder.toString();
            sql += orderDesc ? "ORDER BY year DESC;" : ";";

            ResultSet rs = DBService.dbExecuteQuery(sql);

            iterateResultSet(rs);

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return years;

    }

    public static ObservableList<Year> getYearsRange(int from, int to) {

//        ObservableList<Year> years = FXCollections.observableArrayList();

        try {
            StringBuilder stringBuilder = buildQuery();
            stringBuilder.append("WHERE year BETWEEN ? AND ?;");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setInt(1, from);
            pstm.setInt(2, to);
            ResultSet rs = pstm.executeQuery();

            iterateResultSet(rs);
            DBService.dbDisconnect();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return years;
    }

    private static StringBuilder buildQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT *\n");
        stringBuilder.append("FROM years\n");

        return stringBuilder;
    }

    private static void iterateResultSet(ResultSet rs) throws SQLException {
        years.clear();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                int year = rs.getInt("year");
                years.add(new Year(id, year));
                System.out.println("Year: " + year);

            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ObservableList<Year> years = getYearsRange(1900, 1915);
    }

}
