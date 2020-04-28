package com.example.cnit355_teamproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MissCounter {

    private float x;
    private float y;
    private int num_allowed_misses;
    private int num_missed;
    private Bitmap default_image;
    private Bitmap missed_image;
    private boolean[] missed;

    private double SPACING_FACTOR = .5;


    public MissCounter(Bitmap def_img, Bitmap missed_img, int num_missed_imgs) {
        this.default_image = def_img;
        this.missed_image = missed_img;
        this.num_allowed_misses = num_missed_imgs;
        this.missed = new boolean[num_allowed_misses];

        this.SPACING_FACTOR = (.5 * default_image.getWidth()) + default_image.getWidth();

        for(int i = 0; i < num_allowed_misses; i++) {
            missed[i] = false;
        }
    }

    public void draw(Canvas canvas) {
        int copy_num_missed = this.num_missed;
        int missed_drawn = 0;
        float last_x = this.x;

        for(int i = 0; i < this.missed.length; i++) {
            if(missed[i]) {
                int new_x = ((int) (last_x + SPACING_FACTOR));
                canvas.drawBitmap(missed_image, new_x, this.y, null);
                last_x = new_x;
            } else {
                int new_x = ((int) (last_x + SPACING_FACTOR));
                canvas.drawBitmap(default_image, new_x, this.y, null);
                last_x = new_x;
            }
        }
    }

    public boolean miss_occurred() {
        // search array from beginning, looking for nearest non-missed/false spot
        // if found, return true and set spot to true, else return false

        for(int i = 0; i < this.missed.length; i++ ) {
            if(!missed[i]) {
                missed[i] = true;
                return true;
            }
        }
        return false;
    }

    public void update() {

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
