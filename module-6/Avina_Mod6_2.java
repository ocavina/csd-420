// Oscar Avina
// 09/13/2026
// Module 6.2 Assignment
// This program uses generic bubble sort methods to sort arrays with Comparable and Comparator.

import java.util.Arrays;
import java.util.Comparator;

public class Avina_Mod6_2 {

    public static void main(String[] args) {
        // stores the expected ascending order for both tests
        Integer[] expectedNumbers = {
                -5, -2, 0, 1, 2, 3, 4, 5, 6, 7,
                8, 8, 9, 11, 12, 14, 15, 16, 19, 20};

        // tests the Comparable method with numbers that are out of order
        Integer[] comparableNumbers = {
                12, -5, 8, 3, 19, 0, 8, 14, -2, 7,
                1, 16, 4, 11, 6, 20, 9, 2, 15, 5};
        bubbleSort(comparableNumbers);
        // displays the sorted numbers and checks them against the expected values
        System.out.println("Comparable result: " + Arrays.toString(comparableNumbers));
        System.out.println("Test passed: " + Arrays.equals(comparableNumbers, expectedNumbers));

        // tests the Comparator method with a separate array and an ascending order rule
        Integer[] comparatorNumbers = {
                12, -5, 8, 3, 19, 0, 8, 14, -2, 7,
                1, 16, 4, 11, 6, 20, 9, 2, 15, 5};
        bubbleSort(comparatorNumbers, Comparator.naturalOrder());
        // displays the sorted numbers and checks them against the expected values
        System.out.println("\nComparator result: " + Arrays.toString(comparatorNumbers));
        System.out.println("Test passed: " + Arrays.equals(comparatorNumbers, expectedNumbers));
    }

    // sorts the array using the natural order of its elements
    public static <E extends Comparable<E>> void bubbleSort(E[] values) {
        // repeats passes through the array to move each value into its correct position
        for (int pass = 0; pass < values.length - 1; pass++) {
            // compares each value with the value next to it
            for (int index = 0; index < values.length - 1; index++) {
                // swaps neighboring values when the comparison result is positive
                if (values[index].compareTo(values[index + 1]) > 0) {
                    // saves the left value so it is not lost during the swap
                    E temporaryValue = values[index];
                    values[index] = values[index + 1];
                    values[index + 1] = temporaryValue;
                }
            }
        }
    }

    // sorts the array using the comparison rule supplied to the method
    public static <E> void bubbleSort(E[] values, Comparator<E> comparator) {
        // repeats the passes using the supplied comparison rule
        for (int pass = 0; pass < values.length - 1; pass++) {
            for (int index = 0; index < values.length - 1; index++) {
                // uses the comparator to check whether the values are out of order
                if (comparator.compare(values[index], values[index + 1]) > 0) {
                    // saves the left value so it is not lost during the swap
                    E temporaryValue = values[index];
                    values[index] = values[index + 1];
                    values[index + 1] = temporaryValue;
                }
            }
        }
    }
}