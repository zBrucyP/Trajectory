package com.example.cnit355_teamproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class UserCharacter {

    private Bitmap image;
    private int x;
    private int y;
    private int height;
    private int width;
    private GameView context;
    private boolean selected = false;

    public UserCharacter(GameView ctx, Bitmap bmp) {
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

    public boolean intersects(float x_touched, float y_touched) {

        // if touch is within width bounds of image
        if (x_touched < (this.getX() + width)
            && x_touched > (this.getX())) {
            // if touch is within height bounds of image
            if (y_touched < (this.getY() + height)
                && y_touched > (this.getY())) {
                return true;
            }
        }

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
