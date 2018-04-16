import java.util.*;
import java.sql.ResultSet;
import java.util.Scanner;

public class Employee {
    private String ID, name[], phone, hours, wage;
    public String store;

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

    Employee(String storeID)
        throws Exception{
        this.ID = ID();
        this.name = name();
        this.phone = phone();
        this.hours = hours();
        this.wage = wage();
        this.store = storeID;
        Database.updateDB("Insert into Employee(ID,FIRSTNAME,LASTNAME,PHONE,HOURS,WAGE) "+
                            " values ('" + this.ID + "','" + this.name[0] + "','" + this.name[1] +
                            "','" + this.phone + "'," + this.hours + "," + this.wage + ");");
        Database.updateDB("Insert into Works_for(ID,SID) values ('" + this.ID +"','" + this.store + "');");
    }

    public void updateEmpInfo()
        throws Exception{
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
                Database.updateDB("UPDATE Employee SET firstname = '" + name[0] + "', lastname = '" + name[1] + "' WHERE ID = '" + this.ID + "'");
                updateEmpInfo();
                break;
            case(2):
                System.out.println("Current phone is:" + this.phone+
                        "\n -1 to cancel or any key to continue");
                if(in.next().equals("-1")) updateEmpInfo();
                String phone = phone();
                this.phone = phone;
                System.out.println("Phone updated: "+phone);
                Database.updateDB("UPDATE Employee SET Phone = '" + phone + "' WHERE ID = '" + this.ID + "'");
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

    public void addItem(String type)
        throws Exception{
        String item_key, table, stock_table, rent_table, release, author_type;
        if(type.equalsIgnoreCase("movie"))
        {
            item_key = "ISAN";
            table = "Movie";
            stock_table = "Movies_in_stock";
            rent_table = "rented_movies";
            author_type = "director";
            release = "yearrelease";
        }
        else
        {
            item_key = "ISBN";
            table = "Book";
            stock_table = "Books_in_stock";
            rent_table = "rented_books";
            author_type = "author";
            release = "yearpublish";
        }

        Scanner in = new Scanner(System.in);
        System.out.println("Enter " + item_key + ":");
        String key = in.next();
        System.out.println("Enter title of " + type + ":");
        String title = in.next();
        System.out.println("Enter author name (First):");
        String author = in.next();
        System.out.println("Enter author name (Last):");
        author += " " + in.next();
        System.out.println("Enter publish year (YYYY):");
        int year = in.nextInt();
        System.out.println("Enter rental fee (x.xx):");
        double rental = in.nextFloat();
        System.out.println("Enter replacement fee (xx.xx):");
        double replace = in.nextFloat();

        System.out.println("How many "+type+"s are being added?");
        int stock = in.nextInt();
        ResultSet query_item = Database.queryDB("SELECT * FROM " + table + " WHERE " + item_key + " = '" + key + "';");
        if(query_item.isBeforeFirst() == false)
        {
            // Item didn't exist, go ahead and add it
            Database.updateDB("Insert into " + table + "(" + item_key + ",title,"+author_type+","+release+",rentfee,replacefee) " +
                                "values ('" + key + "','" + title + "','" + author + "'," + year + "," + rental + "," + replace +");");
        }
        else
        {
            // Item existed, update it instead
            Database.updateDB("update " + table + " set title = '" + title + "', " + author_type + " = '" + author +"', " +
                                release + " = " + year + ", rentfee = " + rental + ", replacefee = " + replace +
                                " where " + item_key + " = '" + key + "';");
        }
        ResultSet query_stock = Database.queryDB("Select * FROM " + stock_table + " where " + item_key + " = '" + key + "';");
        if(query_stock.isBeforeFirst() == false)
        {
            // Stock tuple did not exist:
            Database.updateDB("INSERT Into " + stock_table + "(" + item_key + ",SID,stock) values ('" + key + "','" + this.store + "'," + stock + ");");
        }
        else
        {
            // Stock tuple did exist, update it:
            Database.updateDB("UPDATE " + stock_table + " set stock = " + stock + " where " + item_key + " = '" + key + "' and SID = '" + this.store + "';");
        }
    }

    private String ID()
        throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your ID: ");
        String input_id;
        ResultSet employee_query;
        do
        {
            input_id = in.next();
            employee_query = Database.queryDB("Select * from Employee where ID = '" + input_id + "'");
            if(employee_query.isBeforeFirst() == true)
            {
                System.out.println("That ID is taken.  Try again: ");
            }
        }while(employee_query.isBeforeFirst() == true);
        return input_id;
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
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your weekly hours:");
        return in.next();
    }
    
    private String wage(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your hourly rate:");
        return in.next();
    }

}
