package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Population;
import models.Year;
import utils.DBService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopulationDAO {

    private static ObservableList<Population> populationList = FXCollections.observableArrayList();;

    public static ObservableList<Population> getPopulationForYear(int populationYear) {

//        ObservableList<Population> populationList = FXCollections.observableArrayList();

        try {
            StringBuilder stringBuilder = buildQuery();
            stringBuilder.append("AND y.year = ?;");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setInt(1, populationYear);
            ResultSet rs = pstm.executeQuery();

            iterateResultSet(rs);
            DBService.dbDisconnect();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return populationList;
    }

    public static ObservableList<Population> getPopulationForYearRange(int from, int to) {

//        ObservableList<Population> populationList = FXCollections.observableArrayList();

        try {
            StringBuilder stringBuilder = buildQuery();
            stringBuilder.append("AND y.year BETWEEN ? AND ?;");
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

        return populationList;
    }

    public static ObservableList<Population> getPopulationForCountry(String name) {

//        ObservableList<Population> populationList = FXCollections.observableArrayList();

        try {
            StringBuilder stringBuilder = buildQuery();
            stringBuilder.append("AND c.name LIKE ?;");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setString(1, "%" + name + "%");
            ResultSet rs = pstm.executeQuery();

            iterateResultSet(rs);
            DBService.dbDisconnect();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return populationList;
    }

    private static void iterateResultSet(ResultSet rs) throws SQLException {
        populationList.clear();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                int countryID = rs.getInt("country_id");
                int yearID = rs.getInt("year_id");
                int population = rs.getInt("population");
                int year = rs.getInt("year");
                String country = rs.getString("name");

                populationList.add(new Population(id,
                        new Country(countryID, country),
                        new Year(yearID, year),
                        population));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static StringBuilder buildQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT p.id, p.country_id, p.year_id, p.population, y.year, c.name\n");
        stringBuilder.append("FROM population as p, years as y, countries as c\n");
        stringBuilder.append("WHERE y.id = p.year_id\n");
        stringBuilder.append("AND p.country_id = c.id\n");

        return stringBuilder;
    }

    public static void main(String[] args) {
        ObservableList<Population> population = getPopulationForCountry("AF");
        population.forEach(pop -> {
            System.out.println(pop.getCountry().getName());
            System.out.println(pop);
        });
    }
}

