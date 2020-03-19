package com.example.cnit355_teamproj;

import android.graphics.Bitmap;

public class Icon {

    private GameView context;
    private Bitmap image;
    private boolean showing;
    private int x;
    private int y;
    private int height;
    private int width;

    public Icon(GameView ctx, Bitmap bmp) {
        this.context = ctx;
        this.image = bmp;
        this.height = this.image.getHeight();
        this.width = this.image.getWidth();
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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
}
