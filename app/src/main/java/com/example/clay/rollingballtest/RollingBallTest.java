package com.example.clay.rollingballtest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.clay.rollingballtest.game.GameMediator;
import com.example.clay.rollingballtest.sensors.SensorHandler;
import com.example.clay.rollingballtest.view.GameSurface;

/**
 * Responsible for the game activity
 */
public class RollingBallTest extends Activity {
    private GameSurface gameSurface;

    private SensorHandler sensorHandler;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set to fullscreen and landscape
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sensorHandler = new SensorHandler(this);
        GameMediator.getInstance(this).setSensorHandler(sensorHandler);

        gameSurface = new GameSurface(this);
        setContentView(gameSurface);
    }

    /**
     * Activity override
     * Called when the activity resumes
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        sensorHandler.startSensorListener();
    }

    /**
     * Activity override
     * Called when the game stops
     */
    @Override
    protected void onStop()
    {
        // Unregister the listener
        sensorHandler.stopSensorListener();
        super.onStop();
    }

    /**
     * Activity override
     * Called when the configuration changes
     *
     * @param newConfig the new configuration
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
