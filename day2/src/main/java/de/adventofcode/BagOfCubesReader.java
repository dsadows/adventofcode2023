package de.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BagOfCubesReader {

    public static Map<Integer, Map<Integer, Map<String, Integer>>> countColorsWithOrderedGames(String filePath) {

        Map<Integer, Map<String, Integer>> drawColorCount = new LinkedHashMap<>();
        Map<Integer, Map<Integer, Map<String, Integer>>> gameColorCount = new LinkedHashMap<>();


        try {
            Path path = Paths.get(filePath);
            String content = Files.readString(path);

            String[] games = content.split("\n");
            int gameNumber = 1;

            for (String game : games) {
                String[] rounds = game.split(":");

                int roundNumber = 1;

                for (String round : rounds) {
                    Map<String, Integer> colorCount = new HashMap<>();
                    String[] draws = round.trim().split(";");

                    int drawNumber = 1;

                    for (String draw : draws) {
                        String[] individualDraws = draw.trim().split(", ");

                        for (String individualDraw : individualDraws) {
                            String[] parts = individualDraw.trim().split(" ");

                            if (!parts[0].equals("Game")) {
                                String color = parts[1];
                                int count = Integer.parseInt(parts[0]);

                                if (!drawColorCount.containsKey(color)) {
                                    drawColorCount.put(color, new HashMap<>());
                                }
                                if (!gameColorCount.containsKey(drawNumber)) {
                                    gameColorCount.put(roundNumber, drawColorCount);
                                }

                                colorCount.put(color, colorCount.getOrDefault(color, 0) + count);
                                drawColorCount.put(roundNumber, colorCount);
                                gameColorCount.put(gameNumber, drawColorCount);
                            }

                            Map<Integer, Integer> gamesCount = colorGamesCount.get(color);
                            gamesCount.put(gameNumber, gamesCount.getOrDefault(gameNumber, 0) + count);
                        }
                        drawNumber++;
                    }
                    roundNumber++;
                }
                gameNumber++;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return gameColorCount;
    }
}
