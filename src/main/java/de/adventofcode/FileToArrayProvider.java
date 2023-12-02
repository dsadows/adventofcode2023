package de.adventofcode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileToArrayProvider {

    public static ArrayList<String> writeFileLinesToStringArray(String path) throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> stringArray = new ArrayList<>();
        String line = null;

        while((line = bufferedReader.readLine()) != null) {
            stringArray.add(line);
        }

        bufferedReader.close();
        return stringArray;
    }
}
