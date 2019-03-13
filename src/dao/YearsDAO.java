package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YearsDAO {

    public static ObservableList<String> getAllYears(boolean orderDesc) {

        ObservableList<String> years = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * \n"
                    + "FROM years \n";

            sql += orderDesc ? "ORDER BY year DESC" : "";

            ResultSet rs = DBService.dbExecuteQuery(sql);

            while (rs.next()) {
                int year = rs.getInt("year");

                years.add(String.valueOf(year));

                System.out.println("Year: " + year);
            }

            rs.close();

        } catch (Exception se) {
            se.printStackTrace();
        }


        return years;

    }

}
