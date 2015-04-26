package com.example.clay.rollingballtest.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Obstacle model for the game board
 * When player hits, loses points
 */
public class Obstacle implements GameDrawable {
    private Rect bounds;
    private Bitmap image;
    private DrawableType type;

    /**
     * Default constructor
     * @param image the image to be drawn
     * @param xStart the left location
     * @param yStart the top location
     */
    public Obstacle(Bitmap image, int xStart, int yStart) {
        this.image = image;
        this.type = DrawableType.OBSTACLE;
        this.setBounds(xStart, yStart);
    }

    /**
     * Draws the bitmap on the canvas
     * @param canvas the surface
     */
    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(image, bounds.left, bounds.top, null);
    }

    /**
     * Gets the bounds of the obstacle
     * @return the bounds as defined as a rect
     */
    @Override
    public Rect getBounds() {
        return this.bounds;
    }

    /**
     * Gets the type of drawable the model is
     * @return
     */
    @Override
    public DrawableType getType() {
        return this.type;
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
