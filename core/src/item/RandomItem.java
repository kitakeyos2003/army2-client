package item;

import CLib.mGraphics;
import screen.GameScr;
import model.CRes;
import CLib.mImage;

public class RandomItem {

    int x;
    int y;
    int w;
    int h;
    mImage img;
    static boolean visible;
    boolean falling;
    int turnCount;
    int nexty;

    public RandomItem() {
        this.falling = true;
        this.turnCount = 0;
        this.w = this.img.image.getWidth();
        this.h = this.img.image.getHeight();
    }

    public void newPos(int x, int y) {
        this.x = x;
        this.y = y;
        RandomItem.visible = true;
        this.falling = true;
    }

    public void update() {
        if (this.turnCount == 3) {
            if (RandomItem.visible) {
                RandomItem.visible = false;
            } else {
                this.newPos(CRes.random(300, 800), 400);
            }
            this.turnCount = 0;
        }
        if (RandomItem.visible && this.falling) {
            this.nexty = this.y + 5;
            while (this.y < this.nexty) {
                ++this.y;
                if (GameScr.mm.isLand(this.x, this.y)) {
                    this.falling = false;
                    break;
                }
            }
            if (this.y > GameScr.HEIGHT) {
                RandomItem.visible = false;
                this.falling = false;
            }
        }
    }

    public void paint(mGraphics g) {
        if (RandomItem.visible) {
            g.drawImage(this.img, this.x, this.y, mGraphics.BOTTOM | mGraphics.HCENTER, false);
        }
    }

    static {
        RandomItem.visible = false;
    }
}
