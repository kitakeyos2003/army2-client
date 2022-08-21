package com.teamobi.mobiarmy2;

import CLib.mSystem;
import CLib.mGraphics;
import model.CRes;
import CLib.mVector;
import coreLG.CCanvas;
import CLib.TemGraphics;

public class TemCanvas {

    public static TemCanvas instance;
    public static TemGraphics tem;
    public static int wMain;
    public static int hMain;
    public CCanvas gamecanvas;
    public static mVector listPoint;

    public TemCanvas() {
        (TemCanvas.instance = this).checkZoomLevel(TemCanvas.wMain, TemCanvas.hMain);
    }

    private void checkZoomLevel(int w, int h) {
        CRes.out("w-H " + w + "-" + h);
    }

    public void start() {
    }

    public void paint(mGraphics gx) {
        if (this.gamecanvas != null) {
            this.gamecanvas.paint(gx);
        }
    }

    public void update() {
        CCanvas.timeNow = mSystem.currentTimeMillis();
        if (this.gamecanvas != null) {
            this.gamecanvas.update();
        }
    }

    public void keyPressed(int keyCode) {
        this.gamecanvas.keyPressed(keyCode);
    }

    public void keyReleased(int keyCode) {
        this.gamecanvas.keyReleased(keyCode);
    }

    static {
        TemCanvas.tem = new TemGraphics();
    }
}
