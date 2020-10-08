package com.helldaisy.model;

import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request implements Serializable{
    private static final long serialVersionUID = -4342680460664435265L;
    public final HashMap<String, String> headers = new HashMap<>();
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
        headers.forEach((key, value) -> {
            if (!key.isEmpty()) requestBuilder.header(key, value);
        });
        long startTime = System.currentTimeMillis();
        return client.sendAsync(requestBuilder.build(), BodyHandlers.ofString())
            .thenApply(response -> {

                var responsePOJO = new Response();
                responsePOJO.time = System.currentTimeMillis()-startTime;
                response.headers().map().forEach((key, values) -> responsePOJO.headers.put(key, values.toString()));
                responsePOJO.body = response.body();
                responsePOJO.status = response.statusCode();
                return responsePOJO;
        }).join();
    }

    public static Request importFromCurl(String curl){
        var req= new Request();
        final String urlPattern = "'([a-z]+:\\/\\/(...)+?)'";

        final Matcher urlMatcher = Pattern.compile(urlPattern, Pattern.MULTILINE).matcher(curl);
        
        while (urlMatcher.find()) {
            req.URL = urlMatcher.group(1).trim();
        }
        System.out.println(req.URL);
        curl = curl.replaceAll(urlPattern, "");
        System.out.println(curl);
        String reqular = "[-]+([a-zA-Z0-9_-]+) (.*?)(?=[-]+[a-zA-Z0-9_-]+ |$)";
        Matcher matcher = Pattern.compile(reqular).matcher(curl);
        
        while(matcher.find()){
            if (matcher.group(1).equals("H")){
                var header = matcher.group(2).trim();
                if (header.startsWith("'")){
                    header = header.substring(1);
                }
                if (header.endsWith("'")){
                    header = header.substring(0, header.length()-1);
                }
                req.headers.put(header.split(":")[0],header.split(":")[1]);
            }
            if (matcher.group(1).equals("X")){
                req.method = matcher.group(2).trim();
            }            
            if (matcher.group(1).equals("data-raw")){
                var body = matcher.group(2).trim();
                if (body.startsWith("'")){
                    body = body.substring(1);
                }
                if (body.endsWith("'")){
                    body = body.substring(0, body.length()-1);
                }
                req.body = body;   
            }
        }
        return req;
    }

}