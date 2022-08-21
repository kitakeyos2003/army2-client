package screen;

import model.Font;
import effect.Cloud;
import CLib.mGraphics;
import InApp.MainActivity;
import CLib.mSystem;
import model.CRes;
import model.MoneyInfo;
import network.Command;
import model.Language;
import model.IAction;
import coreLG.CCanvas;
import model.AvatarInfo;
import CLib.mImage;
import java.util.Vector;

public class MoneyScrIOS extends CScreen {

    Vector avs;
    public int selected;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    public int xL;
    public static mImage imgCoin;
    public static String url_Nap;
    int pa;
    boolean trans;

    public int priceFromID(int id) {
        for (int i = 0; i < this.avs.size(); ++i) {
            AvatarInfo av = (AvatarInfo) this.avs.elementAt(i);
            if (av.ID == id) {
                return av.price;
            }
        }
        return 0;
    }

    public void startAnimLeft() {
        this.xL = -CCanvas.width;
    }

    public MoneyScrIOS() {
        this.pa = 0;
        this.trans = false;
        this.nameCScreen = " MoneyScrIOS screen!";
        IAction buyAction = new IAction() {
            @Override
            public void perform() {
                doBuy();
            }
        };
        this.center = new Command(Language.select(), buyAction);
        this.right = new Command(Language.close(), new IAction() {
            @Override
            public void perform() {
                CCanvas.menuScr.show();
            }
        });
    }

    protected void doBuy() {
        if (this.avs == null || this.avs.size() <= 0) {
            CCanvas.startOKDlg("Error Inapp purchase");
            return;
        }
        if (this.selected >= 5) {
            MoneyInfo mi = (MoneyInfo) this.avs.elementAt(this.selected);
            if (mi != null && mi.id.equals("napWeb") && !CRes.isNullOrEmpty(MoneyScrIOS.url_Nap)) {
                mSystem.openUrl(MoneyScrIOS.url_Nap);
            }
        } else {
            MainActivity.makePurchase(MainActivity.google_productIds[this.selected]);
        }
    }

    public MoneyInfo getSelectMoney() {
        if (this.avs == null) {
            return null;
        }
        return (MoneyInfo) this.avs.elementAt(this.selected);
    }

    public void showInputCard() {
    }

    public void startAnimRight() {
        this.xL = CCanvas.width << 1;
    }

    @Override
    public void moveCamera() {
        if (this.cmy != this.cmtoY) {
            this.cmvy = this.cmtoY - this.cmy << 2;
            this.cmdy += this.cmvy;
            this.cmy += this.cmdy >> 4;
            this.cmdy &= 0xF;
        }
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        Cloud.paintCloud(g);
        for (int i = 0; i <= CCanvas.width; i += 32) {
            g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
        }
        g.translate(this.xL, 0);
        Font.bigFont.drawString(g, Language.charge(), 10, 3, 0);
        g.setColor(1407674);
        g.fillRect(0, 25, CCanvas.width, MoneyScrIOS.ITEM_HEIGHT, false);
        Font.normalYFont.drawString(g, Language.payMethod(), 10, 28, 0);
        this.paintRichList(g);
        super.paint(g);
    }

    private void paintRichList(mGraphics g) {
        if (this.avs == null) {
            return;
        }
        if (this.avs.size() <= 0) {
            return;
        }
        g.translate(0, MoneyScrIOS.ITEM_HEIGHT + 25);
        g.translate(0, -this.cmy);
        int y = 0;
        for (int i = 0; i < this.avs.size(); ++i) {
            if (i == this.selected) {
                g.setColor(16765440);
                g.fillRect(0, y, CCanvas.width, 20, false);
            }
            MoneyInfo avi = (MoneyInfo) this.avs.elementAt(i);
            g.drawImage(MoneyScrIOS.imgCoin, 10, y + 2, 0, false);
            String content = String.valueOf(avi.info) + "          " + avi.smsContent;
            Font.borderFont.drawString(g, content, 40, y + 2, 0);
            if (!CCanvas.isTouch) {
                y += 20;
            } else {
                y += 30;
            }
        }
    }

    @Override
    public void onPointerPressed(int xPress, int yPress, int index) {
        super.onPointerPressed(xPress, yPress, index);
    }

    @Override
    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
        if (this.avs == null) {
            return;
        }
        if (!this.trans) {
            this.pa = this.cmy;
            this.trans = true;
        }
        this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag);
        if (this.cmtoY < 0) {
            this.cmtoY = 0;
        }
        if (this.cmtoY > this.cmyLim) {
            this.cmtoY = this.cmyLim;
        }
        if (this.selected >= this.avs.size() - 1 || this.selected == 0) {
            this.cmy = this.cmtoY;
        }
    }

    @Override
    public void onPointerHold(int x, int y2, int index) {
        super.onPointerHold(x, y2, index);
    }

    @Override
    public void onPointerReleased(int xReleased, int yReleased, int index) {
        super.onPointerReleased(xReleased, yReleased, index);
        if (this.avs == null) {
            return;
        }
        if (CCanvas.isPointerDown[index]) {
            if (!this.trans) {
                this.pa = this.cmy;
                this.trans = true;
            }
            this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yReleased);
            if (this.cmtoY < 0) {
                this.cmtoY = 0;
            }
            if (this.cmtoY > this.cmyLim) {
                this.cmtoY = this.cmyLim;
            }
            if (this.selected >= this.avs.size() - 1 || this.selected == 0) {
                this.cmy = this.cmtoY;
            }
        }
        this.trans = false;
        int aa = (this.cmtoY + yReleased - MoneyScrIOS.ITEM_HEIGHT - 25) / 30;
        if (aa == this.selected && CCanvas.isDoubleClick) {
            this.center.action.perform();
        }
        this.selected = aa;
        if (this.selected < 0) {
            this.selected = 0;
        }
        if (this.selected > this.avs.size() - 1) {
            this.selected = this.avs.size() - 1;
        }
    }

    @Override
    public void update() {
        if (this.xL != 0) {
            this.xL += -this.xL >> 1;
        }
        if (this.xL == -1) {
            this.xL = 0;
        }
        this.moveCamera();
        Cloud.updateCloud();
    }

    public void setAvatarList(Vector avatarList) {
        this.avs = avatarList;
        this.selected = 0;
        int n = 0;
        this.cmtoY = n;
        this.cmy = n;
        this.cmyLim = avatarList.size() * 20 - (CCanvas.hh - 40);
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }
    }

    static {
        try {
            MoneyScrIOS.imgCoin = mImage.createImage("/coin.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
