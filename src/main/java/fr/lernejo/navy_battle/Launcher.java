package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class Launcher
{
    public static void main(String[] args)
    {
        HttpServer server = null;
        try
        {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[0])), 0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", new HandlerRequest());
        if (args.length <= 1) {
            server.createContext("/api/game/start", new PostHandler(Integer.parseInt(args[0])));
        }
        else
        {
            new NewClient().CreateNewCLient(args[0], args[1]);
        }
            server.start();
    }
}
