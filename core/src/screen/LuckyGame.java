package screen;

import item.Item;
import CLib.Image;
import model.IAction2;
import network.Session_ME;
import model.Font;
import model.CRes;
import CLib.mGraphics;
import coreLG.TerrainMidlet;
import network.GameService;
import model.IAction;
import model.Language;
import coreLG.CCanvas;
import model.FrameImage;
import CLib.mImage;
import java.util.Vector;
import network.Command;

public class LuckyGame extends CScreen {

    Command cmdStart;
    Command cmdBack;
    Command cmdMoneyLvl;
    int x;
    int y;
    int w;
    int h;
    int degree;
    int xd;
    int yd;
    int r;
    int stopAngle;
    int t;
    int g;
    int count;
    int d;
    int point;
    public int money;
    public int myMoney;
    public Vector gifts;
    int[] p;
    int[] color;
    boolean ready;
    boolean start;
    boolean stop;
    boolean hit;
    static mImage vong;
    static mImage kim;
    static mImage vong_tron;
    static FrameImage s_frBar;
    int[] moneyLvl;
    int _xu;
    int _luong;
    int hdegree1;
    int hdegree2;
    int hdegree3;
    int power;
    int min;
    int max;
    boolean isHoldFire;
    boolean _quay;
    int b;
    int tf;

    public LuckyGame() {
        this.x = CCanvas.width / 2 - 85;
        this.y = CCanvas.hieght / 2 - 85;
        this.w = 170;
        this.h = 170;
        this.r = this.w / 2;
        this.g = 0;
        this.count = 0;
        this.d = 0;
        this.gifts = new Vector();
        this.p = new int[] { 239, 203, 167, 131, 95, 59, 23, 0, 311, 275 };
        this.color = new int[] { 16315529, 9621318, 504643, 701933, 27571, 16315529, 9621318, 504643, 701933, 27571 };
        this.moneyLvl = new int[] { 200, 500, 1000, 1500, 2000 };
        this._xu = 1000;
        this._luong = 1;
        this.hdegree1 = 250;
        this.hdegree2 = 290;
        this.hdegree3 = 270;
        this.nameCScreen = " LuckyGame screen!";
        this.money = this._xu;
        this.cmdStart = new Command(Language.quay(), new IAction() {
            @Override
            public void perform() {
                doQuaySo(money);
            }
        });
        this.cmdMoneyLvl = new Command(Language.muctien(), new IAction() {
            @Override
            public void perform() {
                Vector menu = new Vector();
                Command cmdXu = new Command("1000 " + Language.xu(), new IAction() {
                    @Override
                    public void perform() {
                        money = _xu;
                    }
                });
                Command cmdLuong = new Command("1 " + Language.luong(), new IAction() {
                    @Override
                    public void perform() {
                        money = _luong;
                    }
                });
                menu.addElement(cmdXu);
                menu.addElement(cmdLuong);
                CCanvas.menu.startAt(menu, 0);
            }
        });
        this.cmdBack = new Command(Language.back(), new IAction() {
            @Override
            public void perform() {
                CCanvas.menuScr.show();
            }
        });
        if (!CCanvas.isTouch) {
            this.center = this.cmdStart;
        }
        this.right = this.cmdBack;
        this.left = this.cmdMoneyLvl;
        if (this.y + this.w > CCanvas.hieght - LuckyGame.cmdH) {
            this.y = 5;
        }
    }

    public void doQuaySo(final int money) {
        CCanvas.startYesNoDlg(String.valueOf(Language.bancomuon()) + money
                + ((money == this._xu) ? Language.xu() : Language.luong()) + "?", new IAction() {
                    @Override
                    public void perform() {
                        if (money == _xu) {
                            GameService.gI().sendRulet((byte) 0);
                            if (money < 1000) {
                                power = 0;
                                _quay = false;
                            }
                        }
                        if (money == _luong) {
                            GameService.gI().sendRulet((byte) 1);
                            if (money < 1) {
                                power = 0;
                                _quay = false;
                            }
                        }
                        CCanvas.startOKDlg(Language.pleaseWait());
                        myMoney -= money;
                    }
                });
    }

    @Override
    public void show(CScreen lastScreen) {
        LuckyGame.lastSCreen = lastScreen;
        this.money = this._xu;
        this.myMoney = TerrainMidlet.myInfo.xu;
        super.show();
    }

    public void getGifts(Vector g, int luckyNumb) {
        this.gifts = g;
        this.point = luckyNumb;
        this.center = null;
        this.right = null;
        this.left = null;
        this.ready = true;
        this.degree = 0;
        this.stopAngle = 0;
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        int dx1 = 0;
        int dy1 = 0;
        int dx2 = 0;
        int dy2 = 0;
        int dx3 = 0;
        int dy3 = 0;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        int x3 = 0;
        int y3 = 0;
        dx1 = (2 * this.r / 8 - 5) * CRes.cos(CRes.fixangle(this.hdegree1)) >> 10;
        dy1 = -((2 * this.r / 8 - 5) * CRes.sin(CRes.fixangle(this.hdegree1))) >> 10;
        dx2 = (2 * this.r / 8 - 5) * CRes.cos(CRes.fixangle(this.hdegree2)) >> 10;
        dy2 = -((2 * this.r / 8 - 5) * CRes.sin(CRes.fixangle(this.hdegree2))) >> 10;
        dx3 = 3 * this.r / 5 * CRes.cos(CRes.fixangle(this.hdegree3)) >> 10;
        dy3 = -(3 * this.r / 5 * CRes.sin(CRes.fixangle(this.hdegree3))) >> 10;
        x1 = this.x + this.w / 2 + dx1 + 7;
        y1 = this.y + this.w / 2 + dy1 - 15;
        x2 = this.x + this.w / 2 + dx2;
        y2 = this.y + this.w / 2 + dy2;
        x3 = this.x + this.w / 2 + dx3;
        y3 = this.y + this.w / 2 + dy3;
        g.drawImage(LuckyGame.vong_tron, this.x + this.w / 2 + 5, this.y + this.w / 2 + 5, 3, true);
        for (int i = 0; i < 10; ++i) {
            if (this.degree > 360) {
                this.degree -= 360;
            }
            int deg = this.degree + i * 36 + 18;
            int deg2 = this.degree + i * 36;
            int DX = 4 * this.r / 5 * CRes.cos(CRes.fixangle(deg)) >> 10;
            int DY = -(4 * this.r / 5 * CRes.sin(CRes.fixangle(deg))) >> 10;
            int X = this.x + this.w / 2 + DX;
            int Y = this.y + this.w / 2 + DY - Font.normalFont.getHeight() / 2;
            int DX2 = 4 * this.r / 5 * CRes.cos(CRes.fixangle(deg2)) >> 10;
            g.drawRegion(LuckyGame.vong, 0, 0, 90, 90, 0, this.x - 5, this.y - 5, 0, true);
            g.drawRegion(LuckyGame.vong, 0, 0, 90, 90, 2, this.x + 90 - 5, this.y - 5, 0, true);
            g.drawRegion(LuckyGame.vong, 0, 0, 90, 90, 1, this.x - 5, this.y + 90 - 5, 0, true);
            g.drawRegion(LuckyGame.vong, 0, 0, 90, 90, 7, this.x + 90 - 5, this.y + 90 - 5, 0, true);
            if (this.start || this.stop) {
                int tam = (this.count >= 10) ? 15 : (this.count + 1);
                if (DX2 <= 0 && DX2 + tam >= 0) {
                    this.hit = true;
                }
            }
        }
        for (int i = 0; i < 10; ++i) {
            if (this.degree > 360) {
                this.degree -= 360;
            }
            int deg = this.degree + i * 36 + 18;
            int deg2 = this.degree + i * 36;
            int DX = 4 * this.r / 5 * CRes.cos(CRes.fixangle(deg)) >> 10;
            int DY = -(4 * this.r / 5 * CRes.sin(CRes.fixangle(deg))) >> 10;
            int X = this.x + this.w / 2 + DX;
            int Y = this.y + this.w / 2 + DY - Font.normalFont.getHeight() / 2;
            if (this.gifts != null && this.gifts.size() != 0) {
                Gift gift = (Gift) this.gifts.elementAt(i);
                gift.paintGift(g, X, Y);
            }
        }
        g.drawImage(LuckyGame.kim, x1, y1, 3, true);
        if (CCanvas.h >= 200) {
            Font.normalFont.drawString(g, String.valueOf(TerrainMidlet.myInfo.xu) + " " + Language.xu(), 5,
                    CCanvas.h - LuckyGame.cmdH - 40, 0);
            Font.normalFont.drawString(g, String.valueOf(TerrainMidlet.myInfo.luong) + " " + Language.luong(), 5,
                    CCanvas.h - LuckyGame.cmdH - 20, 0);
        }
        int yF = 0;
        int xF = CCanvas.w / 2;
        if (CCanvas.h > 220) {
            yF = 10;
        } else {
            yF = CCanvas.h / 2 - 30;
        }
        if (CCanvas.w >= 320) {
            xF = 35;
            yF = CCanvas.h / 2 - 30;
        }
        LuckyGame.s_frBar.drawFrame(3, xF, yF, 3, 0, g);
        LuckyGame.s_frBar.fillFrame(2, xF, yF, this.power * 100 / 200, 3, 0, g, false);
        super.paint(g);
    }

    public void getMinMax(int point) {
        this.min = Math.abs(this.p[point]);
        this.max = this.min + ((point != 7) ? 26 : 13);
    }

    public void getResult() {
        Gift gf = (Gift) this.gifts.elementAt(this.point);
        this.myMoney = TerrainMidlet.myInfo.xu;
        CCanvas.startOKDlg(gf.info, new IAction() {
            @Override
            public void perform() {
                _quay = false;
            }
        });
    }

    public void releasePoint() {
        if (this.isHoldFire) {
            this.isHoldFire = false;
            if (this.power > 20) {
                if (this.money == this._xu) {
                    GameService.gI().sendRulet((byte) 0);
                    this._quay = true;
                }
                if (this.money == this._luong) {
                    this._quay = true;
                    GameService.gI().sendRulet((byte) 1);
                }
            }
        }
        if (this.money == this._xu && TerrainMidlet.myInfo.xu < 1000) {
            this.power = 0;
            this._quay = false;
            this.isHoldFire = false;
        }
        if (this.money == this._luong && TerrainMidlet.myInfo.luong < 1) {
            this.power = 0;
            this._quay = false;
            this.isHoldFire = false;
        }
    }

    public void ready() {
        ++this.t;
        if (this.t == 20) {
            this.start = true;
            this.ready = false;
            this.t = 0;
            this.getMinMax(this.point);
            this.b = CRes.random(15, 18);
        }
    }

    public boolean other() {
        return this.point == 7
                && ((this.degree >= this.min && this.degree <= this.max) || (this.degree >= 347 && this.degree <= 360));
    }

    public void start() {
        if (this.count == 19) {
            this.power -= 2;
            if (this.power <= 0) {
                if ((this.degree >= this.min && this.degree <= this.max) || this.other()) {
                    this.count = 19;
                    this.stopAngle = this.degree;
                    this.d = 0;
                    this.start = false;
                    this.stop = true;
                    this.power = 0;
                } else {
                    this.degree += 19;
                }
            } else {
                this.degree += 19;
            }
        } else {
            this.degree += this.count;
            if (this.count == this.b) {
                if (CCanvas.gameTick % 1 == 0) {
                    ++this.count;
                }
            } else {
                ++this.count;
            }
        }
    }

    public void stop() {
        if (this.d < 2850) {
            this.d += this.count;
            this.degree += this.count;
            ++this.g;
            if (this.g == 15) {
                this.g = 0;
                --this.count;
            }
        } else {
            int a = (this.t < 15) ? 2 : 4;
            if (CCanvas.gameTick % a == 0) {
                ++this.t;
                ++this.degree;
            }
            if (this.t == 30) {
                this.t = 0;
                this.d = 0;
                this.count = 0;
                this.stop = false;
                this.center = this.cmdStart;
                this.right = this.cmdBack;
                this.left = this.cmdMoneyLvl;
                this.getResult();
                Session_ME.receiveSynchronized = 0;
            }
        }
    }

    public void hit() {
        this.hit = false;
        if (this.d >= 2850) {
            return;
        }
        int tam = this.count + 1;
        if (tam < 3) {
            tam = 3;
        }
        this.hdegree1 += tam;
        if (this.hdegree1 > 260) {
            this.hdegree1 = 260;
        }
        this.hdegree2 += tam;
        if (this.hdegree2 >= 300) {
            this.hdegree2 = 300;
        }
        this.hdegree3 += tam;
        if (this.hdegree3 >= 280) {
            this.hdegree3 = 280;
        }
    }

    public void arrowUpdate() {
        if (this.hdegree1 != 250) {
            this.hdegree1 -= 2;
        }
        if (this.hdegree1 < 250) {
            this.hdegree1 = 250;
        }
        if (this.hdegree2 != 290) {
            this.hdegree2 -= 2;
        }
        if (this.hdegree2 < 290) {
            this.hdegree2 = 290;
        }
        if (this.hdegree3 != 270) {
            this.hdegree3 -= 2;
        }
        if (this.hdegree3 < 270) {
            this.hdegree3 = 270;
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.ready) {
            this.ready();
        }
        if (this.start) {
            this.start();
        }
        if (this.stop) {
            this.stop();
        }
        if (this.hit) {
            this.hit();
        }
        if (this.power < 0) {
            this.power = 0;
        }
        if (this.power > 200) {
            this.power = 200;
        }
        this.arrowUpdate();
        LuckyGame.lastSCreen.update();
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
        this.releasePoint();
    }

    @Override
    public void onPointerHold(int x, int y2, int index) {
        super.onPointerHold(x, y2, index);
        if (!CCanvas.isTouch) {
            if (!this._quay && CCanvas.keyHold[5]) {
                this.isHoldFire = true;
                this.power += 5;
                if (this.power > 200) {
                    this.power = 200;
                }
            }
            if (!CCanvas.keyHold[5]) {
                this.releasePoint();
            }
        }
        if (CCanvas.isTouch && !this._quay
                && CCanvas.isPointer(0, 0, CCanvas.width, CCanvas.hieght - LuckyGame.cmdH, index)) {
            this.isHoldFire = true;
            this.power += 5;
            if (this.power > 200) {
                this.power = 200;
            }
        }
    }

    static {
        try {
            mImage.createImage("/vong.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    LuckyGame.vong = new mImage((Image) object);
                }
            });
            mImage.createImage("/kim.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    LuckyGame.kim = new mImage((Image) object);
                }
            });
            mImage.createImage("/vong_tron.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    LuckyGame.vong_tron = new mImage((Image) object);
                }
            });
            mImage.createImage("/gui/barMove.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    LuckyGame.s_frBar = new FrameImage((Image) object, 53, 12, false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Gift {

        byte type;
        byte id;
        int n;
        public String info;

        public Gift(byte type, byte id, int n) {
            this.type = type;
            this.id = id;
            this.n = n;
            if (type == 0) {
                this.info = String.valueOf(Language.xinchucmung()) + n + " item " + Item.ITEM_NAME[id] + ".";
            }
            if (type == 1) {
                this.info = String.valueOf(Language.xinchucmung()) + n + Language.xu() + ".";
            }
            if (type == 2) {
                this.info = String.valueOf(Language.xinchucmung()) + n + "XP.";
            }
            if (type == 3) {
                this.info = Language.lansau();
            }
        }

        public void paintGift(mGraphics g, int x, int y) {
            if (this.type == 0) {
                Font.borderFont.drawString(g, new StringBuilder(String.valueOf(this.n)).toString(), x, y + 10, 3);
            }
            if (this.type == 1) {
                Font.borderFont.drawString(g, String.valueOf(this.n) + Language.xu(), x, y, 3);
            }
            if (this.type == 2) {
                Font.borderFont.drawString(g, String.valueOf(this.n) + "XP", x, y, 3);
            }
            if (this.type == 3) {
                return;
            }
        }
    }
}
