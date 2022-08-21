package model;

import CLib.mGraphics;
import network.Command;
import screen.CScreen;
import coreLG.CCanvas;

public class InputDlg extends Dialog {

    protected String[] info;
    public TField tfInput;
    public IAction okAction;
    public IAction backAction;
    int dis;

    public InputDlg() {
        this.dis = 0;
        this.dis = (CCanvas.isTouch ? 15 : 0);
        this.tfInput = new TField();
        this.tfInput.x = 20;
        this.tfInput.y = CCanvas.hieght - CScreen.ITEM_HEIGHT - 48 - this.dis;
        this.tfInput.width = CCanvas.width - 40;
        this.tfInput.height = CScreen.ITEM_HEIGHT + 2;
        this.tfInput.setisFocus(true);
        this.tfInput.setisVisible(false);
        this.right = this.tfInput.cmdClear;
    }

    public void setInfo(String info, IAction ok, IAction back, int type) {
        CRes.out("InputDlg ===============> setInfo " + (ok != null) + "_" + (back != null) + "_type = " + type);
        this.tfInput.resetTextBox();
        this.tfInput.setisVisible(true);
        this.tfInput.setText("");
        this.tfInput.setIputType(type);
        this.info = Font.normalFont.splitFontBStrInLine(info, CCanvas.width - 40);
        this.okAction = ok;
        this.backAction = back;
        this.left = new Command(Language.close(), this.backAction);
        this.center = new Command("OK", this.okAction);
    }

    @Override
    public void update() {
        super.update();
        if (this.tfInput != null) {
            this.tfInput.update();
        }
    }

    @Override
    public void paint(mGraphics g) {
        super.paint(g);
        CScreen.paintDefaultPopup(8, CCanvas.hieght - 102 - this.dis, CCanvas.width - 16, 69, g);
        int yStart = CCanvas.hieght - 35 - 50 - (this.info.length >> 1) * Font.normalFont.getHeight();
        for (int i = 0, y = yStart; i < this.info.length; ++i, y += Font.normalFont.getHeight()) {
            Font.normalFont.drawString(g, this.info[i], CCanvas.hw, y - this.dis, 2);
        }
        super.paint(g);
        this.tfInput.paint(g);
    }

    @Override
    public void keyPress(int keyCode) {
        this.tfInput.keyPressed(keyCode);
        super.keyPress(keyCode);
    }

    @Override
    public void onPointerPressed(int x, int y2, int index) {
        if (CCanvas.isPointer(this.tfInput.x, this.tfInput.y, this.tfInput.width, this.tfInput.height, index)) {
            this.tfInput.doChangeToTextBox();
        }
    }

    @Override
    public void show() {
        CCanvas.currentDialog = this;
    }

    @Override
    public void close() {
        this.tfInput.setisVisible(false);
        CRes.out("===========================> close InputDialog");
    }
}
