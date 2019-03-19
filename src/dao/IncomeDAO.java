package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Income;
import models.Year;
import utils.DBService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeDAO {

    private static ObservableList<Income> incomeList = FXCollections.observableArrayList();

    public static ObservableList<Income> getIncomeForYear(int incomeYear) {
        try {
            StringBuilder stringBuilder = buildQuery();
            stringBuilder.append("AND y.year = ?;");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setInt(1, incomeYear);

            iterateResultSet(pstm.executeQuery());
            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return incomeList;
    }

    public static ObservableList<Income> getIncomeForYearRange(int from, int to) {
        try {
            StringBuilder stringBuilder = buildQuery();
            stringBuilder.append("AND y.year BETWEEN ? AND ?;");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setInt(1, from);
            pstm.setInt(2, to);

            iterateResultSet(pstm.executeQuery());
            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return incomeList;
    }

    public static ObservableList<Income> getIncomeForCountries(String name) {
        try {
            StringBuilder stringBuilder = buildQuery();
            stringBuilder.append("AND c.name LIKE ?;");
            String sql = stringBuilder.toString();

            DBService.dbConnect();
            PreparedStatement pstm = DBService.getConnection().prepareStatement(sql);
            pstm.setString(1, "%" + name + "%");

            iterateResultSet(pstm.executeQuery());
            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return incomeList;
    }

    private static void iterateResultSet(ResultSet rs) throws SQLException {
        incomeList.clear();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                int countryID = rs.getInt("country_id");
                int yearID = rs.getInt("year_id");
                int income = rs.getInt("income");
                int year = rs.getInt("year");
                String country = rs.getString("name");

                incomeList.add(new Income(id,
                        new Country(countryID, country),
                        new Year(yearID, year),
                        income));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            rs.close();
        }
    }

    private static StringBuilder buildQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT i.id, i.country_id, i.year_id, i.income, y.year, c.name\n");
        stringBuilder.append("FROM incomes as i, years as y, countries as c\n");
        stringBuilder.append("WHERE y.id = i.year_id\n");
        stringBuilder.append("AND i.country_id = c.id\n");

        return stringBuilder;
    }

    public static void main(String[] args) {
        ObservableList<Income> population = getIncomeForYearRange(1990, 1991);
        population.forEach(pop -> {
            System.out.println(pop.getCountry().getName());
            System.out.println(pop);
        });
    }
}
