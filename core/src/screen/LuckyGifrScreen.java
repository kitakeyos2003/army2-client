package screen;

import model.PlayerInfo;
import coreLG.TerrainMidlet;
import player.Boss;
import model.Font;
import CLib.mGraphics;
import network.Command;
import network.GameService;
import model.IAction;
import model.Language;
import effect.Explosion;
import java.util.Vector;
import coreLG.CCanvas;
import model.CTime;
import model.LuckyGift;

public class LuckyGifrScreen extends CScreen {

    public static String[] info;
    private LuckyGift[] gifts;
    boolean showAll;
    public int num;
    public static CTime time;
    public int[] giftDelete;
    int count;
    public int wLine;
    public int hLine;
    public int disW;
    public int x;
    public int y;
    int xSelect;
    int ySelect;
    public int select;
    public boolean isShow;
    int dem;
    boolean isDem;

    public LuckyGifrScreen() {
        this.gifts = new LuckyGift[12];
        this.num = 12;
        this.giftDelete = new int[this.num];
        this.dem = 0;
        this.nameCScreen = " LuckyGifrScreen screen!";
        this.disW = ((CCanvas.width < 240) ? 40 : 50);
        this.wLine = 4;
        this.hLine = 3;
        this.x = (CCanvas.width - this.disW * this.wLine) / 2 + this.disW / 2;
        this.y = ((CCanvas.width > CCanvas.hieght) ? 40 : 70) + this.disW / 2;
        if (CCanvas.width < 200) {
            this.y = 25 + this.disW / 2;
        }
    }

    @Override
    public void show() {
        GameScr.exs = new Vector<Explosion>();
        this.giftDelete = new int[this.num];
        this.count = 0;
        this.init();
        super.show();
    }

    private void init() {
        if (this.isShow) {
            this.right = new Command(Language.exit(), new IAction() {
                @Override
                public void perform() {
                    if (isShow) {
                        if (CScreen.lastSCreen != null) {
                            CScreen.lastSCreen.show();
                        } else {
                            CCanvas.roomListScr2.show();
                        }
                    } else {
                        GameService.gI().luckGift((byte) (-2));
                    }
                }
            });
            this.left = null;
            this.center = null;
        } else {
            if (this.gifts != null) {
                for (int i = 0; i < this.gifts.length; ++i) {
                    if (this.gifts[i] != null) {
                        this.gifts[i].isShow = false;
                        this.gifts[i].isWait = false;
                    }
                }
            }
            this.center = new Command(Language.select(), new IAction() {
                @Override
                public void perform() {
                    isDem = true;
                    if (dem == 0) {
                        if (giftDelete[select] != -1) {
                            giftDelete[select] = -1;
                            gifts[select] = new LuckyGift();
                            gifts[select].isWait = true;
                            GameService.gI().luckGift((byte) select);
                        }
                        new Explosion(xSelect, ySelect, (byte) 1);
                        ++count;
                    }
                }
            });
            this.left = new Command("Xong", new IAction() {
                @Override
                public void perform() {
                    GameService.gI().luckGift((byte) (-2));
                    left = null;
                    center = null;
                }
            });
            this.right = null;
        }
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        for (int i = 0; i < LuckyGifrScreen.info.length; ++i) {
            Font.normalFont.drawString(g, LuckyGifrScreen.info[i], CCanvas.width / 2, 5 + i * 20, 3);
        }
        int i = 0;
        int j = 0;
        if (LuckyGifrScreen.time != null) {
            LuckyGifrScreen.time.paint(g);
        }
        for (int a = 0; a < this.num; ++a) {
            int X = this.x + i * this.disW;
            int Y = this.y + j * this.disW;
            if (a == this.select && !this.isShow) {
                this.xSelect = X;
                this.ySelect = Y;
                g.setColor(3374591);
                g.fillRect(X - 2 - Boss.gift_1.image.getWidth() / 2, Y - 2 - Boss.gift_1.image.getWidth() / 2, Boss.gift_1.image.getWidth() + 4, Boss.gift_1.image.getWidth() + 4, false);
            }
            if (this.gifts[a] != null) {
                this.gifts[a].paint(g, X, Y);
            } else {
                g.drawImage(Boss.gift_1, X, Y, 3, false);
            }
            if (++i == this.wLine) {
                ++j;
                i = 0;
            }
        }
        PlayerInfo myInfo = TerrainMidlet.myInfo;
        Font.borderFont.drawString(g, String.valueOf(myInfo.xu) + " " + Language.xu() + " - " + myInfo.luong + " " + Language.luong(), 5, CCanvas.hieght - LuckyGifrScreen.cmdH - LuckyGifrScreen.ITEM_HEIGHT - 20, 0);
        for (int a2 = 0; a2 < GameScr.exs.size(); ++a2) {
            GameScr.exs.elementAt(a2).paint(g);
        }
        super.paint(g);
    }

    @Override
    public void update() {
        if (this.isDem) {
            ++this.dem;
            if (this.dem == 20) {
                this.dem = 0;
                this.isDem = false;
            }
        }
        if (LuckyGifrScreen.time != null) {
            LuckyGifrScreen.time.update();
            if (CTime.seconds < 0) {
                LuckyGifrScreen.time = null;
            }
        }
        for (int i = 0; i < 12; ++i) {
            if (this.gifts[i] != null) {
                this.gifts[i].update();
            }
        }
        for (int i = 0; i < GameScr.exs.size(); ++i) {
            GameScr.exs.elementAt(i).update();
        }
        super.update();
    }

    public void setGiftByItemID(LuckyGift luckyGift) {
        this.gifts[luckyGift.id] = luckyGift;
    }

    public LuckyGift getGiftByItemID(int id) {
        for (int i = 0; i < this.gifts.length; ++i) {
            if (this.gifts[i] != null && this.gifts[i].id == id) {
                return this.gifts[i];
            }
        }
        return null;
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
        int aa = (CCanvas.pY[index] - this.y + this.disW / 2) / this.disW * this.wLine + (CCanvas.pX[index] - this.x + this.disW / 2) / this.disW;
        if (aa == -1) {
            return;
        }
        if (aa == this.select && this.center != null && CCanvas.isDoubleClick) {
            this.center.action.perform();
        }
        if (aa >= 0 && aa < this.num) {
            this.select = aa;
        }
    }
}
