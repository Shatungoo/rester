package com.helldaisy;

import javafx.beans.property.SimpleStringProperty;

public class Headers {

    private SimpleStringProperty key;

    private SimpleStringProperty value;

    // added to create the model from the Person object, which might be data
    // retrieved from a database

    // public PersonTableData(Person person) {

    //     this.setKey(new SimpleStringProperty(person.getFirstName()));

    //     this.value = new SimpleStringProperty(person.getSurname());


    // }

    public String getKey() {
        return key.get();
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public String getValue() {
        return value.get();
    }

    public void setKValue(String value) {
        this.value.set(value);
    }
}