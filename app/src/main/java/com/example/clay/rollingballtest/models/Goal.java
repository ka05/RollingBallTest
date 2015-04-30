package com.example.clay.rollingballtest.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Responsibel for handling the goal in our game
 */
public class Goal implements GameDrawable {
    private final int COLS = 4;
    private static long SWITCH_SPEED = 15;

    private Rect bounds;
    private Bitmap image;
    private DrawableType type;
    private int width, height;
    private long lastDrawn, switchThreshold;
    private int currentFrame = 0;

    /**
     * Default constructor
     *
     * @param image the image to be drawn
     * @param xStart the left start of the image
     * @param yStart the top start of the image
     */
    public Goal(Bitmap image, int xStart, int yStart) {
        this.image = image;
        this.type = DrawableType.GOAL;
        this.width = image.getWidth() / COLS;
        this.height = image.getHeight();
        this.switchThreshold = 1000 / SWITCH_SPEED;
        this.lastDrawn = 0;
        this.setBounds(xStart, yStart);
    }

    /**
     * Draws the image on the canvas
     *
     * @param canvas the canvas to be drawn on
     */
    @Override
    public void render(Canvas canvas) {
        this.update();

        int translateX = width * currentFrame;
        Rect subset = new Rect(translateX, 0, translateX + width, height);
        canvas.drawBitmap(image, subset, bounds, null);
    }

    /**
     * Gets the bounds for the goal
     *
     * @return the bounds
     */
    @Override
    public Rect getBounds() {
        return bounds;
    }

    /**
     * Gets the type of drawable it is
     *
     * @return the drawable type
     */
    @Override
    public DrawableType getType() {
        return type;
    }

    /**
     * Updates the current frame
     */
    private void update() {
        if (lastDrawn == 0 || (System.currentTimeMillis() - lastDrawn) > switchThreshold) {
            currentFrame = ++currentFrame % COLS;
            lastDrawn = System.currentTimeMillis();
        }
    }

    /**
     * Sets the bounds of the drawable by creating a rect from left and top
     *
     * @param xStart left start
     * @param yStart top start
     */
    private void setBounds(int xStart, int yStart) {
        this.bounds = new Rect(xStart, yStart, xStart + width, yStart + height);
    }
}
