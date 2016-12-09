package assignment.basics;

import static java.lang.System.out;

/*
 * Exercising nested for loops
 */
public class NestedForLoop {
    /** Main **/
    // TODO Print out a half square and a rhombus
    public static void main(String[] args) {

        printHalfSquare();
        printRhombus();

    }
    /** Methods **/
    public static void printHalfSquare() {
        for(int i1 = 11; i1 > 0; i1--){
            for(int i2 = 0; i2<i1; i2++){
                if (i2 % 2 == 0) {
                    System.out.print("X");
                }else{
                    System.out.print("O");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printRhombus() {
        for (int i = 0; i < 5; i++)
            System.out.println("    *********".substring(i, 5 + 2*i));

        for (int i =5; i>0;i--)
            System.out.println("     **********".substring(i-1,5+(2*i)-3));

        System.out.println();
    }
}

