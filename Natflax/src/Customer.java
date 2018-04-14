import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {

    private String CID, user, password, name[], address, bday, phone, payment[];
    public ArrayList<String> rentalList;

    public Customer(String CID, String user, String password, String fname, String lname, String address, String bday,
                    String phone, String cc, String ccExp, String ccPin, String ccSec, ArrayList<String> rentalList){
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
        this.user = info[1];
        this.password = info[2];
        this.name = new String[]{info[3],info[4]};
        this.address = info[5];
        this.bday = info[6];
        this.phone = info[7];
        this.payment = new String[]{cc_info[0],cc_info[1],cc_info[2],cc_info[3]};
    }
    public Customer(String[] info){
        this.CID = info[0];
        this.user = info[1];
        this.password = info[2];
        this.name = new String[]{info[3],info[4]};
        this.address = info[5];
        this.bday = info[6];
        this.phone = info[7];
    }

    public Customer(){
        this.CID = custID();
        this.user = userName();
        this.password = password();
        this.name = name();
        this.address = address();
        this.bday = bDay();
        this.phone = phone();
        this.payment = paymentMethod();
    }

    public void updateCustInfo(){
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
                if(in.next().equals("-1")) updateCustInfo();
                String addr = address();
                this.address = addr;
                System.out.println("Address updated: "+addr);
                //SQL UPDATE Address
                updateCustInfo();
                break;
            case(2):
                System.out.println("Current phone is:" + this.phone+
                        "\n -1 to cancel or any key to continue");
                if(in.next().equals("-1")) updateCustInfo();
                String phone = phone();
                this.phone = phone;
                System.out.println("Phone updated: "+phone);
                //SQL UPDATE phone
                updateCustInfo();
                break;
            case(3):
                System.out.println("Current name is: "+ this.name[0]+" "+ this.name[1]+
                        "\n -1 to cancel or any key to coninue");
                if(in.next().equals("-1")) updateCustInfo();
                String[] name = name();
                this.name = name;
                System.out.println("Name updated to: "+ name[0]+" "+name[1]);
                //SQL UPDATE 0 = fname, 1 = lname
                updateCustInfo();
                break;
            case(4):
                System.out.println("Current password is: "+this.password+
                        "\n -1 to cancel or any key to continue");
                if(in.next().equals("-1")) updateCustInfo();
                this.password = password();
                //SQL UPDATE
                updateCustInfo();
                break;
            case(5):
                System.out.println("Current payment method is:\n" +
                        this.payment[0]+"\n" +
                        this.payment[1]+"\n" +
                        this.payment[2]+
                        "\n -1 to cancel or any key to continue");
                if(in.next().equals("-1")) updateCustInfo();
                String[] pmt = paymentMethod();
                this.payment = pmt;
                //SQL UPDATE 0 = ccNum, 1 = exp, 2 = sec
                System.out.println("Payment method updated to: " +
                        pmt[0]+"\n" +
                        pmt[1]+"\n" +
                        pmt[2]);
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
        System.out.println("Enter your birthdate (MM/DD/YYYY):");
        return in.next();
    }

    private String userName(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a username:");
        //if !Exist
        return in.next();
        //else "its taken try again"
    }

    private String custID(){
        String CID="1";
        //SQL create an ID?
        return CID;
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
        String[] payment = new String[3];
        System.out.println("Enter credit card numebr");
        payment[0] = in.next();
        System.out.println("Enter expiration date (mm/yy)");
        payment[1] = in.next();
        System.out.println("Enter security pin on back of card");
        payment[2] = in.next();
        return payment;
    }

    public ArrayList<String> checkRentals(){
        //SQL SELECT customer's rentals, populate array with CURSOR
        if (this.rentalList.size() > 0) {
            System.out.println("You currently have these titles out:");
            for (String item : this.rentalList)
                System.out.println(item);
        } else
            System.out.println("You don't have any titles out right now.");
        return this.rentalList;
    }

    public void checkOut(String type)
            throws Exception{
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
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the " + type + "'s " + item_key + "(-1 to cancel):");

        try{
            String item = in.next();
            if(item.equals("-1")) return;
            
            ResultSet item_query = Database.queryDB("SELECT " + table + ".* from " + table + " natural join " + stock_table + " where stock > 0 and " + table + "." + item_key + " = '" + item + "'");
            // Item was found - ask them to pick a store to rent from
            if(item_query.first() == true)
            {
                ResultSet already_renting = Database.queryDB("SELECT * FROM " + rent_table + " WHERE CID = '" + this.CID + "' and " + item_key + " = '" + item +"'");
                if(already_renting.next() == true)
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
                while((valid_query = store_query.first()) == false);
                
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
