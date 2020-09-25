package com.helldaisy;

import java.io.Serializable;
import java.util.HashMap;

public class Response implements Serializable{
 
    private static final long serialVersionUID = -7224232704681355741L;
    public final HashMap<String, String> headers = new HashMap<>();
    public int status;
    public long time;
    public String body;
}