package shop;

import model.PlayerInfo;
import model.Font;
import CLib.mGraphics;
import network.GameService;
import coreLG.TerrainMidlet;
import item.Item;
import screen.PrepareScr;
import model.CRes;
import model.IAction;
import model.Language;
import coreLG.CCanvas;
import screen.CScreen;
import java.util.Vector;
import item.MyItemIcon;
import network.Command;
import screen.TabScreen;

public class ShopItem extends TabScreen {

    Command cmdTroRa;
    Command cmdHoanTatGiaoDich;
    Command cmdBatDauMua;
    public static MyItemIcon ItemIcon;
    static Vector sellItem;
    static final int maxItemBuy = 99;
    boolean isChooseAItem;
    static int numItemMua;
    static int tongTien;
    private boolean trans;
    byte money;
    int XU;
    int LUONG;
    String giaText;

    @Override
    public void show(CScreen lastScreen) {
        super.show(lastScreen);
        this.xPaint = CScreen.w - ShopItem.ItemIcon.shopW >> 1;
        this.yPaint = (CCanvas.hieght - CScreen.cmdH) / 2 - 80;
        if (!CCanvas.isTouch) {
            this.hTabScreen = 157;
            this.n = 3;
        } else {
            this.n = 4;
            this.hTabScreen = 177;
        }
        this.title = Language.cuahang();
        this.getCommand();
    }

    public ShopItem() {
        this.XU = 0;
        this.LUONG = 0;
        this.isChooseAItem = false;
        this.nameCScreen = " ShopItem screen!";
        this.title = Language.cuahang();
        this.getCommand();
    }

    public void getCommand() {
        this.center = new Command(Language.buy(), new IAction() {
            @Override
            public void perform() {
                if (!isChooseAItem && (ShopItem.getCurI().type == 36 || ShopItem.getCurI().type == 37)) {
                    CCanvas.startYesNoDlg(Language.muavasudung(), new IAction() {
                        @Override
                        public void perform() {
                            isChooseAItem = true;
                            CCanvas.endDlg();
                            center.action.perform();
                        }
                    }, new IAction() {
                        @Override
                        public void perform() {
                            isChooseAItem = false;
                            CCanvas.endDlg();
                        }
                    });
                }
                if (!isChooseAItem) {
                    if (!isChooseAItem && !ShopItem.getCurI().isFreeItem && !ShopItem.getCurI().isCannotBuy) {
                        if (ShopItem.getCurI().num < 99) {
                            isChooseAItem = true;
                            if (99 - ShopItem.getCurI().num < ShopItem.getCurI().nCurBuyPackage) {
                                ShopItem.numItemMua = 99 - ShopItem.getCurI().num;
                            } else {
                                ShopItem.numItemMua = ShopItem.getCurI().nCurBuyPackage;
                            }
                            ShopItem.checkTongTien(ShopItem.ItemIcon.select, ShopItem.numItemMua);
                        } else {
                            CCanvas.startOKDlg(Language.fullItem());
                        }
                    }
                } else if (ShopItem.getCurI().price != -1 && ShopItem.getCurI().price2 != -1) {
                    Vector<Command> menu = new Vector<Command>();
                    Command xu = new Command(Language.muaXu(), new IAction() {
                        @Override
                        public void perform() {
                            buyAChooseItem((byte) 0, ShopItem.getCurI().type, (byte) ShopItem.numItemMua);
                        }
                    });
                    Command luong = new Command(Language.muaLuong(), new IAction() {
                        @Override
                        public void perform() {
                            buyAChooseItem((byte) 1, ShopItem.getCurI().type, (byte) ShopItem.numItemMua);
                        }
                    });
                    menu.addElement(xu);
                    menu.addElement(luong);
                    CCanvas.menu.startAt(menu, 2);
                } else if (ShopItem.getCurI().price != -1) {
                    buyAChooseItem((byte) 0, ShopItem.getCurI().type, (byte) ShopItem.numItemMua);
                } else if (ShopItem.getCurI().price2 != -1) {
                    buyAChooseItem((byte) 1, ShopItem.getCurI().type, (byte) ShopItem.numItemMua);
                }
            }
        });
        this.cmdTroRa = new Command(Language.back(), new IAction() {
            @Override
            public void perform() {
                if (isChooseAItem) {
                    isChooseAItem = false;
                } else {
                    isClose = true;
                }
            }
        });
        this.right = this.cmdTroRa;
        this.cmdHoanTatGiaoDich = new Command(Language.dathang(), new IAction() {
            @Override
            public void perform() {
            }
        });
    }

    public static void setItemVector(Vector itemShop) {
        ShopItem.sellItem = itemShop;
        int Num = ShopItem.sellItem.size();
        CRes.out("item size= " + Num);
        int NumPrepareItemChose = Num;
        int[] icon = new int[Num];
        for (int i = 0; i < Num; ++i) {
            icon[i] = getI(i).type;
        }
        if (!CCanvas.isTouch) {
            ShopItem.ItemIcon = new MyItemIcon(icon, 4, 6, 3);
        } else {
            ShopItem.ItemIcon = new MyItemIcon(icon, 4, 4, 2);
        }
        int[] prepareItemIcon = new int[NumPrepareItemChose];
        for (int j = 0; j < NumPrepareItemChose; ++j) {
            prepareItemIcon[j] = getI(j).type;
        }
        if (!CCanvas.isTouch) {
            PrepareScr.prepareScrItemIcon = new MyItemIcon(prepareItemIcon, 4, 5, 3);
        } else {
            PrepareScr.prepareScrItemIcon = new MyItemIcon(prepareItemIcon, 4, 4, 2);
        }
    }

    public static Item getI(int elementAt) {
        return (Item) ShopItem.sellItem.elementAt(elementAt);
    }

    public static Item getCurI() {
        return (Item) ShopItem.sellItem.elementAt(ShopItem.ItemIcon.select);
    }

    public static int[] getItemNum() {
        int[] itemNum = new int[ShopItem.sellItem.size()];
        for (int i = 0; i < ShopItem.sellItem.size(); ++i) {
            itemNum[i] = getI(i).num;
            itemNum[1] = (itemNum[0] = -1);
        }
        return itemNum;
    }

    public static void resetItemBuy() {
        for (int i = 0; i < ShopItem.sellItem.size(); ++i) {
            getI(i).numToBuy = 0;
        }
        ShopItem.tongTien = 0;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void mainLoop() {
        super.mainLoop();
        ShopItem.ItemIcon.mainLoop();
    }

    public void buyAChooseItem(byte money, byte itemID, byte numBuy) {
        checkTongTien(itemID, numBuy);
        if (getI(itemID).price * numBuy > TerrainMidlet.myInfo.xu || getI(itemID).price2 * numBuy > TerrainMidlet.myInfo.luong) {
            CCanvas.startOKDlg(Language.kocotien());
        } else {
            Item curI3;
            Item curI = curI3 = getCurI();
            curI3.numToBuy += numBuy;
            Item curI4;
            Item curI2 = curI4 = getCurI();
            curI4.num += numBuy;
            if (ShopItem.tongTien > 0 && this.n > 0) {
                GameService.gI().requestBuyItem(money, itemID, numBuy);
                resetItemBuy();
                CCanvas.endDlg();
            }
            if (this.isChooseAItem) {
                this.isChooseAItem = false;
            }
        }
    }

    public static void checkTongTien(int curidChoose, int numCurChoose) {
        ShopItem.tongTien = 0;
        for (int i = 0; i < ShopItem.sellItem.size(); ++i) {
            if (i == curidChoose) {
                int cur = numCurChoose * getI(i).price;
                if (cur == -1) {
                    cur = numCurChoose * getI(i).price2;
                }
                ShopItem.tongTien += cur;
            } else if (getI(i).numToBuy > 0) {
                int cur = getI(i).numToBuy * getI(i).price;
                if (cur == -1) {
                    cur = numCurChoose * getI(i).price2;
                }
                ShopItem.tongTien += cur;
            }
        }
    }

    public static void checkItemWhenChose(int[] itemUse) {
        for (int i = 0; i < ShopItem.sellItem.size(); ++i) {
            getI(i).numUsed = 0;
        }
        for (int i = 0; i < itemUse.length; ++i) {
            if (itemUse[i] > 0) {
                Item k;
                Item j = k = getI(itemUse[i]);
                ++k.numUsed;
            }
        }
    }

    public static int[] checkSetItem(int[] itemOut) {
        int[] itemUse = itemOut;
        CRes.out(" item itemUse4 =  " + itemUse[itemUse.length - 4]);
        CRes.out(" item itemUse3 =  " + itemUse[itemUse.length - 3]);
        CRes.out(" item itemUse2 =  " + itemUse[itemUse.length - 2]);
        CRes.out(" item itemUse1 =  " + itemUse[itemUse.length - 1]);
        if (getI(12).num > 0) {
            if (itemUse[itemUse.length - 4] == -1) {
                itemUse[itemUse.length - 4] = -2;
            }
        } else {
            itemUse[itemUse.length - 4] = -1;
        }
        if (getI(13).num > 0) {
            if (itemUse[itemUse.length - 3] == -1) {
                itemUse[itemUse.length - 3] = -2;
            }
        } else {
            itemUse[itemUse.length - 3] = -1;
        }
        if (getI(14).num > 0) {
            if (itemUse[itemUse.length - 2] == -1) {
                itemUse[itemUse.length - 2] = -2;
            }
        } else {
            itemUse[itemUse.length - 2] = -1;
        }
        if (getI(15).num > 0) {
            if (itemUse[itemUse.length - 1] == -1) {
                itemUse[itemUse.length - 1] = -2;
            }
        } else {
            itemUse[itemUse.length - 1] = -1;
        }
        for (int i = 0; i < itemUse.length; ++i) {
            if (itemUse[i] > 0 && getI(itemUse[i]).num < 1) {
                itemUse[i] = -2;
            }
            if (i == itemUse.length - 1 && getI(15).num < 1) {
                itemUse[i] = -1;
            } else if (i == itemUse.length - 2 && getI(14).num < 1) {
                itemUse[i] = -1;
            } else if (i == itemUse.length - 3 && getI(13).num < 1) {
                itemUse[i] = -1;
            } else if (i == itemUse.length - 4 && getI(12).num < 1) {
                itemUse[i] = -1;
            }
        }
        return itemUse;
    }

    public static void receiveAItemBuy(byte n, byte[] itemID, byte[] nAfterBuy, int moneyAfterBuy, int moneyAfterBuy2) {
        for (int i = 0; i < n; ++i) {
            getI(itemID[i]).num = nAfterBuy[i];
        }
        TerrainMidlet.myInfo.xu = moneyAfterBuy;
        TerrainMidlet.myInfo.luong = moneyAfterBuy2;
        CCanvas.startOKDlg(Language.thanks());
    }

    @Override
    public void paint(mGraphics g) {
        super.paint(g);
        Font.normalYFont.drawString(g, "  ", 0, 0, 0, true);
        Font.normalYFont.drawString(g, "  ", 0, 0, 0, true);
        paintItem(ShopItem.ItemIcon, this.xPaint, this.yPaint, g);
        Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint, 2, false);
        g.setColor(2509680);
        int dis = CCanvas.isTouch ? 20 : 0;
        paintTien(this.xPaint - 2, this.yPaint + 88 + dis, g);
        Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint - 5, 2, false);
        this.paintDetail(g, dis, ShopItem.ItemIcon.getWidth(), ShopItem.ItemIcon.getHeight());
        Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 2, 2, false);
        if (this.isChooseAItem && getCurI().type != 36 && getCurI().type != 37) {
            paintBuyBar(ShopItem.ItemIcon.select, CScreen.w - 140 >> 1, CScreen.h - 80 >> 1, g);
        }
        Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 5, 2, false);
        painSeller(CScreen.w - 5, CScreen.h - 20, g);
        Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 10, 2, false);
        this.paintSuper(g);
    }

    public static void paintItem(MyItemIcon MyIcon, int x, int y, mGraphics g) {
        MyIcon.paint(x, y + 25, g, true, getItemNum());
    }

    public void paintDetail(mGraphics g, int dis, int w, int h) {
        this.giaText = (getCurI().isFreeItem ? (String.valueOf(Language.price()) + ": " + Language.freeItem()) : (String.valueOf((getCurI().price != -1) ? (String.valueOf(getCurI().price) + Language.xu()) : "") + ((getCurI().price2 != -1) ? (String.valueOf((getCurI().price != -1) ? "-" : "") + getCurI().price2 + " " + Language.luong()) : "")));
        g.fillRoundRect(this.xPaint - 2, this.yPaint + 105 + dis, w + 4, 46, 6, 7, false);
        Font.normalYFont.drawString(g, getI(ShopItem.ItemIcon.select).decription, this.xPaint + 5, this.yPaint + 107 + dis, 0);
        Font.normalYFont.drawString(g, this.giaText, this.xPaint + 4, this.yPaint + 121 + dis, 0);
        Font.normalYFont.drawString(g, (getCurI().num > 0) ? (String.valueOf(Language.having()) + ": " + getCurI().num + " " + Language.per()) : "", this.xPaint + 4, this.yPaint + 135 + dis, 0);
    }

    public static void paintTileBar(byte type, int x, int y, mGraphics g) {
        CScreen.paintBorderRect(g, y, 3, 147, "=====");
    }

    public static void paintTien(int x, int y, mGraphics g) {
        PlayerInfo m = TerrainMidlet.myInfo;
        String money = String.valueOf(m.xu) + Language.xu() + "-" + m.luong + Language.luong();
        Font.normalFont.drawString(g, money, CCanvas.width / 2, y + 2, 3);
    }

    public static void paintSoluong(int Select, int x, int y, mGraphics g) {
    }

    public static void painSeller(int x, int y, mGraphics g) {
    }

    public static void paintBuyBar(int Select, int x, int y, mGraphics g) {
        CScreen.paintDefaultPopup(x - 5, y, 150, 75, g);
        Font.normalFont.drawString(g, Language.howMuch(), CCanvas.hw, y + 7, 2);
        getI(Select).drawThisItem(g, x + 20, y + 25);
        Font.normalFont.drawString(g, String.valueOf(ShopItem.numItemMua) + " " + Language.per(), x + 70, y + 25, 0);
        Font.normalFont.drawString(g, String.valueOf((getI(Select).price != -1) ? (String.valueOf(ShopItem.numItemMua * getI(Select).price) + Language.xu()) : "") + ((getI(Select).price2 != -1) ? (String.valueOf((getI(Select).price != -1) ? "/" : "") + ShopItem.numItemMua * getI(Select).price2 + " luong") : ""), CCanvas.hw, y + 52, 2);
        g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 4, x + 45 + CCanvas.gameTick % 3, y + 27, 0, false);
        g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 7, x + 115 - CCanvas.gameTick % 3, y + 27, 0, false);
    }

    @Override
    public void onPointerPressed(int x, int y2, int index) {
        super.onPointerPressed(x, y2, index);
        if (ShopItem.ItemIcon != null) {
            ShopItem.ItemIcon.onPointerPressed(x, y2, index);
        }
    }

    @Override
    public void onPointerReleased(int xRealse, int yRealse, int index) {
        this.trans = false;
        if (CCanvas.isPointer(0, 0, CCanvas.width, CCanvas.hieght - CScreen.cmdH, index)) {
            int X = CScreen.w - 140 >> 1;
            int Y = CScreen.h - 80 >> 1;
            if (!this.isChooseAItem) {
                if (ShopItem.ItemIcon != null) {
                    ShopItem.ItemIcon.onPointerReleased(xRealse, yRealse, index);
                }
                if (CCanvas.isDoubleClick && this.center != null) {
                    this.center.action.perform();
                }
            } else {
                if (!CCanvas.isPointer(X - 5, Y, 150, 75, index)) {
                    this.isChooseAItem = false;
                    return;
                }
                if (CCanvas.isPointer(X + 45, Y + 27, 40, 40, index)) {
                    ShopItem.numItemMua -= getCurI().nCurBuyPackage;
                    if (ShopItem.numItemMua < getCurI().nCurBuyPackage) {
                        ShopItem.numItemMua = getCurI().nCurBuyPackage;
                    }
                    checkTongTien(ShopItem.ItemIcon.select, ShopItem.numItemMua);
                }
                if (CCanvas.isPointer(X + 115, Y + 27, 40, 40, index)) {
                    ShopItem.numItemMua += getCurI().nCurBuyPackage;
                    if (ShopItem.numItemMua > 99 - getCurI().num) {
                        ShopItem.numItemMua = 99 - getCurI().num;
                    }
                    checkTongTien(ShopItem.ItemIcon.select, ShopItem.numItemMua);
                }
            }
        }
        super.onPointerReleased(xRealse, yRealse, index);
    }

    @Override
    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
        if (!this.isChooseAItem && ShopItem.ItemIcon != null) {
            ShopItem.ItemIcon.onPointerDragged(xDrag, yDrag, index);
        }
    }

    static {
        ShopItem.sellItem = new Vector();
    }
}
