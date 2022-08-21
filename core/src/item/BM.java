package item;

import network.GameService;
import CLib.mGraphics;
import player.CPlayer;
import effect.Explosion;
import model.Position;
import effect.Tornado;
import map.MM;
import player.PM;
import coreLG.CCanvas;
import CLib.Image;
import model.IAction2;
import Equipment.PlayerEquip;
import model.CRes;
import screen.GameScr;
import model.FrameImage;
import java.util.Vector;
import CLib.mImage;

public class BM {

    public static mImage airFighter;
    public Vector<Bullet> bullets;
    public byte numShoot;
    int x;
    int y;
    public static int angle;
    public static byte force;
    byte type;
    byte whoShot;
    byte delayBullCound;
    byte delayBullType;
    int nDelayBull;
    int nLazerDelay;
    int nMeteorDelay;
    int timedelay;
    boolean isEndDelayBull;
    public static boolean active;
    static boolean isActiveAirFly;
    static boolean isActiveBomBay;
    static boolean isActiveLazer;
    public static boolean isActiveTornado;
    static boolean isAciveExplore;
    static boolean isActiveEgg;
    static boolean isActiveMeteor;
    static boolean isActive4Missile;
    static boolean isActiveMissileRain;
    public int nBull;
    static int nArray;
    public static int nBum;
    public static int[] bumX;
    public static int[] bumY;
    public static int[] bumX_Last;
    public static int[] bumY_Last;
    static final int bombangle = 0;
    public static int airPlaneStartVx;
    static final byte bombForce = 5;
    public static final int rangeActive = 165;
    public static final int airPlaneStartX = 400;
    public static final int airPlaneStartY = 320;
    public static int airPlaneX;
    public static int airPlaneY;
    int airPlaneVx;
    static int lazerX;
    static int lazerY;
    static int tonardoX;
    static int tonardoY;
    public static Vector<Tornado> vTornado;
    static int exploreX;
    static int exploreY;
    static byte exploreForce;
    static int exploreAngel;
    static int exploreVx;
    static int exploreVy;
    public static boolean allSendENDSHOOT;
    public static boolean shootNextStep;
    static int eggX;
    static int eggY;
    static int meteorX;
    static int meteorY;
    static int meteorDesX;
    static int meteorDesY;
    static int missileXS;
    static int missileYS;
    static int missileXD;
    static int missileYD;
    static int missileP;
    static int missleAngle;
    static int mRainX;
    static int mRainY;
    int nWasShoot;
    static int xChicken;
    static int yChicken;
    public static int nOrbit;
    public static boolean activeUFOLazer;
    public static Vector<Position> lazerPosition;
    public FrameImage[][] frameBulletSpecial;
    public mImage bulletChicken;
    short[][] xPaint;
    short[][] yPaint;
    short[][] xHit;
    short[][] yHit;
    int bIndex;
    public static byte force2;
    public static boolean isBombBalloon;
    public byte critical;
    private int idBullet;
    boolean isDouble;
    boolean endShoot;

    public BM() {
        this.bullets = new Vector<Bullet>();
        this.numShoot = 0;
        this.delayBullCound = -1;
        this.delayBullType = -1;
        this.nDelayBull = 1;
        this.nLazerDelay = 0;
        this.nMeteorDelay = 0;
        this.timedelay = 0;
        this.isEndDelayBull = true;
        this.frameBulletSpecial = new FrameImage[58][2];
        this.bIndex = 0;
        this.isDouble = false;
        BM.active = false;
        this.nBull = 0;
        this.numShoot = 0;
    }

    public void setBullType(byte critical, final byte whoShot, final byte type, final short[][] xPaint, final short[][] yPaint, final byte nShoot, final byte force2, final short[][] xHit, final short[][] yHit, final int idBullet) {
        this.type = type;
        this.xPaint = xPaint;
        this.yPaint = yPaint;
        this.xHit = xHit;
        this.yHit = yHit;
        this.critical = critical;
        this.idBullet = idBullet;
        BM.xChicken = this.x;
        BM.yChicken = this.y;
        this.whoShot = whoShot;
        BM.force2 = force2;
        BM.active = true;
        this.numShoot = nShoot;
        if (this.numShoot == 2) {
            this.isDouble = true;
        }
        this.nWasShoot = 1;
        this.bIndex = 0;
        BM.allSendENDSHOOT = false;
        BM.shootNextStep = true;
        this.endShoot = false;
        BM.isBombBalloon = false;
        if (type == 43) {
            BM.isBombBalloon = true;
        }
    }

    private void createShootInfo() {
        System.out.println("===================>BM CReateBullet nShoot: " + this.type);
        this.isEndDelayBull = true;
        BM.nBum = 0;
        for (int i = 0; i < BM.nArray; ++i) {
            BM.bumX[i] = -1;
            BM.bumY[i] = -1;
            BM.bumX_Last[i] = -1;
            BM.bumY_Last[i] = -1;
        }
        this.createBullet(this.type);
        boolean isFocusCAM = true;
        int camIndex = 0;
        switch (this.type) {
            case 0:
            case 32:
            case 40:
            case 41:
            case 48:
            case 49: {
                this.nBull = 1;
                break;
            }
            case 1: {
                this.delayBullType = 5;
                this.isEndDelayBull = false;
                this.nBull = ((this.critical == 0) ? 2 : 6);
                break;
            }
            case 2: {
                camIndex = 1;
                if (this.critical == 0) {
                    this.nBull = 3;
                }
                if (this.critical == 1) {
                    this.nBull = 7;
                    break;
                }
                break;
            }
            case 4: {
                BM.isActiveAirFly = false;
                BM.isActiveBomBay = false;
                BM.airPlaneX = 400;
                BM.airPlaneY = BM.airPlaneStartVx;
                this.nBull = 2;
                break;
            }
            case 5:
            case 36: {
                this.nBull = 1;
                break;
            }
            case 6: {
                camIndex = 1;
                this.nBull = 3;
                break;
            }
            case 7:
            case 31: {
                this.nBull = 1;
                break;
            }
            case 8: {
                this.nBull = 1;
                break;
            }
            case 9: {
                this.nBull = 4;
                break;
            }
            case 10: {
                if (this.critical == 0 || this.critical == 1) {
                    this.delayBullType = 3;
                    this.isEndDelayBull = false;
                    this.nBull = 3;
                    break;
                }
                break;
            }
            case 11: {
                this.delayBullType = 5;
                this.isEndDelayBull = false;
                this.nBull = 5;
                break;
            }
            case 16: {
                this.nBull = 7;
                break;
            }
            case 14: {
                this.nBull = 2;
                break;
            }
            case 13: {
                this.nBull = 1;
                break;
            }
            case 19: {
                this.nBull = 1;
                break;
            }
            case 23: {
                this.nBull = 8;
                break;
            }
            case 26: {
                this.nBull = 5;
                break;
            }
            case 17: {
                this.nBull = 1;
                break;
            }
            case 28: {
                this.nBull = 14;
                break;
            }
            case 30: {
                this.nBull = 1;
                break;
            }
            case 21: {
                this.nBull = 1;
                break;
            }
            case 25: {
                this.nBull = 1;
                break;
            }
            case 22: {
                this.nBull = 1;
                break;
            }
            case 33: {
                this.delayBullType = 5;
                this.isEndDelayBull = false;
                this.nBull = 5;
                break;
            }
            case 34: {
                this.nBull = 1;
            }
            case 35: {
                this.nBull = 1;
                break;
            }
            case 37: {
                this.nBull = 1;
                break;
            }
            case 42: {
                this.nBull = 1;
                break;
            }
            case 43: {
                this.delayBullType = 11;
                this.isEndDelayBull = false;
                this.nBull = 10;
                break;
            }
            case 44: {
                this.delayBullType = 3;
                this.isEndDelayBull = false;
                this.nBull = 15;
                break;
            }
            case 45: {
                this.nBull = 1;
                break;
            }
            case 47: {
                this.delayBullType = 2;
                this.isEndDelayBull = false;
                this.nBull = 5;
                break;
            }
            case 51: {
                this.nBull = 1;
                break;
            }
            case 50: {
                this.nBull = 1;
                break;
            }
            case 52: {
                this.nBull = 1;
                break;
            }
            case 54: {
                this.nBull = 1;
                break;
            }
            case 55: {
                this.nBull = 1;
                break;
            }
            case 56: {
                this.nBull = 3;
                break;
            }
            case 57: {
                this.nBull = 1;
                break;
            }
        }
        if (isFocusCAM && this.type != 43) {
            GameScr.cam.setBulletMode(this.bullets.elementAt(camIndex));
        }
        this.nDelayBull = this.nBull;
        this.delayBullCound = this.delayBullType;
    }

    private void createBullet(byte Type) {
        int a = this.nBull * (this.nWasShoot - 1);
        switch (Type) {
            case 0: {
                Bullet b = new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet);
                this.bullets.addElement(b);
                ++this.bIndex;
                break;
            }
            case 32:
            case 34:
            case 35:
            case 37:
            case 41:
            case 42:
            case 45:
            case 50:
            case 51:
            case 52:
            case 54:
            case 55:
            case 57: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
                ++this.bIndex;
                break;
            }
            case 48: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.xHit[this.bIndex], this.yHit[this.bIndex]));
                ++this.bIndex;
                break;
            }
            case 43: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
                ++this.bIndex;
                break;
            }
            case 10: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
                ++this.bIndex;
                break;
            }
            case 1: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
                ++this.bIndex;
                break;
            }
            case 33:
            case 44:
            case 47: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
                ++this.bIndex;
                break;
            }
            case 11: {
                CRes.out("ID BULLET= " + this.idBullet);
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
                ++this.bIndex;
                break;
            }
            case 2: {
                for (int m = (this.critical == 0) ? 3 : 7, i = 0; i < m; ++i) {
                    this.bullets.addElement(new Bullet(Type, this.xPaint[i + this.bIndex], this.yPaint[i + this.bIndex], this.whoShot, this.idBullet));
                }
                this.bIndex += 3;
                break;
            }
            case 56: {
                for (int j = 0; j < 3; ++j) {
                    this.bullets.addElement(new Bullet(Type, this.xPaint[j + this.bIndex], this.yPaint[j + this.bIndex], this.whoShot));
                }
                this.bIndex += 3;
                break;
            }
            case 3: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[1], this.yPaint[1], this.whoShot));
                GameScr.isDarkEffect = false;
                break;
            }
            case 4: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 5:
            case 36: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 6: {
                for (int j = 0; j < 3; ++j) {
                    this.bullets.addElement(new Bullet(Type, this.xPaint[j], this.yPaint[j], this.whoShot, this.idBullet));
                }
                break;
            }
            case 7:
            case 31: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 8: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 9: {
                for (int j = 0; j < 4; ++j) {
                    this.bullets.addElement(new Bullet(Type, this.xPaint[j + this.bIndex], this.yPaint[j + this.bIndex], this.whoShot, this.idBullet));
                }
                this.bIndex += 4;
                break;
            }
            case 16: {
                this.bullets.addElement(new Bullet((byte) 16, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 12: {
                for (int j = 1; j <= 6; ++j) {
                    this.bullets.addElement(new Bullet(Type, this.xPaint[j], this.yPaint[j], this.whoShot));
                }
                break;
            }
            case 14:
            case 40: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 15: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[1], this.yPaint[1], this.whoShot));
                break;
            }
            case 19: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
                ++this.bIndex;
                break;
            }
            case 49: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
                ++this.bIndex;
                break;
            }
            case 13:
            case 17: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
                ++this.bIndex;
                break;
            }
            case 20: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
                ++this.nBull;
                ++this.bIndex;
                break;
            }
            case 18: {
                if (!this.endShoot) {
                    for (int j = 0; j < 3; ++j) {
                        this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
                        ++this.bIndex;
                    }
                    break;
                }
                break;
            }
            case 21: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
                ++this.bIndex;
                break;
            }
            case 22: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
                break;
            }
            case 23: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 24: {
                for (int j = 1; j <= 7; ++j) {
                    this.bullets.addElement(new Bullet(Type, this.xPaint[j], this.yPaint[j], this.whoShot));
                }
                break;
            }
            case 25: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 26: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 27: {
                for (int j = 1; j <= 4; ++j) {
                    this.bullets.addElement(new Bullet(Type, this.xPaint[j], this.yPaint[j], this.whoShot));
                }
                break;
            }
            case 28: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
            case 29: {
                for (int j = 1; j <= 13; ++j) {
                    this.bullets.addElement(new Bullet(Type, this.xPaint[j], this.yPaint[j], this.whoShot));
                }
                break;
            }
            case 30: {
                this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
                break;
            }
        }
    }

    public void onInitSpecialBullet() {
        CCanvas.cutImage(PlayerEquip.bullets[7], 0, new IAction2() {
            @Override
            public void perform(Object object) {
                frameBulletSpecial[21][0] = new FrameImage((Image) object, 4);
            }
        });
        CCanvas.cutImage(PlayerEquip.bullets[3], this.idBullet, new IAction2() {
            @Override
            public void perform(Object object) {
                frameBulletSpecial[9][0] = new FrameImage((Image) object, 4);
            }
        });
        CCanvas.cutImage(PlayerEquip.bullets[8], this.idBullet, new IAction2() {
            @Override
            public void perform(Object object) {
            frameBulletSpecial[17][0] = new FrameImage((Image) object, 8);
            }
        });
        CCanvas.cutImage(PlayerEquip.bullets[6], this.idBullet, new IAction2() {
            @Override
            public void perform(Object object) {
                bulletChicken = new mImage((Image) object);
            }
        });
        this.frameBulletSpecial[7][0] = new FrameImage(Bullet.grenadeImg.image, 8);
        this.frameBulletSpecial[9][1] = new FrameImage(Bullet.chuoiImg2.image, 4);
        this.frameBulletSpecial[21][1] = new FrameImage(Bullet.boomerangBig.image, 4);
        this.frameBulletSpecial[2][0] = new FrameImage(PlayerEquip.bullets[2].image, 20, 20);
        this.frameBulletSpecial[2][1] = new FrameImage(PlayerEquip.bullets[2].image, 20, 20);
    }

    public void update() {
        try {
            for (int i = 0; i < this.bullets.size(); ++i) {
                this.bullets.elementAt(i).fixedUpdate();
            }
            if (!this.isEndDelayBull) {
                if (!BM.isBombBalloon) {
                    --this.delayBullCound;
                    if (this.delayBullCound == 0 && this.nDelayBull > 0) {
                        this.createBullet(this.type);
                        this.delayBullCound = this.delayBullType;
                        --this.nDelayBull;
                        if (this.nDelayBull <= 1) {
                            this.isEndDelayBull = true;
                        }
                    }
                } else {
                    CPlayer p = PM.p[this.whoShot];
                    if (this.xPaint[this.bIndex][0] <= p.x + 10 && this.xPaint[this.bIndex][0] >= p.x - 10) {
                        this.createBullet(this.type);
                        --this.nDelayBull;
                        if (this.nDelayBull <= 1) {
                            this.isEndDelayBull = true;
                        }
                    }
                }
            } else if (this.bullets.size() == 0 && this.nBull <= 0 && this.numShoot > 0 && !BM.allSendENDSHOOT) {
                this.createShootInfo();
                --this.numShoot;
                ++this.nWasShoot;
                if (this.nWasShoot == this.numShoot) {
                    this.nWasShoot = 0;
                }
            }
            if (BM.isActiveAirFly) {
                BM.airPlaneX += BM.airPlaneStartVx;
                if (!BM.isActiveBomBay && BM.airPlaneX >= this.x - 165) {
                    this.createBullet((byte) 3);
                    GameScr.cam.setBulletMode(this.bullets.elementAt(0));
                    BM.isActiveBomBay = true;
                }
                if (BM.airPlaneX >= MM.mapWidth) {
                    BM.isActiveAirFly = false;
                }
            }
            if (BM.isActiveLazer) {
                ++this.nLazerDelay;
                if (this.nLazerDelay == 10) {
                    GameScr.cam.setTargetPointMode(BM.lazerX, 100);
                }
                if (this.nLazerDelay == 20) {
                    this.nLazerDelay = 0;
                    BM.isActiveLazer = false;
                    CRes.out("======> CReate Bullet Lazer ");
                    this.createBullet((byte) 15);
                    GameScr.cam.setLazerMode(this.bullets.elementAt(0));
                }
            }
            if (BM.vTornado.size() != 0) {
                BM.isActiveTornado = true;
                for (int i = 0; i < BM.vTornado.size(); ++i) {
                    BM.vTornado.elementAt(i).update();
                }
            } else {
                BM.isActiveTornado = false;
            }
            if (BM.isAciveExplore) {
                BM.isAciveExplore = false;
                this.createBullet((byte) 18);
                GameScr.cam.setBulletMode(this.bullets.elementAt(0));
                this.bullets.elementAt(0).vx = BM.exploreVx;
                this.bullets.elementAt(0).vy = BM.exploreVy;
            }
            if (BM.isActiveEgg) {
                BM.isActiveEgg = false;
                this.createBullet((byte) 20);
                if (BM.eggX > -500 && BM.eggY > -500) {
                    GameScr.cam.setBulletMode(this.bullets.elementAt(this.bullets.size() - 1));
                }
            }
            if (BM.isActiveMeteor) {
                ++this.nMeteorDelay;
                if (this.nMeteorDelay == 20) {
                    this.nMeteorDelay = 0;
                    BM.isActiveMeteor = false;
                    this.createBullet((byte) 24);
                    GameScr.cam.setMeteorMode(this.bullets.elementAt(0));
                }
            }
            if (BM.isActive4Missile) {
                BM.isActive4Missile = false;
                this.createBullet((byte) 27);
                GameScr.cam.setBulletMode(this.bullets.elementAt(0));
            }
            if (BM.isActiveMissileRain) {
                BM.isActiveMissileRain = false;
                this.createBullet((byte) 29);
                GameScr.cam.setMRainMode(this.bullets.elementAt(0));
            }
            if (BM.activeUFOLazer && CCanvas.gameTick % 3 == 0) {
                if (BM.lazerPosition.size() != 0) {
                    Position pos = (Position) BM.lazerPosition.elementAt(0);
                    GameScr.sm.addLazer(pos.xF, pos.yF, pos.xT, pos.yT, 1);
                    int X = pos.xT + CRes.random(-35, 35);
                    int Y = pos.yT + CRes.random(-10, 10);
                    new Explosion(pos.xT, pos.yT, (byte) 9);
                    new Explosion(X, Y, (byte) 9);
                    new Explosion(X + CRes.random(-30, 30), Y + CRes.random(-10, 10), (byte) 9);
                    BM.lazerPosition.removeElement(pos);
                } else {
                    BM.activeUFOLazer = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.endShoot();
        }
    }

    public void removeAll(int x, final int y, final int x_Last, final int y_Last, final byte whoShot) {
        for (int i = 0; i < this.bullets.size(); ++i) {
            Bullet bull = this.bullets.elementAt(i);
            this.removeBullet(bull, true, x, y, x_Last, y_Last, whoShot);
        }
    }

    public void paint(mGraphics g) {
        for (int i = 0; i < this.bullets.size(); ++i) {
            this.bullets.elementAt(i).paint(g);
        }
        if (BM.isActiveAirFly) {
            g.drawImage(BM.airFighter, BM.airPlaneX, BM.airPlaneY + CCanvas.gameTick % 3, mGraphics.VCENTER | mGraphics.HCENTER, false);
        }
        if (BM.isActiveLazer && this.nLazerDelay > 5 && this.nLazerDelay < 20) {
            g.setColor(16771821);
            g.fillRect(BM.lazerX - 2, -100, 4, BM.lazerY + 100, false);
        }
        if (BM.isActiveTornado) {
            for (int i = 0; i < BM.vTornado.size(); ++i) {
                BM.vTornado.elementAt(i).paint(g);
            }
        }
    }

    public boolean isHaveEgg() {
        for (int i = 0; i < this.bullets.size(); ++i) {
            if (this.bullets.elementAt(i).type == 20) {
                return true;
            }
        }
        return false;
    }

    public void removeBullet(Bullet b, final boolean isExplode, final int x, final int y, final int x_Last, final int y_Last, final byte whoShot) {
        this.bullets.removeElement(b);
        if (b.type == 45) {
            this.endShoot();
            return;
        }
        if (this.bullets.size() > 0) {
            Bullet bull = this.bullets.elementAt(0);
            if (bull.type != 19 && bull.type != 43) {
                if (bull.type == 29) {
                    GameScr.cam.setMRainMode(bull);
                } else {
                    GameScr.cam.setBulletMode(bull);
                }
            }
        }
        if (!Bullet.isFlagBull(b.type) || this.type == 14) {
            ++BM.nBum;
            int n = BM.nBum - 1;
            if (isExplode) {
                BM.bumX[n] = x;
                BM.bumY[n] = y;
                BM.bumX_Last[n] = x_Last;
                BM.bumY_Last[n] = y_Last;
            } else {
                BM.bumX[n] = -1;
                BM.bumY[n] = -1;
                BM.bumX_Last[n] = -1;
                BM.bumY_Last[n] = -1;
            }
        }
        if (Bullet.isFlagBull(b.type)) {
            if (isExplode) {
                this.x = x;
                this.y = y;
            } else {
                this.nBull = 1;
            }
        }
        if (this.type == 19) {
            this.x = BM.xChicken;
            this.y = BM.yChicken;
        }
        --this.nBull;
        if (this.nBull == 0 && this.numShoot == 0) {
            if (whoShot == GameScr.myIndex) {
                BM.allSendENDSHOOT = true;
                BM.shootNextStep = true;
            }
            if (this.numShoot == 0) {
                this.endShoot();
            }
            GameService.gI().check_cross((byte) BM.nBum, BM.bumX_Last, BM.bumY_Last);
        }
    }

    public static void removeTornado() {
        BM.vTornado.removeAllElements();
    }

    public void tornadoTurnUpd() {
        if (BM.isActiveTornado) {
            for (int i = 0; i < BM.vTornado.size(); ++i) {
                Tornado tornado4 = BM.vTornado.elementAt(i);
                --tornado4.nturn;
            }
        }
    }

    public void endShoot() {
        BM.active = false;
        this.nBull = 0;
        this.numShoot = 0;
        this.endShoot = true;
        CPlayer.isStopFire = false;
        this.tornadoTurnUpd();
        CRes.out("END SHOOT");
        CCanvas.lockNotify = true;
        CCanvas.tNotify = 0;
        CPlayer.closeMirror = true;
    }

    public void activeAirplane(int X, final int Y) {
        BM.isActiveAirFly = true;
        BM.airPlaneX = X - 400;
        BM.airPlaneY = Y - 320;
        GameScr.cam.setTargetPointMode(X - 180, Y - 320);
        GameScr.isDarkEffect = false;
    }

    public void activeLazer(int X, final int Y) {
        BM.isActiveLazer = true;
        BM.lazerX = X;
        BM.lazerY = Y;
        GameScr.cam.setTargetPointMode(X, Y);
    }

    public void activeTornado(int X, final int Y) {
        BM.vTornado.addElement(new Tornado(X, Y, 3));
        BM.tonardoX = X;
        BM.tonardoY = Y;
    }

    public void activeExplore(int XD, final int YD, final int X, final int Y, final int VX, final int VY, final int force, final int angle) {
        BM.isAciveExplore = true;
        BM.exploreX = X;
        BM.exploreY = Y;
        BM.exploreVx = 0;
        BM.exploreVy = 0;
        BM.exploreForce = (byte) force;
        int dx2 = this.x - X;
        int dy2 = this.y - Y;
        int a2 = CRes.angle(dx2, dy2);
        BM.exploreForce = (byte) (force / 2);
        BM.exploreAngel = 180 - (angle + a2);
    }

    public void activeEgg(int X, final int Y) {
        BM.isActiveEgg = true;
        BM.eggX = X;
        BM.eggY = Y;
    }

    public void activeMortarBum(int x, final int y) {
        this.x = x;
        this.y = y - 500;
        this.createBullet((byte) 12);
    }

    public void active4Missle(int XSource, final int YSource, final int XDes, final int YDes) {
        BM.isActive4Missile = true;
        BM.missileXS = XSource;
        BM.missileYS = YSource;
        BM.missileXD = XDes;
        BM.missileYD = YDes;
        BM.missileP = ((BM.angle < 90 && BM.angle > -90) ? 1 : -1);
        BM.missleAngle = ((BM.missileP > 0) ? BM.angle : (180 - BM.angle));
    }

    public void activeMeteor(int X, final int Y, final int angle) {
        BM.isActiveMeteor = true;
        BM.meteorX = X;
        BM.meteorY = -30;
        BM.meteorDesX = X;
        BM.meteorDesY = Y;
    }

    public void activeMissleRain(int X, final int Y) {
        BM.isActiveMissileRain = true;
        BM.mRainX = X;
        BM.mRainY = Y;
    }

    public void onClear() {
    }

    static {
        BM.airFighter = GameScr.airFighter;
        BM.active = false;
        BM.nArray = 20;
        BM.nBum = 0;
        BM.bumX = new int[BM.nArray];
        BM.bumY = new int[BM.nArray];
        BM.bumX_Last = new int[BM.nArray];
        BM.bumY_Last = new int[BM.nArray];
        BM.airPlaneStartVx = 20;
        BM.vTornado = new Vector<>();
        BM.allSendENDSHOOT = false;
        BM.shootNextStep = true;
        BM.lazerPosition = new Vector<>();
    }
}
