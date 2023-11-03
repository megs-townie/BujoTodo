package com.example.a7_sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGameView extends SurfaceView implements Runnable, SensorEventListener {
    private Thread gameThread;
    private SurfaceHolder surfaceHolder;
    private boolean playing;
    private boolean gamePaused;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private float accelerationX, accelerationY;

    private int screenWidth, screenHeight;
    private int gridSize = 30;
    private int snakeLength = 3;
    private List<SnakeSegment> snake;
    private int foodX, foodY;
    private Random random = new Random();
    private Direction currentDirection = Direction.RIGHT;
    private Direction nextDirection = Direction.RIGHT;

    private Paint snakePaint = new Paint();
    private Paint foodPaint = new Paint();

    private static final int UPDATE_INTERVAL = 150; // Milliseconds

    public SnakeGameView(Context context) {
        super(context);
        surfaceHolder = getHolder();

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        snakePaint.setColor(Color.GREEN);
        foodPaint.setColor(Color.RED);

        // Initialize the game
        initGame();
    }

    private void initGame() {
        snake = new ArrayList<>();
        snake.add(new SnakeSegment(gridSize, gridSize));

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        spawnFood();
    }

    private void spawnFood() {
        foodX = random.nextInt(screenWidth / gridSize) * gridSize;
        foodY = random.nextInt(screenHeight / gridSize) * gridSize;
    }

    private void moveSnake() {
        SnakeSegment newHead;

        switch (nextDirection) {
            case UP:
                newHead = new SnakeSegment(snake.get(0).x, snake.get(0).y - gridSize);
                break;
            case DOWN:
                newHead = new SnakeSegment(snake.get(0).x, snake.get(0).y + gridSize);
                break;
            case LEFT:
                newHead = new SnakeSegment(snake.get(0).x - gridSize, snake.get(0).y);
                break;
            case RIGHT:
                newHead = new SnakeSegment(snake.get(0).x + gridSize, snake.get(0).y);
                break;
            default:
                newHead = new SnakeSegment(snake.get(0).x, snake.get(0).y);
        }

        snake.add(0, newHead);

        // Check for collisions with the food
        if (newHead.x == foodX && newHead.y == foodY) {
            // Snake ate the food
            snakeLength++;
            spawnFood();
        } else {
            // Remove the tail segment
            snake.remove(snake.size() - 1);
        }

        // Check for collisions with the screen boundaries or itself
        if (newHead.x < 0 || newHead.x >= screenWidth || newHead.y < 0 || newHead.y >= screenHeight || checkSelfCollision()) {
            // Game over
            gameOver();
        }
    }

    private boolean checkSelfCollision() {
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).x == snake.get(0).x && snake.get(i).y == snake.get(0).y) {
                return true;
            }
        }
        return false;
    }

    private void gameOver() {
        // Handle game over logic here

        // Stop the game
        pause();

        // You can show a game over dialog or restart the game
        // For simplicity, we'll just restart the game after a delay
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initGame();
                resume();
            }
        }, 2000); // Restart the game after 2 seconds
    }

    @Override
    public void run() {
        while (playing) {
            if (!gamePaused) {
                moveSnake();
                drawGame();
            }
            try {
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        playing = false;
        gamePaused = true;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        gamePaused = false;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch events to start the game
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!playing) {
                // Start or restart the game
                // Initialize game objects and variables here
                initGame();
                resume();
            }
        }
        return true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerationX = event.values[0];
            accelerationY = event.values[1];

            // Determine the snake's direction based on accelerometer values
            if (Math.abs(accelerationX) > Math.abs(accelerationY)) {
                // Horizontal movement
                nextDirection = (accelerationX > 0) ? Direction.RIGHT : Direction.LEFT;
            } else {
                // Vertical movement
                nextDirection = (accelerationY > 0) ? Direction.DOWN : Direction.UP;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void drawGame() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();

            // Clear the canvas
            canvas.drawColor(Color.BLACK);

            // Draw the food
            canvas.drawRect(foodX, foodY, foodX + gridSize, foodY + gridSize, foodPaint);

            // Draw the snake
            for (SnakeSegment segment : snake) {
                canvas.drawRect(segment.x, segment.y, segment.x + gridSize, segment.y + gridSize, snakePaint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private class SnakeSegment {
        int x, y;

        SnakeSegment(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
