package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View  {

    private ArrayList<Ball> balls = new ArrayList<Ball>(); // list of Balls
    private ArrayList<Square> squares = new ArrayList<>(); // list of squares
    private Ball ball_1;  // use this to reference first ball in arraylist
    private Box box;
    private Rectangle scoreRectangle;
    private int score = 0;

    // Status message to show Ball's (x,y) position and speed.
    private StringBuilder statusMsg = new StringBuilder();
    private Formatter formatter = new Formatter(statusMsg);
    private Paint paint;

    private int string_line = 1;  //
    private int string_x = 10;
    private int string_line_size = 40;  // pixels to move down one line
    private ArrayList<String> debug_dump1 = new ArrayList();
    private String[] debug_dump2 = new String[200];

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;

    private int generateRandomColor() {
        int alpha = 255;
        int red = (int)(Math.random() * 256);
        int green = (int)(Math.random() * 256);
        int blue = (int)(Math.random() * 256);

        return Color.argb(alpha, red, green, blue);
    }


    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);;

        Log.v("BouncingBallView", "Constructor BouncingBallView");

        // Init the array
        for (int i = 1; i < 200; i++) {
            debug_dump2[i] = "  ";
        }

        // create the box
        box = new Box(Color.BLACK);

        //ball_1 = new Ball(Color.GREEN);
        balls.add(new Ball(Color.GREEN));
        ball_1 = balls.get(0);  // points ball_1 to the first; (zero-ith) element of list
        Log.w("BouncingBallLog", "Just added a bouncing ball");

        //ball_2 = new Ball(Color.CYAN);
        balls.add(new Ball(Color.CYAN));
        Log.w("BouncingBallLog", "Just added another bouncing ball");

        // Initialize scoreRectangle in the center of the Box
        scoreRectangle = new Rectangle(Color.RED);
        float rectWidth = 200f;  // Adjust the width as needed
        float rectHeight = 100f; // Adjust the height as needed
        float rectX = (box.xMax - rectWidth) / 2;
        float rectY = (box.yMax - rectHeight) / 2;
        scoreRectangle.init(rectX, rectY, rectWidth, rectHeight);


        // Set up status message on paint object
        paint = new Paint();

        // Set the font face and size of drawing text
        paint.setTypeface(Typeface.MONOSPACE);
        paint.setTextSize(32);
        paint.setColor(Color.CYAN);

        // To enable keypad
        this.setFocusable(true);
        this.requestFocus();
        // To enable touch mode
        this.setFocusableInTouchMode(true);
    }

    // Called back to draw the view. Also called after invalidate().
    @Override
    protected void onDraw(Canvas canvas) {


        Log.v("BouncingBallView", "onDraw");


        // Draw the components

        box.draw(canvas);
        scoreRectangle.draw(canvas);

        for (Ball b : balls) {
            b.draw(canvas);
            b.moveWithCollisionDetection(box);

            if (scoreRectangle.intersects(b.x, b.y, b.radius)) {
                score++;
                scoreRectangle.changeColor(generateRandomColor());
                Log.d("SCORE", "Score: " + score);
            }
        }

        for (Square s : squares) {
            s.draw(canvas);
            s.moveWithCollisionDetection(box);

            if (scoreRectangle.intersects(s.x, s.y, s.side)) {
                score++;
                scoreRectangle.changeColor(generateRandomColor());
                Log.d("SCORE", "Score: " + score);
            }
        }


        // inc-rotate string_line
        if (string_line * string_line_size > box.yMax) {
            string_line = 1;  // first line is status
            debug_dump1.clear();
        } else {
            string_line++;
        }

        // inc-rotate string_x
        if (string_x > box.xMax) {
            string_x = 10;  // first line is status
        } else {
            string_x++;
        }


        this.invalidate();
    }

    // Called back when the view is first created or its size changes.
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        // Set the movement bounds for the ball
        box.set(0, 0, w, h);
        Log.w("BouncingBallLog", "onSizeChanged w=" + w + " h=" + h);
    }


    // Touch-input handler
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                deltaX = currentX - previousX;
                deltaY = currentY - previousY;

                float swipeSpeed = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                float scalingFactor = 0.3f;
                deltaX *= scalingFactor;
                deltaY *= scalingFactor;

                if (swipeSpeed > 200) {
                    squares.add(new Square(generateRandomColor(), previousX, previousY, deltaX, deltaY));
                } else {
                    balls.add(new Ball(generateRandomColor(), previousX, previousY, deltaX, deltaY));
                    ball_1.speedX += deltaX;
                    ball_1.speedY += deltaY;
                }

                if (balls.size() > 20) {
                    balls.clear();
                    balls.add(new Ball(generateRandomColor(), previousX, previousY, deltaX, deltaY));
                    ball_1 = balls.get(0);
                }

                if (squares.size() > 20) {
                    squares.clear();
                    squares.add(new Square(generateRandomColor(), previousX, previousY, deltaX, deltaY));
                }
        }
        // Save current x, y
        previousX = currentX;
        previousY = currentY;

        // Try this (remove other invalidate(); )
        //this.invalidate();

        return true;  // Event handled
    }

}
