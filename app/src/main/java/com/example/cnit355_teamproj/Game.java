package com.example.cnit355_teamproj;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

public class Game {

    private GameView context;
    private UserCharacter user_character;
    private Weapon weapon;
    private Projectile projectile;
    private EnemyCharacter enemy_character;
    private Scene scene;
    private Icon cancel_selection_icon;
    private Icon reset_icon;
    private Icon pause_icon;
    private Icon instructions_menu_icon;
    private Paint score_font_theme;
    private int score;
    private enum difficulty {EASY, MEDIUM, HARD}
    private difficulty game_difficulty;
    private boolean isPaused;


    public Game(GameView context, int diff) {
        this.context = context;
        if(diff == 0) {
            this.game_difficulty = difficulty.EASY;
        } else if (diff == 1) {
            this.game_difficulty = difficulty.MEDIUM;
        } else {
            this.game_difficulty = difficulty.HARD;
        }
    }

    public void setupGame() {

        // mark game as playing
        isPaused = false;

        // set the background and scene objects
        scene = new Scene(this.context);

        // score setup
        score_font_theme = new Paint();
        score_font_theme.setColor(Color.BLACK);
        score_font_theme.setTextSize(50);

        // set the user's character
        user_character = new UserCharacter(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.users_character));
        user_character.setX((int) (context.getScreenWidth() * .1));
        user_character.setY((int) (context.getScreenHeight() / 2));

        // set the user's weapon
        weapon = new Weapon(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.weapon1));
        weapon.setX((int) (user_character.getX() + (user_character.getX() * .30))); // 30% further to the right than user's character model
        weapon.setY(user_character.getY());

        // set and configure the enemy character
        enemy_character = new EnemyCharacter(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_character));
        enemy_character.setX((int) (Math.random() * (context.getScreenWidth() - (user_character.getX() * 3.5))) + (int)(user_character.getX() * 3)); // so enemy is not placed behind user
        enemy_character.setY((int) (Math.random() * (context.getScreenHeight() - (context.getScreenHeight() * .2))));

        // create the projectile for the weapon
        projectile = new Projectile(context, weapon, BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile1), 18);
        projectile.setEnemy(enemy_character);

        // set and configure the cancel icon to get out of character selection
        cancel_selection_icon = new Icon(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.cancel_icon));
        cancel_selection_icon.setX((int) (user_character.getX() - (user_character.getX() * .5)));
        cancel_selection_icon.setY((int) (user_character.getY() - (user_character.getY() * .3)));

        // set and configure the reset icon to bring projectile back
        reset_icon = new Icon(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.reset_icon));
        reset_icon.setX((int) (user_character.getX()));
        reset_icon.setY((int) (user_character.getY() - (user_character.getY() * .3)));

        // set and configure pause icon for instructions and quit game
        pause_icon = new Icon(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_icon));
        pause_icon.setX((int) (context.getScreenWidth() * .05));
        pause_icon.setY((int) (context.getScreenHeight() * .05));
        pause_icon.setActive(true);

        // setup instructions menu
        instructions_menu_icon = new Icon(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.instructions));
        instructions_menu_icon.setX((int) (context.getScreenWidth() * .5));
        instructions_menu_icon.setY((int) (context.getScreenHeight() * .5));
    }

    public void update() {
        scene.update();
        user_character.update();
        weapon.update();
        projectile.update();
        if (!projectile.isOnScreen()) {
            projectile.setFired(false);
            projectile.reset();
            updateScore(false);
        }

        enemy_character.update();
        if (enemy_character.isHit()){
            updateScore(true);
            projectile.reset();
            enemy_character.setX((int) (Math.random() * (context.getScreenWidth() - (user_character.getX() * 3.5))) + (int)(user_character.getX() * 3)); // so enemy is not placed behind user
            enemy_character.setY((int) (Math.random() * (context.getScreenHeight() - (context.getScreenHeight() * .2))));
            enemy_character.setHit(false);
        }
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            scene.draw(canvas);
            canvas.drawText(String.valueOf(this.score), context.getScreenWidth()*.1f, context.getScreenHeight()*.1f, score_font_theme); // draw score
            user_character.draw(canvas);
            weapon.draw(canvas);
            projectile.draw(canvas);
            enemy_character.draw(canvas);
            cancel_selection_icon.draw(canvas);
            reset_icon.draw(canvas);
            pause_icon.draw(canvas);
            instructions_menu_icon.draw(canvas);
        }
    }

    public void updateScore(boolean isReward) {
        switch (game_difficulty) {
            case EASY:
                score += isReward ? 1 : 0;
                break;
            case MEDIUM:
                score += isReward ? 1 : -1;
                break;
            case HARD:
                score += isReward ? 1 : -3;
                break;
        }
    }

    public void handleUserAction(MotionEvent event) {
        // get event details
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            if (pause_icon.intersects(x, y)) {
                instructions_menu_icon.setActive(true);
                isPaused = true;
            }
            else if (user_character.isSelected() && !isPaused) {
                if (cancel_selection_icon.intersects(x, y)) {
                    user_character.setSelected(false);
                    user_character.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.users_character));
                    projectile.setVisible(false);
                    projectile.reset();
                    cancel_selection_icon.setActive(false);
                    reset_icon.setActive(false);
                }
                else if (reset_icon.intersects(x, y)) {
                    projectile.reset();
                }
                else {
                    if(!projectile.isFired()
                            && x > (user_character.getX() * 1.1)) {
                        projectile.setDestinationPoint((int) x,(int) y);
                        projectile.fire_projectile();
                    }
                }
            }
            else {
                isPaused = false;
                instructions_menu_icon.setActive(false);

                if (user_character.intersects(x, y)) {
                    user_character.setSelected(true);
                    user_character.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.users_character_selected));
                    projectile.setVisible(true);
                    cancel_selection_icon.setActive(true);
                    reset_icon.setActive(true);
                }
            }
        }
    }
}
