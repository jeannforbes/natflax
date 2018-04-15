import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {

    private String CID, user, password, name[], address, bday, phone, payment[];

    public Customer(String CID, String user, String password, String fname, String lname, String address, String bday,
                    String phone, String cc, String ccExp, String ccPin, String ccSec){
        this.CID = CID;
        this.user = user;
        this.password = password;
        this.name = new String[]{fname,lname};
        this.address = address;
        this.bday = bday;
        this.phone = phone;
        this.payment = new String[]{cc,ccExp,ccPin,ccSec};
    }
    public Customer(String[] info, String[] cc_info){
        this.CID = info[0];
        this.password = info[1];
        this.user = info[2];
        this.address = info[3];
        this.bday = info[4];
        this.phone = info[5];
        this.name = new String[]{info[6],info[7]};
        this.payment = new String[]{cc_info[0],cc_info[1],cc_info[2],cc_info[3]};
    }
    public Customer(String[] info){
        this.CID = info[0];
        this.password = info[1];
        this.user = info[2];
        this.address = info[3];
        this.bday = info[4];
        this.phone = info[5];
        this.name = new String[]{info[6],info[7]};
    }

    public Customer()
        throws Exception{
        this.CID = custID();
        this.user = userName();
        this.password = password();
        this.name = name();
        this.address = address();
        this.bday = bDay();
        this.phone = phone();
        this.payment = paymentMethod();
        Database.updateDB("INSERT into Customer(CID, PASSWORD, USERNAME, ADDRESS, BIRTHDAY, PHONE, FIRSTNAME, LASTNAME)" +
                            " values ('" + this.CID + "','" + this.password + "','" + this.user +
                            "','" + this.address +"','" + this.bday + "','" + this.phone + 
                            "','" + this.name[0] + "','" + this.name[1] + "');");
        
        Database.updateDB("INSERT into Payment(CID,CCNUMBER,CCEXPIRATION,CCPIN,CCSECURITY)" +
                            " values ('" + this.CID + "','" + this.payment[0] + "','" + this.payment[1] + "','" + this.payment[2] + "','" + this.payment[3] + "');");
    }

    public void updateCustInfo()
        throws Exception {
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
                System.out.println("Current address is: "+this.address+
                        "\n -1 to cancel or any key to continue");
                in.hasNext();
                if(in.next().equals("-1"))
                {
                    updateCustInfo();
                    break;
                }
                String addr = address();
                this.address = addr;
                System.out.println("Address updated: "+addr);
                //SQL UPDATE Address
                Database.updateDB("UPDATE Customer SET Address = '" + addr + "' WHERE CID = '" + this.CID + "'");
                updateCustInfo();
                break;
            case(2):
                System.out.println("Current phone is:" + this.phone+
                        "\n -1 to cancel or any key to continue");
                if(in.next().equals("-1"))
                {
                    updateCustInfo();
                    break;
                }
                String phone = phone();
                this.phone = phone;
                System.out.println("Phone updated: " + phone);
                Database.updateDB("UPDATE Customer SET Phone = '" + phone + "' WHERE CID = '" + this.CID + "'");
                updateCustInfo();
                break;
            case(3):
                System.out.println("Current name is: "+ this.name[0]+" "+ this.name[1]+
                        "\n -1 to cancel or any key to coninue");
                if(in.next().equals("-1"))
                {
                    updateCustInfo();
                    break;
                }
                String[] name = name();
                this.name = name;
                System.out.println("Name updated to: "+ name[0]+" "+name[1]);
                Database.updateDB("UPDATE Customer SET firstname = '" + name[0] + "', lastname = '" + name[1] + "' WHERE CID = '" + this.CID + "'");
                updateCustInfo();
                break;
            case(4):
                System.out.println("Current password is: "+this.password+
                        "\n -1 to cancel or any key to continue");
                if(in.next().equals("-1"))
                {
                    updateCustInfo();
                    break;
                }
                this.password = password();
                Database.updateDB("UPDATE Customer SET Password = '" + this.password + "' WHERE CID = '" + this.CID + "'");
                updateCustInfo();
                break;
            case(5):
                if(this.payment != null)
                {
                    System.out.println("Current payment method is:\n" +
                            this.payment[0]+"\n" +
                            this.payment[1]+"\n" +
                            this.payment[2]+"\n" +
                            this.payment[3]+"\n" +
                            "-1 to cancel or any key to continue");
                }
                else
                {
                    System.out.println("Current payment method is empty.\n" +
                            "-1 to cancel or any key to continue");                    
                }
                if(in.next().equals("-1"))
                {
                    updateCustInfo();
                    break;
                }
                String[] pmt = paymentMethod();
                this.payment = pmt;
                Database.updateDB("UPDATE Payment SET CCNumber = '" + pmt[0] + 
                                  "', CCExpiration = '" + pmt[1] + 
                                  "', CCPin = '" + pmt[2] + 
                                  "', CCSecurity = '" + pmt[3] + 
                                  " WHERE CID = '" + this.CID + "'");
                System.out.println("Payment method updated to: " +
                        pmt[0]+"\n" +
                        pmt[1]+"\n" +
                        pmt[2]+"\n" +
                        pmt[3]);
                updateCustInfo();
                break;
            case(6):
                break;
            default:
                System.out.println("Command not recognized");
                updateCustInfo();
                break;
        }
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

    private String address(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your Address");
        return in.next();
    }
    private String phone(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your phone number (xxx)xxx-xxxx :");
        return in.next();
    }

    private String bDay(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your birthdate (YYYY-MM-DD):");
        return in.next();
    }

    private String userName()
        throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a username:");
        String input_name;
        ResultSet user_query;
        do
        {
            input_name = in.next();
            user_query = Database.queryDB("Select * from Customer where username = '" + input_name + "'");
            if(user_query.isBeforeFirst() == true)
            {
                System.out.println("That username is taken.  Try again: ");
            }
        }while(user_query.isBeforeFirst() == true);

        return input_name;
    }

    private String custID()
        throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a CID:");
        String input_id;
        ResultSet user_query;
        do
        {
            input_id = in.next();
            user_query = Database.queryDB("Select * from Customer where CID = '" + input_id + "'");
            if(user_query.isBeforeFirst() == true)
            {
                System.out.println("That CID is taken.  Try again: ");
            }
        }while(user_query.isBeforeFirst() == true);
        return input_id;
    }

    private String password() {
        Scanner in = new Scanner(System.in);
        String pw1="", pw2;
        boolean set = false;
        while (!set) {
            System.out.println("Enter a new password:");
            pw1 = in.next();
            System.out.println("Confirm new password:");
            pw2 = in.next();
            if (pw1.equals(pw2)) {
                System.out.println("Password set");
                set = true;
            } else
                System.out.println("Passwords do not match, try again.");
        }
        return pw1; //shouldn't return until pws match, if it does, you have no password ¯\_(ツ)_/¯
    }

    private String[] paymentMethod(){
        Scanner in = new Scanner(System.in);
        String[] payment = new String[4];
        System.out.println("Enter credit card numebr");
        payment[0] = in.next();
        System.out.println("Enter expiration date (YYYY-MM-DD)");
        payment[1] = in.next();
        System.out.println("Enter pin number (####)");
        payment[2] = in.next();
        System.out.println("Enter security code on back of card");
        payment[3] = in.next();
        return payment;
    }

    public void checkRentals()
        throws Exception{
        String[] type = new String[2];
        String[] rent_table = new String[2];
        
        type[0] = "book";
        type[1] = "movie";
        rent_table[0] = "rented_books";
        rent_table[1] = "rented_movies";
        for(int i = 0; i < 2; i++)
        {
            ResultSet rentals = Database.queryDB("SELECT * FROM " + rent_table[i] + " WHERE CID = '" + this.CID + "'");
            if(rentals.isBeforeFirst() == true)
            {
                System.out.println("\nYou currently have these " + type[i] + "s rented:");
                Database.printResultSet(rentals);
            }
            else
            {
                System.out.println("You are not renting any " + type[i] + "s right now.");
            }
        }
    }

    public void checkOut(String type)
            throws Exception{
        Scanner in = new Scanner(System.in);
        String item_key, table, stock_table, rent_table;
        if(type.equalsIgnoreCase("movie"))
        {
            item_key = "ISAN";
            table = "Movie";
            stock_table = "Movies_in_stock";
            rent_table = "rented_movies";
        }
        else
        {
            item_key = "ISBN";
            table = "Book";
            stock_table = "Books_in_stock";
            rent_table = "rented_books";
        }
        // Here's where a well architected codebase would do
        // if this.payment == NULL.  But payment isn't always loaded into
        // the class on startup so that's not guarenteed.
        ResultSet payment_query = Database.queryDB("Select * from Payment where CID = '" + this.CID + "';");
        if(payment_query.isBeforeFirst() == false)
        {
            System.out.println("You need a payment method before renting a " + type + ".");
            return;
        }
        System.out.println("Enter the " + type + "'s " + item_key + "(-1 to cancel):");

        try{
            String item = in.next();
            if(item.equals("-1")) return;
            
            ResultSet item_query = Database.queryDB("SELECT " + table + ".* from " + table + " natural join " + stock_table + " where stock > 0 and " + table + "." + item_key + " = '" + item + "'");
            // Item was found - ask them to pick a store to rent from
            if(item_query.first() == true)
            {
                ResultSet already_renting = Database.queryDB("SELECT * FROM " + rent_table + " WHERE CID = '" + this.CID + "' and " + item_key + " = '" + item +"'");
                if(already_renting.isBeforeFirst() == true)
                {
                    System.out.println("You're already renting a copy of that " + type + ".");
                    return;
                }
                System.out.println("List of stores with that have that " + type +":");
                ResultSet store_query = Database.queryDB("SELECT Store.* from Store natural join " + stock_table + " where stock > 0 and " + item_key + " = '" + item + "'");
                boolean valid_query = Database.printResultSet(store_query);
                
                System.out.println("\nWhich store would you like to rent from (Enter SID):");
                String store_id;
                do
                {
                    if(valid_query == false)
                    {
                        System.out.println("Store not found. Enter SID: ");
                    }
                    store_id = in.next();
                    store_query = Database.queryDB("SELECT * from " + stock_table + " where stock > 0 and SID = '" + store_id + "' and " + item_key + " = '" + item + "'");
                }
                while((valid_query = store_query.isBeforeFirst()) == false);
                
                Database.updateDB("INSERT INTO " + rent_table + "(CID, " + item_key + ",SID, rented_date) " +
                                 "values ('" + this.CID + "','" + item +"','" + store_id + "', GETDATE());");
                Database.updateDB("UPDATE " +  stock_table + " SET stock = stock - 1 WHERE " + 
                                 item_key + " = '" + item + "' and SID = '" + store_id + "'");
                // Rent the book from the store
                //System.out.println(results[rent]+" has been added to your list of rentals");
            }
            else
            {
                // Item not found
                System.out.println("The " + type + " you've requested is not available\n");
                checkOut(type);
            }
        }catch(InputMismatchException e){
            System.out.println("Input not in correct format, try again.");
            checkOut(type);
        }
    }
}
