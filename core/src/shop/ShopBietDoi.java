package shop;

import CLib.mSystem;
import model.Font;
import screen.PrepareScr;
import effect.Cloud;
import CLib.mGraphics;
import network.Command;
import coreLG.CCanvas;
import network.GameService;
import model.IAction;
import model.Language;
import model.ClanItem;
import java.util.Vector;
import screen.CScreen;

public class ShopBietDoi extends CScreen {

    private long currentTimeClick;
    public Vector items;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    int selected;
    int disY;
    boolean isPaintItemLish;
    int pa;
    boolean trans;

    public ShopBietDoi() {
        this.disY = 50;
        this.pa = 0;
        this.trans = false;
    }

    public ClanItem getCurrItem() {
        return (ClanItem) this.items.elementAt(this.selected);
    }

    public ClanItem getClanItem(byte id) {
        for (int i = 0; i < this.items.size(); ++i) {
            ClanItem item = (ClanItem) this.items.elementAt(i);
            if (item.id == id) {
                return item;
            }
        }
        return null;
    }

    public void initCommand() {
        final Command xu = new Command(Language.muaXu(), new IAction() {
            @Override
            public void perform() {
                CCanvas.startYesNoDlg(Language.areYouSure(), new IAction() {
                    @Override
                    public void perform() {
                        if (getCurrItem() != null) {
                            GameService.gI().getShopBietDoi((byte) 1, (byte) 0, getCurrItem().id);
                        }
                    }
                });
            }
        });
        final Command luong = new Command(Language.muaLuong(), new IAction() {
            @Override
            public void perform() {
                CCanvas.startYesNoDlg(Language.areYouSure(), new IAction() {
                    @Override
                    public void perform() {
                        if (getCurrItem() != null) {
                            GameService.gI().getShopBietDoi((byte) 1, (byte) 1, getCurrItem().id);
                        }
                    }
                });
            }
        });
        if (this.getCurrItem() != null && this.getCurrItem().xu != -1 && this.getCurrItem().luong != -1) {
            this.center = new Command("Menu", new IAction() {
                @Override
                public void perform() {
                    Vector menu = new Vector();
                    menu.addElement(xu);
                    menu.addElement(luong);
                    CCanvas.menu.startAt(menu, 2);
                }
            });
        } else {
            if (this.getCurrItem().xu != -1) {
                this.center = xu;
            }
            if (this.getCurrItem().luong != -1) {
                this.center = luong;
            }
        }
        this.right = new Command(Language.back(), new IAction() {
            @Override
            public void perform() {
                CCanvas.menuScr.show();
            }
        });
    }

    public void setItems(Vector items) {
        this.items = items;
        this.cmyLim = items.size() * 50 - (CCanvas.hieght - (ShopBietDoi.ITEM_HEIGHT + ShopBietDoi.cmdH)) + 10;
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }
    }

    @Override
    public void show() {
        this.nameCScreen = " ShopBietDoi screen!";
        super.show();
        this.initCommand();
        CCanvas.endDlg();
    }

    @Override
    public void paint(mGraphics g) {
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        CScreen.paintDefaultBg(g);
        Cloud.paintCloud(g);
        for (int i = 0; i <= CCanvas.width; i += 32) {
            g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, true);
        }
        Font.bigFont.drawString(g, Language.ITEM_DOI(), 10, 3, 0);
        this.paintItems(g);
        super.paint(g);
    }

    @Override
    public void moveCamera() {
        if (this.cmy != this.cmtoY) {
            this.cmvy = this.cmtoY - this.cmy >> 2;
            this.cmy += this.cmvy;
        }
    }

    public void paintItems(mGraphics g) {
        g.translate(0, 30);
        g.setClip(0, 1, CCanvas.width, CCanvas.hieght - ShopBietDoi.cmdH);
        g.translate(0, -this.cmy);
        int y = 0;
        for (int i = 0; i < this.items.size(); ++i) {
            if (i == this.selected) {
                g.setColor(16765440);
                g.fillRect(0, y, CCanvas.width, 49, true);
            }
            if (i * 50 > -g.getTranslateY() && i * 50 < -g.getTranslateY() + CScreen.h) {
                ClanItem item = (ClanItem) this.items.elementAt(i);
                String name = item.name;
                Font.borderFont.drawString(g, "Level: " + item.levelRequire + ": " + name, 5, y, 0);
                String gia = String.valueOf(Language.price()) + ": ";
                if (item.xu != -1 && item.luong != -1) {
                    gia = String.valueOf(gia) + item.xu + Language.xu() + " - " + item.luong + " " + Language.luong();
                } else {
                    if (item.xu != -1) {
                        gia = String.valueOf(gia) + item.xu + Language.xu();
                    }
                    if (item.luong != -1) {
                        gia = String.valueOf(gia) + item.luong + Language.luong();
                    }
                }
                Font.normalFont.drawString(g, String.valueOf(Language.price()) + ": " + gia, 5, y + 18, 0);
                Font.normalFont.drawString(g, String.valueOf(Language.time()) + ": " + item.expDate + " " + Language.gio(), 5, y + 34, 0);
            }
            y += 50;
        }
    }

    @Override
    public void update() {
        Cloud.updateCloud();
    }

    @Override
    public void mainLoop() {
        super.mainLoop();
        this.moveCamera();
    }

    @Override
    public void onPointerPressed(int xPressed, int yPressed, int index) {
        super.onPointerPressed(xPressed, yPressed, index);
    }

    @Override
    public void onPointerReleased(int xRealsed, int yRealsed, int index) {
        super.onPointerReleased(xRealsed, yRealsed, index);
        this.trans = false;
        if (CCanvas.isPointer(0, 0, ShopBietDoi.w, CCanvas.hieght - ShopBietDoi.cmdH, index)) {
            int b = ShopBietDoi.ITEM_HEIGHT;
            int aa = (this.cmtoY + yRealsed - b) / this.disY;
            if (aa == this.selected && mSystem.currentTimeMillis() - this.currentTimeClick > 100L) {
                if (this.center != null) {
                    if (CCanvas.isDoubleClick) {
                        this.center.action.perform();
                    }
                } else if (this.left != null && CCanvas.isDoubleClick) {
                    this.left.action.perform();
                }
            }
            this.selected = aa;
            this.initCommand();
            if (this.selected < 0) {
                this.selected = 0;
            }
            if (this.selected >= this.items.size()) {
                this.selected = this.items.size() - 1;
            }
        }
        this.cmtoY = this.cmy;
        this.currentTimeClick = mSystem.currentTimeMillis();
    }

    @Override
    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
        this.currentTimeClick = mSystem.currentTimeMillis();
        if (!this.trans) {
            this.pa = this.cmy;
            this.trans = true;
        }
        this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag);
        if (this.cmtoY > this.cmyLim) {
            this.cmtoY = this.cmyLim;
        }
        if (this.cmtoY < 0) {
            this.cmtoY = 0;
        }
    }
}
