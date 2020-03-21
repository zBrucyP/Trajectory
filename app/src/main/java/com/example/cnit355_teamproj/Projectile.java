package com.example.cnit355_teamproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

public class Projectile {

    private GameView context;
    private Bitmap image;
    private int x;
    private int y;
    private int width;
    private int height;
    private float x_velocity;
    private float y_velocity;
    private float gravity; // 0-1
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

    }

    public void draw(Canvas canvas) {
        if (visible) {
            canvas.drawBitmap(image, x, y, null);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getX_velocity() {
        return x_velocity;
    }

    public void setX_velocity(float x_velocity) {
        this.x_velocity = x_velocity;
    }

    public float getY_velocity() {
        return y_velocity;
    }

    public void setY_velocity(float y_velocity) {
        this.y_velocity = y_velocity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public boolean isFired() {
        return fired;
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

    public void rotate(float angle) {
        //TODO: fix this function, bitmap rotation is overcomplicated
        int prev_x = this.x;
        int prev_y = this.y;

        Matrix matrix = new Matrix();
        //matrix.setTranslate(this.x, this.y);
        //matrix.postTranslate(this.x, this.y);
        //matrix.postRotate(angle, this.width/2, this.height/2);
        matrix.setRotate(angle, this.width/2, this.height/2);

        Bitmap new_bmp = Bitmap.createBitmap(this.image, 0, 0, this.width, this.height, matrix, true);

        this.height = new_bmp.getHeight();
        this.width = new_bmp.getWidth();
        this.x = prev_x;
        this.y = prev_y;
        this.image = new_bmp;
    }
}
