package screen;

import coreLG.CCanvas;
import CLib.mGraphics;

public class TabScreen extends CScreen {

    public int xPaint;
    public int yPaint;
    public int wTabScreen;
    public int hTabScreen;
    public int n;
    public int select;
    public String title;
    public boolean isClose;
    public int tClose;
    protected int wBlank;
    protected int size_px;

    public TabScreen() {
        this.n = 4;
        this.wBlank = 56;
        this.size_px = 32;
    }

    public void getW() {
        this.wTabScreen = this.n * this.size_px + this.wBlank;
    }

    @Override
    public void paint(mGraphics g) {
        if (TabScreen.lastSCreen != null) {
            TabScreen.lastSCreen.paint(g);
        }
        g.translate(this.cmx, 0);
        CScreen.paintBorderRect(g, this.yPaint, this.n, this.hTabScreen, this.title);
    }

    public void paintSuper(mGraphics g) {
        super.paint(g);
    }

    @Override
    public void update() {
        this.moveCamera();
        if (TabScreen.lastSCreen != null) {
            TabScreen.lastSCreen.update();
        }
        if (this.isClose) {
            this.cmtoX = -CCanvas.width;
            ++this.tClose;
            if (this.tClose == 5) {
                this.tClose = 0;
                this.isClose = false;
                CCanvas.curScr = null;
                if (TabScreen.lastSCreen != null) {
                    TabScreen.lastSCreen.show();
                }
            }
        }
    }

    @Override
    public void show(CScreen lastScreen) {
        TabScreen.lastSCreen = lastScreen;
        this.cmx = -CCanvas.width;
        this.cmtoX = 0;
        super.show();
    }
}
