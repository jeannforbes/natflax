import java.util.ArrayList;
import java.util.Scanner;

public class Store {
    String SID, address, phone, mgrSSN;
    ArrayList<Book> bookCatalog;
    ArrayList<Movie> movieCatalog;
    ArrayList<Employee> employees;

    Store(String SID, String address, String phone, String mgrSSN,
          ArrayList<Book> bookCatalog, ArrayList<Movie> movieCatalog, ArrayList<Employee> employees){
        this.SID = SID;
        this.address = address;
        this.phone = phone;
        this.mgrSSN = mgrSSN;
        this.bookCatalog = bookCatalog;
        this.movieCatalog = movieCatalog;
        this.employees = employees;
    }

    Store(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Store ID:");
        this.SID = in.next();
        System.out.println("Enter Store address:");
        this.address = in.next();
        System.out.println("Enter Store phone number:");
        this.phone = in.next();
        System.out.println("Enter Store manager SSN:");
        this.mgrSSN = in.next();
        this.bookCatalog = new ArrayList<>();
        this.movieCatalog = new ArrayList<>();
        this.employees = new ArrayList<>();
    }
}
