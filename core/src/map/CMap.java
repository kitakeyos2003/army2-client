package map;

import CLib.Image;
import model.IAction2;
import model.CRes;
import model.FilePack;
import coreLG.CCanvas;
import coreLG.CONFIG;
import CLib.mGraphics;
import CLib.mImage;

public class CMap {

    mImage map;
    public int[] aMap;
    public static boolean isDrawRGB;
    public static mImage MANGNHEN;
    static final byte HOLE_Proton = 0;
    static final byte HOLE_Ak = 1;
    static final byte HOLE_Small = 2;
    static final byte HOLE_Cannon = 3;
    static final byte HOLE_Rocket = 4;
    static final byte HOLE_Range = 5;
    static final byte HOLE_RANGCUA = 6;
    static final byte HOLE_GRENADE = 7;
    static final byte HOLE_Smallest = 8;
    static final byte HOLE_BigHole = 9;
    public int index;
    static byte curHoleType;
    public static mImage[] holeIMask;
    public static int[][] holeIntMask;
    static int holeW;
    static int holeH;
    mGraphics gMask;
    public int width;
    public int height;
    public int x;
    public int y;
    public boolean isDestroy;
    public int id;
    public boolean changed;

    public static void onInitCmap() {
        try {
            (CRes.filePak = new FilePack(CCanvas.getClassPathConfig(String.valueOf(CONFIG.PATH_EFFECT) + "Hole"))).loadImage("mangnhen.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.MANGNHEN = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "mangnhen", true);
                }
            });
            CRes.filePak.loadImage("h32x26.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[0] = new mImage((Image) object);
                    int w = CMap.holeIMask[0].image.getWidth();
                    int h = CMap.holeIMask[0].image.getHeight();
                    CMap.holeIntMask[0] = new int[w * h];
                    CMap.holeIMask[0].getRGB(CMap.holeIntMask[0], 0, w, 0, 0, w, h);
                    CRes.onSaveToFile((Image) object, "h32x26", true);
                }
            });
            CRes.filePak.loadImage("smallhole.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[1] = new mImage((Image) object);
                    int w = CMap.holeIMask[1].image.getWidth();
                    int h = CMap.holeIMask[1].image.getHeight();
                    CMap.holeIntMask[1] = new int[w * h];
                    CMap.holeIMask[1].getRGB(CMap.holeIntMask[1], 0, w, 0, 0, w, h);
                    CRes.onSaveToFile((Image) object, "smallhole", true);
                }
            });
            CRes.filePak.loadImage("smallhole.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[2] = new mImage((Image) object);
                    int w = CMap.holeIMask[2].image.getWidth();
                    int h = CMap.holeIMask[2].image.getHeight();
                    CMap.holeIntMask[2] = new int[w * h];
                    CMap.holeIMask[2].getRGB(CMap.holeIntMask[2], 0, w, 0, 0, w, h);
                    CRes.onSaveToFile((Image) object, "smallhole", true);
                }
            });
            CRes.filePak.loadImage("h36x30.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[3] = new mImage((Image) object);
                    int w = CMap.holeIMask[3].image.getWidth();
                    int h = CMap.holeIMask[3].image.getHeight();
                    CMap.holeIntMask[3] = new int[w * h];
                    CMap.holeIMask[3].getRGB(CMap.holeIntMask[3], 0, w, 0, 0, w, h);
                }
            });
            CRes.filePak.loadImage("rocket.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[4] = new mImage((Image) object);
                    int w = CMap.holeIMask[4].image.getWidth();
                    int h = CMap.holeIMask[4].image.getHeight();
                    CMap.holeIntMask[4] = new int[w * h];
                    CMap.holeIMask[4].getRGB(CMap.holeIntMask[4], 0, w, 0, 0, w, h);
                }
            });
            CRes.filePak.loadImage("rangehole.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[5] = new mImage((Image) object);
                    int w = CMap.holeIMask[5].image.getWidth();
                    int h = CMap.holeIMask[5].image.getHeight();
                    CMap.holeIntMask[5] = new int[w * h];
                    CMap.holeIMask[5].getRGB(CMap.holeIntMask[5], 0, w, 0, 0, w, h);
                }
            });
            CRes.filePak.loadImage("hrangcua.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[6] = new mImage((Image) object);
                    int w = CMap.holeIMask[6].image.getWidth();
                    int h = CMap.holeIMask[6].image.getHeight();
                    CMap.holeIntMask[6] = new int[w * h];
                    CMap.holeIMask[6].getRGB(CMap.holeIntMask[6], 0, w, 0, 0, w, h);
                }
            });
            CRes.filePak.loadImage("hgrenade.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[7] = new mImage((Image) object);
                    int w = CMap.holeIMask[7].image.getWidth();
                    int h = CMap.holeIMask[7].image.getHeight();
                    CMap.holeIntMask[7] = new int[w * h];
                    CMap.holeIMask[7].getRGB(CMap.holeIntMask[7], 0, w, 0, 0, w, h);
                }
            });
            CRes.filePak.loadImage("h14x12.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[8] = new mImage((Image) object);
                    int w = CMap.holeIMask[8].image.getWidth();
                    int h = CMap.holeIMask[8].image.getHeight();
                    CMap.holeIntMask[8] = new int[w * h];
                    CMap.holeIMask[8].getRGB(CMap.holeIntMask[8], 0, w, 0, 0, w, h);
                }
            });
            CMap.holeIMask[9] = CRes.filePak.loadImage("h55x50.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CMap.holeIMask[9] = new mImage((Image) object);
                    int w = CMap.holeIMask[9].image.getWidth();
                    int h = CMap.holeIMask[9].image.getHeight();
                    CMap.holeIntMask[9] = new int[w * h];
                    CMap.holeIMask[9].getRGB(CMap.holeIntMask[9], 0, w, 0, 0, w, h);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        CRes.filePak = null;
    }

    public static byte getHoleType(int bulletType) {
        switch (bulletType) {
            case 0:
            case 32: {
                return 3;
            }
            case 1: {
                return 1;
            }
            case 2: {
                return 0;
            }
            case 6: {
                return 6;
            }
            case 7:
            case 31:
            case 37: {
                return 7;
            }
            case 3: {
                return 9;
            }
            case 9: {
                return 5;
            }
            case 10: {
                return 4;
            }
            case 11: {
                return 2;
            }
            case 12: {
                return 6;
            }
            case 15: {
                return 7;
            }
            case 22: {
                return 7;
            }
            case 24: {
                return 3;
            }
            case 25: {
                return 8;
            }
            case 19: {
                return 2;
            }
            case 20: {
                return 0;
            }
            case 27: {
                return 1;
            }
            case 17:
            case 18: {
                return 2;
            }
            case 30: {
                return 0;
            }
            case 21: {
                return 2;
            }
            case 42:
            case 43: {
                return 7;
            }
            case 44: {
                return 2;
            }
            case 45: {
                return 7;
            }
            case 47: {
                return 8;
            }
            case 48: {
                return 3;
            }
            case 52: {
                return 3;
            }
            case 57: {
                return 7;
            }
            default: {
                return 0;
            }
        }
    }

    public CMap() {
        this.changed = false;
    }

    public void createRGB(mImage img) {
        this.map = img;
        this.width = this.map.image.getWidth();
        this.height = this.map.image.getHeight();
        if (!MM.isExistId(this.id)) {
            this.aMap = new int[this.width * this.height];
            this.map.getRGB(this.aMap, 0, this.width, 0, 0, this.width, this.height);
        } else {
            this.aMap = new int[this.width * this.height];
            int[] intClone = MM.rgbMap(this.id);
            System.arraycopy(intClone, 0, this.aMap, 0, this.width * this.height);
        }
        for (int i = 0; i < this.aMap.length; ++i) {
            int n = this.aMap[i];
        }
    }

    public CMap(int id, int x, int y, mImage img, boolean isDestroy) {
        this.changed = false;
        this.x = x;
        this.y = y;
        this.id = id;
        this.isDestroy = isDestroy;
        if (img != null) {
            this.createRGB(img);
        }
    }

    public void paint(mGraphics g) {
        if (this.map != null) {
            g.drawImage(this.map, this.x, this.y, 0, false);
        }
    }

    public void update() {
        if (this.changed) {
            this.map = mImage.createImageNotRunable(this.aMap, this.width, this.height);
            this.changed = false;
        }
    }

    public void makeHole(int bx, int by, int bullType) {
        CMap.curHoleType = getHoleType(bullType);
        CMap.holeW = CMap.holeIMask[CMap.curHoleType].image.getWidth();
        CMap.holeH = CMap.holeIMask[CMap.curHoleType].image.getHeight();
        int xM = this.x;
        int yM = this.y;
        int xH = bx - CMap.holeW / 2;
        int yH = by - CMap.holeH / 2;
        int a1 = 0;
        int a2 = 0;
        int b1 = 0;
        int b2 = 0;
        if (xH < xM) {
            a1 = xM - xH;
            a2 = CMap.holeW;
            if (a2 - a1 > this.width) {
                a2 = a1 + this.width;
            }
        } else {
            a1 = 0;
            a2 = a1 + (this.width - xH + xM);
            if (a2 > CMap.holeW) {
                a2 = CMap.holeW;
            }
        }
        if (yH < yM) {
            b1 = yM - yH;
            b2 = CMap.holeH;
            if (b2 - b1 > this.height) {
                b2 = b1 + this.height;
            }
        } else {
            b1 = 0;
            b2 = b1 + (this.height - yH + yM);
            if (b2 > CMap.holeH) {
                b2 = CMap.holeH;
            }
        }
        int lcx = 0;
        int lcy = 0;
        lcx = bx - this.x - CMap.holeW / 2;
        lcy = by - this.y - CMap.holeH / 2;
        boolean isHole = false;
        for (int i = b1; i < b2; ++i) {
            for (int j = a1; j < a2; ++j) {
                if (CRes.inRect(lcx + j, lcy + i, 0, 0, this.width, this.height)) {
                    if (CMap.holeIntMask[CMap.curHoleType][i * CMap.holeW + j] == -65536 && CRes.isLand(this.getPixel(lcx + j, lcy + i))) {
                        this.aMap[(lcy + i) * this.width + lcx + j] = -16777216;
                        if (!isHole) {
                            isHole = true;
                            HoleInfo holeInfo = new HoleInfo();
                            holeInfo.mapID = (short) this.index;
                            holeInfo.x = (short) bx;
                            holeInfo.y = (short) by;
                            holeInfo.holeType = (byte) bullType;
                            MM.vHoleInfo.addElement(holeInfo);
                        }
                    } else if (CMap.holeIntMask[CMap.curHoleType][i * CMap.holeW + j] == -16777216 || CMap.holeIntMask[CMap.curHoleType][i * CMap.holeW + j] == 16777216) {
                        this.aMap[(lcy + i) * this.width + lcx + j] = 16777215;
                        if (!isHole) {
                            isHole = true;
                            HoleInfo holeInfo = new HoleInfo();
                            holeInfo.mapID = (short) this.index;
                            holeInfo.x = (short) bx;
                            holeInfo.y = (short) by;
                            holeInfo.holeType = (byte) bullType;
                            MM.vHoleInfo.addElement(holeInfo);
                        }
                    }
                }
            }
        }
        this.changed = true;
    }

    public static int getHoleW(int bulletType) {
        return CMap.holeIMask[getHoleType(bulletType)].image.getWidth();
    }

    public static int getHoleH(int bulletType) {
        return CMap.holeIMask[getHoleType(bulletType)].image.getHeight();
    }

    public int getPixel(int x, int y) {
        return this.aMap[y * this.width + x];
    }

    static {
        CMap.isDrawRGB = true;
        CMap.holeIMask = new mImage[10];
        CMap.holeIntMask = new int[10][];
    }
}
