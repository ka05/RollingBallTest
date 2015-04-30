package com.example.clay.rollingballtest.game;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.example.clay.rollingballtest.sensors.SensorHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Responsible for getting frame information
 */
public class GameMediator {
    private Context context;
    private SensorHandler sensorHandler;
    private int xMax, yMax;
    private static GameMediator instance;

    /**
     * Default constructor
     *
     * @param context the context of the activity
     */
    private GameMediator(Context context) {
        this.context = context;
    }

    /**
     * Gets the instance of the FrameInfo class
     * @param context the context of the activity
     * @return the FrameInfo class
     */
    public static GameMediator getInstance(Context context) {
        if (instance == null) {
            instance = new GameMediator(context);
            instance.getFrameBounds();
        }
        else if (context != null) {
            instance.setContext(context);
            instance.getFrameBounds();
        }
        return instance;
    }

    /**
     * Gets the frame bounds
     */
    private void getFrameBounds() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        yMax = Math.round(displayMetrics.heightPixels);
        xMax = Math.round(displayMetrics.widthPixels);
    }

    /**
     * Sets the sensor handler for the activity
     *
     * @param sensorHandler
     */
    public void setSensorHandler(SensorHandler sensorHandler) {
        this.sensorHandler = sensorHandler;
    }

    /**
     * Gets the sensor handler for the actibity
     *
     * @return
     */
    public SensorHandler getSensorHandler() {
        return sensorHandler;
    }

    /**
     * Sets the context of the activity
     *
     * @param context the activity context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Gets the current activity context
     *
     * @return the current context of the activity
     */
    public Context getContext() {
        return context;
    }

    /**
     * Gets the xMax of the frame
     *
     * @return the x bounds
     */
    public int getXMax() {
        return xMax;
    }

    /**
     * Gets the yMax of the frame
     *
     * @return the y bounds
     */
    public int getYMax() {
        return yMax;
    }
}
