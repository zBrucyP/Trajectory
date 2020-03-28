package com.example.cnit355_teamproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class EnemyCharacter {

    private Bitmap image;
    private int x;
    private int y;
    private int height;
    private int width;
    private GameView context;
    private boolean visible;
    private boolean hit;

    public EnemyCharacter(GameView ctx, Bitmap bmp) {
        this.context = ctx;
        this.image = bmp;
        this.height = this.image.getHeight();
        this.width = this.image.getWidth();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {

    }

    public boolean intersects(float x_coord, float y_coord) {

        // if x_coord is within width bounds of image
        if (x_coord < (this.getX() + width)
                && x_coord > (this.getX())) {
            // if touch is within height bounds of image
            if (y_coord < (this.getY() + height)
                    && y_coord > (this.getY())) {
                return true;
            }
        }

        // not within bounds of image
        return false;
    }

    public boolean intersects(float x_left, float y_top, int width_obj, int height_obj) {

        //TODO: determine if object intersects the enermy character.

        // not within bounds of image
        return false;
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
}
