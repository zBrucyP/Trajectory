package com.example.cnit355_teamproj;

import android.graphics.Canvas;
import android.graphics.Color;

public class Scene {

    private GameView context;

    public Scene (GameView ctx) {
        this.context = ctx;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
    }

    public void update() {

    }
}
