package screen;

import CLib.Image;
import model.IAction2;
import model.FilePack;
import coreLG.CONFIG;
import model.ChatPopup;
import item.Item;
import com.teamobi.mobiarmy2.GameMidlet;
import coreLG.TerrainMidlet;
import player.Boss;
import com.teamobi.mobiarmy2.MainGame;
import model.Font;
import effect.Effect;
import CLib.mGraphics;
import effect.GiftEffect;
import network.Session_ME;
import network.GameService;
import model.IAction;
import network.Command;
import model.CRes;
import CLib.mSystem;
import item.Bullet;
import model.Language;
import player.CPlayer;
import map.Background;
import coreLG.CCanvas;
import Equipment.Equip;
import effect.Snow;
import model.FrameImage;
import model.CTime;
import effect.SmokeManager;
import effect.Explosion;
import item.BM;
import effect.Camera;
import player.PM;
import map.MM;
import model.TField;
import model.TimeBomb;
import java.util.Vector;
import CLib.mImage;

public class GameScr extends CScreen {

    static int WIDTH;
    public static int HEIGHT;
    public static final byte GRAPHIC_HIGH = 0;
    public static final byte GRAPHIC_MEDIUM = 1;
    public static final byte GRAPHIC_LOW = 2;
    public static byte curGRAPHIC_LEVEL;
    public static boolean whiteEffect;
    public static boolean electricEffect;
    public static boolean freezeEffect;
    public static boolean suicideEffect;
    public static boolean poisonEffect;
    public boolean nukeEffect;
    public int tN;
    public int tW;
    public int wE;
    public int xE;
    public int tE;
    public static int xNuke;
    public static int yNuke;
    public static int yElectric;
    public static int xElectric;
    public static int xFreeze;
    public static int yFreeze;
    public static int xSuicide;
    public static int ySuicide;
    public static int xPoison;
    public static int yPoison;
    public static mImage airFighter;
    public static mImage imgMode;
    public static mImage lock;
    public static mImage lockImg;
    public static mImage crosshair;
    public static mImage imgInfoPopup;
    public static mImage s_imgITEM;
    public static mImage imgTeam;
    public static mImage imgPlane;
    public static mImage logoGame;
    public static mImage logoII;
    public static mImage imgQuanHam;
    public static mImage imgBack;
    public static mImage imgMap;
    public static mImage trangbiTileImg;
    public static mImage shopTileImg;
    public static mImage tienBarImg;
    public static mImage soLuongBarImg;
    public static mImage buyBar;
    public static mImage ladySexyImg;
    public static mImage imgCurPos;
    public static mImage imgSmallCloud;
    public static mImage imgArrowRed;
    public static mImage imgRoomStat;
    public static mImage imgTrs;
    public static mImage imgIcon;
    public static mImage itemBarImg;
    public static mImage imgChat;
    public static mImage s_imgTransparent;
    public static mImage arrowMenu;
    public static mImage wind1;
    public static mImage wind2;
    public static mImage wind3;
    public static mImage trai;
    public static mImage phai;
    public static mImage crossHair2;
    public static mImage nut_up;
    public static mImage nut_down;
    public static mImage[] imgReady;
    public static mImage[] imgMsg;
    public static Vector<TimeBomb> timeBombs;
    public static TField tfChat;
    public static MM mm;
    public static PM pm;
    public static Camera cam;
    public static BM bm;
    public static Vector<Explosion> exs;
    public static SmokeManager sm;
    public static CTime time;
    public Vector<GiftEffect> vGift;
    public static int windx;
    public static int windy;
    int teamSize;
    int mapID;
    public static boolean trainingMode;
    mImage pause;
    public static int tickCount;
    public static byte ID_Turn;
    public static mImage s_imgAngle;
    public static FrameImage s_frBar;
    public static FrameImage s_frWind;
    public static boolean isDarkEffect;
    public static int s_iPlane_x;
    public static int s_iPlane_y;
    public static int s_iBombTargetX;
    public static byte room;
    public static byte board;
    byte exBonus;
    int moneyBonus;
    int moneyY;
    public static String res;
    int moneyBonus2;
    int moneyY2;
    boolean isMoney2Fly;
    int whoGetMoney2;
    boolean isMoneyFly;
    int nBoLuot;
    private boolean isSelectItem;
    public static int curItemSelec;
    private long timeDelayClosePauseMenu;
    public static byte myIndex;
    Vector<String> chatList;
    int chatDelay;
    int MAX_CHAT_DELAY;
    Snow snow;
    public static boolean iconOnOf;
    public boolean isShowPausemenu;
    public long timeShowPauseMenu;
    int chatWait;
    boolean isChat;
    public boolean isFly;
    public String text;
    public int xFly;
    public int yFly;
    public int tFly;
    public Equip equip;
    public static int trainingStep;
    public static boolean isUpdateHP;
    int left;
    int right;
    int up;
    int down;
    public static boolean cantSee;
    public byte whoCantSee;
    public static int xL;
    public static int yL;
    public static int xR;
    public static int yR;
    public static int xF;
    public static int yF;
    public static int xU;
    public static int yU;
    public static int xD;
    public static int yD;
    static mImage imgArrow;
    public static int windAngle;
    public static int windPower;
    public int t1;
    public int t2;
    public int dem;
    boolean b;
    public static byte[] num;
    boolean isPressXL;
    boolean isPressXR;
    boolean isPressXF;

    public GameScr() {
        this.tW = 0;
        this.tE = 0;
        this.vGift = new Vector<>();
        this.moneyY = -100;
        this.chatList = new Vector<>();
        this.MAX_CHAT_DELAY = 40;
        this.chatWait = 0;
        this.text = "";
        this.left = 0;
        this.right = 1;
        this.up = 2;
        this.down = 3;
        this.initGamescr();
    }

    public void initGamescr() {
        GameScr.mm = new MM();
        GameScr.pm = new PM();
        GameScr.cam = new Camera();
        GameScr.bm = new BM();
        GameScr.sm = new SmokeManager();
        GameScr.time = new CTime();
        GameScr.tfChat = new TField();
        GameScr.tfChat.x = 2;
        GameScr.tfChat.y = CCanvas.hieght - GameScr.ITEM_HEIGHT - 25;
        if (CCanvas.isTouch) {
            GameScr.tfChat.y = CCanvas.hieght - CScreen.cmdH - GameScr.ITEM_HEIGHT;
        }
        GameScr.tfChat.width = CCanvas.width - 4;
        GameScr.tfChat.height = GameScr.ITEM_HEIGHT + 2;
        GameScr.tfChat.setisFocus(true);
        GameScr.tfChat.nameDebug = "Tfield ====> Gamescr";
        this.nameCScreen = "GameScr screen!";
    }

    public void initGame(byte MapID, byte timeInterval, short[] playerX, short[] playerY, short[] maxHP, int team) {
        GameScr.isDarkEffect = false;
        GameScr.s_iPlane_x = -1;
        GameScr.s_iPlane_y = -1;
        GameScr.s_iBombTargetX = -1;
        this.isMoneyFly = false;
        GameScr.res = "";
        this.nBoLuot = 3;
        GameScr.iconOnOf = true;
        if (GameScr.curGRAPHIC_LEVEL != 2) {
            GameScr.mm.createBackGround();
        }
        switch (GameScr.curGRAPHIC_LEVEL) {
            case 0:
            case 1: {
                if (!Background.isLoadImage) {
                    Background.isLoadImage = true;
                    Background.initImage();
                    break;
                }
                break;
            }
            case 2: {
                Background.removeImage();
                break;
            }
        }
        this.initGamescr();
        GameScr.pm.init();
        CPlayer.isStopFire = false;
        GameScr.bm.onInitSpecialBullet();
        GameScr.exs = new Vector<Explosion>();
        GameScr.timeBombs = new Vector<TimeBomb>();
        if (team != 0) {
            CCanvas.gameScr.flyText("+" + team + Language.diemdongdoi(), CCanvas.width / 2, CCanvas.hieght - 50, null);
        }
        GameScr.time.initTimeInterval(timeInterval);
        this.snow = null;
        if (GameScr.curGRAPHIC_LEVEL != 2) {
            if (Background.curBGType == 2) {
                (this.snow = new Snow()).startSnow(0);
            }
            if (Background.curBGType == 10) {
                this.snow = new Snow();
                if (MM.mapID == 34) {
                    this.snow.waterY = 35;
                }
                if (MM.mapID == 35) {
                    this.snow.waterY = 30;
                }
                if (MM.mapID == 38) {
                    this.snow.waterY = 80;
                }
                if (MM.mapID == 39) {
                    this.snow.waterY = 0;
                }
                this.snow.startSnow(1);
            }
        }
        GameScr.pm.initPlayer(playerX, playerY, maxHP);
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null) {
                PM.p[i].cantSee = false;
            }
        }
        GameScr.cantSee = false;
        Bullet.webId = 200;
    }

    private void onDragCamera(int xDrag, int yDrag, int index) {
        if (Camera.mode == 0) {
            int deltaX = xDrag - CCanvas.pxFirst[index];
            int deltaY = yDrag - CCanvas.pyFirst[index];
            if (deltaX > 1) {
                Camera.dx2 -= Math.abs(deltaX) >> 2;
            } else if (deltaX < -1) {
                Camera.dx2 += Math.abs(deltaX) >> 2;
            }
            if (deltaY > 1) {
                Camera.dy2 -= Math.abs(deltaY) >> 2;
            } else if (deltaY < -1) {
                Camera.dy2 += Math.abs(deltaY) >> 2;
            }
        }
    }

    public void selectedItemPanelRealeased(int x, int y2, int index) {
        int num = PM.getMyPlayer().item.length;
        int itemW = 3;
        int itemH = 2;
        int xItem = CCanvas.hw - 80;
        int yItem = CCanvas.hh - 33;
        if (CCanvas.isPointer(xItem, yItem, 170, 90, index)) {
            int aa = (y2 - yItem) / 40 * 4 + (x - xItem) / 40;
            if (aa >= 0 && aa <= 7) {
                if (aa != GameScr.curItemSelec) {
                    GameScr.curItemSelec = aa;
                } else if (CCanvas.isDoubleClick) {
                    int[] itemList = PM.getMyPlayer().item;
                    if (GameScr.trainingMode) {
                        clearKey();
                        PM.getMyPlayer().UseItem(itemList[GameScr.curItemSelec], true, GameScr.curItemSelec);
                        if (itemList[GameScr.curItemSelec] == 0) {
                            CPlayer cPlayer2;
                            CPlayer cPlayer = cPlayer2 = PM.p[0];
                            cPlayer2.hp += 30;
                        }
                        this.isSelectItem = false;
                        this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 300L;
                    } else {
                        CRes.out("itemList: " + itemList.length);
                        for (int i = 0; i < itemList.length; ++i) {
                            CRes.out("itemList: " + i + "_value_" + itemList[i]);
                        }
                        CRes.out("index/idItem/ItemUsed: " + GameScr.curItemSelec + "/" + itemList[GameScr.curItemSelec]
                                + "/" + PM.getMyPlayer().itemUsed);
                        if (PM.getMyPlayer().itemUsed != -1 || itemList[GameScr.curItemSelec] == -2
                                || itemList[GameScr.curItemSelec] == -1) {
                            this.isSelectItem = false;
                            return;
                        }
                        if (GameScr.pm.isYourTurn()) {
                            if (PrepareScr.currLevel == 7) {
                                if (GameScr.num[GameScr.curItemSelec] != 0) {
                                    PM.getMyPlayer().UseItem(itemList[GameScr.curItemSelec], false,
                                            GameScr.curItemSelec);
                                    this.isSelectItem = false;
                                    this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 300L;
                                }
                            } else {
                                PM.getMyPlayer().UseItem(itemList[GameScr.curItemSelec], false, GameScr.curItemSelec);
                                this.isSelectItem = false;
                                this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 300L;
                            }
                        }
                        clearKey();
                    }
                }
            }
        }
        if (!CCanvas.isPointer(xItem - 50, yItem - 50, 210, 130, index) && this.isSelectItem) {
            this.isSelectItem = false;
            this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 550L;
        }
    }

    public void flyText(String text, int xFly, int yFly, Equip equip) {
        this.isFly = true;
        this.text = text;
        this.xFly = xFly;
        this.yFly = yFly;
        this.equip = equip;
    }

    private void doShowPauseMenu() {
        this.isShowPausemenu = true;
        Vector<Command> menu = new Vector<Command>();
        menu.addElement(new Command(Language.CONTINUE(), new IAction() {
            @Override
            public void perform() {
                isShowPausemenu = false;
                timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
            }
        }));
        if (GameScr.pm.isYourTurn()) {
            menu.addElement(new Command(Language.USEITEM(), new IAction() {
                @Override
                public void perform() {
                    if (GameScr.pm.isYourTurn()) {
                        isSelectItem = true;
                    }
                    isShowPausemenu = false;
                    GameScr.curItemSelec = 7;
                    timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                }
            }));
        }
        if (GameScr.pm.isYourTurn() && PM.getCurPlayer().isAngry && !PM.getCurPlayer().isUsedItem) {
            menu.addElement(new Command(Language.SPECIAL(), new IAction() {
                @Override
                public void perform() {
                    GameService.gI().useItem((byte) 100);
                    PM.getCurPlayer().isUsedItem = true;
                    PM.getCurPlayer().itemUsed = 100;
                    PM.getCurPlayer().angryX = 0;
                    PM.getCurPlayer().currAngry = 0;
                    PM.getCurPlayer().is2TurnItem = true;
                    isShowPausemenu = false;
                    timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                }
            }));
        }
        menu.addElement(new Command(Language.battaticon(), new IAction() {
            @Override
            public void perform() {
                GameScr.iconOnOf = !GameScr.iconOnOf;
                isShowPausemenu = false;
                timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
            }
        }));
        if (GameScr.pm.isYourTurn() && !GameScr.trainingMode && !BM.active && PM.getMyPlayer().active
                && this.nBoLuot > 0) {
            menu.addElement(new Command(Language.SKIP(), new IAction() {
                @Override
                public void perform() {
                    GameScr.time.skipTurn();
                    --nBoLuot;
                    isShowPausemenu = false;
                    timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                }
            }));
        }
        menu.addElement(new Command(Language.LEAVEBATTLE(), new IAction() {
            @Override
            public void perform() {
                if (GameScr.trainingMode) {
                    GameService.gI().training((byte) 1);
                    GameScr.trainingMode = false;
                    isShowPausemenu = false;
                    timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                    CScreen.isSetClip = true;
                    return;
                }
                if (PM.p[GameScr.myIndex].getState() == 5) {
                    CCanvas.startYesNoDlg(Language.youWillLose(), new IAction() {
                        @Override
                        public void perform() {
                            exitGiuaChung();
                            CCanvas.endDlg();
                            isShowPausemenu = false;
                            timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                        }
                    });
                    return;
                }
                CCanvas.startYesNoDlg(Language.wantExit(), new IAction() {
                    @Override
                    public void perform() {
                        exitGiuaChung();
                        CCanvas.endDlg();
                        isShowPausemenu = false;
                        timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                    }
                });
            }
        }));
        CCanvas.pausemenu.startAt(menu);
    }

    public void addTimeBomb(TimeBomb bomb) {
        GameScr.timeBombs.addElement(bomb);
        waitting();
    }

    public void explodeTimeBomb(int id) {
        for (int i = 0; i < GameScr.timeBombs.size(); ++i) {
            TimeBomb b = GameScr.timeBombs.elementAt(i);
            if (b.id == id) {
                b.isExplore = true;
                GameScr.mm.makeHole(b.x, b.y, (byte) 57, 9);
                return;
            }
        }
        waitting();
    }

    public static void waitting() {
        CTime.seconds += 2;
        CCanvas.tNotify = 0;
        CCanvas.lockNotify = true;
        if (CCanvas.curScr == CCanvas.gameScr) {
            Session_ME.receiveSynchronized = 1;
        }
    }

    public void exitGiuaChung() {
        if (GameScr.pm != null && PM.p != null && PM.p[GameScr.myIndex] != null) {
            GameService.gI().leaveBoard();
            CCanvas.startWaitDlgWithoutCancel(Language.leaveBattle(), 9);
            GameService.gI().requestRoomList();
            CScreen.isSetClip = true;
        }
    }

    public void doExit() {
        for (int i = 0; i < PM.MAX_PLAYER; ++i) {
            if (PM.p[i] != null) {
                PM.p[i] = null;
            }
        }
        CCanvas.prepareScr.show();
        Session_ME.receiveSynchronized = 0;
    }

    @Override
    public void update() {
        if (GameScr.trainingMode) {
            this.doTraining();
        }
        if (this.chatWait > 0) {
            --this.chatWait;
        }
        GameScr.tfChat.update();
        this.updateChat();
        GameScr.bm.update();
        GameScr.pm.update();
        GameScr.sm.update();
        GameScr.cam.update();
        if (this.snow != null) {
            this.snow.update();
        }
        for (int i = 0; i < GameScr.timeBombs.size(); ++i) {
            TimeBomb b = GameScr.timeBombs.elementAt(i);
            if (b != null) {
                b.update();
            }
        }
        for (int i = 0; i < GameScr.exs.size(); ++i) {
            GameScr.exs.elementAt(i).update();
        }
        GameScr.time.update();
        ++GameScr.tickCount;
        if (GameScr.tickCount > 10000) {
            GameScr.tickCount = 0;
        }
        if (this.isMoneyFly) {
            --this.moneyY;
            if (this.moneyY < 50) {
                this.isMoneyFly = false;
                this.moneyY = GameScr.h / 2 - 15;
            }
        }
        if (this.isMoney2Fly) {
            --this.moneyY2;
            if (this.moneyY2 < PM.p[this.whoGetMoney2].y + 100) {
                this.isMoney2Fly = false;
            }
        }
        if (this.vGift.size() != 0) {
            ++this.tFly;
            if (this.tFly == 10) {
                for (int i = 0; i < this.vGift.size(); ++i) {
                    GiftEffect giftEffect = this.vGift.elementAt(i);
                    if (!giftEffect.isFly) {
                        giftEffect.isFly = true;
                        break;
                    }
                }
                this.tFly = 0;
            }
        }
        for (int i = 0; i < this.vGift.size(); ++i) {
            this.vGift.elementAt(i).update();
        }
    }

    @Override
    public void mainLoop() {
        super.mainLoop();
        GameScr.mm.update();
        GameScr.cam.mainLoop();
    }

    private void doTraining() {
        switch (GameScr.trainingStep) {
            case 0: {
                if (!PM.p[0].falling) {
                    GameScr.trainingStep = -1;
                    CCanvas.startOKDlg(Language.training1(), new IAction() {
                        @Override
                        public void perform() {
                            CCanvas.startOKDlg(Language.training2(), new IAction() {
                                @Override
                                public void perform() {
                                    GameScr.trainingStep = 1;
                                }
                            });
                        }
                    });
                    break;
                }
                break;
            }
            case 1: {
                if (PM.p[0].movePoint > 20) {
                    GameScr.trainingStep = -1;
                    CCanvas.startOKDlg(Language.trainin3(), new IAction() {
                        @Override
                        public void perform() {
                            CCanvas.startOKDlg(Language.training4(), new IAction() {
                                @Override
                                public void perform() {
                                    CCanvas.startOKDlg(Language.training5(), new IAction() {
                                        @Override
                                        public void perform() {
                                            GameScr.trainingStep = 2;
                                        }
                                    });
                                }
                            });
                        }
                    });
                    break;
                }
                break;
            }
            case 2: {
                if (PM.p[1].y > 514) {
                    GameScr.trainingStep = -1;
                    CCanvas.startOKDlg(Language.training6(), new IAction() {
                        @Override
                        public void perform() {
                            GameScr.trainingStep = 3;
                        }
                    });
                    break;
                }
                break;
            }
            case 3: {
                if (PM.getMyPlayer().hp == 100 || PM.p[1].y > MM.mapHeight || PM.getMyPlayer().y > MM.mapHeight) {
                    GameScr.trainingStep = -1;
                    CCanvas.startOKDlg(Language.training7(), new IAction() {
                        @Override
                        public void perform() {
                            GameScr.trainingStep = 0;
                            GameScr.trainingMode = false;
                            GameService.gI().training((byte) 1);
                            CCanvas.menuScr.show();
                        }
                    });
                    break;
                }
                break;
            }
        }
    }

    public void activeMoney2Fly(int money, int ID_of_whoget) {
        if (PM.p[PM.getIndexByIDDB(ID_of_whoget)] == null || PM.p == null) {
            return;
        }
        if (money > 0) {
            this.showChat(ID_of_whoget, " +" + money + Language.xu());
        } else {
            this.showChat(ID_of_whoget, " " + money + Language.xu());
        }
    }

    public void setWin(byte isWin, byte _exBonus, int _moneyBonus) {
        this.chatList.removeAllElements();
        this.exBonus = _exBonus;
        this.moneyBonus = _moneyBonus;
        this.moneyY = CScreen.h / 2;
        this.isMoneyFly = true;
        GameScr.time.stop();
        if (isWin == 0) {
            GameScr.res = Language.RAW();
            GameScr.pm.setPlayerAfterDraw();
        } else if (PM.p[GameScr.myIndex] != null) {
            boolean teamWin = false;
            if (isWin == 1) {
                GameScr.res = Language.WIN();
                teamWin = PM.p[GameScr.myIndex].team;
            } else {
                GameScr.res = Language.LOSE();
                teamWin = !PM.p[GameScr.myIndex].team;
            }
            if (teamWin == PM.p[GameScr.myIndex].team) {
                GameScr.pm.setPlayerAfterSetWin(teamWin);
            }
        }
    }

    public static int[][] getPointAround(int x, int y, int number) {
        int[] xPoint = new int[number];
        int[] yPoint = new int[number];
        xPoint[6] = x - 10;
        yPoint[6] = y;
        xPoint[5] = x + 10;
        yPoint[5] = y;
        xPoint[4] = x;
        yPoint[4] = y - 35;
        xPoint[3] = x;
        yPoint[3] = y - 70;
        xPoint[2] = x - 30;
        yPoint[2] = y - 70;
        xPoint[1] = x + 30;
        yPoint[1] = y - 70;
        xPoint[0] = x;
        yPoint[0] = y - 85;
        return new int[][] { xPoint, yPoint };
    }

    public void checkEyeSmoke(byte whoCantSee, byte typeSee) {
        if (typeSee == 1) {
            PM.p[whoCantSee].cantSee = false;
            if (whoCantSee == GameScr.myIndex) {
                GameScr.cantSee = false;
            }
        } else {
            PM.p[whoCantSee].cantSee = true;
            if (whoCantSee == GameScr.myIndex) {
                GameScr.cantSee = true;
            }
        }
        waitting();
    }

    public void checkInvisible2(byte whoInvisible) {
        PM.p[whoInvisible].isInvisible = false;
        GameScr.cam.setPlayerMode(whoInvisible);
        waitting();
    }

    public void checkVampire(byte whoVampire) {
        PM.p[whoVampire].isVampire = false;
        GameScr.cam.setPlayerMode(whoVampire);
        waitting();
    }

    public void checkFreeze(byte whoCantMove, byte type) {
        if (type == 1) {
            PM.p[whoCantMove].isFreeze = false;
        } else {
            PM.p[whoCantMove].isFreeze = true;
        }
        waitting();
    }

    public void checkPostion(byte whoPoison) {
        PM.p[whoPoison].isPoison = true;
        waitting();
    }

    private void paintTouch(mGraphics g) {
        int xCenter = 0;
        int yCenter = CCanvas.hieght - GameScr.phai.image.height - 10;
        GameScr.xL = xCenter + 35;
        GameScr.yL = yCenter;
        GameScr.xR = xCenter + 140;
        GameScr.yR = yCenter;
        GameScr.xF = CCanvas.width - (GameScr.crossHair2.image.width + GameScr.crossHair2.image.width / 2);
        GameScr.yF = CCanvas.hieght - (GameScr.crossHair2.image.height + GameScr.crossHair2.image.height / 2);
        if (CCanvas.isDebugging()) {
            g.setColor(16765440);
            int xOffset = 30;
            int yOffset = 30;
            g.fillRect(GameScr.xF - xOffset / 2, GameScr.yF - yOffset / 2,
                    GameScr.crossHair2.image.getWidth() + xOffset, GameScr.crossHair2.image.getHeight() + yOffset,
                    false);
        }
        if (this.isPressXF) {
            g.drawImage(GameScr.crossHair2, GameScr.xF + 2, GameScr.yF + 2, mGraphics.TOP | mGraphics.LEFT, false);
        } else {
            g.drawImage(GameScr.crossHair2, GameScr.xF, GameScr.yF, mGraphics.TOP | mGraphics.LEFT, false);
        }
        if (CCanvas.isDebugging()) {
            g.setColor(16765440);
            int xOffset = 30;
            int yOffset = 30;
            g.fillRect(GameScr.xL - GameScr.trai.image.width / 2 - xOffset / 2,
                    GameScr.yL - GameScr.trai.image.height / 2 - yOffset / 2, GameScr.trai.image.getWidth() + xOffset,
                    GameScr.trai.image.getHeight() + yOffset, false);
        }
        if (this.isPressXL) {
            g.drawImage(GameScr.trai, GameScr.xL + 2, GameScr.yL + 2, mGraphics.VCENTER | mGraphics.HCENTER, false);
        } else {
            g.drawImage(GameScr.trai, GameScr.xL, GameScr.yL, mGraphics.VCENTER | mGraphics.HCENTER, false);
        }
        if (CCanvas.isDebugging()) {
            g.setColor(16765440);
            int xOffset = 30;
            int yOffset = 30;
            g.fillRect(GameScr.xR - GameScr.phai.image.width / 2 - xOffset / 2,
                    GameScr.yR - GameScr.trai.image.height / 2 - yOffset / 2, GameScr.phai.image.getWidth() + xOffset,
                    GameScr.phai.image.getHeight() + yOffset, false);
        }
        if (this.isPressXR) {
            g.drawImage(GameScr.phai, GameScr.xR + 2, GameScr.yR + 2, mGraphics.VCENTER | mGraphics.HCENTER, false);
        } else {
            g.drawImage(GameScr.phai, GameScr.xR, GameScr.yR, mGraphics.VCENTER | mGraphics.HCENTER, false);
        }
    }

    @Override
    public void paint(mGraphics g) {
        Camera.translate(g);
        if (GameScr.curGRAPHIC_LEVEL != 2) {
            GameScr.mm.paintBackGround(g);
        } else {
            g.setColor(6483442);
            g.fillRect(Camera.x, Camera.y, CCanvas.width, CCanvas.hieght, false);
        }
        if (this.snow != null) {
            this.snow.paintSmallSnow(g);
        }
        GameScr.mm.paint(g);
        for (int i = 0; i < this.vGift.size(); ++i) {
            this.vGift.elementAt(i).paint(g);
        }
        if (GameScr.isDarkEffect) {
            Effect.FillTransparentRect(g, Camera.x, Camera.y, GameScr.w, GameScr.h);
        }
        if (GameScr.cantSee) {
            g.setColor(16777215);
            g.fillRect(Camera.x, Camera.y, GameScr.w, GameScr.h, false);
        }
        for (int i = 0; i < GameScr.timeBombs.size(); ++i) {
            TimeBomb bomb = GameScr.timeBombs.elementAt(i);
            if (bomb != null) {
                bomb.paint(g);
            }
        }
        GameScr.pm.paint(g);
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null && PM.p[i].isFreeze) {
                g.drawImage(Explosion.dongbang, PM.p[i].x, PM.p[i].y - 12, 3, false);
            }
        }
        GameScr.sm.paint(g);
        GameScr.bm.paint(g);
        if (MM.isHaveWaterOrGlass) {
            GameScr.mm.paintWater(g);
        }
        for (int i = 0; i < GameScr.exs.size(); ++i) {
            GameScr.exs.elementAt(i).paint(g);
        }
        if (this.snow != null) {
            this.snow.paintBigSnow(g);
        }
        if (GameScr.whiteEffect) {
            g.setColor(16777215);
            g.fillArc(Camera.x + CCanvas.width / 2 - this.xE, Camera.y + CCanvas.width / 2 - this.xE, this.wE, this.wE,
                    0, 360, false);
            this.xE += 30;
            this.wE += 60;
            if (this.xE > CCanvas.width + 100) {
                this.xE = 0;
                this.wE = 0;
                int[][] array = getPointAround(GameScr.xNuke, GameScr.yNuke, 7);
                for (int j = 0; j < 7; ++j) {
                    new Explosion(array[0][j], array[1][j], (byte) 7);
                }
                GameScr.whiteEffect = false;
            }
        }
        if (GameScr.electricEffect) {
            ++this.tE;
            if (this.tE % 2 == 0) {
                new Explosion(GameScr.xElectric + CRes.random(-20, 20), GameScr.yElectric + CRes.random(-20, 20),
                        (byte) 8);
            }
            if (this.tE == 10) {
                this.tE = 0;
                GameScr.electricEffect = false;
            }
        }
        if (GameScr.freezeEffect) {
            ++this.tE;
            if (this.tE % 2 == 0) {
                new Explosion(GameScr.xFreeze + CRes.random(-50, 50), GameScr.yFreeze + CRes.random(-50, 50),
                        (byte) 14);
            }
            if (this.tE == 30) {
                this.tE = 0;
                GameScr.freezeEffect = false;
            }
        }
        if (GameScr.suicideEffect) {
            ++this.tE;
            if (this.tE % 2 == 0) {
                new Explosion(GameScr.xSuicide + CRes.random(-50, 50), GameScr.ySuicide + CRes.random(-50, 50),
                        (byte) 0);
            }
            if (this.tE == 60) {
                this.tE = 0;
                GameScr.suicideEffect = false;
            }
        }
        if (GameScr.poisonEffect) {
            ++this.tE;
            if (this.tE % 2 == 0) {
                new Explosion(GameScr.xPoison + CRes.random(-50, 50), GameScr.yPoison + CRes.random(-50, 50),
                        (byte) 15);
            }
            if (this.tE == 60) {
                this.tE = 0;
                GameScr.poisonEffect = false;
            }
        }
        if (CCanvas.isDebugging()) {
            for (int i = 0; i < MM.mapWidth / 100; ++i) {
                g.setColor(16711680);
                g.drawLine(100 * (i + 1), 0, 100 * (i + 1), MM.mapHeight, false);
                Font.normalFont.drawString(g, new StringBuilder(String.valueOf(i)).toString(), 50 + i * 100,
                        CCanvas.h / 2, 0);
            }
        }
        if (Camera.shaking == 2 && GameScr.tickCount / 2 % 2 == 0) {
            g.setColor(16711680);
            g.fillRect(Camera.x, Camera.y, GameScr.w, 10, false);
            g.fillRect(Camera.x, Camera.y + GameScr.h - 10, GameScr.w, 10, false);
            g.fillRect(Camera.x, Camera.y, 10, GameScr.h, false);
            g.fillRect(Camera.x + GameScr.w - 10, Camera.y, 10, GameScr.h, false);
        }
        if (!GameScr.trainingMode) {
            GameScr.time.paint(g);
        }
        if (GameScr.pm.isYourTurn() && PM.getCurPlayer() != null) {
            int forceT1 = (PM.getMyPlayer().getState() == 3) ? PM.getMyPlayer().force : 0;
            int forceT2 = (PM.getMyPlayer().getState() == 3) ? PM.getMyPlayer().force_2 : 0;
            int movePoint = PM.getMyPlayer().movePoint;
            int lastForcePoint1 = PM.getMyPlayer().lastForcePoint;
            int lastForcePoint2 = PM.getMyPlayer().lastForcePoint_2;
            if (!this.isSelectItem && MainGame.getNumberFingerOnScreen() < 2 && Camera.mode != 0
                    && !CPlayer.isShooting) {
                onDrawPowerBar(g, Camera.x + (GameScr.w >> 1), Camera.y + GameScr.h - 25 + 5, forceT1, lastForcePoint1,
                        movePoint);
                if (PM.getMyPlayer().isDoublePower) {
                    onDrawSecondPowerBar(g, Camera.x + (GameScr.w >> 1), Camera.y + GameScr.h - 25 - 15 + 5, forceT2,
                            lastForcePoint2, movePoint);
                }
                onDrawAngleBar(g, Camera.x + (GameScr.w >> 1), Camera.y + GameScr.h - 25 + 8, PM.getMyPlayer().angle);
            }
        }
        if (!GameScr.pm.isYourTurn()) {
            g.translate(-g.getTranslateX(), -g.getTranslateY());
        } else if (CCanvas.isTouch) {
            g.translate(-g.getTranslateX(), -g.getTranslateY());
            if (!this.isSelectItem && MainGame.getNumberFingerOnScreen() < 2 && Camera.mode != 0
                    && !CPlayer.isShooting) {
                this.paintTouch(g);
            }
        }
        int tam = 0;
        if (this.isSelectItem) {
            if (CCanvas.isTouch) {
                tam = (CCanvas.isTouch ? 7 : 0);
                g.translate(-g.getTranslateX(), -g.getTranslateY());
                CScreen.paintBorderRect(g, CCanvas.hh - 65, 4, 130, Language.chonItem());
                onDrawItem(g, CCanvas.hw - 67, CCanvas.hh - 20);
                g.drawImage(CRes.imgMenu, 25, 5, 0, false);
            } else {
                onDrawItem(g, Camera.x + (CCanvas.hw - 27), Camera.y + CCanvas.hh);
            }
        }
        this.drawSCORE(g);
        if (PM.getCurPlayer() != null) {
            String name = PM.getCurPlayer().name;
            int dis = CCanvas.isTouch ? 25 : 0;
            if (name != null && !(PM.getCurPlayer() instanceof Boss)) {
                (PM.getCurPlayer().team ? Font.smallFontRed : Font.smallFontYellow).drawString(g, name.toUpperCase(),
                        CScreen.w - 16, 22 + dis, 2);
            }
        }
        this.drawWind(g);
        if (Camera.mode == 0) {
            this.drawWhenFreeCam(g);
        }
        if (CCanvas.currentDialog == null && !this.isSelectItem) {
            this.drawMenuCameraIcon(g);
        }
        if (!CRes.isNullOrEmpty(GameScr.tfChat.getText()) && this.isChat) {
            this.isChat = false;
            if (this.chatWait == 0) {
                String text = GameScr.tfChat.getText();
                GameService.gI().chatToBoard(text);
                GameScr.tfChat.setText("");
                this.showChat(TerrainMidlet.myInfo.IDDB, text);
                CCanvas.gameScr.showChat(TerrainMidlet.myInfo.IDDB, text, 90);
                this.chatWait = this.chatDelay;
            } else {
                GameScr.tfChat.setText("");
            }
            clearKey();
        }
        this.drawChat(g);
        if (CCanvas.isDebugging()) {
            int xPaint = CCanvas.width - 2 - Font.normalRFont.getWidth(GameMidlet.version);
            int yPaint = CCanvas.hieght - Font.normalRFont.getHeight() * 2;
            Font.normalRFont.drawString(g, new StringBuilder(String.valueOf(GameMidlet.timePingPaint)).toString(),
                    xPaint, yPaint, 2, false);
            Font.normalRFont.drawString(g, "CAM: " + Camera.getMode(), xPaint, yPaint - 15, 2, false);
            if (GameScr.pm.isYourTurn()) {
                Font.normalRFont.drawString(g, "SHOOT: " + CPlayer.isShooting, xPaint, yPaint - 30, 2, false);
            }
            if (CCanvas.isPointerDown[0]) {
                Font.normalFont.drawString(g, String.valueOf(CCanvas.pX[0]) + "/" + CCanvas.pY[0], CCanvas.pX[0],
                        CCanvas.pY[0] - 15, 2, false);
            }
        }
        if (CCanvas.currentDialog != null) {
            super.paintCommand(g);
        }
    }

    private void drawWhenFreeCam(mGraphics g) {
        Font.borderFont.drawString(g, Language.cameraMode(), CCanvas.hw, CCanvas.hh - 15, 2);
        int dx = 0;
        if (CCanvas.gameTick % 10 > 4) {
            dx = 2;
        }
        g.drawImage(GameScr.imgArrow, 0 + dx, CCanvas.hh, mGraphics.LEFT | mGraphics.VCENTER, false);
        g.drawRegion(GameScr.imgArrow, 0, 0, GameScr.imgArrow.image.getWidth(), GameScr.imgArrow.image.getHeight(), 2,
                CCanvas.width - dx, CCanvas.hh, mGraphics.VCENTER | mGraphics.RIGHT, false);
        g.drawRegion(GameScr.imgArrow, 0, 0, GameScr.imgArrow.image.getWidth(), GameScr.imgArrow.image.getHeight(), 5,
                CCanvas.hw, 25 + dx, mGraphics.TOP | mGraphics.HCENTER, false);
        g.drawRegion(GameScr.imgArrow, 0, 0, GameScr.imgArrow.image.getWidth(), GameScr.imgArrow.image.getHeight(), 6,
                CCanvas.hw, CCanvas.hieght - 30 - dx, mGraphics.BOTTOM | mGraphics.HCENTER, false);
    }

    private void drawMenuCameraIcon(mGraphics g) {
        if (!CCanvas.isTouch) {
            if (Camera.mode == 0) {
                if (CCanvas.gameTick % 10 > 5) {
                    g.drawImage(CRes.imgCam, Camera.x + GameScr.w - 20, Camera.y + GameScr.h - 18, 0, false);
                }
            } else {
                g.drawImage(CRes.imgCam, Camera.x + GameScr.w - 20, Camera.y + GameScr.h - 18, 0, false);
            }
        } else {
            int hDraw = CRes.imgMenu.image.getHeight();
            if (Camera.mode == 0) {
                if (CCanvas.gameTick % 10 > 5) {
                    g.drawImage(CRes.imgCam, GameScr.w - CRes.imgCam.image.getWidth() - 5, hDraw + 5, 0, false);
                }
            } else {
                g.drawImage(CRes.imgMenu, 20, hDraw + 5, 0, false);
                g.drawImage(PrepareScr.iconChat, 70, hDraw + 5, 0, false);
                g.drawImage(CRes.imgCam, GameScr.w - CRes.imgCam.image.getWidth() - 5, hDraw + 5, 0, false);
            }
        }
    }

    public static void changeWind(int NextWindX, int NextWindY) {
        GameScr.windx = NextWindX;
        GameScr.windy = NextWindY;
        GameScr.windAngle = CRes.fixangle(CRes.angle(GameScr.windx, -GameScr.windy));
        GameScr.windPower = CRes.sqrt(GameScr.windx * GameScr.windx + GameScr.windy * GameScr.windy);
    }

    public void drawWind(mGraphics g) {
        if (Camera.mode == 0) {
            return;
        }
        g.drawImage(GameScr.wind1, CCanvas.width / 2, 22, 3, true);
        if (!this.b) {
            ++this.dem;
        } else {
            --this.dem;
        }
        if (this.dem > 5) {
            this.b = true;
        }
        if (this.dem < 0) {
            this.b = false;
        }
        g.drawImage(GameScr.wind2, CCanvas.w / 2, 22, 3, true);
        if (GameScr.windPower != 0) {
            int DX = 13 * CRes.cos(CRes.fixangle(GameScr.windAngle)) >> 10;
            int DY = 13 * CRes.sin(CRes.fixangle(GameScr.windAngle)) >> 10;
            g.drawImage(GameScr.wind3, CCanvas.w / 2 + 2 + DX, 22 - DY, mGraphics.VCENTER | mGraphics.HCENTER, true);
        }
        Font.borderFont.drawString(g, new StringBuilder(String.valueOf(GameScr.windPower)).toString(), CCanvas.w / 2,
                15, 3);
        Font.borderFont.drawString(g, String.valueOf(Language.windAngle()) + ": " + GameScr.windAngle, CCanvas.w / 2,
                45, 2);
    }

    public void drawSCORE(mGraphics g) {
        if (!GameScr.res.equals("")) {
            Font.bigFont.drawString(g, GameScr.res, GameScr.w / 2, 80, mGraphics.HCENTER | mGraphics.VCENTER);
            Font.borderFont.drawString(g, String.valueOf(Language.money()) + ": " + this.moneyBonus + Language.xu(),
                    GameScr.w / 2, this.moneyY, mGraphics.HCENTER | mGraphics.VCENTER);
        }
        if (this.isMoney2Fly) {
            Font.borderFont.drawString(g, "+" + this.moneyBonus2 + Language.xu(), PM.p[this.whoGetMoney2].x,
                    this.moneyY2, mGraphics.HCENTER | mGraphics.VCENTER);
        }
    }

    @Override
    public void show(CScreen lastScreen) {
        GameScr.lastSCreen = lastScreen;
        CScreen.isSetClip = false;
        super.show();
    }

    @Override
    public void show() {
        super.show();
        CScreen.isSetClip = false;
    }

    @Override
    protected void onClose() {
        super.onClose();
        CScreen.isSetClip = true;
    }

    public static void onDrawPowerBar(mGraphics g, int x, int y, int power, int mark, int movePoint) {
        if (GameScr.s_frBar == null) {
            return;
        }
        GameScr.s_frBar.drawFrame(1, x - 54 - 10, y + 5, 3, 0, g, false);
        GameScr.s_frBar.drawFrame(3, x + 2 + 10, y + 5, 3, 0, g, false);
        GameScr.s_frBar.fillFrame(0, x - 54 - 10, y + 5, (60 - movePoint) * 100 / 60, 3, 0, g, true);
        GameScr.s_frBar.fillFrame(2, x + 2 + 10, y + 5, power * 100 / 30, 3, 0, g, true);
        GameScr.s_frBar.drawFrame(5, x - 53 - 10, y - 7, 3, 0, g, true);
        GameScr.s_frBar.fillFrame(4, x - 54 - 10, y - 7, PM.getMyPlayer().angryX * 100 / 100, 3, 0, g, true);
        if (mark > 0) {
            g.setColor(16482175);
            g.drawLine(x + 2 + mark * 49 / 30 + 10, y + 7, x + 2 + mark * 49 / 30 + 10, y + 7 + 7, false);
        }
    }

    public static void onDrawSecondPowerBar(mGraphics g, int x, int y, int power, int mark, int movePoint) {
        if (Camera.mode == 0) {
            return;
        }
        GameScr.s_frBar.drawFrame(3, x + 2 + 10, y + 8, 0, 0, g);
        GameScr.s_frBar.fillFrame(2, x + 2 + 10, y + 8, power * 100 / 30, 3, 0, g, true);
        if (mark > 0) {
            g.setColor(16482175);
            g.drawLine(x + 2 + mark * 49 / 30 + 10, y + 10, x + 2 + mark * 49 / 30 + 10, y + 8 + 9, false);
        }
    }

    public static void onDrawAngleBar(mGraphics g, int x, int y, int angle) {
        if (Camera.mode == 0) {
            return;
        }
        g.drawImage(GameScr.s_imgAngle, x, y + 2, mGraphics.TOP | mGraphics.HCENTER, false);
        Font.smallFontYellow.drawString(g, new StringBuilder().append((angle >= 90) ? (180 - angle) : angle).toString(),
                x, y + 4, 2);
    }

    public static void onDrawItem(mGraphics g, int x, int y) {
        if (!CCanvas.isTouch) {
            g.setColor(16767817);
            g.fillRect(Camera.x, y - 1, CCanvas.width, CCanvas.isTouch ? 43 : 36, false);
        }
        Item.DrawSetItem(g, PM.getMyPlayer().item, GameScr.curItemSelec, x, y, CCanvas.isTouch,
                (byte[]) ((PrepareScr.currLevel == 7) ? GameScr.num : null));
        Font.borderFont.drawString(g, Language.use(), Camera.x + 5,
                Camera.y + CCanvas.hieght - Font.normalFont.getHeight() - 4, 0);
        Font.borderFont.drawString(g, Language.close(), Camera.x + CCanvas.width - 5,
                Camera.y + CCanvas.hieght - Font.normalFont.getHeight() - 4, 1);
    }

    public static void onDrawArrow(mGraphics g, int x, int y, int color, boolean isDynamic) {
        if (isDynamic) {
            y += 2 * (GameScr.tickCount / 2 % 2);
        }
        g.setColor(color);
        g.fillRect(x, y, 11, 2, false);
        g.fillTriangle(x, y + 3, x + 11, y + 3, x + 5, y + 9, false);
    }

    public void showChat(int fromID, String text) {
        if (PrepareScr.currLevel != 7) {
            this.chatList.addElement(CCanvas.prepareScr.getPlayerNameFromID(fromID) + ": " + text);
        } else {
            this.chatList.addElement(GameScr.pm.getPlayerNameFromID(fromID) + ": " + text);
        }
        if (this.chatDelay == 0) {
            this.chatDelay = this.MAX_CHAT_DELAY;
        }
    }

    public void updateChat() {
        if (this.chatDelay > 0) {
            --this.chatDelay;
            if (this.chatDelay == 0) {
                if (this.chatList.size() > 0) {
                    this.chatList.removeElementAt(0);
                }
                if (this.chatList.size() > 0) {
                    this.chatDelay = this.MAX_CHAT_DELAY;
                }
            }
        }
    }

    public void drawChat(mGraphics g) {
        if (this.chatList.size() == 0) {
            return;
        }
        String chat = (String) this.chatList.elementAt(0);
        int nDevision = this.MAX_CHAT_DELAY - this.chatDelay;
        if (nDevision > 10) {
            nDevision = 10;
        }
        int xChat = CCanvas.width;
        for (int i = 0; i < nDevision; ++i) {
            xChat >>= 1;
        }
        Font.borderFont.drawString(g, chat, 3 + xChat, CCanvas.hieght - 14, 0);
    }

    public void showChat(int fromID, String text, int Interval) {
        ChatPopup cp = new ChatPopup();
        CPlayer _player = GameScr.pm.getPlayerFromID(fromID);
        if (_player != null) {
            cp.show(Interval, _player.x - Camera.x, _player.y - Camera.y - 30, text);
            CCanvas.arrPopups.addElement(cp);
        }
    }

    public void onClearMap() {
        GameScr.mm.onClearMap();
        GameScr.sm.onClearMap();
        System.gc();
    }

    @Override
    public void onPointerPressed(int xScreen, int yScreen, int index) {
        if (Camera.mode == 1 && mSystem.currentTimeMillis() - this.timeDelayClosePauseMenu > 550L) {
            GameScr.pm.onPointerPressed(xScreen, yScreen, index);
        }
        super.onPointerPressed(xScreen, yScreen, index);
    }

    @Override
    public void onPointerHold(int xScreen, int yScreen, int index) {
        if (this.isSelectItem) {
            return;
        }
        if (this.isShowPausemenu) {
            return;
        }
        if (mSystem.currentTimeMillis() - this.timeDelayClosePauseMenu < 300L) {
            return;
        }
        if (mSystem.currentTimeMillis() - this.timeShowPauseMenu < 300L) {
            return;
        }
        if (Camera.mode == 1) {
            GameScr.pm.onPointerHold(xScreen, yScreen, index);
            int xOffset = 30;
            int yOffset = 30;
            if (CCanvas.isPointer(GameScr.xL - GameScr.trai.image.width / 2 - xOffset / 2,
                    GameScr.yL - GameScr.trai.image.height / 2 - yOffset / 2, GameScr.trai.image.getWidth() + xOffset,
                    GameScr.trai.image.getHeight() + yOffset, index) && GameScr.pm.isYourTurn()) {
                this.isPressXL = true;
                return;
            }
            if (CCanvas.isPointer(GameScr.xR - GameScr.phai.image.width / 2 - xOffset / 2,
                    GameScr.yR - GameScr.trai.image.height / 2 - yOffset / 2, GameScr.phai.image.getWidth() + xOffset,
                    GameScr.phai.image.getHeight() + yOffset, index) && GameScr.pm.isYourTurn()) {
                this.isPressXR = true;
                return;
            }
            xOffset = 30;
            yOffset = 30;
            if (CCanvas.isPointer(GameScr.xF - xOffset / 2, GameScr.yF - yOffset / 2,
                    GameScr.crossHair2.image.getWidth() + xOffset, GameScr.crossHair2.image.getHeight() + yOffset,
                    index) && GameScr.pm.isYourTurn()) {
                this.isPressXF = true;
            }
        }
    }

    @Override
    public void onPointerDragged(int xScreen, int yScreen, int index) {
        if (this.isSelectItem) {
            return;
        }
        if (this.isShowPausemenu) {
            return;
        }
        if (mSystem.currentTimeMillis() - this.timeDelayClosePauseMenu < 300L) {
            return;
        }
        if (mSystem.currentTimeMillis() - this.timeShowPauseMenu < 300L) {
            return;
        }
        try {
            if (GameScr.pm.isYourTurn() || !(PM.p[PM.curP] instanceof Boss)) {
                if (MainGame.getNumberFingerOnScreen() >= 2) {
                    if (Camera.mode == 1 && index == 1) {
                        GameScr.pm.onPointerDragRighCorner(xScreen, yScreen, index);
                    }
                } else {
                    if (Camera.mode == 1) {
                        GameScr.pm.onPointerDrag(xScreen, yScreen, index);
                    }
                    if (Camera.mode == 0) {
                        this.onDragCamera(xScreen, yScreen, index);
                    }
                }
            }
        } catch (Exception ex) {
        }
        super.onPointerDragged(xScreen, yScreen, index);
    }

    @Override
    public void onPointerReleased(int x, int y2, int index) {
        boolean isPressXL = false;
        this.isPressXF = isPressXL;
        this.isPressXR = isPressXL;
        this.isPressXL = isPressXL;
        if (Camera.mode == 1 && !this.isSelectItem) {
            GameScr.pm.onPointerReleased(x, y2, index);
        }
        if (this.isSelectItem) {
            if (!CCanvas.isPointer(x - 50, y2 - 50, 210, 130, index)) {
                this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 550L;
            } else {
                this.isSelectItem = true;
                this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 550L;
                this.selectedItemPanelRealeased(x, y2, index);
            }
        } else {
            if (CCanvas.pausemenu.isShow) {
                CCanvas.pausemenu.onPointerRealeased(x, y2, index);
            } else if (CCanvas.isPointer(0, 0, 50, 50, index)) {
                this.doShowPauseMenu();
            }
            this.isShowPausemenu = CCanvas.pausemenu.isShow;
            if (CCanvas.pausemenu.isShow) {
                return;
            }
            if (CCanvas.isPointer(60, 0, 60, 60, index)) {
                this.isChat = true;
                GameScr.tfChat.doChangeToTextBox();
            }
            if (PM.getCurPlayer() != null && CCanvas.isPointer(CCanvas.width - 50, 0, 50, 50, index)) {
                if (Camera.mode != 0) {
                    if (!(PM.getCurPlayer() instanceof Boss)
                            && CCanvas.isPointer(CCanvas.width - 50, 0, 50, 50, index)) {
                        Camera.mode = 0;
                        clearKey();
                    }
                } else if (Camera.mode == 0) {
                    if (BM.active && GameScr.bm.bullets.size() > 0) {
                        GameScr.cam.setBulletMode(GameScr.bm.bullets.elementAt(0));
                    } else {
                        GameScr.cam.setPlayerMode(PM.curP);
                    }
                }
            }
        }
    }

    public void onPaintSliderRightConer(mGraphics gr, int xPaint, int yPaint) {
    }

    public void notClearMap(int indexClear) {
        System.gc();
    }

    static {
        GameScr.WIDTH = 1000;
        GameScr.HEIGHT = 1000;
        GameScr.curGRAPHIC_LEVEL = 0;
        GameScr.imgReady = new mImage[9];
        GameScr.imgMsg = new mImage[2];
        GameScr.timeBombs = new Vector<TimeBomb>();
        FilePack filePak = null;
        try {
            filePak = new FilePack(CCanvas.getClassPathConfig(String.valueOf(CONFIG.PATH_GUI) + "gui"));
            GameScr.airFighter = filePak.loadImage("fighter.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "fighter");
                }
            });
            GameScr.imgMode = filePak.loadImage("mode.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "mode");
                }
            });
            GameScr.lock = filePak.loadImage("lock2.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "lock2");
                }
            });
            GameScr.lockImg = filePak.loadImage("lock.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "lock");
                }
            });
            GameScr.crosshair = filePak.loadImage("hongTam.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "hongTam");
                }
            });
            GameScr.imgInfoPopup = filePak.loadImage("popupRound.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "popupRound");
                }
            });
            GameScr.s_imgITEM = filePak.loadImage("item.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "item");
                }
            });
            GameScr.imgPlane = filePak.loadImage("fighter.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "fighter");
                }
            });
            GameScr.logoGame = mImage.createImage("/gui/logoGame.png");
            GameScr.imgReady[0] = filePak.loadImage("on.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "on");
                }
            });
            GameScr.imgReady[1] = filePak.loadImage("off.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "off");
                }
            });
            GameScr.imgReady[2] = filePak.loadImage("r2.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "r2");
                }
            });
            GameScr.imgReady[3] = filePak.loadImage("arrowup.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "arrowup");
                }
            });
            GameScr.imgReady[4] = filePak.loadImage("tile1.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "tile1");
                }
            });
            GameScr.imgQuanHam = filePak.loadImage("quanham.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "quanham");
                }
            });
            GameScr.imgBack = filePak.loadImage("menubg.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "menubg");
                }
            });
            GameScr.imgCurPos = filePak.loadImage("curMapPos.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "curMapPos");
                }
            });
            GameScr.imgSmallCloud = PrepareScr.cloud1;
            GameScr.imgArrowRed = filePak.loadImage("arrowRed.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "arrowRed");
                }
            });
            GameScr.imgRoomStat = filePak.loadImage("stat.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "stat");
                }
            });
            GameScr.imgTrs = filePak.loadImage("trs.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "trs");
                }
            });
            GameScr.imgIcon = filePak.loadImage("icon.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "icon");
                }
            });
            GameScr.s_imgAngle = filePak.loadImage("angle.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "angle");
                }
            });
            GameScr.imgChat = filePak.loadImage("chat.png", new IAction2() {
                @Override
                public void perform(Object object) {
                }
            });
            GameScr.s_imgTransparent = filePak.loadImage("transparent.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "transparent");
                }
            });
            GameScr.imgMsg[0] = filePak.loadImage("msg0.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "msg0");
                }
            });
            GameScr.imgMsg[1] = filePak.loadImage("msg1.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "msg1");
                }
            });
            GameScr.logoII = filePak.loadImage("logo_2.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "logo_2");
                }
            });
            GameScr.arrowMenu = filePak.loadImage("arrowMenu.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "arrowMenu");
                }
            });
            mImage.createImage("/gui/nut2.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    GameScr.trai = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut2");
                }
            });
            mImage.createImage("/gui/nut1.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    GameScr.phai = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut1");
                }
            });
            mImage.createImage("/gui/nut3.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    GameScr.crossHair2 = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut3");
                }
            });
            mImage.createImage("/gui/nut_up.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    GameScr.nut_up = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut_up");
                }
            });
            mImage.createImage("/gui/nut_down.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    GameScr.nut_down = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut_down");
                }
            });
            mImage.createImage("/wind.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    GameScr.wind1 = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "wind");
                }
            });
            mImage.createImage("/wind2.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    GameScr.wind2 = new mImage((Image) object);
                }
            });
            mImage.createImage("/gui/wind3.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    GameScr.wind3 = new mImage((Image) object);
                }
            });
            mImage.createImage("/gui/barMove.png", new IAction2() {
                @Override
                public void perform(Object object) {
                    GameScr.s_frBar = new FrameImage((Image) object, 53, 12, false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        filePak = null;
        GameScr.tickCount = 0;
        GameScr.ID_Turn = 0;
        GameScr.isDarkEffect = false;
        GameScr.s_iPlane_x = -1;
        GameScr.s_iPlane_y = -1;
        GameScr.s_iBombTargetX = -1;
        GameScr.res = "";
        try {
            GameScr.imgArrow = mImage.createImage("/arrow.png");
        } catch (Exception ex) {
        }
        GameScr.num = new byte[8];
    }
}
