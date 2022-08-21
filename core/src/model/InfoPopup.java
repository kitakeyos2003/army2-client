package model;

import CLib.mGraphics;
import coreLG.CCanvas;
import java.util.Vector;

public class InfoPopup extends Popup {

    private static InfoPopup me;
    private Vector<String> list;
    int x;
    int lim;

    public InfoPopup() {
        this.list = new Vector<>();
    }

    public static InfoPopup gI() {
        return (InfoPopup.me == null) ? (InfoPopup.me = new InfoPopup()) : InfoPopup.me;
    }

    @Override
    public void show() {
        int a = 0;
        for (int i = 0; i < CCanvas.arrPopups.size(); ++i) {
            Popup p = CCanvas.arrPopups.elementAt(i);
            if (p instanceof InfoPopup) {
                a = 1;
                break;
            }
        }
        if (a == 0) {
            super.show();
        }
    }

    public void setInfo(String info) {
        if (this.list.size() == 0) {
            this.lim = -Font.normalFont.getWidth(info);
        }
        this.list.addElement(info);
        if (this.list.size() == 1) {
            this.x = CCanvas.width;
        }
    }

    @Override
    public void update() {
        this.x -= 2;
        if (this.x < this.lim) {
            this.x = CCanvas.width;
            this.list.removeElementAt(0);
            if (this.list.size() <= 0) {
                CCanvas.arrPopups.removeElement(this);
            } else {
                this.lim = -Font.normalFont.getWidth(this.list.elementAt(0));
            }
        }
    }

    @Override
    public void paint(mGraphics g) {
        if (CCanvas.isIos()) {
            return;
        }
        g.translate(-g.getTranslateX(), -g.getTranslateY());
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        g.setClip(0, 0, CCanvas.width, 50);
        g.translate(this.x, 0);
        String str = this.list.elementAt(0);
        if (CCanvas.curScr != CCanvas.gameScr) {
            Font.borderFont.drawString(g, str, 10, 2, 0);
        } else {
            Font.borderFont.drawString(g, str, 10, 20, 0);
        }
    }
}
