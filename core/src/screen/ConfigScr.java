package screen;

import CLib.RMS;
import CLib.mSound;
import model.Language;
import model.Font;
import effect.Cloud;
import CLib.mGraphics;
import model.CRes;
import network.Command;
import coreLG.CCanvas;
import model.IAction;
import map.CMap;
import CLib.mImage;

public class ConfigScr extends CScreen {

    public static int vibrate;
    public int selected;
    public static mImage[] imgTick;
    public int[] level;
    boolean curDrawRGBType;
    public static String[] graphicText;

    public ConfigScr() {
        this.curDrawRGBType = CMap.isDrawRGB;
        this.level = new int[2];
        this.center = new Command("OK", new IAction() {
            @Override
            public void perform() {
                CCanvas.menuScr.show();
            }
        });
        this.nameCScreen = "ConfigScr screen!";
    }

    @Override
    public void show() {
        this.level[0] = CRes.loadRMSInt("sound") / 10;
        this.level[1] = CRes.loadRMSInt("vibrate");
        if (GameScr.curGRAPHIC_LEVEL == -1) {
            GameScr.curGRAPHIC_LEVEL = 1;
        }
        this.curDrawRGBType = (CRes.loadRMSInt("drawRGB") == 0);
        super.show();
    }

    @Override
    public void paint(mGraphics g) {
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        CScreen.paintDefaultBg(g);
        Cloud.paintCloud(g);
        for (int i = 0; i <= CCanvas.width; i += 32) {
            g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
        }
        Font.bigFont.drawString(g, Language.option(), ConfigScr.w >> 1, 5, 2);
        int x = CCanvas.hw - 50;
        int y = CCanvas.hh - 60;
        if (CCanvas.gameTick % 10 > 2) {
            Font.borderFont.drawString(g, "$", x - 15, y + 14 + 33 * this.selected, 0);
            Font.borderFont.drawString(g, "#", x + 103, y + 14 + 33 * this.selected, 0);
        }
        Font.borderFont.drawString(g, String.valueOf(Language.amthanh()) + ":", x, y, 0);
        y += ConfigScr.ITEM_HEIGHT;
        for (int j = 0; j < 10; ++j) {
            g.drawImage(ConfigScr.imgTick[j < this.level[0] ? 1 : 0], x + j * 10, y, 0, false);
        }
        y += 14;
        Font.borderFont.drawString(g, String.valueOf(Language.vibrate()) + ":", x, y, 0);
        y += ConfigScr.ITEM_HEIGHT;
        for (int j = 0; j < 10; ++j) {
            g.drawImage(ConfigScr.imgTick[j < this.level[1] ? 1 : 0], x + j * 10, y, 0, false);
        }
        Font.borderFont.drawString(g, String.valueOf(Language.imageQuality()) + ":", x, y + 13, 0);
        Font.borderFont.drawString(g, ConfigScr.graphicText[GameScr.curGRAPHIC_LEVEL], CCanvas.hw, y + 29, 2);
        Font.borderFont.drawString(g, String.valueOf(Language.graphicQuality()) + ":", x, y + 48, 0);
        Font.borderFont.drawString(g, this.curDrawRGBType ? Language.macdinh() : Language.khac(), CCanvas.hw, y + 62, 2);
        super.paint(g);
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
        int x = CCanvas.hw - 50;
        int y = CCanvas.hh - 60;
        int aa = (yRealse - y) / 33;
        if (this.selected != aa) {
            this.selected = aa;
        } else {
            if (CCanvas.isPointer(CCanvas.width / 2 - 100, 0, 100, CCanvas.hieght, index)) {
                CCanvas.keyPressed[4] = true;
            }
            if (CCanvas.isPointer(CCanvas.width / 2, 0, 100, CCanvas.hieght, index)) {
                CCanvas.keyPressed[6] = true;
            }
        }
        if (this.selected > 3) {
            this.selected = 0;
        } else if (this.selected < 0) {
            this.selected = 3;
        }
        boolean isChangeValue = false;
        if (CCanvas.keyPressed[4] || ConfigScr.keyLeft) {
            isChangeValue = true;
            CCanvas.keyPressed[4] = false;
            ConfigScr.keyLeft = false;
            if (this.selected == 2) {
                ++GameScr.curGRAPHIC_LEVEL;
                if (GameScr.curGRAPHIC_LEVEL > 1) {
                    GameScr.curGRAPHIC_LEVEL = 0;
                }
            } else if (this.selected == 3) {
                this.curDrawRGBType = !this.curDrawRGBType;
            } else {
                int[] level = this.level;
                int selected = this.selected;
                int[] array = level;
                int n = selected;
                --array[n];
                if (this.level[this.selected] < 0) {
                    this.level[this.selected] = 1;
                }
            }
        } else if (CCanvas.keyPressed[6] || ConfigScr.keyRight) {
            isChangeValue = true;
            CCanvas.keyPressed[6] = false;
            ConfigScr.keyRight = false;
            if (this.selected == 2) {
                --GameScr.curGRAPHIC_LEVEL;
                if (GameScr.curGRAPHIC_LEVEL < 0) {
                    GameScr.curGRAPHIC_LEVEL = 1;
                }
            } else if (this.selected == 3) {
                this.curDrawRGBType = !this.curDrawRGBType;
            } else {
                int[] level2 = this.level;
                int selected2 = this.selected;
                int[] array2 = level2;
                int n2 = selected2;
                ++array2[n2];
                if (this.level[this.selected] > 10) {
                    this.level[this.selected] = 10;
                }
            }
        }
        if (isChangeValue) {
            isChangeValue = false;
            if (this.selected == 0) {
                mSound.setVolume(this.level[this.selected] * 10);
            } else if (this.selected == 1) {
                CRes.saveRMSInt("vibrate", this.level[this.selected]);
                ConfigScr.vibrate = this.level[this.selected];
            } else {
                this.saveGraphicAndDrawRGB_RMS();
            }
        }
    }

    private void saveGraphicAndDrawRGB_RMS() {
        RMS.saveRMSInt("Graphic", GameScr.curGRAPHIC_LEVEL);
        CMap.isDrawRGB = this.curDrawRGBType;
        CRes.saveRMSInt("drawRGB", this.curDrawRGBType ? 0 : 1);
        if (GameScr.curGRAPHIC_LEVEL != 2) {
            GameScr.mm.createBackGround();
        } else {
            GameScr.mm.clearBackGround();
        }
    }

    @Override
    public void update() {
        super.update();
        Cloud.updateCloud();
    }

    static {
        try {
            (ConfigScr.imgTick = new mImage[2])[0] = mImage.createImage("/tick0.png");
            ConfigScr.imgTick[1] = mImage.createImage("/tick1.png");
            ConfigScr.vibrate = CRes.loadRMSInt("vibrate");
            if (CRes.loadRMSInt("vibrate") == -1) {
                CRes.saveRMSInt("vibrate", ConfigScr.vibrate = 10);
            } else {
                mSound.volumeSound = (float) CRes.loadRMSInt("vibrate");
            }
            if (CRes.loadRMSInt("sound") != -1) {
                mSound.volumeSound = CRes.loadRMSInt("sound") / 100.0f;
            } else {
                CRes.saveRMSInt("sound", 100);
                mSound.volumeSound = 1.0f;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConfigScr.graphicText = Language.quality();
    }
}
