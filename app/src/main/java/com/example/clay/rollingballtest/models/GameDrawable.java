package com.example.clay.rollingballtest.models;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by wyattmcbain on 4/25/15.
 */
public interface GameDrawable {
    void render(Canvas canvas);
    Rect getBounds();
    DrawableType getType();
}
