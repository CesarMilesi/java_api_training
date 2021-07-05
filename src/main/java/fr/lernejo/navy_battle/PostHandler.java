package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PostHandler implements HttpHandler {

    private final int port;
    private final ServerMaster serverMaster;

    public PostHandler(int port, ServerMaster serverMaster)
    {
        this.port = port;
        this.serverMaster = serverMaster;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            new ErrorsHandler().NotFound(exchange);
        } else {
            InputStream stream = exchange.getRequestBody();
            String body = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            try {
                JsonNode corpsRequest = new ObjectMapper().readTree(body);
                this.serverMaster.setOpponentId(new CorpsRequest(corpsRequest.get("id").asText(), corpsRequest.get("url").asText(), corpsRequest.get("message").asText()));
                CorpsRequest send = new CorpsRequest("0c575465-21f6-43c9-8a2d-bc64c3ae6241", "http://localhost" + port, "I will crush you!");
                sendResponse(send, exchange);
            } catch (IOException e) {
                    new ErrorsHandler().BadRequest(exchange);
                }
        }
    }

    public void sendResponse(CorpsRequest request, HttpExchange exchange) throws IOException {
        String body = new ObjectMapper().writeValueAsString(request);
        exchange.getResponseHeaders().set("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
        exchange.getResponseHeaders().set("Accept", "application/json");
        exchange.sendResponseHeaders(202, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }
}
