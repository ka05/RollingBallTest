package com.example.clay.rollingballtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.clay.rollingballtest.game.LevelManager;
import com.example.clay.rollingballtest.models.GameDrawable;
import com.example.clay.rollingballtest.game.GameLoop;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for running the game off the main thread
 */
public class GameSurface extends SurfaceView {
    private SurfaceHolder holder;
    private LevelManager levelManager;
    private List<GameDrawable> drawables;
    private GameLoop gameLoop;

    /**
     * Default constructor
     * Creates a holder callback to change the surface
     *
     * @param context
     */
    public GameSurface(Context context) {
        super(context);

        this.gameLoop = new GameLoop(this);
        this.levelManager = new LevelManager();
        this.drawables = levelManager.getDrawables();
        this.holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoop.setRunning(true);
                gameLoop.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoop.setRunning(false);
                while (retry) {
                    try {
                        gameLoop.join();
                        retry = false;
                    } catch(InterruptedException ie) {}
                }
            }
        });
    }

    /**
     * Draws to the canvas
     * @param canvas
     */
    protected void render(Canvas canvas) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.WHITE);

        for (GameDrawable drawable : drawables) {
            drawable.render(canvas);
        }
    }
}
