package ra.phone_store_manager.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/quanlydienthoai?currentSchema=data";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static Connection getConnection(){
        //Khai báo Driver;
        try {
            Class.forName(DRIVER);
            // Mở kết nối
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public static void closeConnection(Connection conn){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
}
