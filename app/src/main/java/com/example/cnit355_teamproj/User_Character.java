package com.example.cnit355_teamproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class User_Character {

    private Bitmap image;

    public User_Character(Bitmap bmp) {
        this.image = bmp;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 100, 100, null);
    }

    public void update() {

    }

}
