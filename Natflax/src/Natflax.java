import java.sql.*;
import java.util.*;
//import org.h2.*;


public class Natflax {

    public static void main(String[] args){
        //Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        //Statement statement = conn.createStatement();
        //statement.executeQuery("SELECT * FROM Employees");
/*        Connection conn = connectToDatabase(
                "jdbc:h2:~/test",
                "sa",
                "");

        ResultSet result = queryDB(conn, "SELECT * FROM Employees");
        System.out.println(result);
*/

        startDBQuery();
//        conn.close();
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

    private static void startDBQuery(){
        System.out.println("Welcome to NATFLAX!\n" +
                "Are you a(n):\n" +
                "\t1-Customer\n" +
                "\t2-Employee\n" +
                "\t3-New\n" +
                "\t4-Quit");
        Scanner in = new Scanner(System.in);
        try {
            int login = in.nextInt();
            switch(login){
                case(1):
                    //Customer login
                    customerLogin();
                    break;
                case(2):
                    //Employee login
                    Employee();
                    break;
                case(3):
                    //Create new login
                    CreateNewCustomer();
                    break;
                case(4):
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Command not recognized");
                    startDBQuery();

            }
        }catch (InputMismatchException e){
            System.out.println("Input not in correct format, try again.");
            startDBQuery();
        }
    }

    private static void CreateNewCustomer() {
        System.out.println("Welcome! Please fill out some basic information.");
        Customer c = new Customer();
        customerActions(c);//for now
        //customerLogin() //when it's implemented
    }

    private static void customerLogin(){
        System.out.println("Please log in.");
        System.out.println("Enter username:3");
        Scanner in = new Scanner(System.in);
        try {
            String user = in.next();
            System.out.println("Enter Password:");
            String pw = in.next();
            Customer sample = new Customer("1","user","password","Fname","Lname",
                    "123 Sample St, Place JE, 12345","01/01/1900","(123)456-7890",
                    "1234123412341234","01/20","123", new ArrayList<String>());
            //actually log in somehow or whatever
            //if(logged in) c = customer that exists somehow
            customerActions(sample);
        }catch (InputMismatchException e){
            System.out.println("Input not in correct format, try again.");
            customerLogin();
        }
    }

    private static void customerActions(Customer c) {
        System.out.println("What can we help you with?:\n" +
                "\t1-Search\n" +
                "\t2-Edit Information\n" +
                "\t3-Check Rentals\n" +
                "\t4-Quit");
        Scanner in = new Scanner(System.in);
        try{
            int action = in.nextInt();
            switch (action) {
                case (1):
                    System.out.println("Input search keyword:");
                    String search = in.next();
                    //SQL search (search) FROM BOOKS, MOVIES = results, limit to 10 results bc its easier, or not just
                    //change that later
                    String[] results = new String[]{"book1","book2","book3"};
                    System.out.println("I searched for:" + search + "\n");//
                    System.out.println("Would you like to check out a title? (yes/no)");
                    String response = in.next();
                    if(response.equals("yes"))
                        c.checkOut(results);
                    else if(response.equals("no"))
                        customerActions(c);
                    else System.out.println("not recognized, assuming no");
                        customerActions(c);
                    break;
                case (2):
                    c.updateCustInfo();
                    customerActions(c);
                    break;
                case (3):
                    c.checkRentals();
                    customerActions(c);
                    break;
                case (4):
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Command not recognized");
                    customerActions(c);
                    break;
            }
        }catch (InputMismatchException e){
            System.out.println("Input not in right format, try again.");
            customerActions(c);
        }
    }


    private static void Employee(){
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