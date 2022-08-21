package screen;

import model.CRes;
import Equipment.Equip;
import model.Font;
import CLib.mGraphics;
import effect.Cloud;
import model.PlayerInfo;
import java.util.Vector;
import coreLG.CCanvas;
import network.GameService;
import coreLG.TerrainMidlet;
import network.Command;
import model.IAction;
import model.Language;
import item.Bullet;
import Equipment.PlayerEquip;
import CLib.mImage;

public class ChangePlayerCSr extends CScreen {

    public CScreen lastScr;
    public static final String[] GunDecription;
    public static final int[][] GunInfo;
    public static mImage lockImg;
    public static int curMenu;
    private int blankW;
    private int[] _iconX;
    private int _centerIX;
    private int nMainIcon;
    public static int gunPassiveIndexSub;
    public static final byte IS_UNLOCK = 1;
    public static final byte IS_LOCK = 0;
    public static byte[] isUnlock;
    public static int[] gunXu;
    public static int[] gunLuong;
    public static byte[] power;
    public static byte[] number;
    PlayerEquip[] equip;
    boolean isShowInfo;

    public ChangePlayerCSr() {
        this.blankW = ChangePlayerCSr.w / 2 - 50;
        this.nMainIcon = 10;
        this.equip = new PlayerEquip[10];
        ChangePlayerCSr.GunInfo[0] = new int[]{ChangePlayerCSr.number[0], ChangePlayerCSr.power[0], Bullet.BULLset_WIND_AFFECT[0]};
        ChangePlayerCSr.GunInfo[1] = new int[]{ChangePlayerCSr.number[1], ChangePlayerCSr.power[1], Bullet.BULLset_WIND_AFFECT[1]};
        ChangePlayerCSr.GunInfo[2] = new int[]{ChangePlayerCSr.number[2], ChangePlayerCSr.power[2], Bullet.BULLset_WIND_AFFECT[2]};
        ChangePlayerCSr.GunInfo[3] = new int[]{ChangePlayerCSr.number[3], ChangePlayerCSr.power[3], Bullet.BULLset_WIND_AFFECT[3]};
        ChangePlayerCSr.GunInfo[4] = new int[]{ChangePlayerCSr.number[4], ChangePlayerCSr.power[4], Bullet.BULLset_WIND_AFFECT[4]};
        ChangePlayerCSr.GunInfo[5] = new int[]{ChangePlayerCSr.number[5], ChangePlayerCSr.power[5], Bullet.BULLset_WIND_AFFECT[5]};
        ChangePlayerCSr.GunInfo[6] = new int[]{ChangePlayerCSr.number[6], ChangePlayerCSr.power[6], Bullet.BULLset_WIND_AFFECT[6]};
        ChangePlayerCSr.GunInfo[7] = new int[]{ChangePlayerCSr.number[7], ChangePlayerCSr.power[7], Bullet.BULLset_WIND_AFFECT[7]};
        ChangePlayerCSr.GunInfo[8] = new int[]{ChangePlayerCSr.number[8], ChangePlayerCSr.power[8], Bullet.BULLset_WIND_AFFECT[8]};
        ChangePlayerCSr.GunInfo[9] = new int[]{ChangePlayerCSr.number[9], ChangePlayerCSr.power[9], 0};
        this.right = new Command(Language.close(), new IAction() {
            @Override
            public void perform() {
                doClose();
            }
        });
        int menuX = (ChangePlayerCSr.w >> 1) - this.nMainIcon * (24 + this.blankW) / 2;
        this._iconX = new int[this.nMainIcon];
        for (int i = 0; i < this.nMainIcon; ++i) {
            this._iconX[i] = menuX + i * this.blankW;
        }
        this._centerIX = ChangePlayerCSr.w >> 1;
        this.nameCScreen = "ChangePlayerCSr screen!";
    }

    protected void doClose() {
        this.lastScr.show();
    }

    public void doChangePlayer() {
        if (ChangePlayerCSr.isUnlock[ChangePlayerCSr.curMenu] == 0) {
            final Command xu = new Command(Language.muaXu(), new IAction() {
                @Override
                public void perform() {
                    CCanvas.startYesNoDlg(String.valueOf(Language.buyCharactor()) + TerrainMidlet.myInfo.xu + Language.xu(), new IAction() {
                        @Override
                        public void perform() {
                            if (TerrainMidlet.myInfo.xu >= ChangePlayerCSr.gunXu[ChangePlayerCSr.curMenu]) {
                                GameService.gI().buyGun((byte) (ChangePlayerCSr.curMenu - ChangePlayerCSr.gunPassiveIndexSub), (byte) 0);
                                CCanvas.startWaitDlgWithoutCancel(Language.trading(), 15);
                            } else {
                                CCanvas.startOKDlg(Language.noMoney());
                            }
                        }
                    });
                }
            });
            final Command luong = new Command(Language.muaLuong(), new IAction() {
                @Override
                public void perform() {
                    CCanvas.startYesNoDlg(String.valueOf(Language.buyCharactor()) + TerrainMidlet.myInfo.luong + Language.luong(), new IAction() {
                        @Override
                        public void perform() {
                            if (TerrainMidlet.myInfo.luong >= ChangePlayerCSr.gunLuong[ChangePlayerCSr.curMenu]) {
                                GameService.gI().buyGun((byte) (ChangePlayerCSr.curMenu - ChangePlayerCSr.gunPassiveIndexSub), (byte) 1);
                                CCanvas.startWaitDlgWithoutCancel(Language.trading(), 16);
                            } else {
                                CCanvas.startOKDlg(Language.noMoney());
                            }
                        }
                    });
                }
            });
            Command menu = new Command(Language.select(), new IAction() {
                @Override
                public void perform() {
                    Vector menu = new Vector();
                    menu.addElement(xu);
                    menu.addElement(luong);
                    CCanvas.menu.startAt(menu, 2);
                }
            });
            if (ChangePlayerCSr.gunLuong[ChangePlayerCSr.curMenu] != -1) {
                this.center = menu;
            } else {
                this.center = xu;
            }
            return;
        }
        this.center = new Command(Language.select(), new IAction() {
            @Override
            public void perform() {
                CCanvas.startWaitDlg(Language.pleaseWait());
                GameService.gI().changeGun((byte) ChangePlayerCSr.curMenu);
            }
        });
    }

    public void getCurrEquip() {
        PlayerInfo p = TerrainMidlet.myInfo;
        for (int i = 0; i < 10; ++i) {
            boolean isVip = TerrainMidlet.isVip[i];
            short[] sung = {(short) i, 0, isVip ? p.equipVipID[i][0] : p.equipID[i][0]};
            short[] non = {(short) i, 1, isVip ? p.equipVipID[i][1] : p.equipID[i][1]};
            short[] giap = {(short) i, 2, isVip ? p.equipVipID[i][2] : p.equipID[i][2]};
            short[] kinh = {(short) i, 3, isVip ? p.equipVipID[i][3] : p.equipID[i][3]};
            short[] canh = {(short) i, 4, isVip ? p.equipVipID[i][4] : p.equipID[i][4]};
            this.equip[i] = new PlayerEquip(new short[][]{sung, non, giap, kinh, canh});
        }
    }

    @Override
    public void show(CScreen LastScr) {
        this.lastScr = LastScr;
        CCanvas.arrPopups.removeAllElements();
        CCanvas.msgPopup.nMessage = 0;
        this.getCurrEquip();
        this.doChangePlayer();
        super.show();
    }

    @Override
    public void onPointerPressed(int x, int y2, int index) {
        super.onPointerPressed(x, y2, index);
        if (CCanvas.keyPressed[4] || ChangePlayerCSr.keyLeft) {
            ChangePlayerCSr.curMenu = this.getLastP(ChangePlayerCSr.curMenu, 1);
            int last = this.getLastP(ChangePlayerCSr.curMenu, 1);
            int lastTo = this.getLastP(last, 1);
            this._iconX[last] = this._iconX[ChangePlayerCSr.curMenu] - this.blankW;
            this._iconX[lastTo] = this._iconX[ChangePlayerCSr.curMenu] - this.blankW * 2;
            this.doChangePlayer();
        }
        if (CCanvas.keyPressed[6] || ChangePlayerCSr.keyRight) {
            ChangePlayerCSr.curMenu = this.getNextP(ChangePlayerCSr.curMenu, 1);
            int next = this.getNextP(ChangePlayerCSr.curMenu, 1);
            int nextTo = this.getNextP(next, 1);
            this._iconX[next] = this._iconX[ChangePlayerCSr.curMenu] + this.blankW;
            this._iconX[nextTo] = this._iconX[ChangePlayerCSr.curMenu] + this.blankW * 2;
            this.doChangePlayer();
        }
        if (CCanvas.isPointerClick[index]) {
            if (CCanvas.isPointer(0, 0, CCanvas.width / 2 - 30, CCanvas.hieght - ChangePlayerCSr.cmdH, index)) {
                ChangePlayerCSr.curMenu = this.getLastP(ChangePlayerCSr.curMenu, 1);
                int last = this.getLastP(ChangePlayerCSr.curMenu, 1);
                int lastTo = this.getLastP(last, 1);
                this._iconX[last] = this._iconX[ChangePlayerCSr.curMenu] - this.blankW;
                this._iconX[lastTo] = this._iconX[ChangePlayerCSr.curMenu] - this.blankW * 2;
                this.doChangePlayer();
            }
            if (CCanvas.isPointer(CCanvas.width / 2 + 30, 0, CCanvas.width / 2 - 30, CCanvas.hieght - ChangePlayerCSr.cmdH, index)) {
                ChangePlayerCSr.curMenu = this.getNextP(ChangePlayerCSr.curMenu, 1);
                int next = this.getNextP(ChangePlayerCSr.curMenu, 1);
                int nextTo = this.getNextP(next, 1);
                this._iconX[next] = this._iconX[ChangePlayerCSr.curMenu] + this.blankW;
                this._iconX[nextTo] = this._iconX[ChangePlayerCSr.curMenu] + this.blankW * 2;
                this.doChangePlayer();
            }
            if (CCanvas.isPointer(CCanvas.width / 2 - 30, 0, 60, CCanvas.hieght - ChangePlayerCSr.cmdH, index)) {
                this.center.action.perform();
            }
        }
        clearKey();
    }

    @Override
    public void keyPressed(int keyCode) {
        super.keyPressed(keyCode);
    }

    public void input() {
    }

    @Override
    public void update() {
        Cloud.updateCloud();
        this.moveMenu();
    }

    public void moveMenu() {
        this.isShowInfo = false;
        int dx = Math.max(Math.abs(this._centerIX - this._iconX[ChangePlayerCSr.curMenu] >> 1), 1);
        if (this._iconX[ChangePlayerCSr.curMenu] < this._centerIX) {
            for (int i = 0; i < this.nMainIcon; ++i) {
                int[] iconX = this._iconX;
                int n = i;
                int[] array = iconX;
                int n3 = n;
                array[n3] += dx;
            }
        } else if (this._iconX[ChangePlayerCSr.curMenu] > this._centerIX) {
            for (int i = 0; i < this.nMainIcon; ++i) {
                int[] iconX2 = this._iconX;
                int n2 = i;
                int[] array2 = iconX2;
                int n4 = n2;
                array2[n4] -= dx;
            }
        } else {
            this.isShowInfo = true;
        }
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        Cloud.paintCloud(g);
        int rW = 140;
        int rH = 124;
        int menuY = ChangePlayerCSr.h >> 1;
        painRoundR(ChangePlayerCSr.w / 2 - 70, ChangePlayerCSr.h - rH >> 1, rW, rH, g);
        Font.bigFont.drawString(g, PrepareScr.GUN_NAME[ChangePlayerCSr.curMenu], ChangePlayerCSr.w / 2, menuY - 62, 2);
        this.drawMenuIcon(menuY - 11, g);
        if (this.isShowInfo) {
            int y = menuY - 4;
            int x = ChangePlayerCSr.w / 2 - 60;
            Font.borderFont.drawString(g, String.valueOf(ChangePlayerCSr.GunDecription[0]) + ChangePlayerCSr.GunInfo[ChangePlayerCSr.curMenu][0], x, y + 20, 0);
            Font.borderFont.drawString(g, ChangePlayerCSr.GunDecription[1], x, y + 34, 0);
            Font.borderFont.drawString(g, ChangePlayerCSr.GunDecription[2], x, y + 48, 0);
            g.setColor(0);
            g.fillRect(x + 70, y + 38, 50, 10, false);
            g.fillRect(x + 70, y + 52, 50, 10, false);
            g.setColor(4868682);
            g.fillRect(x + 72, y + 40, 46, 6, false);
            g.fillRect(x + 72, y + 54, 46, 6, false);
            g.setColor(16741888);
            g.fillRect(x + 72, y + 40, ChangePlayerCSr.GunInfo[ChangePlayerCSr.curMenu][1] * 46 / 35, 6, false);
            g.fillRect(x + 72, y + 54, ChangePlayerCSr.GunInfo[ChangePlayerCSr.curMenu][2] * 46 / 100, 6, false);
        }
        g.setColor(16777215);
        super.paint(g);
    }

    public static void painRoundR(int x, int y, int W, int H, mGraphics g) {
        g.setColor(8040447);
        g.fillRoundRect(x, y, W, H, 10, 10, false);
        g.setColor(16777215);
        g.drawRoundRect(x, y, W, H, 10, 10, false);
    }

    private void drawMenuIcon(int y, mGraphics g) {
        int frame = (CCanvas.gameTick % 5 > 2) ? 5 : 4;
        byte look = 2;
        for (int i = 0; i < this.nMainIcon; ++i) {
            this.equip[i].paint(g, 0, 0, this._iconX[i], y);
            if (ChangePlayerCSr.isUnlock[i] == 0) {
                g.drawImage(ChangePlayerCSr.lockImg, this._iconX[i], y + 5, mGraphics.TOP | mGraphics.HCENTER, false);
                if (ChangePlayerCSr.curMenu == i) {
                    String xu = String.valueOf(ChangePlayerCSr.gunXu[ChangePlayerCSr.curMenu]) + Language.xu();
                    String luong = String.valueOf(ChangePlayerCSr.gunLuong[ChangePlayerCSr.curMenu]) + Language.luong();
                    if (ChangePlayerCSr.gunLuong[ChangePlayerCSr.curMenu] == -1) {
                        luong = "";
                    }
                    Font.borderFont.drawString(g, String.valueOf(xu) + "-" + luong, this._iconX[i], y + 30, 2);
                    this.isShowInfo = false;
                }
            }
        }
    }

    public void changeEquipAttribute() {
        Vector inventory = EquipScreen.inventory;
        PlayerInfo m = TerrainMidlet.myInfo;
        for (int j = 0; j < m.myEquip.equips.length; ++j) {
            Equip currE = m.myEquip.equips[j];
            if (currE != null) {
                for (int i = 0; i < inventory.size(); ++i) {
                    Equip e = (Equip) inventory.elementAt(i);
                    if (currE.id == m.myEquip.id) {
                        m.addCurrEquip(e);
                        CRes.out("TOI DAY TOI DAY TOI DAY");
                    }
                }
            }
        }
    }

    public void onChangeGun() {
        if (!TerrainMidlet.isVip[TerrainMidlet.myInfo.gun]) {
            TerrainMidlet.myInfo.myEquip = this.equip[ChangePlayerCSr.curMenu];
        } else {
            TerrainMidlet.myInfo.myVipEquip = this.equip[ChangePlayerCSr.curMenu];
        }
        this.doClose();
    }

    public int getNextP(int cur, int count) {
        return (cur + count > this.nMainIcon - count) ? 0 : (cur + count);
    }

    public int getLastP(int cur, int count) {
        return (cur - count < 0) ? (this.nMainIcon - count) : (cur - count);
    }

    static {
        GunDecription = new String[]{String.valueOf(Language.bulletNumber()) + "        ", String.valueOf(Language.damage()) + "   ", String.valueOf(Language.windEffect()) + "  "};
        GunInfo = new int[10][];
        ChangePlayerCSr.lockImg = GameScr.lockImg;
        ChangePlayerCSr.curMenu = 2;
        ChangePlayerCSr.gunPassiveIndexSub = 3;
        ChangePlayerCSr.isUnlock = new byte[10];
        ChangePlayerCSr.gunXu = new int[10];
        ChangePlayerCSr.gunLuong = new int[10];
    }
}
