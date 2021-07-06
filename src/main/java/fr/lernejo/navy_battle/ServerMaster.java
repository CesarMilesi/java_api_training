package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

public class ServerMaster {
    private final PlayersGame playersGame = new PlayersGame();
    private final ArrayList<String> opponentId = new ArrayList<String>(1);

    public ServerMaster(int port) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", new HandlerRequest());
        server.createContext("/api/game/start", new PostHandler(port, this));
        server.createContext("/api/game/fire", new GetHandler(this.playersGame, this));
        server.start();
    }

    public void startGame(int port, String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestPost = HttpRequest.newBuilder().uri(URI.create(url + "/api/game/start")).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + port + "\", \"message\":\"hello\"}")).build();
        try {
            HttpResponse response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                this.opponentId.add(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fireClient() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestPost = HttpRequest.newBuilder().uri(URI.create(this.opponentId.get(0).replace("\"", "") + "/api/game/fire?cell=" + playersGame.selectCell())).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").GET().build();
        try {
            HttpResponse response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                String responseBody = (String)response.body();
                JsonNode corpsRequest = new ObjectMapper().readTree(responseBody);
                if (corpsRequest.get("shipLeft").asBoolean() == false) {
                    exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setOpponentId(String url) {
        this.opponentId.add(url);
    }
}
