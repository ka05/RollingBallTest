package com.example.clay.rollingballtest.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.clay.rollingballtest.R;
import com.example.clay.rollingballtest.models.Ball;
import com.example.clay.rollingballtest.models.Coin;
import com.example.clay.rollingballtest.models.GameDrawable;
import com.example.clay.rollingballtest.models.Goal;
import com.example.clay.rollingballtest.models.Obstacle;

import java.util.ArrayList;

/**
 * Stub class to emulate getting objects from the database
 */
public class DatabaseEmulator {
    private Context context;
    private GameMediator gameMediator;
    private int xMax, yMax;

    /**
     * Default constructor
     *
     */
    public DatabaseEmulator() {
        gameMediator = GameMediator.getInstance(null);
        this.context = gameMediator.getContext();
        this.xMax = gameMediator.getXMax();
        this.yMax = gameMediator.getYMax();
    }

    public ArrayList getLevelObjects() {
        Goal goal = new Goal(getBitmap(R.mipmap.goal), getXFromPercent(0.9f), getYFromPercent(0.5f));
        Coin coin1 = new Coin(getBitmap(R.mipmap.coin), getXFromPercent(0.2f), getYFromPercent(0.2f));
        Coin coin2 = new Coin(getBitmap(R.mipmap.coin), getXFromPercent(0.3f), getYFromPercent(0.3f));
        Coin coin3 = new Coin(getBitmap(R.mipmap.coin), getXFromPercent(0.4f), getYFromPercent(0.4f));
        Obstacle rock = new Obstacle(getBitmap(R.mipmap.brown_rock), getXFromPercent(0.5f), getYFromPercent(0.5f));
        Obstacle tree = new Obstacle(getBitmap(R.mipmap.tree), getXFromPercent(0.6f), getYFromPercent(0.6f));
        Ball ball = new Ball(getBitmap(R.drawable.ball), getXFromPercent(0.1f), getYFromPercent(0.1f));

        ArrayList drawables = new ArrayList<GameDrawable>();
        drawables.add(goal);
        drawables.add(coin1);
        drawables.add(coin2);
        drawables.add(coin3);
        drawables.add(rock);
        drawables.add(tree);
        drawables.add(ball);

        return drawables;
    }

    private int getXFromPercent(float percent) {
        return Math.round(percent * xMax);
    }

    private int getYFromPercent(float percent) {
        return Math.round(percent * yMax);
    }

    private Bitmap getBitmap(int id) {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        return Bitmap.createBitmap(image);
    }
}
