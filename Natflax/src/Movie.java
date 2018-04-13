import java.util.InputMismatchException;
import java.util.Scanner;

public class Movie {
    String title, ISAN, author;
    int year;
    float rental, replace;

    Movie(String title, String ISAN, String author, int year, float rental, float replace){
        this.title = title;
        this.ISAN = ISAN;
        this.author = author;
        this.year = year;
        this.rental = rental;
        this.replace = replace;
    }

    Movie(){
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Enter title of movie:");
            this.title = in.next();
            System.out.println("Enter ISAN:");
            this.ISAN = in.next();
            System.out.println("Enter director name (First Last):");
            this.author = in.next();
            System.out.println("Enter release year (YYYY):");
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