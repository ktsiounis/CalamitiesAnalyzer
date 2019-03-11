package dao;

import utils.DBService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDAO {

    public static void getAllCountries(boolean orderDesc) {

        try {
            String sql = "SELECT * \n"
                    + "FROM countries \n";

            sql += orderDesc ? "ORDER BY name DESC" : "";

            ResultSet rs = DBService.dbExecuteQuery(sql);


            while (rs.next()) {
                String country = rs.getString("name");

                System.out.println("Country: " + country);
            }

            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
