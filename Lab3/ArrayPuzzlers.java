package assignment.basics;

import java.util.Arrays;

import static java.lang.System.*;

/*
 * Some array puzzlers
 */
public class ArrayPuzzlers {


    public static void main(String[] args) {
        new ArrayPuzzlers().program();
    }

    void program() {
        // This is testing. expected outcome as commented
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr2 = {1, 2, 2, 3, 3};
        int[] arr3 = {1, 2, 3, 4, 5};
        int[] arr4 = {1, 1, 1, 1, 1, 1};

        out.println(Arrays.toString(arr1));  // [1, 2, 3, 4, 5, 6, 7, 8]
        int [] rotatedList = rotate(arr1, 3);
        out.println(Arrays.toString(rotatedList));  //[6, 7, 8, 1, 2, 3, 4, 5]

        out.println(Arrays.toString(arr2));   // [1, 2, 2, 3, 3]
        out.println(Arrays.toString(removeDupl(arr2)));  // [1, 2, 3]
        out.println(Arrays.toString(removeDupl(arr3)));  // [1, 2, 3, 4, 5]
        out.println(Arrays.toString(removeDupl(arr4)));  // [1]

        out.println(Arrays.toString(arr2));  // [1, 2, 2, 3, 3] not changed



    }



    // TODO Rotate all elements in arr k steps to the right (in a circular fashion)
    public static int[] rotate(int[] list, int steps){
        int index;
        int[] rotatedList = new int[list.length];
        for (index = 0; index < list.length; index ++) {
            int rotatedIndex = (index + steps) % (list.length);
            rotatedList[rotatedIndex] = list[index];
        }
        return rotatedList;
    }



    // TODO Remove all duplicates from arr (original unchanged, copy created)
    // NOTE: Assume arr i sorted in increasing order
    public static int[] removeDupl(int[] list) {
        if (list.length < 2) {return list;
        }else{
            int index1 = 0;
            int index2 = 1;

            while (index2 < list.length) {
                if (list[index1] == list[index2]) {
                    index2++;
                }else{
                    index1++;
                    list[index1] = list[index2];
                    index2++;
                }
            }

            int[] nonDupList = Arrays.copyOf(list, index1 + 1);
            return nonDupList;
        }
    }
}

