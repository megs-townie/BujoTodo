/*
 * This class is used to contain the data sent over 
 * the network.  That is why it is serializable (can be
 * stored in a file).  
 * 
 * We could use any data structures, but we selected 
 * an int, string, and double as a sample.
 * 
 * The data can be as simple or as complex as you want, but
 * must be serializable.
 * 
 * This class is "visible" in both projects, client and server.
 * The class exists in the client project (Module_11_Web_Applet), 
 * and that project is set as an external library for the 
 * other project (Module_11_Web)....see properties->libraries.
 * 
 */
package com.example.russ.m08_net_01.common;

//import android.graphics.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;

/**
 * Network Animal is animal data meant to cross the network.
 */
public class NetworkAnimal implements Serializable {

    /**
     * Code for this class
     */
    private static final long serialVersionUID = 472468576408832414L;

    public static final Integer serverport = 53475; // Declared here, used both
    // server and client

    // When this object is pushed over the network, we need a way to which
    // animal it is.
    private int animal_number = 0;

    public enum Animal {
        Turtle, Bird, Moose, Bunny, Cat
    }

    public enum NetCommand {
        No_Command, Change_Movement, Add_Animal, Remove_Animal
    }

    // The command, one of enumerations above
    public NetCommand comm;
    public Animal a;
    public int pos_x;
    public int pos_y;
    public int vel_x;
    public int vel_y;
    public int colour; // Color as argb using bits of int
    public String s;// "HelloThere"

    // Alpha + RGB                   0b.Alpha..===R====...G....===B====
    public final static int blue = 0b00111000000000000000000001100000;
    public final static int red = 0b00111000011000000000000000000000;
    public final static int green = 0b00111000000000000110000000000000;
    public final static int purple = 0b00111000011000000000000001100000;

    // Use defaults
    public NetworkAnimal() {
    }

    // Construct from individual components
    public NetworkAnimal(Animal a, int pos_x, int pos_y, int vel_x, int vel_y,
                         int colour, String s) {
        this.a = a;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.vel_x = vel_x;
        this.vel_y = vel_y;
        this.colour = colour;
        this.s = s;
        this.setAnimal_number(0);
        this.comm = NetCommand.No_Command;
    }

    // Construct from individual components
    public NetworkAnimal(Animal a, int pos_x, int pos_y, int vel_x, int vel_y,
                         int colour, String s, NetCommand comm) {
        this.a = a;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.vel_x = vel_x;
        this.vel_y = vel_y;
        this.colour = colour;
        this.s = s;
        this.setAnimal_number(0);
        this.comm = comm;
    }

    // Construct from an object of type NetworkAnimal
    public NetworkAnimal(NetworkAnimal netAnimal) {
        this.a = netAnimal.a;
        this.pos_x = netAnimal.pos_x;
        this.pos_y = netAnimal.pos_y;
        this.vel_x = netAnimal.vel_x;
        this.vel_y = netAnimal.vel_y;
        this.colour = netAnimal.colour;
        this.s = netAnimal.s;
        this.animal_number = netAnimal.animal_number;
        this.comm = netAnimal.comm;
    }

    public void updateIndex(int index) {
        this.animal_number = index;
    }

    public void updateAllButIndex(NetworkAnimal netAnimal) {
        this.a = netAnimal.a;
        this.pos_x = netAnimal.pos_x;
        this.pos_y = netAnimal.pos_y;
        this.vel_x = netAnimal.vel_x;
        this.vel_y = netAnimal.vel_y;
        this.colour = netAnimal.colour;
        this.s = netAnimal.s;
    }

    // Read string from input stream, convert to animal enum
    public static NetworkAnimal readAnimal(BufferedReader qfs) {
        String stringValue; // = null;
        NetworkAnimal a = null;
        try {
            if ((stringValue = qfs.readLine()) != null) {
                // Actually read animal, but fudge position and location
                a = new NetworkAnimal(Animal.valueOf(stringValue), 10, 10, 1,
                        1, NetworkAnimal.blue, "Hello");
            }
        } catch (IOException e) {
            stringValue = "IOException occurred in server.";
        }
        return a;
    }

    @Override
    public String toString() {
        return "NetworkAnimal: " + "data is Animal=" + this.a.name() + " x="
                + this.pos_x + " y=" + this.pos_y + " Color=" + this.colour
                + " Name= " + this.s + " Number=" + this.animal_number
                + " Command=" + this.comm;
    }

    public int getAnimal_number() {
        return animal_number;
    }

    public void setAnimal_number(int animal_number) {
        this.animal_number = animal_number;
    }

    public void setComm(NetCommand comm) {
        this.comm = comm;
    }

    public NetCommand getComm() {
        return comm;
    }
}