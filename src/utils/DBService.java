package utils;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class DBService {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://35.187.106.41/advanced_databases";

    private static final String USER = "devaccount";
    private static final String PASS = "devH3r0@2019";

    private static Properties connectionProps = new Properties();
    private static Connection connection = null;

    //Connect to Cloud SQL DB
    public static void dbConnect() {
        connectionProps.setProperty("user", USER);
        connectionProps.setProperty("password", PASS);

        try {
            Class.forName(JDBC_DRIVER).newInstance();
        } catch (Exception e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }

        System.out.println("MySQL JDBC Driver Registered!");

        //Establish the MySQL Connection using Connection String
        try {
            connection = DriverManager.getConnection(DB_URL, connectionProps);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
        }

    }

    public static void dbDisconnect() throws Exception {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws Exception {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            //Connect to DB (Establish MySQL Connection)
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");

            //Create statement
            stmt = connection.createStatement();

            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);

            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return crs;
    }

}
