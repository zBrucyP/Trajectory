package com.example.cnit355_teamproj;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.concurrent.ThreadLocalRandom;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private UserCharacter user_character;
    private Weapon weapon;
    private EnemyCharacter enemy_character;
    private Scene scene;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean aiming;
    private float previousX = 0;
    private float previousY = 0;

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
        user_character.setX((int) (screenWidth * .1));
        user_character.setY((int) (screenHeight / 2));

        // set the user's weapon
        weapon = new Weapon(this, BitmapFactory.decodeResource(getResources(), R.drawable.weapon1));
        weapon.setX((int) (user_character.getX() + (user_character.getX() * .30))); // 30% further to the right than user's character model
        weapon.setY(user_character.getY());

        // set and configure the enemy character
        enemy_character = new EnemyCharacter(this, BitmapFactory.decodeResource(getResources(), R.drawable.enemy_character));
        enemy_character.setX((int) (Math.random() * (screenWidth - user_character.getX())) + user_character.getX()); // so enemy is not placed behind user
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
        weapon.update();
        enemy_character.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            scene.draw(canvas);

            if (user_character.isSelected()) {
                user_character.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.users_character_selected));
            } else {
                user_character.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.users_character));
            }
            user_character.draw(canvas);

            weapon.draw(canvas);

            enemy_character.draw(canvas);
        }
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // get event details
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            if (user_character.intersects(x, y)) {
                //TODO: show user's inventory
                //TODO: handle game event where user aims shot
                if(!user_character.isSelected()) {
                    user_character.setSelected(true);
                }
            }
        }

        return true;


        //Log.d("xcoord", String.valueOf(event.getX()));
        //Log.d("ycoord", String.valueOf(event.getY()));
        /*
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("down", "down" + x + ' ' + y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("move", "move" + x + ' ' + y);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("up", "up" + x + ' ' + y);
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("cancel", "cancel" + x + ' ' + y);
                break;
        }
         */
    }
}
