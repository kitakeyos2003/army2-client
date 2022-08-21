package screen;

import model.CRes;
import model.Font;
import CLib.mGraphics;
import network.GameService;
import model.IAction;
import model.Language;
import model.PlayerInfo;
import coreLG.TerrainMidlet;
import coreLG.CCanvas;
import network.Command;
import CLib.mImage;

public class LevelScreen extends TabScreen {

    static mImage ability;
    static mImage plus;
    static mImage arrow;
    public static byte point;
    public static byte level;
    int select;
    short currPoint;
    short[] currAbility;
    byte[] deltaA;
    Command cmdSelect;
    public static final String[] strAbility;
    Command cmdLamlai;
    byte[] canUp;
    byte[] canDown;

    @Override
    public void show(CScreen lastScr) {
        super.show(lastScr);
        CCanvas.arrPopups.removeAllElements();
        this.currPoint = TerrainMidlet.myInfo.point;
        for (int i = 0; i < 5; ++i) {
            this.currAbility[i] = TerrainMidlet.myInfo.ability[i];
            this.canDown[i] = 0;
        }
        this.deltaA = new byte[5];
        PlayerInfo m = TerrainMidlet.myInfo;
        this.title = "Lvl " + m.level2 + ((m.level2Percen >= 0) ? "+" : "") + m.level2Percen + "%";
        if (m.point > 0) {
            for (int j = 0; j < 5; ++j) {
                this.canUp[j] = 1;
            }
        }
    }

    public void doClose() {
        for (int i = 0; i < 5; ++i) {
            this.canDown[0] = 0;
            this.canDown[0] = 0;
        }
        this.isClose = true;
    }

    public LevelScreen() {
        this.select = 0;
        this.currAbility = new short[5];
        this.deltaA = new byte[5];
        this.canUp = new byte[5];
        this.canDown = new byte[5];
        this.nameCScreen = "LevelScreen screen!";
        this.xPaint = CCanvas.width / 2 - 65;
        this.yPaint = (CCanvas.hieght - CScreen.cmdH) / 2 - 90;
        this.hTabScreen = 170;
        if (CCanvas.isTouch) {
            this.hTabScreen = 185;
        }
        this.right = new Command(Language.close(), new IAction() {
            @Override
            public void perform() {
                restartPoint();
                doClose();
            }
        });
        this.cmdSelect = new Command(Language.xacnhan(), new IAction() {
            @Override
            public void perform() {
                doFire();
            }
        });
        this.cmdLamlai = new Command(Language.lamlai(), new IAction() {
            @Override
            public void perform() {
                restartPoint();
                CCanvas.menu.showMenu = false;
            }
        });
        this.title = "";
        this.n = (CCanvas.isTouch ? 4 : 3);
        this.getW();
    }

    public void doFire() {
        CCanvas.startYesNoDlg(Language.areYouSure(), new IAction() {
            @Override
            public void perform() {
                GameService.gI().addPoint(deltaA);
                currPoint = TerrainMidlet.myInfo.point;
                for (int i = 0; i < 5; ++i) {
                    canDown[i] = 0;
                }
                if (TerrainMidlet.myInfo.point > 0) {
                    for (int i = 0; i < 5; ++i) {
                        canUp[i] = 1;
                    }
                }
                for (int i = 0; i < 5; ++i) {
                    deltaA[i] = 0;
                    canDown[i] = 0;
                }
                CCanvas.endDlg();
            }
        }, new IAction() {
            @Override
            public void perform() {
                restartPoint();
                CCanvas.endDlg();
            }
        });
    }

    public void restartPoint() {
        TerrainMidlet.myInfo.point = this.currPoint;
        if (this.currPoint == 0) {
            return;
        }
        for (int i = 0; i < 5; ++i) {
            short[] currAbility = this.currAbility;
            int n = i;
            short[] array = currAbility;
            int n2 = n;
            array[n2] -= this.deltaA[i];
            this.deltaA[i] = 0;
            this.canDown[i] = 0;
            this.canUp[i] = 1;
        }
    }

    public void doUp() {
        if (TerrainMidlet.myInfo.point > 0) {
            byte[] deltaA = this.deltaA;
            int select = this.select;
            byte[] array = deltaA;
            int n = select;
            ++array[n];
            PlayerInfo myInfo2;
            PlayerInfo myInfo = myInfo2 = TerrainMidlet.myInfo;
            --myInfo2.point;
            short[] currAbility = this.currAbility;
            int select2 = this.select;
            short[] array2 = currAbility;
            int n2 = select2;
            ++array2[n2];
            this.canDown[this.select] = 1;
        }
        if (TerrainMidlet.myInfo.point == 0) {
            for (int i = 0; i < 5; ++i) {
                this.canUp[i] = 0;
            }
        }
    }

    public void doDown() {
        int ability = TerrainMidlet.myInfo.ability[this.select];
        if (this.currAbility[this.select] > ability) {
            byte[] deltaA = this.deltaA;
            int select = this.select;
            byte[] array = deltaA;
            int n = select;
            --array[n];
            PlayerInfo myInfo2;
            PlayerInfo myInfo = myInfo2 = TerrainMidlet.myInfo;
            ++myInfo2.point;
            short[] currAbility = this.currAbility;
            int select2 = this.select;
            short[] array2 = currAbility;
            int n2 = select2;
            --array2[n2];
            this.canUp[this.select] = 1;
        }
        if (this.currAbility[this.select] == ability) {
            this.canDown[this.select] = 0;
        }
        if (TerrainMidlet.myInfo.point > 0) {
            for (int i = 0; i < 5; ++i) {
                this.canUp[i] = 1;
            }
        }
    }

    public static void paintLevelPercen(mGraphics g, int x, int y) {
        PlayerInfo m = TerrainMidlet.myInfo;
        g.setColor(1521982);
        g.fillRect(x, y, 102, 17, false);
        g.setColor(2378093);
        g.fillRect(x + 1, y + 1, 100, 15, false);
        int percen = m.level2Percen * 100 / 100;
        g.setColor(16767817);
        g.fillRect(x + 1, y + 1, percen, 15, false);
        Font.borderFont.drawString(g, String.valueOf(m.exp) + "/" + m.nextExp, x + 51, y, 2);
    }

    @Override
    public void paint(mGraphics g) {
        super.paint(g);
        int W = 122;
        int dis = 0;
        PlayerInfo m = TerrainMidlet.myInfo;
        int lv = m.level2;
        g.translate(-15, -33);
        dis = 30;
        int aa = CCanvas.isTouch ? 15 : 0;
        int bb = CCanvas.isTouch ? 10 : 0;
        for (int i = 0; i < 5; ++i) {
            g.drawRegion(LevelScreen.ability, 0, i * 16, 16, 16, 0, this.xPaint, this.yPaint + 57 + i * dis, 0, false);
            if (!CCanvas.isTouch) {
                if (i == this.select) {
                    Font.normalYFont.drawString(g, LevelScreen.strAbility[i], this.xPaint + 25, this.yPaint + 58 + i * dis, 0);
                } else {
                    Font.normalFont.drawString(g, LevelScreen.strAbility[i], this.xPaint + 25, this.yPaint + 58 + i * dis, 0);
                }
            } else {
                Font.normalFont.drawString(g, LevelScreen.strAbility[i], this.xPaint + 25, this.yPaint + 58 + i * dis, 0);
            }
            short ability = this.currAbility[i];
            g.setColor(1521982);
            g.fillRect(this.xPaint + 98 + aa, this.yPaint + 57 + i * dis, 30, 16, false);
            if (this.canDown[i] == 1) {
                Font.normalGFont.drawString(g, new StringBuilder(String.valueOf(ability)).toString(), this.xPaint + 113 + aa, this.yPaint + 57 + i * dis, 3);
            } else {
                Font.normalYFont.drawString(g, new StringBuilder(String.valueOf(ability)).toString(), this.xPaint + 113 + aa, this.yPaint + 57 + i * dis, 3);
            }
            if (this.canDown[i] == 1) {
                g.drawImage(LevelScreen.arrow, this.xPaint + 96 + aa - bb, this.yPaint + 62 + i * dis, 24, false);
            }
            if (this.canUp[i] == 1) {
                g.drawRegion(LevelScreen.arrow, 0, 0, 4, 7, 2, this.xPaint + 130 + aa + bb, this.yPaint + 62 + i * dis, 0, false);
            }
        }
        g.translate(0, -g.getTranslateY());
        int a = CCanvas.isTouch ? 15 : 0;
        Font.borderFont.drawString(g, "Point: " + m.point, this.xPaint + W / 2 - a, this.yPaint + 57 + 90 + 2 + a, 3);
        this.paintSuper(g);
    }

    @Override
    public void onPointerPressed(int xPress, int yPress, int index) {
        super.onPointerPressed(xPress, yPress, index);
    }

    @Override
    public void onPointerReleased(int xReleased, int yReleased, int index) {
        super.onPointerReleased(xReleased, yReleased, index);
        int aa = (yReleased - (this.yPaint + 57 - 25)) / 25;
        this.select = aa;
        if (this.select < 0) {
            this.select = 0;
        }
        if (this.select > 4) {
            this.select = 4;
        }
        CRes.out("==> sellect = " + aa);
        if (CCanvas.isPointer(this.xPaint + 70, this.yPaint + 57 - 25 + this.select * 25, 30, 30, index)) {
            this.doDown();
        }
        if (CCanvas.isPointer(this.xPaint + 130, this.yPaint + 57 - 25 + this.select * 25, 30, 30, index)) {
            this.doUp();
        }
    }

    @Override
    public void update() {
        super.update();
        GameScr.sm.update();
        if (TerrainMidlet.myInfo.point >= 0) {
            this.center = this.cmdSelect;
            this.left = this.cmdLamlai;
        } else {
            this.center = null;
            this.left = null;
        }
    }

    static {
        strAbility = new String[]{Language.heath(), Language.dam(), Language.defend(), Language.lucky(), Language.team()};
        try {
            LevelScreen.ability = mImage.createImage("/item/ability.png");
            LevelScreen.plus = mImage.createImage("/item/+.png");
            LevelScreen.arrow = mImage.createImage("/map/arrow1.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
