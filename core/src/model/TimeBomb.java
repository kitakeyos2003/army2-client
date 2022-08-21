package model;

import item.Bullet;
import screen.GameScr;
import effect.Camera;
import coreLG.CCanvas;
import CLib.mGraphics;
import effect.Explosion;

public class TimeBomb {

    public int id;
    public int x;
    public int y;
    public boolean isExplore;
    public boolean isFall;
    public boolean falling;
    int dy;

    public TimeBomb(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        new Explosion(x, y, (byte) 1);
    }

    public void paint(mGraphics g) {
        int frame = (CCanvas.gameTick % 3 != 0) ? 1 : 0;
        g.drawRegion(Explosion.timeBomb, 0, frame * 15, 28, 15, 0, this.x, this.y, mGraphics.HCENTER | mGraphics.BOTTOM, false);
    }

    public void update() {
        if (this.isExplore) {
            Camera.shaking = 2;
            int[][] array = GameScr.getPointAround(this.x, this.y, 7);
            for (int i = 0; i < 7; ++i) {
                new Explosion(array[0][i], array[1][i], (byte) 7);
            }
            GameScr.timeBombs.removeElement(this);
        }
        if (this.isFall) {
            this.falling = true;
            for (int j = 0; j < 14; ++j) {
                if (GameScr.mm.isLand(this.x - 7 + j, this.y)) {
                    this.falling = false;
                    this.dy = 0;
                    break;
                }
            }
            this.isFall = false;
        }
        if (this.falling) {
            int x1 = this.x;
            int y1 = this.y;
            ++this.dy;
            this.y += this.dy;
            int k = 0;
            while (k < 14) {
                if (GameScr.mm.isLand(this.x - 7 + k, this.y)) {
                    this.falling = false;
                    this.dy = 0;
                    int[] p = Bullet.getCollisionPoint(x1, y1, this.x, this.y);
                    if (p != null) {
                        this.y = p[1];
                        break;
                    }
                    break;
                } else {
                    ++k;
                }
            }
        }
    }
}
