package com.helldaisy.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.helldaisy.model.Exchange;

public class Loader {

    private static  String PATH= "d:\\address.ser";

    public static ArrayList<Exchange> load() {
        try (var ois = new ObjectInputStream(new FileInputStream(PATH))){
            return (ArrayList<Exchange>)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(ArrayList<Exchange> history){
        try {
            FileOutputStream fout = new FileOutputStream(PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(new ArrayList(history));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
