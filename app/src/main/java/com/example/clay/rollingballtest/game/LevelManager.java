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
        this.gameMediator = GameMediator.getInstance(null);
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
        ArrayList<GameDrawable> copy = new ArrayList<GameDrawable>(drawables);
        for (GameDrawable drawable : copy) {
            if (drawable.getType() == DrawableType.BALL) continue;
            if (ballRect.intersect(drawable.getBounds())) {
                drawables.remove(drawable);
                drawable = null;
            }
        }
    }

    /**
     * Gets the drawables
     *
     * @return an ArrayList of drawables
     */
    public List<GameDrawable> getDrawables() {
        return drawables;
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
            if (drawable.getType() == DrawableType.BALL) {
                this.gameBall = (Ball)drawable;
                gameMediator.getSensorHandler().addSubscriber(this.gameBall);
                this.gameBall.addSubscriber(this);
            }
            else {

            }
        }
    }
}
