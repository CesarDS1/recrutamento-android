package com.test.cesar.palomitasmovile.models;

/**
 * Model of the episode
 * Created by Cesar on 14/09/2016.
 */
public class Episode {

    private int number;
    private String name;

    public Episode(int number, String name)
    {
        this.number=number;
        this.name=name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
