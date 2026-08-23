// Oscar Avina
// 08/23/2026
// Module 2.2 Assignment
// This program reads and displays integers and doubles from a binary file.

import java.io.IOException;
import java.io.RandomAccessFile;

public class Avina_Mod2_2_Read {

    public static void main(String[] args) {
        int valuesPerArray = 5;

        try {
            // opens the existing file for reading
            RandomAccessFile dataFile =
                    new RandomAccessFile("OscarAvinaDataFile.dat", "r");

            // continues reading until the end of the file is reached
            while (dataFile.getFilePointer() < dataFile.length()) {
                System.out.print("Integers: ");

                // reads and displays five integers
                for (int index = 0; index < valuesPerArray; index++) {
                    System.out.print(dataFile.readInt() + " ");
                }

                System.out.println();
                System.out.print("Doubles: ");

                // reads and displays five doubles
                for (int index = 0; index < valuesPerArray; index++) {
                    System.out.print(dataFile.readDouble() + " ");
                }

                System.out.println();
            }

            // closes the file when reading is finished
            dataFile.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
