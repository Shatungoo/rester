package com.helldaisy;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;

public class Request {
    final HashMap<String, String> headers = new HashMap<>();
    public String body;
    public String URL;
    public String method;

    public Response send() {
        final HttpClient client = HttpClient.newHttpClient();
        var uri= (!URL.toLowerCase().matches("^\\w+://.*"))
            ? "http://" + URL
            :URL;
        final var requestBuilder = HttpRequest.newBuilder()
            .method(method, BodyPublishers.ofString(body))
            .uri(URI.create(uri));
        headers.forEach((key, value) -> requestBuilder.header(key, value));
        return client.sendAsync(requestBuilder.build(), BodyHandlers.ofString())
            .thenApply(response -> {
                var responsePOJO = new Response();
                response.headers().map().forEach((key, values) -> responsePOJO.headers.put(key, values.toString()));
                responsePOJO.body = response.body();
                return responsePOJO;
        }).join();
    }

}