package effect;

import item.BM;
import CLib.mGraphics;
import coreLG.CCanvas;
import java.util.Random;
import CLib.mImage;
import model.FrameImage;

public class Tornado {

    public int x;
    public int y;
    public int nturn;
    public byte whoShot;
    FrameImage frmImg;
    static mImage imgTornado;
    int n;
    int[] currFrame;
    int dis;
    Random rd;

    public Tornado(int x, int y, int nturn) {
        this.frmImg = null;
        this.dis = 20;
        this.rd = new Random();
        this.x = x - 10;
        this.y = y;
        this.nturn = nturn;
        this.n = CCanvas.hieght / this.dis + 2;
        this.currFrame = new int[this.n];
        this.frmImg = new FrameImage(Tornado.imgTornado.image, 40, 32);
        for (int i = 0; i < this.n; ++i) {
            this.currFrame[i] = i % 3;
        }
    }

    public void paint(mGraphics g) {
        if (this.frmImg != null) {
            for (int i = 0; i < this.n; ++i) {
                this.frmImg.drawFrame(this.currFrame[i], this.x, CCanvas.hieght + Camera.y - i * this.dis, 0, mGraphics.VCENTER | mGraphics.HCENTER, g);
            }
        }
    }

    public void update() {
        if (this.nturn < 0) {
            BM.vTornado.removeElement(this);
        }
        if (CCanvas.gameTick % 2 == 0) {
            for (int i = 0; i < this.n; ++i) {
                int[] currFrame = this.currFrame;
                int n = i;
                int[] array = currFrame;
                int n2 = n;
                ++array[n2];
                if (this.currFrame[i] > 2) {
                    this.currFrame[i] = 0;
                }
            }
        }
    }

    static {
        Tornado.imgTornado = Smoke.imgTornado;
    }
}
