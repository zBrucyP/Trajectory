package com.example.cnit355_teamproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class UserCharacter {

    private Bitmap image;
    private int x;
    private int y;
    private int height;
    private int width;

    public UserCharacter(Bitmap bmp) {
        this.image = bmp;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {

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
