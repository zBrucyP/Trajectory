package com.example.cnit355_teamproj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cnit355_teamproj.database.DatabaseHelper;
import com.example.cnit355_teamproj.database.DbSchema;

public class MainActivity extends Activity {

    private Button play_button;
    private RadioGroup rg_difficulties;
    private Button how_to_play_button;
    private SQLiteDatabase dB;
    private int highscore_easy;
    private int highscore_medium;
    private int highscore_hard;

    private int LAUNCH_GAME_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // start app with the main menu displayed
        setContentView(R.layout.main_menu);

        // grab connection to database and highest of stored scores by difficulty
        dB = new DatabaseHelper(this.getApplicationContext())
                .getWritableDatabase();
        highscore_easy = getHighScore("EASY");
        highscore_medium = getHighScore("MEDIUM");
        highscore_hard = getHighScore("HARD");

        Log.d("difficultyprint", Integer.toString(highscore_easy) + " | " + Integer.toString(highscore_medium) + " | " + Integer.toString(highscore_hard));

        // difficulties radiogroup
        rg_difficulties = (RadioGroup) findViewById(R.id.radioGroup_difficulties);

        // play button
        play_button = (Button) findViewById(R.id.playButton);
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        // how to play button
        how_to_play_button = (Button) findViewById(R.id.howtoplayfr);
        how_to_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howToPlay();
            }
        });
    }

    public void howToPlay() {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        Uri imgUri=Uri.parse("android.resource://" + getPackageName() + "/drawable/" +R.drawable.instructions);
        imageView.setImageURI(imgUri);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    public void startGame() {
        // get user-selected difficulty of game
        int id_radioButton = rg_difficulties.getCheckedRadioButtonId();
        View v_chosenDifficulty = rg_difficulties.findViewById(id_radioButton);
        int difficulty = rg_difficulties.indexOfChild(v_chosenDifficulty);

        Intent i = GameActivity.newInstance(this);
        i.putExtra("EXTRA_DIFFICULTY", difficulty);
        startActivityForResult(i, LAUNCH_GAME_ACTIVITY);

        //setContentView(new GameView(this, difficulty));
    }

    public int getHighScore(String difficulty) {
        Cursor cursor = DatabaseHelper.query_table(
                dB,
                DbSchema.ScoreTable.NAME,
                "difficulty='" + difficulty + "' AND score=(SELECT MAX(score) FROM " + DbSchema.ScoreTable.NAME + ")",
                null
                );

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(cursor.getColumnIndex(DbSchema.ScoreTable.Cols.SCORE));
            }
            else {
                return 0;
            }
        } finally {
            cursor.close();
        }
    }
}
