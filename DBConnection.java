import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;

        try {

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ecommerce_tracking",
                    "root",
                    "ssk23.2007"
            );

            System.out.println("Database Connected");

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return con;
    }
}
