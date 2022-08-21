package model;

import screen.CScreen;
import effect.Camera;
import CLib.mGraphics;
import coreLG.CCanvas;
import network.Session_ME;
import network.GameService;
import player.PM;
import screen.GameScr;

public class CTime {

    int timeInterval;
    public static int seconds;
    long last;
    long cur;
    boolean visible;

    public CTime() {
        this.visible = true;
        long currentTimeMillis = System.currentTimeMillis();
        this.cur = currentTimeMillis;
        this.last = currentTimeMillis;
    }

    public void initTimeInterval(byte time) {
        this.timeInterval = time;
    }

    public void resetTime() {
        CTime.seconds = this.timeInterval;
        this.visible = true;
    }

    public void skipTurn() {
        if (GameScr.pm.isYourTurn()) {
            PM pm = GameScr.pm;
            PM.getMyPlayer().active = false;
            GameService.gI().skipTurn();
            this.visible = false;
        }
    }

    public void update() {
        if (this.visible && !GameScr.trainingMode) {
            this.cur = System.currentTimeMillis();
            if (this.cur - this.last >= 1000L) {
                --CTime.seconds;
                if (CTime.seconds <= -10 && CTime.seconds % 10 == 0) {
                    --CTime.seconds;
                    Session_ME.receiveSynchronized = 0;
                    PM.getCurPlayer().x = PM.getCurPlayer().xToNow;
                    PM.getCurPlayer().y = PM.getCurPlayer().yToNow;
                }
                if (CCanvas.curScr == CCanvas.gameScr && CTime.seconds <= 0) {
                    if (PM.getCurPlayer() != null) {
                        CTime.seconds = 0;
                        if (PM.getCurPlayer().itemUsed != -1) {
                            PM.getCurPlayer().itemUsed = -1;
                        }
                    }
                    return;
                }
                this.last = this.cur;
            }
        }
    }

    public void paint(mGraphics g) {
        int dis = CCanvas.isTouch ? 25 : 0;
        if (this.visible) {
            if (CCanvas.curScr == CCanvas.gameScr) {
                Font.bigFont.drawString(g, Integer.toString(CTime.seconds), Camera.x + CScreen.w - 16, Camera.y + 2 + dis, 2, false);
            }
            if (CCanvas.curScr == CCanvas.luckyGifrScreen) {
                Font.bigFont.drawString(g, Integer.toString(CTime.seconds), CScreen.w - 16, 2, 2, false);
            }
        }
    }

    public void stop() {
        this.visible = false;
    }
}
