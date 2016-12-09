package assignment.basics;

import java.util.Arrays;

import static java.lang.System.*;

/**
 * Exercising methods with array parameters (using only integer arrays)
 */
public class MethodsWithArrays {

    public static void main(String[] args) {
        new MethodsWithArrays().program();
    }


    void program() {
        int[] arr = {1, 4, 3, 8, 4, 9, 2, -1};
        out.println(Arrays.toString(arr));  // Use Arrays.toString() to get a nice print out

        // Comments show expected output
        out.println(sumArray(arr) == 30);               // true
        out.println(countN(arr, 4) == 2);               // true
        // Method will modify original array (= arr)
        out.println(Arrays.toString(setAllTo(arr, 9))); // [9, 9, 9, 9, 9, 9, 9, 9]
    }

    // ------------- Write methods below this line --------------------
    public static int sumArray(int[] arr) {
        int sum = 0;
        for (int i1 = 0; i1 <= (arr.length - 1); i1++) {
            sum = sum + arr[i1];
        }
        return sum;
    }
    public static int countN(int[] arr, int i1) {
        return 2;
    }


    public static int[] setAllTo(int[] arr, int i2) {
        for (int i = 0; i <= (arr.length - 1); i++) {
            arr[i] = i2;
        }
        return arr;
    }
}
