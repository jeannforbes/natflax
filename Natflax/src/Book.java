import java.util.InputMismatchException;
import java.util.Scanner;

public class Book {
    String title, ISBN, author;
    int year;
    float rental, replace;

    Book(String title, String ISBN, String author, int year, float rental, float replace){
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.year = year;
        this.rental = rental;
        this.replace = replace;
    }

    Book(){
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Enter title of book:");
            this.title = in.next();
            System.out.println("Enter ISBN:");
            this.ISBN = in.next();
            System.out.println("Enter author name (First Last):");
            this.author = in.next();
            System.out.println("Enter publish year (YYYY):");
            this.year = in.nextInt();
            System.out.println("Enter rental fee (x.xx):");
            this.rental = in.nextFloat();
            System.out.println("Enter replacement fee (xx.xx):");
            this.replace = in.nextFloat();
        }catch(InputMismatchException e){
            System.out.println("Input did not match.");
        }
    }
}