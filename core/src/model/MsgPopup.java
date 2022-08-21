package model;

import coreLG.CCanvas;
import CLib.mGraphics;
import screen.GameScr;
import CLib.mImage;

public class MsgPopup extends Popup {

    public static mImage[] imgMsg;
    public int nMessage;

    public MsgPopup() {
        if (MsgPopup.imgMsg == null) {
            MsgPopup.imgMsg = new mImage[2];
        }
        MsgPopup.imgMsg[0] = GameScr.imgMsg[0];
        MsgPopup.imgMsg[1] = GameScr.imgMsg[1];
    }

    @Override
    public void paint(mGraphics g) {
        g.translate(-g.getTranslateX(), -g.getTranslateY());
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        g.setColor(14279153);
        g.fillRoundRect(CCanvas.width - 60, 5, 34, 20, 6, 6, false);
        g.setColor(4682453);
        g.fillRect(CCanvas.width - 60 + 2, 7, 30, 16, false);
        g.drawImage(MsgPopup.imgMsg[0], CCanvas.width - 60 + 4, 9, 0, false);
        Font.normalFont.drawString(g, new StringBuilder().append(this.nMessage).toString(), CCanvas.width - 60 + 28, 9, 2);
    }

    @Override
    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
    }

    @Override
    public void onPointerPressed(int xScreen, int yScreen, int index) {
        super.onPointerPressed(xScreen, yScreen, index);
    }

    @Override
    public void onPointerReleased(int xRealse, int yRealse, int index) {
        super.onPointerReleased(xRealse, yRealse, index);
        if (CCanvas.isPointer(CCanvas.width - 60, 5, 34, 20, index)) {
            CCanvas.msgScr.show(CCanvas.curScr);
        }
    }
}
