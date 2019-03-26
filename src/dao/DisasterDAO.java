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

    public static ObservableList<Disaster> getAllForDisaster(final DisasterType type) {
        try {
            String sql = buildQuery(type).append(";")
                                         .toString();

            iterateResultSet(DBService.dbExecuteQuery(sql), type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disasters;
    }

    public static ObservableList<Disaster> getDisasterForCountry(final DisasterType type, String countryName) {
        try {
            String sql = buildQuery(type).append("\n")
                                         .append("AND c.name LIKE ?;")
                                         .toString();

            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setString(1, "%" + countryName + "%");

            iterateResultSet(pstm.executeQuery(), type);

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disasters;
    }

    private static void iterateResultSet(final ResultSet rs, final DisasterType type) throws SQLException {
        disasters.clear();
        try {
            while (rs.next()) {
                disasters.add(new Disaster(rs.getInt("id"),
                              new Country(rs.getInt("country_id"), rs.getString("name")),
                              new Year(rs.getInt("year_id"), rs.getInt("year")),
                              type, rs.getInt("affected"), rs.getInt("deaths")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            rs.close();
        }
    }

    private static StringBuilder buildQuery(final DisasterType type) {
        return new StringBuilder()
                .append("SELECT d.id, d.country_id, d.year_id, d.affected, d.deaths, y.year, c.name\n")
                .append("FROM ")
                .append(type.getTableName())
                .append(" as d, countries as c, years as y\n")
                .append("WHERE d.country_id = c.id AND d.year_id = y.id");
    }

    public static void main(String[] args) {
        ObservableList<Disaster> disasters = getDisasterForCountry(DisasterType.EPIDEMIC, "af");
        disasters.forEach(dis -> {
            System.out.println(dis);
        });
    }
}