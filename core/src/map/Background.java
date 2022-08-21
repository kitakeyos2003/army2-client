package map;

import player.CPlayer;
import model.IAction2;
import model.FilePack;
import coreLG.CONFIG;
import screen.PrepareScr;
import coreLG.CCanvas;
import CLib.mGraphics;
import model.CRes;
import effect.Camera;
import screen.GameScr;
import screen.CScreen;
import effect.Snow;
import model.FrameImage;
import CLib.mImage;

public class Background {

    public static final byte BACKGR_HALONG = 0;
    public static final byte BACKGR_RUONGLUA = 1;
    public static final byte BACKGR_SIMPLESKY = 2;
    public static final byte BACKGR_RUNGRAM = 3;
    public static final byte BACKGR_HOANGTAN = 4;
    public static final byte BACKGR_SONGNUI = 5;
    public static final byte BACKGR_CITY = 6;
    public static final byte BACKGR_ICE = 7;
    public static final byte BACKGR_FORREST = 8;
    public static final byte BACKGR_BOSS_1 = 9;
    public static final byte BACKGR_CITY_NIGHT = 10;
    public static final byte BACKGR_NIGHT_FORREST = 12;
    public static final byte BACKGR_CAVE = 13;
    public static final byte BACKGR_CLOUD = 14;
    public static final byte BACKGR_GREY = 15;
    public static mImage sun;
    public static mImage sun2;
    public static mImage haLongbg;
    public static mImage cloud;
    public static mImage water;
    public static mImage inWater;
    public static mImage canhdong;
    public static mImage co;
    public static mImage co2;
    public static mImage rungRam;
    public static mImage bang;
    public static mImage back01;
    public static mImage back02;
    public static mImage back03;
    public static mImage back04;
    public static mImage back05;
    public static mImage back06;
    public static mImage back07;
    public static mImage back08;
    public static mImage back11;
    public static mImage back12;
    public static mImage back14;
    public static mImage back15;
    public static mImage back16;
    public static mImage back17;
    public static mImage thaprua;
    public static mImage balloon;
    public static mImage a;
    public static mImage b;
    public static mImage bigBalloon;
    public static mImage mocxich;
    public static mImage bg_cloud;
    public static mImage bg_cloud_1;
    public static mImage logo;
    public static mImage[] may;
    public static mImage rock_up;
    public static mImage map_spider_layout;
    public static mImage rock_down;
    public static mImage stone;
    public static boolean isLoadImage;
    public static byte curBGType;
    public int yBackGr;
    public int yCloud;
    static FrameImage waterSp;
    static int skyLine;
    static int sunX;
    static int sunY;
    static int nBgX;
    static int nBgY;
    static int nBgX2;
    public static int waterY;
    public static int glassY;
    int[] cloudx;
    int[] cloudy;
    int[] cloudz;
    int[] cloudx2;
    int[] cloudy2;
    static int nWave;
    static int[] wavex;
    static int[] wavex2;
    static int[] wavey;
    static int[] length;
    static int[] delay;
    Snow snow;
    boolean boltActive;
    int tBolt;
    int wLazer;
    boolean changeSign;
    int limit;
    int[] t;
    static int[] xT;
    static int[] yT;

    public void loadImage(int Type) {
        try {
            switch (Type) {
                case 0: {
                    Background.haLongbg = mImage.createImage("/map/bgItem/halongkaka.png");
                    break;
                }
                case 1: {
                    Background.canhdong = mImage.createImage("/map/bgItem/canhdong.png");
                    break;
                }
                case 2: {
                    Background.bang = mImage.createImage("/map/bgItem/bang.png");
                    break;
                }
                case 3: {
                    Background.rungRam = mImage.createImage("/map/bgItem/rungRam.png");
                    break;
                }
                case 4: {
                    Background.back01 = mImage.createImage("/map/bgItem/back1.png");
                    Background.back02 = mImage.createImage("/map/bgItem/back2.png");
                    break;
                }
                case 5: {
                    Background.back03 = mImage.createImage("/map/bgItem/back3.png");
                    Background.back04 = mImage.createImage("/map/bgItem/back4.png");
                    break;
                }
                case 6: {
                    Background.back05 = mImage.createImage("/map/bgItem/back5.png");
                    Background.back06 = mImage.createImage("/map/bgItem/back6.png");
                    break;
                }
                case 10: {
                    Background.back11 = mImage.createImage("/map/bgItem/back11.png");
                    Background.back12 = mImage.createImage("/map/bgItem/back12.png");
                    break;
                }
                case 9: {
                    Background.back05 = mImage.createImage("/map/bgItem/back5.png");
                    Background.back06 = mImage.createImage("/map/bgItem/back6.png");
                    break;
                }
                case 7: {
                    Background.back07 = mImage.createImage("/map/bgItem/back7.png");
                    Background.back08 = mImage.createImage("/map/bgItem/back8.png");
                    break;
                }
                case 12: {
                    Background.back14 = mImage.createImage("/map/bgItem/back14.png");
                    Background.back15 = mImage.createImage("/map/bgItem/back15.png");
                    break;
                }
                case 13: {
                    Background.rock_up = mImage.createImage("/map/bgItem/rock_up.png");
                    Background.map_spider_layout = mImage.createImage("/map/bgItem/map_spider_layout.png");
                    Background.rock_down = mImage.createImage("/map/bgItem/rock_down.png");
                    break;
                }
                case 14: {
                    Background.bg_cloud = mImage.createImage("/map/bgItem/bg-cloud.png");
                    Background.bg_cloud_1 = mImage.createImage("/map/bgItem/bg_cloud1.png");
                    break;
                }
                case 15: {
                    Background.back16 = mImage.createImage("/map/bgItem/back16.png");
                    Background.back17 = mImage.createImage("/map/bgItem/back17.png");
                    break;
                }
            }
        } catch (Exception ex) {
        }
    }

    public static void removeImage() {
        Background.haLongbg = null;
        Background.canhdong = null;
        Background.rungRam = null;
        Background.bang = null;
        Background.back01 = null;
        Background.back02 = null;
        Background.back03 = null;
        Background.back04 = null;
        Background.back05 = null;
        Background.back06 = null;
        Background.back07 = null;
        Background.back08 = null;
        Background.back11 = null;
        Background.back12 = null;
        Background.back14 = null;
        Background.back15 = null;
        Background.back16 = null;
        Background.back17 = null;
        Background.bg_cloud = null;
        Background.bg_cloud_1 = null;
        Background.rock_up = null;
        Background.map_spider_layout = null;
        Background.rock_down = null;
    }

    public static void initImage() {
    }

    public Background(byte BGType) {
        this.yBackGr = 0;
        this.yCloud = 130;
        this.wLazer = 8;
        this.limit = 613;
        this.t = new int[10];
        removeImage();
        this.cloudx = new int[] { 52, 110, 250 };
        this.cloudy = new int[] { 100, 180, 150 };
        this.cloudz = new int[] { 45, 40, 50 };
        this.cloudx2 = new int[] { 100, 200, 300 };
        this.cloudy2 = new int[] { 80, 50, 100 };
        Background.skyLine = CScreen.h - 135;
        Background.curBGType = BGType;
        Background.waterSp = new FrameImage(Background.water.image, 24, 24);
        Background.glassY = MM.mapHeight - Background.co.image.getHeight();
        Background.sunX = CScreen.w - 60;
        Background.sunY = Background.skyLine - 75;
        switch (BGType) {
            case 0: {
                Background.nBgX = CScreen.w / 128;
                break;
            }
            case 1: {
                Background.nBgX = CScreen.w / 72;
                break;
            }
            case 3: {
                Background.nBgX = CScreen.w / 72;
                break;
            }
            case 2: {
                Background.nBgX = CScreen.w / 64;
                break;
            }
            case 4: {
                Background.nBgX = CScreen.w / 241;
                Background.nBgX2 = CScreen.w / 226;
                break;
            }
            case 5: {
                Background.nBgX = CScreen.w / 241;
                break;
            }
            case 6:
            case 10: {
                Background.nBgX = CScreen.w / 238;
                Background.nBgX2 = CScreen.w / 225;
                break;
            }
            case 8: {
                Background.nBgX = GameScr.w / 219;
                Background.nBgX2 = GameScr.w / 210;
                break;
            }
            case 7: {
                Background.nBgX = GameScr.w / 219;
                Background.nBgX2 = GameScr.w / 218;
                break;
            }
            case 12: {
                Background.nBgX = CScreen.w / 108;
                Background.nBgX2 = CScreen.w / 108;
                this.snow = new Snow();
                this.snow.min = 300;
                this.snow.max = 400;
                this.snow.vymin = 5;
                this.snow.vymax = 7;
                this.snow.vxmin = 3;
                this.snow.waterY = 150;
                this.snow.startSnow(1);
                this.snow.waterY = -50;
                break;
            }
            case 13: {
                Background.nBgX = GameScr.w / 219;
                Background.nBgX2 = GameScr.w / 218;
                break;
            }
            case 14: {
                Background.nBgX = GameScr.w / 128;
                Background.nBgX2 = GameScr.w / 128;
                break;
            }
            case 15: {
                Background.nBgX = GameScr.w / 241;
                Background.nBgX = GameScr.w / 241;
                break;
            }
        }
        this.loadImage(BGType);
    }

    public void update() {
        if (GameScr.curGRAPHIC_LEVEL != 2 && Background.curBGType != 3) {
            this.updateCloud();
        }
        if (this.snow != null) {
            this.snow.update();
        }
    }

    private void updateCloud() {
        for (int i = 0; i < this.cloudx.length; ++i) {
            int[] cloudx = this.cloudx;
            int n = i;
            int[] array = cloudx;
            int n5 = n;
            array[n5] -= (Camera.x - Camera.startx) * this.cloudz[i] / 100;
            int[] cloudy = this.cloudy;
            int n2 = i;
            int[] array2 = cloudy;
            int n6 = n2;
            array2[n6] -= (Camera.y - Camera.starty) * this.cloudz[i] / 100;
            int[] cloudx2 = this.cloudx2;
            int n3 = i;
            int[] array3 = cloudx2;
            int n7 = n3;
            array3[n7] -= (Camera.x - Camera.startx) * this.cloudz[i] / 100;
            int[] cloudy2 = this.cloudy2;
            int n4 = i;
            int[] array4 = cloudy2;
            int n8 = n4;
            array4[n8] -= (Camera.y - Camera.starty) * this.cloudz[i] / 100;
        }
    }

    public void updateWave() {
        for (int i = 0; i < Background.nWave; ++i) {
            if (Background.delay[i] > 50) {
                Background.wavex[i] = CRes.random(0, CScreen.w - 10);
                Background.wavey[i] = CRes.random(Background.skyLine, CScreen.h);
                Background.length[i] = CRes.random(1, 5);
                Background.delay[i] = 0;
            } else {
                int[] delay = Background.delay;
                int n = i;
                int[] array = delay;
                int n2 = n;
                ++array[n2];
            }
        }
    }

    public void paint(mGraphics g) {
        this.drawBackGround(Background.curBGType, g);
    }

    private void drawBackGround(byte Type, mGraphics g) {
        g.translate(-g.getTranslateX(), -g.getTranslateY());
        switch (Type) {
            case 0: {
                g.setColor(8831994);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 2) % 128;
                    int dYKeo = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;
                    g.setColor(2002158);
                    g.fillRect(0, dYKeo, CScreen.w, CScreen.h - dYKeo, false);
                    for (int i = 0; i <= Background.nBgX + 1; ++i) {
                        g.drawImage(Background.haLongbg, -dXKeo + i * 128, dYKeo, mGraphics.LEFT | mGraphics.VCENTER,
                                false);
                    }
                    g.drawImage(Background.sun, Background.sunX, dYKeo - 100, 0, false);
                    g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
                    this.drawCloud(g);
                    break;
                }
                g.setColor(8438010);
                g.fillRect(0, 0, CScreen.w, CScreen.h, false);
                break;
            }
            case 1: {
                g.setColor(8180459);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 2) % 64;
                    int dYKeo = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;
                    for (int i = 0; i <= Background.nBgX + 1; ++i) {
                        g.drawImage(Background.canhdong, -dXKeo + i * 64, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    g.drawImage(Background.sun, Background.sunX, dYKeo - 200, 0, false);
                    g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
                    this.drawCloud(g);
                    break;
                }
                break;
            }
            case 2: {
                g.setColor(8180459);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 2) % 64;
                    int dYKeo = CScreen.h - (Camera.y + CScreen.h >> 2) + 150 + this.yBackGr;
                    for (int i = 0; i <= Background.nBgX + 1; ++i) {
                        g.drawImage(Background.bang, -dXKeo + i * 64, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
                    }
                    g.drawImage(Background.sun, Background.sunX, dYKeo - 200, 0, false);
                    g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
                    this.drawCloud(g);
                    g.setColor(18797);
                    g.fillRect(0, dYKeo, CCanvas.width, 500, false);
                    break;
                }
                break;
            }
            case 3: {
                g.setColor(8711932);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 2) % 72;
                    int dYKeo = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;
                    for (int i = 0; i <= Background.nBgX + 1; ++i) {
                        g.drawImage(Background.rungRam, -dXKeo + i * 72, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    break;
                }
                break;
            }
            case 4: {
                g.setColor(14282750);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 3) % 241;
                    int dXKeo2 = (Camera.x >> 2) % 226;
                    int dYKeo2 = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back02, -dXKeo + j * 241, dYKeo2 - 5, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    for (int j = 0; j <= Background.nBgX2 + 1; ++j) {
                        g.drawImage(Background.back01, -dXKeo2 + j * 226, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    g.setColor(7434609);
                    g.fillRect(0, dYKeo2, CCanvas.width, 100, false);
                    g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
                    this.drawCloud(g);
                    break;
                }
                break;
            }
            case 5: {
                g.setColor(16775152);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 3) % 241;
                    int dXKeo2 = (Camera.x >> 2) % 241;
                    int dYKeo2 = CScreen.h - Camera.y / 2 + this.yBackGr;
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back04, -dXKeo + j * 241, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back03, -dXKeo2 + j * 241, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    g.setColor(7905231);
                    g.fillRect(0, dYKeo2, CCanvas.width, 100, false);
                    break;
                }
                break;
            }
            case 6: {
                g.setColor(16706268);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 3) % 238;
                    int dXKeo2 = (Camera.x >> 2) % 225;
                    int dYKeo2 = CScreen.h - Camera.y / 2 + this.yBackGr;
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back06, -dXKeo + j * 238, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back05, -dXKeo2 + j * 225, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    g.setColor(10268132);
                    g.fillRect(0, dYKeo2, CCanvas.width, 600, false);
                    g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
                    this.drawCloud(g);
                    break;
                }
                break;
            }
            case 10: {
                g.setColor(7106965);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 3) % 238;
                    int dXKeo2 = (Camera.x >> 2) % 225;
                    int dYKeo2 = CScreen.h - Camera.y / 2 + this.yBackGr;
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back12, -dXKeo + j * 238, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back11, -dXKeo2 + j * 225, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    g.setColor(3093573);
                    g.fillRect(0, dYKeo2, CCanvas.width, 600, false);
                    g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
                    break;
                }
                break;
            }
            case 9: {
                g.setColor(16752448);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 3) % 238;
                    int dXKeo2 = (Camera.x >> 2) % 225;
                    int dYKeo2 = CScreen.h - Camera.y / 2 + this.yBackGr;
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back06, -dXKeo + j * 238, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back05, -dXKeo2 + j * 225, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    g.setColor(12373247);
                    g.fillRect(0, dYKeo2, CCanvas.width, 100, false);
                    g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
                    this.drawCloud(g);
                    break;
                }
                break;
            }
            case 7: {
                g.setColor(15267327);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 3) % 269;
                    int dXKeo2 = (Camera.x >> 2) % 368;
                    int dYKeo2 = CScreen.h - Camera.y / 3 + this.yBackGr - 100;
                    int dYKeo3 = CScreen.h - Camera.y / 2 + this.yBackGr;
                    for (int k = 0; k <= Background.nBgX + 1; ++k) {
                        g.drawImage(Background.back08, -dXKeo + k * 269, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    for (int k = 0; k <= Background.nBgX + 1; ++k) {
                        g.drawImage(Background.back08, -dXKeo + k * 269 + 30, dYKeo2 + 150,
                                mGraphics.LEFT | mGraphics.BOTTOM, false);
                    }
                    for (int k = 0; k <= Background.nBgX + 1; ++k) {
                        g.drawImage(Background.back07, -dXKeo2 + k * 368 + 50, dYKeo3,
                                mGraphics.LEFT | mGraphics.BOTTOM, false);
                    }
                    break;
                }
                break;
            }
            case 12: {
                if (CCanvas.gameTick % 200 == 0) {
                    this.boltActive = true;
                }
                g.setColor(530454);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 4) % 108;
                    int dXKeo2 = (Camera.x >> 3) % 108;
                    int dYKeo2 = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back15, -dXKeo + j * 108, dYKeo2, mGraphics.LEFT | mGraphics.VCENTER,
                                false);
                    }
                    if (this.boltActive) {
                        ++this.tBolt;
                        if (this.tBolt == 10) {
                            this.tBolt = 0;
                            this.boltActive = false;
                        }
                        if (this.tBolt % 2 == 0) {
                            g.setColor(16777215);
                            g.fillRect(0, dYKeo2 - 60, CCanvas.width, 130, false);
                        }
                    }
                    if (this.snow != null) {
                        g.setClip(0, dYKeo2 - 50, 1000, 120);
                        this.snow.paintOnlySmall(g);
                        g.setClip(0, 0, 2000, 2000);
                    }
                    g.setClip(0, dYKeo2 - 50, 1000, 100);
                    g.setClip(0, 0, 2000, 2000);
                    for (int j = 0; j <= Background.nBgX + 1; ++j) {
                        g.drawImage(Background.back14, -dXKeo2 + j * 108, dYKeo2, mGraphics.LEFT | mGraphics.VCENTER,
                                false);
                    }
                    g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
                    break;
                }
                break;
            }
            case 13: {
                g.setColor(8229794);
                g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 2) % 144;
                    int dXKeo3 = (Camera.x >> 2) % 176;
                    int dXKeo4 = (Camera.x >> 3) % 69;
                    int dYKeo4 = CScreen.h - Camera.y / 3 + this.yBackGr;
                    int dYKeo5 = CScreen.h - Camera.y / 4 + this.yBackGr - 80;
                    for (int l = 0; l <= CCanvas.width / 69 + 1; ++l) {
                        g.drawImage(Background.map_spider_layout, -dXKeo4 + l * 69, dYKeo5,
                                mGraphics.LEFT | mGraphics.BOTTOM, false);
                    }
                    g.setColor(9728620);
                    g.fillRect(0, dYKeo5, CCanvas.width, 300, false);
                    for (int l = 0; l <= CCanvas.width / 144 + 1; ++l) {
                        g.drawImage(Background.rock_down, -dXKeo + l * 144, dYKeo4, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    g.setColor(3223857);
                    g.fillRect(0, -500, CCanvas.width, -Camera.y / 3 + 120 + 500 - 88, false);
                    for (int l = 0; l <= CCanvas.width / 176 + 1; ++l) {
                        g.drawImage(Background.rock_up, -dXKeo3 + l * 176, -Camera.y / 3 + 120,
                                mGraphics.LEFT | mGraphics.BOTTOM, false);
                    }
                    g.setColor(4996403);
                    g.fillRect(0, dYKeo4, CCanvas.width, 300, false);
                    break;
                }
                break;
            }
            case 14: {
                g.setColor(6606845);
                g.fillRect(0, 0, CCanvas.width + 10, CCanvas.hieght + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    int dXKeo = (Camera.x >> 2) % 128;
                    int dXKeo2 = (Camera.x >> 3) % 128;
                    int dYKeo2 = CCanvas.hieght;
                    for (int j = 0; j <= CCanvas.width / 128 + 1; ++j) {
                        g.drawImage(Background.bg_cloud_1, -dXKeo2 + j * 128, dYKeo2 + 10,
                                mGraphics.LEFT | mGraphics.BOTTOM, false);
                    }
                    for (int j = 0; j <= CCanvas.width / 128 + 1; ++j) {
                        g.drawImage(Background.bg_cloud, -dXKeo + j * 128, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    this.drawSun(g);
                    break;
                }
                break;
            }
            case 15: {
                g.setColor(16694934);
                g.fillRect(0, 0, CCanvas.width + 10, CCanvas.hieght + 10, false);
                if (GameScr.curGRAPHIC_LEVEL != 2) {
                    this.drawSun2(g);
                    int dXKeo = (Camera.x >> 2) % 241;
                    int dXKeo2 = (Camera.x >> 3) % 241;
                    int dYKeo2 = CCanvas.hieght - Camera.y / 3 - this.yBackGr;
                    int dYKeo3 = CCanvas.hieght - 10 - Camera.y / 4 - this.yBackGr;
                    for (int k = 0; k <= CCanvas.width / 241 + 1; ++k) {
                        g.drawImage(Background.back16, -dXKeo2 + k * 241, dYKeo3, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    g.setColor(7628133);
                    g.fillRect(0, dYKeo3, CCanvas.width, 200, false);
                    for (int k = 0; k <= CCanvas.width / 241 + 1; ++k) {
                        g.drawImage(Background.back17, -dXKeo + k * 241, dYKeo2, mGraphics.LEFT | mGraphics.BOTTOM,
                                false);
                    }
                    g.setColor(2491910);
                    g.fillRect(0, dYKeo2, CCanvas.width, 200, false);
                    break;
                }
                break;
            }
        }
        g.translate(-Camera.x, -Camera.y);
    }

    public void drawSun(mGraphics g) {
        g.drawImage(Background.sun, Background.sunX, Background.sunY, 0, false);
    }

    public void drawSun2(mGraphics g) {
        g.drawImage(Background.sun2, Background.sunX, Background.sunY, 0, false);
    }

    public void drawWave(mGraphics g) {
        if (GameScr.curGRAPHIC_LEVEL != 0) {
            return;
        }
        g.setColor(16777215);
        for (int i = 0; i < Background.nWave; ++i) {
            g.drawLine(Background.wavex[i], Background.wavey[i], Background.wavex[i] + Background.length[i],
                    Background.wavey[i], false);
        }
    }

    private void drawCloud(mGraphics g) {
        for (int i = 0; i < this.cloudx.length; ++i) {
            g.drawImage(Background.cloud, this.cloudx[i], this.cloudy[i] + this.yCloud, 0, false);
        }
    }

    public static void drawWater(byte WaterOrGlass, mGraphics g) {
        int start = 0;
        int end = 0;
        if (WaterOrGlass == 0) {
            start = Camera.x / 24;
            end = (Camera.x + CScreen.w) / 24;
            for (int i = start; i <= end; ++i) {
                Background.waterSp.drawFrame((CCanvas.gameTick % 8 <= 4) ? 1 : 0, i * 24,
                        Background.waterY - 12 + Background.inWater.image.getHeight() / 2, 0, 0, g);
                g.drawImage(Background.inWater, i * 24,
                        Background.waterY - 12 + 24 + Background.inWater.image.getHeight() / 2, 0, false);
            }
        } else if (WaterOrGlass == 1) {
            start = Camera.x / 64;
            end = (Camera.x + CScreen.w) / 64;
            for (int i = start; i <= end; ++i) {
                g.drawImage(Background.co, i * 64, Background.glassY, 0, false);
            }
        } else if (WaterOrGlass == 2) {
            start = Camera.x / 64;
            end = (Camera.x + CScreen.w) / 64;
            for (int i = start; i <= end; ++i) {
                g.drawImage(Background.co2, i * 64, Background.glassY, 0, false);
            }
        }
    }

    public static void paintTree(mGraphics g) {
        for (int i = 0; i < 7; ++i) {
            if (Background.xT[i] >= 0 && Background.xT[i] <= CCanvas.width) {
                g.drawImage(Background.b, Background.xT[i], Background.yT[i], mGraphics.HCENTER | mGraphics.VCENTER,
                        false);
            }
            for (int j = 0; j < (CCanvas.hieght - Background.yT[i]) / 21 + 1; ++j) {
                g.drawImage(Background.a, Background.xT[i],
                        Background.yT[i] + j * 21 + Background.b.image.getHeight() / 2,
                        mGraphics.TOP | mGraphics.HCENTER, false);
            }
        }
    }

    public static void paintMenuBackGround(mGraphics g) {
        g.setColor(6606845);
        g.fillRect(0, 0, CCanvas.width, CCanvas.hieght, false);
        for (int i = 0; i <= CCanvas.width; i += 32) {
            g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
        }
    }

    public void onClearMap() {
    }

    static {
        Background.isLoadImage = false;
        try {
            FilePack filePack = new FilePack(CCanvas.getClassPathConfig(String.valueOf(CONFIG.PATH_MAP) + "bg"));
            PrepareScr.rockImg = filePack.loadImage("rock1.png");
            PrepareScr.rock2Img = filePack.loadImage("rock2.png");
            PrepareScr.glassFly = filePack.loadImage("cobay.png");
            PrepareScr.cloud1 = filePack.loadImage("cloud1.png");
            PrepareScr.chickenHair = filePack.loadImage("longga.png");
            Background.cloud = filePack.loadImage("cl2.png");
            Background.sun = filePack.loadImage("sun0.png");
            Background.sun2 = filePack.loadImage("sun1.png");
            Background.water = filePack.loadImage("wts.png");
            Background.co = filePack.loadImage("co.png");
            Background.logo = filePack.loadImage("lg.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.out("===> isData");
                }
            });
            CPlayer.web = filePack.loadImage("web.png");
            Background.co2 = filePack.loadImage("co2.png");
            Background.inWater = filePack.loadImage("inWater.png");
            Background.may = new mImage[3];
            PrepareScr.imgSun = filePack.loadImage("sun0.png");
            PrepareScr.imgCloud = new mImage[3];
            for (int i = 0; i < 3; ++i) {
                PrepareScr.imgCloud[i] = filePack.loadImage("cl" + (i + 1) + ".png");
            }
            Background.balloon = filePack.loadImage("miniballoon.png");
            Background.a = filePack.loadImage("a.png");
            Background.b = filePack.loadImage("b.png");
            Background.bigBalloon = filePack.loadImage("bigballoon.png");
            Background.mocxich = filePack.loadImage("mocxich.png");
            Background.stone = filePack.loadImage("stone.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Background.nWave = 10;
        Background.wavex = new int[Background.nWave];
        Background.wavex2 = new int[Background.nWave];
        Background.wavey = new int[Background.nWave];
        Background.length = new int[Background.nWave];
        Background.delay = new int[] { 1, 1, 10, 10, 20, 20, 30, 30, 40, 40, 40 };
        Background.xT = new int[] { CCanvas.width / 2 - 150, CCanvas.width / 2 - 110, CCanvas.width / 2 - 80,
                CCanvas.width / 2 - 10, CCanvas.width / 2 + 90, CCanvas.width / 2 + 110, CCanvas.width / 2 + 140 };
        int dx = (CCanvas.hieght >= 320) ? (CCanvas.hieght - 320) : (-(320 - CCanvas.hieght));
        Background.yT = new int[] { 221 + dx, 201 + dx, 240 + dx, 271 + dx, 223 + dx, 265 + dx, 243 + dx };
    }
}
