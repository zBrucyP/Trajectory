package com.example.cnit355_teamproj;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

public class Game {

    private Context context;
    private GameView view;
    private UserCharacter user_character;
    private Weapon weapon;
    private Projectile projectile;
    private EnemyCharacter enemy_character;
    private Scene scene;
    private Icon cancel_selection_icon;
    private Icon reset_icon;
    private Icon pause_icon;
    private Icon instructions_menu_icon;
    private Icon return_to_main_menu_icon;
    private Paint score_font_theme;
    private Paint timer_font_theme;
    private int score;
    private int timer;
    private boolean time_is_up;
    private enum difficulty {EASY, MEDIUM, HARD}
    private difficulty game_difficulty;
    private boolean isPaused;
    private boolean isGameover;


    public Game(Context context, int diff) {
        this.context = context;

        // set difficulty of game
        if(diff == 0) {
            this.game_difficulty = difficulty.EASY;
        } else if (diff == 1) {
            this.game_difficulty = difficulty.MEDIUM;
        } else {
            this.game_difficulty = difficulty.HARD;
        }
    }

    public boolean isGameover() {
        return isGameover;
    }

    public void setupGame(GameView v) {

        // attach/set the view to use
        setView(v);

        // mark game as playing
        isPaused = false;

        // set the background and scene objects
        scene = new Scene(view);

        // score setup
        score_font_theme = new Paint();
        score_font_theme.setColor(Color.BLACK);
        score_font_theme.setTextSize(50);
        score = 0;

        // timer setup
        timer_font_theme = new Paint();
        timer_font_theme.setColor(Color.BLACK);
        timer_font_theme.setTextSize(50);
        timer = 60;

        // set the user's character
        user_character = new UserCharacter(view, BitmapFactory.decodeResource(context.getResources(), R.drawable.users_character));
        user_character.setX((int) (view.getScreenWidth() * .1));
        user_character.setY((int) (view.getScreenHeight() / 2));

        // set the user's weapon
        weapon = new Weapon(view, BitmapFactory.decodeResource(context.getResources(), R.drawable.weapon1));
        weapon.setX((int) (user_character.getX() + (user_character.getX() * .30))); // 30% further to the right than user's character model
        weapon.setY(user_character.getY());

        // set and configure the enemy character
        enemy_character = new EnemyCharacter(view, BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_character));
        enemy_character.setX((int) (Math.random() * (view.getScreenWidth() - (user_character.getX() * 3.5))) + (int)(user_character.getX() * 3)); // so enemy is not placed behind user
        enemy_character.setY((int) (Math.random() * (view.getScreenHeight() - (view.getScreenHeight() * .2))));

        // create the projectile for the weapon
        projectile = new Projectile(view, weapon, BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile1), 35);
        projectile.setEnemy(enemy_character);

        // set and configure the cancel icon to get out of character selection
        cancel_selection_icon = new Icon(view, BitmapFactory.decodeResource(context.getResources(), R.drawable.cancel_icon));
        cancel_selection_icon.setX((int) (user_character.getX() - (user_character.getX() * .5)));
        cancel_selection_icon.setY((int) (user_character.getY() - (user_character.getY() * .3)));

        // set and configure the reset icon to bring projectile back
        reset_icon = new Icon(view, BitmapFactory.decodeResource(context.getResources(), R.drawable.reset_icon));
        reset_icon.setX((int) (user_character.getX()));
        reset_icon.setY((int) (user_character.getY() - (user_character.getY() * .3)));

        // set and configure pause icon for instructions and quit game
        pause_icon = new Icon(view, BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_icon));
        pause_icon.setX((int) (view.getScreenWidth() * .05));
        pause_icon.setY((int) (view.getScreenHeight() * .05));
        pause_icon.setActive(true);

        // setup instructions menu
        instructions_menu_icon = new Icon(view, BitmapFactory.decodeResource(context.getResources(), R.drawable.instructions));
        instructions_menu_icon.setX((int) (view.getScreenWidth() * .5) - (instructions_menu_icon.getWidth() / 2));
        instructions_menu_icon.setY((int) (view.getScreenHeight() * .5) - (instructions_menu_icon.getHeight()));

        // icon to return to main menu
        return_to_main_menu_icon = new Icon(view, BitmapFactory.decodeResource(context.getResources(), R.drawable.return_to_menu_icon));
        return_to_main_menu_icon.setX(instructions_menu_icon.getX());
        return_to_main_menu_icon.setY((int) ((instructions_menu_icon.getY() + instructions_menu_icon.getHeight()) * 1.1));

    }

    public void update() {
        scene.update();
        user_character.update();
        weapon.update();
        projectile.update();

        // prevent the projectile from going off screen
        if (!projectile.isOnScreen()) {
            projectile.setFired(false);
            projectile.reset();
            updateScore(false);
        }

        enemy_character.update();
        // if the enemy character was hit by the projectile:
        // 1. update the score by rewarding the player
        // 2. reset the projectile to its home location
        // 3. reset the enemy character, including a new position
        if (enemy_character.isHit()){
            updateScore(true);
            projectile.reset();
            enemy_character.setX((int) (Math.random() * (view.getScreenWidth() - (user_character.getX() * 3.5))) + (int)(user_character.getX() * 3)); // so enemy is not placed behind user
            enemy_character.setY((int) (Math.random() * (view.getScreenHeight() - (view.getScreenHeight() * .2))));
            enemy_character.setHit(false);
        }
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            scene.draw(canvas);
            canvas.drawText(String.valueOf(this.score), view.getScreenWidth()*.1f, view.getScreenHeight()*.1f, score_font_theme); // draw score
            user_character.draw(canvas);
            weapon.draw(canvas);
            projectile.draw(canvas);
            enemy_character.draw(canvas);
            cancel_selection_icon.draw(canvas);
            reset_icon.draw(canvas);
            pause_icon.draw(canvas);
            instructions_menu_icon.draw(canvas);
            return_to_main_menu_icon.draw(canvas);
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

    public void setView(GameView v) {
        this.view = v;
    }

    public void handleUserAction(MotionEvent event) {
        // get event details
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            if (pause_icon.intersects(x, y)) { // if user clicked the pause icon
                isPaused = true;
                instructions_menu_icon.setActive(true);
                return_to_main_menu_icon.setActive(true);
            }
            else if (user_character.isSelected() && !isPaused) { // user in selected state
                if (cancel_selection_icon.intersects(x, y)) { // cancel selection state of user icon
                    user_character.setSelected(false);
                    user_character.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.users_character));
                    projectile.setVisible(false);
                    projectile.reset();
                    cancel_selection_icon.setActive(false);
                    reset_icon.setActive(false);
                }
                else if (reset_icon.intersects(x, y)) { // user clicked reset icon
                    projectile.reset();
                }
                else {
                    if(!projectile.isFired() // if projectile is ready to be fired and user clicked acceptable destination
                            && x > (user_character.getX() * 1.1)) {
                        projectile.setDestinationPoint((int) x,(int) y);
                        projectile.fire_projectile();
                    }
                }
            }
            else {
                if(return_to_main_menu_icon.intersects(x, y) // user clicked button to return to the main menu during pause menu
                        && isPaused) {
                    // go back to main menu
                    isGameover = true;
                }
                else { // close in-game menu, return to game
                    isPaused = false;
                    instructions_menu_icon.setActive(false);
                    return_to_main_menu_icon.setActive(false);

                    if (user_character.intersects(x, y)) { // user selected their character
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
}
