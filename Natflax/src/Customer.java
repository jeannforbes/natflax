import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {

    private String CID, user, password, name[], address, bday, phone, payment[];
    private ArrayList<String> rentalList;

    public Customer(String CID, String user, String password, String fname, String lname, String address, String bday,
                    String phone, String cc, String ccExp, String ccSec, ArrayList<String> rentalList){
        this.name = new String[2];
        this.payment = new String[3];
        this.CID = CID;
        this.user = user;
        this.password = password;
        this.name[0] = fname;
        this.name[1] = lname;
        this.address = address;
        this.bday = bday;
        this.phone = phone;
        this.payment[0] = cc;
        this.payment[1] = ccExp;
        this.payment[2] = ccSec;
        this.rentalList = rentalList;

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
        this.rentalList = checkRentals();
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
        String CID="";
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

    public void checkOut(String[] results){
        Scanner in = new Scanner(System.in);
        System.out.println("Which result would you like to check out?\n" +
                "-1 to cancel");
        for (int i = 0; i < results.length; i++) {
            System.out.println(i+"-"+results[i]);
        }
        try{
            int rent = in.nextInt();
            if(rent == -1) return;
            this.rentalList.add(results[rent]);
            //SQL UPDATE Stock, cust rentals
            System.out.println(results[rent]+" has been added to your list of rentals");
        }catch(InputMismatchException e){
            System.out.println("Input not in correct format, try again.");
            checkOut(results);
        }
    }
}
