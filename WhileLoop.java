package assignment.basics;

import static java.lang.System.*;

/*
 *   Exercising while-loop
 */
public class WhileLoop {

    public static void main(String[] args) {
        new WhileLoop().program();
    }


    void program()
    {
        // Problem a
        System.out.print("a. ");
        int a = -20;
        while ( a < 4 ){
            System.out.print( a+ ", " );
            a = a + 1;
        }
        System.out.println(" ");


        // Problem b
        System.out.print("b. ");
        int b = 10;
        while ( b > -11 ){
            System.out.print( b+ ", " );
            b = b - 1;
        }
        System.out.println(" ");


        // Problem c
        System.out.print("c. ");
        int c = 5;
        while ( c < 101 ){
            System.out.print( c+ ", " );
            c = c + 5;
        }
        System.out.println(" ");


        // Problem d
        System.out.print("d. ");
        double d = 0.0;
        while ( d < 9 ){
            System.out.print( d + ", " );
            d = d + 0.3;
        }
        System.out.println(" ");

        // Problem e
        System.out.print("e. ");
        int e1 = 1;
        int e2 = 2;
        while (e2 < 512) {
            System.out.print(e1 + "/" + e2 + ", ");
            e2 = e2*2;
        }
        System.out.println(" ");

        // Problem f
        System.out.print("f. ");
        double x = 3;
        double sum1 = 1 / 3.0;
        int c1 = 0;

        while ( c1 < 50){
            x = x + 4;
            sum1 = sum1 + (1/x);
            c1 ++;
        }
        double y = 5;
        double sum2 = 1 / 5.0;
        int c2 = 0;

        while ( c2 < 50){
            y = y + 4;
            sum2 = sum2 + (1/y);
            c2 ++;
        }
        double sum3 = 4*(1-sum1 + sum2);
        System.out.println(sum3);
    }
}
