package fr.lernejo.navy_battle;

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
    }

    public boolean hitBoat(int x, int y) {
        return this.sea[x][y] != 0;
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

    public void sentHit(PlayersGame playersGame, int x, int y) {
        playersGame.opponentSea[x][y] = -1;
    }
}
