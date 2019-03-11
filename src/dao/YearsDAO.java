package dao;

import utils.DBService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YearsDAO {

    public static void getAllYears(boolean orderDesc) {
        try {
            String sql = "SELECT * \n"
                    + "FROM years \n";

            sql += orderDesc ? "ORDER BY year DESC" : "";

            ResultSet rs = DBService.dbExecuteQuery(sql);

            while (rs.next()) {
                int year = rs.getInt("year");

                System.out.println("Year: " + year);
            }

            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
