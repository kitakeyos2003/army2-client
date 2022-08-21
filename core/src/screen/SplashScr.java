package screen;

import map.Background;
import CLib.mGraphics;
import coreLG.CCanvas;

public class SplashScr extends CScreen {

    int i;
    boolean loadScreen;
    int frame;
    boolean isPaint;

    public SplashScr() {
        this.i = 0;
        this.frame = 0;
        this.nameCScreen = " SplashScr screen!";
        SplashScr.w = CCanvas.width;
        SplashScr.h = CCanvas.hieght;
        this.loadScreen = true;
    }

    @Override
    public void paint(mGraphics g) {
        g.setColor(7852799);
        g.fillRect(0, 0, SplashScr.w, SplashScr.h, false);
        if (this.isPaint) {
            g.drawRegion(Background.logo, 0, this.frame * 51, 71, 51, 0, CCanvas.width / 2, CCanvas.hieght / 2, mGraphics.BOTTOM | mGraphics.HCENTER, false);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void update() {
        if (this.isPaint) {
            if (this.i < 50) {
                ++this.frame;
                if (this.frame > 3) {
                    this.frame = 3;
                }
            } else {
                --this.frame;
                if (this.frame < 0) {
                    this.frame = 0;
                    this.isPaint = false;
                }
            }
        }
        if (this.loadScreen) {
            this.loadScreen = false;
            this.isPaint = true;
        } else if (this.i == 55) {
            CCanvas.serverListScreen.show();
            return;
        }
        ++this.i;
    }
}
