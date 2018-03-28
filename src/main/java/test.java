import java.sql.*;
import org.h2.*;
public class test {
    public static void main(String[] a)
            throws Exception {
        //Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        //Statement statement = conn.createStatement();
        //statement.executeQuery("SELECT * FROM Employees");

        Connection conn = connectToDatabase(
                "jdbc:h2:~/test",
                "sa",
                "");

        ResultSet result = queryDB(conn, "SELECT * FROM Employees");
        System.out.println(result);

        conn.close();
    }

    // Handles connecting to the database
    public static Connection connectToDatabase(String url, String user, String pass)
            throws Exception{
        return DriverManager.getConnection(url, user, pass);
    }

    // Returns a result set for the given SQL query
    public static ResultSet queryDB(Connection conn, String query)
            throws Exception{
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }
}