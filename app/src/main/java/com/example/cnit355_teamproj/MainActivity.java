package com.example.cnit355_teamproj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button play_button;
    private RadioGroup rg_difficulties;

    private int LAUNCH_GAME_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // start app with the main menu displayed
        setContentView(R.layout.main_menu);

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
}
