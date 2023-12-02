package de.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BagOfCubesReader {

    public static Map<Integer, Map<String, Integer>> countColorsWithOrderedGames(String filePath) {
        Map<Integer, Map<String, Integer>> gameColorCount = new LinkedHashMap<>();
        int gameNumber = 1;

        try {
            Path path = Paths.get(filePath);
            String content = Files.readString(path);

            String[] games = content.split("\n");
            for (String game : games) {
                String[] rounds = game.split(":");

                for (String round : rounds) {
                    String[] draws = round.trim().split(";");

                    for (String draw : draws) {
                        String[] individualDraws = draw.trim().split(", ");

                        for(String individualDraw : individualDraws){
                            String[] parts = individualDraw.trim().split(" ");

                            if (!gameColorCount.containsKey(gameNumber)) {
                                gameColorCount.put(gameNumber, new HashMap<>());
                            }

                            if(!parts[0].equals("Game")) {
                                String color = parts[1];
                                int count = Integer.parseInt(parts[0]);
                                Map<String, Integer> colorCount = gameColorCount.get(gameNumber);
                                colorCount.put(color, colorCount.getOrDefault(color, 0) + count);
                            }
                        }
                    }
                }
                gameNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameColorCount;
    }
}