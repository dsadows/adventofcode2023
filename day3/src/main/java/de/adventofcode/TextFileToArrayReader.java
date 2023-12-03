package de.adventofcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileToArrayReader {

    public static char[][] textFileToTwoDimensionalArray(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        int columnCount = 0;

        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
            if (line.length() > columnCount) {
                columnCount = line.length();
            }
        }

        int rowCount = lines.size();

        scanner.close();

        char[][] array = new char[rowCount][columnCount];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                array[i][j] = line.charAt(j);
            }
        }

        return array;
    }
}
