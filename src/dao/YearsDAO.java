package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Year;
import utils.DBService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class YearsDAO {

    public static ObservableList<Year> getAllYears(boolean orderDesc) {

        ObservableList<Year> years = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * \n"
                       + "FROM years \n";

            sql += orderDesc ? "ORDER BY year DESC" : "";

            ResultSet rs = DBService.dbExecuteQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int year = rs.getInt("year");

                years.add(new Year(id, year));

                System.out.println("Year: " + year);
            }

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return years;

    }

    public static ObservableList<Year> getYearsRange(int from, int to) {

        ObservableList<Year> years = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * \n"
                       + "FROM years \n"
                       + "WHERE year BETWEEN ? AND ?;";

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setInt(1, from);
            pstm.setInt(2, to);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int year = rs.getInt("year");

                years.add(new Year(id, year));

                System.out.println("Year: " + year);

            }
            DBService.dbDisconnect();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return years;
    }

    public static void main(String[] args) {
        ObservableList<Year> years = getYearsRange(1900, 1915);
    }

}
