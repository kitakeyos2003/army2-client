package coreLG;

import CLib.mSystem;
import CLib.RMS;
import java.io.IOException;
import model.PlayerInfo;
import CLib.mImage;
import model.IAction2;
import Equipment.Equip;
import Equipment.TypeEquip;
import Equipment.EquipGlass;
import Equipment.PlayerEquip;
import map.MapFile;
import network.Message;
import player.CPlayer;
import model.CRes;
import CLib.mGraphics;
import network.GameLogicHandler;
import model.IAction;
import model.Language;
import com.teamobi.mobiarmy2.GameMidlet;
import network.Session_ME;
import network.GameService;
import map.CMap;
import map.MM;
import android.content.Context;
import CLib.mSound;
import model.Position;
import java.util.Random;
import CLib.Image;
import model.IconManager;
import screen.QuangCao;
import screen.ArchivementScr;
import screen.FomulaScreen;
import screen.LuckyGifrScreen;
import screen.ClanScreen;
import screen.Inventory;
import screen.EquipScreen;
import screen.LevelScreen;
import screen.ConfigScr;
import screen.MoneyScrIOS;
import screen.MoneyScr;
import model.InfoPopup;
import model.MsgPopup;
import model.Popup;
import java.util.Vector;
import model.Dialog;
import model.MsgDlg;
import network.Command;
import model.PauseMenu;
import model.Menu;
import model.InputDlg;
import shop.ShopBietDoi;
import screen.MissionScreen;
import screen.ServerListScreen;
import screen.LuckyGame;
import screen.ChangePlayerCSr;
import shop.ShopLinhTinh;
import shop.ShopEquipment;
import shop.ShopItem;
import screen.MsgScreen;
import screen.PrepareScr;
import screen.ListScr;
import screen.BoardListScr;
import screen.RoomListScr2;
import screen.RoomListScr;
import screen.LoginScr;
import screen.GameScr;
import screen.MenuScr;
import screen.SplashScr;
import screen.CScreen;
import com.teamobi.mobiarmy2.IActionListener;
import com.teamobi.mobiarmy2.MotherCanvas;

public class CCanvas extends MotherCanvas implements IActionListener {

    private static boolean currentScreen;
    boolean isRunning;
    public static int gameTick;
    public static int width;
    public static int hieght;
    public static int hh;
    public static int hw;
    public static CScreen curScr;
    public static SplashScr splashScr;
    public static MenuScr menuScr;
    public static GameScr gameScr;
    public static LoginScr loginScr;
    public static RoomListScr roomListScr;
    public static RoomListScr2 roomListScr2;
    public static BoardListScr boardListScr;
    public static ListScr listScr;
    public static PrepareScr prepareScr;
    public static MsgScreen msgScr;
    public static ShopItem shopItemScr;
    public static ShopEquipment shopEquipScr;
    public static ShopLinhTinh shopLinhtinh;
    public static ChangePlayerCSr changePScr;
    public static LuckyGame luckyGame;
    public static ServerListScreen serverListScreen;
    public static MissionScreen missionScreen;
    public static ShopBietDoi shopBietDoi;
    public static boolean isVirHorizontal;
    public static InputDlg inputDlg;
    public static Menu menu;
    public static PauseMenu pausemenu;
    public static boolean isMoto;
    public static boolean isWifi;
    public static boolean isBB;
    public static Command cmdMenu;
    public static MsgDlg msgdlg;
    public static Dialog currentDialog;
    public static Vector<Popup> arrPopups;
    public static MsgPopup msgPopup;
    public static InfoPopup infoPopup;
    public static int waitSendMessage;
    public static MoneyScr moneyScr;
    public static MoneyScrIOS moneyScrIOS;
    public static ConfigScr configScr;
    public static LevelScreen levelScreen;
    public static EquipScreen equipScreen;
    public static Inventory inventory;
    public static ClanScreen clanScreen;
    public static ClanScreen topClanScreen;
    public static LuckyGifrScreen luckyGifrScreen;
    public static FomulaScreen fomulaScreen;
    public static ArchivementScr archScreen;
    public static QuangCao quangCaoScr;
    public static byte[] fileData5;
    public static int[] pX;
    public static int[] pY;
    public static int[] pxLast;
    public static int[] pyLast;
    public static int[] pxFirst;
    public static int[] pyFirst;
    public static boolean[] keyPressed;
    public static boolean[] keyReleased;
    public static boolean[] keyHold;
    public static boolean[] isPointerDown;
    public static boolean[] isPointerRelease;
    public static boolean[] isPointerSelect;
    public static boolean[] isPointerMove;
    public static boolean[] isPointerClick;
    public static int pointer;
    public static int button;
    public static boolean isTouch;
    public static IconManager iconMn;
    public static int nBigImage;
    public static boolean isPurchaseIOS;
    int t;
    public static boolean isDoubleClick;
    private static int MAX_TIME_CLICK;
    private static long timeClick;
    public static boolean isInGameRunTime;
    public static boolean lockNotify;
    public static int countNotify;
    public static int tNotify;
    public static boolean isReconnect;
    public static byte tileMapVersion;
    public static byte mapIconVersion;
    public static byte mapValuesVersion;
    public static byte playerVersion;
    public static byte equipVersion;
    public static byte levelCVersion;
    boolean isTestMap;
    private static int indexBullet;
    public static Image imgTest;
    public static long timeHideStartWaitingDlg;
    public static Random r;
    public static long timeNow;
    public static boolean isSmallScreen;
    private Vector<Position> listPoint;
    private int curPos;
    long timeHold;

    public CCanvas() {
        this.isRunning = true;
        this.isTestMap = false;
        this.listPoint = new Vector<Position>();
        this.timeHold = 0L;
        MotherCanvas.setFullScreenMode(true);
        this.screenInit(CCanvas.isTouch = true);
        CScreen.cmdH = 35;
        CCanvas.isInGameRunTime = false;
        mSound.init();
    }

    public CCanvas(Context c) {
        this.isRunning = true;
        this.isTestMap = false;
        this.listPoint = new Vector<Position>();
        this.timeHold = 0L;
    }

    public void screenInit(boolean setV) {
        MotherCanvas.setFullScreenMode(true);
        CCanvas.width = CCanvas.w;
        CCanvas.hieght = CCanvas.h;
        CCanvas.hh = MotherCanvas.hh;
        CCanvas.hw = MotherCanvas.hw;
        CCanvas.splashScr = new SplashScr();
        CCanvas.isTouch = true;
        CCanvas.splashScr.show();
        CScreen.w = CCanvas.width;
        CScreen.h = CCanvas.hieght;
        loadScreen();
    }

    public static void loadScreen() {
        CCanvas.luckyGame = new LuckyGame();
        CCanvas.shopEquipScr = new ShopEquipment();
        CCanvas.shopItemScr = new ShopItem();
        CCanvas.shopLinhtinh = new ShopLinhTinh();
        CCanvas.listScr = new ListScr();
        CCanvas.msgScr = new MsgScreen();
        CCanvas.infoPopup = new InfoPopup();
        CCanvas.prepareScr = new PrepareScr();
        CCanvas.inputDlg = new InputDlg();
        CCanvas.msgPopup = new MsgPopup();
        CCanvas.clanScreen = new ClanScreen(1);
        CCanvas.topClanScreen = new ClanScreen(0);
        CCanvas.serverListScreen = new ServerListScreen();
        CCanvas.luckyGifrScreen = new LuckyGifrScreen();
        CCanvas.fomulaScreen = new FomulaScreen();
        CCanvas.archScreen = new ArchivementScr();
        CCanvas.moneyScr = new MoneyScr();
        CCanvas.moneyScrIOS = new MoneyScrIOS();
        CCanvas.inventory = new Inventory();
        CCanvas.equipScreen = new EquipScreen();
        CCanvas.pausemenu = new PauseMenu();
        CCanvas.menu = new Menu();
        GameScr.mm = new MM();
        CMap.onInitCmap();
        CCanvas.iconMn = new IconManager();
    }

    public int getGameAction(int keyCode) {
        return this.getGameAction(keyCode);
    }

    public void mainLoop() {
        if (CCanvas.menu != null && CCanvas.menu.showMenu) {
            CCanvas.menu.update();
        }
        if (CCanvas.curScr != null) {
            CCanvas.curScr.mainLoop();
        }
    }

    public void update() {
        ++CCanvas.gameTick;
        if (CCanvas.gameTick > 10000) {
            CCanvas.gameTick = 0;
        }
        if (CCanvas.gameTick % 50 == 0) {
            GameService.gI().ping(CCanvas.gameTick, -1L);
            if (Session_ME.gI().connected) {
                ++GameMidlet.pingCount;
            }
        }
        if (GameMidlet.server == 2 && GameMidlet.pingCount > 15 && !CCanvas.isReconnect && Session_ME.gI().connected) {
            CCanvas.isReconnect = true;
            startYesNoDlg(Language.reConnect(), new IAction() {
                @Override
                public void perform() {
                    CCanvas.endDlg();
                    CCanvas.isReconnect = false;
                }
            }, new IAction() {
                @Override
                public void perform() {
                    GameLogicHandler.gI().onResetGame();
                    CCanvas.isReconnect = false;
                }
            });
            if (GameMidlet.ping) {
                GameMidlet.pingCount = 0;
                if (CCanvas.currentDialog != null) {
                    endDlg();
                }
            }
        }
        if (CCanvas.waitSendMessage > 0) {
            --CCanvas.waitSendMessage;
        }
        if (CCanvas.lockNotify) {
            ++CCanvas.tNotify;
            if (CCanvas.tNotify == 20) {
                CCanvas.tNotify = 0;
                CCanvas.lockNotify = false;
                Session_ME.receiveSynchronized = 0;
            }
        }
        if (Session_ME.receiveSynchronized == 1) {
            ++CCanvas.countNotify;
            if (CCanvas.countNotify > 3000) {
                CCanvas.countNotify = 0;
                Session_ME.receiveSynchronized = 0;
            }
        }
        if (CCanvas.currentDialog != null) {
            CCanvas.currentDialog.update();
        }
        for (int i = 0; i < CCanvas.arrPopups.size(); ++i) {
            CCanvas.arrPopups.elementAt(i).update();
        }
        if (CCanvas.curScr != null) {
            CCanvas.curScr.update();
        }
    }

    public void paint(mGraphics g) {
        if (CCanvas.curScr != null) {
            CCanvas.curScr.paint(g);
        }
        if (CCanvas.currentDialog != null) {
            CCanvas.currentDialog.paint(g);
        }
        if (CCanvas.menu != null && CCanvas.menu.showMenu) {
            CCanvas.menu.paintMenu(g);
        } else if (CCanvas.pausemenu != null && CCanvas.pausemenu.isShow) {
            CCanvas.pausemenu.paint(g);
        }
        for (int i = 0; i < CCanvas.arrPopups.size(); ++i) {
            if (!(CCanvas.arrPopups.elementAt(i) instanceof MsgPopup)) {
                CCanvas.arrPopups.elementAt(i).paint(g);
            }
        }
        for (int i = 0; i < CCanvas.arrPopups.size(); ++i) {
            Popup p = CCanvas.arrPopups.elementAt(i);
            if (p instanceof MsgPopup) {
                CCanvas.arrPopups.elementAt(i).paint(g);
                break;
            }
        }
    }

    public void keyPressed(int keyCode) {
        this.mapKeyPress(keyCode);
    }

    public void mapKeyPress(int keyCode) {
        if (CCanvas.currentDialog != null) {
            CCanvas.currentDialog.keyPress(keyCode);
        } else {
            boolean showMenu = CCanvas.menu.showMenu;
        }
        switch (keyCode) {
            case 42: {
                CCanvas.keyHold[10] = true;
                CCanvas.keyPressed[10] = true;
                break;
            }
            case 35: {
                CCanvas.keyHold[11] = true;
                CCanvas.keyPressed[11] = true;
                break;
            }
            case -21:
            case -6: {
                CCanvas.keyHold[12] = true;
                CCanvas.keyPressed[12] = true;
                break;
            }
            case -22:
            case -7: {
                CCanvas.keyHold[13] = true;
                CCanvas.keyPressed[13] = true;
                break;
            }
            case -5:
            case 10: {
                CCanvas.keyHold[5] = true;
                CCanvas.keyPressed[5] = true;
                break;
            }
            case -38:
            case -1: {
                CCanvas.keyHold[2] = true;
                CCanvas.keyPressed[2] = true;
                break;
            }
            case -39:
            case -2: {
                CCanvas.keyHold[8] = true;
                CCanvas.keyPressed[8] = true;
                break;
            }
            case -3: {
                CCanvas.keyHold[4] = true;
                CCanvas.keyPressed[4] = true;
                break;
            }
            case -4: {
                CCanvas.keyHold[6] = true;
                CCanvas.keyPressed[6] = true;
                break;
            }
        }
        if (CCanvas.curScr != null) {
            CCanvas.curScr.keyPressed(keyCode);
        }
    }

    public void keyReleased(int keyCode) {
        this.mapKeyRelease(keyCode);
    }

    public void mapKeyRelease(int keyCode) {
        switch (keyCode) {
            case 42: {
                CCanvas.keyHold[10] = false;
                CCanvas.keyReleased[10] = true;
                return;
            }
            case 35: {
                CCanvas.keyHold[11] = false;
                CCanvas.keyReleased[11] = true;
                return;
            }
            case -21:
            case -6: {
                CCanvas.keyHold[12] = false;
                CCanvas.keyReleased[12] = true;
                return;
            }
            case -22:
            case -7: {
                CCanvas.keyHold[13] = false;
                CCanvas.keyReleased[13] = true;
                return;
            }
            case -5:
            case 10: {
                CCanvas.keyHold[5] = false;
                CCanvas.keyReleased[5] = true;
                return;
            }
            case -38:
            case -1: {
                CCanvas.keyHold[2] = false;
                return;
            }
            case -39:
            case -2: {
                CCanvas.keyHold[8] = false;
                return;
            }
            case -3: {
                CCanvas.keyHold[4] = false;
                return;
            }
            case -4: {
                CCanvas.keyHold[6] = false;
                return;
            }
            default: {
                if (CCanvas.curScr != null) {
                    CCanvas.curScr.keyReleased(keyCode);
                }
            }
        }
    }

    public static void sendMapData() {
        CRes.out("=============================> SEND MAP DATA");
        CCanvas.mapIconVersion = (byte) loadVersion("iconversion2");
        if (CCanvas.mapIconVersion == -1) {
            CCanvas.mapIconVersion = 0;
        }
        CCanvas.mapValuesVersion = (byte) loadVersion("valuesversion2");
        if (CCanvas.mapValuesVersion == -1) {
            CCanvas.mapValuesVersion = 0;
        }
        CCanvas.playerVersion = (byte) loadVersion("playerVersion2");
        if (CCanvas.playerVersion == -1) {
            CCanvas.playerVersion = 0;
        }
        CCanvas.equipVersion = (byte) loadVersion("equipVersion2");
        if (CCanvas.equipVersion == -1) {
            CCanvas.equipVersion = 0;
        }
        CCanvas.levelCVersion = (byte) loadVersion("levelCVersion2");
        if (CCanvas.levelCVersion == -1) {
            CCanvas.levelCVersion = 0;
        }
        GameService.gI().sendVersion((byte) 2, CCanvas.mapValuesVersion);
        if (loadData("valuesdata2") != null) {
            readMess(loadData("valuesdata2"), (byte) 0);
        } else {
            LoginScr.isWait = true;
        }
        if (loadData("icondata2") != null) {
            PrepareScr.fileData = loadData("icondata2");
            if (PrepareScr.fileData != null) {
                PrepareScr.init();
            }
        }
        if (loadData("tiledata2") != null) {
            MM.fullData = loadData("tiledata2");
        }
        CPlayer.fileData = loadData("playerdata2");
        if (CPlayer.fileData != null) {
            CPlayer.init();
        }
        CRes.out("=======> loadData(\"equipdata2\") != null " + (loadData("equipdata2") != null));
        if (loadData("equipdata2") != null) {
            byte[] filepackDataRaw = loadData("equipdata2");
            if (filepackDataRaw != null) {
                readMess(filepackDataRaw, (byte) 1);
            }
        }
        if (loadData("levelCData2") != null) {
            byte[] filepackDataRaw = loadData("levelCData2");
            if (filepackDataRaw != null) {
                readMess(filepackDataRaw, (byte) 2);
            }
        }
    }

    public static void readMess(byte[] data, byte command) {
        Message msg = new Message(command, data);
        switch (command) {
            case 0: {
                try {
                    byte len6 = MM.NUM_MAP = msg.reader().readByte();
                    MM.mapName = new String[len6];
                    MM.mapFileName = new String[len6];
                    for (int i = 0; i < len6; ++i) {
                        byte fileID = msg.reader().readByte();
                        short fLenght = msg.reader().readShort();
                        byte[] fData = new byte[fLenght];
                        msg.reader().read(fData, 0, fLenght);
                        short[] values = new short[5];
                        for (int j = 0; j < 5; ++j) {
                            values[j] = msg.reader().readShort();
                        }
                        MM.mapName[i] = msg.reader().readUTF();
                        MM.mapFileName[i] = msg.reader().readUTF();
                        MapFile mf = new MapFile(fData, fileID, values);
                        MM.mapFiles.addElement(mf);
                        fData = null;
                    }
                    CRes.out("=============================> MM.mapFileName  " + len6);
                } catch (Exception ex) {
                }
                break;
            }
            case 1: {
                CRes.out("=============================> read Trang bi  type = 1 ");
                PlayerEquip.playerData = new Vector();
                try {
                    Vector<EquipGlass> vGlass = new Vector<EquipGlass>();
                    byte nglass = (byte) (CCanvas.nBigImage = msg.reader().readByte());
                    byte[] glass = new byte[nglass];
                    short maxDame = 0;
                    byte nFrame = 6;
                    byte nid = 0;
                    for (int k = 0; k < nglass; ++k) {
                        glass[k] = msg.reader().readByte();
                        maxDame = msg.reader().readShort();
                        EquipGlass eqGlass = new EquipGlass(glass[k]);
                        eqGlass.maxDamage = maxDame;
                        byte ntype = msg.reader().readByte();
                        byte[] type = new byte[ntype];
                        Vector<TypeEquip> vType = new Vector<TypeEquip>();
                        for (int l = 0; l < ntype; ++l) {
                            type[l] = msg.reader().readByte();
                            TypeEquip tEquip = new TypeEquip(type[l]);
                            Vector<Equip> vEquip = new Vector<Equip>();
                            nid = msg.reader().readByte();
                            for (int a = 0; a < nid; ++a) {
                                Equip e = new Equip();
                                e.id = msg.reader().readShort();
                                if (type[l] == 0) {
                                    e.bullet = msg.reader().readByte();
                                }
                                e.type = type[l];
                                e.glass = glass[k];
                                e.icon = msg.reader().readShort();
                                e.level = msg.reader().readByte();
                                e.x = new short[nFrame];
                                e.y = new short[nFrame];
                                e.w = new byte[nFrame];
                                e.h = new byte[nFrame];
                                e.dx = new byte[nFrame];
                                e.dy = new byte[nFrame];
                                for (int m = 0; m < nFrame; ++m) {
                                    e.x[m] = msg.reader().readShort();
                                    e.y[m] = msg.reader().readShort();
                                    e.w[m] = msg.reader().readByte();
                                    e.h[m] = msg.reader().readByte();
                                    e.dx[m] = msg.reader().readByte();
                                    e.dy[m] = msg.reader().readByte();
                                }
                                byte[] aibity = new byte[10];
                                for (int b = 0; b < 10; ++b) {
                                    aibity[b] = msg.reader().readByte();
                                }
                                e.setInvAtribute();
                                e.getInvAtribute(aibity);
                                e.getShopAtribute(aibity);
                                vEquip.addElement(e);
                            }
                            tEquip.addEquip(vEquip);
                            vType.addElement(tEquip);
                        }
                        eqGlass.addType(vType);
                        vGlass.addElement(eqGlass);
                    }
                    PlayerEquip.addGlassEquip(vGlass);
                    short lenIcon = msg.reader().readShort();
                    byte[] iconImg = new byte[lenIcon];
                    msg.reader().read(iconImg, 0, lenIcon);
                    CRes.out("2 =============================> read Trang bi  type = 1 ");
                    mImage.createImage("/equip/01.png", new IAction2() {
                        @Override
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[0] = new mImage((Image) object);
                        }
                    });
                    mImage.createImage("/equip/02.png", new IAction2() {
                        @Override
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[1] = new mImage((Image) object);
                        }
                    });
                    mImage.createImage("/equip/03.png", new IAction2() {
                        @Override
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[2] = new mImage((Image) object);
                        }
                    });
                    mImage.createImage("/equip/04.png", new IAction2() {
                        @Override
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[3] = new mImage((Image) object);
                        }
                    });
                    mImage.createImage("/equip/05.png", new IAction2() {
                        @Override
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[4] = new mImage((Image) object);
                        }
                    });
                    CRes.out("3 =============================> read Trang bi  type = 1 ");
                    byte[] bullets = null;
                    for (int i2 = 0; i2 < 10; ++i2) {
                        short lentBullet = msg.reader().readShort();
                        bullets = new byte[lentBullet];
                        msg.reader().read(bullets, 0, lentBullet);
                        mImage.createImage(bullets, 0, lentBullet, new IAction2() {
                            @Override
                            public void perform(Object object) {
                                try {
                                    PlayerEquip.bullets[CCanvas.indexBullet] = new mImage((Image) object);
                                    indexBullet = CCanvas.indexBullet + 1;
                                } catch (Exception ex) {
                                }
                            }
                        });
                    }
                    bullets = null;
                    CRes.out("===================> create PlayerEquip.playerData to set myEquip!");
                    CRes.out("__ =============================> read Trang bi  type = 1 have PlayerEquip.playerData "
                            + (PlayerEquip.playerData != null));
                    CRes.out("4 =============================> read Trang bi  type = 1 !!!!! DONE!!!!");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    byte size = msg.reader().readByte();
                    PlayerInfo.strLevelCaption = new String[size];
                    PlayerInfo.levelCaption = new int[size];
                    for (int i = 0; i < size; ++i) {
                        String name = msg.reader().readUTF();
                        int type2 = msg.reader().readUnsignedByte();
                        PlayerInfo.strLevelCaption[i] = name;
                        PlayerInfo.levelCaption[i] = type2;
                    }
                    TerrainMidlet.myInfo.getQuanHam();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                break;
            }
        }
        msg.cleanup();
        msg = null;
    }

    public static void saveData(String name, byte[] data) {
        try {
            RMS.saveRMS(name, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] loadData(String name) {
        return CRes.loadRMSData(name);
    }

    public static void saveVersion(String name, byte version) {
        CRes.saveRMSInt(name, version);
    }

    public static int loadVersion(String name) {
        return CRes.loadRMSInt(name);
    }

    public static Image cutImage(mImage img, int pos) {
        int sw = img.image.getWidth();
        int[] data = new int[sw * sw];
        img.getRGB(data, 0, sw, 0, pos * sw, sw, sw);
        return Image.createRGBImage(data, sw, sw, true);
    }

    public static Image cutImage(mImage img, int pos, IAction2 iAction) {
        int sw = img.image.getWidth();
        int[] data = new int[sw * sw];
        img.getRGB(data, 0, sw, 0, pos * sw, sw, sw);
        return Image.createRGBImage(data, sw, sw, true, iAction);
    }

    public static Image rotateImage(Image src, int angle, mGraphics g, int x, int y, boolean isRGB) {
        int sw = src.getWidth();
        int sh = src.getHeight();
        int[] srcData = new int[sw * sh];
        src.getRGB(srcData, 0, sw, 0, 0, sw, sh);
        int[] dstData = new int[sw * sh];
        float sa = (float) CRes.sin(angle);
        float ca = (float) CRes.cos(angle);
        int isa = (int) (256.0f * sa) / 1000;
        int ica = (int) (256.0f * ca) / 1000;
        int my = -(sh >> 1);
        for (int i = 0; i < sh; ++i) {
            int wpos = i * sw;
            int xacc = my * isa - (sw >> 1) * ica + (sw >> 1 << 8);
            int yacc = my * ica + (sw >> 1) * isa + (sh >> 1 << 8);
            for (int j = 0; j < sw; ++j) {
                int srcx = xacc >> 8;
                int srcy = yacc >> 8;
                if (srcx < 0) {
                    srcx = 0;
                }
                if (srcy < 0) {
                    srcy = 0;
                }
                if (srcx > sw - 1) {
                    srcx = sw - 1;
                }
                if (srcy > sh - 1) {
                    srcy = sh - 1;
                }
                dstData[wpos++] = srcData[srcx + srcy * sw];
                xacc += ica;
                yacc -= isa;
            }
            ++my;
        }
        isRGB = false;
        if (isRGB) {
            g.drawRGB(dstData, 0, sw, x - sw / 2, y - sw / 2, sw, sh, true);
            return null;
        }
        return Image.createRGBImage(dstData, sw, sh, true);
    }

    public static void rotateImage(Image src, int angle, mGraphics g, int x, int y, boolean isRGB, IAction2 action) {
        int sw = src.getWidth();
        int sh = src.getHeight();
        int[] srcData = new int[sw * sh];
        src.getRGB(srcData, 0, sw, 0, 0, sw, sh);
        int[] dstData = new int[sw * sh];
        float sa = (float) CRes.sin(angle);
        float ca = (float) CRes.cos(angle);
        int isa = (int) (256.0f * sa) / 1000;
        int ica = (int) (256.0f * ca) / 1000;
        int my = -(sh >> 1);
        for (int i = 0; i < sh; ++i) {
            int wpos = i * sw;
            int xacc = my * isa - (sw >> 1) * ica + (sw >> 1 << 8);
            int yacc = my * ica + (sw >> 1) * isa + (sh >> 1 << 8);
            for (int j = 0; j < sw; ++j) {
                int srcx = xacc >> 8;
                int srcy = yacc >> 8;
                if (srcx < 0) {
                    srcx = 0;
                }
                if (srcy < 0) {
                    srcy = 0;
                }
                if (srcx > sw - 1) {
                    srcx = sw - 1;
                }
                if (srcy > sh - 1) {
                    srcy = sh - 1;
                }
                dstData[wpos++] = srcData[srcx + srcy * sw];
                xacc += ica;
                yacc -= isa;
            }
            ++my;
        }
        Image.createRGBImage(dstData, sw, sh, true, action);
    }

    public static void rotateImage(mImage dan, int de, mGraphics g2, int x, int y, boolean b) {
        int sw = dan.image.getWidth();
        int sh = dan.image.getHeight();
        g2.rotate(de, sw / 2, sh / 2);
        g2.drawImage(dan, x, y, mGraphics.VCENTER | mGraphics.HCENTER, false);
        g2.resetRotate();
    }

    public static boolean isPointer(int x, int y, int w, int h, int index) {
        return (CCanvas.isPointerDown[index] || CCanvas.isPointerClick[index]) && CCanvas.pX[index] >= x
                && CCanvas.pX[index] <= x + w && CCanvas.pY[index] >= y && CCanvas.pY[index] <= y + h;
    }

    public static boolean isPointerLast(int x, int y, int w, int h, int index) {
        return (CCanvas.isPointerDown[index] || CCanvas.isPointerClick[index]) && CCanvas.pxLast[index] >= x
                && CCanvas.pxLast[index] <= x + w && CCanvas.pyLast[index] >= y && CCanvas.pyLast[index] <= y + h;
    }

    public static void startOKDlg(String info) {
        CCanvas.msgdlg.setInfo(info, null, new Command("OK", new IAction() {
            @Override
            public void perform() {
                CCanvas.currentDialog = null;
                if (CCanvas.curScr.menuScroll) {
                    CCanvas.menuScr.startScrollDown();
                }
            }
        }), null);
        CCanvas.currentDialog = CCanvas.msgdlg;
    }

    public static void closeDlg() {
        CCanvas.currentDialog = null;
        if (CCanvas.curScr.menuScroll) {
            CCanvas.menuScr.startScrollDown();
        }
    }

    public static void startOKDlg(String info, final IAction action) {
        CCanvas.msgdlg.setInfo(info, null, new Command("OK", new IAction() {
            @Override
            public void perform() {
                if (CCanvas.curScr.menuScroll) {
                    CCanvas.menuScr.startScrollDown();
                }
                CCanvas.currentDialog = null;
                if (action != null) {
                    action.perform();
                }
            }
        }), null);
        CCanvas.currentDialog = CCanvas.msgdlg;
    }

    public static void startYesNoDlg(String info, IAction yesAction) {
        CCanvas.msgdlg.setInfo(info, new Command(Language.yes(), yesAction), new Command("", yesAction),
                new Command(Language.no(), new IAction() {
                    @Override
                    public void perform() {
                        if (CCanvas.curScr.menuScroll) {
                            CCanvas.menuScr.startScrollDown();
                        }
                        CCanvas.currentDialog = null;
                    }
                }));
        CCanvas.currentDialog = CCanvas.msgdlg;
    }

    public static void startYesNoDlg(String info, IAction yesAction, IAction noAction) {
        CCanvas.msgdlg.setInfo(info, new Command(Language.yes(), yesAction), new Command("", yesAction),
                new Command(Language.no(), noAction));
        CCanvas.currentDialog = CCanvas.msgdlg;
    }

    public static void startWaitDlg(String info) {
        CCanvas.msgdlg.setInfo(info, null, new Command("Cancel", new IAction() {
            @Override
            public void perform() {
                if (CCanvas.curScr.menuScroll) {
                    CCanvas.menuScr.startScrollDown();
                }
                CCanvas.currentDialog = null;
            }
        }), null);
        CCanvas.currentDialog = CCanvas.msgdlg;
    }

    public static void startWaitDlgWithoutCancel(String info, int index) {
        CCanvas.msgdlg.setInfo(info, null, null, null);
        CCanvas.currentDialog = CCanvas.msgdlg;
    }

    public static void startWaitDlgWithoutCancel(String info, long timeShow, IAction callback) {
        CCanvas.msgdlg.setInfo(info, timeShow, callback, null, null, null);
        CCanvas.currentDialog = CCanvas.msgdlg;
    }

    public static int random(int a, int b) {
        return a + CCanvas.r.nextInt(b - a);
    }

    public static void endDlg() {
        if (CCanvas.currentDialog != null) {
            CCanvas.currentDialog.close();
        }
        CCanvas.currentDialog = null;
    }

    public void stopGame() {
        this.isRunning = false;
        if (CCanvas.gameScr != null) {
            GameService.gI().leaveBoard();
        }
    }

    @Override
    public void onPointerDragged(int x, int y, int index) {
        CCanvas.isPointerSelect[index] = false;
        CCanvas.pX[index] = x;
        CCanvas.pY[index] = y;
        if (CCanvas.isPointerMove[index]) {
            this.listPoint.addElement(new Position(x, y));
        } else if (CRes.abs(CCanvas.pX[index] - CCanvas.pxLast[index]) >= 15
                || CRes.abs(CCanvas.pY[index] - CCanvas.pyLast[index]) >= 15) {
            CCanvas.isPointerMove[index] = true;
        }
        ++this.curPos;
        if (this.curPos > 3) {
            this.curPos = 0;
        }
        if (CCanvas.currentDialog != null) {
            return;
        }
        if (CCanvas.menu != null && CCanvas.menu.showMenu) {
            CCanvas.menu.onPointerDragged(x, y, index);
            return;
        }
        if (CCanvas.curScr != null) {
            CCanvas.curScr.onPointerDragged(x, y, index);
        }
    }

    @Override
    public void onPointerPressed(int xScreen, int yScreen, int index, int button) {
        CRes.out("==> press " + (mSystem.currentTimeMillis() - CCanvas.timeClick));
        CCanvas.isDoubleClick = (mSystem.currentTimeMillis() - CCanvas.timeClick < CCanvas.MAX_TIME_CLICK);
        CCanvas.isPointerDown[index] = true;
        CCanvas.isPointerRelease[index] = false;
        CCanvas.isPointerMove[index] = false;
        CCanvas.isPointerSelect[index] = false;
        CCanvas.pxFirst[index] = xScreen;
        CCanvas.pyFirst[index] = yScreen;
        CCanvas.pX[index] = xScreen;
        CCanvas.pY[index] = yScreen;
        CCanvas.pointer = index;
        CCanvas.button = button;
        for (int i = 0; i < CCanvas.arrPopups.size(); ++i) {
            CCanvas.arrPopups.elementAt(i).onPointerPressed(xScreen, yScreen, index);
        }
        if (CCanvas.menu != null && CCanvas.menu.showMenu) {
            CCanvas.menu.onPointerPressed(xScreen, yScreen, index);
            return;
        }
        if (CCanvas.currentDialog != null) {
            CCanvas.currentDialog.onPointerPressed(xScreen, yScreen, index);
            if (CCanvas.inputDlg != null) {
                CCanvas.inputDlg.onPointerPressed(xScreen, yScreen, index);
            }
            return;
        }
        if (CCanvas.pausemenu != null && CCanvas.pausemenu.isShow) {
            CCanvas.pausemenu.onPointerPressed(xScreen, yScreen, index);
            return;
        }
        if (CCanvas.curScr != null) {
            CCanvas.curScr.onPointerPressed(xScreen, yScreen, index);
        }
    }

    @Override
    public void onPointerReleased(int x, int y, int index, int button) {
        CCanvas.timeClick = mSystem.currentTimeMillis();
        if (!CCanvas.isPointerMove[index]) {
            CCanvas.isPointerSelect[index] = true;
        }
        CCanvas.isPointerDown[index] = false;
        CCanvas.isPointerRelease[index] = true;
        CCanvas.isPointerMove[index] = false;
        CCanvas.isPointerClick[index] = true;
        CCanvas.pxLast[index] = x;
        CCanvas.pyLast[index] = y;
        CCanvas.pointer = index;
        CCanvas.button = button;
        if (CCanvas.menu != null && CCanvas.menu.showMenu) {
            CCanvas.menu.onPointerReleased(x, y, index);
            return;
        }
        if (CCanvas.currentDialog != null) {
            CCanvas.currentDialog.onPointerReleased(x, y, index);
            return;
        }
        for (int i = 0; i < CCanvas.arrPopups.size(); ++i) {
            Popup p = CCanvas.arrPopups.elementAt(i);
            if (p instanceof MsgPopup) {
                CCanvas.arrPopups.elementAt(i).onPointerReleased(x, y, index);
            }
        }
        if (CCanvas.curScr != null) {
            CCanvas.curScr.onPointerReleased(x, y, index);
        }
    }

    @Override
    public void onPointerHolder(int xScreen, int yScreen, int index) {
        if (index == -1) {
            return;
        }
        if (mSystem.currentTimeMillis() < this.timeHold) {
            return;
        }
        this.timeHold = mSystem.currentTimeMillis() + 50L;
        CCanvas.isPointerDown[index] = true;
        CCanvas.isPointerRelease[index] = false;
        CCanvas.isPointerMove[index] = false;
        CCanvas.isPointerSelect[index] = false;
        CCanvas.pX[index] = xScreen;
        CCanvas.pY[index] = yScreen;
        CCanvas.pointer = index;
        CCanvas.button = -1;
        if (CCanvas.curScr != null) {
            CCanvas.curScr.onPointerHold(xScreen, yScreen, index);
        }
    }

    @Override
    public void onPointerHolder() {
    }

    public void keyHold(int keycode) {
    }

    public void keyHold(char keycode) {
    }

    @Override
    public void perform(int idAction, Object p) {
    }

    public static void clearKeyHold() {
    }

    public static void resetTrans(mGraphics g) {
    }

    @Override
    public void onClearMap() {
    }

    public static boolean isTouchAndKey() {
        return false;
    }

    public static boolean isTouchNoOrPC() {
        return !CCanvas.isTouch || isTouchAndKey();
    }

    public static boolean isJ2ME() {
        return GameMidlet.DEVICE == 0;
    }

    public static boolean isPc() {
        return GameMidlet.DEVICE == 4;
    }

    public static boolean isIos() {
        return GameMidlet.DEVICE == 2 || GameMidlet.DEVICE == 6;
    }

    public static boolean isIosStore() {
        return GameMidlet.DEVICE == 6;
    }

    public static boolean isGDX() {
        return isPc() || isIos();
    }

    public static boolean isAndroid() {
        return GameMidlet.DEVICE == 1 || GameMidlet.DEVICE == 5;
    }

    public static boolean isAndroidStore() {
        return GameMidlet.DEVICE == 5;
    }

    public static boolean isStore() {
        return GameMidlet.DEVICE == 5 || GameMidlet.DEVICE == 6;
    }

    public static boolean isDebugging() {
        return GameMidlet.COMPILE == 0;
    }

    public static boolean isTabScreen() {
        return false;
    }

    public static boolean isTabClanScreen() {
        return false;
    }

    public static int getIPdx() {
        if (isIos()) {
            return 20;
        }
        return 0;
    }

    public static String getClassPathConfig(String pathConfig) {
        if (isPc()) {
            return "/res/" + pathConfig;
        }
        return "res/" + pathConfig;
    }

    public void backAndroid() {
        if (CCanvas.curScr != null && CCanvas.curScr.right != null) {
            CCanvas.curScr.right.action.perform();
        }
    }

    public static void onClearCCanvas() {
        CCanvas.luckyGame = null;
        CCanvas.shopEquipScr = null;
        CCanvas.shopItemScr = null;
        CCanvas.shopLinhtinh = null;
        CCanvas.listScr = null;
        CCanvas.msgScr = null;
        CCanvas.infoPopup = null;
        CCanvas.prepareScr = null;
        CCanvas.inputDlg = null;
        CCanvas.msgPopup = null;
        CCanvas.clanScreen = null;
        CCanvas.topClanScreen = null;
        CCanvas.serverListScreen = null;
        CCanvas.luckyGifrScreen = null;
        CCanvas.fomulaScreen = null;
        CCanvas.archScreen = null;
        CCanvas.moneyScr = null;
        CCanvas.moneyScrIOS = null;
        CCanvas.inventory = null;
        CCanvas.equipScreen = null;
        CCanvas.gameScr = null;
        CCanvas.pausemenu = null;
        CCanvas.msgdlg = new MsgDlg();
        CCanvas.currentDialog = null;
        CCanvas.arrPopups = new Vector<Popup>();
        CCanvas.msgPopup = null;
        CCanvas.infoPopup = null;
    }

    static {
        CCanvas.menu = new Menu();
        CCanvas.pausemenu = new PauseMenu();
        CCanvas.isWifi = false;
        CCanvas.msgdlg = new MsgDlg();
        CCanvas.arrPopups = new Vector<Popup>();
        CCanvas.pX = new int[2];
        CCanvas.pY = new int[2];
        CCanvas.pxLast = new int[2];
        CCanvas.pyLast = new int[2];
        CCanvas.pxFirst = new int[2];
        CCanvas.pyFirst = new int[2];
        CCanvas.keyPressed = new boolean[55];
        CCanvas.keyReleased = new boolean[55];
        CCanvas.keyHold = new boolean[55];
        CCanvas.isPointerDown = new boolean[2];
        CCanvas.isPointerRelease = new boolean[2];
        CCanvas.isPointerSelect = new boolean[2];
        CCanvas.isPointerMove = new boolean[2];
        CCanvas.isPointerClick = new boolean[2];
        CCanvas.MAX_TIME_CLICK = 120;
        CCanvas.indexBullet = 0;
        CCanvas.r = new Random();
    }
}
