package com.example.cnit355_teamproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    // TODO: have MainActivity launch this activity, then this activity launches the gameview/pieces.
    // This will help with returning to the main menu + adding modularity
    private int difficulty;
    private String EXTRA_DIFFICULTY = "EXTRA_DIFFICULTY";
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // get difficulty
        Intent i = getIntent();
        this.difficulty = i.getIntExtra(EXTRA_DIFFICULTY, 0);

        // set game to show
        setContentView(new GameView(this, difficulty));
    }

    public static Intent newInstance(Context ctx) {
        Intent i = new Intent(ctx, GameActivity.class);
        return i;
    }

    public void isGameOver() {

    }
}
