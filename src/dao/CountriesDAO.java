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

    public static ObservableList<Country> getAllCountries(boolean orderDesc) {
        try {
            StringBuilder stringBuilder = buildQuery();
            String sql = stringBuilder.toString();
            sql += orderDesc ? "ORDER BY name DESC;" : ";";

            iterateResultSet(DBService.dbExecuteQuery(sql));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;

    }

    public static ObservableList<Country> getCountries(String country) {
        try {
            StringBuilder stringBuilder = buildQuery();
            stringBuilder.append("WHERE name LIKE ?;");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setString(1, "%" + country + "%");

            iterateResultSet(pstm.executeQuery());
            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;
    }

    private static void iterateResultSet(ResultSet rs) throws SQLException {
        countries.clear();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                countries.add(new Country(id, name));
                System.out.println("Country: " + name);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            rs.close();
        }
    }

    private static StringBuilder buildQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT *\n");
        stringBuilder.append("FROM countries\n");

        return  stringBuilder;
    }

    public static void main(String[] args) { ObservableList<Country> countries = getCountries("af"); }
}
