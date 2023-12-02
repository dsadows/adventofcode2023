package de.adventofcode;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.print("""
                --- Day 1: Trebuchet?! ---

                Something is wrong with global snow production, and you've been selected to take a look. The Elves have even given you a map; on it, they've used stars to mark the top fifty locations that are likely to be having problems.

                You've been doing this long enough to know that to restore snow operations, you need to check all fifty stars by December 25th.

                Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

                You try to ask why they can't just use a weather machine ("not powerful enough") and where they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a lot of questions") and hang on did you just say the sky ("of course, where do you think snow comes from") when you realize that the Elves are already loading you into a trebuchet ("please hold still, we need to strap you in").

                As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been amended by a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are having trouble reading the values on the document.

                The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.

                For example:

                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
                In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.

                Consider your entire calibration document. What is the sum of all of the calibration values?
                                
                """);

        ArrayList<String> listOfStrings = FileToArrayProvider.writeFileLinesToStringArray("day1/src/main/resources/input.txt");
        List<Integer> listOfNumbers = new ArrayList<>();

        for (String line : listOfStrings) {

            String reversedLine = new StringBuilder(line).reverse().toString();
            char firstDigit = ' ';
            char lastDigit = ' ';

            while (!line.isEmpty()) {
                char firstCharacter = line.charAt(0);
                if (Character.isDigit(firstCharacter)) {
                    firstDigit = firstCharacter;
                    System.out.println("The first digit is: " + firstDigit);
                    break;
                }
                line = line.substring(1);
            }

            while (!reversedLine.isEmpty()) {
                char firstCharacter = reversedLine.charAt(0);
                if (Character.isDigit(firstCharacter)) {
                    lastDigit = firstCharacter;
                    System.out.println("The last digit is: " + lastDigit);
                    break;
                }
                reversedLine = reversedLine.substring(1);
            }

            if (Character.isDigit(firstDigit) && Character.isDigit(lastDigit)) {
                listOfNumbers.add(Integer.parseInt(String.valueOf(firstDigit) + String.valueOf(lastDigit)));
                System.out.println("The number is: " + String.valueOf(firstDigit) + String.valueOf(lastDigit));
            }
        }

        int sum = 0;

        for (int number : listOfNumbers) {
            sum += number;
            System.out.println("New value of the sum is: " + sum);
        }

        System.out.println("The sum of all of the calibration values is: " + sum);

        System.out.print("""
                
                --- Part Two ---
                                
                Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
                                
                Equipped with this new information, you now need to find the real first and last digit on each line. For example:
                                
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
                In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these together produces 281.
                                
                What is the sum of all of the calibration values?
                                
                """);

        listOfStrings = FileToArrayProvider.writeFileLinesToStringArray("day1/src/main/resources/input.txt");
        listOfNumbers = new ArrayList<>();
        ArrayList<String> listOfStringNumbers = new ArrayList<>();

        listOfStringNumbers.add("zero");
        listOfStringNumbers.add("one");
        listOfStringNumbers.add("two");
        listOfStringNumbers.add("three");
        listOfStringNumbers.add("four");
        listOfStringNumbers.add("five");
        listOfStringNumbers.add("six");
        listOfStringNumbers.add("seven");
        listOfStringNumbers.add("eight");
        listOfStringNumbers.add("nine");

        for (String line : listOfStrings) {

            StringBuilder removedFirstPart = new StringBuilder();
            StringBuilder removedLastPart = new StringBuilder();
            String reversedLine = new StringBuilder(line).reverse().toString();
            Integer firstDigit = null;
            Integer lastDigit = null;

            while (!line.isEmpty()) {
                char firstCharacter = line.charAt(0);
                if (getNumberFromWroteInFull(removedFirstPart.toString()) != null) {
                    firstDigit = getNumberFromWroteInFull(removedFirstPart.toString());
                    System.out.println("The first digit is: " + firstDigit);
                    removedFirstPart = new StringBuilder();
                    break;
                }
                if (Character.isDigit(firstCharacter)) {
                    firstDigit = Integer.parseInt(String.valueOf(firstCharacter));
                    System.out.println("The first digit is: " + firstDigit);
                    removedFirstPart = new StringBuilder();
                    break;
                }
                removedFirstPart.append(firstCharacter);
                line = line.substring(1);
            }

            while (!reversedLine.isEmpty()) {
                char firstCharacter = reversedLine.charAt(0);
                if (getNumberFromWroteInFullBackwards(removedLastPart.toString(), removedLastPart.length()) != null) {
                    lastDigit = getNumberFromWroteInFullBackwards(removedLastPart.toString(), removedLastPart.length());
                    System.out.println("The last digit is: " + lastDigit);
                    removedLastPart = new StringBuilder();
                    break;
                }
                if (Character.isDigit(firstCharacter)) {
                    lastDigit = Integer.parseInt(String.valueOf(firstCharacter));
                    System.out.println("The last digit is: " + lastDigit);
                    removedLastPart = new StringBuilder();
                    break;
                }
                removedLastPart.insert(0, firstCharacter);
                reversedLine = reversedLine.substring(1);
            }

            if (firstDigit != null && lastDigit != null) {
                listOfNumbers.add(Integer.parseInt(String.valueOf(firstDigit) + String.valueOf(lastDigit)));
                System.out.println("The number is: " + String.valueOf(firstDigit) + String.valueOf(lastDigit));
            }
        }

        int newSum = 0;

        for (int number : listOfNumbers) {
            newSum += number;
        }

        System.out.println("The new sum of all of the calibration values is: " + newSum);

    }

    private static Integer getNumberFromWroteInFull(String wroteInFullNumber){
        if(!wroteInFullNumber.isEmpty()){
            switch (wroteInFullNumber) {
                case "zero" -> {
                    return 0;
                }
                case "one" -> {
                    return 1;
                }
                case "two" -> {
                    return 2;
                }
                case "three" -> {
                    return 3;
                }
                case "four" -> {
                    return 4;
                }
                case "five" -> {
                    return 5;
                }
                case "six" -> {
                    return 6;
                }
                case "seven" -> {
                    return 7;
                }
                case "eight" -> {
                    return 8;
                }
                case "nine" -> {
                    return 9;
                }
                default -> {
                    return getNumberFromWroteInFull(wroteInFullNumber.substring(1));
                }
            }
        }
        return null;
    }

    private static Integer getNumberFromWroteInFullBackwards(String wroteInFullNumber, int length){
        if(!wroteInFullNumber.isEmpty()){
            switch (wroteInFullNumber) {
                case "zero" -> {
                    return 0;
                }
                case "one" -> {
                    return 1;
                }
                case "two" -> {
                    return 2;
                }
                case "three" -> {
                    return 3;
                }
                case "four" -> {
                    return 4;
                }
                case "five" -> {
                    return 5;
                }
                case "six" -> {
                    return 6;
                }
                case "seven" -> {
                    return 7;
                }
                case "eight" -> {
                    return 8;
                }
                case "nine" -> {
                    return 9;
                }
                default -> {
                    return getNumberFromWroteInFullBackwards(wroteInFullNumber.substring(0, length - 1), length - 1);
                }
            }
        }
        return null;
    }
}