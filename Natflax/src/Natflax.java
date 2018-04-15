import java.sql.*;
import java.util.*;
import org.h2.*;


public class Natflax {

    static Store store;
    public static void main(String[] args)
        throws Exception{
        //Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        //Statement statement = conn.createStatement();
        //statement.executeQuery("SELECT * FROM Employees");
        Database.connectToDatabase(
                "jdbc:h2:~/test",
                "natflax",
                "admin");

        store = new Store("1","123 place","(132)123-1593","123-45-6789",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
        startDBQuery();
        Database.close();
    }
    
    private static void startDBQuery()
        throws Exception{
        System.out.println("Welcome to NATFLAX!\n" +
                "Are you a(n):\n" +
                "\t1-Customer\n" +
                "\t2-Employee\n" +
                "\t3-Manager\n" +
                "\t4-New\n" +
                "\t5-Quit");
        Scanner in = new Scanner(System.in);
        try {
            int login = in.nextInt();
            if(login == 1 | login == 2 | login == 3)
                login(login);
            switch(login){
                case(4):
                    //Create new login
                    CreateNewCustomer();
                    break;
                case(5):
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

    private static void CreateNewCustomer()
        throws Exception {
        System.out.println("Welcome! Please fill out some basic information.");
        Customer c = new Customer();
        customerActions(c);//for now
        //customerLogin() //when it's implemented
    }


    private static void login(int action)
        throws Exception{
        System.out.println("Please log in.");
        System.out.println("Enter username:");
        Scanner in = new Scanner(System.in);
        try {
            String user = in.next();
            //actually log in somehow or whatever
            //if(logged in) c = customer that exists somehow
            switch(action){
                case(1):
                    ResultSet user_query = Database.queryDB("SELECT * FROM Customer WHERE username = '" + user + "'");
                    if(user_query.first() == false)
                    {
                        System.out.println("Login failed - user " + user + " not found");
                        login(action);
                    }
                    else
                    {
                        Customer sampleC;
                        int columns = user_query.getMetaData().getColumnCount();
                        String[] info = new String[columns];
                        for(int i = 0; i < columns; i++)
                        {
                            info[i] = user_query.getString(i+1);
                        }
                        
                        ResultSet cc_query = Database.queryDB("SELECT P.ccNumber, P.ccExpiration, P.ccPIN, P.ccSecurity FROM Customer natural join Payment as P WHERE Customer.username = '" + user + "'");
                        if(cc_query.first() == true)
                        {
                            columns = cc_query.getMetaData().getColumnCount();
                            String[] cc_info = new String[columns];
                            for(int i = 0; i < columns; i++)
                            {
                                cc_info[i] = cc_query.getString(i+1);
                            }
                            sampleC = new Customer(info, cc_info);
                        }
                        else
                        {
                            sampleC = new Customer(info);
                        }
                        customerActions(sampleC);
                    }
                    break;
                case(2):
                    Employee sampleE = new Employee("123-45-6789","user","password","Fname","Lname",
                            "123 Sample St, Place JE, 12345","(123)456-7890","1");
                    employeeActions(sampleE);
                    break;
                case(3):
                    Employee sampleM = new Employee("123-45-6789","user","password","Fname","Lname",
                        "123 Sample St, Place JE, 12345","(123)456-7890","1");
                    managerActions(sampleM);
                    break;
            }
        }catch (InputMismatchException e){
            System.out.println("Input not in correct format, try again.");
            login(action);
        }
    }

    private static void managerActions(Employee m){
        System.out.println("What would you like to do?\n" +
                "\t1-Return book\n" +
                "\t2-Add book\n" +
                "\t3-Edit Information\n" +
                "\t4-Add Employee\n" +
                "\t5-Fire Employee\n" +
                "\t6-Quit");
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
                System.out.println("Fill out your information:");
                Employee e = new Employee();
                store.employees.add(e);
                break;
            case(5):
                System.out.println("Enter ssn of Employee to fire:");
                //SQL SELECT Employee with that ssn = e
//                store.employees.remove(e);
                break;
            case(6):
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Command not recognized");
                managerActions(m);
                break;
        }
    }

    private static void customerActions(Customer c) 
        throws Exception {
        System.out.println("What can we help you with?:\n" +
                "\t1-Search Books\n" +
                "\t2-Search Movies\n" +
                "\t3-Edit Information\n" +
                "\t4-Check Rentals\n" +
                "\t5-Quit");
        Scanner in = new Scanner(System.in);
        try{
            int action = in.nextInt();
            switch (action) {
                case (1):
                    beginRental(c, "book");
                    break;
                case (2):
                    beginRental(c, "movie");
                    break;
                case (3):
                    c.updateCustInfo();
                    customerActions(c);
                    break;
                case (4):
                    c.checkRentals();
                    customerActions(c);
                    break;
                case (5):
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
    private static void beginRental(Customer c, String type)
            throws Exception
    {
        String table, stock_table;
        if(type.equalsIgnoreCase("movie"))
        {
            table = "Movie";
            stock_table = "Movies_in_stock";
        }
        else
        {
            table = "Book";
            stock_table = "Books_in_stock";
        }

        Scanner in = new Scanner(System.in);
        System.out.println("Input search keyword:");
        
        String search = in.next();
        ResultSet item_query = Database.queryDB("SELECT distinct " + table + ".* from " + table + " natural join " + stock_table + " where stock > 0 and title like '%" + search + "%'");

        if(Database.printResultSet(item_query) == false)
        {
            // Could not find any books in stock
            item_query = Database.queryDB("SELECT * from " + table + " where title like '%" + search + "%'");
            if(item_query.first() == true)
            {
                Database.printResultSet(item_query);
                System.out.println("The above " + type + "s were found, but not in stock.\n");
            }
            else
            {
                System.out.println("No " + type + "s were found with a title like that.\n");
            }
            customerActions(c);
        }
        else
        {
            System.out.println("\nWould you like to check out a title? (yes/no)");
            String response = in.next();
            if(response.equals("yes"))
            {
                c.checkOut(type);
                customerActions(c);
            }
            else if(response.equals("no"))
            {
                customerActions(c);
            }
            else 
            {
                System.out.println("not recognized, assuming no");
                customerActions(c);
            }
        }
    }


    private static void employeeActions(Employee e){
        System.out.println("What would you like to do?\n" +
                "\t1-Return book\n" +
                "\t2-Add book\n" +
                "\t3-Edit Information\n" +
                "\t4-Quit");
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
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Command not recognized");
                employeeActions(e);
                break;
        }
    }
}