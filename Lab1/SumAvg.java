package assignment.basics;

import java.util.Scanner;

import static java.lang.System.*;;

/*
 * Sum and average for integers
 */
public class SumAvg {

    static Scanner sc = new Scanner(in);
    public static void main(String[] args) {
        new SumAvg().program();
    }

    void program()
    {
        // declare 3 variables and 3 checkers
        double dou1;
        double dou2;
        double dou3;
        boolean done = false;
        boolean done1 = false;
        boolean done2 = false;
        // user inputs variables

        // Non-negativeInt checker for variables 1
        while (!done)
        {
            System.out.print("Enter variable 1: ");
            dou1 = sc.nextDouble();
            if (dou1 > 0)
            {
                // Non-negativeInt checker for variables 2
                while (!done1) {
                    System.out.print("Enter variable 2: ");
                    dou2 = sc.nextDouble();
                    if (dou2 > 0)
                    {
                        // Non-negativeInt checker for variables 3
                        while (!done2) {
                            System.out.print("Enter variable 3: ");
                            dou3 = sc.nextDouble();
                            if (dou3 > 0) {
                                double sum = dou1 + dou2 + dou3;
                                int amount = 3;
                                double average = sum / amount;
                                System.out.println("amount: " + amount + " |sum: " + sum + " |average: " + average);
                                break;
                            }
                            else {
                                System.out.print("Negative variables are not acceptable. ");
                                done2 = false;
                            }
                        }
                    }
                    else {
                        System.out.print("Negative variables are not acceptable. ");
                        done1 = false;
                    }
                }
            }
            else {
                System.out.print("Negative variables are not acceptable. ");
                done = false;
            }
        }
    }
}
