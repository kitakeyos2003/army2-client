package model;

import CLib.mGraphics;
import screen.CScreen;
import network.Command;
import coreLG.CCanvas;
import java.util.Vector;

public class Menu {

    public boolean showMenu;
    public Vector<Command> menuItems;
    public int menuSelectedItem;
    public int menuX;
    public int menuY;
    public int menuW;
    public int menuH;
    public int menuTemY;
    public static int cmtoY;
    public static int cmy;
    public static int cmdy;
    public static int cmvy;
    public static int cmyLim;
    public static int xc;
    public int dis;
    int pa;
    boolean trans;

    public Menu() {
        this.pa = 0;
        this.trans = false;
    }

    public void startAt(Vector<Command> menuItems, int pos) {
        this.menuItems = menuItems;
        if (CCanvas.isTouch) {
            this.dis = 10;
        } else {
            this.dis = 0;
        }
        this.menuW = 0;
        this.menuH = 0;
        for (int i = 0; i < menuItems.size(); ++i) {
            Command c = (Command) menuItems.elementAt(i);
            int w = Font.normalFont.getWidth(c.caption);
            if (w > this.menuW) {
                this.menuW = w;
            }
            this.menuH += CScreen.ITEM_HEIGHT + this.dis;
        }
        this.menuW += 10;
        if (this.menuW < 115) {
            this.menuW = 115;
        }
        this.menuH += 4;
        if (pos == 0) {
            this.menuX = 2;
        } else if (pos == 1) {
            this.menuX = CCanvas.width - this.menuW - 2;
        } else {
            this.menuX = (CCanvas.width >> 1) - (this.menuW >> 1);
        }
        this.menuY = CCanvas.hieght - 21 - this.menuH - 6 - this.dis;
        this.menuTemY = CCanvas.hieght - (CScreen.ITEM_HEIGHT + this.dis);
        int limit = (CCanvas.hieght - CScreen.cmdH - 10) / (CScreen.ITEM_HEIGHT + this.dis);
        int max = CCanvas.isTouch ? limit : 7;
        if (menuItems.size() > max) {
            this.menuY = CCanvas.hieght - (CScreen.ITEM_HEIGHT + this.dis) * max - 31 - this.dis;
            this.menuH = (CScreen.ITEM_HEIGHT + this.dis) * max + 4;
        }
        if (CCanvas.hieght < 200 && !CCanvas.isTouch) {
            this.menuY += 10;
        }
        this.showMenu = true;
        this.menuSelectedItem = 0;
        Menu.cmyLim = this.menuItems.size() * (CScreen.ITEM_HEIGHT + this.dis) - (CScreen.ITEM_HEIGHT + this.dis) * max;
        if (Menu.cmyLim < 0) {
            Menu.cmyLim = 0;
        }
        Menu.cmtoY = 0;
        Menu.cmy = 0;
    }

    public void moveCamera() {
        if (Menu.cmy != Menu.cmtoY) {
            Menu.cmvy = Menu.cmtoY - Menu.cmy << 2;
            Menu.cmdy += Menu.cmvy;
            Menu.cmy += Menu.cmdy >> 4;
            Menu.cmdy &= 0xF;
        }
    }

    public void onPointerPressed(int x, int y, int index) {
        if (CCanvas.keyPressed[2] || CScreen.keyUp) {
            --this.menuSelectedItem;
            if (this.menuSelectedItem < 0) {
                this.menuSelectedItem = this.menuItems.size() - 1;
            }
            Menu.cmtoY = this.menuSelectedItem * (CScreen.ITEM_HEIGHT + this.dis) - 2 * (CScreen.ITEM_HEIGHT + this.dis);
        } else if (CCanvas.keyPressed[8] || CScreen.keyDown) {
            ++this.menuSelectedItem;
            if (this.menuSelectedItem > this.menuItems.size() - 1) {
                this.menuSelectedItem = 0;
            }
            Menu.cmtoY = this.menuSelectedItem * (CScreen.ITEM_HEIGHT + this.dis) - 2 * (CScreen.ITEM_HEIGHT + this.dis);
        } else if (CCanvas.keyPressed[5] || CCanvas.keyPressed[12] || CScreen.getCmdPointerPressed((byte) 2, index, true) || CScreen.getCmdPointerPressed((byte) 0, index, true)) {
            this.showMenu = false;
            if (this.menuItems.size() > 0) {
                this.menuItems.elementAt(this.menuSelectedItem).action.perform();
            }
        } else if (CCanvas.keyPressed[13] || CScreen.getCmdPointerPressed((byte) 1, index, true)) {
            this.showMenu = false;
        }
        this.trans = false;
        CScreen.clearKey();
    }

    public void onPointerReleased(int x, int y, int index) {
        this.trans = false;
        if (CCanvas.isPointer(this.menuX, this.menuY, this.menuW, this.menuH, index)) {
            this.trans = false;
            int currSelect = (y - this.menuTemY + Menu.cmtoY) / (CScreen.ITEM_HEIGHT + this.dis);
            if (this.menuSelectedItem != currSelect) {
                this.menuSelectedItem = currSelect;
            } else {
                try {
                    this.showMenu = false;
                    this.menuItems.elementAt(this.menuSelectedItem).action.perform();
                } catch (Exception e) {
                    CRes.out("=====> onpointer is over list");
                }
            }
        } else {
            this.showMenu = false;
        }
    }

    public void onPointerDragged(int x, int y, int index) {
        if (!this.trans) {
            this.pa = Menu.cmy;
            this.trans = true;
        }
        Menu.cmtoY = this.pa + (CCanvas.pyFirst[index] - y);
        if (Menu.cmtoY > Menu.cmyLim) {
            Menu.cmtoY = Menu.cmyLim;
        }
        if (Menu.cmtoY < 0) {
            Menu.cmtoY = 0;
        }
        if (this.menuSelectedItem == this.menuItems.size() - 1 || this.menuSelectedItem == 0) {
            Menu.cmy = Menu.cmtoY;
        }
    }

    public void updateMenuKey() {
    }

    public static void paintDefaultPopup(mGraphics g) {
        g.setColor(14279153);
        g.fillRoundRect(8, CCanvas.hieght - 102, CCanvas.width - 16, 69, 6, 6, false);
        g.setColor(4682453);
        g.fillRect(10, CCanvas.hieght - 100, CCanvas.width - 20, 65, false);
    }

    public void paintMenu(mGraphics g) {
        g.translate(-g.getTranslateX(), -g.getTranslateY());
        CScreen.paintDefaultPopup(this.menuX - 2, this.menuY - 6, this.menuW, this.menuH + 12, g);
        g.translate(this.menuX + 5, this.menuTemY + 2);
        g.setClip(-5, 0, this.menuW, this.menuH);
        g.translate(0, -Menu.cmy);
        int aa = CCanvas.isTouch ? 5 : 0;
        for (int i = 0; i < this.menuItems.size(); ++i) {
            g.setColor(0);
            if (i == this.menuSelectedItem && this.menuY == this.menuTemY) {
                g.setColor(16767817);
                if (!CCanvas.isTouch) {
                    g.fillRect(0, i * (CScreen.ITEM_HEIGHT + this.dis) + aa, this.menuW - 14, CScreen.ITEM_HEIGHT, false);
                } else {
                    g.fillRect(0, i * (CScreen.ITEM_HEIGHT + this.dis) + aa - 4, this.menuW - 14, CScreen.ITEM_HEIGHT + 8, false);
                }
            }
            if (this.menuY == this.menuTemY) {
                Font.normalFont.drawString(g,  this.menuItems.elementAt(i).caption, 5, 3 + i * (CScreen.ITEM_HEIGHT + this.dis) + aa, 0);
            }
        }
        g.translate(-g.getTranslateX(), -g.getTranslateY());
        g.setClip(-1000, -1000, 2000, 2000);
        int a = 0;
        if (CCanvas.isTouch) {
            a = 12;
        } else {
            a = 3;
        }
        Font.normalFont.drawString(g, Language.select(), 5, CCanvas.hieght - Font.normalFont.getHeight() - a, 0);
        Font.normalFont.drawString(g, Language.no(), CCanvas.width - 5, CCanvas.hieght - Font.normalFont.getHeight() - a, 1);
    }

    public void update() {
        this.moveCamera();
        if (this.menuTemY > this.menuY) {
            int delta = this.menuTemY - this.menuY >> 1;
            if (delta < 1) {
                delta = 1;
            }
            this.menuTemY -= delta;
        }
        this.menuTemY = this.menuY;
        if (Math.abs(Menu.cmtoY - Menu.cmy) < 15 && Menu.cmy < 0) {
            Menu.cmtoY = 0;
        }
        if (Math.abs(Menu.cmtoY - Menu.cmy) < 10 && Menu.cmy > Menu.cmyLim) {
            Menu.cmtoY = Menu.cmyLim;
        }
    }

    public void mainLoop() {
        this.moveCamera();
        if (this.menuTemY > this.menuY) {
            int delta = this.menuTemY - this.menuY >> 1;
            if (delta < 1) {
                delta = 1;
            }
            this.menuTemY -= delta;
        }
        this.menuTemY = this.menuY;
        if (Math.abs(Menu.cmtoY - Menu.cmy) < 15 && Menu.cmy < 0) {
            Menu.cmtoY = 0;
        }
        if (Math.abs(Menu.cmtoY - Menu.cmy) < 10 && Menu.cmy > Menu.cmyLim) {
            Menu.cmtoY = Menu.cmyLim;
        }
    }
}
