package com.blaze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadInputs {

    public static void inputs() throws IOException {
        data input = data.createData();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("known_geoff.txt"));
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
}
