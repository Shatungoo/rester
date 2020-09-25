package com.helldaisy;

public class CollectionFX  {

    public Collection collection;
    
    public Request request;

    public CollectionFX(Collection collection){
        this.collection =  collection;
    }

    
    public CollectionFX(Request request){
        this.request =  request;
    }

    public String toString(){
        if (collection != null) return collection.name;
        return request.URL;
    }
}