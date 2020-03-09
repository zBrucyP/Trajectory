package com.example.cnit355_teamproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class EnemyCharacter {

    private Bitmap image;
    private int x;
    private int y;
    private int height;
    private int width;

    public EnemyCharacter(Bitmap bmp) {
        this.image = bmp;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {

    }

}
