package prime.punkdomus.primebank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class responsible for providing a connection to the database.
 */
public class ConnectionDB {

    private static final String url = "jdbc:mysql://172.18.0.2:3306/crud_student_javafx";
    private static final String user = "root";
    private static final String password = "rootpassword";

    /**
     * Provides a connection to the database.
     *
     * @return a connection to the database.
     */
    public static Connection getInstance()  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Close a connection to the database.
     *
     * @param connection the connection to close.
     */
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}