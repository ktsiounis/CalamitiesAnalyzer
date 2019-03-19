package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Disaster;
import models.DisasterType;
import models.Year;
import utils.DBService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisasterDAO {

    private static ObservableList<Disaster> disasters = FXCollections.observableArrayList();

    public static ObservableList<Disaster> getAllForDisaster(DisasterType type) {
        try {
            StringBuilder stringBuilder = buildQuery(type);
            stringBuilder.append(";");
            String sql = stringBuilder.toString();

            iterateResultSet(DBService.dbExecuteQuery(sql), type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disasters;
    }

    public static ObservableList<Disaster> getDisasterForCountry(DisasterType type, String countryName) {
        try {
            StringBuilder stringBuilder = buildQuery(type);
            stringBuilder.append("\n");
            stringBuilder.append("AND c.name LIKE ?;");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setString(1, "%" + countryName + "%");

            iterateResultSet(pstm.executeQuery(), type);

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disasters;
    }

    private static void iterateResultSet(ResultSet rs, DisasterType type) throws SQLException {
        disasters.clear();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                int countryID = rs.getInt("country_id");
                int yearID = rs.getInt("year_id");
                int affected = rs.getInt("affected");
                int deaths = rs.getInt("deaths");
                int year = rs.getInt("year");
                String country = rs.getString("name");

                disasters.add(new Disaster(id,
                        new Country(countryID, country),
                        new Year(yearID, year),
                        type, affected, deaths));

                System.out.println(country + " " + year + " " + affected + " " + deaths);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            rs.close();
        }
    }

    private static StringBuilder buildQuery(DisasterType type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT d.id, d.country_id, d.year_id, d.affected, d.deaths, y.year, c.name\n");
        stringBuilder.append("FROM " + type.getTableName() + " ");
        stringBuilder.append("as d, countries as c, years as y\n");
        stringBuilder.append("WHERE d.country_id = c.id AND d.year_id = y.id");

        return stringBuilder;
    }

    public static void main(String[] args) {
        ObservableList<Disaster> disasters = getDisasterForCountry(DisasterType.EPIDEMIC, "af");
        disasters.forEach(dis -> {
            System.out.println(dis);
        });
    }
}