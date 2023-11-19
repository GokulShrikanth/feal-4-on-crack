package com.blaze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadInputs {
    static data input = data.createData();
    public static data inputs() throws IOException, FileNotFoundException {
        System.out.println("Reading data from the file, known_geoff.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("known_geoff.txt"))) {
            int count = 0;
            boolean isPlainText = true;
            String line = bufferedReader.readLine();

            while (line != null && count < input.plaintext.length) {
                if (line.length() != 0) {
                    if (isPlainText) {
                        input.plaintext[count] = line.substring(12);
                    } else {
                        input.cyphertext[count] = line.substring(12);
                        count++;
                    }
                    isPlainText = !isPlainText;
                }
                line = bufferedReader.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException Occured :" + e.getMessage());
            System.exit(0);
        }
        catch (Exception e) {
            System.out.println("An Exception Occured :" + e.getMessage());
            System.exit(0);
        }
        return input;
    }
}
