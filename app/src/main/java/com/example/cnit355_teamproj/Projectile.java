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
    private int x_velocity = 0;
    private int y_velocity = 0;
    private float gravity; // 0-1
    private int x_destination = 0;
    private int y_destination = 0;
    private boolean visible;
    private boolean fired;
    private Weapon weapon;
    private int rotate_angle = 0;

    public Projectile (GameView ctx, Weapon weapon, Bitmap bmp) {
        this.context = ctx;
        this.weapon = weapon;
        this.image = bmp;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.visible = false;

        this.x = (int) (weapon.getX());
        this.y = (int) (weapon.getY() + (weapon.getHeight() * .3));
    }

    public void update() {
        Log.d("isfiring?guess", " " + this.isOnScreen());
        Log.d("test i guess", "test test");

        if (!this.isOnScreen()){
            setFired(false);
        }

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

    public void setY_velocity(int y_velocity) {
        this.y_velocity = y_velocity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void rotate(float angle) {
        //TODO: fix this function, bitmap rotation is overcomplicated
        Log.d("new height/width", String.valueOf(this.height) + " " + String.valueOf(this.width));

        Matrix matrix = new Matrix();
        matrix.postRotate(angle, weapon.getX(), weapon.getHeight()/2);
        //matrix.postScale(1f,1f);
        //matrix.postTranslate(this.x, this.y);

        int crop = (width - height) / 2;
        Bitmap rotated_bmp = Bitmap.createBitmap(this.image, 0, 0, this.width, this.height, matrix, true);
        Bitmap new_bmp = Bitmap.createBitmap(rotated_bmp, 0, 0, this.width, this.height);
        //this.height = new_bmp.getHeight();
        //this.width = new_bmp.getWidth();

        Log.d("new height/width", String.valueOf(this.height) + " " + String.valueOf(this.width));

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
        Log.d("guessfire_projectile", "in fire projectile");
        if (x_destination > 0
                && y_destination > 0) {
            setX_velocity(5);
            setY_velocity(5);
            this.fired = true;
            Log.d("fire_pr cond guess", "fire_projectile in conditional");
        }
    }
}
