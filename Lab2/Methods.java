package assignment.basics;

import static java.lang.System.*;

/*
 * Exercising methods with primitive types
 */
public class Methods {

    public static void main(String[] args) {
        new Methods().program();
    }


    void program() {
        // Testing the methods.
        // All output should print "true" (comment/uncomment as needed)
        out.println(sign(-100) == -1);
        out.println(sign(0) == 0);
        out.println(sign(14) == 1);

        out.println(factorial(0)== 1);
        out.println(factorial(1)== 1);
        out.println(factorial(2)== 2);
        out.println(factorial(3)== 6);

        out.println(gcd(24, 8) == 8);
        out.println(gcd(7, 2) == 1);
    }

    // ------------- Write your methods below this line --------------------

    public static int sign(int i1) {
        if (i1 == 0) {return 0;}
        else if (i1 > 1) {return 1;}
        else {return -1;}
    }
    public static int factorial(int i2) {
        int factorialX = 1;
        int index = 1;
        while (index <= i2) {
            factorialX = factorialX * index;
            index ++;
        }
        return factorialX;
    }
    public static int gcd(int i3, int i4) {
        if (i4 == 0) {return i3;}
        return gcd(i4, i3%i4); // recursive calling. Euclidean formulas for finding GCD
    }
}
