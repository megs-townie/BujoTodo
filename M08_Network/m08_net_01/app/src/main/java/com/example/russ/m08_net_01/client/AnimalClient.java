package com.example.russ.m08_net_01.client;

import com.example.russ.m08_net_01.BouncingBallView;
import com.example.russ.m08_net_01.common.NetReader;
import com.example.russ.m08_net_01.common.NetWriter;
import com.example.russ.m08_net_01.common.NetworkAnimal;
import com.example.russ.m08_net_01.common.NetworkCommand;

//import android.graphics.Color;  // use int for now
import android.graphics.Canvas;
import android.graphics.Color;

//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("serial")
public class AnimalClient implements Observer {

    private ArrayList<MovingDot_Android> localBalls = new ArrayList<MovingDot_Android>();

    private String server;

    private InetAddress address;
    private Socket sock;
    private static final int TIMEOUT = 0; // 0 = no time out

    String myIP;

    NetWriter sender;

    // Box (background) to paint on
    //static protected Box_Android box_android;

    // A timer to refresh the screen
//    static Timer timer;

    public AnimalClient(String server) {

        //box_android = new Box_Android(Color.YELLOW);  // ARGB
        //P6_Main_Start.localClient.setBox(box);  // Client needs bounds, all balls created on network


        // Create Box to paint on

        // Connect to Server
        this.server = server;

        try {
            // Need INTERNET permission
            //java.lang.SecurityException: Permission denied (missing INTERNET permission?)
            address = (InetAddress.getByName(this.server));
            sock = new Socket(address, NetworkAnimal.serverport);
            sock.setSoTimeout(TIMEOUT);
            myIP = sock.getLocalAddress().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // SendHandler sender = new SendHandler(sock);
        // sender.start();
        sender = new NetWriter(sock);
        sender.start();

        // Set up events to handle new animals from Network
        NetReader getter = new NetReader(sock);
        (new Thread(getter)).start();

        // We get incoming network messages
        getter.addObserver(this);

    }

//    public void setBox(Box_Android box) {
//        box_android = box;
//    }
//
//    public Box_Android getBox() {
//        return box_android;
//    }

    public synchronized void onDrawAll(Canvas canvas) {
        // Draw the components
        if (BouncingBallView.box != null) {
            BouncingBallView.box.draw(canvas);
            for (MovingDot_Android b : localBalls) {
                b.draw(canvas);  //draw each ball in the list
            }
        }
    }


    @Override
    public void update(Observable arg0, Object event) {

        System.out.println("Trigger Class: " + arg0.getClass());
        System.out.println(" event= " + event.toString());

        if (event instanceof NetworkAnimal) {
            NetworkAnimal e = (NetworkAnimal) event;
            // / Add MovingDot per locations given over net (per event)
            MovingDot_Android newDot = new MovingDot_Android(e.pos_x, e.pos_y, e.vel_x, e.vel_y, e.colour,
                    e.s);
            newDot.start();

            this.Synched_Add(newDot);
//            localBalls.add(newDot); // add to arraylist
        }

        if (event instanceof NetworkCommand) {
            NetworkCommand e = (NetworkCommand) event;

            switch (e.getComm()) {
                case Add_Animal:
                    break;
                case Change_Movement:
                    // flip x-y for all animals
                    for (MovingDot c : localBalls) {
                        c.flipXY();
                    }
                    break;
                case No_Command:
                    break;
                case Remove_Animal:
                    break;
                case Reset:
                    //this.removeKeyListener(localBalls.get(0)); // remove key listener
                    // clear all animals
                    for (MovingDot c : localBalls) {
                        c.killDot(); // force threads to stop
                    }

                    // Call synched reset instead of clearing directly
                    Synched_Reset();
//                    localBalls.clear(); // clear the arraylist
                    break;
                default:
                    break;
            }
        }
    }

    // Synch methods that create multi-threading issues
    private synchronized void Synched_Reset() {
        localBalls.clear(); // clear the arraylist
    }

    private synchronized void Synched_Add(MovingDot_Android newDot) {
        localBalls.add(newDot); // add to arraylist
    }


    private int rnd(int max) {
        int r = (int) (Math.random() * max);
        return r;
    }

    public int ballCount() {
        return localBalls.size();
    }

    public void resetAll() {
        sender.sendThisMsgOnQueue(new NetworkCommand(
                NetworkCommand.netCommands.Reset));
    }

    public void newBird(float x, float y, float dx, float dy) {
        sender.sendThisMsgOnQueue(new NetworkAnimal(
                NetworkAnimal.Animal.Bird, (int) x, (int) y, (int) dx, (int) dy,
                (int) Color.BLUE, NetworkAnimal.Animal.Bird.toString()));
    }

}
