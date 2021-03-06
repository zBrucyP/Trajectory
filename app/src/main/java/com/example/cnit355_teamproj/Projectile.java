package com.example.cnit355_teamproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Projectile {

    private GameView context;
    private Bitmap image;
    private int x;
    private int y;
    private int width;
    private int height;
    private float x_velocity = 0;
    private float y_velocity = 0;
    private float gravity; // 0-1
    private int x_destination = 0;
    private int y_destination = 0;
    private int x_reset;
    private int y_reset;
    private boolean visible;
    private boolean fired;
    private Weapon weapon;
    private EnemyCharacter enemy_target;


    public Projectile (GameView ctx, Weapon weapon, Bitmap bmp, float velocity) {
        this.context = ctx;
        this.weapon = weapon;
        this.image = bmp;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.visible = false;
        this.x_velocity = velocity; // allow call to set how fast projectile moves. Y velocity will be dependent on this variable

        this.x = (int) (weapon.getX());
        this.y = (int) (weapon.getY() + (weapon.getHeight() * .3));
        this.x_reset = this.x;
        this.y_reset = this.y;
    }

    public void update() {

        // if projectile hits enemy, stop it. Enemy will already know it has been hit
        if(enemy_target.intersects(new Point(x+width, y), new Point(x+width, y+height))){
            setFired(false);
        }

        // if user launched projectile and it has had no reason to stop, keep moving it
        if (fired) {
            this.x += x_velocity;
            this.y += y_velocity;
        }
    }

    public void draw(Canvas canvas) {
        if (visible) {
            canvas.drawBitmap(image, this.x, this.y, null);
        }
    }

    public void setX_velocity(int x_velocity) {
        this.x_velocity = x_velocity;
    }

    public void setY_velocity(float y_velocity) {
        this.y_velocity = y_velocity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public boolean isFired() {
        return this.fired;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    public void rotate(float angle) {
        // this function is currently unused and does not work
        //TODO: fix this function, bitmap rotation is overcomplicated

        Matrix matrix = new Matrix();
        matrix.postRotate(angle, weapon.getX(), weapon.getHeight()/2);
        //matrix.postScale(1f,1f);
        //matrix.postTranslate(this.x, this.y);

        int crop = (width - height) / 2;
        Bitmap rotated_bmp = Bitmap.createBitmap(this.image, 0, 0, this.width, this.height, matrix, true);
        Bitmap new_bmp = Bitmap.createBitmap(rotated_bmp, 0, 0, this.width, this.height);
        //this.height = new_bmp.getHeight();
        //this.width = new_bmp.getWidth();

        this.image = new_bmp;
    }

    public void setDestinationPoint(int x, int y) {
        this.x_destination = x;
        this.y_destination = y;
    }

    public boolean isOnScreen() {
        int screen_width = this.context.getScreenWidth();
        int screen_height = this.context.getScreenHeight();

        if(this.x <= 0
                || this.x + this.width >= screen_width
                || this.y <= 0
                || this.y + this.height >= screen_height){
            return false;
        }

        return true;
    }

    public void fire_projectile() {
        if (x_destination > 0
                && y_destination > 0) {
            setY_velocity(calculateY_velocity() * x_velocity); // y velocity is dependent on x velocity to maintain proper slope
            this.fired = true;
        }
    }

    public float calculateY_velocity() {
        float slope = ((float) y_destination - y) / (x_destination - x);
        return slope;
        //int b = (slope * -x) + y;
    }

    public void reset() {
        this.x = this.x_reset;
        this.y = this.y_reset;
        this.fired = false;
    }

    public void setEnemy(EnemyCharacter enemy) {
        this.enemy_target = enemy;
    }
}
