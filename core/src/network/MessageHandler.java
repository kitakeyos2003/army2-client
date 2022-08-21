package network;

import screen.QuangCao;
import model.Mission;
import model.Fomula;
import model.CTime;
import screen.LuckyGifrScreen;
import model.Font;
import model.LuckyGift;
import model.ClanItem;
import shop.ShopBietDoi;
import model.ImageIcon;
import model.MaterialIconMn;
import effect.GiftEffect;
import screen.LuckyGame;
import model.Clan;
import model.TimeBomb;
import shop.ShopEquipment;
import Equipment.Equip;
import screen.LevelScreen;
import CLib.RMS;
import Equipment.PlayerEquip;
import player.CPlayer;
import model.IAction;
import CLib.mSystem;
import CLib.mImage;
import item.Bullet;
import item.BM;
import screen.CScreen;
import screen.MoneyScr;
import screen.MoneyScrIOS;
import InApp.MainActivity;
import model.MoneyInfo;
import model.MsgInfo;
import player.PM;
import screen.PrepareScr;
import screen.GameScr;
import screen.BoardListScr;
import model.BoardInfo;
import screen.RoomListScr2;
import map.MM;
import model.Language;
import model.RoomInfo;
import coreLG.CCanvas;
import screen.LoginScr;
import screen.MenuScr;
import screen.ChangePlayerCSr;
import shop.ShopItem;
import item.Item;
import java.util.Vector;
import com.teamobi.mobiarmy2.GameMidlet;
import coreLG.TerrainMidlet;
import model.PlayerInfo;
import model.CRes;

public class MessageHandler implements IMessageHandler {

    IGameLogicHandler gameLogicHandler;
    protected static MessageHandler instance;
    public static boolean nextTurnFlag;
    public static boolean lag;
    public static Object LOCK;
    public static int dem;
    public static long currt;
    public static long timePing;

    public static MessageHandler gI() {
        if (MessageHandler.instance == null) {
            MessageHandler.instance = new MessageHandler();
        }
        return MessageHandler.instance;
    }

    @Override
    public void onConnectOK() {
        this.gameLogicHandler.onConnectOK();
    }

    @Override
    public void onConnectionFail() {
        this.gameLogicHandler.onConnectFail();
    }

    @Override
    public void onDisconnected() {
        this.gameLogicHandler.onDisconnect();
    }

    @Override
    public void onMessage(Message msg) {
        if (msg != null) {
            try {

                switch (msg.command) {
                    case 4: {
                        this.gameLogicHandler.onLoginFail(msg.reader().readUTF());
                        break;
                    }
                    case 3: {
                        CRes.out("=========> Cmd_Server2Client.LOGIN_SUCESS:");
                        GameService.gI().platform_request();
                        GameService.gI().bangxephang((byte) (-1), -1);
                        MessageHandler.currt = System.currentTimeMillis();
                        GameService.gI().ping(MessageHandler.dem, -1L);
                        TerrainMidlet.myInfo = new PlayerInfo();
                        if (GameMidlet.server == 2) {
                            TerrainMidlet.myInfo.name = msg.reader().readUTF();
                        }
                        TerrainMidlet.myInfo.IDDB = msg.reader().readInt();
                        TerrainMidlet.myInfo.xu = msg.reader().readInt();
                        TerrainMidlet.myInfo.luong = msg.reader().readInt();
                        TerrainMidlet.myInfo.gun = msg.reader().readByte();
                        CRes.out(" TerrainMidlet.myInfo.gun " + TerrainMidlet.myInfo.gun);
                        TerrainMidlet.myInfo.clanID = msg.reader().readShort();
                        TerrainMidlet.myInfo.isMaster = msg.reader().readByte();
                        for (int i = 0; i < 10; ++i) {
                            if ((TerrainMidlet.isVip[i] = msg.reader().readBoolean())) {
                                for (int j = 0; j < 5; ++j) {
                                    TerrainMidlet.myInfo.equipVipID[i][j] = msg.reader().readShort();
                                }
                            }
                            for (int j = 0; j < 5; ++j) {
                                TerrainMidlet.myInfo.equipID[i][j] = msg.reader().readShort();
                            }
                        }
                        Vector<Item> SellItem = new Vector<>();
                        System.out.println("=============ITEM==============");
                        for (int k = 0; k < 36; ++k) {
                            byte numI = msg.reader().readByte();
                            int price = msg.reader().readInt();
                            int price2 = msg.reader().readInt();
                            SellItem.addElement(new Item((byte) k, numI, price, price2));
                        }
                        ShopItem.setItemVector(SellItem);
                        System.out.println("=============NHAN VAT==============");
                        for (int l = 0; l < 10; ++l) {
                            if (l < 3) {
                                ChangePlayerCSr.isUnlock[l] = 1;
                                ChangePlayerCSr.gunXu[l] = 0;
                                ChangePlayerCSr.gunLuong[l] = 0;
                            } else {
                                ChangePlayerCSr.isUnlock[l] = msg.reader().readByte();
                                ChangePlayerCSr.gunXu[l] = msg.reader().readShort() * 1000;
                                ChangePlayerCSr.gunLuong[l] = msg.reader().readShort();

                            }
                        }
                        MenuScr.suKienStr = msg.reader().readUTF().toUpperCase();
                        MenuScr.linkWapStr = msg.reader().readUTF().toUpperCase();
                        MenuScr.linkTeam = msg.reader().readUTF().toUpperCase();
                        MenuScr.getIdMenu(0);
                        MenuScr.menuString[MenuScr.MENU_SUKIEN] = MenuScr.suKienStr.toUpperCase().toUpperCase();
                        if (!LoginScr.isLoadData) {
                            CCanvas.sendMapData();
                            break;
                        }
                        GameService.gI().sendVersion((byte) 5, (byte) 0);
                        GameLogicHandler.gI().onLoginSuccess();
                        break;
                    }
                    case -28: {
                        Vector<RoomInfo> roomList2 = new Vector();
                        byte raction = msg.reader().readByte();
                        byte rlevel = -1;
                        byte subLevel = -1;
                        while (msg.reader().available() > 0) {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.id = msg.reader().readByte();
                            if (roomInfo.id != -1) {
                                roomInfo.boardID = msg.reader().readByte();
                                roomInfo.mapID = msg.reader().readByte();
                                roomInfo.playerMax = String.valueOf(msg.reader().readByte()) + "/"
                                        + msg.reader().readByte();
                                roomInfo.money = msg.reader().readInt();
                                String mapName = (roomInfo.mapID == 100) ? Language.random()
                                        : MM.mapName[roomInfo.mapID];
                                roomInfo.name = "P" + roomInfo.id + "-" + roomInfo.boardID + " "
                                        + ((CCanvas.width > 200) ? ("(" + mapName + ")") : "");
                                roomInfo.lv = rlevel;
                                roomList2.addElement(roomInfo);
                            } else {
                                roomInfo.name = msg.reader().readUTF();
                                roomList2.addElement(roomInfo);
                                ++subLevel;
                                ++rlevel;
                                RoomInfo roomCreate = new RoomInfo();
                                roomCreate.name = Language.createZone();
                                roomCreate.lv = rlevel;
                                roomCreate.boardID = -1;
                                roomList2.addElement(roomCreate);
                            }
                        }
                        CCanvas.endDlg();
                        CCanvas.roomListScr2.isEmptyRoom = true;
                        CCanvas.roomListScr2.setRoomList(roomList2);
                        CCanvas.roomListScr2.show();
                        if (CCanvas.gameScr != null) {
                            CCanvas.gameScr.onClearMap();
                            CCanvas.gameScr = null;
                            break;
                        }
                        break;
                    }
                    case 6: {
                        Vector<RoomInfo> roomList3 = new Vector();
                        while (msg.reader().available() > 0) {
                            RoomInfo roomInfo2 = new RoomInfo();
                            roomInfo2.id = msg.reader().readByte();
                            byte a = msg.reader().readByte();
                            roomInfo2.roomFree = a;
                            roomInfo2.roomWait = msg.reader().readByte();
                            roomInfo2.lv = msg.reader().readByte();
                            roomList3.addElement(roomInfo2);
                        }
                        CCanvas.endDlg();
                        if (CCanvas.roomListScr2 == null) {
                            CCanvas.roomListScr2 = new RoomListScr2();
                        }
                        CCanvas.roomListScr2.isEmptyRoom = false;
                        CCanvas.roomListScr2.setRoomList(roomList3);
                        CCanvas.roomListScr2.show();
                        if (CCanvas.gameScr != null) {
                            CCanvas.gameScr.onClearMap();
                            CCanvas.gameScr = null;
                        }
                        GameService.gI().changeRoomName();
                        break;
                    }
                    case 7: {
                        if (CCanvas.curScr == CCanvas.prepareScr) {
                            return;
                        }
                        Vector<BoardInfo> boardList = new Vector<>();
                        byte roomID = msg.reader().readByte();
                        while (msg.reader().available() > 0) {
                            BoardInfo boardInfo = new BoardInfo();
                            boardInfo.boardID = msg.reader().readByte();
                            boardInfo.nPlayer = msg.reader().readByte();
                            boardInfo.maxPlayer = msg.reader().readByte();
                            boardInfo.isPass = msg.reader().readBoolean();
                            boardInfo.money = msg.reader().readInt();
                            boardInfo.isPlaying = msg.reader().readBoolean();
                            boardInfo.name = msg.reader().readUTF();
                            boardInfo.mode = msg.reader().readByte();
                            boardList.addElement(boardInfo);
                        }
                        CCanvas.endDlg();
                        if (CCanvas.boardListScr == null) {
                            CCanvas.boardListScr = new BoardListScr();
                        }
                        CCanvas.boardListScr.roomID = roomID;
                        CCanvas.boardListScr.setBoardList(boardList);
                        CCanvas.boardListScr.show();
                        if (CCanvas.gameScr != null) {
                            CCanvas.gameScr.onClearMap();
                            CCanvas.gameScr = null;
                            break;
                        }
                        break;
                    }
                    case 8: {
                        int ownerID = msg.reader().readInt();
                        int money = msg.reader().readInt();
                        byte map = msg.reader().readByte();
                        byte gameMODE = msg.reader().readByte();
                        Vector<PlayerInfo> players = new Vector<>();
                        while (msg.reader().available() > 0) {
                            PlayerInfo playerInfo = new PlayerInfo();
                            playerInfo.IDDB = msg.reader().readInt();
                            if (playerInfo.IDDB == -1) {
                                playerInfo.name = "";
                            } else {
                                playerInfo.clanID = msg.reader().readShort();
                                playerInfo.name = msg.reader().readUTF();
                                playerInfo.xu = msg.reader().readInt();
                                playerInfo.level2 = msg.reader().readUnsignedByte();
                                playerInfo.getQuanHam();
                                playerInfo.gun = msg.reader().readByte();
                                for (int m = 0; m < 5; ++m) {
                                    playerInfo.equipID[playerInfo.gun][m] = msg.reader().readShort();
                                    playerInfo.getMyEquip(1);
                                }
                                playerInfo.isReady = msg.reader().readBoolean();
                            }
                            players.addElement(playerInfo);
                        }
                        CCanvas.endDlg();
                        GameScr.trainingMode = false;
                        CCanvas.prepareScr.setPlayers(ownerID, money, players);
                        for (int i2 = 0; i2 < players.size(); ++i2) {
                            PlayerInfo p = (PlayerInfo) players.elementAt(i2);
                            if (p.IDDB == ownerID) {
                                p.isReady = true;
                            }
                        }
                        if (CCanvas.prepareScr == null) {
                            CCanvas.prepareScr = new PrepareScr();
                        }
                        CCanvas.prepareScr.show();
                        CCanvas.prepareScr.onResetPrepare();
                        CCanvas.prepareScr.getIcon();
                        this.gameLogicHandler.onJoinGameSuccess(ownerID, money, players, map);
                        break;
                    }
                    case 12: {
                        PlayerInfo joinPersonInfo = new PlayerInfo();
                        int seat = msg.reader().readByte();
                        joinPersonInfo.IDDB = msg.reader().readInt();
                        joinPersonInfo.clanID = msg.reader().readShort();
                        joinPersonInfo.name = msg.reader().readUTF();
                        joinPersonInfo.level2 = msg.reader().readUnsignedByte();
                        joinPersonInfo.getQuanHam();
                        joinPersonInfo.gun = msg.reader().readByte();
                        for (int i3 = 0; i3 < 5; ++i3) {
                            joinPersonInfo.equipID[joinPersonInfo.gun][i3] = msg.reader().readShort();
                            joinPersonInfo.getMyEquip(2);
                        }
                        joinPersonInfo.isReady = false;
                        CCanvas.prepareScr.setAt(seat, joinPersonInfo);
                        GameService.gI().getClanIcon(joinPersonInfo.clanID);
                        this.gameLogicHandler.onSomeOneJoinBoard(seat, joinPersonInfo);
                        break;
                    }
                    case 14: {
                        int IDLeave = msg.reader().readInt();
                        int IDNewOwner = msg.reader().readInt();
                        CCanvas.prepareScr.playerLeave(IDLeave);
                        CCanvas.prepareScr.setOwner(IDNewOwner);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            int i4 = 0;
                            while (i4 < PM.p.length) {
                                CPlayer p2 = PM.p[i4];
                                if (p2 != null && IDLeave == p2.IDDB) {
                                    if (PrepareScr.currLevel != 7) {
                                        PM.p[i4].setState((byte) 5);
                                        break;
                                    }
                                    PM.p[i4] = null;
                                    break;
                                } else {
                                    ++i4;
                                }
                            }
                        }
                        if (PrepareScr.currLevel != 7) {
                            GameScr.cam.setPlayerMode(PM.curP);
                        }
                        Session_ME.receiveSynchronized = 0;
                        break;
                    }
                    case 16: {
                        int ID = msg.reader().readInt();
                        boolean isReady = msg.reader().readBoolean();
                        this.gameLogicHandler.onSomeOneReady(ID, isReady);
                        break;
                    }
                    case 19: {
                        msg.reader().readShort();
                        int money2 = msg.reader().readInt();
                        CCanvas.prepareScr.setMoney(money2);
                        break;
                    }
                    case 9: {
                        int from = msg.reader().readInt();
                        String text = msg.reader().readUTF();
                        this.gameLogicHandler.onChatFromBoard(text, from);
                        break;
                    }
                    case 11: {
                        msg.reader().readShort();
                        int kicked = msg.reader().readInt();
                        String reason = msg.reader().readUTF();
                        this.gameLogicHandler.onKicked(kicked, reason);
                        CRes.out("NHAN M KICK id: " + kicked);
                        break;
                    }
                    case 29: {
                        Vector<PlayerInfo> friendList = new Vector<>();
                        while (msg.reader().available() > 0) {
                            PlayerInfo info = new PlayerInfo();
                            info.IDDB = msg.reader().readInt();
                            info.name = msg.reader().readUTF();
                            info.xu = msg.reader().readInt();
                            info.gun = msg.reader().readByte();
                            info.clanID = msg.reader().readShort();
                            info.isReady = (msg.reader().readByte() != 0);
                            info.level2 = msg.reader().readUnsignedByte();
                            info.level2Percen = msg.reader().readByte();
                            info.getQuanHam();
                            short[] idEq = new short[5];
                            for (int i5 = 0; i5 < 5; ++i5) {
                                idEq[i5] = msg.reader().readShort();
                                info.equipID[info.gun][i5] = idEq[i5];
                                info.getMyEquip(3);
                            }
                            friendList.addElement(info);
                        }
                        this.gameLogicHandler.onFriendList(friendList);
                        break;
                    }
                    case 36: {
                        Vector<PlayerInfo> searchList = new Vector<>();
                        while (msg.reader().available() > 0) {
                            PlayerInfo info2 = new PlayerInfo();
                            info2.IDDB = msg.reader().readInt();
                            info2.name = msg.reader().readUTF();
                            searchList.addElement(info2);
                        }
                        this.gameLogicHandler.onSearchResult(searchList);
                        break;
                    }
                    case 32: {
                        byte addResult = msg.reader().readByte();
                        this.gameLogicHandler.onAddFriendResult(addResult);
                        break;
                    }
                    case 33: {
                        byte delResult = msg.reader().readByte();
                        this.gameLogicHandler.onDelFriendResult(delResult);
                        break;
                    }
                    case 5: {
                        MsgInfo msg2 = new MsgInfo();
                        msg2.fromID = msg.reader().readInt();
                        msg2.fromName = msg.reader().readUTF();
                        msg2.message = msg.reader().readUTF();
                        this.gameLogicHandler.onChatFrom(msg2);
                        break;
                    }
                    case 122: {
                        try {
                            Vector<MoneyInfo> avs = new Vector<>();
                            CCanvas.isPurchaseIOS = CCanvas.isIos();
                            if (CCanvas.isIos()) {
                                for (int i6 = 0; i6 < 5; ++i6) {
                                    MoneyInfo _money = new MoneyInfo();
                                    _money.id = MainActivity.google_productIds[i6];
                                    _money.info = MainActivity.google_listGems[i6];
                                    _money.smsContent = MainActivity.google_price[i6];
                                    avs.addElement(_money);
                                }
                                if (GameMidlet.versioncode < 11 && GameMidlet.versionByte >= 240) {
                                    MoneyInfo _money2 = new MoneyInfo();
                                    _money2.id = "napWeb";
                                    _money2.info = msg.reader().readUTF();
                                    _money2.smsContent = "";
                                    MoneyScrIOS.url_Nap = msg.reader().readUTF();
                                    avs.addElement(_money2);
                                }
                                this.gameLogicHandler.onMoneyInfo(avs);
                            } else if (GameMidlet.versionByte >= 240) {
                                MoneyInfo _money2 = new MoneyInfo();
                                _money2.id = "napWeb";
                                _money2.info = msg.reader().readUTF();
                                _money2.smsContent = "";
                                MoneyScr.url_Nap = msg.reader().readUTF();
                                avs.addElement(_money2);
                                this.gameLogicHandler.onMoneyInfo(avs);
                            } else {
                                byte actionC2 = msg.reader().readByte();
                                if (actionC2 == 0) {
                                    while (msg.reader().available() > 0) {
                                        MoneyInfo info3 = new MoneyInfo();
                                        info3.id = msg.reader().readUTF();
                                        info3.info = msg.reader().readUTF();
                                        info3.smsContent = msg.reader().readUTF();
                                        avs.addElement(info3);
                                    }
                                    this.gameLogicHandler.onMoneyInfo(avs);
                                }
                                if (actionC2 == 2) {
                                    String moneyInfo = msg.reader().readUTF();
                                    String moneySms = msg.reader().readUTF();
                                    String strinInfo = msg.reader().readUTF();
                                    this.gameLogicHandler.onChargeMoneySms(moneyInfo, moneySms, strinInfo);
                                }
                            }
                        } catch (Exception ex2) {
                        }
                        break;
                    }
                    case 45: {
                        this.gameLogicHandler.onServerMessage(msg.reader().readUTF());
                        break;
                    }
                    case 46: {
                        this.gameLogicHandler.onServerInfo(msg.reader().readUTF());
                        break;
                    }
                    case 48: {
                        try {
                            this.gameLogicHandler.onVersion(msg.reader().readUTF(), msg.reader().readUTF());
                        } catch (Exception ex3) {
                        }
                        break;
                    }
                    case 47: {
                        this.gameLogicHandler.onAdminCommandResponse(msg.reader().readUTF());
                        break;
                    }
                    case 52: {
                        int whoBonusId = msg.reader().readInt();
                        int moneyBonus = msg.reader().readInt();
                        int newMoney = msg.reader().readInt();
                        if (whoBonusId == TerrainMidlet.myInfo.IDDB) {
                            TerrainMidlet.myInfo.xu = newMoney;
                        }
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            CCanvas.gameScr.activeMoney2Fly(moneyBonus, whoBonusId);
                            break;
                        }
                        break;
                    }
                    case 10: {
                        String error = msg.reader().readUTF();
                        this.gameLogicHandler.onSetMoneyError(error);
                        break;
                    }
                    case 20: {
                        CCanvas.isInGameRunTime = true;
                        CCanvas.prepareScr.bossInfos.removeAllElements();
                        short[] equipID = new short[5];
                        if (GameScr.trainingMode) {
                            for (int i7 = 0; i7 < 5; ++i7) {
                                equipID[i7] = msg.reader().readShort();
                            }
                        }
                        byte mapID = msg.reader().readByte();
                        byte time = msg.reader().readByte();
                        int team = msg.reader().readUnsignedShort();
                        byte playerLent = 0;
                        if (PrepareScr.currLevel == 7) {
                            playerLent = msg.reader().readByte();
                        } else {
                            playerLent = 8;
                        }
                        short[] playerX = new short[playerLent];
                        short[] playerY = new short[playerLent];
                        short[] maxHP = new short[playerLent];
                        for (int i8 = 0; i8 < playerLent; ++i8) {
                            playerX[i8] = msg.reader().readShort();
                            if (playerX[i8] != -1) {
                                playerY[i8] = msg.reader().readShort();
                                maxHP[i8] = msg.reader().readShort();
                            } else {
                                playerY[i8] = -1;
                            }
                        }
                        if (CCanvas.gameScr == null) {
                            CCanvas.gameScr = new GameScr();
                        }
                        if (!GameScr.trainingMode) {
                            CCanvas.gameScr.initGame(mapID, time, playerX, playerY, maxHP, team);
                            CCanvas.gameScr.show(CCanvas.prepareScr);
                        } else {
                            CCanvas.menuScr.doTraining(mapID, time, playerX, playerY, maxHP, equipID);
                        }
                        if (CCanvas.prepareScr.itemCur[4] >= 0) {
                            Item i43;
                            Item i9 = i43 = ShopItem.getI(12);
                            --i43.num;
                        }
                        if (CCanvas.prepareScr.itemCur[5] >= 0) {
                            Item i44;
                            Item i10 = i44 = ShopItem.getI(13);
                            --i44.num;
                        }
                        if (CCanvas.prepareScr.itemCur[6] >= 0) {
                            Item i45;
                            Item i11 = i45 = ShopItem.getI(14);
                            --i45.num;
                        }
                        if (CCanvas.prepareScr.itemCur[7] >= 0) {
                            Item i46;
                            Item i12 = i46 = ShopItem.getI(15);
                            --i46.num;
                        }
                        CCanvas.endDlg();
                        CCanvas.menu.showMenu = false;
                        BM.removeTornado();
                        CScreen.isSetClip = false;
                        MessageHandler.nextTurnFlag = true;
                        break;
                    }
                    case 93: {
                        byte whoFly = msg.reader().readByte();
                        short xF = msg.reader().readShort();
                        short yF = msg.reader().readShort();
                        GameScr.pm.flyTo(whoFly, xF, yF);
                        GameScr.cam.setPlayerMode(whoFly);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                            break;
                        }
                        break;
                    }
                    case 21: {
                        byte whoMove = msg.reader().readByte();
                        short x = msg.reader().readShort();
                        short y = msg.reader().readShort();
                        CRes.out("=========================> rec move = " + x + "_" + y);
                        PM.p[whoMove].xToNow = x;
                        PM.p[whoMove].yToNow = y;
                        if (PM.p[whoMove].x == x && PM.p[whoMove].y == y) {
                            break;
                        }
                        GameScr.pm.movePlayer(whoMove, x, y);
                        if (whoMove != GameScr.myIndex && !PM.p[whoMove].isRunSpeed) {
                            Session_ME.receiveSynchronized = 1;
                            break;
                        }
                        break;
                    }
                    case 53: {
                        byte whoUpdateXY = msg.reader().readByte();
                        short xUpdate = msg.reader().readShort();
                        short yUpdate = msg.reader().readShort();
                        GameScr.pm.updatePlayerXY(whoUpdateXY, xUpdate, yUpdate);
                        PM.p[whoUpdateXY].bulletType = -1;
                        break;
                    }
                    case 22:
                    case 84: {
                        byte typeShoot = msg.reader().readByte();
                        byte critical = msg.reader().readByte();
                        byte whoFire = msg.reader().readByte();
                        byte type = msg.reader().readByte();
                        short xS = msg.reader().readShort();
                        short yS = msg.reader().readShort();
                        short angle = msg.reader().readShort();
                        byte force_2 = 0;
                        if (type == 17 || type == 19) {
                            force_2 = msg.reader().readByte();
                        }
                        if (type == 14 || type == 40) {
                            BM.angle = msg.reader().readByte();
                            BM.force = msg.reader().readByte();
                        }
                        if (type == 44 || type == 45 || type == 47) {
                            BM.angle = msg.reader().readByte();
                            CRes.out("ANGLE= " + BM.angle);
                        }
                        byte nShoot = msg.reader().readByte();
                        byte nBullet = (byte) (BM.nOrbit = msg.reader().readByte());
                        short[][] xArr = new short[nBullet][];
                        short[][] yArr = new short[nBullet][];
                        short[][] xHitArr = new short[nBullet][];
                        short[][] yHitArr = new short[nBullet][];
                        for (int k2 = 0; k2 < nBullet; ++k2) {
                            short lenght = msg.reader().readShort();
                            short[] xPaintLast = new short[lenght];
                            short[] yPaintLast = new short[lenght];
                            short[] xPaint = new short[lenght];
                            short[] yPaint = new short[lenght];
                            if (typeShoot == 0) {
                                for (int i13 = 0; i13 < lenght; ++i13) {
                                    if (i13 == 0) {
                                        xPaintLast[i13] = msg.reader().readShort();
                                        yPaintLast[i13] = msg.reader().readShort();
                                        xPaint[i13] = xPaintLast[i13];
                                        yPaint[i13] = yPaintLast[i13];
                                    } else {
                                        if (i13 == lenght - 1 && type == 49) {
                                            try {
                                                xPaint[i13] = msg.reader().readShort();
                                                yPaint[i13] = msg.reader().readShort();
                                                if (type == 49) {
                                                    Bullet.dXLaser = msg.reader().readByte();
                                                    Bullet.dYLaser = msg.reader().readByte();
                                                    if (Bullet.dXLaser != 0) {
                                                        while (Math.abs(Bullet.dXLaser) < 15) {
                                                            Bullet.dXLaser += Bullet.dXLaser;
                                                            Bullet.dYLaser += Bullet.dYLaser;
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                CRes.out("error");
                                            }
                                            break;
                                        }
                                        xPaintLast[i13] = msg.reader().readByte();
                                        yPaintLast[i13] = msg.reader().readByte();
                                        xPaint[i13] = (short) (xPaint[i13 - 1] + xPaintLast[i13]);
                                        yPaint[i13] = (short) (yPaint[i13 - 1] + yPaintLast[i13]);
                                    }
                                }
                            }
                            if (typeShoot == 1) {
                                for (int i13 = 0; i13 < lenght; ++i13) {
                                    xPaint[i13] = msg.reader().readShort();
                                    yPaint[i13] = msg.reader().readShort();
                                }
                            }
                            xArr[k2] = xPaint;
                            yArr[k2] = yPaint;
                            if (type == 48) {
                                byte sizeHit = msg.reader().readByte();
                                short[] xHit = new short[sizeHit];
                                short[] yHit = new short[sizeHit];
                                for (int i14 = 0; i14 < sizeHit; ++i14) {
                                    xHit[i14] = msg.reader().readShort();
                                    yHit[i14] = msg.reader().readShort();
                                }
                                xHitArr[k2] = xHit;
                                yHitArr[k2] = yHit;
                            }
                        }
                        byte typeSuper = msg.reader().readByte();
                        int xSuper = -1;
                        int ySuper = -1;
                        if (typeSuper == 1 || typeSuper == 2) {
                            xSuper = msg.reader().readShort();
                            ySuper = msg.reader().readShort();
                        } else if (typeSuper != 3) {
                        }
                        PM.p[whoFire].shoot(critical, whoFire, xS, yS, type, xArr, yArr, nShoot, force_2, angle,
                                xHitArr, yHitArr, xSuper, ySuper);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                            break;
                        }
                        break;
                    }
                    case 51: {
                        byte whoUpdateHP = msg.reader().readByte();
                        int nextHP = msg.reader().readUnsignedShort();
                        byte pixel = msg.reader().readByte();
                        if (PrepareScr.currLevel != 7) {
                            if (PM.p[whoUpdateHP] != null) {
                                PM.p[whoUpdateHP].updateHP(nextHP, pixel);
                                break;
                            }
                        } else {
                            if (PM.findPlayerByIndex(whoUpdateHP) != null) {
                                PM.findPlayerByIndex(whoUpdateHP).updateHP(nextHP, pixel);
                                break;
                            }
                        }
                        break;
                    }
                    case 83: {
                        CCanvas.menuScr.show();
                        if (CCanvas.gameScr != null) {
                            CCanvas.gameScr.onClearMap();
                            CCanvas.gameScr = null;
                        }
                        CScreen.isSetClip = true;
                        Session_ME.receiveSynchronized = 0;
                        break;
                    }
                    case 24: {
                        byte whoNext = msg.reader().readByte();
                        GameScr.pm.setNextPlayer(whoNext);
                        GameScr.bm.nBull = 0;
                        if (MessageHandler.nextTurnFlag) {
                            MessageHandler.nextTurnFlag = false;
                            CCanvas.endDlg();
                            break;
                        }
                        break;
                    }
                    case 25: {
                        byte windx = msg.reader().readByte();
                        byte windy = msg.reader().readByte();
                        GameScr.changeWind(windx, windy);
                        break;
                    }
                    case 26: {
                        byte whoUse = msg.reader().readByte();
                        byte item = msg.reader().readByte();
                        CRes.err("=======> USED ITEM = " + item);
                        PM.p[whoUse].UseItem(item, true, 0);
                        break;
                    }
                    case 69: {
                        CRes.out("=========> Cmd_Server2Client.CHOOSE_GUN: ");
                        int userID1 = msg.reader().readInt();
                        byte itemGun = msg.reader().readByte();
                        if (userID1 == TerrainMidlet.myInfo.IDDB) {
                            TerrainMidlet.myInfo.gun = itemGun;
                        }
                        if (CCanvas.curScr == CCanvas.changePScr) {
                            CCanvas.changePScr.onChangeGun();
                        }
                        CCanvas.endDlg();
                        break;
                    }
                    case 71: {
                        int userID2 = msg.reader().readInt();
                        byte position = msg.reader().readByte();
                        this.gameLogicHandler.onChangeTeam(userID2, position);
                        break;
                    }
                    case 50: {
                        byte whoWin = msg.reader().readByte();
                        byte exBonus = msg.reader().readByte();
                        int moneyBonus2 = msg.reader().readInt();
                        CCanvas.gameScr.setWin(whoWin, exBonus, moneyBonus2);
                        CCanvas.prepareScr.resetReady();
                        CCanvas.prepareScr.readyDelay = 5;
                        Session_ME.receiveSynchronized = 0;
                        break;
                    }
                    case 34: {
                        int id = msg.reader().readInt();
                        if (id == -1) {
                            CCanvas.startOKDlg(Language.cantsee());
                            break;
                        }
                        String username1 = msg.reader().readUTF();
                        int money3 = msg.reader().readInt();
                        byte level = msg.reader().readByte();
                        byte levelPercen = msg.reader().readByte();
                        int money4 = msg.reader().readInt();
                        int xp = msg.reader().readInt();
                        int nextXp = msg.reader().readInt();
                        int cup = msg.reader().readInt();
                        PlayerInfo tem = new PlayerInfo();
                        CCanvas.archScreen.level = level;
                        CCanvas.archScreen.levelPercen = levelPercen;
                        CCanvas.archScreen.xu = money3;
                        CCanvas.archScreen.luong = money4;
                        CCanvas.archScreen.exp = xp;
                        CCanvas.archScreen.nextExp = nextXp;
                        CCanvas.archScreen.cup = cup;
                        CCanvas.archScreen.rank = msg.reader().readUTF();
                        if (CCanvas.iconMn.isExist(TerrainMidlet.myInfo.clanID)) {
                            CCanvas.archScreen.imgClan = new mImage(
                                    CCanvas.iconMn.getImage(TerrainMidlet.myInfo.clanID));
                        } else {
                            GameService.gI().getClanIcon(TerrainMidlet.myInfo.clanID);
                        }
                        String result = String.valueOf(Language.name()) + ": " + username1 + ". "
                                + Language.money() + ": " + money3 + Language.xu() + "-" + money4
                                + Language.luong() + ". Level:" + level + "+" + levelPercen + "%";
                        if (CCanvas.curScr == CCanvas.prepareScr) {
                            CCanvas.startOKDlg(result);
                            break;
                        }
                        CCanvas.endDlg();
                        CCanvas.archScreen.show();
                        break;
                    }
                    case 72: {
                        byte nAterBuy = msg.reader().readByte();
                        byte[] idAfterBuy = new byte[nAterBuy];
                        byte[] nAfterBuy = new byte[nAterBuy];
                        for (int i15 = 0; i15 < nAterBuy; ++i15) {
                            idAfterBuy[i15] = msg.reader().readByte();
                            nAfterBuy[i15] = msg.reader().readByte();
                        }
                        int monneyAfterBuy = msg.reader().readInt();
                        int moneyAfterBuy2 = msg.reader().readInt();
                        ShopItem.receiveAItemBuy(nAterBuy, idAfterBuy, nAfterBuy, monneyAfterBuy,
                                moneyAfterBuy2);
                        break;
                    }
                    case 74: {
                        byte gunIDAfter = msg.reader().readByte();
                        ChangePlayerCSr.isUnlock[gunIDAfter + ChangePlayerCSr.gunPassiveIndexSub] = 1;
                        CCanvas.changePScr.doChangePlayer();
                        CCanvas.endDlg();
                        break;
                    }
                    case 42: {
                        GameMidlet.timePingPaint = (int) ((mSystem.currentTimeMillis()
                                - MessageHandler.timePing) / 2L);
                        GameMidlet.ping = true;
                        CCanvas.isReconnect = false;
                        break;
                    }
                    case 63: {
                        String textSMS = msg.reader().readUTF();
                        final String dataSMS = msg.reader().readUTF();
                        final String toSMS = msg.reader().readUTF();
                        CRes.out(dataSMS);
                        CRes.out("sms://" + toSMS);
                        CCanvas.startYesNoDlg(textSMS, new IAction() {
                            @Override
                            public void perform() {
                                TerrainMidlet.sendSMS(dataSMS, "sms://" + toSMS, new IAction() {
                                    @Override
                                    public void perform() {
                                        CCanvas.startOKDlg(Language.sendSuccess());
                                    }
                                }, new IAction() {
                                    @Override
                                    public void perform() {
                                        CCanvas.startOKDlg(Language.sendFail());
                                    }
                                });
                            }
                        }, new IAction() {
                            @Override
                            public void perform() {
                                CCanvas.endDlg();
                            }
                        });
                        break;
                    }
                    case 75: {
                        byte mID = msg.reader().readByte();
                        if (CCanvas.curScr != CCanvas.luckyGifrScreen) {
                            CCanvas.curScr = CCanvas.prepareScr;
                            CCanvas.prepareScr.resetReady();
                            CCanvas.prepareScr.show();
                            PrepareScr.curMap = mID;
                            if (mID != 27 && mID != 100) {
                                try {
                                    CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 3);
                                    if (MM.maps != null) {
                                        MM.maps.removeAllElements();
                                    }
                                    GameScr.mm.createMap(mID);
                                    CCanvas.endDlg();
                                } catch (Exception ex) {
                                    CCanvas.endDlg();
                                }
                            }
                            if (!CRes.isNullOrEmpty(GameScr.res)) {
                                GameService.gI().luckGift((byte) (-3));
                            }
                            System.gc();
                            break;
                        }
                        PrepareScr.curMap = mID;
                        if (mID != 27 && mID != 100) {
                            try {
                                CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 3);
                                if (MM.maps != null) {
                                    MM.maps.removeAllElements();
                                }
                                GameScr.mm.createMap(mID);
                                CCanvas.endDlg();
                            } catch (Exception ex) {
                                CCanvas.endDlg();
                            }
                        }
                        if (!CRes.isNullOrEmpty(GameScr.res)) {
                            GameService.gI().luckGift((byte) (-3));
                            break;
                        }
                        break;
                    }
                    case 64: {
                        byte len1 = msg.reader().readByte();
                        byte[] b1 = new byte[len1];
                        msg.reader().read(b1, 0, len1);
                        byte len2 = msg.reader().readByte();
                        short[] b2 = new short[len2];
                        for (int i16 = 0; i16 < len2; ++i16) {
                            b2[i16] = msg.reader().readShort();
                        }
                        byte len3 = msg.reader().readByte();
                        byte[] b3 = new byte[len3];
                        msg.reader().read(b3, 0, len3);
                        byte len4 = msg.reader().readByte();
                        byte[] b4 = new byte[len4];
                        msg.reader().read(b4, 0, len4);
                        PM.MAX_PLAYER = msg.reader().readByte();
                        byte lenMapID = msg.reader().readByte();
                        PrepareScr.mapBossID = new byte[lenMapID];
                        for (int i17 = 0; i17 < lenMapID; ++i17) {
                            PrepareScr.mapBossID[i17] = msg.reader().readByte();
                        }
                        PrepareScr.bossID = new byte[lenMapID];
                        for (int i17 = 0; i17 < lenMapID; ++i17) {
                            PrepareScr.bossID[i17] = msg.reader().readByte();
                        }
                        PM.NUMB_PLAYER = msg.reader().readByte();
                        Bullet.BULLset_WIND_AFFECT = b1;
                        for (int i17 = 0; i17 < b2.length; ++i17) {
                            CRes.out(String.valueOf(this.getClass().getName()) + " debug: " + b2[i17]);
                        }
                        CPlayer.angleLock = b2;
                        CPlayer.angleLockMain = b2;
                        ChangePlayerCSr.power = b3;
                        ChangePlayerCSr.number = b4;
                        CCanvas.changePScr = new ChangePlayerCSr();
                        CCanvas.roomListScr2 = new RoomListScr2();
                        break;
                    }
                    case 76: {
                        byte currRoom = PrepareScr.currentRoom = msg.reader().readByte();
                        byte boardID = msg.reader().readByte();
                        String boardName = msg.reader().readUTF();
                        byte roomLevel = PrepareScr.currLevel = msg.reader().readByte();
                        BoardListScr.setBoardName(boardID, boardName);
                        break;
                    }
                    case 78: {
                        boolean isList = msg.reader().readBoolean();
                        if (isList) {
                            byte usSize = msg.reader().readByte();
                            Vector<PlayerInfo> findPlayer = new Vector<>();
                            for (int i18 = 0; i18 < usSize; ++i18) {
                                PlayerInfo info4 = new PlayerInfo();
                                info4.name = msg.reader().readUTF();
                                info4.IDDB = msg.reader().readInt();
                                info4.gun = msg.reader().readByte();
                                info4.xu = msg.reader().readInt();
                                info4.level2 = msg.reader().readUnsignedByte();
                                info4.level2Percen = msg.reader().readUnsignedByte();
                                for (int a2 = 0; a2 < 5; ++a2) {
                                    info4.equipID[info4.gun][a2] = msg.reader().readShort();
                                }
                                info4.getQuanHam();
                                info4.getMyEquip(4);
                                info4.isReady = true;
                                findPlayer.addElement(info4);
                            }
                            CCanvas.endDlg();
                            GameLogicHandler.gI().onInviteList(findPlayer);
                            break;
                        }
                        CRes.out("Someone invite ");
                        String info5 = msg.reader().readUTF();
                        final byte roomInvite = msg.reader().readByte();
                        final byte boardInvite = msg.reader().readByte();
                        final String boardNameInvite = msg.reader().readUTF();
                        CCanvas.startYesNoDlg(info5, new IAction() {
                            @Override
                            public void perform() {
                                PrepareScr.currentRoom = roomInvite;
                                BoardListScr.setBoardName(boardInvite, boardNameInvite);
                                GameService.gI().joinBoard(roomInvite, boardInvite, "");
                            }
                        }, new IAction() {
                            @Override
                            public void perform() {
                                CCanvas.endDlg();
                            }
                        });
                        break;
                    }
                    case 94: {
                        GameService.gI().changeItem(CCanvas.prepareScr.itemCur);
                        break;
                    }
                    case 86: {
                        String A = msg.reader().readUTF();
                        String B = msg.reader().readUTF();
                        byte C = msg.reader().readByte();
                        this.gameLogicHandler.onURL(A, B, C);
                        break;
                    }
                    case 87: {
                        String myName = msg.reader().readUTF();
                        TerrainMidlet.myInfo.name = myName;
                        break;
                    }
                    case 88: {
                        byte lenght2 = msg.reader().readByte();
                        RoomListScr2.roomLevelText = new String[lenght2];
                        for (int i19 = 0; i19 < lenght2; ++i19) {
                            String vn = msg.reader().readUTF();
                            String eng = msg.reader().readUTF();
                            RoomListScr2.roomLevelText[i19] = ((Language.language == 0) ? vn : eng);
                        }
                        break;
                    }
                    case 89: {
                        byte lenBoss = msg.reader().readByte();
                        short[] bX = new short[lenBoss];
                        short[] bY = new short[lenBoss];
                        int gun = -1;
                        for (int i20 = 0; i20 < lenBoss; ++i20) {
                            PlayerInfo playerInfo2 = new PlayerInfo();
                            playerInfo2.IDDB = msg.reader().readInt();
                            playerInfo2.name = msg.reader().readUTF();
                            playerInfo2.maxHP = msg.reader().readInt();
                            playerInfo2.gun = msg.reader().readByte();
                            gun = playerInfo2.gun;
                            playerInfo2.isBoss = true;
                            bX[i20] = msg.reader().readShort();
                            bY[i20] = msg.reader().readShort();
                            if (PrepareScr.currLevel == 7) {
                                playerInfo2.index = msg.reader().readByte();
                            }
                            CCanvas.prepareScr.bossInfos.addElement(playerInfo2);
                        }
                        GameScr.pm.initBoss(bX, bY);
                        if (gun == 23 || gun == 24) {
                            Session_ME.receiveSynchronized = 1;
                            break;
                        }
                        break;
                    }
                    case 92: {
                        short lentTile = msg.reader().readShort();
                        MM.undestroyTile = new short[lentTile];
                        for (int i21 = 0; i21 < lentTile; ++i21) {
                            MM.undestroyTile[i21] = msg.reader().readShort();
                        }
                        break;
                    }
                    case 90: {
                        byte fileType = msg.reader().readByte();
                        switch (fileType) {
                            case 1:
                                byte fileVersion2 = msg.reader().readByte();
                                if (fileVersion2 != CCanvas.mapIconVersion) {
                                    int fileLenght2 = msg.reader().readUnsignedShort();
                                    PrepareScr.fileData = new byte[fileLenght2];
                                    msg.reader().read(PrepareScr.fileData, 0, fileLenght2);
                                    PrepareScr.init();
                                    CCanvas.saveVersion("iconversion2", fileVersion2);
                                    CCanvas.saveData("icondata2", PrepareScr.fileData);
                                }
                                LoginScr.currTime = 45;
                                LoginScr.maxTime = 60;
                                GameService.gI().sendVersion((byte) 3, CCanvas.mapIconVersion);
                                break;

                            case 2:
                                LoginScr.isWait = true;
                                CRes.out(
                                        "====================================================> NHAN FILE PACK 3");
                                byte fileVersion3 = msg.reader().readByte();
                                if (fileVersion3 != CCanvas.mapValuesVersion) {
                                    CRes.err(String.valueOf(this.getClass().getName())
                                            + " cmd:90 load SUB_FILEPACK_3  ");
                                    int fileLenght3 = msg.reader().readUnsignedShort();
                                    byte[] fileData3 = new byte[fileLenght3];
                                    msg.reader().read(fileData3, 0, fileLenght3);
                                    CCanvas.readMess(fileData3, (byte) 0);
                                    CCanvas.saveData("valuesdata2", fileData3);
                                    CCanvas.saveVersion("valuesversion2", fileVersion3);
                                    fileData3 = null;
                                } else {
                                    CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 5);
                                    LoginScr.isWait = false;
                                }
                                LoginScr.currTime = 15;
                                LoginScr.maxTime = 30;
                                GameService.gI().sendVersion((byte) 1, CCanvas.tileMapVersion);
                                CRes.err("MessageHandler ================> SUB_FILEPACK_3 Load Done");
                                break;

                            case 3:
                                CRes.out(
                                        "====================================================> NHAN FILE PACK 4");
                                byte fileVersion4 = msg.reader().readByte();
                                if (fileVersion4 != CCanvas.playerVersion) {
                                    int fileLenght4 = msg.reader().readUnsignedShort();
                                    CPlayer.fileData = new byte[fileLenght4];
                                    CRes.err("SUB_FILEPACK_4 ======================> playerData fileLenght4 = "
                                            + fileLenght4);
                                    msg.reader().read(CPlayer.fileData, 0, fileLenght4);
                                    CPlayer.init();
                                    CCanvas.saveVersion("playerVersion2", fileVersion4);
                                    CCanvas.saveData("playerdata2", CPlayer.fileData);
                                }
                                LoginScr.currTime = 60;
                                LoginScr.maxTime = 75;
                                GameService.gI().sendVersion((byte) 4, CCanvas.equipVersion);
                                CRes.err(
                                        "MessageHandler ================> SUB_FILEPACK_4 Load Done PlayerData");
                                break;

                            case 4:
                                CRes.out(
                                        "====================================================> NHAN FILE PACK 5");
                                byte fileVersion5 = msg.reader().readByte();
                                if (fileVersion5 != CCanvas.equipVersion) {
                                    int fileLenght5 = msg.reader().readInt();
                                    byte[] fileData4 = new byte[fileLenght5];
                                    msg.reader().read(fileData4, 0, fileLenght5);
                                    CCanvas.readMess(fileData4, (byte) 1);
                                    CCanvas.saveVersion("equipVersion2", fileVersion5);
                                    CCanvas.saveData("equipdata2", fileData4);
                                    fileData4 = null;
                                }
                                LoginScr.currTime = 75;
                                LoginScr.maxTime = 90;
                                GameService.gI().sendVersion((byte) 5, CCanvas.equipVersion);
                                CRes.err("MessageHandler ================> SUB_FILEPACK_5 Load Done Equipment");
                                break;

                            case 5:
                                CRes.out(
                                        "====================================================> NHAN FILE PACK 6");
                                byte fileVersion6 = msg.reader().readByte();
                                if (fileVersion6 != CCanvas.levelCVersion) {
                                    int fileLenght6 = msg.reader().readUnsignedShort();
                                    byte[] fileData5 = new byte[fileLenght6];
                                    msg.reader().read(fileData5, 0, fileLenght6);
                                    CCanvas.readMess(fileData5, (byte) 2);
                                    CCanvas.saveVersion("levelCVersion2", fileVersion6);
                                    CCanvas.saveData("levelCData2", fileData5);
                                    for (int i22 = 0; i22 < CCanvas.nBigImage; ++i22) {
                                        GameService.gI().getBigImage((byte) i22);
                                    }
                                    fileData5 = null;
                                } else {
                                    boolean isLoadRawFata = false;
                                    for (int i23 = 0; i23 < CCanvas.nBigImage; ++i23) {
                                        byte[] dataBig = CCanvas.loadData("bigImage" + i23);
                                        if (dataBig == null) {
                                            isLoadRawFata = false;
                                            break;
                                        }
                                        PlayerEquip.imgData[i23] = mImage.createImage(dataBig, 0,
                                                dataBig.length, "bigImage" + i23);
                                        isLoadRawFata = true;
                                        dataBig = null;
                                    }
                                    if (isLoadRawFata) {
                                        GameService.gI().sendVersion((byte) 6, (byte) 0);
                                        this.gameLogicHandler.onLoginSuccess();
                                    } else {
                                        for (int i23 = 0; i23 < CCanvas.nBigImage; ++i23) {
                                            RMS.clearRMS("bigImage" + i23);
                                        }
                                        int fileLenght7 = msg.reader().readUnsignedShort();
                                        byte[] fileData6 = new byte[fileLenght7];
                                        msg.reader().read(fileData6, 0, fileLenght7);
                                        CCanvas.readMess(fileData6, (byte) 2);
                                        CCanvas.saveVersion("levelCVersion2", fileVersion6);
                                        CCanvas.saveData("levelCData2", fileData6);
                                        for (int i24 = 0; i24 < CCanvas.nBigImage; ++i24) {
                                            GameService.gI().getBigImage((byte) i24);
                                        }
                                        fileData6 = null;
                                    }
                                }
                                CRes.err("MessageHandler ================> SUB_FILEPACK_6 Load Done");
                                break;
                        }
                        break;
                    }
                    case 95: {
                        CRes.out("CAPTURE");
                        byte whoCapture = msg.reader().readByte();
                        byte whoCaptured = msg.reader().readByte();
                        PM.p[whoCapture].capture(whoCaptured);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                            break;
                        }
                        break;
                    }
                    case 96: {
                        byte whoBit = msg.reader().readByte();
                        byte whoBited = msg.reader().readByte();
                        PM.p[whoBited].isPoison = true;
                        PM.p[whoBited].poisonEff = true;
                        PM.p[whoBit].sLook = 3;
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                            break;
                        }
                        break;
                    }
                    case 97: {
                        int expAdd = msg.reader().readInt();
                        TerrainMidlet.myInfo.exp = msg.reader().readInt();
                        TerrainMidlet.myInfo.nextExp = msg.reader().readInt();
                        CRes.out("expAdd= " + expAdd);
                        byte levelUp = msg.reader().readByte();
                        if (levelUp == 0) {
                            TerrainMidlet.myInfo.level2Percen = msg.reader().readByte();
                            CRes.out("percen 0 = " + TerrainMidlet.myInfo.level2Percen);
                        }
                        if (CCanvas.curScr == CCanvas.gameScr && PM.p[GameScr.myIndex] != null) {
                            PM.p[GameScr.myIndex].updateExp(expAdd);
                        }
                        if (levelUp == 1) {
                            int currLevel = msg.reader().readUnsignedByte();
                            byte percenLevel = msg.reader().readByte();
                            short currPoint = msg.reader().readShort();
                            TerrainMidlet.myInfo.point = currPoint;
                            CRes.out("currLevel= " + currLevel + " currPoint= " + currPoint);
                            TerrainMidlet.myInfo.level2 = currLevel;
                            TerrainMidlet.myInfo.level2Percen = percenLevel;
                            CRes.out("percen 1 = " + TerrainMidlet.myInfo.level2Percen);
                            break;
                        }
                        break;
                    }
                    case 99: {
                        TerrainMidlet.myInfo.level2 = msg.reader().readUnsignedByte();
                        TerrainMidlet.myInfo.level2Percen = msg.reader().readByte();
                        TerrainMidlet.myInfo.getQuanHam();
                        CRes.out("level=" + TerrainMidlet.myInfo.lvl);
                        TerrainMidlet.myInfo.point = msg.reader().readShort();
                        for (int i25 = 0; i25 < 5; ++i25) {
                            TerrainMidlet.myInfo.ability[i25] = msg.reader().readShort();
                            CRes.out("my ability= " + TerrainMidlet.myInfo.ability[i25]);
                        }
                        TerrainMidlet.myInfo.exp = msg.reader().readInt();
                        TerrainMidlet.myInfo.nextExp = msg.reader().readInt();
                        TerrainMidlet.myInfo.cup = msg.reader().readInt();
                        TerrainMidlet.myInfo.getAttribute();
                        if (MenuScr.viewInfo) {
                            MenuScr.viewInfo = false;
                            CCanvas.endDlg();
                            if (CCanvas.levelScreen == null) {
                                CCanvas.levelScreen = new LevelScreen();
                            }
                            CCanvas.levelScreen.show(CCanvas.menuScr);
                            break;
                        }
                        break;
                    }
                    case 100: {
                        byte whoLuck = msg.reader().readByte();
                        PM.p[whoLuck].lucky();
                        Session_ME.receiveSynchronized = 1;
                        break;
                    }
                    case 104: {
                        byte action = msg.reader().readByte();
                        if (action == 0) {
                            int dbKey = msg.reader().readInt();
                            byte gl = msg.reader().readByte();
                            byte tp = msg.reader().readByte();
                            short idb = msg.reader().readShort();
                            String n = msg.reader().readUTF();
                            byte li = msg.reader().readByte();
                            byte[] ab = new byte[li];
                            for (int a3 = 0; a3 < li; ++a3) {
                                ab[a3] = msg.reader().readByte();
                            }
                            byte dte = msg.reader().readByte();
                            byte vip = msg.reader().readByte();
                            int level2 = msg.reader().readUnsignedByte();
                            Equip equip = PlayerEquip.getEquip(gl, tp, idb);
                            equip.getInvAtribute(ab);
                            Equip tam = null;
                            if (equip != null) {
                                tam = new Equip();
                                tam.id = equip.id;
                                tam.type = equip.type;
                                tam.icon = equip.icon;
                                tam.glass = equip.glass;
                                tam.x = equip.x;
                                tam.y = equip.y;
                                tam.w = equip.w;
                                tam.h = equip.h;
                                tam.dx = equip.dx;
                                tam.dy = equip.dy;
                                tam.date = dte;
                                tam.name = String.valueOf(n) + ((level2 != 0) ? (" " + level2) : "");
                                tam.dbKey = dbKey;
                                tam.level = equip.level;
                                tam.vip = vip;
                                tam.slot = 3;
                                for (int a4 = 0; a4 < 5; ++a4) {
                                    tam.inv_attAddPoint[a4] = equip.inv_attAddPoint[a4];
                                    tam.inv_ability[a4] = equip.inv_ability[a4];
                                    tam.inv_percen[a4] = equip.inv_percen[a4];
                                }
                                if (TerrainMidlet.myInfo.myEquip.equips[tam.type] != null
                                        && TerrainMidlet.myInfo.myEquip.equips[tam.type].id == tam.id) {
                                    TerrainMidlet.myInfo.myEquip.equips[tam.type].dbKey = dbKey;
                                }
                            }
                            CCanvas.equipScreen.addEquip(tam);
                        }
                        if (action == 1) {
                            String info6 = msg.reader().readUTF();
                            CCanvas.inventory.requestServer(info6);
                        }
                        if (action == 2) {
                            PlayerInfo p3 = TerrainMidlet.myInfo;
                            for (int i26 = 0; i26 < 10; ++i26) {
                                for (int j2 = 0; j2 < 5; ++j2) {
                                    p3.equipID[i26][j2] = msg.reader().readShort();
                                }
                            }
                            break;
                        }
                        break;
                    }
                    case 101: {
                        CRes.out("=====> Cmd_Server2Client.INVENTORY: 101");
                        Vector<Equip> inventory = new Vector<>();
                        CRes.out("=======>Cmd_Server2Client.INVENTORY: TerrainMidlet.myInfo == null "
                                + (TerrainMidlet.myInfo == null));
                        byte lenI = msg.reader().readByte();
                        short[] idI = new short[lenI];
                        String[] name = new String[lenI];
                        int[] dbKey2 = new int[lenI];
                        byte[] glassI = new byte[lenI];
                        byte[] typeI = new byte[lenI];
                        byte[] date = new byte[lenI];
                        byte[] slot = new byte[lenI];
                        byte[] vip2 = new byte[lenI];
                        int[] level3 = new int[lenI];
                        CRes.out("=======> Cmd_Server2Client.INVENTORY: lenI" + lenI);
                        for (int i27 = 0; i27 < lenI; ++i27) {
                            dbKey2[i27] = msg.reader().readInt();
                            glassI[i27] = msg.reader().readByte();
                            typeI[i27] = msg.reader().readByte();
                            idI[i27] = msg.reader().readShort();
                            name[i27] = msg.reader().readUTF();
                            byte nAbility = msg.reader().readByte();
                            byte[] currAb = new byte[nAbility];
                            for (int a5 = 0; a5 < nAbility; ++a5) {
                                currAb[a5] = msg.reader().readByte();
                            }
                            date[i27] = msg.reader().readByte();
                            slot[i27] = msg.reader().readByte();
                            vip2[i27] = msg.reader().readByte();
                            level3[i27] = msg.reader().readUnsignedByte();
                            Equip e2 = PlayerEquip.createEquip(glassI[i27], typeI[i27], idI[i27]);
                            e2.level2 = level3[i27];
                            e2.removeAbility();
                            e2.getInvAtribute(currAb);
                            Equip tam2 = new Equip();
                            if (e2 != null) {
                                e2.date = date[i27];
                                e2.name = name[i27];
                                tam2.id = e2.id;
                                tam2.type = e2.type;
                                tam2.frame = e2.frame;
                                tam2.x = e2.x;
                                tam2.y = e2.y;
                                tam2.w = e2.w;
                                tam2.h = e2.h;
                                tam2.dx = e2.dx;
                                tam2.dy = e2.dy;
                                tam2.icon = e2.icon;
                                tam2.type = e2.type;
                                tam2.glass = glassI[i27];
                                tam2.date = e2.date;
                                tam2.name = String.valueOf(e2.name)
                                        + ((e2.level2 != 0) ? (" " + e2.level2) : "");
                                tam2.dbKey = dbKey2[i27];
                                tam2.level = e2.level;
                                tam2.slot = slot[i27];
                                tam2.vip = vip2[i27];
                                TerrainMidlet.myInfo.getMyEquip(5);
                                tam2.removeAbility();
                                tam2.getInvAtribute(currAb);
                                inventory.addElement(tam2);
                            }
                        }
                        for (int i27 = 0; i27 < 5; ++i27) {
                            int myDbKey = msg.reader().readInt();
                            if (TerrainMidlet.myInfo.myEquip.equips[i27] != null) {
                                TerrainMidlet.myInfo.myEquip.equips[i27].dbKey = myDbKey;
                                TerrainMidlet.myInfo.dbKey[i27] = myDbKey;
                            }
                        }
                        CCanvas.equipScreen.getEquip(inventory);
                        CCanvas.endDlg();
                        break;
                    }
                    case 102: {
                        byte change = msg.reader().readByte();
                        if (change == 0) {
                            CCanvas.endDlg();
                            CCanvas.equipScreen.resetEquip();
                            CCanvas.menuScr.show();
                        }
                        if (change == 1) {
                            CCanvas.equipScreen.getLastEquip();
                            CCanvas.menuScr.show();
                            CCanvas.endDlg();
                        }
                        if (change != 2) {
                            break;
                        }
                        for (int i28 = 0; i28 < 10; ++i28) {
                            for (int j3 = 0; j3 < 5; ++j3) {
                                TerrainMidlet.myInfo.equipID[i28][j3] = msg.reader().readShort();
                                if (i28 == 0) {
                                    CRes.out("my equip= " + TerrainMidlet.myInfo.equipID[i28][j3]);
                                }
                            }
                        }
                        if (CCanvas.curScr != CCanvas.inventory) {
                            CCanvas.equipScreen.init();
                            CCanvas.equipScreen.show(CCanvas.menuScr);
                            break;
                        }
                        break;
                    }
                    case 103: {
                        Vector<Equip> items = new Vector<>();
                        short nE = msg.reader().readShort();
                        for (int i29 = 0; i29 < nE; ++i29) {
                            byte glassID = msg.reader().readByte();
                            byte typeE = msg.reader().readByte();
                            short idE = msg.reader().readShort();
                            String nameE = msg.reader().readUTF();
                            int xuE = msg.reader().readInt();
                            int luongE = msg.reader().readInt();
                            byte dateE = msg.reader().readByte();
                            byte levelE = msg.reader().readByte();
                            Equip eq = PlayerEquip.getEquip(glassID, typeE, idE);
                            if (eq != null) {
                                eq.date = dateE;
                                eq.name = nameE;
                                eq.xu = xuE;
                                eq.luong = luongE;
                                eq.level = levelE;
                                eq.glass = glassID;
                                eq.isSelect = false;
                                eq.index = i29;
                                items.addElement(eq);
                            }
                        }
                        if (CCanvas.shopEquipScr == null) {
                            CCanvas.shopEquipScr = new ShopEquipment();
                        }
                        CCanvas.shopEquipScr.setItems(items);
                        CCanvas.menuScr.doEquipItem();
                        CCanvas.endDlg();
                        break;
                    }
                    case 105: {
                        int xu = msg.reader().readInt();
                        int gold = msg.reader().readInt();
                        TerrainMidlet.myInfo.xu = xu;
                        TerrainMidlet.myInfo.luong = gold;
                        CRes.out("xu= " + xu + " luong= " + gold);
                        break;
                    }
                    case 106: {
                        byte typeSee = msg.reader().readByte();
                        byte whoCantSee = msg.reader().readByte();
                        CCanvas.gameScr.checkEyeSmoke(whoCantSee, typeSee);
                        break;
                    }
                    case 107: {
                        byte typeFreeze = msg.reader().readByte();
                        byte whoFreeze = msg.reader().readByte();
                        CCanvas.gameScr.checkFreeze(whoFreeze, typeFreeze);
                        break;
                    }
                    case 108: {
                        byte whoPoison = msg.reader().readByte();
                        CCanvas.gameScr.checkPostion(whoPoison);
                        break;
                    }
                    case 109: {
                        CRes.out("DAT BOM HEN GIO");
                        int typeBomb = msg.reader().readByte();
                        byte bombId = msg.reader().readByte();
                        if (typeBomb == 0) {
                            int xBomb = msg.reader().readInt();
                            int yBomb = msg.reader().readInt();
                            CRes.out("bomb x= " + xBomb + " bomb y= " + yBomb);
                            TimeBomb bomb = new TimeBomb(bombId, xBomb, yBomb);
                            CCanvas.gameScr.addTimeBomb(bomb);
                        }
                        if (typeBomb == 1) {
                            CRes.out("BOM EXPLORE id=" + bombId);
                            CCanvas.gameScr.explodeTimeBomb(bombId);
                            break;
                        }
                        break;
                    }
                    case 112: {
                        CRes.out("Tra ve 4 Slot");
                        ShopItem.getI(12).num = msg.reader().readByte();
                        ShopItem.getI(13).num = msg.reader().readByte();
                        ShopItem.getI(14).num = msg.reader().readByte();
                        ShopItem.getI(15).num = msg.reader().readByte();
                        break;
                    }
                    case 113: {
                        byte whoAngry = msg.reader().readByte();
                        byte angry = msg.reader().readByte();
                        CRes.out("angry= " + angry);
                        PM.p[whoAngry].updateAngry(angry);
                        break;
                    }
                    case 116: {
                        CCanvas.endDlg();
                        byte pageTop = msg.reader().readByte();
                        Vector<Clan> clans = new Vector<>();
                        while (msg.reader().available() > 0) {
                            Clan cl = new Clan();
                            cl.id = msg.reader().readShort();
                            cl.name = msg.reader().readUTF();
                            cl.count = (byte) msg.reader().readUnsignedByte();
                            cl.max = (byte) msg.reader().readUnsignedByte();
                            cl.master = msg.reader().readUTF();
                            cl.money = msg.reader().readInt();
                            cl.money2 = msg.reader().readInt();
                            cl.cup = msg.reader().readInt();
                            cl.level = msg.reader().readByte();
                            cl.percen = msg.reader().readByte();
                            cl.slogan = msg.reader().readUTF();
                            clans.addElement(cl);
                        }
                        if (clans.size() > 0) {
                            CCanvas.topClanScreen.show(CCanvas.curScr);
                            CCanvas.topClanScreen.getClanList(pageTop, clans);
                            break;
                        }
                        CCanvas.startOKDlg(Language.clanSize());
                        break;
                    }
                    case 117: {
                        CRes.out("Cmd_Server2Client.CLAN_INFO: ======> Clan Info");
                        CCanvas.endDlg();
                        Clan clan = new Clan();
                        clan.id = msg.reader().readShort();
                        clan.name = msg.reader().readUTF();
                        clan.count = (byte) msg.reader().readUnsignedByte();
                        clan.max = (byte) msg.reader().readUnsignedByte();
                        clan.master = msg.reader().readUTF();
                        clan.money = msg.reader().readInt();
                        clan.money2 = msg.reader().readInt();
                        clan.cup = msg.reader().readInt();
                        clan.exp = msg.reader().readInt();
                        clan.nextExp = msg.reader().readInt();
                        clan.level = msg.reader().readByte();
                        clan.percen = msg.reader().readByte();
                        clan.slogan = msg.reader().readUTF();
                        clan.date = msg.reader().readUTF();
                        byte itemL = msg.reader().readByte();
                        clan.item = new String[itemL];
                        clan.time = new int[itemL];
                        for (int i30 = 0; i30 < itemL; ++i30) {
                            clan.item[i30] = msg.reader().readUTF();
                            clan.time[i30] = msg.reader().readInt();
                        }
                        CCanvas.clanScreen.clan = clan;
                        CCanvas.clanScreen.show(CCanvas.curScr);
                        break;
                    }
                    case 115: {
                        short idIcon = msg.reader().readShort();
                        short lenImg = msg.reader().readShort();
                        byte[] iconData = new byte[lenImg];
                        CRes.out("======> Cmd_Server2Client.CLAN_ICON lenImg = " + lenImg);
                        mImage icon = null;
                        if (lenImg > 1) {
                            msg.reader().read(iconData, 0, lenImg);
                            icon = mImage.createImage(iconData, 0, lenImg, "");
                        } else {
                            icon = CRes.empty;
                        }
                        if (icon == null) {
                            icon = CRes.imgEr;
                        }
                        Clan cl2 = new Clan();
                        cl2.id = idIcon;
                        cl2.icon = icon;
                        if (!CCanvas.iconMn.isExist(cl2.id)) {
                            CCanvas.iconMn.addIcon(cl2);
                        }
                        GameLogicHandler.gI().onGetImage(idIcon, icon.image);
                        break;
                    }
                    case 118: {
                        byte pageMem = msg.reader().readByte();
                        Vector<PlayerInfo> clanMember = new Vector<>();
                        String clanName = msg.reader().readUTF();
                        while (msg.reader().available() > 0) {
                            PlayerInfo info7 = new PlayerInfo();
                            info7.IDDB = msg.reader().readInt();
                            info7.name = msg.reader().readUTF();
                            CRes.out("name= " + info7.name);
                            info7.xu = msg.reader().readInt();
                            info7.gun = msg.reader().readByte();
                            info7.isReady = (msg.reader().readByte() != 0);
                            info7.level2 = msg.reader().readUnsignedByte();
                            info7.level2Percen = msg.reader().readByte();
                            info7.STT = msg.reader().readUnsignedByte();
                            info7.cup = msg.reader().readInt();
                            info7.getQuanHam();
                            short[] idEq2 = new short[5];
                            for (int i31 = 0; i31 < 5; ++i31) {
                                idEq2[i31] = msg.reader().readShort();
                                info7.equipID[info7.gun][i31] = idEq2[i31];
                                info7.getMyEquip(6);
                            }
                            info7.clanContribute1 = msg.reader().readUTF();
                            info7.clanContribute2 = msg.reader().readUTF();
                            clanMember.addElement(info7);
                        }
                        this.gameLogicHandler.onClanMemberList(pageMem, clanMember);
                        break;
                    }
                    case 110: {
                        Vector<LuckyGame.Gift> gifts = new Vector<>();
                        for (int i32 = 0; i32 < 10; ++i32) {
                            byte typer = msg.reader().readByte();
                            byte itemId = msg.reader().readByte();
                            int count = msg.reader().readInt();
                            LuckyGame.Gift g = new LuckyGame.Gift(typer, itemId, count);
                            gifts.addElement(g);
                        }
                        byte numb = msg.reader().readByte();
                        CCanvas.endDlg();
                        CCanvas.luckyGame.getGifts(gifts, numb);
                        Session_ME.receiveSynchronized = 1;
                        break;
                    }
                    case 119: {
                        byte indxGift = msg.reader().readByte();
                        byte userGift = msg.reader().readByte();
                        byte typeGift = msg.reader().readByte();
                        CRes.out("Gift= " + typeGift);
                        String nameG = PM.p[userGift].name;
                        if (typeGift == 0) {
                            int xuGift = msg.reader().readUnsignedShort();
                            String text2 = String.valueOf(nameG) + ": +" + xuGift + Language.xu();
                            CCanvas.gameScr.vGift.addElement(new GiftEffect(text2, null));
                        }
                        if (typeGift == 1) {
                            byte idItem = msg.reader().readByte();
                            byte numbGift = msg.reader().readByte();
                            String text3 = String.valueOf(nameG) + " : +" + numbGift + "x "
                                    + Item.ITEM_NAME[idItem];
                            CCanvas.gameScr.vGift.addElement(new GiftEffect(text3, null));
                        }
                        if (typeGift == 2) {
                            byte glassGift = msg.reader().readByte();
                            byte tGift = msg.reader().readByte();
                            short idGift = msg.reader().readShort();
                            Equip eq2 = PlayerEquip.getEquip(glassGift, tGift, idGift);
                            String text4 = String.valueOf(nameG) + " : +" + msg.reader().readUTF();
                            CCanvas.gameScr.vGift.addElement(new GiftEffect(text4, eq2));
                        }
                        if (typeGift == 3) {
                            byte expGift = msg.reader().readByte();
                            String text5 = String.valueOf(nameG) + " : +" + expGift + "xp";
                            CCanvas.gameScr.vGift.addElement(new GiftEffect(text5, null));
                        }
                        if (typeGift == 4) {
                            String material = msg.reader().readUTF();
                            CCanvas.gameScr.vGift.addElement(new GiftEffect("+ " + material, null));
                            break;
                        }
                        break;
                    }
                    case 120: {
                        CRes.out(" =========================> Save BIG Image ");
                        byte idBigImg = msg.reader().readByte();
                        int lenBigImg = msg.reader().readUnsignedShort();
                        byte[] dataBig2 = new byte[lenBigImg];
                        msg.reader().read(dataBig2, 0, lenBigImg);
                        CCanvas.saveData("bigImage" + idBigImg, dataBig2);
                        if (PlayerEquip.imgData == null) {
                            PlayerEquip.imgData = new mImage[idBigImg + 1];
                        }
                        PlayerEquip.imgData[idBigImg] = mImage.createImage(dataBig2, 0, lenBigImg, "");
                        dataBig2 = null;
                        LoginScr.maxTime = 90 + idBigImg;
                        if (idBigImg == 9) {
                            LoginScr.isWait = false;
                            GameService.gI().sendVersion((byte) 6, (byte) 0);
                            CCanvas.endDlg();
                            this.gameLogicHandler.onLoginSuccess();
                        }
                        CRes.out(" =========================> Load BigImage  DONE!");
                        break;
                    }
                    case -120: {
                        CRes.out(" =========================> Save GET_BIG_EQUIP_HD Image ");
                        CRes.out(" =========================> Save BIG Image ");
                        byte idBigImg = msg.reader().readByte();
                        CRes.out(" =========================> Save idBigImg " + idBigImg);
                        int lenBigImg = msg.reader().readInt();
                        CRes.out(" =========================> Save lenBigImg " + lenBigImg);
                        byte[] dataBig2 = new byte[lenBigImg];
                        msg.reader().read(dataBig2, 0, lenBigImg);
                        CCanvas.saveData("bigImage" + idBigImg, dataBig2);
                        if (PlayerEquip.imgData == null) {
                            PlayerEquip.imgData = new mImage[idBigImg + 1];
                        }
                        PlayerEquip.imgData[idBigImg] = mImage.createImage(dataBig2, 0, lenBigImg, "");
                        dataBig2 = null;
                        LoginScr.maxTime = 90 + idBigImg;
                        if (idBigImg == 9) {
                            LoginScr.isWait = false;
                            GameService.gI().sendVersion((byte) 6, (byte) 0);
                            CCanvas.endDlg();
                            this.gameLogicHandler.onLoginSuccess();
                            break;
                        }
                        break;
                    }
                    case 121: {
                        String smsReg2 = "";
                        boolean isReg2Succ = msg.reader().readBoolean();
                        if (!isReg2Succ) {
                            smsReg2 = msg.reader().readUTF();
                            CCanvas.startOKDlg(smsReg2);
                            break;
                        }
                        CCanvas.startOKDlg(Language.dangkySucceed(), new IAction() {
                            @Override
                            public void perform() {
                                LoginScr _loginScr = (LoginScr) CCanvas.curScr;
                                if (_loginScr != null) {
                                    _loginScr.setLogin();
                                }
                            }
                        });
                        break;
                    }
                    case -93: {
                        boolean smsAvaiable = msg.reader().readBoolean();
                        String smsContent = msg.reader().readUTF();
                        String smsStr = msg.reader().readUTF();
                        String infoC = msg.reader().readUTF();
                        this.gameLogicHandler.onRegisterInfo2(smsContent, smsAvaiable, smsStr, infoC);
                        break;
                    }
                    case 123: {
                        System.out.println("CHAT TO TEAM");
                        MsgInfo msg3 = new MsgInfo();
                        msg3.fromName = String.valueOf(msg.reader().readUTF()) + " " + Language.chatAll();
                        msg3.message = msg.reader().readUTF();
                        this.gameLogicHandler.onChatFrom(msg3);
                        break;
                    }
                    case 124: {
                        byte gIndex = msg.reader().readByte();
                        byte pIndex = msg.reader().readByte();
                        PM.p[gIndex].ghostHit(pIndex);
                        PM.p[gIndex].checkGhostLook(PM.p[pIndex].x, PM.p[gIndex].x);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                            break;
                        }
                        break;
                    }
                    case 125: {
                        byte typeMaterial = msg.reader().readByte();
                        if (typeMaterial == 0) {
                            byte nMaterial = msg.reader().readByte();
                            for (int i33 = 0; i33 < nMaterial; ++i33) {
                                byte mId = msg.reader().readByte();
                                short numM = msg.reader().readShort();
                                String mName = msg.reader().readUTF();
                                String mDetail = msg.reader().readUTF();
                                Equip material2 = new Equip();
                                material2.id = mId;
                                material2.name = mName;
                                material2.strDetail = mDetail;
                                material2.isMaterial = true;
                                material2.icon = mId;
                                material2.num = numM;
                                if (material2.materialIcon == null && MaterialIconMn.isExistIcon(mId)) {
                                    material2.materialIcon = MaterialIconMn.getImageFromID(mId);
                                }
                                CCanvas.equipScreen.addMaterial(material2);
                            }
                            break;
                        }
                        break;
                    }
                    case 126: {
                        Message messageTem = msg;
                        try {
                            byte typeIcon = messageTem.reader().readByte();
                            int idMIcon = messageTem.reader().readUnsignedByte();
                            short lenMIcon = messageTem.reader().readShort();
                            byte[] dataMIcon = new byte[lenMIcon];
                            messageTem.reader().read(dataMIcon, 0, lenMIcon);
                            if (typeIcon == 0) {
                                MaterialIconMn.addIcon(new ImageIcon(idMIcon, dataMIcon, lenMIcon));
                                CCanvas.equipScreen.getMaterialIcon(idMIcon, dataMIcon, lenMIcon);
                            }
                            if (typeIcon == 1) {
                                MaterialIconMn.addIcon(new ImageIcon(idMIcon, dataMIcon, lenMIcon));
                                CCanvas.shopLinhtinh.getMaterialIcon(idMIcon, dataMIcon, lenMIcon);
                            }
                            if (typeIcon == 2) {
                                GameScr.mm.addImage(idMIcon, dataMIcon, lenMIcon);
                            }
                            if (typeIcon == 3) {
                                byte indexIcon = messageTem.reader().readByte();
                                CCanvas.luckyGifrScreen.getGiftByItemID(indexIcon).setIcon(dataMIcon, lenMIcon);
                            }
                            if (typeIcon == 4) {
                                messageTem.reader().readByte();
                            }
                            dataMIcon = null;
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        break;
                    }
                    case 17: {
                        byte iAction = msg.reader().readByte();
                        if (iAction == 0) {
                            String iMess = msg.reader().readUTF();
                            CCanvas.inventory.combineYesNo(iMess);
                            break;
                        }
                        break;
                    }
                    case 27: {
                        byte nAction = msg.reader().readByte();
                        CRes.out("================> inventory Update type= " + nAction);
                        for (int i34 = 0; i34 < nAction; ++i34) {
                            byte IAction = msg.reader().readByte();
                            CRes.out("action= " + IAction);
                            if (IAction == 0) {
                                int IdbKey = msg.reader().readInt();
                                byte nRemove = msg.reader().readByte();
                                CRes.out("nRemove= " + nRemove);
                                CCanvas.equipScreen.removeEquip(IdbKey, nRemove);
                                CCanvas.inventory.removeEquip(IdbKey, nRemove);
                            } else if (IAction == 2) {
                                int IdbKey = msg.reader().readInt();
                                CRes.out("======> INVENTORY_UPDATE IDDB= " + IdbKey);
                                byte nAb = msg.reader().readByte();
                                byte[] IAb = new byte[nAb];
                                for (int a6 = 0; a6 < nAb; ++a6) {
                                    IAb[a6] = msg.reader().readByte();
                                    CRes.out("====> INVENTORY_UPDATE ability= " + IAb[a6]);
                                }
                                byte slotUpdate = msg.reader().readByte();
                                byte dateUpdate = msg.reader().readByte();
                                Equip tam3 = null;
                                if (CCanvas.curScr == CCanvas.inventory) {
                                    tam3 = CCanvas.inventory.getEquip(IdbKey);
                                }
                                if (CCanvas.curScr == CCanvas.equipScreen) {
                                    tam3 = CCanvas.equipScreen.getEquip(IdbKey);
                                }
                                tam3.getInvAtribute(IAb);
                                tam3.slot = slotUpdate;
                                tam3.date = dateUpdate;
                                if (CCanvas.curScr == CCanvas.inventory) {
                                    CCanvas.inventory.getDetail();
                                }
                                if (CCanvas.curScr == CCanvas.equipScreen) {
                                    CCanvas.equipScreen.getDetail();
                                }
                                if (TerrainMidlet.myInfo.myEquip.equips[tam3.type] != null
                                        && TerrainMidlet.myInfo.myEquip.equips[tam3.type].dbKey == tam3.dbKey) {
                                    TerrainMidlet.myInfo.myEquip.equips[tam3.type].changeToEquip(tam3);
                                    TerrainMidlet.myInfo.clearAttAddPoint();
                                }
                                CCanvas.equipScreen.getBaseAttribute();
                            } else if (IAction == 1) {
                                byte IdMaterial = msg.reader().readByte();
                                String nameMaterial = msg.reader().readUTF();
                                String detailM = msg.reader().readUTF();
                                Equip eq3 = new Equip();
                                eq3.id = IdMaterial;
                                eq3.name = nameMaterial;
                                eq3.strDetail = detailM;
                                eq3.isMaterial = true;
                                CCanvas.equipScreen.addEquip(eq3, false);
                            } else if (IAction == 3) {
                                byte IdMaterial = msg.reader().readByte();
                                byte numM2 = msg.reader().readByte();
                                String nameMaterial2 = msg.reader().readUTF();
                                String detailM2 = msg.reader().readUTF();
                                Equip eq4 = new Equip();
                                eq4.id = IdMaterial;
                                eq4.num = numM2;
                                eq4.name = nameMaterial2;
                                eq4.strDetail = detailM2;
                                eq4.isMaterial = true;
                                CCanvas.equipScreen.addEquip(eq4, true);
                            }
                        }
                        CCanvas.inventory.unSelectEquip();
                        break;
                    }
                    case 80: {
                        byte whoEnd = msg.reader().readByte();
                        CCanvas.gameScr.checkInvisible2(whoEnd);
                        break;
                    }
                    case 59: {
                        byte whoEndV = msg.reader().readByte();
                        CCanvas.gameScr.checkVampire(whoEndV);
                        break;
                    }
                    case -2: {
                        CRes.out("VIP EQUIP");
                        byte vipAction = msg.reader().readByte();
                        if (vipAction == 0) {
                            TerrainMidlet.isVip[TerrainMidlet.myInfo.gun] = false;
                            TerrainMidlet.myInfo.getMyEquip(7);
                        } else {
                            for (int i35 = 0; i35 < 5; ++i35) {
                                short vipID = msg.reader().readShort();
                                CRes.out(" vip ID= " + vipID);
                                TerrainMidlet.myInfo.equipVipID[TerrainMidlet.myInfo.gun][i35] = vipID;
                            }
                            TerrainMidlet.myInfo.getVipEquip();
                            TerrainMidlet.isVip[TerrainMidlet.myInfo.gun] = true;
                        }
                        CCanvas.equipScreen.getBaseAttribute();
                        break;
                    }
                    case -3: {
                        Vector<Equip> vipItems = new Vector<>();
                        while (msg.reader().available() > 0) {
                            byte vipID2 = msg.reader().readByte();
                            String vipName = msg.reader().readUTF();
                            String vipInfo = msg.reader().readUTF();
                            int xuVip = msg.reader().readInt();
                            int luongVip = msg.reader().readInt();
                            byte dateVip = msg.reader().readByte();
                            byte isByteNum = msg.reader().readByte();
                            Equip vipEq = new Equip();
                            vipEq.id = vipID2;
                            vipEq.name = vipName;
                            vipEq.strDetail = vipInfo;
                            vipEq.xu = xuVip;
                            vipEq.luong = luongVip;
                            vipEq.date = dateVip;
                            vipEq.isMaterial = true;
                            if (MaterialIconMn.isExistIcon(vipID2)) {
                                vipEq.materialIcon = MaterialIconMn.getImageFromID(vipID2);
                            } else {
                                GameService.gI().getMaterialIcon((byte) 1, vipID2, -1);
                            }
                            if (isByteNum == 0) {
                                vipEq.isBuyNum = true;
                            }
                            vipItems.addElement(vipEq);
                        }
                        CCanvas.endDlg();
                        CCanvas.shopLinhtinh.setItems(vipItems);
                        CCanvas.shopLinhtinh.show(CCanvas.menuScr);
                        break;
                    }
                    case -6: {
                        byte tMID = msg.reader().readByte();
                        MM.maps.removeAllElements();
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            CCanvas.curScr = CCanvas.prepareScr;
                        }
                        GameScr.mm.createMap(tMID);
                        System.gc();
                        CCanvas.endDlg();
                        break;
                    }
                    case -7: {
                        for (int i36 = 0; i36 < 5; ++i36) {
                            int currDbKey = msg.reader().readInt();
                            if (TerrainMidlet.myInfo.myEquip.equips[i36] != null) {
                                TerrainMidlet.myInfo.myEquip.equips[i36].dbKey = currDbKey;
                            }
                        }
                        break;
                    }
                    case -10: {
                        for (int i36 = 0; i36 < 8; ++i36) {
                            GameScr.num[i36] = msg.reader().readByte();
                        }
                        break;
                    }
                    case -12: {
                        if (CCanvas.shopBietDoi == null) {
                            CCanvas.shopBietDoi = new ShopBietDoi();
                        }
                        byte nCl = msg.reader().readByte();
                        Vector<ClanItem> clItems = new Vector<>();
                        for (int i37 = 0; i37 < nCl; ++i37) {
                            ClanItem clanItem = new ClanItem();
                            clanItem.id = msg.reader().readByte();
                            clanItem.name = msg.reader().readUTF();
                            clanItem.xu = msg.reader().readInt();
                            clanItem.luong = msg.reader().readInt();
                            clanItem.expDate = msg.reader().readByte();
                            clanItem.levelRequire = msg.reader().readByte();
                            clItems.addElement(clanItem);
                        }
                        CCanvas.shopBietDoi.setItems(clItems);
                        CCanvas.shopBietDoi.show();
                        break;
                    }
                    case -14: {
                        byte typeList = msg.reader().readByte();
                        if (typeList == -1) {
                            byte lentArry = msg.reader().readByte();
                            MenuScr.subMenuString[7] = new String[lentArry];
                            for (int i38 = 0; i38 < lentArry; ++i38) {
                                MenuScr.subMenuString[7][i38] = msg.reader().readUTF().toUpperCase();
                            }
                            break;
                        }
                        byte pageThanhtich = msg.reader().readByte();
                        String title = msg.reader().readUTF();
                        Vector<PlayerInfo> thanhtich = new Vector<>();
                        while (msg.reader().available() > 0) {
                            PlayerInfo myPlayers = new PlayerInfo();
                            myPlayers.IDDB = msg.reader().readInt();
                            myPlayers.name = msg.reader().readUTF();
                            myPlayers.gun = msg.reader().readByte();
                            myPlayers.clanID = msg.reader().readShort();
                            myPlayers.level2 = msg.reader().readUnsignedByte();
                            myPlayers.level2Percen = msg.reader().readByte();
                            myPlayers.getQuanHam();
                            myPlayers.STT = msg.reader().readUnsignedByte();
                            for (int i39 = 0; i39 < 5; ++i39) {
                                myPlayers.equipID[myPlayers.gun][i39] = msg.reader().readShort();
                            }
                            myPlayers.aa = msg.reader().readUTF();
                            CRes.out("aa= " + myPlayers.aa);
                            myPlayers.isReady = true;
                            myPlayers.getMyEquip(8);
                            thanhtich.addElement(myPlayers);
                        }
                        this.gameLogicHandler.onXepHanglist((byte) (-typeList), pageThanhtich, thanhtich,
                                title);
                        break;
                    }
                    case -17: {
                        byte luckyAction = msg.reader().readByte();
                        CRes.out("===>lucky gift action = " + luckyAction);
                        if (luckyAction == 0) {
                            byte idGift2 = msg.reader().readByte();
                            byte typeLuckGift = msg.reader().readByte();
                            byte idLuckyGift = msg.reader().readByte();
                            String strLuckyGift = msg.reader().readUTF();
                            LuckyGift g2 = new LuckyGift();
                            g2.id = idGift2;
                            g2.type = typeLuckGift;
                            g2.info = strLuckyGift;
                            g2.itemID = idLuckyGift;
                            g2.isWait = true;
                            g2.isServerSend = true;
                            CCanvas.luckyGifrScreen.setGiftByItemID(g2);
                            if (g2.type == 2) {
                                GameService.gI().getMaterialIcon((byte) 3, g2.itemID, idGift2);
                            }
                        }
                        if (luckyAction == -1) {
                            if (CCanvas.curScr == CCanvas.gameScr) {
                                break;
                            }
                            byte timeGift = msg.reader().readByte();
                            String giftInfo = msg.reader().readUTF();
                            if (CCanvas.width < 200) {
                                CCanvas.startOKDlg(giftInfo);
                            }
                            LuckyGifrScreen.info = Font.normalFont.splitFontBStrInLine(giftInfo,
                                    CCanvas.width - 80);
                            (LuckyGifrScreen.time = new CTime()).initTimeInterval(timeGift);
                            LuckyGifrScreen.time.resetTime();
                            CCanvas.luckyGifrScreen.isShow = false;
                            CCanvas.luckyGifrScreen.show();
                        }
                        if (luckyAction == -2) {
                            for (int i40 = 0; i40 < 12; ++i40) {
                                LuckyGift g3 = new LuckyGift();
                                byte typeLuckGift2 = msg.reader().readByte();
                                if (typeLuckGift2 != -1) {
                                    byte idLuckyGift2 = msg.reader().readByte();
                                    String strLuckyGift2 = msg.reader().readUTF();
                                    g3.id = i40;
                                    g3.type = typeLuckGift2;
                                    g3.info = strLuckyGift2;
                                    g3.itemID = idLuckyGift2;
                                    g3.isServerSend = true;
                                    g3.isWait = true;
                                    CCanvas.luckyGifrScreen.setGiftByItemID(g3);
                                    CCanvas.luckyGifrScreen.giftDelete[i40] = -1;
                                    if (g3.type == 2) {
                                        GameService.gI().getMaterialIcon((byte) 3, g3.itemID, (byte) i40);
                                    }
                                }
                            }
                            CCanvas.luckyGifrScreen.isShow = true;
                            CCanvas.luckyGifrScreen.show();
                            break;
                        }
                        break;
                    }
                    case -18: {
                        byte fomulaAction = msg.reader().readByte();
                        CRes.out("FOMULA= " + fomulaAction);
                        if (fomulaAction == 0) {
                            String fInfo = msg.reader().readUTF();
                            CRes.out("fInfo= " + fInfo);
                            CCanvas.startOKDlg(fInfo, new IAction() {
                                @Override
                                public void perform() {
                                    CCanvas.fomulaScreen.lastScr.show();
                                }
                            });
                        }
                        if (fomulaAction != 1) {
                            break;
                        }
                        CCanvas.fomulaScreen.fomulas.removeAllElements();
                        CCanvas.endDlg();
                        byte idFomula = msg.reader().readByte();
                        byte nFomula = msg.reader().readByte();
                        CRes.out(" nFomular= " + nFomula);
                        for (int i41 = 0; i41 < nFomula; ++i41) {
                            Fomula fomula = new Fomula();
                            byte idItemCreate = msg.reader().readByte();
                            String nameEquip = msg.reader().readUTF();
                            byte leveRequire = msg.reader().readByte();
                            byte gunFomula = msg.reader().readByte();
                            byte typeFomula = msg.reader().readByte();
                            CRes.out("id item create= " + idItemCreate + " type Fomula= " + typeFomula
                                    + " gun= " + gunFomula);
                            fomula.e = PlayerEquip.createEquip(gunFomula, typeFomula, idItemCreate);
                            fomula.e.name = nameEquip;
                            CRes.out("Name equip= " + nameEquip);
                            fomula.levelRequire = leveRequire;
                            fomula.ID = idFomula;
                            byte nMaterial2 = msg.reader().readByte();
                            fomula.imgMaterial = new mImage[nMaterial2];
                            fomula.numMaterial = new String[nMaterial2];
                            fomula.materialName = new String[nMaterial2];
                            fomula.idImage = new int[nMaterial2];
                            for (int j4 = 0; j4 < nMaterial2; ++j4) {
                                byte materialIcon = msg.reader().readByte();
                                String materialName = msg.reader().readUTF();
                                fomula.materialName[j4] = materialName;
                                if (MaterialIconMn.isExistIcon(materialIcon)) {
                                    fomula.imgMaterial[j4] = MaterialIconMn.getImageFromID(materialIcon);
                                } else {
                                    GameService.gI().getMaterialIcon((byte) 4, materialIcon, (byte) i41);
                                }
                                fomula.idImage[j4] = materialIcon;
                                int materialRequire = msg.reader().readUnsignedByte();
                                int materialHave = msg.reader().readUnsignedByte();
                                fomula.numMaterial[j4] = String.valueOf(materialHave) + "/" + materialRequire;
                                CRes.out("Image id= " + materialIcon + " numMaterial= "
                                        + fomula.numMaterial[i41]);
                            }
                            byte idEquipRequire = msg.reader().readByte();
                            String nameEquipRequire = msg.reader().readUTF();
                            byte requireLevel = msg.reader().readByte();
                            nameEquipRequire = String.valueOf(nameEquipRequire)
                                    + ((requireLevel != 0) ? (" " + requireLevel) : "");
                            boolean isHave = msg.reader().readBoolean();
                            boolean isFinish = msg.reader().readBoolean();
                            byte nAbility2 = msg.reader().readByte();
                            fomula.ability = new String[nAbility2];
                            for (int a7 = 0; a7 < nAbility2; ++a7) {
                                fomula.ability[a7] = msg.reader().readUTF();
                            }
                            fomula.h1 = nAbility2 * 18;
                            fomula.isHave = isHave;
                            CRes.out("is Have= " + isHave);
                            fomula.equipRequire = PlayerEquip.createEquip(gunFomula, typeFomula,
                                    idEquipRequire);
                            fomula.equipRequire.name = nameEquipRequire;
                            fomula.finish = isFinish;
                            CRes.out("is Finish= " + isFinish);
                            CCanvas.fomulaScreen.setFomula(fomula);
                        }
                        if (CCanvas.curScr == CCanvas.inventory) {
                            CCanvas.fomulaScreen.show(CCanvas.inventory);
                        }
                        if (CCanvas.curScr == CCanvas.shopLinhtinh) {
                            CCanvas.fomulaScreen.show(CCanvas.shopLinhtinh);
                            break;
                        }
                        break;
                    }
                    case -19: {
                        byte nRoomName = msg.reader().readByte();
                        for (int i42 = 0; i42 < nRoomName; ++i42) {
                            byte roomNameID = msg.reader().readByte();
                            String roomName = msg.reader().readUTF();
                            byte levelRoom = msg.reader().readByte();
                            CCanvas.roomListScr2.changeName(roomNameID + levelRoom + 1, roomName);
                        }
                        break;
                    }
                    case -22: {
                        CCanvas.clanScreen.clan.money = msg.reader().readInt();
                        CCanvas.clanScreen.clan.money2 = msg.reader().readInt();
                        break;
                    }
                    case -23: {
                        CCanvas.endDlg();
                        Vector<Mission> missions = new Vector<>();
                        while (msg.reader().available() > 0) {
                            Mission m2 = new Mission();
                            m2.id = msg.reader().readByte();
                            m2.level = msg.reader().readByte();
                            m2.name = msg.reader().readUTF();
                            m2.reward = msg.reader().readUTF();
                            m2.require = msg.reader().readInt();
                            m2.have = msg.reader().readInt();
                            m2.isComplete = msg.reader().readBoolean();
                            missions.addElement(m2);
                        }
                        CCanvas.missionScreen.setMission(missions);
                        CCanvas.missionScreen.show();
                        break;
                    }
                    case -24: {
                        byte cup2 = msg.reader().readByte();
                        int currCup = msg.reader().readInt();
                        TerrainMidlet.myInfo.cup = currCup;
                        if (CCanvas.curScr == CCanvas.gameScr && PM.p[GameScr.myIndex] != null) {
                            PM.p[GameScr.myIndex].updateCup(cup2);
                            break;
                        }
                        break;
                    }
                    case -25: {
                        final int idGiaHan = msg.reader().readInt();
                        String giahanInfo = msg.reader().readUTF();
                        CCanvas.startYesNoDlg(giahanInfo, new IAction() {
                            @Override
                            public void perform() {
                                GameService.gI().get_more_day((byte) 1, idGiaHan);
                            }
                        });
                        break;
                    }
                    case -100: {
                        CRes.out(
                                "Quang cao-----------------------------------------------------------------------------------");
                        String strGameName = msg.reader().readUTF();
                        String strContent = msg.reader().readUTF();
                        String linkGame = msg.reader().readUTF();
                        MenuScr.getIdMenu(1);
                        MenuScr.menuString[MenuScr.MENU_QUANGCAO] = strGameName.toUpperCase();
                        MenuScr.gameContent = strContent;
                        MenuScr.gameLink = linkGame;
                        if (CCanvas.quangCaoScr == null) {
                            CCanvas.quangCaoScr = new QuangCao();
                        }
                        QuangCao.content = strContent;
                        QuangCao.link = linkGame;
                        CCanvas.quangCaoScr.getCommand();
                        CRes.out("game = " + strGameName + " strContent= " + strContent + " linkGame= "
                                + linkGame);
                        break;
                    }
                    case -26: {
                        String agent = msg.reader().readUTF();
                        byte provider = msg.reader().readByte();
                        CRes.out("==============> agent= " + agent + " provider= " + provider);
                        CRes.saveRMSInt("provider", TerrainMidlet.PROVIDER = provider);
                        CRes.saveRMS_String("agent", TerrainMidlet.AGENT = agent);
                        break;
                    }
                    case -101: {
                        boolean regSucceed = msg.reader().readBoolean();
                        if (regSucceed) {
                            CCanvas.msgdlg.setInfo("\u0110\u0103ng k\u00ed th\u00e0nh c\u00f4ng", null,
                                    new Command("OK", new IAction() {
                                        @Override
                                        public void perform() {
                                            CCanvas.endDlg();
                                        }
                                    }), null);
                            CCanvas.msgdlg.show();
                            break;
                        }
                        String _error = msg.reader().readUTF();
                        CCanvas.msgdlg.setInfo(_error, null, new Command("OK", new IAction() {
                            @Override
                            public void perform() {
                                CCanvas.endDlg();
                            }
                        }), null);
                        CCanvas.msgdlg.show();
                        break;
                    }
                    case -103: {
                        byte status = msg.reader().readByte();
                        String announce = msg.reader().readUTF();
                        if (status == 0) {
                            CCanvas.startOKDlg(announce, new IAction() {
                                @Override
                                public void perform() {
                                    CCanvas.inputDlg.setInfo(Language.createCharName(), new IAction() {
                                        @Override
                                        public void perform() {
                                            if (!CCanvas.inputDlg.tfInput.getText().isEmpty()) {
                                                GameService.gI().onSendChangeRequest(
                                                        CCanvas.inputDlg.tfInput.getText());
                                            }
                                        }
                                    }, null, 1);
                                    CCanvas.inputDlg.show();
                                }
                            });
                            break;
                        }
                        CCanvas.startOKDlg(announce);
                        break;
                    }
                    case 30: {
                        CCanvas.endDlg();
                        break;
                    }
                }

            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    public void setGameLogicHandler(IGameLogicHandler gameLogicHandler) {
        this.gameLogicHandler = gameLogicHandler;
    }

    static {
        MessageHandler.nextTurnFlag = false;
        MessageHandler.lag = false;
        MessageHandler.LOCK = new Object();
        MessageHandler.dem = 0;
    }
}
