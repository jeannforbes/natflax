import java.sql.*;
import java.util.*;
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

        startDBQuery();
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

    public static void startDBQuery(){
        System.out.println("Welcome to NATFLAX!\n" +
                "Are you: 1-Customer\n" +
                "\t2-Employee\n" +
                "\t3-New\n");
        Scanner in = new Scanner(System.in);
        int login = in.nextInt();
        System.out.println("Please log in.");
        switch(login){
            case(1):
                //Customer login
                Customer();
                break;
            case(2):
                //Employee login
                Employee();
                break;
            case(3):
                //Create new login
                break;
        }
    }

    public static void Customer() {
        System.out.println("What can we help you with?:\n" +
                "\t1-Search for a book\n" +
                "\t2-Search for a movie\n" +
                "\t3-Search for either\n" +
                "\t4-Edit Information\n" +
                "\t5-Check Rentals\n");
        Scanner in = new Scanner(System.in);
        int action = in.nextInt();
        switch (action){
            case(1):
                System.out.println("Input search keyword:");
                String search = in.next();
                //SQL search (search) FROM BOOKS
//                System.out.println(result list );
                break;
            case(2):
                break;
            case(3):
                break;
            case(4):
                break;
            case(5):
                break;
            default:
                break;
        }
    }

    public static void Employee(){
        System.out.println("Whatchu wan do?\n" +
                "\t1-Return book\n" +
                "\t2-idk whatever else");
        Scanner in = new Scanner(System.in);
        int action = in.nextInt();
        switch(action){
            case(1):
                break;
            case(2):
                break;
            default:
                break;
        }
    }
}