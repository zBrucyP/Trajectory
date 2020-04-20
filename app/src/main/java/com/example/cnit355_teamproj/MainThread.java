package com.example.cnit355_teamproj;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private GameActivity context;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;

    public MainThread(GameActivity ctx, SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.context = ctx;
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

    @Override
    public void run() {
        while(running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.context.updateGame(canvas);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            try { // I added this
                this.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
