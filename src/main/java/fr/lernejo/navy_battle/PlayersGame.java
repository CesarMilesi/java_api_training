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

    // Si pas touché -> 0
    // Si touché -> 1
    // Coulé -> -1

    public boolean hitBoat(int x, int y) {
        return this.sea[x][y] != 0;
    }

    public void receivedHit(PlayersGame playersGame, int x, int y) {
        playersGame.sea[x][y] = -1;
    }

    public void sentHit(PlayersGame playersGame, int x, int y) {
        playersGame.opponentSea[x][y] = -1;
    }
}
