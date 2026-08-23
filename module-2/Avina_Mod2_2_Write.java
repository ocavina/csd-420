// Oscar Avina
// 08/23/2026
// Module 2.2 Assignment
// This program stores random integers and doubles in a binary file.

import java.io.IOException;
import java.io.RandomAccessFile;

public class Avina_Mod2_2_Write {

    public static void main(String[] args) {
        int[] randomIntegers = new int[5];
        double[] randomDoubles = new double[5];

        // fills both arrays with five random values
        for (int index = 0; index < randomIntegers.length; index++) {
            randomIntegers[index] = (int) (Math.random() * 100);
            randomDoubles[index] = Math.random() * 100;
        }

        try {
            // uses "rw" mode to create the file if it does not already exist
            RandomAccessFile dataFile =
                    new RandomAccessFile("OscarAvinaDataFile.dat", "rw");

            // moves to the end of the file so the new values are appended
            dataFile.seek(dataFile.length());

            // writes the five integers to the file
            for (int index = 0; index < randomIntegers.length; index++) {
                dataFile.writeInt(randomIntegers[index]);
            }

            // writes the five doubles to the file
            for (int index = 0; index < randomDoubles.length; index++) {
                dataFile.writeDouble(randomDoubles[index]);
            }

            // closes the file when writing is finished
            dataFile.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
