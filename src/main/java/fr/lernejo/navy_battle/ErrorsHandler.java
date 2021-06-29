package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ErrorsHandler
{
    // Sert à savoir ici quelle requête a été envoyée (GET, POST, ...)
    public void NotFound(HttpExchange exchange) throws IOException
    {
        String error = "Not Found";
        exchange.sendResponseHeaders(404, error.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(error.getBytes());
        }
    }

    public void BadRequest(HttpExchange exchange) throws IOException
    {
        String badreq = "Bad request";
        exchange.sendResponseHeaders(400, badreq.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(badreq.getBytes());
        }
    }
}
