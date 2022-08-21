package effect;

import CLib.mGraphics;
import map.Background;
import screen.GameScr;
import map.MM;
import coreLG.CCanvas;
import CLib.mImage;

public class Snow {

    int[] x;
    int[] y;
    int[] vx;
    int[] vy;
    public static mImage imgSnow;
    int[] type;
    int sum;
    int state;
    int typeSnow;
    int xx;
    public int waterY;
    boolean[] isRainEffect;
    int[] frame;
    int[] t;
    boolean[] activeEff;
    public int min;
    public int max;
    public int vymin;
    public int vymax;
    public int vxmin;

    public Snow() {
        this.waterY = 0;
        this.min = 99;
        this.max = 100;
        this.vymin = 15;
        this.vymax = 20;
        this.vxmin = 7;
    }

    public void startSnow(int typeS) {
        this.typeSnow = typeS;
        if (this.typeSnow == 0) {
            this.sum = CCanvas.random(150, 200);
        }
        if (this.typeSnow == 1) {
            this.sum = CCanvas.random(this.min, this.max);
        }
        if (Snow.imgSnow == null) {
            try {
                Snow.imgSnow = mImage.createImage("/tuyet.png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.x = new int[this.sum];
        this.y = new int[this.sum];
        this.vx = new int[this.sum];
        this.vy = new int[this.sum];
        this.type = new int[this.sum];
        this.isRainEffect = new boolean[this.sum];
        for (int i = 0; i < this.sum; ++i) {
            this.x[i] = CCanvas.random(-10, MM.mapWidth);
            this.y[i] = CCanvas.random(-100, MM.mapHeight - this.waterY);
            this.vx[i] = 0;
            if (this.typeSnow == 0) {
                this.vy[i] = CCanvas.random(1, 3);
            }
            if (this.typeSnow == 1) {
                this.vy[i] = CCanvas.random(this.vymin, this.vymax);
            }
            this.type[i] = CCanvas.random(1, 3);
            if (this.type[i] == 2 && i % 2 == 0) {
                this.isRainEffect[i] = true;
            }
        }
    }

    public void update() {
        if (this.state == 100) {
            return;
        }
        for (int i = 0; i < this.sum; ++i) {
            if (this.state == 0) {
                int[] y = this.y;
                int n = i;
                int[] array = y;
                int n3 = n;
                array[n3] += this.vy[i];
                if (this.typeSnow == 0) {
                    this.vx[i] = GameScr.windx >> 4;
                }
                if (this.typeSnow == 1) {
                    if (GameScr.windx == 0) {
                        this.vx[i] = 1;
                    } else {
                        this.vx[i] = ((GameScr.windx > 0) ? this.vxmin : (-this.vxmin));
                    }
                }
                int[] x = this.x;
                int n2 = i;
                int[] array2 = x;
                int n4 = n2;
                array2[n4] += this.vx[i];
            }
            if (this.y[i] < -200 || this.y[i] > Background.waterY + 40 || this.x[i] > MM.mapWidth || this.x[i] < -10) {
                if (this.y[i] > Background.waterY + 40) {
                    new Explosion(this.x[i], this.y[i], (byte) 6);
                }
                this.x[i] = CCanvas.random(-10, MM.mapWidth);
                this.y[i] = CCanvas.random(-100, Background.waterY + 40);
            }
        }
    }

    public void paintBigSnow(mGraphics g) {
        if (this.state == 100) {
            return;
        }
        for (int i = 0; i < this.sum; ++i) {
            if (this.type[i] == 2) {
                if (this.typeSnow == 0) {
                    g.drawImage(Snow.imgSnow, this.x[i], this.y[i], 0, false);
                }
                if (this.typeSnow == 1) {
                    g.setColor(15987699);
                    int wind = Math.abs(GameScr.windx);
                    int nWind = GameScr.windx;
                    if (wind == 0) {
                        g.drawLine(this.x[i], this.y[i], this.x[i] + 1, this.y[i] + 4, false);
                    }
                    if (wind > 0) {
                        g.drawLine(this.x[i], this.y[i], this.x[i] + ((nWind > 0) ? 4 : -4), this.y[i] + 4, false);
                        g.drawLine(this.x[i], this.y[i], this.x[i] + ((nWind > 0) ? 3 : -3), this.y[i] + 4, false);
                    }
                }
            }
        }
    }

    public void paintOnlySmall(mGraphics g) {
        for (int i = 0; i < this.sum; ++i) {
            if (this.typeSnow == 1) {
                g.setColor(10921638);
                int wind = Math.abs(GameScr.windx);
                int nWind = GameScr.windx;
                if (wind == 0) {
                    g.drawLine(this.x[i], this.y[i], this.x[i] + 1, this.y[i] + 2, false);
                }
                if (wind > 0) {
                    g.drawLine(this.x[i], this.y[i], this.x[i] + ((nWind > 0) ? 2 : -2), this.y[i] + 2, false);
                }
            }
        }
    }

    public void paintSmallSnow(mGraphics g) {
        if (this.state == 100) {
            return;
        }
        g.setColor(10742731);
        for (int i = 0; i < this.sum; ++i) {
            if (this.type[i] != 2) {
                if (this.typeSnow == 0) {
                    g.fillRect(this.x[i], this.y[i], 2, 2, false);
                }
                if (this.typeSnow == 1) {
                    g.setColor(11908790);
                    int wind = Math.abs(GameScr.windx);
                    int nWind = GameScr.windx;
                    if (wind == 0) {
                        g.drawLine(this.x[i], this.y[i], this.x[i] + 1, this.y[i] + 2, false);
                    }
                    if (wind > 0) {
                        g.drawLine(this.x[i], this.y[i], this.x[i] + ((nWind > 0) ? 2 : -2), this.y[i] + 2, false);
                    }
                }
            }
        }
    }
}
