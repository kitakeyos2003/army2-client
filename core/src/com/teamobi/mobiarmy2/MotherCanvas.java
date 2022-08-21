package com.teamobi.mobiarmy2;

import CLib.mSystem;
import android.content.Context;
import model.CRes;
import CLib.mGraphics;
import coreLG.CCanvas;

public abstract class MotherCanvas {

    public static int w;
    public static int h;
    public static int hw;
    public static int hh;
    public static int w4;
    public static int h4;
    public CCanvas tCanvas;
    public static int[] ipadPro;
    public static int[] ipadPro1;
    public static int[] iphoneXSMAX;
    public static int[] ipad;
    public static int[] iphoneX;
    public static int[] iphoneXR;
    public static int[] ipadMini;
    public static int[] iphone5;
    public static int[] iphone4;
    public static boolean bRun;
    public static int FPS;

    private void checkZoomLevel(int wM, int hM) {
        if (GameMidlet.DEVICE == 0) {
            mGraphics.zoomLevel = 1;
        } else if (wM * hM >= 1228800) {
            mGraphics.zoomLevel = 4;
        } else if (wM * hM >= 691200) {
            mGraphics.zoomLevel = 3;
        } else if (wM * hM >= 240000) {
            mGraphics.zoomLevel = 2;
        }
        if (wM * hM >= 3166208) {
            mGraphics.zoomLevel = 6;
        } else if (wM * hM >= 2073600) {
            mGraphics.zoomLevel = 4;
        } else if (wM * hM >= 1000500) {
            mGraphics.zoomLevel = 3;
        } else if (wM * hM >= 727040) {
            mGraphics.zoomLevel = 3;
        } else if (wM * hM >= 384000) {
            mGraphics.zoomLevel = 2;
        } else {
            mGraphics.zoomLevel = 1;
        }
        if (mGraphics.zoomLevel > 1) {
            --mGraphics.zoomLevel;
        }
        MotherCanvas.w = wM / mGraphics.zoomLevel;
        MotherCanvas.h = hM / mGraphics.zoomLevel;
        MotherCanvas.hw = MotherCanvas.w / 2;
        MotherCanvas.hh = MotherCanvas.h / 2;
        MotherCanvas.w4 = MotherCanvas.w / 4;
        MotherCanvas.h4 = MotherCanvas.h / 4;
        CRes.out("======> Mother canvas zoom level = " + mGraphics.zoomLevel);
        if (CCanvas.isJ2ME() && CCanvas.width * CCanvas.hieght <= 82500) {
            GameMidlet.lowGraphic = true;
        }
    }

    public void displayMe(GameMidlet m) {
    }

    public MotherCanvas(Context context) {
        setFullScreenMode(true);
        this.checkZoomLevel(this.getWidthL(), this.getHeightL());
    }

    public int getHeightL() {
        return MainGame.getHeight();
    }

    public int getWidthL() {
        return MainGame.getWidth();
    }

    public static void setFullScreenMode(boolean i) {
    }

    public MotherCanvas() {
        setFullScreenMode(true);
        this.checkZoomLevel(this.getWidthL(), this.getHeightL());
    }

    public void start() {
    }

    public static String getPlatformName() {
        return System.getProperty("microedition.platform");
    }

    public int getWidthz() {
        int realWidth = this.getWidthL();
        return realWidth / mGraphics.zoomLevel + ((realWidth % mGraphics.zoomLevel != 0) ? 1 : 0);
    }

    public int getHeightz() {
        int realHeight = this.getHeightL();
        return realHeight / mGraphics.zoomLevel + ((realHeight % mGraphics.zoomLevel != 0) ? 1 : 0);
    }

    public abstract void onPointerDragged(int p0, int p1, int p2);

    public abstract void onPointerPressed(int p0, int p1, int p2, int p3);

    public abstract void onPointerReleased(int p0, int p1, int p2, int p3);

    public abstract void onPointerHolder(int p0, int p1, int p2);

    public abstract void onPointerHolder();

    protected abstract void update();

    public boolean hasPointerEvents() {
        return true;
    }

    public void run() {
    }

    public static int getSecond() {
        return (int) (mSystem.currentTimeMillis() / 1000L);
    }

    public boolean keyPressPc(int keycode) {
        switch (keycode) {
            case 97: {
                CCanvas.keyHold[34] = true;
                return CCanvas.keyPressed[34] = true;
            }
            case 119: {
                return CCanvas.keyPressed[32] = true;
            }
            case 100: {
                CCanvas.keyHold[36] = true;
                return CCanvas.keyPressed[36] = true;
            }
            case 115: {
                return CCanvas.keyPressed[38] = true;
            }
            case 103: {
                CCanvas.keyHold[31] = true;
                return CCanvas.keyPressed[31] = true;
            }
            case 104: {
                return CCanvas.keyPressed[33] = true;
            }
            case 106: {
                CCanvas.keyHold[35] = true;
                return CCanvas.keyPressed[35] = true;
            }
            case 107: {
                return CCanvas.keyPressed[37] = true;
            }
            case 108: {
                CCanvas.keyHold[39] = true;
                CCanvas.keyPressed[39] = true;
                break;
            }
            case -21: {
                CCanvas.keyPressed[40] = true;
                break;
            }
            case -22: {
                CCanvas.keyHold[41] = true;
                return CCanvas.keyPressed[41] = true;
            }
            case 109: {
                CCanvas.keyHold[42] = true;
                return CCanvas.keyPressed[42] = true;
            }
            case 112: {
                return CCanvas.keyPressed[50] = true;
            }
            case 111: {
                CCanvas.keyHold[44] = true;
                return CCanvas.keyPressed[44] = true;
            }
            case 99: {
                return CCanvas.keyPressed[48] = true;
            }
            case 105: {
                CCanvas.keyHold[46] = true;
                return CCanvas.keyPressed[46] = true;
            }
            case 113: {
                return CCanvas.keyPressed[47] = true;
            }
            case 121: {
                CCanvas.keyHold[45] = true;
                return CCanvas.keyPressed[45] = true;
            }
            case 120: {
                return CCanvas.keyPressed[49] = true;
            }
            case 101: {
                CCanvas.keyHold[43] = true;
                return CCanvas.keyPressed[43] = true;
            }
            case 98: {
                return CCanvas.keyPressed[51] = true;
            }
        }
        return false;
    }

    public boolean keyReleasedPc(int keycode) {
        switch (keycode) {
            case 97: {
                CCanvas.keyHold[34] = false;
                CCanvas.keyPressed[34] = false;
                return true;
            }
            case 119: {
                CCanvas.keyPressed[32] = false;
                return true;
            }
            case 100: {
                CCanvas.keyHold[36] = false;
                CCanvas.keyPressed[36] = false;
                return true;
            }
            case 115: {
                CCanvas.keyPressed[38] = false;
                return true;
            }
            case 103: {
                CCanvas.keyHold[31] = false;
                CCanvas.keyPressed[31] = false;
                return true;
            }
            case 104: {
                CCanvas.keyPressed[33] = false;
                return true;
            }
            case 106: {
                CCanvas.keyHold[35] = false;
                CCanvas.keyPressed[35] = false;
                return true;
            }
            case 107: {
                CCanvas.keyPressed[37] = false;
                return true;
            }
            case 108: {
                CCanvas.keyHold[39] = false;
                CCanvas.keyPressed[39] = false;
                break;
            }
            case -21: {
                CCanvas.keyPressed[40] = false;
                break;
            }
            case -22: {
                CCanvas.keyHold[41] = false;
                CCanvas.keyPressed[41] = false;
                return true;
            }
            case 109: {
                CCanvas.keyPressed[42] = false;
                return true;
            }
            case 112: {
                CCanvas.keyHold[50] = false;
                CCanvas.keyPressed[50] = false;
                return true;
            }
            case 111: {
                CCanvas.keyPressed[44] = false;
                return true;
            }
            case 99: {
                CCanvas.keyHold[48] = false;
                CCanvas.keyPressed[48] = false;
                return true;
            }
            case 105: {
                CCanvas.keyPressed[46] = false;
                return true;
            }
            case 113: {
                CCanvas.keyHold[47] = false;
                CCanvas.keyPressed[47] = false;
                return true;
            }
            case 121: {
                CCanvas.keyPressed[45] = false;
                return true;
            }
            case 120: {
                CCanvas.keyHold[49] = false;
                CCanvas.keyPressed[49] = false;
                return true;
            }
            case 101: {
                CCanvas.keyPressed[43] = false;
                return true;
            }
            case 98: {
                CCanvas.keyHold[51] = false;
                CCanvas.keyPressed[51] = false;
                return true;
            }
        }
        return false;
    }

    public abstract void onClearMap();

    static {
        MotherCanvas.ipadPro = new int[]{2732, 2048};
        MotherCanvas.ipadPro1 = new int[]{2224, 1668};
        MotherCanvas.iphoneXSMAX = new int[]{2688, 1242};
        MotherCanvas.ipad = new int[]{2048, 1563};
        MotherCanvas.iphoneX = new int[]{2436, 1125};
        MotherCanvas.iphoneXR = new int[]{1792, 828};
        MotherCanvas.ipadMini = new int[]{1334, 750};
        MotherCanvas.iphone5 = new int[]{1136, 640};
        MotherCanvas.iphone4 = new int[]{960, 640};
        MotherCanvas.FPS = 50;
    }
}
