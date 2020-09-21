package com.helldaisy;

public class History {
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