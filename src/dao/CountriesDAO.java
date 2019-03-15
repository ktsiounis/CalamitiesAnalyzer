package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import utils.DBService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDAO {

    public static ObservableList<Country> getAllCountries(boolean orderDesc) {

        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * \n"
                       + "FROM countries \n";

            sql += orderDesc ? "ORDER BY name DESC" : "";

            ResultSet rs = DBService.dbExecuteQuery(sql);


            while (rs.next()) {
                int id = rs.getInt("id");
                String country = rs.getString("name");
                countries.add(new Country(id, country));

                System.out.println("Country: " + country);
            }

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;

    }

    public static ObservableList<Country> getCountries(String country) {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT *\n");
            stringBuilder.append("FROM countries\n");
            stringBuilder.append("WHERE name LIKE ?;");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setString(1, "%" + country + "%");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                countries.add(new Country(id, name));

                System.out.println("Country: " + name);

            }
            DBService.dbDisconnect();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;
    }

    public static void main(String[] args) { ObservableList<Country> countries = getCountries("af"); }
}
