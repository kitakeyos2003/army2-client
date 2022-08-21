package screen;

import coreLG.TerrainMidlet;
import network.Command;
import model.Font;
import effect.Cloud;
import CLib.mGraphics;
import coreLG.CCanvas;
import model.CRes;
import model.IAction;
import model.Language;
import network.GameService;
import model.Position;
import model.Clan;
import java.util.Vector;

public class ClanScreen extends CScreen {

    Vector clans;
    public int selected;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    public int type;
    public static final int TOP_CLAN = 0;
    public static final int CLAN_INFO = 1;
    public Clan clan;
    public static boolean isFromMenu;
    public static boolean backToTop;
    public int[] endTime;
    public long[] dieTime;
    public long[] currentTime;
    public String[] strEndTime;
    Position transText1;
    Position transText2;
    Position transText3;
    Position transText4;
    public byte page;
    int pa;
    boolean trans;

    public Position transTextLimit(Position pos, int limit) {
        pos.x += pos.y;
        if (pos.y == -1 && Math.abs(pos.x) > limit) {
            pos.y *= -1;
        }
        if (pos.y == 1 && pos.x > 5) {
            pos.y *= -1;
        }
        return pos;
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

    public ClanScreen(int type) {
        this.clans = new Vector();
        this.transText1 = new Position(0, 1);
        this.transText2 = new Position(0, 1);
        this.transText3 = new Position(0, 1);
        this.transText4 = new Position(0, 1);
        this.pa = 0;
        this.trans = false;
        this.type = type;
        this.nameCScreen = "ClanScreen screen!";
    }

    public void doDetail() {
        GameService.gI().clanInfo(this.clanSelected().id);
        CCanvas.startOKDlg(Language.pleaseWait(), new IAction() {
            @Override
            public void perform() {
                CRes.out(" ======> Ko respond server ");
            }
        });
        ClanScreen.backToTop = true;
    }

    public void getClanList(byte page, Vector clan) {
        this.clans = clan;
        this.page = page;
        for (int i = 0; i < this.clans.size(); ++i) {
            Clan cl = (Clan) this.clans.elementAt(i);
            GameService.gI().getClanIcon(cl.id);
        }
        this.cmyLim = this.clans.size() * 50 - (CCanvas.hieght - (ClanScreen.ITEM_HEIGHT + ClanScreen.cmdH) - 12);
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }
    }

    public Clan clanSelected() {
        Clan cl = (Clan) this.clans.elementAt(this.selected);
        return cl;
    }

    public Clan findClan(int id) {
        for (int i = 0; i < this.clans.size(); ++i) {
            Clan cl = (Clan) this.clans.elementAt(i);
            if (cl.id == id) {
                return cl;
            }
        }
        return null;
    }

    @Override
    public void paint(mGraphics g) {
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        CScreen.paintDefaultBg(g);
        Cloud.paintCloud(g);
        if (this.type == 0) {
            Font.bigFont.drawString(g, Language.TOPCLAN(), CCanvas.width / 2, 3, 3);
            g.translate(0, ClanScreen.ITEM_HEIGHT + 5);
            g.translate(0, -this.cmy);
            int y = 0;
            for (int i = 0; i < this.clans.size(); ++i) {
                g.setClip(0, this.cmy, CCanvas.width, CCanvas.hieght - 2 * ClanScreen.ITEM_HEIGHT - 5 + this.cmy);
                if (i == this.selected) {
                    g.setColor(16765440);
                    g.fillRect(0, y, CCanvas.width, 49, true);
                }
                Clan cl = (Clan) this.clans.elementAt(i);
                if (cl.icon != null) {
                    g.drawImage(cl.icon, 4, y + 1, 0, true);
                } else {
                    g.drawImage(CRes.empty, 4, y + 1, 0, true, true);
                }
                int cc = 0;
                int bb = Font.normalFont.getWidth(cl.name);
                int dd = 0;
                int ee = Font.normalFont.getWidth(String.valueOf(Language.doitruong()) + ": " + cl.master);
                if (i == this.selected) {
                    if (bb > CCanvas.width - 30) {
                        this.transTextLimit(this.transText2, bb - (CCanvas.width - 30));
                    }
                    cc = this.transText2.x;
                    if (ee > CCanvas.width - 20) {
                        this.transTextLimit(this.transText3, ee - (CCanvas.width - 20));
                    }
                    dd = this.transText3.x;
                }
                String line1 = cl.name;
                String line2 = "Level: " + cl.level + "+" + cl.percen + "%";
                String line3 = String.valueOf(Language.doitruong()) + ": " + cl.master;
                g.setClip(20, this.cmy, CCanvas.width, CCanvas.hieght - 2 * ClanScreen.ITEM_HEIGHT - 5 + this.cmy);
                Font.borderFont.drawString(g, line1, 20 + cc, y, 0);
                g.setClip(5, this.cmy, CCanvas.width, CCanvas.hieght - 2 * ClanScreen.ITEM_HEIGHT - 5 + this.cmy);
                Font.normalFont.drawString(g, line2, 7, y + 34, 0);
                g.setClip(0, this.cmy, CCanvas.width, CCanvas.hieght - 2 * ClanScreen.ITEM_HEIGHT - 5 + this.cmy);
                Font.normalFont.drawString(g, line3, 7 + dd, y + 17, 0);
                g.drawImage(ClanScreen.cup, CCanvas.width - 25, y + 16, 0, true);
                Font.borderFont.drawString(g, new StringBuilder(String.valueOf(cl.cup)).toString(), CCanvas.width - 30, y + 17, 1);
                y += 50;
            }
        }
        if (this.type == 1) {
            g.translate(0, -this.cmy);
            int cc2 = 0;
            int dd2 = 0;
            int bb2 = Font.normalFont.getWidth(this.clan.name);
            int ee2 = Font.normalFont.getWidth("( " + this.clan.slogan + " )");
            if (bb2 > CCanvas.width - 20) {
                this.transTextLimit(this.transText1, bb2 - (CCanvas.width - 40));
            }
            if (ee2 > CCanvas.width - 20) {
                this.transTextLimit(this.transText4, ee2 - (CCanvas.width - 40));
            }
            cc2 = this.transText1.x;
            dd2 = this.transText4.x;
            if (bb2 > CCanvas.width - 20) {
                Font.borderFont.drawString(g, this.clan.name, 20 + cc2, 15, 0);
            } else {
                Font.borderFont.drawString(g, this.clan.name, CCanvas.width / 2 + 5, 15, 3);
            }
            if (ee2 > CCanvas.width - 20) {
                Font.borderFont.drawString(g, "( " + this.clan.slogan + " )", 20 + dd2, 35, 0);
            } else {
                Font.borderFont.drawString(g, "( " + this.clan.slogan + " )", CCanvas.width / 2, 35, 3);
            }
            if (this.clan.icon != null) {
                int w = Font.borderFont.getWidth(this.clan.name);
                if (bb2 > CCanvas.width - 20) {
                    g.drawImage(this.clan.icon, 5 + cc2, 17, 0, true);
                } else {
                    g.drawImage(this.clan.icon, CCanvas.width / 2 - w / 2 - 10, 17, 0, true);
                }
            }
            g.drawImage(ClanScreen.cup, CCanvas.width / 2 - 20, 55, 0, true);
            Font.borderFont.drawString(g, new StringBuilder(String.valueOf(this.clan.cup)).toString(), CCanvas.width / 2 + 2, 56, 0);
            String member = String.valueOf(Language.thanhvien()) + ": " + this.clan.count + "/" + this.clan.max;
            String money = String.valueOf(Language.ngansach()) + ": " + this.clan.money + Language.xu() + " - " + this.clan.money2 + Language.luong();
            String level = "Level: " + this.clan.level + "+" + this.clan.percen + "%";
            String date = String.valueOf(Language.ngaythanhlap()) + ": " + this.clan.date;
            Font.normalFont.drawString(g, member, CCanvas.width / 2, 75, 3);
            Font.normalFont.drawString(g, money, CCanvas.width / 2, 90, 3);
            Font.normalFont.drawString(g, level, CCanvas.width / 2, 105, 3);
            g.setColor(1521982);
            g.fillRect(CCanvas.width / 2 - 54, 120, 110, 17, true);
            g.setColor(2378093);
            g.fillRect(CCanvas.width / 2 - 54 + 1, 121, 108, 15, true);
            int percen = this.clan.percen * 108 / 100;
            g.setColor(16767817);
            g.fillRect(CCanvas.width / 2 - 54 + 1, 121, percen, 15, true);
            Font.borderFont.drawString(g, String.valueOf(this.clan.exp) + "/" + this.clan.nextExp, CCanvas.width / 2 - 54 + 55, 120, 2);
            Font.normalFont.drawString(g, date, CCanvas.width / 2, 140, 3);
            if (this.clan.item.length == 0) {
                String t = Language.clanItem();
                String[] text = Font.normalFont.splitFontBStrInLine(t, CCanvas.width - 50);
                for (int j = 0; j < text.length; ++j) {
                    Font.borderFont.drawString(g, text[j], CCanvas.width / 2, 185 + j * 15, 3);
                }
            } else {
                int[] time = new int[this.clan.item.length];
                for (int k = 0; k < this.clan.item.length; ++k) {
                    if (this.dieTime[k] != 0L) {
                        this.currentTime[k] = System.currentTimeMillis() / 1000L;
                        if (this.currentTime[k] > this.dieTime[k]) {
                            this.dieTime[k] = 0L;
                        }
                        long sec = this.currentTime[k] - this.dieTime[k];
                        int[] array = time;
                        int n = k;
                        int[] array2 = array;
                        int n2 = n;
                        array2[n2] += (int) (-sec);
                        this.strEndTime[k] = CRes.formatIntoDDHHMMSS(time[k], true);
                    }
                    Font.borderFont.drawString(g, this.clan.item[k], CCanvas.width / 2, 165 + k * 30, 3);
                    Font.normalFont.drawString(g, String.valueOf(Language.time()) + ": " + this.strEndTime[k], CCanvas.width / 2, 180 + k * 30, 3);
                }
            }
        }
        g.translate(-g.getTranslateX(), -g.getTranslateY());
        super.paint(g);
    }

    protected void doNext() {
        GameService.gI().topClan((byte) (this.page + 1));
        CCanvas.startOKDlg(Language.gettingList());
    }

    protected void doRefresh() {
        GameService.gI().topClan(this.page);
        CCanvas.startOKDlg(Language.gettingList());
    }

    public void commandInit() {
        if (this.type == 0) {
            this.center = new Command(Language.detail(), new IAction() {
                @Override
                public void perform() {
                    doDetail();
                }
            });
            this.right = new Command(Language.back(), new IAction() {
                @Override
                public void perform() {
                    CRes.out("isFrommenu= " + ClanScreen.isFromMenu);
                    CCanvas.menuScr.show();
                }
            });
            this.left = new Command("Menu", new IAction() {
                @Override
                public void perform() {
                    Vector<Command> menu = new Vector<Command>();
                    Command cmdRefresh = new Command(Language.update(), new IAction() {
                        @Override
                        public void perform() {
                            doRefresh();
                        }
                    });
                    Command cmdNext = new Command(Language.more(), new IAction() {
                        @Override
                        public void perform() {
                            doNext();
                        }
                    });
                    menu.addElement(cmdRefresh);
                    menu.addElement(cmdNext);
                    CCanvas.menu.startAt(menu, 0);
                }
            });
        }
        if (this.type == 1) {
            GameService.gI().getClanIcon(this.clan.id);
            this.left = new Command("Menu", new IAction() {
                @Override
                public void perform() {
                    Vector<Command> menu = new Vector<Command>();
                    Command cmdTop = new Command(Language.topClan(), new IAction() {
                        @Override
                        public void perform() {
                            GameService.gI().topClan((byte) 0);
                        }
                    });
                    Command cmdMember = new Command(Language.thanhvien(), new IAction() {
                        @Override
                        public void perform() {
                            GameService.gI().clanMember(page, clan.id);
                        }
                    });
                    Command cmdAddXu = new Command(Language.goptienXu(), new IAction() {
                        @Override
                        public void perform() {
                            CCanvas.inputDlg.setInfo(Language.nhapsoxu(), new IAction() {
                                @Override
                                public void perform() {
                                    if (CCanvas.inputDlg.tfInput.getText() != null && CCanvas.inputDlg.tfInput.getText() != "") {
                                        GameService.gI().inputMoney((byte) 0, Integer.parseInt(CCanvas.inputDlg.tfInput.getText()));
                                        CCanvas.endDlg();
                                    }
                                }
                            }, new IAction() {
                                @Override
                                public void perform() {
                                    CCanvas.endDlg();
                                }
                            }, 1);
                            CCanvas.inputDlg.show();
                        }
                    });
                    Command cmdAddLuong = new Command(Language.goptienLuong(), new IAction() {
                        @Override
                        public void perform() {
                            CCanvas.inputDlg.setInfo(Language.nhapsoLuong(), new IAction() {
                                @Override
                                public void perform() {
                                    if (CCanvas.inputDlg.tfInput.getText() != null && CCanvas.inputDlg.tfInput.getText() != "") {
                                        GameService.gI().inputMoney((byte) 1, Integer.parseInt(CCanvas.inputDlg.tfInput.getText()));
                                        CCanvas.endDlg();
                                    }
                                }
                            }, new IAction() {
                                @Override
                                public void perform() {
                                    CCanvas.endDlg();
                                }
                            }, 1);
                            CCanvas.inputDlg.show();
                        }
                    });
                    menu.addElement(cmdTop);
                    menu.addElement(cmdMember);
                    if (clan.id == TerrainMidlet.myInfo.clanID) {
                        menu.addElement(cmdAddXu);
                        menu.addElement(cmdAddLuong);
                    }
                    CCanvas.menu.startAt(menu, 0);
                }
            });
            this.right = new Command(Language.back(), new IAction() {
                @Override
                public void perform() {
                    if (!ClanScreen.backToTop) {
                        CCanvas.menuScr.show();
                    } else {
                        CCanvas.topClanScreen.show();
                    }
                }
            });
        }
    }

    @Override
    public void show(CScreen lastScreen) {
        super.show(lastScreen);
        this.commandInit();
        if (this.type == 1) {
            this.endTime = new int[this.clan.time.length];
            this.currentTime = new long[this.clan.time.length];
            this.dieTime = new long[this.clan.time.length];
            this.strEndTime = new String[this.clan.time.length];
            for (int i = 0; i < this.clan.time.length; ++i) {
                this.endTime[i] = this.clan.time[i];
                this.currentTime[i] = System.currentTimeMillis();
                this.dieTime[i] = System.currentTimeMillis() / 1000L + this.endTime[i];
            }
            this.cmyLim = 165 + this.clan.item.length * 30 - (CCanvas.hieght - ClanScreen.cmdH - 25);
            if (this.cmyLim < 0) {
                this.cmyLim = 0;
            }
        } else {
            this.selected = 0;
            this.cmtoY = 0;
        }
    }

    @Override
    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
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
    }

    @Override
    public void onPointerPressed(int xScreen, int yScreen, int index) {
        super.onPointerPressed(xScreen, yScreen, index);
    }

    @Override
    public void onPointerReleased(int xRealse, int yRealse, int index) {
        super.onPointerReleased(xRealse, yRealse, index);
        this.trans = false;
        if (this.type != 0) {
            return;
        }
        if (this.clans.size() < 1) {
            return;
        }
        if (CCanvas.isPointer(0, ClanScreen.ITEM_HEIGHT, ClanScreen.w, CCanvas.hieght - ClanScreen.cmdH, index)) {
            int b = ClanScreen.ITEM_HEIGHT;
            int aa = (this.cmtoY + yRealse - b) / 50;
            if (aa == this.selected) {
                if (this.center != null) {
                    if (CCanvas.isDoubleClick) {
                        this.center.action.perform();
                    }
                } else if (this.left != null && CCanvas.isDoubleClick) {
                    this.left.action.perform();
                }
            }
            this.selected = aa;
            if (this.selected < 0) {
                this.selected = 0;
            }
            if (this.selected >= this.clans.size()) {
                this.selected = this.clans.size() - 1;
            }
        }
    }

    public void onPointerHolder(int xScreen, int yScreen, int index) {
    }

    @Override
    public void update() {
        super.update();
        Cloud.updateCloud();
        this.moveCamera();
    }
}
