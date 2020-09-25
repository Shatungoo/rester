package com.helldaisy;

import java.io.Serializable;
import java.util.ArrayList;

public class Collection implements Serializable{
	private static final long serialVersionUID = -6697876031957045257L;
    public ArrayList<Request> requests;

    public String name;

    public Collection(String name){
        this.name = name;
    }
}