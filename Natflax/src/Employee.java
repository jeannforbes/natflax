import java.util.*;
import java.sql.ResultSet;
import java.util.Scanner;

public class Employee {
    private String ID, name[], phone, hours, wage, store;

    Employee(String ID, String fname, String lname, String phone, String hours, String wage, String SID){
        this.ID = ID;
        this.name = new String[]{fname,lname};
        this.phone = phone;
        this.hours = hours;
        this.wage = wage;
        this.store = SID;
    }
    
    Employee(String[] info, String SID)
    {
        this.ID = info[0];
        this.name = new String[]{info[1],info[2]};
        this.phone = info[3];
        this.hours = info[4];
        this.wage = info[5];
        this.store = SID;
    }

    Employee(){
        this.ID = ID();
        this.name = name();
        this.phone = phone();
        this.hours = hours();
        this.wage = wage();
        this.store = storeID();
    }

    public void updateEmpInfo(){
        System.out.println("What info do you want to update?\n" +
                "\t1-Name\n" +
                "\t2-Phone Number\n" +
                "\t3-Back");
        Scanner in = new Scanner(System.in);
        int action = in.nextInt();
        switch(action){
            case(1):
                System.out.println("Current name is: "+ this.name[0]+" "+ this.name[1]+
                        "\n -1 to cancel or any key to coninue");
                if(in.next().equals("-1")) updateEmpInfo();
                String[] name = name();
                this.name = name;
                System.out.println("Name updated to: "+ name[0]+" "+name[1]);
                //SQL UPDATE 0 = fname, 1 = lname
                updateEmpInfo();
                break;
            case(2):
                System.out.println("Current phone is:" + this.phone+
                        "\n -1 to cancel or any key to continue");
                if(in.next().equals("-1")) updateEmpInfo();
                String phone = phone();
                this.phone = phone;
                System.out.println("Phone updated: "+phone);
                //SQL UPDATE phone
                updateEmpInfo();
                break;
            case(3):
                break;
            default:
                System.out.println("Command not recognized");
                updateEmpInfo();
                break;
        }
    }

    public void returnItem(String type)
        throws Exception{
        Scanner in = new Scanner(System.in);
        String item_key, rent_table, table, stock_table;
        if(type.equalsIgnoreCase("movie"))
        {
            item_key = "ISAN";
            table = "Movie";
            rent_table = "Rented_movies";
            stock_table = "Movies_in_stock";
        }
        else
        {
            item_key = "ISBN";
            table = "Book";
            rent_table = "Rented_books";
            stock_table = "Books_in_stock";
        }
        System.out.println("Current outstanding rentals from your store:");
        ResultSet item_query = Database.queryDB("SELECT C.CID, C.firstname, C.lastname, I." + item_key + ", I.title, rented_date  " +
                            "FROM (" + rent_table + " natural join " + table +" as I) natural join Customer as C " +
                            "WHERE SID = '" + this.store +"';");
        
        Database.printResultSet(item_query);
        System.out.println("\nWhat " + type + " was returned? (Enter " + item_key +", -1 to cancel):");
        String item_id;
        do
        {
            item_id= in.next();
            if(item_id.equals("-1"))
            {
                return;
            }

            item_query = Database.queryDB("SELECT C.CID, C.firstname, C.lastname, I." + item_key + ", I.title, rented_date " +
                            "FROM (" + rent_table + " natural join " + table +" as I) natural join Customer as C " +
                            "WHERE SID = '" + this.store +"' and I." + item_key + " = '" + item_id + "';");
            if(item_query.isBeforeFirst() == false)
            {
                System.out.println("The " + type + " with " + item_key + ": " + item_id + 
                                    " has no outstanding rentals");
            }
        }while(item_query.isBeforeFirst() == false);
        
        String cid;
        System.out.println("All customers who have rented " + type + ": " + item_id);
        Database.printResultSet(item_query);
        
        System.out.println("Who has returned this " + type + "?");
        do
        {
            cid= in.next();
            item_query = Database.queryDB("SELECT C.CID, C.firstname, C.lastname, I." + item_key + ", I.title " +
                            "FROM (" + rent_table + " natural join " + table +" as I) natural join Customer as C " +
                            "WHERE SID = '" + this.store +"' and I." + item_key + " = '" + item_id + 
                            "' and C.CID = '" + cid + "';");
            if(item_query.isBeforeFirst() == false)
            {
                System.out.println("The customer " + cid + " has no outstanding rentals of that " + type + ".");
            }
        }while(item_query.isBeforeFirst() == false);
        
        // Get the date which it was rented
        item_query = Database.queryDB("SELECT Rented_date FROM " + rent_table + 
                                        " where CID = '" + cid + "' and " + item_key + 
                                        " = '" + item_id + "' and SID = '" + this.store + "';");
        item_query.first();
        Date rented_date = item_query.getDate(1);
        Date today = new Date();
        int days = (int) Math.ceil((today.getTime() - rented_date.getTime()) / (1000*60*60*24));
        item_query = Database.queryDB("SELECT RENTFEE FROM " + table + " where " + item_key + " = '" + item_id + "';");
        item_query.first();
        float value = item_query.getFloat(1);
        
        System.out.print("Charging Customer " + cid + " ");
        System.out.format("$%5.2f. for renting for %d days at $%5.2f per day\n", (days * value), days, value);
        
        
        // Remove rent tuple
        Database.updateDB("DELETE FROM " + rent_table + " where " + item_key + 
                            " = '" + item_id + "' and CID = '" + cid + "' and SID = '" + this.store + "';");
        // Add to stock associated with store
        Database.updateDB("UPDATE " +  stock_table + " SET stock = stock - 1 WHERE " + 
                            item_key + " = '" + item_id + "' and SID = '" + this.store + "'");
    }

    public void addBook(){
        Book b = new Book();
    }

    private String ID(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your ID: ");
        return in.next();
    }

    private String[] name(){
        Scanner in = new Scanner(System.in);
        String[] name = new String[2];
        System.out.println("Enter First name:");
        name[0] = in.next();
        System.out.println("Enter Last name:");
        name[1] = in.next();
        return name;
    }

    private String phone(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your phone number (xxx)xxx-xxxx :");
        return in.next();
    }

    private String hours(){
        System.out.println("Enter your weekly hours:");
        String SID ="1";
        //SQL get store id
        return SID;
    }
    
    private String wage(){
        System.out.println("Enter your hourly rate:");
        String SID ="1";
        //SQL get store id
        return SID;
    }
    
    private String storeID(){
        String SID ="1";
        //SQL get store id
        return SID;
    }

}
