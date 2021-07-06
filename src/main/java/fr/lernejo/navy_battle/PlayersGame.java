package fr.lernejo.navy_battle;

import java.util.Random;

public class PlayersGame {
    private final int[][] sea = new int[10][10];
    private final int[][] opponentSea = new int[10][10];

    public PlayersGame()
    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                sea[i][j] = 0;
                opponentSea[i][j] = 0;
            }
        }
        sea[2][3] = 1;
    }

    private boolean isGameOver(int id) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (sea[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isDestroyed(int id) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (sea[i][j] == id) {
                    return false;
                }
            }
        }
        return true;
    }

    public String selectCell()
    {
        int column;
        int line;
        Random random = new Random();
        column = random.nextInt(10);
        line = random.nextInt(10);
        while (opponentSea[line][column] != 0) {
            column = random.nextInt(10);
            line = random.nextInt(10);
        }
        opponentSea[line][column] = -1;
        return convertColumn(column) + String.valueOf(line);
    }

    private String convertColumn(int column) {
        switch (column) {
            case 0: return "A";
            case 1: return "B";
            case 2: return "C";
            case 3: return "D";
            case 4: return "E";
            case 5: return "F";
            case 6: return "G";
            case 7: return "H";
            case 8: return "I";
            default: return "J";
        }
    }

    public FireRequest receivedHit(int x, int y) {
        if (this.sea[x][y] != 0) {
            int id = this.sea[x][y];
            this.sea[x][y] = 0;
            if (isDestroyed(id)) {
                if (isGameOver(id))
                    return new FireRequest("sunk", false);
                else
                    return new FireRequest("sunk", true);
            }
            else
                return new FireRequest("hit", true);
        }
        else return new FireRequest("miss", true);
    }
}
