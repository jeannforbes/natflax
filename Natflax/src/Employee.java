import java.util.Scanner;

public class Employee {
    private String eSsn, user, password, name[], address, phone, store;

    Employee(String eSsn, String user, String password, String fname, String lname,
             String address, String phone, String SID){
        this.eSsn = eSsn;
        this.user = user;
        this.password = password;
        this.name = new String[]{fname,lname};
        this.address = address;
        this.phone = phone;
        this.store = SID;
    }

    Employee(){
        this.eSsn = eSSN();
        this.user = userName();
        this.password = password();
        this.name = name();
        this.address = address();
        this.phone = phone();
        this.store = storeID();

    }

    public void updateEmpInfo(){
        System.out.println("What info do you want to update?\n" +
                "\t1-Address\n" +
                "\t2-Phone Number\n" +
                "\t3-Name\n" +
                "\t4-Password\n" +
                "\t5-Back");
        Scanner in = new Scanner(System.in);
        int action = in.nextInt();
        switch(action){
            case(1):
                System.out.println("Current address is: "+this.address+
                        "\n -1 to cancel or any key to continue");
                if(in.next().equals("-1")) updateEmpInfo();
                String addr = address();
                this.address = addr;
                System.out.println("Address updated: "+addr);
                //SQL UPDATE Address
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
                System.out.println("Current name is: "+ this.name[0]+" "+ this.name[1]+
                        "\n -1 to cancel or any key to coninue");
                if(in.next().equals("-1")) updateEmpInfo();
                String[] name = name();
                this.name = name;
                System.out.println("Name updated to: "+ name[0]+" "+name[1]);
                //SQL UPDATE 0 = fname, 1 = lname
                updateEmpInfo();
                break;
            case(4):
                System.out.println("Current password is: "+this.password+
                        "\n -1 to cancel or any key to continue");
                if(in.next().equals("-1")) updateEmpInfo();
                this.password = password();
                //SQL UPDATE
                updateEmpInfo();
                break;
            case(5):
                break;
            default:
                System.out.println("Command not recognized");
                updateEmpInfo();
                break;
        }
    }

    public void returnBook(Customer c, String title){
        if(c.checkRentals().contains(title)){
            c.rentalList.remove(title);
        }
    }

    public void addBook(){
        Book b = new Book();
    }

    private String eSSN(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your Social Security Number: (xxx-xx-xxxx)");
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

    private String userName() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a username:");
        //if !Exist
        return in.next();
        //else "its taken try again"
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

    private String storeID(){
        String SID ="1";
        //SQL get store id
        return SID;
    }

}
