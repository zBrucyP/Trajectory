package com.example.cnit355_teamproj;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.concurrent.ThreadLocalRandom;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Game game;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Context context;

    public GameView(Context ctx, int difficulty, Game game) {
        super(ctx);
        getHolder().addCallback(this);

        this.game = game;
        this.context = ctx;
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        /*
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        retry = false;
         */
    }

    public void update() {
        //game.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.handleUserAction(event);
        return true;
    }
}
