// Oscar Avina
// 08/29/2026
// Module 3.2 Assignment
// This program generates random integers and uses a generic method to remove duplicate values.

import java.util.ArrayList;

public class Avina_Mod3_2 {

    public static void main(String[] args) {
        ArrayList<Integer> originalValues = new ArrayList<>();

        // fills the original list with 50 random integers from 1 through 20
        for (int count = 0; count < 50; count++) {
            int randomValue = (int) (Math.random() * 20) + 1;
            originalValues.add(randomValue);
        }

        // uses the generic method to create a new list without duplicate values
        ArrayList<Integer> uniqueValues = removeDuplicates(originalValues);

        System.out.println("Original ArrayList:");
        System.out.println(originalValues);

        System.out.println("ArrayList without duplicates:");
        System.out.println(uniqueValues);
    }

    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        // creates a new list that will store only one copy of each value
        ArrayList<E> uniqueValues = new ArrayList<>();

        // checks each value from the original list
        for (int index = 0; index < list.size(); index++) {
            E currentValue = list.get(index);

            // adds the current value only if it has not already been stored
            if (!uniqueValues.contains(currentValue)) {
                uniqueValues.add(currentValue);
            }
        }

        return uniqueValues;
    }
}