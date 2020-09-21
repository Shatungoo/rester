package com.helldaisy;

import java.io.Serializable;

public class History implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -532317016218163920L;
    public Request request;
    public Response response;

    public History(Request req){
        this.request = req;
    }

    
    public History(Response resp){
        this.response= resp;
    }

    
    public History(Request req, Response resp){
        this.request = req;
        this.response= resp;
    }
}