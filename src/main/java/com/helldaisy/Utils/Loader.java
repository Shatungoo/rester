package com.helldaisy.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;
import com.helldaisy.model.Exchange;

public class Loader {

    private static String PATH = "d:\\address.json";

    public static List<Exchange> load() {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(PATH));
            Exchange[] exs = gson.fromJson(reader, Exchange[].class);
            return Arrays.asList(exs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(ArrayList<Exchange> history) {
        Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
        try (var writer = new FileWriter(PATH)) {
            gson.toJson(history, writer);
        } catch (JsonIOException | IOException e) {
            e.printStackTrace();
        }
    }
}
