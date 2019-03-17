package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Disaster;
import models.DisasterType;
import models.Year;
import utils.DBService;
import utils.Misc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DisasterDAO {

    public static ObservableList<Disaster> getAllForDisaster(DisasterType type) {

        ObservableList<Disaster> disasters = FXCollections.observableArrayList();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT d.id, d.country_id, d.year_id, d.affected, d.deaths, y.year, c.name\n");
            stringBuilder.append("FROM ");
            stringBuilder.append(Misc.getTableName(type));
            stringBuilder.append(" as d, countries as c, years as y\n");
            stringBuilder.append("WHERE d.country_id = c.id AND d.year_id = y.id;");
            String sql = stringBuilder.toString();

            ResultSet rs = DBService.dbExecuteQuery(sql);

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
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return disasters;
    }

    public static ObservableList<Disaster> getDisasterForCountry(DisasterType type, String countryName) {

        ObservableList<Disaster> disasters = FXCollections.observableArrayList();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT d.id, d.country_id, d.year_id, d.affected, d.deaths, y.year, c.name\n");
            stringBuilder.append("FROM ");
            stringBuilder.append(Misc.getTableName(type));
            stringBuilder.append(" as d, countries as c, years as y\n");
            stringBuilder.append("WHERE d.country_id = c.id AND d.year_id = y.id\n");
            stringBuilder.append("AND c.name LIKE ?");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setString(1, "%" + countryName + "%");
            ResultSet rs = pstm.executeQuery();

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
            DBService.dbDisconnect();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return disasters;
    }

    public static void main(String[] args) {
        ObservableList<Disaster> disasters = getDisasterForCountry(DisasterType.EPIDEMIC, "af");
        disasters.forEach(dis -> {
            System.out.println(dis);
        });
    }
}