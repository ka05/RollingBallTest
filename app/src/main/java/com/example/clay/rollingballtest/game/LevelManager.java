package com.example.clay.rollingballtest.game;

import android.graphics.Rect;

import com.example.clay.rollingballtest.models.Ball;
import com.example.clay.rollingballtest.models.DrawableType;
import com.example.clay.rollingballtest.models.GameDrawable;
import com.example.clay.rollingballtest.observer.GameSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for getting the drawable objects from the database
 * Mapping the current bounds of the different objects on the board
 */
public class LevelManager implements GameSubscriber {
    private GameMediator gameMediator;
    private DatabaseEmulator dbEmulator;

    private List<GameDrawable> drawables;
    private Ball gameBall;
    private Rect ballRect;

    /**
     * Default constructor
     */
    public LevelManager() {
        this.dbEmulator = new DatabaseEmulator();
        this.getModelsForLevel();
        this.mapBounds();
    }

    /**
     * Observer method
     * Called when the balls state changes
     */
    @Override
    public void stateChanged() {
        ballRect = gameBall.getBounds();
    }

    /**
     * Gets the drawables
     *
     * @return an ArrayList of drawables
     */
    public ArrayList<GameDrawable> getDrawables() {
        return (ArrayList)drawables;
    }

    /**
     * Gets the models from the database
     */
    private void getModelsForLevel() {
        drawables = dbEmulator.getLevelObjects();
    }

    /**
     * Maps the bounds of the drawables to be searched through later
     */
    private void mapBounds() {
        for (GameDrawable drawable : drawables) {
            if (drawable.getType() == DrawableType.BALL) this.setGameBall(drawable);

        }
    }

    /**
     * Sets the game ball in the context
     * Adds an observer to the game ball
     *
     * @param drawable the drawable ball
     */
    private void setGameBall(GameDrawable drawable) {
        this.gameBall = (Ball)drawable;
        gameBall.addSubscriber(this);
    }
}
