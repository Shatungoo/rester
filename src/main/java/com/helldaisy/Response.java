package com.helldaisy;

import java.util.HashMap;

public class Response {
    final HashMap<String, String> headers = new HashMap<>();
    public int status;
    public long time;
    public String body;
}