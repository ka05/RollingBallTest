package com.example.clay.rollingballtest.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


/**
 * Responsible for the coin sprite and emulating animation
 */
public class Coin implements GameDrawable {
    private final int COLS = 4;
    private final int ROWS = 2;
    private static final long SWITCH_SPEED = 10;

    private Rect bounds;
    private Bitmap image;
    private int width, height;
    private int currentXFrame, currentYFrame, frameCnt = 0;
    private DrawableType type;
    private long lastDrawn, switchThreshold;

    /**
     * Default constructor
     * @param image the image
     * @param xStart the x start position, left
     * @param yStart the y start position, top
     */
    public Coin(Bitmap image, int xStart, int yStart) {
        this.image = image;
        this.width = image.getWidth() / COLS;
        this.height = image.getHeight() / ROWS;
        this.switchThreshold = 1000 / SWITCH_SPEED;
        this.lastDrawn = 0;
        this.setBounds(xStart, yStart);
    }

    /**
     * Draws the bitmap on the canvas, in the subset of the current frame
     *
     * @param canvas the canvas to be drawn on
     */
    @Override
    public void render(Canvas canvas) {
        this.update();

        int translateX = width * currentXFrame;
        int translateY = height * currentYFrame;
        Rect subset = new Rect(translateX, translateY, translateX + width, translateY + height);

        canvas.drawBitmap(image, subset, bounds, null);
    }

    /**
     * Gets the bounds of the frame
     *
     * @return the bounds
     */
    @Override
    public Rect getBounds() {
        return bounds;
    }

    /**
     * Gets the drawable type
     *
     * @return the type of drawable
     */
    @Override
    public DrawableType getType() {
        return null;
    }

    /**
     * Updates the current frame position
     */
    private void update() {
        if (!(lastDrawn == 0 || (System.currentTimeMillis() - lastDrawn) > switchThreshold)) return;
        currentXFrame = ++frameCnt % COLS;
        currentYFrame = (frameCnt > 4) ? 1 : 0;
        if (frameCnt == 8) frameCnt = 0;
    }

    /**
     * Sets the bounds of the drawable by creating a rect from left and top
     *
     * @param xStart left start
     * @param yStart top start
     */
    private void setBounds(int xStart, int yStart) {
        int width = image.getWidth();
        int height = image.getHeight();
        this.bounds = new Rect(xStart, yStart, xStart + width, yStart + height);
    }
}
