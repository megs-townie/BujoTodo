package com.example.russ.m08_net_01.client;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
//import android.support.annotation.ColorInt;

import java.util.Random;

/**
 * Created by Ba on 2/21/2016.
 */
public class MovingDot_Android extends MovingDot {


    private RectF bounds = new RectF();   // Needed for Canvas.drawOval
    private Paint paint = new Paint();    // The paint style, color used for drawing

    public MovingDot_Android(int xStart, int yStart, int xVel, int yVel,
                             int dotColor, String theFilename) {

        // Call constructor of MovingDot
        super(xStart, yStart, xVel, yVel,
                dotColor, theFilename);

        // now set android color
        paint.setColor(dotColor);
    }


    Random r = new Random();  // seed random number generator

    // Constructor
    public MovingDot_Android(int color) {

        paint.setColor(color);

        // random position and speed
        xPos = shapeSize + r.nextInt(800);
        yPos = shapeSize + r.nextInt(800);
        xVel = r.nextInt(10) - 5;
        yVel = r.nextInt(10) - 5;
    }

    // Constructor
    public MovingDot_Android(int color, float x, float y, float speedX, float speedY) {

        paint.setColor(color);

        // use parameter position and speed
        xPos = x;
        yPos = y;
        xVel = speedX;
        yVel = speedY;
    }


    public void draw(Canvas canvas) {
//        bounds.set(x - radius, y - radius, x + radius, y + radius);
//        Log.v("MovingDot_Android", "bounds="+bounds.toString());
//        Log.v("MovingDot_Android", "canvas="+canvas.toString());
//        Log.v("MovingDot_Android", "this="+this.xPos+" "+this.yPos);
        bounds.set(this.xPos - this.shapeSize,
                this.yPos - this.shapeSize,
                this.xPos + this.shapeSize,
                this.yPos + this.shapeSize);
        canvas.drawOval(bounds, paint);
    }

}
