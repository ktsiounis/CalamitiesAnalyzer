package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDAO {

    public static ObservableList<String> getAllCountries(boolean orderDesc) {

        ObservableList<String> countries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * \n"
                    + "FROM countries \n";

            sql += orderDesc ? "ORDER BY name DESC" : "";

            ResultSet rs = DBService.dbExecuteQuery(sql);


            while (rs.next()) {
                String country = rs.getString("name");
                countries.add(country);

                System.out.println("Country: " + country);
            }

            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;

    }

}
