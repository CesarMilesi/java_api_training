package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetHandler implements HttpHandler {

    private final PlayersGame playersGame;

    public GetHandler(PlayersGame playersGame) {
        this.playersGame = playersGame;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            new ErrorsHandler().NotFound(exchange);
        } else {
            String cell = exchange.getRequestURI().getQuery().split("=")[1];
            if (!checkCell(cell)) {
                new ErrorsHandler().BadRequest(exchange);
            }
            getParameters(exchange, cell);
        }
    }

    // 1 - Parser la cell pour

    public boolean checkCell(String cell)
    {
        Pattern pattern = Pattern.compile("[A-J][0-1][0-1]|[A-J][1-9]");
        Matcher regex = pattern.matcher(cell);
        return (regex.find()) ? true : false;
    }

    public void hitResponse(HttpExchange exchange, String cell) throws IOException {
        String body = new ObjectMapper().writeValueAsString(new PlayersGame(Consequence.sunk, false));
        exchange.getResponseHeaders().set("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
        exchange.getResponseHeaders().set("Accept", "application/json");
        exchange.sendResponseHeaders(202, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }
}
