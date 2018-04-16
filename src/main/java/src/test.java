import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.h2.*;


public class test {
    static public SimpleDateFormat formatter;

    public static void main(String[] a)
            throws Exception {

        formatter = new SimpleDateFormat("YYYY-MM-dd");

        Connection conn = connectToDatabase(
                "jdbc:h2:~/natflax",
                "admin",
                "");
        startDBQuery(conn);

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
        ResultSet rs = null;
        try {
            Statement statement = conn.createStatement();
            rs = statement.executeQuery(query);
        } catch(Exception e){
            System.out.println("Could not complete query: "+e);
            conn.close();
        }
        return rs;
    }

    public static boolean executeDB(Connection conn, String query)
            throws Exception{
        boolean result = false;
        try {
            Statement statement = conn.createStatement();
            result = statement.execute(query);
        } catch(Exception e){
            System.out.println("Could not complete query: "+e);
            conn.close();
        }
        return result;
    }

    public static void startDBQuery(Connection conn) throws Exception {
        System.out.println("Welcome to NATFLAX!\n" +
                "\nAre you:\n"+
                "\t1-Customer\n" +
                "\t2-Employee\n" +
                "\t3-New\n");
        Scanner in = new Scanner(System.in);
        int login = in.nextInt();
        System.out.println("Please log in.");
        switch(login){
            case(1):
                //Customer login
                System.out.print("username: ");
                String user = in.next();
                System.out.print("password: ");
                String pass = in.next();
                ResultSet rs = queryDB(conn, "SELECT * FROM Customer WHERE username='"+user+
                        "' AND password='"+pass+"'");
                if(rs.next()) Customer(conn, rs.getString("CID"));
                else System.out.println("Incorrect username and password.");
                break;
            case(2):
                //Employee login
                Employee(conn);
                break;
            case(3):
                //Create new login
                break;
        }
    }

    public static void Customer(Connection conn, String cid) throws Exception {
        String CID = cid;
        String search = null;
        ResultSet rs = null;
        int action = 0;

        System.out.println("Welcome, customer!\n  What can we help you with?:\n" +
                "\t1-Search for a book\n" +
                "\t2-Search for a movie\n" +
                "\t3-View rentals\n" +
                "\t4-Edit personal information\n");
        Scanner in = new Scanner(System.in);
        action = in.nextInt();
        switch (action){
            case(1): // Search for a book
                System.out.println("Are you...\n" +
                        "\t1 - searching by title?\n" +
                        "\t2 - searching by author?\n");
                action = in.nextInt();
                switch(action){
                    case(1): // Search by title
                        System.out.print("Enter the book title:");
                        search = in.nextLine();
                        rs = queryDB(conn,"SELECT * FROM Book WHERE title='"+search+"'");
                        if(rs.next()){
                            String author = rs.getString("AUTHOR");
                            String title = rs.getString("TITLE");
                            System.out.println("Do you want to rent a copy of "+title+" by "+author+"?\n"+
                                    "\t1 - yes\n"+
                                    "\t2 - no\n");
                            action = in.nextInt();
                            switch(action){
                                case 1:
                                    System.out.println("You rented "+title+" by "+author+".");
                                    // Rent it!
                                    break;
                                case 2:
                                    System.out.println("Have a nice day!");
                                    // Go back to the start
                                    break;

                            }
                        }
                        else System.out.println("We don't have a book by that title.");
                        break;
                    case(2): // Search by author
                        System.out.print("Enter the author:");
                        in.nextLine();
                        search = in.nextLine();
                        rs = queryDB(conn,"SELECT * FROM Book WHERE author='"+search+"'");
                        if(rs.next()){
                            String author = rs.getString("AUTHOR");
                            String title = rs.getString("TITLE");
                            System.out.println("Do you want to rent a copy of "+title+" by "+author+"?\n"+
                                    "\t1 - yes\n"+
                                    "\t2 - no\n");
                            action = in.nextInt();
                            switch(action){
                                case 1:
                                    System.out.println("You rented "+title+" by "+author+".");
                                    String ISBN = rs.getString("ISBN");
                                    int SID = 1;
                                    String rented_date = formatter.format(new Date());
                                    // Rent it!
                                    boolean result = executeDB(conn, "INSERT INTO rented_books (CID, ISBN, SID, rented_date) "+
                                        "VALUES ('"+CID+"','"+ISBN+"','"+SID+"','"+rented_date+"')");
                                    if(result) System.out.println("There was an issue renting this book.  Try again later!");
                                    break;
                                case 2:
                                    System.out.println("Have a nice day!");
                                    // Go back to the start
                                    break;

                            }
                        } else System.out.println("We don't have a book by that author.");
                        break;
                    default:
                        break;
                }
                break;
            case(2): // Search for a movie
                System.out.println("Are you...\n" +
                        "\t1 - searching by title?\n" +
                        "\t2 - searching by director?\n");
                action = in.nextInt();
                switch(action){
                    case(1): // Search by title
                        System.out.println("Enter the movie title:");
                        search = in.nextLine();
                        rs = queryDB(conn,"SELECT * FROM Movie WHERE title='"+search+"'");
                        if(rs.next()){
                            String director = rs.getString("DIRECTOR");
                            String title = rs.getString("TITLE");
                            System.out.println("Do you want to rent a copy of "+title+" by "+director+"?\n"+
                                    "\t1 - yes\n"+
                                    "\t2 - no\n");
                            action = in.nextInt();
                            switch(action){
                                case 1:
                                    System.out.println("You rented "+title+" by "+director+".");
                                    // Rent it!
                                    break;
                                case 2:
                                    System.out.println("Have a nice day!");
                                    // Go back to the start
                                    break;

                            }
                        }
                        else System.out.println("We don't have a movie by that title.");
                        break;
                    case(2): // Search by author
                        System.out.println("Enter the director:");
                        search = in.nextLine();
                        rs = queryDB(conn,"SELECT * FROM Movie WHERE director='"+search+"'");
                        if(rs.next()){
                            String director = rs.getString("DIRECTOR");
                            String title = rs.getString("TITLE");
                            System.out.println("Do you want to rent a copy of "+title+" by "+director+"?\n"+
                                    "\t1 - yes\n"+
                                    "\t2 - no\n");
                            action = in.nextInt();
                            switch(action){
                                case 1:
                                    System.out.println("You rented "+title+" by "+director+".");
                                    // Rent it!
                                    break;
                                case 2:
                                    System.out.println("Have a nice day!");
                                    // Go back to the start
                                    break;

                            }
                        } else System.out.println("We don't have a movie by that director.");
                        break;
                    default:
                        break;
                }
                break;
            case(3):

                break;
            case(4):
                break;
            default:
                break;
        }
    }

    public static void Employee(Connection conn){
        System.out.println("Hello, employee!  What needs doing?\n" +
                "\t1-Return book\n" +
                "\t2-Return movie\n" +
                "\t3-Add book"+
                "\t4-Add movie\n");
        Scanner in = new Scanner(System.in);
        int action = in.nextInt();
        switch(action){
            case(1):
                break;
            case(2):
                break;
            case(3):
                break;
            case(4):
                break;
            default:
                break;
        }
    }
}