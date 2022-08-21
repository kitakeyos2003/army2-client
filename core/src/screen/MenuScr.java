package screen;

import com.teamobi.mobiarmy2.MainGame;
import Equipment.PlayerEquip;
import player.CPlayer;
import com.teamobi.mobiarmy2.GameMidlet;
import model.Font;
import map.Background;
import CLib.mGraphics;
import shop.ShopEquipment;
import shop.ShopItem;
import player.PM;
import model.PlayerInfo;
import CLib.mSystem;
import effect.Camera;
import effect.Cloud;
import network.GameService;
import model.IAction;
import coreLG.TerrainMidlet;
import coreLG.CCanvas;
import model.Language;
import model.CRes;
import model.Position;
import network.Command;
import java.util.Vector;

public class MenuScr extends CScreen {

    int select;
    int nselect;
    public static byte MENU_CHOINGAY;
    public static byte MENU_CUAHANG;
    public static byte MENU_SUKIEN;
    public static byte MENU_NAPTIEN;
    public static byte MENU_BANGHOI;
    public static byte MENU_TINTUC;
    public static byte MENU_CAUHINH;
    public static byte MENU_QUANGCAO;
    public static byte MENU_NGAUNHIEN;
    public static byte MENU_CHONBAN;
    public static final byte MENU_CHOINHANH = 6;
    public static final byte MENU_XEPHANG = 7;
    public static final byte MENU_TUYCHONKHAC = 5;
    public static final byte MENU_1VS1 = 0;
    public static final byte MENU_2VS2 = 1;
    public static final byte MENU_3VS3 = 2;
    public static final byte MENU_4VS4 = 3;
    public static final byte MENU_DENKHUVUC = 0;
    public static final byte MENU_CHONNHANVAT = 1;
    public static final byte MENU_LEVEL = 2;
    public static final byte MENU_TRANGBI = 3;
    public static final byte MENU_LUYENTAP = 4;
    public static final byte MENU_QUAYSO = 5;
    public static final byte MENU_BANXEPHANG = 0;
    public static final byte MENU_BANBE = 1;
    public static final byte MENU_TOPDOI = 2;
    public static final byte MENU_THANHTICH = 3;
    public static final byte MENU_ARCHIVEMENT = 4;
    public static final byte MENU_TINNHAN = 5;
    public static final byte MENU_DOIMATKHAU = 6;
    public static final byte MENU_CUAHANG_ITEM = 0;
    public static final byte MENU_CUAHANG_TRANGBI = 1;
    public static final byte MENU_CUAHANG_LINHTINH = 2;
    public static final byte MENU_CUAHANG_BIEDOI = 3;
    public static final byte MENU_CAUHINH1 = 0;
    public static final byte MENU_GAMEKHAC = 1;
    public static final byte MENU_ABOUT = 2;
    public static final byte TOPCAOTHU = 0;
    public static final byte TOP_DAIGIAXU = 1;
    public static final byte TOP_DAIGIALUONG = 2;
    public static final byte TOP_CAOTHUTUAN = 3;
    public static final byte TOP_XUTUAN = 4;
    public static String suKienStr;
    public static String linkWapStr;
    public static String linkTeam;
    int yB;
    int hB;
    int hBMax;
    public static boolean isTraining;
    public static String[][] subMenuString;
    private static int curMenuLevel;
    private static int curMenuSelect;
    private static int curSubMenuSelect;
    public static boolean viewInfo;
    public static String[] menuString;
    public static String gameContent;
    public static String gameLink;
    String[] str;
    public static Vector menuCroll;
    public static int[] menuX;
    Command cmdExit;
    Command cmdExitGame;
    int dis;
    int yMenu;
    int nItemShow;
    Position transText1;
    boolean scrollUp;
    boolean scrollDown;
    int dyUp;
    int dyDown;
    boolean levelUp;
    int time;
    public static int dem2;
    public boolean hide;
    public static boolean IS_TEST_POS;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    public int xL;
    int hMenu;
    int W;
    int pa;
    boolean trans;
    int speed;

    public static void getIdMenu(int type) {
        CRes.out(" getIdMenu type = " + type);
        MenuScr.curMenuLevel = 0;
        MenuScr.curMenuSelect = 0;
        MenuScr.curSubMenuSelect = 0;
        if (type == 0) {
            MenuScr.MENU_CHOINGAY = 0;
            MenuScr.MENU_CUAHANG = 1;
            MenuScr.MENU_SUKIEN = 2;
            MenuScr.MENU_NAPTIEN = 3;
            MenuScr.MENU_BANGHOI = 4;
            MenuScr.MENU_TINTUC = 5;
            MenuScr.MENU_CAUHINH = 6;
            MenuScr.menuString = new String[] { Language.startGame(), Language.CUAHANG(), Language.event(),
                    Language.charge(), Language.BIETDOI(), Language.information(), Language.option() };
        }
        if (type == 1) {
            MenuScr.MENU_CHOINGAY = 0;
            MenuScr.MENU_CUAHANG = 1;
            MenuScr.MENU_QUANGCAO = 2;
            MenuScr.MENU_NAPTIEN = 3;
            MenuScr.MENU_BANGHOI = 4;
            MenuScr.MENU_TINTUC = 5;
            MenuScr.MENU_CAUHINH = 6;
            MenuScr.menuString = new String[] { Language.startGame(), Language.CUAHANG(), Language.charge(),
                    Language.BIETDOI(), Language.information(), Language.option() };
        }
        MenuScr.subMenuString[MenuScr.MENU_CHOINGAY] = new String[] { Language.toArea(), Language.selectCharactor(),
                Language.NANGCAP(), Language.INVENTORY(), Language.training(), Language.QUAYSO() };
        MenuScr.subMenuString[MenuScr.MENU_TINTUC] = new String[] { Language.topScore(), Language.FRIEND(),
                Language.TOPCLAN(), Language.achievement(), Language.banthan(), Language.MESS(),
                Language.changePass() };
        MenuScr.subMenuString[6] = new String[] { Language.emptyRoom(), "1 VS 1", "2 VS 2", "3 VS 3", "4 VS 4",
                Language.bossbattle() };
        MenuScr.subMenuString[MenuScr.MENU_CUAHANG] = new String[] { Language.shop(), Language.shop_eq(),
                Language.DODACBIET(), Language.ITEM_DOI() };
        MenuScr.subMenuString[MenuScr.MENU_NGAUNHIEN] = new String[] { "1 VS 1", "2 VS 2", "3 VS 3", "4 VS 4",
                "ĐẤU TRÙM" };
    }

    public MenuScr() {
        this.select = 0;
        this.nselect = 12;
        this.str = new String[] { Language.playnow(), Language.toArea(), Language.selectCharactor(),
                Language.training(), Language.shop(), Language.topScore(), Language.FRIEND(), Language.achievement(),
                Language.MESS(), Language.charge(), Language.option(), Language.otherGame() };
        this.transText1 = new Position(0, 1);
        this.scrollUp = false;
        this.scrollDown = false;
        this.dyDown = 20;
        this.pa = 0;
        this.trans = false;
        this.speed = 1;
        this.nameCScreen = " MenuScr screen!";
        MenuScr.w = CCanvas.width;
        MenuScr.h = CCanvas.hieght;
        this.activeCroll(MenuScr.curMenuLevel, MenuScr.curMenuSelect);
        this.createIAction();
        TerrainMidlet.myInfo.getMyEquip(10);
        if (TerrainMidlet.isVip[TerrainMidlet.myInfo.gun]) {
            TerrainMidlet.myInfo.getVipEquip();
        }
        this.menuScroll = true;
        if (CCanvas.isTouch) {
            this.dis = 32;
        } else {
            this.dis = 20;
        }
        this.dis = 32;
    }

    public void goToGame() {
        CCanvas.quangCaoScr.show();
    }

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

    public void getRectHeight() {
        this.yMenu = (CCanvas.isTouch ? 80 : ((MenuScr.h >> 1) - 10));
        this.yB = this.yMenu - 25;
        this.nItemShow = (CCanvas.hh - 20) / this.dis;
        if (CCanvas.isTouch) {
            this.nItemShow = (CCanvas.hieght - this.yMenu - MenuScr.cmdH - 10) / this.dis;
        }
        if (this.nItemShow >= MenuScr.menuX.length) {
            this.nItemShow = MenuScr.menuX.length;
        }
        int deltaY = this.nItemShow * this.dis + 35;
        this.hBMax = deltaY;
        if (this.hBMax > (CCanvas.isTouch ? (CCanvas.hieght - MenuScr.cmdH + 15 - this.yMenu) : 154)) {
            this.hBMax = (CCanvas.isTouch ? (CCanvas.hieght - MenuScr.cmdH + 15 - this.yMenu) : 154);
        }
    }

    @Override
    public void show() {
        LoginScr.isLoadData = true;
        CRes.err("===================> show MenuScr");
        TerrainMidlet.myInfo.getMyEquip(15);
        TerrainMidlet.myInfo.getVipEquip();
        this.hide = false;
        this.getRectHeight();
        this.startScrollDown();
        super.show();
    }

    public void activeCroll(int level, int select) {
        if (level == 0) {
            MenuScr.menuX = new int[MenuScr.menuString.length];
        } else if (level == 1) {
            MenuScr.menuX = new int[MenuScr.subMenuString[select].length];
        } else if (level == 2) {
            MenuScr.menuX = new int[MenuScr.subMenuString[select].length];
        }
        MenuScr.curMenuLevel = level;
        for (int i = 0; i < MenuScr.menuX.length; ++i) {
            MenuScr.menuX[i] = CCanvas.width >> 1;
        }
        if (level == 0) {
            this.activeCMTOY(this.select = select, MenuScr.menuX.length);
            this.right = this.cmdExitGame;
        } else if (level == 1) {
            MenuScr.curMenuSelect = select;
            this.activeCMTOY(this.select = 0, MenuScr.menuX.length + 2);
            this.right = this.cmdExit;
        }
        int aa = CCanvas.isTouch ? 5 : 0;
        this.cmyLim = this.yMenu + MenuScr.menuX.length * this.dis - (CCanvas.hieght - MenuScr.cmdH - 30 - aa);
    }

    public void startScrollDown() {
        this.hide = false;
        this.scrollDown = true;
        this.hB = 50;
        int aa = CCanvas.isTouch ? 5 : 0;
        this.cmyLim = this.yMenu + MenuScr.menuX.length * this.dis - (CCanvas.hieght - MenuScr.cmdH - 30 - aa);
    }

    public void startScrollUp(boolean levelUp) {
        this.hide = false;
        this.scrollUp = true;
        this.levelUp = levelUp;
    }

    public void scrollUp() {
        if (this.scrollUp) {
            this.hB -= this.hBMax / 4;
            this.hMenu = this.hB - 30;
            if (this.hMenu < 40) {
                this.hMenu = 0;
            }
            if (this.hB < 38) {
                this.hB = 38;
                this.hMenu = 0;
                if (this.levelUp) {
                    getMenuLevel();
                } else {
                    int level = MenuScr.curMenuLevel - 1;
                    if (level < 0) {
                        level = 0;
                    }
                    activeCroll(level, MenuScr.curMenuSelect);
                    getRectHeight();
                }
                this.scrollUp = false;
                if (!this.hide) {
                    this.scrollDown = true;
                }
            }
        }
    }

    public void scrollDown() {
        if (this.scrollDown) {
            if (this.nItemShow > 6) {
                this.nItemShow = 6;
            }
            int hMenuMax = this.nItemShow * this.dis;
            this.hB += this.hBMax / 4;
            if (this.hBMax <= 0) {
                this.hBMax = (CCanvas.isTouch ? (CCanvas.hieght - MenuScr.cmdH + 15 - this.yMenu) : 154);
            }
            this.hMenu = this.hB - 30;
            if (this.hMenu > hMenuMax) {
                this.hMenu = hMenuMax;
            }
            if (this.hB > this.hBMax) {
                this.hB = this.hBMax;
                this.hMenu = hMenuMax;
                this.scrollDown = false;
            }
            CRes.out(String.valueOf(this.getClass().getName()) + " debug: hmenu/hMenuMax/hBMax " + this.hMenu + "/"
                    + hMenuMax + "/" + this.hBMax);
        }
    }

    public void createIAction() {
        IAction selectAction = new IAction() {
            @Override
            public void perform() {
                doFire();
            }
        };
        this.cmdExit = new Command(Language.back(), new IAction() {
            @Override
            public void perform() {
                hide = false;
                startScrollUp(false);
            }
        });
        this.cmdExitGame = new Command(Language.exit(), new IAction() {
            @Override
            public void perform() {
                doExit();
            }
        });
        this.left = new Command(Language.select(), selectAction);
        this.center = null;
        this.right = this.cmdExitGame;
    }

    protected void doExit() {
        IAction actionSkip = new IAction() {
            @Override
            public void perform() {
                GameService.gI().signOut();
            }
        };
        CCanvas.msgdlg.setInfo(Language.wantExit(), new Command(Language.yes(), actionSkip),
                new Command("", actionSkip), new Command(Language.no(), new IAction() {
                    @Override
                    public void perform() {
                        CCanvas.endDlg();
                    }
                }));
        CCanvas.msgdlg.show();
    }

    @Override
    public void update() {
        GameScr.sm.update();
        Cloud.updateCloud();
        Cloud.balloonUpdate();
        super.update();
    }

    @Override
    public void mainLoop() {
        super.mainLoop();
        Camera.x += 3;
        this.moveCamera();
        this.scrollDown();
        this.scrollUp();
    }

    public void doClan() {
        PlayerInfo m = TerrainMidlet.myInfo;
        if (m.clanID > 0) {
            GameService.gI().clanInfo(m.clanID);
            CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 2000L + mSystem.currentTimeMillis(),
                    new IAction() {
                        @Override
                        public void perform() {
                            CCanvas.startOKDlg(Language.noTimeRespond());
                        }
                    });
            ClanScreen.isFromMenu = true;
        } else {
            CCanvas.startYesNoDlg(Language.chuacodoi(), new IAction() {
                @Override
                public void perform() {
                    doGoToTeam(MenuScr.linkTeam);
                }
            });
        }
    }

    public void doLevelUp() {
        if (CCanvas.levelScreen == null) {
            CCanvas.levelScreen = new LevelScreen();
        }
        CCanvas.levelScreen.show(this);
    }

    public void doEquip() {
        CCanvas.equipScreen.init();
        CCanvas.equipScreen.show(this);
    }

    public void doTopClan() {
        ClanScreen.isFromMenu = false;
        CCanvas.startOKDlg(Language.pleaseWait());
        GameService.gI().topClan((byte) 0);
    }

    private void doGoToTeam(String link) {
        mSystem.openUrl(link);
    }

    public void getMenuLevel() {
        if (MenuScr.curMenuLevel == 0) {
            if (this.select == MenuScr.MENU_CHOINGAY) {
                this.hide = false;
                this.activeCroll(1, this.select);
            } else if (this.select == MenuScr.MENU_CUAHANG) {
                this.hide = false;
                this.activeCroll(1, this.select);
            } else if (this.select == MenuScr.MENU_NAPTIEN) {
                this.hide = true;
                GameService.gI().requestChargeMoneyInfo2((byte) 0, "");
                this.doChargeMoney();
            } else if (this.select == MenuScr.MENU_QUANGCAO) {
                this.goToGame();
            } else if (this.select == MenuScr.MENU_SUKIEN) {
                this.hide = true;
                CCanvas.startYesNoDlg(Language.vaoxemtin(), new IAction() {
                    @Override
                    public void perform() {
                        doGoToWap(MenuScr.linkWapStr);
                    }
                }, new IAction() {
                    @Override
                    public void perform() {
                        if (cmdExit != null) {
                            cmdExit.action.perform();
                        }
                        CCanvas.endDlg();
                    }
                });
            } else if (this.select == MenuScr.MENU_BANGHOI) {
                this.hide = true;
                this.doClan();
            } else if (this.select == MenuScr.MENU_TINTUC) {
                this.activeCroll(1, this.select);
            } else if (this.select == MenuScr.MENU_CAUHINH) {
                this.hide = true;
                if (CCanvas.configScr == null) {
                    CCanvas.configScr = new ConfigScr();
                }
                CCanvas.configScr.show();
            }
        } else if (MenuScr.curMenuLevel == 1) {
            if (MenuScr.curMenuSelect == MenuScr.MENU_CHOINGAY) {
                this.hide = true;
                switch (this.select) {
                    case 0: {
                        PrepareScr.isBossRoom = false;
                        this.doRequestRoomList();
                        this.hide = false;
                        this.activeCroll(2, this.select);
                        break;
                    }
                    case 1: {
                        this.doChangePlayer();
                        break;
                    }
                    case 5: {
                        CCanvas.luckyGame.show(this);
                        break;
                    }
                    case 4: {
                        GameScr.trainingMode = true;
                        MenuScr.isTraining = true;
                        GameService.gI().trainingMap();
                        CCanvas.startOKDlg(Language.pleaseWait());
                        break;
                    }
                    case 2: {
                        this.hide = true;
                        MenuScr.viewInfo = true;
                        GameService.gI().charactorInfo();
                        CCanvas.startOKDlg(Language.pleaseWait());
                        this.doLevelUp();
                        break;
                    }
                    case 3: {
                        this.hide = true;
                        this.doEquip();
                        break;
                    }
                }
            } else if (MenuScr.curMenuSelect == MenuScr.MENU_TINTUC) {
                this.hide = true;
                switch (this.select) {
                    case 0: {
                        this.doShowLeaderBoard((byte) 1);
                        this.hide = false;
                        this.activeCroll(2, 7);
                        break;
                    }
                    case 1: {
                        this.doShowFriend();
                        break;
                    }
                    case 2: {
                        this.doTopClan();
                        break;
                    }
                    case 3: {
                        this.doShowInfo();
                        if (CCanvas.missionScreen == null) {
                            CCanvas.missionScreen = new MissionScreen();
                        }
                        GameService.gI().mission((byte) 0, (byte) (-1));
                        CCanvas.startOKDlg(Language.pleaseWait());
                        break;
                    }
                    case 4: {
                        GameService.gI().requestInfoOf(TerrainMidlet.myInfo.IDDB);
                        CCanvas.startOKDlg(Language.pleaseWait());
                        break;
                    }
                    case 5: {
                        this.doViewMessage();
                        break;
                    }
                    case 6: {
                        this.doChangePass();
                        break;
                    }
                }
                // switch (this.select) {
                // case 1: {
                // this.doGoToWap("");
                // break;
                // }
                // }
            } else if (MenuScr.curMenuLevel == MenuScr.MENU_CUAHANG) {
                this.hide = true;
                switch (this.select) {
                    case 0: {
                        this.hide = true;
                        this.doShowShopItem();
                        break;
                    }
                    case 1: {
                        this.hide = true;
                        GameService.gI().getShopEquip();
                        CCanvas.startOKDlg(Language.pleaseWait());
                        break;
                    }
                    case 2: {
                        this.hide = true;
                        GameService.gI().getShopLinhtinh((byte) 0, (byte) (-1), (byte) (-1), (byte) (-1));
                        CCanvas.startOKDlg(Language.pleaseWait());
                        break;
                    }
                    case 3: {
                        this.hide = true;
                        GameService.gI().getShopBietDoi((byte) 0, (byte) (-1), (byte) (-1));
                        CCanvas.startOKDlg(Language.pleaseWait());
                        break;
                    }
                }
            }
        } else if (MenuScr.curMenuLevel == 2) {
            this.hide = true;
            if (MenuScr.curMenuSelect == MenuScr.MENU_CHOINGAY) {
                if (this.select == 0) {
                    GameService.gI().requestEmptyRoom((byte) 0, (byte) (-1), null);
                } else {
                    this.doPlayNow((byte) this.select);
                }
            } else if (MenuScr.curMenuSelect == MenuScr.MENU_TINTUC) {
                GameService.gI().bangxephang((byte) this.select, 0);
            }
        } else if (MenuScr.curMenuLevel == 3 && MenuScr.curMenuSelect == 0) {
            this.hide = true;
            this.doPlayNow((byte) (this.select - 1));
        }
        this.getRectHeight();
    }

    protected void doFire() {
        this.startScrollUp(true);
    }

    private void doShowLeaderBoard(byte type) {
        CCanvas.startWaitDlg(Language.pleaseWait());
        GameService.gI().requestStrongest(0);
    }

    private void doChangePlayer() {
        if (CCanvas.changePScr != null) {
            CCanvas.changePScr.show(this);
        } else {
            (CCanvas.changePScr = new ChangePlayerCSr()).show(this);
        }
    }

    private void doGoToWap(String link) {
        mSystem.openUrl(link);
    }

    public void doTraining(byte MapID, byte timeInterval, short[] playerX, short[] playerY, short[] maxHP,
            short[] equipID) {
        CCanvas.prepareScr.playerInfos.removeAllElements();
        if (!MenuScr.IS_TEST_POS) {
            PlayerInfo me = new PlayerInfo();
            me.name = "";
            me.gun = TerrainMidlet.myInfo.gun;
            me.level2 = 1;
            me.getQuanHam();
            for (int i = 0; i < 5; ++i) {
                me.equipID[me.gun][i] = equipID[i];
            }
            me.getMyEquip(11);
            CCanvas.prepareScr.playerInfos.addElement(me);
            me = new PlayerInfo();
            me.name = "Enemy";
            me.gun = TerrainMidlet.myInfo.gun;
            me.level2 = 1;
            me.getQuanHam();
            for (int i = 0; i < 5; ++i) {
                me.equipID[me.gun][i] = equipID[i];
            }
            me.getMyEquip(12);
            CCanvas.prepareScr.playerInfos.addElement(me);
        }
        if (MenuScr.IS_TEST_POS) {
            for (int j = 0; j < 8; ++j) {
                PlayerInfo pos = new PlayerInfo();
                pos.name = "p" + j;
                CCanvas.prepareScr.playerInfos.addElement(pos);
            }
            CCanvas.gameScr.initGame(MapID, timeInterval, playerX, playerY, maxHP, 0);
        } else {
            CCanvas.gameScr.initGame(MapID, timeInterval, playerX, playerY, maxHP, 0);
        }
        CCanvas.gameScr.show();
        GameScr.trainingMode = true;
        GameScr.cam.setPlayerMode(0);
        GameScr.pm.setNextPlayer((byte) 0);
        PM.p[0].isCom = false;
        PM.p[0].item = PrepareScr.getStrainingItem();
        GameScr.trainingStep = 0;
        PM.p[0].hp = 70;
        GameScr.myIndex = 0;
    }

    private void doShowShopItem() {
        ShopItem shopItemScr = CCanvas.shopItemScr;
        ShopItem.resetItemBuy();
        CCanvas.shopItemScr.show(this);
    }

    public void doEquipItem() {
        if (CCanvas.shopEquipScr == null) {
            CCanvas.shopEquipScr = new ShopEquipment();
        }
        CCanvas.shopEquipScr.show(this);
    }

    private void doShowInfo() {
        GameService.gI().requestInfoOf(TerrainMidlet.myInfo.IDDB);
        CCanvas.startWaitDlg(Language.pleaseWait());
    }

    public void doChargeMoney() {
        if (!CCanvas.isIos()) {
            if (CCanvas.moneyScr != null) {
                CCanvas.moneyScr.show();
            }
        } else if (CCanvas.moneyScrIOS != null) {
            CCanvas.moneyScrIOS.show();
        }
    }

    public void doRequestRoomList() {
        GameService.gI().requestRoomList();
        CCanvas.startWaitDlg(Language.pleaseWait());
    }

    private void doPlayNow(byte select) {
        if (select == 5) {
            PrepareScr.isBossRoom = true;
        } else {
            PrepareScr.isBossRoom = false;
        }
        GameService.gI().joinAnyBoard(select);
        CCanvas.startWaitDlg(Language.pleaseWait());
    }

    private void doShowFriend() {
        GameService.gI().requestFriendList();
        CCanvas.startWaitDlg(Language.pleaseWait());
    }

    private void doViewMessage() {
        CCanvas.msgScr.show(this);
    }

    private void doChangePass() {
        final String[] text = new String[3];
        final String[] name = { Language.oldPass(), Language.newPass(), Language.retypeNewPass() };
        CCanvas.inputDlg.setInfo(name[0], new IAction() {
            @Override
            public void perform() {
                if (CCanvas.inputDlg.tfInput.getText().equals("")) {
                    CCanvas.startOKDlg(Language.plOldPass(), new IAction() {
                        @Override
                        public void perform() {
                            CCanvas.inputDlg.show();
                        }
                    });
                    return;
                }
                text[0] = CCanvas.inputDlg.tfInput.getText();
                CCanvas.inputDlg.setInfo(name[1], new IAction() {
                    @Override
                    public void perform() {
                        if (CCanvas.inputDlg.tfInput.getText().equals("")) {
                            CCanvas.startOKDlg(Language.plNewPass(), new IAction() {
                                @Override
                                public void perform() {
                                    CCanvas.inputDlg.show();
                                }
                            });
                            return;
                        }
                        text[1] = CCanvas.inputDlg.tfInput.getText();
                        CCanvas.inputDlg.setInfo(name[2], new IAction() {
                            @Override
                            public void perform() {
                                if (CCanvas.inputDlg.tfInput.getText().equals("")) {
                                    CCanvas.startOKDlg(Language.plRetypeNewPass(), new IAction() {
                                        @Override
                                        public void perform() {
                                            CCanvas.inputDlg.show();
                                        }
                                    });
                                    return;
                                }
                                text[2] = CCanvas.inputDlg.tfInput.getText();
                                if (text[2].equals(text[1])) {
                                    GameService.gI().requestChangePass(text[0], text[1]);
                                    CCanvas.endDlg();
                                    CCanvas.startOKDlg(Language.pleaseWait());
                                } else {
                                    CCanvas.startOKDlg(Language.newPassNotMath());
                                }
                            }
                        }, new IAction() {
                            @Override
                            public void perform() {
                                startScrollDown();
                                CCanvas.endDlg();
                            }
                        }, 2);
                    }
                }, new IAction() {
                    @Override
                    public void perform() {
                        startScrollDown();
                        CCanvas.endDlg();
                    }
                }, 2);
                CCanvas.inputDlg.show();
            }
        }, new IAction() {
            @Override
            public void perform() {
                startScrollDown();
                CCanvas.endDlg();
            }
        }, 2);
        CCanvas.inputDlg.show();
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
        Background.paintTree(g);
        Cloud.paintBalloonWithCloud(g);
        super.paint(g);
        if (this.hide) {
            return;
        }
        int nTab = 3;
        CScreen.paintBorderRect(g, this.yB, nTab, this.hB, null);
        if (MenuScr.curMenuLevel == 1 || MenuScr.curMenuLevel == 2) {
            int pInfoY = 0;
            int pInfoX = 0;
            pInfoX = CCanvas.width / 2 - this.W / 2;
            pInfoY = this.yB;
            if (MenuScr.curMenuSelect == MenuScr.MENU_CHOINGAY) {
                this.paintInformation(g, pInfoX, pInfoY);
            }
            if (!CCanvas.isTouch) {
                Font.bigFont.drawString(g, MenuScr.menuString[MenuScr.curMenuSelect], MenuScr.w >> 1, 5, 2);
            }
        }
        int x = CCanvas.width / 2 - this.W / 2;
        this.W = nTab * 32 + 23 + 33;
        g.setClip(x + 5, this.yMenu, this.W - 10, this.hB);
        g.translate(0, -this.cmy);
        int a = CCanvas.isTouch ? 5 : 0;
        if (!this.scrollUp) {
            g.setColor(3374591);
            g.fillRect(x, this.yMenu + this.select * this.dis + a, this.W, 22, true);
            int yS = this.yMenu + this.select * this.dis + a;
            g.drawImage(GameScr.arrowMenu, x, yS, 0, true);
            g.drawRegion(GameScr.arrowMenu, 0, 0, 14, 21, 3, x + this.W, yS, 24, true);
        }
        for (int i = 0; i < MenuScr.menuX.length; ++i) {
            if (MenuScr.curMenuLevel == 0) {
                int cc = 0;
                String strMenu = MenuScr.menuString[i];
                int bb = Font.normalFont.getWidth(strMenu);
                if (bb > 85) {
                    this.transTextLimit(this.transText1, bb - 85);
                }
                cc = this.transText1.x;
                Font.bigFont.drawString(g, MenuScr.menuString[i], (bb > 85) ? (x + 8 + cc) : MenuScr.menuX[i],
                        this.yMenu + i * this.dis + a, (bb > 85) ? 0 : 2);
            } else if (MenuScr.curMenuLevel == 1) {
                Font.bigFont.drawString(g, MenuScr.subMenuString[MenuScr.curMenuSelect][i], MenuScr.menuX[i],
                        this.yMenu + i * this.dis + a, 2);
            } else if (MenuScr.curMenuLevel == 2) {
                if (MenuScr.curMenuSelect == MenuScr.MENU_CHOINGAY) {
                    Font.bigFont.drawString(g, MenuScr.subMenuString[6][i], MenuScr.menuX[i],
                            this.yMenu + i * this.dis + a, 2);
                }
                if (MenuScr.curMenuSelect == MenuScr.MENU_TINTUC) {
                    Font.bigFont.drawString(g, MenuScr.subMenuString[7][i], MenuScr.menuX[i],
                            this.yMenu + i * this.dis + a, 2);
                }
            } else if (MenuScr.curMenuLevel == 3 && MenuScr.curMenuSelect == 0) {
                Font.bigFont.drawString(g, MenuScr.subMenuString[MenuScr.MENU_NGAUNHIEN][i], MenuScr.menuX[i],
                        this.yMenu + i * this.dis + a, 2);
            }
        }
        g.translate(0, this.cmy);
        GameMidlet.serverInformation(Font.normalFont, g);
    }

    private void paintInformation(mGraphics g, int pInfoX, int pInfoY) {
        pInfoY -= 5;
        Font.borderFont.drawString(g, String.valueOf(Language.name()) + ": " + TerrainMidlet.myInfo.name, pInfoX + 20,
                pInfoY - Font.borderFont.getHeight() * 3, 0, false);
        Font.borderFont.drawString(g,
                "Level: " + TerrainMidlet.myInfo.level2 + "   " + Language.cup() + ": " + TerrainMidlet.myInfo.cup,
                pInfoX + 20, pInfoY - Font.borderFont.getHeight() * 2, 0, false);
        Font.borderFont.drawString(g, String.valueOf(TerrainMidlet.myInfo.xu) + Language.xu() + " - "
                + TerrainMidlet.myInfo.luong + Language.luong(), pInfoX + 20, pInfoY - Font.borderFont.getHeight(), 0,
                false);
        int quanHamType = TerrainMidlet.myInfo.nQuanHam2;
        g.drawRegion(PrepareScr.imgQuanHam, 0, 12 * quanHamType, 12, 12, 0, pInfoX + 6,
                pInfoY - Font.borderFont.getHeight() * 2 - 5, mGraphics.TOP | mGraphics.HCENTER, false);
        PlayerEquip e = null;
        if (TerrainMidlet.isVip[TerrainMidlet.myInfo.gun]) {
            e = TerrainMidlet.myInfo.myVipEquip;
        } else {
            e = TerrainMidlet.myInfo.myEquip;
        }
        CPlayer.paintSimplePlayer(TerrainMidlet.myInfo.gun, (CCanvas.gameTick % 10 > 2) ? 5 : 4, pInfoX + 6, pInfoY, 0,
                e, g);
    }

    public void paintRoundR(int x, int y, int W, int H, mGraphics g) {
        g.setColor(8040447);
        g.fillRoundRect(x, y, W, H, 10, 10, false);
        g.setColor(16777215);
        g.drawRoundRect(x, y, W, H, 10, 10, false);
    }

    @Override
    public void onPointerPressed(int x, int y2, int index) {
    }

    @Override
    public void onPointerReleased(int x, int y2, int index) {
        if (this.scrollDown || this.scrollUp) {
            return;
        }
        super.onPointerReleased(x, y2, index);
        this.trans = false;
        if (CCanvas.isPointer(0, this.yMenu, CCanvas.width, this.nItemShow * this.dis + 33, index)) {
            int currSelect = (y2 - this.yMenu + this.cmy) / this.dis;
            if (currSelect < 0) {
                currSelect = 0;
            }
            if (currSelect > MenuScr.menuX.length - 1) {
                currSelect = MenuScr.menuX.length - 1;
            }
            if (!MainGame.touchDrag) {
                if (this.select != currSelect) {
                    this.select = currSelect;
                } else {
                    this.doFire();
                }
            }
        }
    }

    @Override
    public void onPointerDragged(int x, int y2, int index) {
        if (this.scrollDown || this.scrollUp) {
            return;
        }
        super.onPointerDragged(x, y2, index);
        if (!this.trans) {
            this.pa = this.cmy;
            this.trans = true;
        }
        if (!CCanvas.isPc()) {
            this.speed = 3;
        }
        if (CCanvas.isTouch) {
            this.cmtoY = this.pa + (CCanvas.pyFirst[index] - y2) * this.speed;
        }
        this.cmtoY = CScreen.Clamp(this.cmtoY, 0, (MenuScr.menuX.length - this.nItemShow) * this.dis);
    }

    private void activeCMTOY(int select, int nselect) {
        this.cmtoY = select * this.dis - this.hMenu / 2;
        if (CCanvas.isTouch && select != 0) {
            this.cmtoY += 10;
        }
        int hMenuMax = this.nItemShow * this.dis;
        if (this.cmtoY > nselect * this.dis - hMenuMax) {
            this.cmtoY = nselect * this.dis - hMenuMax;
        }
        if (this.cmtoY < 0) {
            this.cmtoY = 0;
        }
    }

    static {
        MenuScr.MENU_NGAUNHIEN = 8;
        MenuScr.MENU_CHONBAN = 9;
        MenuScr.isTraining = false;
        (MenuScr.subMenuString = new String[10][])[7] = new String[] { Language.topCaothu(), Language.topDaiGiaXu(),
                Language.topDaigiaLuong(), Language.topCaothuTuan(), Language.topXuTuan() };
        MenuScr.subMenuString[5] = new String[] { Language.option(), Language.otherGame(), "ABOUT" };
        MenuScr.menuCroll = new Vector();
        MenuScr.dem2 = 0;
        MenuScr.IS_TEST_POS = false;
    }
}
