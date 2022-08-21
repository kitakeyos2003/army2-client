package effect;

import model.CRes;
import CLib.Image;
import model.IAction2;
import coreLG.CONFIG;
import coreLG.CCanvas;
import item.Item;
import CLib.mGraphics;
import player.PM;
import screen.GameScr;
import model.FrameImage;
import model.FilePack;
import CLib.mImage;

public class Explosion {

    public static mImage explode;
    public static mImage teleport;
    public static mImage waterBum;
    public static mImage itemEffImg;
    public static mImage invisibleEff;
    public static mImage imgNuke;
    public static mImage electric;
    public static mImage electric2;
    public static mImage freeze;
    public static mImage dongbang;
    public static mImage khoidoc;
    public static mImage timeBomb;
    static FilePack filePack;
    static mImage imgRain;
    static mImage poison_attack;
    FrameImage frmImg;
    int x;
    int y;
    int endY;
    int wE;
    int delay;
    int frameCountDelay;
    int curFrame;
    public byte whoUseEffect;
    public byte itemID;
    int[][] array;
    public static final byte EXPLODE_NORMAL = 0;
    public static final byte EXPLORE_NORMAL_2 = 11;
    public static final byte TELEPORT = 1;
    public static final byte WATER_BUM = 2;
    public static final byte RAIN_EFFECT = 6;
    public static final byte ITEM_USE_EFFECT = 3;
    public static final byte ITEM_BLINK = 4;
    public static final byte ITEM_ICON_FLY = 5;
    public static final byte NUKE = 7;
    public static final byte ELECTRIC = 8;
    public static final byte ELECTRIC_2 = 9;
    public static final byte POISON = 10;
    public static final byte EYE_SMOKE = 12;
    public static final byte SUICIDE = 13;
    public static final byte FREEZE = 14;
    public static final byte POSION_SMOKE = 15;
    public byte type;
    int maxDelay;

    public Explosion(int x, final int y, final byte exType) {
        this.curFrame = 0;
        this.maxDelay = 1;
        this.x = x;
        this.y = y;
        switch (this.type = exType) {
            case 0:
            case 13: {
                this.frmImg = new FrameImage(Explosion.explode.image, 59, 64);
                break;
            }
            case 11: {
                this.frmImg = new FrameImage(Smoke.smokeNuke.image, 27, 27);
                break;
            }
            case 1: {
                this.frmImg = new FrameImage(Explosion.teleport.image, 32, 32);
                break;
            }
            case 2: {
                this.frmImg = new FrameImage(Explosion.waterBum.image, 32, 48);
                break;
            }
            case 6: {
                this.frmImg = new FrameImage(Explosion.imgRain.image, 13, 7);
                break;
            }
            case 7: {
                this.frmImg = new FrameImage(Explosion.imgNuke.image, 48, 62);
                break;
            }
            case 8: {
                this.frmImg = new FrameImage(Explosion.electric.image, 59, 64);
                break;
            }
            case 9: {
                this.frmImg = new FrameImage(Explosion.electric2.image, 59, 64);
                break;
            }
            case 10: {
                this.frmImg = new FrameImage(Explosion.poison_attack.image, 12, 12);
                break;
            }
            case 14: {
                this.frmImg = new FrameImage(Explosion.freeze.image, 40, 40);
                break;
            }
            case 15: {
                this.frmImg = new FrameImage(Explosion.khoidoc.image, 32, 32);
                break;
            }
        }
        GameScr.exs.addElement(this);
    }

    public Explosion(int x, final int y, final byte exType, final byte WhoUseEffect, final byte itemID) {
        this.curFrame = 0;
        this.maxDelay = 1;
        this.x = x;
        this.y = y;
        this.type = exType;
        this.itemID = itemID;
        this.whoUseEffect = WhoUseEffect;
        if (exType == 3) {
            this.frmImg = new FrameImage(Explosion.itemEffImg.image, 40, 40);
        } else if (exType == 5) {
            this.y -= 24;
            this.endY = this.y - 50;
        }
        if (itemID == 4) {
            this.frmImg = new FrameImage(Explosion.invisibleEff.image, 24, 32);
        }
        if (itemID == 34) {
            this.frmImg = new FrameImage(Explosion.invisibleEff.image, 24, 32);
        }
        GameScr.exs.addElement(this);
    }

    public void update() {
        switch (this.type) {
            case 0:
            case 11:
            case 13: {
                if (this.curFrame == 5) {
                    GameScr.exs.removeElement(this);
                    return;
                }
                break;
            }
            case 8:
            case 9: {
                this.maxDelay = 2;
                if (this.curFrame == 7) {
                    GameScr.exs.removeElement(this);
                    return;
                }
                break;
            }
            case 1: {
                if (this.curFrame == 4) {
                    GameScr.exs.removeElement(this);
                    return;
                }
                break;
            }
            case 2: {
                if (this.curFrame == 4) {
                    GameScr.exs.removeElement(this);
                    return;
                }
                break;
            }
            case 7: {
                if (this.curFrame == 3) {
                    GameScr.exs.removeElement(this);
                    return;
                }
                this.maxDelay = 3;
                break;
            }
            case 10: {
                if (this.curFrame == 3) {
                    GameScr.exs.removeElement(this);
                    return;
                }
                break;
            }
            case 3: {
                if (PM.p[this.whoUseEffect] != null) {
                    this.x = PM.p[this.whoUseEffect].x;
                    this.y = PM.p[this.whoUseEffect].y;
                }
                if (this.curFrame == 4) {
                    GameScr.exs.removeElement(this);
                    return;
                }
                break;
            }
            case 5: {
                this.x = PM.p[this.whoUseEffect].x;
                --this.y;
                if (this.y < this.endY) {
                    GameScr.exs.removeElement(this);
                    return;
                }
                break;
            }
            case 4: {
                if (PM.p[this.whoUseEffect] == null) {
                    break;
                }
                this.x = PM.p[this.whoUseEffect].x;
                this.y = PM.p[this.whoUseEffect].y - 13;
                if (this.itemID == 4 || this.itemID == 34) {
                    if (!PM.p[this.whoUseEffect].isInvisible) {
                        GameScr.exs.removeElement(this);
                        return;
                    }
                    if (this.curFrame == 4) {
                        this.curFrame = 0;
                        break;
                    }
                } else {
                    if (this.itemID == 3 && !PM.p[this.whoUseEffect].isRunSpeed) {
                        GameScr.exs.removeElement(this);
                        return;
                    }
                }
                break;
            }
            case 6: {
                if (this.curFrame == 2) {
                    GameScr.exs.removeElement(this);
                    break;
                }
                break;
            }
            case 14: {
                this.maxDelay = 2;
                if (this.curFrame == 4) {
                    GameScr.exs.removeElement(this);
                    break;
                }
                break;
            }
            case 15: {
                this.maxDelay = 2;
                if (this.curFrame == 4) {
                    GameScr.exs.removeElement(this);
                    break;
                }
                break;
            }
        }
        ++this.delay;
        if (this.delay > this.maxDelay) {
            ++this.curFrame;
            this.delay = 0;
        }
    }

    public void paint(mGraphics g) {
        switch (this.type) {
            case 0:
            case 1:
            case 3:
            case 7:
            case 8:
            case 9:
            case 11:
            case 14:
            case 15: {
                this.frmImg.drawFrame(this.curFrame, this.x, this.y - 12, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                break;
            }
            case 13: {
                this.frmImg.drawFrame(this.curFrame, this.x, this.y - 12, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                this.frmImg.drawFrame(this.curFrame, this.x + 10, this.y - 12 + 10, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                this.frmImg.drawFrame(this.curFrame, this.x - 10, this.y - 12 + 10, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                this.frmImg.drawFrame(this.curFrame, this.x + 10, this.y - 12 - 10, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                this.frmImg.drawFrame(this.curFrame, this.x - 10, this.y - 12 - 10, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                this.frmImg.drawFrame(this.curFrame, this.x, this.y - 12 + 20, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                this.frmImg.drawFrame(this.curFrame, this.x, this.y - 12 - 20, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                this.frmImg.drawFrame(this.curFrame, this.x + 20, this.y - 12, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                this.frmImg.drawFrame(this.curFrame, this.x - 20, this.y - 12, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                break;
            }
            case 2: {
                this.frmImg.drawFrame(this.curFrame, this.x, this.y + 6, 0, mGraphics.HCENTER | mGraphics.BOTTOM, g);
                break;
            }
            case 10: {
                this.frmImg.drawFrame(this.curFrame, this.x, this.y - 6, 0, 3, g);
                break;
            }
            case 5: {
                Item.DrawItem(g, this.itemID, this.x - 8, this.y);
                break;
            }
            case 4: {
                if (this.itemID == 4 || this.itemID == 34) {
                    if (PM.p[this.whoUseEffect].team != PM.p[GameScr.myIndex].team && this.whoUseEffect != GameScr.myIndex) {
                        break;
                    }
                    this.frmImg.drawFrame(this.curFrame, this.x, this.y - 2, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                    if (CCanvas.gameTick % 10 > 2) {
                        Item.DrawItem(g, this.itemID, this.x - 8, this.y - 37);
                        break;
                    }
                } else {
                    if (!PM.p[this.whoUseEffect].isInvisible && CCanvas.gameTick % 10 > 2) {
                        Item.DrawItem(g, this.itemID, this.x - 8, this.y - 37);
                        break;
                    }
                }
                break;
            }
            case 6: {
                this.frmImg.drawFrame(this.curFrame, this.x, this.y - 5, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                break;
            }
            case 12: {
                g.setColor(16777215);
                this.wE += 60;
                if (this.wE > 1000) {
                    this.x = 0;
                    this.wE = 0;
                    GameScr.exs.removeElement(this);
                    break;
                }
                break;
            }
        }
    }

    static {
        try {
            Explosion.filePack = new FilePack(CCanvas.getClassPathConfig(String.valueOf(CONFIG.PATH_EFFECT) + "effect"));
            Explosion.explode = Explosion.filePack.loadImage("ex3.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "ex3", true);
                }
            });
            Explosion.teleport = Explosion.filePack.loadImage("teleport_eff.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "teleport_eff", true);
                }
            });
            Explosion.waterBum = Explosion.filePack.loadImage("waterBum.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "waterBum", true);
                }
            });
            Explosion.itemEffImg = Explosion.filePack.loadImage("Eff.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "Eff", true);
                }
            });
            Explosion.invisibleEff = Explosion.filePack.loadImage("tangHinhEffect.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "tangHinhEffect", true);
                }
            });
            Explosion.imgRain = Explosion.filePack.loadImage("mua.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "mua", true);
                }
            });
            Explosion.imgNuke = Explosion.filePack.loadImage("bomnguyentu.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "bomnguyentu", true);
                }
            });
            Explosion.electric = Explosion.filePack.loadImage("electric.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "electric", true);
                }
            });
            Explosion.electric2 = Explosion.filePack.loadImage("electric2.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "electric2", true);
                }
            });
            Explosion.freeze = Explosion.filePack.loadImage("freeze.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "freeze", true);
                }
            });
            Explosion.dongbang = Explosion.filePack.loadImage("dongbang.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "dongbang", true);
                }
            });
            Explosion.khoidoc = Explosion.filePack.loadImage("khoidoc.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "khoidoc", true);
                }
            });
            Explosion.timeBomb = Explosion.filePack.loadImage("thuocno.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "thuocno", true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Explosion.filePack = null;
        try {
            Explosion.poison_attack = mImage.createImage("/effect/poison-attack.png");
        } catch (Exception ex) {
        }
    }
}
