package com.helldaisy.model;

import java.io.Serializable;
import java.util.ArrayList;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;

public class Collection implements Serializable{
	private static final long serialVersionUID = -6697876031957045257L;
    public ArrayList<Request> requests = new ArrayList<>();

    public String name;

    public Collection(String name){
        this.name = name;
    }

    public static Collection importFromSwagger(String path){
        OpenAPI openAPI = new OpenAPIV3Parser().read(path);
        // openAPI.getPaths().values().forEach(p -> {
        //     if (p ==null || p.getGet() == null) return;
        //     System.out.println(p);
        //     // System.out.println(p.getParameters().get(0).getSchema());
        // }); 
        openAPI.getPaths().forEach((k,v) -> {
            var get = v.getGet();
            if (get!=null){
                var req= new Request();
                req.URL = k;
                req.method = "GET";
                // req.
            }
        });
        var collection = new Collection("");
        // collection.requests.add(req);
        return collection;
    }
}