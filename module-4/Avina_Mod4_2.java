// Oscar Avina
// 08/30/2026
// Module 4.2 Assignment
// This program compares the time to traverse a LinkedList using an iterator and get(index).

import java.util.Iterator;
import java.util.LinkedList;

public class Avina_Mod4_2 {

    public static void main(String[] args) {
        // tests the time with both required list sizes
        testLinkedListTraversal(50000);
        testLinkedListTraversal(500000);
    }

    public static void testLinkedListTraversal(int listSize) {
        LinkedList<Integer> integerList = new LinkedList<>();
        long expectedSum = 0;

        // fills the list with the required integers and calculates their expected total
        for (int number = 0; number < listSize; number++) {
            integerList.add(number);
            expectedSum += number;
        }

        System.out.println("Testing " + listSize + " integers...");

        // traverses the list with an iterator and records the elapsed time
        long iteratorSum = 0;
        Iterator<Integer> iterator = integerList.iterator();
        long iteratorStartTime = System.currentTimeMillis();

        while (iterator.hasNext()) {
            iteratorSum += iterator.next();
        }

        long iteratorTime =
                System.currentTimeMillis() - iteratorStartTime;

        // traverses the same list with get(index) and records the elapsed time
        long getSum = 0;
        long getStartTime = System.currentTimeMillis();

        for (int index = 0; index < listSize; index++) {
            getSum += integerList.get(index);
        }

        long getTime = System.currentTimeMillis() - getStartTime;
        long timeDifference = getTime - iteratorTime;

        System.out.println(
                "Iterator time: " + iteratorTime + " ms");
        System.out.println(
                "get(index) time: " + getTime + " ms");
        System.out.println(
                "Difference: " + timeDifference + " ms");

        // confirms that the list size and both calculated totals are correct
        if (integerList.size() == listSize
                && iteratorSum == expectedSum
                && getSum == expectedSum) {
            System.out.println("Correctness test: Passed");
        }
        else {
            System.out.println("Correctness test: Failed");
        }

        System.out.println();
    }
}

// Test results:
// Traversing 50,000 integers took 2 ms with an iterator and
// 641 ms with get(index), for a difference of 639 ms.

// Traversing 500,000 integers took 3 ms with an iterator and
// 71,513 ms with get(index), for a difference of 71,510 ms.

// The iterator was faster because it moved directly from one linked element
// to the next. The get(index) approach was slower because each call had to
// locate the requested position in the LinkedList again. When the list increased from 50,000
// to 500,000 integers, the get(index) time increased from 641 ms to 71,513 ms.
// This shows that repeatedly using get(index) becomes much less efficient as a LinkedList grows larger.