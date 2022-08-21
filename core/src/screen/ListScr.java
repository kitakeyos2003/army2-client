package screen;

import com.teamobi.mobiarmy2.GameMidlet;
import player.CPlayer;
import model.Font;
import effect.Cloud;
import CLib.mGraphics;
import coreLG.CCanvas;
import network.GameService;
import model.IAction;
import model.Language;
import model.PlayerInfo;
import CLib.Image;
import network.Command;
import java.util.Vector;
import CLib.mImage;

public class ListScr extends CScreen {

    public static mImage imgBoard;
    Vector<PlayerInfo> list;
    int selected;
    public int page;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    public byte roomID;
    public int type;
    Command cmdAddFriend;
    Command cmdDeleteFriend;
    Command cmdRefresh;
    Command cmdNext;
    private String[] title;
    private String[] bangxephang;
    public boolean isArmy2;
    int disY;
    boolean isNext;
    public String typeList;
    int pa;
    boolean trans;
    int pxFirst;

    public void getPlayerIcon(short clanID, Image icon) {
        for (int i = 0; i < this.list.size(); ++i) {
            PlayerInfo info = (PlayerInfo) this.list.elementAt(i);
            if (info.clanID == clanID) {
                info.clanIcon = new mImage(icon);
            }
        }
    }

    @Override
    public void moveCamera() {
        if (this.cmy != this.cmtoY) {
            this.cmvy = this.cmtoY - this.cmy >> 2;
            this.cmy += this.cmvy;
        }
    }

    public ListScr() {
        this.type = 0;
        this.title = Language.top();
        this.bangxephang = new String[] { Language.topCaothu(), Language.topDaiGiaXu(), Language.topDaigiaLuong(),
                Language.topCaothuTuan(), Language.topXuTuan() };
        this.disY = 40;
        this.pa = 0;
        this.trans = false;
        this.nameCScreen = " ListScr screen!";
        this.cmdAddFriend = new Command(Language.addFriend(), new IAction() {
            @Override
            public void perform() {
                doAddFriend();
            }
        });
        this.cmdRefresh = new Command(Language.update(), new IAction() {
            @Override
            public void perform() {
                doRefresh();
            }
        });
        this.cmdNext = new Command(Language.more(), new IAction() {
            @Override
            public void perform() {
                doNext();
            }
        });
        this.cmdDeleteFriend = new Command(Language.deleteFriend(), new IAction() {
            @Override
            public void perform() {
                doDeleteFriend();
            }
        });
    }

    protected void doNext() {
        this.isNext = true;
        if (this.type == 2 || this.type == 3) {
            return;
        }
        if (this.type <= 0) {
            GameService.gI().bangxephang((byte) (-this.type), this.page + 1);
            return;
        }
        if (this.type == 5) {
            GameService.gI().clanMember((byte) (this.page + 1), CCanvas.clanScreen.clan.id);
            CCanvas.startWaitDlg(Language.gettingList());
        } else {
            GameService.gI().requestStrongest(this.page + 1);
            CCanvas.startWaitDlg(Language.gettingList());
        }
    }

    protected void doBack() {
        this.isNext = true;
        if (this.type == 2 || this.type == 3) {
            return;
        }
        if (this.page < 0) {
            return;
        }
        if (this.type <= 0) {
            GameService.gI().bangxephang((byte) (-this.type), this.page - 1);
            return;
        }
        if (this.type == 5) {
            GameService.gI().clanMember((byte) (this.page - 1), CCanvas.clanScreen.clan.id);
            CCanvas.startWaitDlg(Language.gettingList());
        } else {
            GameService.gI().requestStrongest(this.page - 1);
            CCanvas.startWaitDlg(Language.gettingList());
        }
    }

    protected void doRefresh() {
        this.isNext = false;
        if (this.type <= 0) {
            GameService.gI().bangxephang((byte) (-this.type), this.page);
            return;
        }
        if (this.type == 3) {
            GameService.gI().inviteFriend(true, -1);
            CCanvas.startWaitDlg(Language.gettingList());
        } else if (this.type == 5) {
            GameService.gI().clanMember((byte) this.page, CCanvas.clanScreen.clan.id);
            CCanvas.startWaitDlg(Language.gettingList());
        } else {
            GameService.gI().requestStrongest(this.page);
            CCanvas.startWaitDlg(Language.gettingList());
        }
    }

    protected void doDeleteFriend() {
        int id = ((PlayerInfo) this.list.elementAt(this.selected)).IDDB;
        GameService.gI().deleteFriend(id);
        CCanvas.startWaitDlg(Language.deleting());
    }

    protected void doAddFriend() {
        CCanvas.inputDlg.setInfo(Language.inputName(), new IAction() {
            @Override
            public void perform() {
                if (CCanvas.inputDlg.tfInput.getText().length() < 4) {
                    CCanvas.startOKDlg(Language.input4());
                    return;
                }
                CCanvas.startWaitDlg(Language.searching());
                GameService.gI().searchFriend(CCanvas.inputDlg.tfInput.getText());
            }
        }, new IAction() {
            @Override
            public void perform() {
                CCanvas.endDlg();
            }
        }, 3);
        CCanvas.inputDlg.show();
    }

    protected void doShowMenu() {
        Vector<Command> menu = new Vector<Command>();
        if (this.type == 2) {
            menu.addElement(this.cmdAddFriend);
            if (this.selected >= 0 && this.selected < this.list.size()) {
                menu.addElement(this.cmdDeleteFriend);
            }
        } else {
            menu.addElement(this.cmdRefresh);
            menu.addElement(this.cmdNext);
        }
        CCanvas.menu.startAt(menu, 0);
    }

    protected void doSendMessage() {
        if (this.selected < 0 || this.selected >= this.list.size()) {
            return;
        }
        if (CCanvas.waitSendMessage > 0) {
            CCanvas.startOKDlg(Language.justSent());
            return;
        }
        PlayerInfo p = (PlayerInfo) this.list.elementAt(this.selected);
        CCanvas.inputDlg.setInfo(String.valueOf(Language.sendTo()) + p.name + ":", new IAction() {
            @Override
            public void perform() {
                PlayerInfo p = (PlayerInfo) list.elementAt(selected);
                String text = CCanvas.inputDlg.tfInput.getText();
                if (text.length() == 0) {
                    return;
                }
                GameService.gI().chatTo(p.IDDB, text);
                CCanvas.startOKDlg(Language.hasSent());
            }
        }, new IAction() {
            @Override
            public void perform() {
                CCanvas.endDlg();
            }
        }, 0);
        CCanvas.inputDlg.show();
    }

    private void doExit() {
        CCanvas.curScr = null;
        ListScr.lastSCreen.show();
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        Cloud.paintCloud(g);
        for (int i = 0; i <= CCanvas.width; i += 32) {
            g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
        }
        this.paintRichList(g);
        g.translate(0, -g.getTranslateY());
        g.setColor(6606845);
        g.fillRect(0, 0, CCanvas.width, 25 + ListScr.ITEM_HEIGHT, false);
        g.setColor(1407674);
        g.fillRect(0, 25, CCanvas.width, ListScr.ITEM_HEIGHT, false);
        if (this.type != 5) {
            if (this.type > 0) {
                Font.bigFont.drawString(g, this.title[this.type], CCanvas.width / 2, 3,
                        mGraphics.HCENTER | mGraphics.TOP);
                Font.normalYFont.drawString(g, Language.name(), 10, 28, 0);
            } else {
                Font.bigFont.drawString(g, MenuScr.subMenuString[7][-this.type], CCanvas.width / 2, 3,
                        mGraphics.HCENTER | mGraphics.TOP);
                if (MenuScr.subMenuString[7][-this.type].equals("DANH SÁCH")) {
                    g.drawImage(ListScr.cup, CCanvas.width / 2 - 60, 4, 0, false);
                }
                Font.normalYFont.drawString(g, Language.name(), 10, 28, 0);
                Font.normalYFont.drawString(g, this.typeList, CCanvas.width - 10, 28, 1);
            }
        } else {
            String clanName = CCanvas.clanScreen.clan.name;
            Font.bigFont.drawString(g, Language.THANHVIENDOI(), 10, 3, 0);
            if (Font.normalYFont.getWidth(clanName) > CCanvas.width - 20) {
                clanName = String.valueOf(Font.normalYFont.splitFontBStrInLine(clanName, CCanvas.width - 20)[0])
                        + "...";
            }
            Font.normalYFont.drawString(g, clanName, 10, 28, 0);
        }
        if (this.list.size() == 0 && this.type == 2) {
            Font.borderFont.drawString(g, Language.chuacoban(), CCanvas.hw, 50, 2);
            Font.borderFont.drawString(g, Language.xinchonmenu(), CCanvas.hw, 75, 2);
            Font.borderFont.drawString(g, Language.themtuphongcho(), CCanvas.hw, 90, 2);
        }
        super.paint(g);
    }

    private void paintRichList(mGraphics g) {
        g.translate(0, ListScr.ITEM_HEIGHT + 25);
        g.translate(0, -this.cmy);
        int y = 0;
        for (int i = 0; i < this.list.size(); ++i) {
            if (i == this.selected) {
                g.setColor(16767817);
                g.fillRect(0, y, CCanvas.width, this.disY, true);
            }
            if (i * this.disY + this.disY > -g.getTranslateY() && i * this.disY < -g.getTranslateY() + CScreen.h) {
                PlayerInfo playerInfo = (PlayerInfo) this.list.elementAt(i);
                String name = playerInfo.name;
                int dis = (this.type == 2 || this.type == 5) ? 9 : 0;
                String tam = String.valueOf(playerInfo.STT) + "." + PlayerInfo.strLevelCaption[playerInfo.nQuanHam2];
                if (playerInfo.level2 != 0) {
                    tam = String.valueOf(tam) + " (" + playerInfo.level2 + ((playerInfo.level2Percen >= 0) ? "+" : "")
                            + playerInfo.level2Percen + "%)";
                }
                Font.borderFont.drawString(g, tam, 30 + dis, y + 3, 0);
                if (playerInfo.aa != null && playerInfo.aa != "") {
                    Font.borderFont.drawString(g, playerInfo.aa, CCanvas.width - 5, y + 19, 1);
                }
                if (playerInfo.clanIcon != null) {
                    g.drawImage(playerInfo.clanIcon, 30, y + 20, 0, true);
                    Font.borderFont.drawString(g, name, 45, y + 19, 0);
                } else {
                    Font.borderFont.drawString(g, name, 30, y + 19, 0);
                }
                CPlayer.paintSimplePlayer(playerInfo.gun, 0, 12, y + ((this.type != 5) ? 35 : 30), 2,
                        playerInfo.myEquip, g);
                PrepareScr.paintQuanHam(playerInfo.nQuanHam2, 22, y + ((this.type != 5) ? 10 : 5),
                        mGraphics.VCENTER | mGraphics.HCENTER, g);
                if (this.type == 2 || this.type == 5) {
                    g.drawImage(PrepareScr.imgReady[playerInfo.isReady ? 1 : 0], 33, y + 12, 3, false);
                }
                if (this.type == 5) {
                    Font.borderFont.drawString(g, playerInfo.clanContribute1, 5, y + 35, 0);
                    Font.borderFont.drawString(g, playerInfo.clanContribute2, 5, y + 51, 0);
                    g.drawImage(ListScr.cup, CCanvas.width - 5, y + 20, mGraphics.TOP | mGraphics.RIGHT, false);
                    Font.borderFont.drawString(g, new StringBuilder(String.valueOf(playerInfo.cup)).toString(),
                            CCanvas.width - 10, y + 40, 1);
                }
            }
            y += this.disY;
        }
    }

    @Override
    public void update() {
        Cloud.updateCloud();
        this.moveCamera();
    }

    @Override
    public void show(CScreen lastScreen) {
        if (this.isNext) {
            int n = 0;
            this.cmtoY = n;
            this.cmy = n;
            this.selected = 0;
        }
        super.show(lastScreen);
        for (int i = 0; i < this.list.size(); ++i) {
            PlayerInfo info = (PlayerInfo) this.list.elementAt(i);
            GameService.gI().getClanIcon(info.clanID);
        }
    }

    public void setList(int type, Vector<PlayerInfo> richestList) {
        this.type = type;
        this.list = richestList;
        this.selected = 0;
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }
        this.selected = 0;
        int n = 0;
        this.cmtoY = n;
        this.cmy = n;
        this.center = new Command(Language.sendMess(), new IAction() {
            @Override
            public void perform() {
                doSendMessage();
            }
        });
        if (type == 2) {
            this.left = new Command(Language.update(), new IAction() {
                @Override
                public void perform() {
                    doShowMenu();
                }
            });
        } else {
            this.left = this.cmdRefresh;
        }
        this.right = new Command(Language.close(), new IAction() {
            @Override
            public void perform() {
                doExit();
            }
        });
        this.disY = 40 + ((type != 5) ? 0 : 30);
        this.cmyLim = richestList.size() * this.disY - (CCanvas.hieght - (ListScr.ITEM_HEIGHT * 2 + ListScr.cmdH)) + 10;
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }
    }

    public void doInvite() {
        if (this.list != null && this.list.size() > 0 && this.selected < this.list.size() && this.selected >= 0) {
            PlayerInfo p = this.list.elementAt(this.selected);
            GameService.gI().inviteFriend(false, p.IDDB);
            CCanvas.startOKDlg(Language.invited());
        }
    }

    public void setInviteList(int type, Vector richestList) {
        this.type = type;
        this.list = richestList;
        this.cmyLim = richestList.size() * 57 - (CCanvas.hieght - ListScr.ITEM_HEIGHT * 3 - 7);
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }
        this.selected = 0;
        int n = 0;
        this.cmtoY = n;
        this.cmy = n;
        this.center = new Command(Language.moichoi(), new IAction() {
            @Override
            public void perform() {
                doInvite();
            }
        });
        this.left = this.cmdRefresh;
        this.right = new Command(Language.close(), new IAction() {
            @Override
            public void perform() {
                doExit();
            }
        });
    }

    public void doUpdate() {
    }

    public void setFriendFind() {
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
    public void onPointerReleased(int xReleased, int yReleased, int index) {
        super.onPointerReleased(xReleased, yReleased, index);
        this.trans = false;
        int b = ListScr.ITEM_HEIGHT;
        int tam = CCanvas.isTouch ? 40 : ListScr.ITEM_HEIGHT;
        int aa = (this.cmtoY - ListScr.ITEM_HEIGHT + yReleased - b) / 40;
        if (aa == this.selected) {
            if (this.center != null) {
                if (CCanvas.isDoubleClick) {
                    this.center.action.perform();
                }
            } else if (this.left != null && CCanvas.isDoubleClick) {
                this.left.action.perform();
            }
        }
        if (aa >= 0 && aa < this.list.size()) {
            this.selected = aa;
        }
        if (CCanvas.isPointer(0, 0, ListScr.w, CCanvas.hieght - ListScr.cmdH, index)
                && Math.abs(xReleased - CCanvas.pxFirst[index]) > 50 && GameMidlet.server != 2) {
            if (xReleased > CCanvas.pxFirst[index]) {
                this.doNext();
            } else {
                this.doBack();
            }
        }
    }
}
