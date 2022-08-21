package shop;

import model.PlayerInfo;
import model.CRes;
import coreLG.TerrainMidlet;
import screen.PrepareScr;
import model.Font;
import CLib.mGraphics;
import CLib.Image;
import CLib.mImage;
import network.GameService;
import model.IAction;
import model.Language;
import screen.CScreen;
import coreLG.CCanvas;
import Equipment.Equip;
import model.Position;
import network.Command;
import java.util.Vector;
import screen.TabScreen;

public class ShopLinhTinh extends TabScreen {

    int select;
    public static Vector items;
    public int W;
    static int nLine;
    public static int hLine;
    public static int wTab;
    static int wXp;
    static int wYp;
    Command cmdSelect;
    public static int cmtoYI;
    public static int cmyI;
    public static int cmdyI;
    public static int cmvyI;
    public static int cmyILim;
    Vector myShop;
    int size;
    Position transText1;
    Equip eSelect;
    public String equipDetail;
    public String equipName;
    public String price;
    int num;
    boolean isSelectNum;
    String ngay;
    public static int pa;
    public static boolean trans;
    static int numItemMua;
    static int tongTien;

    public ShopLinhTinh() {
        this.W = CCanvas.width;
        this.myShop = new Vector();
        this.size = 0;
        this.transText1 = new Position(0, 1);
        this.equipDetail = "";
        this.equipName = "";
        this.price = "";
        this.num = 1;
        this.xPaint = CCanvas.width / 2 - 85;
        this.yPaint = (CCanvas.hieght - CScreen.cmdH) / 2 - 85;
        this.hTabScreen = 180;
        this.n = 4;
        this.title = Language.shoDacBiet();
        this.getW();
        if (CCanvas.isTouch) {
            ShopLinhTinh.nLine = 4;
            ShopLinhTinh.wXp = 9;
            ShopLinhTinh.wYp = 5;
            ShopLinhTinh.wTab = 40;
        } else {
            ShopLinhTinh.nLine = 8;
            ShopLinhTinh.wXp = 0;
            ShopLinhTinh.wYp = 0;
            ShopLinhTinh.wTab = 20;
        }
        this.nameCScreen = " ShopLinhTinh screen!";
    }

    public void getCommand() {
        final Command xu = new Command(Language.muaXu(), new IAction() {
            @Override
            public void perform() {
                String qInfo = "";
                if (getCurrEq().isBuyNum) {
                    qInfo = String.valueOf(Language.bancochac()) + eSelect.xu * num
                            + " " + Language.xu();
                } else {
                    qInfo = String.valueOf(Language.bancochac()) + eSelect.xu + " " + Language.xu();
                }
                CCanvas.startYesNoDlg(qInfo, new IAction() {
                    @Override
                    public void perform() {
                        CCanvas.startOKDlg(Language.pleaseWait());
                        getCurrEq().numSelected = 0;
                        GameService.gI().getShopLinhtinh((byte) 1, (byte) 0, (byte) getCurrEq().id,
                                (byte) num);
                    }
                });
            }
        });
        final Command luong = new Command(Language.muaLuong(), new IAction() {
            @Override
            public void perform() {
                String qInfo = "";
                if (getCurrEq().isBuyNum) {
                    qInfo = String.valueOf(Language.bancochac())
                            + eSelect.luong * num + " " + Language.luong();
                } else {
                    qInfo = String.valueOf(Language.bancochac()) + eSelect.luong + " "
                            + Language.luong();
                }
                CCanvas.startYesNoDlg(qInfo, new IAction() {
                    @Override
                    public void perform() {
                        CCanvas.startOKDlg(Language.pleaseWait());
                        getCurrEq().numSelected = 0;
                        GameService.gI().getShopLinhtinh((byte) 1, (byte) 1, (byte) getCurrEq().id,
                                (byte) num);
                    }
                });
            }
        });
        Command menuLeft = new Command("Menu", new IAction() {
            @Override
            public void perform() {
                Vector<Command> menu = new Vector<Command>();
                if (getCurrEq().strDetail.startsWith(Language.fomula())) {
                    menu.addElement(new Command(Language.detail(), new IAction() {
                        @Override
                        public void perform() {
                            GameService.gI().getFomula((byte) getCurrEq().id, (byte) 1, (byte) (-1));
                        }
                    }));
                }
                menu.addElement(xu);
                menu.addElement(luong);
                CCanvas.menu.startAt(menu, 0);
            }
        });
        if (this.eSelect == null) {
            this.left = xu;
            return;
        }
        if (this.eSelect.luong != -1 && this.eSelect.xu != -1) {
            this.left = menuLeft;
        } else if (this.eSelect.xu == -1 && this.eSelect.luong != -1) {
            this.left = luong;
        } else {
            this.left = xu;
        }
        this.right = new Command(Language.back(), new IAction() {
            @Override
            public void perform() {
                if (!isSelectNum) {
                    doClose();
                } else {
                    isSelectNum = false;
                    getCurrEq().numSelected = 0;
                }
            }
        });
        this.cmdSelect = new Command(Language.select(), new IAction() {
            @Override
            public void perform() {
                isSelectNum = true;
                num = 1;
                for (int i = 0; i < myShop.size(); ++i) {
                    Equip e = (Equip) myShop.elementAt(i);
                    e.numSelected = 0;
                    e.isSelect = false;
                }
            }
        });
    }

    public static void itemCamera() {
        if (ShopLinhTinh.cmyI != ShopLinhTinh.cmtoYI) {
            ShopLinhTinh.cmvyI = ShopLinhTinh.cmtoYI - ShopLinhTinh.cmyI << 2;
            ShopLinhTinh.cmdyI += ShopLinhTinh.cmvyI;
            ShopLinhTinh.cmyI += ShopLinhTinh.cmdyI >> 4;
            ShopLinhTinh.cmdyI &= 0xF;
        }
        if (ShopLinhTinh.cmyI > ShopLinhTinh.cmyILim) {
            ShopLinhTinh.cmyI = ShopLinhTinh.cmyILim;
        }
        if (ShopLinhTinh.cmyI < 0) {
            ShopLinhTinh.cmyI = 0;
        }
    }

    public void doClose() {
        this.isClose = true;
    }

    public Equip getCurrEq() {
        Equip e = (Equip) this.myShop.elementAt(this.select);
        return e;
    }

    public Equip getCurrEq(int index) {
        if (index >= this.myShop.size()) {
            return null;
        }
        if (index < 0) {
            return null;
        }
        Equip e = (Equip) this.myShop.elementAt(index);
        return e;
    }

    public void getMyShop() {
        this.myShop.removeAllElements();
        for (int i = 0; i < ShopLinhTinh.items.size(); ++i) {
            Equip e = (Equip) ShopLinhTinh.items.elementAt(i);
            this.myShop.addElement(e);
        }
    }

    public void setItems(Vector item) {
        this.select = 0;
        ShopLinhTinh.items.removeAllElements();
        ShopLinhTinh.items = item;
        this.getMyShop();
        this.size = this.myShop.size();
        ShopLinhTinh.hLine = this.myShop.size() / ShopLinhTinh.nLine;
        if (this.size % ShopLinhTinh.nLine != 0) {
            ++ShopLinhTinh.hLine;
        }
        ShopLinhTinh.cmyILim = ShopLinhTinh.hLine * ShopLinhTinh.wTab - 70;
        this.eSelect = (Equip) this.myShop.elementAt(this.select);
        this.equipDetail = this.eSelect.strDetail;
        this.equipName = this.eSelect.name;
        this.price = String.valueOf(Language.price()) + ": " + this.eSelect.xu + Language.xu() + "(" + this.eSelect.date
                + Language.ngay() + ")";
    }

    public void getMaterialIcon(int id, byte[] dataRawImages, int len) {
        for (int i = 0; i < this.myShop.size(); ++i) {
            Equip e = (Equip) this.myShop.elementAt(i);
            if (e.id == id) {
                e.materialIcon = mImage.createImage(dataRawImages, 0, len, "");
            }
        }
    }

    public void getMaterialIcon(int id, Image img) {
        for (int i = 0; i < this.myShop.size(); ++i) {
            Equip e = (Equip) this.myShop.elementAt(i);
            if (e.id == id) {
                e.materialIcon = new mImage(img);
            }
        }
    }

    public static void paintEquip(mGraphics g, int X, int Y, Vector it, int s) {
        int a = 0;
        int b = 0;
        int xIcon = 0;
        int yIcon = 0;
        g.setClip(X - 7, Y - 2, 170, 72);
        g.translate(0, -ShopLinhTinh.cmyI);
        g.setColor(16767817);
        for (int i = 0; i < it.size(); ++i) {
            Equip e = (Equip) it.elementAt(i);
            xIcon = X + a * ShopLinhTinh.wTab + ShopLinhTinh.wXp;
            yIcon = Y + b * ShopLinhTinh.wTab + ShopLinhTinh.wYp;
            if (i == s) {
                g.fillRect(xIcon - (CCanvas.isTouch ? 12 : 2), yIcon - (CCanvas.isTouch ? 12 : 2),
                        CCanvas.isTouch ? 40 : 20, CCanvas.isTouch ? 40 : 20, true);
                if (!CCanvas.isTouch) {
                    ShopLinhTinh.cmtoYI = yIcon - (Y + 20);
                }
            }
            if (e.isSelect) {
                g.setColor(5612786);
                g.fillRect(xIcon, yIcon, 16, 16, true);
            }
            e.drawIcon(g, xIcon, yIcon, true);
            if (++a == ShopLinhTinh.nLine) {
                a = 0;
                ++b;
            }
        }
        g.setClip(0, 0, 1000, 1000);
        g.translate(0, -g.getTranslateY());
    }

    public void doNumSelect() {
        if (this.myShop.size() == 0) {
            return;
        }
        Equip e = this.getCurrEq();
        if (e != null) {
            if (e.num > 1) {
                e.numSelected = 1;
                this.isSelectNum = true;
                this.num = 1;
                e.isSelect = true;
            } else {
                e.numSelected = 1;
                e.isSelect = !e.isSelect;
            }
        }
    }

    public void paintBuyBar(int Select, int x, int y, mGraphics g) {
        CScreen.paintDefaultPopup(x - 75, y - 30, 150, 60, g);
        Font.normalFont.drawString(g, Language.nhapsoluong(), CCanvas.hw, y - 15, 2);
        Font.normalFont.drawString(g, String.valueOf(this.num) + " " + Language.per(), x, y + 18 - 15, 2);
        g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 4, x - 40 + CCanvas.gameTick % 3, y + 20 - 15, 0, false);
        g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 7, x + 30 - CCanvas.gameTick % 3, y + 20 - 15, 0, false);
    }

    public void paintDetail(mGraphics g, int X, int Y) {
        PlayerInfo m = TerrainMidlet.myInfo;
        String myMoney = String.valueOf(Language.money()) + ": " + m.xu + Language.xu() + "-" + m.luong
                + Language.luong();
        int cc = 0;
        int bb = Font.normalFont.getWidth(this.equipDetail);
        if (bb > 155) {
            CRes.transTextLimit(this.transText1, bb - 150);
        }
        cc = this.transText1.x;
        Font.normalFont.drawString(g, myMoney, this.W / 2, Y - 1, 3);
        g.setColor(2378093);
        g.fillRoundRect(X, Y + 14, 170, 16, 6, 6, false);
        g.fillRoundRect(X, Y + 34, 170, 16, 6, 6, false);
        g.fillRoundRect(X, Y + 54, 170, 16, 6, 6, false);
        Font.normalGFont.drawString(g, this.equipName, X + 6, Y + 15, 0);
        Font.normalYFont.drawString(g, this.price, X + 6, Y + 35, 0);
        Font.normalYFont.drawString(g, this.equipDetail, X + 6 + cc, Y + 55, 0);
    }

    @Override
    public void paint(mGraphics g) {
        super.paint(g);
        g.setColor(3832504);
        g.fillRoundRect(this.W / 2 - 85, this.yPaint + 23, 170, 78, 6, 6, false);
        paintEquip(g, this.W / 2 - 78, this.yPaint + 29, this.myShop, this.select);
        this.paintDetail(g, this.W / 2 - 85, this.yPaint + 103);
        if (this.isSelectNum) {
            this.paintBuyBar(0, CCanvas.width / 2, CCanvas.hieght / 2, g);
        }
        this.paintSuper(g);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void mainLoop() {
        super.mainLoop();
        itemCamera();
    }

    public void getDetail() {
        this.num = 1;
        for (int i = 0; i < this.myShop.size(); ++i) {
            Equip e = (Equip) this.myShop.elementAt(i);
            e.numSelected = 0;
            e.isSelect = false;
        }
        this.eSelect = (Equip) this.myShop.elementAt(this.select);
        this.equipDetail = this.eSelect.strDetail;
        this.equipName = this.eSelect.name;
        String luong = String.valueOf((this.eSelect.xu != -1) ? "-" : "") + this.eSelect.luong + Language.luong();
        if (this.eSelect.luong == -1) {
            luong = "";
        }
        String xu = String.valueOf(this.eSelect.xu) + Language.xu();
        if (this.eSelect.xu == -1) {
            xu = "";
        }
        this.ngay = ((this.eSelect.date > 0) ? ("(" + this.eSelect.date + Language.ngay() + ")") : "");
        this.price = String.valueOf(Language.price()) + ": " + xu + luong + this.ngay;
        this.getCommand();
        this.transText1.x = 0;
    }

    @Override
    public void onPointerPressed(int x, int y2, int index) {
        super.onPointerPressed(x, y2, index);
    }

    @Override
    public void onPointerReleased(int xRealse, int yRealse, int index) {
        super.onPointerReleased(xRealse, yRealse, index);
        ShopLinhTinh.trans = false;
        int aa = -1;
        if (CCanvas.isPointer(this.xPaint, this.yPaint + 20, 160, 80, index)) {
            aa = (ShopLinhTinh.cmtoYI + yRealse - this.yPaint - 20) / ShopLinhTinh.wTab * ShopLinhTinh.nLine
                    + (xRealse - this.xPaint - 8) / ShopLinhTinh.wTab;
        }
        if (aa == this.select && CCanvas.isDoubleClick) {
            if (this.getCurrEq() != null && this.getCurrEq().isBuyNum) {
                this.cmdSelect.action.perform();
            } else if (this.left != null) {
                this.left.action.perform();
            }
        }
        if (aa > 0 && aa < this.myShop.size()) {
            this.select = aa;
            this.getDetail();
        }
        int xx = CCanvas.width / 2;
        int yy = CCanvas.hieght / 2;
        if (this.isSelectNum) {
            if (CCanvas.isPointer(xx - 40 + CCanvas.gameTick % 3, yy + 20 - 15, 13, 13, index)) {
                Equip e = this.getCurrEq();
                if (e != null) {
                    --this.num;
                    if (this.num <= 0) {
                        e.isSelect = false;
                        this.num = 0;
                    }
                    e.numSelected = ((this.num < 0) ? 0 : this.num);
                }
            }
            if (CCanvas.isPointer(xx + 30 - CCanvas.gameTick % 3, yy + 20 - 15, 40, 40, index)) {
                Equip e = this.getCurrEq();
                if (e != null) {
                    if (e.num > 5) {
                        ++this.num;
                    } else if (this.num > 100) {
                        this.num = 100;
                    } else {
                        ++this.num;
                    }
                    e.numSelected = ((this.num > 100) ? 100 : this.num);
                    e.isSelect = true;
                }
            }
        }
    }

    @Override
    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
        if (!ShopLinhTinh.trans) {
            ShopLinhTinh.pa = ShopLinhTinh.cmyI - 10;
            ShopLinhTinh.trans = true;
        }
        ShopLinhTinh.cmtoYI = ShopLinhTinh.pa + (CCanvas.pyFirst[index] - yDrag);
        if (ShopLinhTinh.cmtoYI < 0) {
            ShopLinhTinh.cmtoYI = 0;
        }
        if (ShopLinhTinh.cmtoYI > this.hTabScreen * 40 - 40) {
            ShopLinhTinh.cmtoYI = this.hTabScreen * 40 - 40;
        }
    }

    @Override
    public void show(CScreen lastScreen) {
        super.show(lastScreen);
        ShopLinhTinh.cmtoYI = 0;
        this.getCommand();
        this.getDetail();
    }

    static {
        ShopLinhTinh.items = new Vector();
        ShopLinhTinh.nLine = 8;
        ShopLinhTinh.pa = 0;
        ShopLinhTinh.trans = false;
    }
}
