package com.example.russ.m08_net_01.common;

//import java.util.Timer;

//import android.graphics.Color;

import android.util.Log;

import com.example.russ.m08_net_01.client.AnimalClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Network Client-Server
 * <p/>
 * Steps:
 * 1) run ClientServerStart -> select blank -> makes server
 * 2) run ClientServerStart -> select IP -> makes client that connects to server
 * 3) ball bouncing in client came from server (ServerHandler, line 91)
 * 4) "R" Remove all dots
 * 5) "T" Add a new dot (Turtle)
 * 6) escape ends the program
 * 7) MovingDot line 185 -> Draw name above dot
 * 8) MovingDot line 185 -> Draw picture instead of dot
 * 9) AnimalClient line 185 -> Add "W" warp dots/pictures
 * 10) AnimalClient line 185+ -> Add "B" Bird
 * 11) AnimalClient line 185+ -> Add "U" Bunny
 * 12) AnimalClient line 185+ -> Add "C" Cat
 * 13) AnimalClient line 185+ -> Add "M" Moose
 * 14) Paint Program -> change pictures of Turtle, Bird, ...
 */
public class P6_Main_Start {

    public static AnimalClient localClient = null;

    public static void main(String Server_IP) {

        // Set up jFrame window
//		JFrame frame = new JFrame();

        // Set up if we are server or client
        InetAddress Address = null;
        try {
            Address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Log.w("P6_Main_Start", "Create new Animal Client");

        localClient = new AnimalClient(Server_IP);   // tell client where server IP is


    }
}