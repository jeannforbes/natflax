import java.sql.*;
import java.util.*;
import org.h2.*;

public class Database {
    static Connection conn;
    // Handles connecting to the database
    public static void connectToDatabase(String url, String user, String pass)
            throws Exception{
        conn = DriverManager.getConnection(url, user, pass);
    }
    public static void close()
            throws Exception{
        conn.close();
    }
    // Returns a result set for the given SQL query
    public static ResultSet queryDB(String query)
            throws Exception{
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }
    
    public static void printResultSet(ResultSet result)
            throws Exception
    {
        if(result.first() == false)
        {
            System.out.println("No tuples found");
        }
        else
        {
            // Gather column information of the results:
            ResultSetMetaData meta_data = result.getMetaData();
            int columns = meta_data.getColumnCount();

            // Create a format string based on the column width of each result column
            String[] format = new String[columns];
            for(int i = 0; i < columns; i++)
            {
                format[i] = "%-" + (meta_data.getColumnDisplaySize(i+1) + 1) + "s";
                System.out.format(format[i], meta_data.getColumnName(i+1));
            }
            System.out.print("\n");
            
            // Print out every single tuple of the query
            do
            {
                for(int i = 0; i < columns; i++)
                {
                    System.out.format(format[i], result.getString(i+1));
                }
                System.out.print("\n");
            } while(result.next() != false);
        }
    }
}
