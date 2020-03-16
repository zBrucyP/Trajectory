package com.example.cnit355_teamproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class UserCharacter {

    private Bitmap image;
    private int x;
    private int y;
    private int height;
    private int width;
    private GameView context;

    public UserCharacter(GameView ctx, Bitmap bmp) {
        this.context = ctx;
        this.image = bmp;
        this.x = (int) (( this.context.getScreenWidth() ) * .1);
        this.y = (int) this.context.getScreenHeight() / 2;
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
