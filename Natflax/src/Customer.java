import java.util.Scanner;

public class Customer {

    private String CID, user, password, name[], address, bday, phone, payment[];

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
                String addr = address();
                System.out.println("Address updated: "+addr);
                //SQL UPDATE Address
                updateCustInfo();
                break;
            case(2):
                String phone = phone();
                System.out.println("Phone updated: "+phone);
                //SQL UPDATE phone
                updateCustInfo();
                break;
            case(3):
                String[] name = name();
                System.out.println("Name updated to: "+ name[0]+" "+name[1]);
                //SQL UPDATE 0 = fname, 1 = lname
                updateCustInfo();
                break;
            case(4):
                String pw = password();
                //SQL UPDATE
                updateCustInfo();
                break;
            case(5):
                String[] pmt = paymentMethod();
                //SQL UPDATE 0 = ccNum, 1 = exp, 2 = sec
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
        boolean set = false;
        while (!set) {
            System.out.println("Enter a new password:");
            String pw1 = in.next();
            System.out.println("Confirm new password:");
            String pw2 = in.next();
            if (pw1.equals(pw2)) {
                System.out.println("Password set");
                return pw1;
            } else
                System.out.println("Passwords do not match, try again.");
        }
        return null; // cant have this need replacement
    }

    private String[] paymentMethod(){
        Scanner in = new Scanner(System.in);
        String[] payment = new String[3];
        System.out.println("Enter credit card numebr");
        payment[0] = in.next();
        System.out.println("Enter expiration date (mm/yyyy)");
        payment[1] = in.next();
        System.out.println("Enter security pin on back of card");
        payment[2] = in.next();
        return payment;
    }

    public void checkRentals(){
        String[] rentals = {"book"};
        //SQL SELECT customer's rentals, populate array with CURSOR
        if (rentals.length>0) {
            System.out.println("You currently have these titles out:");
            for (String item : rentals)
                System.out.println(item);
        } else
            System.out.println("You don't have any titles out right now.");
    }
}
