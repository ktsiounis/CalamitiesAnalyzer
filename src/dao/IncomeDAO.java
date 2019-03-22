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

    public static ObservableList<Income> getIncomeForYear(final int incomeYear) {
        try {
            String sql = buildQuery().append("AND y.year = ?;")
                                     .toString();

            PreparedStatement pstm = DBService.getConnection()
                                              .prepareStatement(sql);

            pstm.setInt(1, incomeYear);

            iterateResultSet(pstm.executeQuery());

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return incomeList;
    }

    public static ObservableList<Income> getIncomeForYearRange(final int from, final int to) {
        try {
            String sql = buildQuery().append("AND y.year BETWEEN ? AND ?;")
                                     .toString();

            PreparedStatement pstm = DBService.getConnection()
                                              .prepareStatement(sql);

            pstm.setInt(1, from);
            pstm.setInt(2, to);

            iterateResultSet(pstm.executeQuery());

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return incomeList;
    }

    public static ObservableList<Income> getIncomeForCountries(final String name) {
        try {
            String sql = buildQuery().append("AND c.name LIKE ?;")
                                     .toString();

            PreparedStatement pstm = DBService.getConnection()
                                              .prepareStatement(sql);

            pstm.setString(1, "%" + name + "%");

            iterateResultSet(pstm.executeQuery());

            DBService.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return incomeList;
    }

    private static void iterateResultSet(final ResultSet rs) throws SQLException {
        incomeList.clear();
        try {
            while (rs.next()) {
                incomeList.add(new Income(rs.getInt("id"),
                               new Country(rs.getInt("country_id"), rs.getString("name")),
                               new Year(rs.getInt("year_id"), rs.getInt("year")),
                               rs.getInt("income")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            rs.close();
        }
    }

    private static StringBuilder buildQuery() {
        return new StringBuilder()
                .append("SELECT i.id, i.country_id, i.year_id, i.income, y.year, c.name\n")
                .append("FROM incomes as i, years as y, countries as c\n")
                .append("WHERE y.id = i.year_id\n")
                .append("AND i.country_id = c.id\n");
    }

    public static void main(String[] args) {
        ObservableList<Income> population = getIncomeForYearRange(1990, 1991);
        population.forEach(pop -> {
            System.out.println(pop.getCountry().getName());
            System.out.println(pop);
        });
    }
}
