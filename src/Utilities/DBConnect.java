package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ07LSq";

    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver Interface Reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    public static Connection conn;

    private static final String userName = "U07LSq";
    private static final String password = "53689060267";

    public Connection getConnection(){
        return conn;
    }



    public static Connection startConnection() {

        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection successful");

        }  catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection Closed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

