package com.example.m03_bounce;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Timer;
import java.util.TimerTask;

public class Rectangle {
    private float centerX, centerY;
    private float width, height;
    private int color;
    private int initialColor;
    private int currentColor;
    private RectF bounds;
    private Paint paint;

    public Rectangle(int color) {
        this.color = color;
        this.initialColor = color;
        this.currentColor = color;

        this.bounds = new RectF();
        this.paint = new Paint();
        this.paint.setColor(color);
    }

    public void init(float x, float y, float width, float height) {
        this.centerX = x + width / 2;
        this.centerY = y + height / 2;
        this.width = width;
        this.height = height;
    }

    public void changeColor(int newColor) {
        currentColor = newColor;
        // Schedule a task to revert to the initial color after a delay (e.g., 1 second)
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                currentColor = initialColor;
            }
        }, 1000); // 1000 milliseconds (1 second) delay
    }

    public void draw(Canvas canvas) {
        bounds.set(centerX - width / 2, centerY - height / 2, centerX + width / 2, centerY + height / 2);
        paint.setColor(currentColor);
        canvas.drawRect(bounds, paint);
    }

    public boolean intersects(float circleX, float circleY, float circleRadius) {
        float rectangleCenterX = centerX;
        float rectangleCenterY = centerY;

        float deltaX = Math.abs(circleX - rectangleCenterX);
        float deltaY = Math.abs(circleY - rectangleCenterY);

        if (deltaX > (width / 2 + circleRadius)) {
            return false;
        }
        if (deltaY > (height / 2 + circleRadius)) {
            return false;
        }

        if (deltaX <= (width / 2)) {
            return true;
        }
        if (deltaY <= (height / 2)) {
            return true;
        }

        float cornerDistance = (deltaX - width / 2) * (deltaX - width / 2) +
                (deltaY - height / 2) * (deltaY - height / 2);

        return (cornerDistance <= (circleRadius * circleRadius));
    }

}

