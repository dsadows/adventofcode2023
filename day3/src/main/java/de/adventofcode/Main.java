package de.adventofcode;

import java.io.FileNotFoundException;
import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.print("""
                --- Day 3: Gear Ratios ---

                You and the Elf eventually reach a gondola lift station; he says the gondola lift will take you up to the water source, but this is as far as he can bring you. You go inside.
                
                It doesn't take long to find the gondolas, but there seems to be a problem: they're not moving.
                
                "Aaah!"
                
                You turn around to see a slightly-greasy Elf with a wrench and a look of surprise. "Sorry, I wasn't expecting anyone! The gondola lift isn't working right now; it'll still be a while before I can fix it." You offer to help.
                
                The engineer explains that an engine part seems to be missing from the engine, but nobody can figure out which one. If you can add up all the part numbers in the engine schematic, it should be easy to work out which part is missing.
                
                The engine schematic (your puzzle input) consists of a visual representation of the engine. There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
                
                Here is an example engine schematic:
                
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.
                
                Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers in the engine schematic?
                                
                """);

        char[][] array = TextFileToArrayReader.textFileToTwoDimensionalArray("day3/src/main/resources/input.txt");
        boolean [][] mapOfNumericValues = getPositionsOfNumericValue(array);
        boolean [][] mapOfSpecialSymbols = getPositionsOfSpecialSymbols(array);

        ArrayList<String> adjacentNumbers = getAdjacentNumbers(array, mapOfNumericValues, mapOfSpecialSymbols);

        int sum = 0;

        for(String adjacentNumber : adjacentNumbers){
            sum += Integer.parseInt(adjacentNumber);
        }

        System.out.println("The sum of all adjacent numbers is: " + sum);

        System.out.print("""
                
                --- Part Two ---

                The engineer finds the missing part and installs it in the engine! As the engine springs to life, you jump in the closest gondola, finally ready to ascend to the water source.
                
                You don't seem to be going very fast, though. Maybe something is still wrong? Fortunately, the gondola has a phone labeled "help", so you pick it up and the engineer answers.
                
                Before you can explain the situation, she suggests that you look out the window. There stands the engineer, holding a phone in one hand and waving with the other. You're going so slowly that you haven't even left the station. You exit the gondola.
                
                The missing part wasn't the only issue - one of the gears in the engine is wrong. A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying those two numbers together.
                
                This time, you need to find the gear ratio of every gear and add them all up so that the engineer can figure out which gear needs to be replaced.
                
                Consider the same engine schematic again:
                
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                In this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.
                
                What is the sum of all of the gear ratios in your engine schematic?
                                
                """);

        int gearRatio = getGearRatios(array, mapOfNumericValues, mapOfSpecialSymbols);

        System.out.println("The sum of all of the gear ratios in the engine schematic is: " + gearRatio);
    }

    private static int getGearRatios(char[][] array, boolean[][] mapOfNumericValues, boolean[][] mapOfSpecialSymbols) {
        int rowCount = array.length;
        int columnCount = array[0].length;
        int gearRatioSum = 0;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (array[i][j] == '*' && cellHasTwoAdjacentNumbers(i, j, mapOfNumericValues, mapOfSpecialSymbols)) {
                    System.out.println("Gear ratio found at position: " + i + ", " + j);
                    System.out.println("Gear ratio: " + calculateAdjacentNumbersProduct(i, j, array, mapOfNumericValues));
                    int product = Integer.parseInt(String.valueOf(calculateAdjacentNumbersProduct(i, j, array, mapOfNumericValues)));
                    gearRatioSum += product;
                }
            }
        }
        return gearRatioSum;
    }

    private static boolean cellHasTwoAdjacentNumbers(int row, int column, boolean[][] mapOfNumericValues, boolean[][] mapOfSpecialSymbols) {
        int adjacentNumbersCount = 0;
        int rowCount = mapOfNumericValues.length;
        int columnCount = mapOfNumericValues[0].length;

        int lastColumnDiscovery = 0;

        for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, rowCount - 1); i++) {
            for (int j = Math.max(0, column - 1); j <= Math.min(column + 1, columnCount - 1); j++) {

                if(lastColumnDiscovery == j - 1){
                    if (mapOfNumericValues[i][j] && !mapOfSpecialSymbols[i][j]) {
                        lastColumnDiscovery = j;
                    }
                    continue;
                }

                if (i != row || j != column) { // Exclude the cell itself
                    if (mapOfNumericValues[i][j] && !mapOfSpecialSymbols[i][j]) {
                        adjacentNumbersCount++;
                        lastColumnDiscovery = j;
                    }
                }
            }
        }
        return adjacentNumbersCount >= 2;
    }

    private static int calculateAdjacentNumbersProduct(int row, int column, char[][] array, boolean[][] mapOfNumericValues) {
        int product = 1;
        int rowCount = array.length;
        int columnCount = array[0].length;

        int lastColumnDiscovery = 0;

        for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, rowCount - 1); i++) {
            for (int j = Math.max(0, column - 1); j <= Math.min(column + 1, columnCount - 1); j++) {

                if(lastColumnDiscovery == j - 1){
                    if (mapOfNumericValues[i][j]) {
                        lastColumnDiscovery = j;
                    }
                    continue;
                }

                if (mapOfNumericValues[i][j] && (i != row || j != column)) {
                    product *= Integer.parseInt(appendNeighboringCellsWithNumericValues(i, j, array, mapOfNumericValues).toString());
                    lastColumnDiscovery = j;
                }
            }
        }
        return product;
    }

    private static ArrayList<String> getAdjacentNumbers(char[][] array, boolean[][] mapOfNumericValues, boolean[][] mapOfSpecialSymbols) {
        ArrayList<String> adjacentNumbers = new ArrayList<>();

        int rowCount = array.length;
        int columnCount = array[0].length;

        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++) {
                if(mapOfNumericValues[i][j] && cellIsAdjacentToSpecialSymbol(i, j, mapOfSpecialSymbols)){
                    StringBuilder stringBuilder = appendNeighboringCellsWithNumericValues(i, j, array, mapOfNumericValues);
                    j = getColumnOfRightSidedNumericNeighbor(i, j, mapOfNumericValues);
                    adjacentNumbers.add(String.valueOf(stringBuilder));
                }
            }
        }
        return adjacentNumbers;
    }

    private static int getColumnOfRightSidedNumericNeighbor(int i, int j, boolean[][] mapOfNumericValues) {
        int rows = mapOfNumericValues.length;
        int cols = mapOfNumericValues[0].length;

        int columnOfRightSidedNumericNeighbor = j;

        for(int y = j; y < cols; y++){
            if(mapOfNumericValues[i][y]){
                columnOfRightSidedNumericNeighbor = y;
            } else {
                break;
            }
        }
        return columnOfRightSidedNumericNeighbor;
    }

    private static StringBuilder appendNeighboringCellsWithNumericValues(int i, int j, char[][] array ,boolean[][] mapOfNumericValues) {
        StringBuilder adjacentNumbersOfCurrentPosition = new StringBuilder();

        int cols = array[0].length;

        for (int y = j; y < cols; y++) {
            if (y >= 0 && mapOfNumericValues[i][y]) {
                adjacentNumbersOfCurrentPosition.append(array[i][y]);
            } else {
                break;
            }
        }

        for (int y = j - 1; y >= 0; y--) {
            if (y < cols && mapOfNumericValues[i][y]) {
                adjacentNumbersOfCurrentPosition.insert(0, array[i][y]);
            } else {
                break;
            }
        }
        return adjacentNumbersOfCurrentPosition;
    }

    private static boolean cellIsAdjacentToSpecialSymbol(int row, int column, boolean[][] mapOfSpecialSymbols){

        int rows = mapOfSpecialSymbols.length;
        int columns = mapOfSpecialSymbols[0].length;

        for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, rows - 1); i++) {
            for (int j = Math.max(0, column - 1); j <= Math.min(column + 1, columns - 1); j++) {
                if (i != row || j != column) { // exclude the cell itself
                    if (mapOfSpecialSymbols[i][j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean[][] getPositionsOfSpecialSymbols(char[][] array) {
        boolean[][] mapOfSpecialSymbols = new boolean[array.length][array[0].length];
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++) {
                if(String.valueOf(array[i][j]).matches("[^0-9\\.]")){
                    mapOfSpecialSymbols[i][j] = true;
                }
            }
        }
        return mapOfSpecialSymbols;
    }

    private static boolean[][] getPositionsOfNumericValue(char[][] array){
        boolean[][] mapOfNumericValues = new boolean[array.length][array[0].length];
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++) {
                if(Character.isDigit(array[i][j])){
                    mapOfNumericValues[i][j] = true;
                }
            }
        }
        return mapOfNumericValues;
    }
}