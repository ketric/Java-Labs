package assignment.basics;

import static java.lang.System.*;

import java.util.Scanner;

/*
 *  Program to calculate easter day for some year (1900-2099)
 */
import java.util.Scanner;
public class EasterDay {

    public static void main(String[] args) {
        new EasterDay().program();
    }
    static Scanner sc = new Scanner(System.in);

    void program() {
        // Write your code here
        // Structure program as: Input -> Process -> Output

        int year;
        int x;
        String easter;
        Boolean done = false;

        while (!done)
        {
            System.out.print("Enter a year: ");
            year = sc.nextInt();
            if (year >= 1900 && year <= 2099)
            {
                int A, B, C, D, E, S, T;   // Avoid on same line (acceptable here)

                A = year % 19; //( mod = resten då årtal divideras med 19)
                B = year % 4;
                C = year % 7;
                S = 19 * A + 24;
                D = S % 30;
                T = 2 * B + 4 * C + 6 * D + 5;
                E = T % 7;
                x = 22 + D + E;

                if (x < 32) {
                    easter = "27/3";
                    System.out.print("The easter of year " +  year + " is : " + easter);
                }
                else
                {
                    //System.out.print(year);

                    int y = D + E - 9;

                    if (y == 26) {
                        easter = "19/4";
                        System.out.print("The easter of year " +  year + " is : " + easter);
                    }
                    else if (y == 25 && A == 16 && D == 28) {
                        easter = "18/4";
                        System.out.print("The easter of year " +  year + " is : " + easter);
                    }
                    else {
                        easter = (y + "/4");
                        System.out.print("The easter of year " +  year + " is : " + easter);
                    }
                }
                done = true;
            }
            else {
                System.out.println("Please enter a year within 1900 and 2099. ");
                done = false;
            }
        }
    }
}
