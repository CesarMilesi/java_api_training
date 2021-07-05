package fr.lernejo.navy_battle;

public class Launcher
{
    public static void main(String[] args)
    {
        if (args.length == 1) {
            ServerMaster s1 = new ServerMaster(Integer.parseInt(args[0]));
        }
        else if (args.length == 2) {
            ServerMaster s2 = new ServerMaster(Integer.parseInt(args[0]));
            s2.startGame(Integer.parseInt(args[0]), args[1]);
        }
    }
}
