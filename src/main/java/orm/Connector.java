package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static Connection connection;
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/";
    public static void createConnection(String username, String password, String dbName) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "1234");
        connection = DriverManager.getConnection(connectionUrl + dbName, properties);

    }

    public static Connection getConnection() {
        return connection;
    }
}
