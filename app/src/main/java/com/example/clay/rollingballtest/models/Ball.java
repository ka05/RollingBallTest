package com.example.clay.rollingballtest.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.clay.rollingballtest.observer.GamePublisher;
import com.example.clay.rollingballtest.observer.GameSubscriber;
import com.example.clay.rollingballtest.sensors.SensorHandler;
import com.example.clay.rollingballtest.game.GameMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyattmcbain on 4/25/15.
 */
public class Ball implements GameDrawable, GameSubscriber, GamePublisher {
    private SensorHandler sensorHandler;
    private GameMediator gameMediator;

    private Rect bounds;
    private Bitmap image;
    private DrawableType type;
    private List<GameSubscriber> subscribers;

    private float xPosition, pitch , xVelocity = 0.0f;
    private float yPosition, roll , yVelocity = 0.0f;
    private int xMax, yMax;

    private final float FRAME_TIME = 0.666f;

    /**
     * Default constructor
     *
     * @param image the image of the ball
     * @param xStart the starting position left
     * @param yStart the starting position top
     */
    public Ball(Bitmap image, int xStart, int yStart) {
        this.image = image;
        this.type = DrawableType.BALL;
        this.gameMediator = GameMediator.getInstance(null);
        this.sensorHandler = gameMediator.getSensorHandler();
        this.xMax = gameMediator.getXMax();
        this.yMax = gameMediator.getYMax();
        this.setBounds(xStart, yStart);
    }

    /**
     * Draws the image on the canvas
     * @param canvas the canvas
     */
    @Override
    public void render(Canvas canvas) {
        this.setBounds(Math.round(xPosition), Math.round(yPosition));
        canvas.drawBitmap(image, Math.round(xPosition), Math.round(yPosition), null);
    }

    /**
     * Gets the bounds of the ball
     * @return the bounds
     */
    @Override
    public Rect getBounds() {
        return bounds;
    }

    /**
     * Gets the drawable type
     * @return the type
     */
    @Override
    public DrawableType getType() {
        return type;
    }

    /**
     * Observer method
     * Is called when the sensor is changed to update the picth and rolls
     */
    @Override
    public void stateChanged() {
        this.pitch = sensorHandler.getPitch();
        this.roll = sensorHandler.getRoll();
        this.update();
    }

    /**
     * Adds a subscriber to the sensor handler
     *
     * @param subscriber the subsciber
     */
    @Override
    public void addSubscriber(GameSubscriber subscriber) {
        if (subscribers == null) subscribers = new ArrayList<GameSubscriber>();
        if (!subscribers.contains(subscriber)) subscribers.add(subscriber);
    }

    /**
     * Removes a subscriber from the list of subscribers
     *
     * @param subscriber the subscriber to be removed
     */
    @Override
    public void removeSubscriber(GameSubscriber subscriber) {
        if (subscribers == null || !subscribers.contains(subscriber)) return;
        subscribers.remove(subscriber);
    }

    /**
     * Notifies the observers of a change
     */
    @Override
    public void notifySubscribers() {
        if (subscribers == null || subscribers.size() == 0) return;
        for (GameSubscriber subscriber : subscribers) {
            subscriber.stateChanged();
        }
    }

    /**
     * Updates the location of where the ball should be drawn
     */
    private void update() {
        //Calculate new speed
        xVelocity += (pitch * FRAME_TIME);
        yVelocity += (roll * FRAME_TIME);

        //Calc distance travelled in that time
        float xS = ((xVelocity / 2) * FRAME_TIME);
        float yS = ((yVelocity / 2) * FRAME_TIME);

        //Add to position negative due to sensor
        //readings being opposite to what we want!
        xPosition -= xS;
        yPosition -= yS;

        if (xPosition > xMax || xPosition < 0) {
            xVelocity *= -0.5;
            xS = ((xVelocity / 2) * FRAME_TIME);
            yS = ((yVelocity / 2) * FRAME_TIME);
            xPosition -= xS;
            yPosition -= yS;
        }
        else if (yPosition > yMax || yPosition < 0) {
            yVelocity *= -0.5;
            xS = ((xVelocity / 2) * FRAME_TIME);
            yS = ((yVelocity / 2) * FRAME_TIME);
            xPosition -= xS;
            yPosition -= yS;
        }
        if (xPosition > xMax) {
            xPosition = xMax;
        } else if (xPosition < 0) {
            xPosition = 0;
        }
        if (yPosition > yMax) {
            yPosition = yMax;
        } else if (yPosition < 0) {
            yPosition = 0;
        }
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
        this.notifySubscribers();
    }
}
