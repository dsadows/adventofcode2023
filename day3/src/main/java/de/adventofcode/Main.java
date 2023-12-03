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
    }

    private static ArrayList<String> getAdjacentNumbers(char[][] array, boolean[][] mapOfNumericValues, boolean[][] mapOfSpecialSymbols) {
        ArrayList<String> adjacentNumbers = new ArrayList<>();

        int rowCount = array.length;
        int columnCount = array[0].length;

        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++) {
                System.out.println("Cell : " + array[i][j] + " i: " + i + " j: " + j + " mapOfNumericValues[i][j]: " + mapOfNumericValues[i][j] + " mapOfSpecialSymbols[i][j]: " + mapOfSpecialSymbols[i][j]);
                if(mapOfNumericValues[i][j] && cellIsAdjacentToSpecialSymbol(i, j, mapOfSpecialSymbols)){
                    StringBuilder stringBuilder = appendNeighboringCellsWithNumericValues(i, j, array, mapOfNumericValues);
                    System.out.println("Hat geklappt! " + stringBuilder);
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
            if (y >= 0 && y < cols && mapOfNumericValues[i][y]) {
                adjacentNumbersOfCurrentPosition.append(array[i][y]);
            } else {
                break;
            }
        }

        for (int y = j - 1; y >= 0; y--) {
            if (y >= 0 && y < cols && mapOfNumericValues[i][y]) {
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