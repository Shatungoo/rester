package com.helldaisy;

import java.io.Serializable;

public class Exchange implements Serializable {

    private static final long serialVersionUID = -532317016218163920L;
    public Request request;
    public Response response;

    public Exchange(Request req){
        this.request = req;
    }
    
    public Exchange(Request req, Response resp){
        this.request = req;
        this.response= resp;
    }
}