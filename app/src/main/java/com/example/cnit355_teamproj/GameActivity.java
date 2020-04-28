package com.example.cnit355_teamproj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    // TODO: have MainActivity launch this activity, then this activity launches the gameview/pieces.
    // This will help with returning to the main menu + adding modularity
    private int difficulty;
    private String EXTRA_DIFFICULTY = "EXTRA_DIFFICULTY";
    private Game game;
    private GameView view;
    private MainThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make full screen and landscape
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // get difficulty
        Intent i = getIntent();
        this.difficulty = i.getIntExtra(EXTRA_DIFFICULTY, 0);

        // create the main elements of the game
        // Game provides the data and game logic to show on the view
        // thread is a thread to run game events
        this.game = new Game(this, difficulty);
        this.view = new GameView(this, difficulty, game);
        this.thread = new MainThread(this, view.getHolder(), view);
        game.setupGame(this.view);

        // start the thread to run the game events
        thread.setRunning(true);
        thread.start();

        // set game to show
        setContentView(view);
    }

    public static Intent newInstance(Context ctx) {
        Intent i = new Intent(ctx, GameActivity.class);
        return i;
    }

    public boolean isGameOver() {
        return game.isGameover();
    }

    public void updateGame(Canvas canvas) {
        if(!isGameOver()) {
            // draw models, make any necessary updates
            draw(canvas);
            update();
        }
        else {
            // game is over, return to main menu activity
            setResult(Activity.RESULT_OK);
            //pass score and diffcult to main activity onResume

            finish();
        }
    }

    public void draw(Canvas canvas) {
        view.draw(canvas);
        game.draw(canvas);
    }

    public void update() {
        view.update();
        game.update();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // ensure the thread is stopped
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
