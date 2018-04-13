import java.sql.*;
import java.util.*;
//import org.h2.*;


public class Natflax {
    public static void main(String[] args){
        //Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        //Statement statement = conn.createStatement();
        //statement.executeQuery("SELECT * FROM Employees");

        /*Connection conn = connectToDatabase(
                "jdbc:h2:~/test",
                "sa",
                "");

        ResultSet result = queryDB(conn, "SELECT * FROM Employees");
        System.out.println(result);



        conn.close();*/
        startDBQuery();
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
                "\t3-New");
        Scanner in = new Scanner(System.in);
        int login = in.nextInt();
        System.out.println("Please log in.");
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
                break;
        }
    }

    private static void customerLogin(){
        System.out.println("Enter username:");
        Scanner in = new Scanner(System.in);
        String user = in.next();
        System.out.println("Enter Password:");
        String pw = in.next();
        //actually log in somehow or whatever
        //if(logged in)
        Customer();
    }

    private static void Customer() {
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
                //SQL search (search) FROM BOOKS, MOVIES
                System.out.println("I searched for:" + search + "\n");
                Customer();
                break;
            case (2):
                updateCustInfo();
                Customer();
                break;
            case (3):
                String[] rentals = {};
                //SQL SELECT customer's rentals, populate array with CURSOR
                if (rentals.length > 0) {
                    System.out.println("You currently have these titles out:");
                    for (String item : rentals)
                        System.out.println(item);
                } else
                    System.out.println("You don't have any titles out right now.");
                Customer();
                break;
            case (4):
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Command not recognized");
                Customer();
                break;
        }
        }catch (InputMismatchException e){
            System.out.println("Input not in right format, try again.");
            Customer();
        }
    }

    private static void updateCustInfo(){
        System.out.println("What info do you want to update?\n" +
                "\t1-Address\n" +
                "\t2-Phone Number\n" +
                "\t3-Name\n" +
                "\t4-Password\n" +
                "\t5-Payment Method\n" +
                "\t6-Back");
        Scanner in = new Scanner(System.in);
        int action = in.nextInt();
        switch(action){
            case(1):
                System.out.println("Enter your Address");
                String addr = in.next();
                //SQL UPDATE
                System.out.println("Updated Address to: "+ addr);
                updateCustInfo();
                break;
            case(2):
                System.out.println("Enter your phone number (xxx)xxx-xxxx :");
                String phone = in.next();
                //SQL UPDATE
                System.out.println("Updated Phone to: "+phone);
                updateCustInfo();
                break;
            case(3):
                System.out.println("Enter First name:");
                String fname = in.next();
                //SQL UPDATE name fname etc
                System.out.println("Enter Last name:");
                String lname = in.next();
                //SQL UPDATE name lname etc
                System.out.println("Name updated to: "+ fname+" "+ lname);
                updateCustInfo();
                break;
            case(4):
                boolean changed = false;
                while(!changed) {
                    System.out.println("Enter a new password:");
                    String pw1 = in.next();
                    System.out.println("Confirm new password:");
                    String pw2 = in.next();
                    if (pw1.equals(pw2)) {
                        //UPDATE PW
                        System.out.println("Password updated");
                        changed = true;
                    }
                    else
                        System.out.println("Passwords do not match, try again.");
                    updateCustInfo();
                }
                break;
            case(5):
                paymentMethod();
                break;
            case(6):
                Customer();
                break;
            default:
                System.out.println("Command not recognized");
                updateCustInfo();
                break;
        }
    }

    private static void paymentMethod(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter credit card numebr");
        String ccNum = in.next();
        //SQL UPDATE ccNumber
        System.out.println("Enter expiration date (mm/yyyy)");
        String ccExp = in.next();
        //SQL UPDATE ccExp
        System.out.println("Enter security pin on back of card");
        String ccSec = in.next();
        //SQL UPDATE ccSec
        updateCustInfo();
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