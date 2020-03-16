package com.example.cnit355_teamproj;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.concurrent.ThreadLocalRandom;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private UserCharacter user_character;
    private EnemyCharacter enemy_character;
    private Scene scene;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean visible;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // set the background and scene objects
        scene = new Scene(this);

        // set the user's character
        user_character = new UserCharacter(this, BitmapFactory.decodeResource(getResources(), R.drawable.users_character));

        // set and configure the enemy character
        enemy_character = new EnemyCharacter(this, BitmapFactory.decodeResource(getResources(), R.drawable.enemy_character));
        enemy_character.setX((int) (Math.random() * screenWidth));
        enemy_character.setY((int) (Math.random() * screenHeight));

        // start game thread
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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
    }

    public void update() {
        scene.update();
        user_character.update();
        enemy_character.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            scene.draw(canvas);
            user_character.draw(canvas);
            enemy_character.draw(canvas);
        }
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
