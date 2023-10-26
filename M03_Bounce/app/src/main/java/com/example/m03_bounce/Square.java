package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Square {
    public float x, y;
    public float side = 50;
    public float speedX, speedY;
    private Paint paint;

    public Square(int color, float x, float y, float speedX, float speedY) {
        this.x = x - side / 2;
        this.y = y - side / 2;
        this.speedX = speedX;
        this.speedY = speedY;
        paint = new Paint();
        paint.setColor(color);
    }

    public void moveWithCollisionDetection(Box box) {
        x += speedX;
        y += speedY;

        if (x + side > box.xMax || x < box.xMin) {
            speedX = -speedX;
        }

        if (y + side > box.yMax || y < box.yMin) {
            speedY = -speedY;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + side, y + side, paint);
    }
}
