package network;

import CLib.mImage;
import CLib.Image;
import model.Font;
import model.AvatarInfo;
import model.UserData;
import model.MsgPopup;
import model.MsgInfo;
import screen.ListScr;
import player.PM;
import screen.BoardListScr;
import screen.PrepareScr;
import screen.ConfigScr;
import com.teamobi.mobiarmy2.MainGame;
import CLib.mSound;
import model.PlayerInfo;
import java.util.Vector;
import screen.MenuScr;
import coreLG.TerrainMidlet;
import screen.LoginScr;
import model.CRes;
import com.teamobi.mobiarmy2.GameMidlet;
import CLib.mSystem;
import coreLG.CCanvas;
import model.IAction;
import model.Language;
import CLib.RMS;

public class GameLogicHandler implements IGameLogicHandler {

    public static boolean isServerThongBao;
    private static GameLogicHandler instance;
    public static boolean isTryGetIPFromWap;

    public static GameLogicHandler gI() {
        return GameLogicHandler.instance;
    }

    public static byte[] loadRMS(String filename) {
        return RMS.loadRMS(filename);
    }

    public static void saveRMS(String filename, byte[] data) {
        try {
            RMS.saveRMS(filename, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveIP(String strID) {
        saveRMS("ARMY2", strID.getBytes());
    }

    public static String loadIP() {
        byte[] data = loadRMS("AMRY2");
        if (data == null) {
            return null;
        }
        return new String(data);
    }

    @Override
    public void onConnectFail() {
        CCanvas.startOKDlg(Language.connectFail(), new IAction() {
            @Override
            public void perform() {
                onResetGame();
            }
        });
    }

    @Override
    public void onConnectOK() {
        CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 10000);
    }

    @Override
    public void onDisconnect() {
        this.onResetGame();
    }

    public void onResetGame() {
        try {
            CCanvas.onClearCCanvas();
            Session_ME.gI().close(1534);
            Session_ME.gI().start = true;
            CCanvas.endDlg();
            mSystem.my_Gc();
        } catch (Exception ex) {
        }
        Session_ME.gI().connected = false;
        GameMidlet.pingCount = 0;
        CCanvas.loadScreen();
        CCanvas.serverListScreen.show();
    }

    @Override
    public void onLoginFail(String reason) {
        CCanvas.startOKDlg(reason);
    }

    @Override
    public void onLoginSuccess() {
        CRes.out("========> Login thangh cong nha ae!!!!!!!");
        if (LoginScr.remember == 1) {
            if (GameMidlet.server != 2) {
                TerrainMidlet.myInfo.name = LoginScr.user;
            }
            CRes.saveRMS_String("caroun", LoginScr.user);
            CRes.saveRMS_String("caropass", LoginScr.pass);
        } else {
            if (GameMidlet.server != 2) {
                TerrainMidlet.myInfo.name = LoginScr.user;
            }
            CRes.saveRMS_String("caroun", "");
            CRes.saveRMS_String("caropass", "");
        }
        if (CCanvas.menuScr == null) {
            CCanvas.menuScr = new MenuScr();
        }
        CCanvas.menuScr.show();
        if (CCanvas.msgScr.list.size() != 0) {
            CCanvas.msgScr.show(CCanvas.menuScr);
        }
    }

    @Override
    public void onRoomList(Vector roomList) {
    }

    @Override
    public void onBoardList(byte roomID, Vector boardList) {
    }

    @Override
    public void onJoinGameSuccess(int ownerID, int money, Vector players, byte map) {
        CRes.out(String.valueOf(this.getClass().getName()) + " onJoinGameSuccess ownerID/money/map  " + ownerID + "/"
                + money + "/" + map);
    }

    @Override
    public void onJoinGameFail(String reason) {
        CCanvas.msgdlg.setInfo(reason, null, new Command("OK", new IAction() {
            @Override
            public void perform() {
                CCanvas.endDlg();
                CCanvas.startWaitDlg(Language.getRoomlist());
                GameService.gI().requestRoomList();
            }
        }), null);
        CCanvas.msgdlg.show();
    }

    @Override
    public void onSomeOneJoinBoard(int seat, PlayerInfo joinPersonInfo) {
        try {
            mSound.playSound(3, mSound.volumeSound, 1);
            MainGame.me.vibrate(ConfigScr.vibrate);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onSomeOneLeaveBoard(int leave, int newOwner) {
    }

    @Override
    public void onSomeOneReady(int id, boolean isReady) {
        if (id == TerrainMidlet.myInfo.IDDB) {
            CCanvas.endDlg();
        }
        CCanvas.prepareScr.setReady(id, isReady);
    }

    @Override
    public void onOwnerSetMoney(int money3) {
    }

    @Override
    public void onChatFromBoard(String text, int fromID) {
        if (CCanvas.prepareScr.isShowing()) {
            if (PrepareScr.currLevel != 7) {
                CCanvas.prepareScr.showChat(fromID, text, 90);
            }
        } else if (CCanvas.gameScr != null && CCanvas.gameScr.isShowing()) {
            CCanvas.gameScr.showChat(fromID, text, 90);
        }
    }

    @Override
    public void onKicked(int kicked, String reason) {
        CCanvas.prepareScr.playerLeave(kicked);
        if (PrepareScr.currLevel != 7) {
            if (kicked == TerrainMidlet.myInfo.IDDB) {
                if (BoardListScr.boardList == null) {
                    CCanvas.menuScr.show();
                } else {
                    CCanvas.boardListScr.show();
                }
                CCanvas.msgdlg.setInfo(reason, null, new Command("OK", new IAction() {
                    @Override
                    public void perform() {
                        CCanvas.endDlg();
                    }
                }), null);
                CCanvas.msgdlg.show();
            } else if (PM.getPlayerByIDDB(kicked) != null) {
                PM.getPlayerByIDDB(kicked).IDDB = -1;
            }
        } else if (kicked == TerrainMidlet.myInfo.IDDB) {
            CCanvas.boardListScr.show();
        } else if (PM.getPlayerByIDDB(kicked) != null) {
            PM.getPlayerByIDDB(kicked).IDDB = -1;
        }
    }

    @Override
    public void onStartGame(byte boardID6, byte roomID6, Vector myCards, int whoMoveFirst, byte interval) {
    }

    @Override
    public void onMove(byte roomID7, byte boardID7, int whoMove, byte[] movedCards, int nextMove) {
    }

    @Override
    public void onForceLose(byte roomID8, byte boardID8, int whoLose) {
    }

    @Override
    public void onMoveAndWin(byte roomID9, byte boardID9, int whoWin, byte x1, byte y1) {
    }

    @Override
    public void onOpponentWantDraw(byte roomID10, byte boardID10) {
    }

    @Override
    public void onGameDraw(byte roomID11, byte boardID11) {
    }

    @Override
    public void onDenyDraw(byte roomID12, byte boardID12) {
    }

    @Override
    public void onWantLose(byte roomID13, byte boardID13, int whoLose2) {
    }

    @Override
    public void onRichestList(int page, Vector richestList) {
    }

    @Override
    public void onStrongestList(int page, Vector strongestList) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }
        CCanvas.endDlg();
        CCanvas.listScr.page = page;
        CCanvas.listScr.setList(1, strongestList);
        CCanvas.listScr.isArmy2 = true;
        CCanvas.listScr.show(CCanvas.menuScr);
    }

    @Override
    public void onXepHanglist(byte type, byte page, Vector v, String title) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }
        CCanvas.endDlg();
        CCanvas.listScr.typeList = title;
        CCanvas.listScr.page = page;
        CCanvas.listScr.setList(type, v);
        CCanvas.listScr.isArmy2 = true;
        CCanvas.listScr.show(CCanvas.menuScr);
    }

    @Override
    public void onRegisterInfo(String username, boolean available, String smsPrefix, String smsTo) {
    }

    @Override
    public void onRegisterInfo2(String content, boolean available, String smsTo, String info) {
        if (!available) {
            CCanvas.startOKDlg(Language.ExistsNick());
        } else {
            final String strContent = content;
            final String strSmsTo = smsTo;
            CCanvas.startYesNoDlg(info, new IAction() {
                @Override
                public void perform() {
                    TerrainMidlet.sendSMS(strContent, "sms://" + strSmsTo, new IAction() {
                        @Override
                        public void perform() {
                        }
                    }, new IAction() {
                        @Override
                        public void perform() {
                        }
                    });
                }
            }, new IAction() {
                @Override
                public void perform() {
                    CCanvas.endDlg();
                }
            });
        }
    }

    @Override
    public void onChargeMoneySms(String content, String sendTo, String info) {
        final String contents = content;
        final String sendTos = sendTo;
        CCanvas.startYesNoDlg(info, new IAction() {
            @Override
            public void perform() {
                TerrainMidlet.sendSMS(contents, "sms://" + sendTos, new IAction() {
                    @Override
                    public void perform() {
                    }
                }, new IAction() {
                    @Override
                    public void perform() {
                    }
                });
            }
        }, new IAction() {
            @Override
            public void perform() {
                CCanvas.endDlg();
            }
        });
    }

    @Override
    public void onFriendList(Vector friendList) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }
        CCanvas.endDlg();
        CCanvas.listScr.setList(2, friendList);
        CCanvas.listScr.isArmy2 = true;
        if (CCanvas.curScr == CCanvas.prepareScr) {
            CCanvas.listScr.show(CCanvas.prepareScr);
        } else if (CCanvas.curScr == CCanvas.menuScr) {
            CCanvas.listScr.show(CCanvas.menuScr);
        }
    }

    @Override
    public void onInviteList(Vector inviteList) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }
        CCanvas.endDlg();
        CCanvas.listScr.isArmy2 = true;
        CCanvas.listScr.setInviteList(3, inviteList);
        if (CCanvas.curScr == CCanvas.prepareScr) {
            CCanvas.listScr.show(CCanvas.prepareScr);
        } else if (CCanvas.curScr == CCanvas.menuScr) {
            CCanvas.listScr.show(CCanvas.menuScr);
        }
    }

    @Override
    public void onSearchResult(Vector searchList) {
        if (searchList.size() > 0) {
            for (int i = 0; i < searchList.size(); ++i) {
                PlayerInfo ch = (PlayerInfo) searchList.elementAt(i);
                if (ch.name.equals(CCanvas.inputDlg.tfInput.getText())) {
                    CCanvas.startWaitDlg(String.valueOf(Language.them()) + " " + ch.name + " " + Language.vao());
                    GameService.gI().addFriend(ch.IDDB);
                }
            }
        } else {
            CCanvas.startOKDlg(Language.notFindID());
        }
    }

    @Override
    public void onAddFriendResult(byte addResult) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }
        if (addResult == 0) {
            CCanvas.startOKDlg(Language.addFriendSuccess());
            if (CCanvas.curScr == CCanvas.listScr && CCanvas.listScr.type == 2) {
                GameService.gI().requestFriendList();
            }
        } else if (addResult == 2) {
            CCanvas.startOKDlg(Language.cannotaddFriend());
        } else {
            CCanvas.startOKDlg(Language.isExist());
        }
    }

    @Override
    public void onDelFriendResult(byte delResult) {
        if (delResult == 0) {
            CCanvas.startOKDlg(Language.deleteFriendSc());
            GameService.gI().requestFriendList();
        } else {
            CCanvas.startOKDlg(Language.cannotdelete());
        }
    }

    @Override
    public void onMatchResult(Vector matchResult) {
    }

    @Override
    public void onChatFrom(MsgInfo msg1) {
        if (CCanvas.curScr != CCanvas.msgScr) {
            MsgPopup msgPopup2;
            MsgPopup msgPopup = msgPopup2 = CCanvas.msgPopup;
            ++msgPopup2.nMessage;
            CCanvas.msgPopup.show();
        }
        CCanvas.msgScr.addMsg(msg1);
    }

    @Override
    public void onMyUserData(UserData userData) {
    }

    @Override
    public void onAvatar(AvatarInfo avi) {
    }

    @Override
    public void onPing() {
    }

    @Override
    public void onAvatarList(Vector avs) {
    }

    @Override
    public void onBuyAvtarSuccess(short id) {
    }

    @Override
    public void onMoneyInfo(Vector mni) {
        CCanvas.endDlg();
        if (CCanvas.isIos()) {
            CCanvas.moneyScrIOS.setAvatarList(mni);
        } else {
            CCanvas.moneyScr.setAvatarList(mni);
        }
    }

    @Override
    public void onServerInfo(String info) {
        CCanvas.infoPopup.setInfo(info);
        CCanvas.infoPopup.show();
    }

    @Override
    public void onServerMessage(String msg) {
        CCanvas.startOKDlg(msg);
    }

    @Override
    public void onVersion(String info, final String url) {
        IAction down = new IAction() {
            @Override
            public void perform() {
                mSystem.connectHTTP(url);
            }
        };
        CCanvas.msgdlg.setInfo(info, new Command("Download", down), new Command("", down),
                new Command(Language.close(), new IAction() {
                    @Override
                    public void perform() {
                        CCanvas.endDlg();
                    }
                }));
        CCanvas.msgdlg.show();
    }

    @Override
    public void onURL(String info, String url, final byte Auto) {
        if (TerrainMidlet.myInfo != null && TerrainMidlet.myInfo.name != null) {
            Font.replace(url, "@username", TerrainMidlet.myInfo.name);
        }
        String link = url;
        IAction down = new IAction() {
            @Override
            public void perform() {
                IAction ac = new IAction() {
                    @Override
                    public void perform() {
                    }
                };
                if (Auto == 0) {
                    if (CCanvas.isBB) {
                        CCanvas.msgdlg.setInfo(Language.Question(), null, new Command(Language.exit(), ac),
                                new Command(Language.no(), new IAction() {
                                    @Override
                                    public void perform() {
                                        CCanvas.endDlg();
                                    }
                                }));
                    } else {
                        CCanvas.msgdlg.setInfo(Language.Question(), new Command(Language.exit(), ac),
                                new Command("", ac), new Command(Language.no(), new IAction() {
                                    @Override
                                    public void perform() {
                                        CCanvas.endDlg();
                                    }
                                }));
                    }
                } else if (Auto != 1 && Auto == 2) {
                    CCanvas.endDlg();
                }
            }
        };
        CCanvas.msgdlg.setInfo(info, new Command("OK", down), new Command("", down),
                new Command(Language.no(), new IAction() {
                    @Override
                    public void perform() {
                        CCanvas.endDlg();
                    }
                }));
        CCanvas.msgdlg.show();
    }

    @Override
    public void onAdminCommandResponse(String responseText) {
    }

    @Override
    public void onSomeOneFinish(byte roomID10, byte boardID10, int whoFinish, byte finishPosition, int dMoney,
            int dExp) {
    }

    @Override
    public void onStopGame(byte roomID11, byte boardID11, int whoShowCards, byte[] cardsShow) {
    }

    @Override
    public void onMoveError(byte boardID19, byte roomID19, String info) {
    }

    @Override
    public void onSetMoneyError(String error) {
        GameLogicHandler.isServerThongBao = true;
        CCanvas.startOKDlg(error, new IAction() {
            @Override
            public void perform() {
                GameLogicHandler.isServerThongBao = false;
            }
        });
    }

    @Override
    public void onFireArmy(byte whoFire, byte type, short x, short y, short angle, byte force) {
    }

    @Override
    public void onMoveArmy(byte whoMove, short x, short y) {
    }

    @Override
    public void onUpdateXY(byte whoUpdateXY, short x, short y) {
    }

    @Override
    public void onStartArmy(byte MapID, byte timeInterval, short[] playerX, short[] playerY) {
    }

    @Override
    public void onUpdateHP(byte whoUpdateHP, short nextHP) {
    }

    @Override
    public void onNextTurn(byte whoNext) {
    }

    @Override
    public void onWind(byte wind) {
    }

    @Override
    public void onUseItem(int whoUse, byte item) {
    }

    @Override
    public void onChooseGun(int userID1, byte itemGun) {
    }

    @Override
    public void onMapChanged(byte newMap) {
        CCanvas.endDlg();
        CCanvas.prepareScr.onMapChanged(newMap);
    }

    @Override
    public void onChangeTeam(int userID2, byte seat) {
        CCanvas.endDlg();
        CCanvas.prepareScr.onChangeTeam(userID2, seat);
    }

    @Override
    public void onBonusMoney(int whoBonus, int moneyBonus, int newMoney) {
    }

    @Override
    public void onClanMemberList(byte page, Vector memberList) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }
        CCanvas.endDlg();
        CCanvas.listScr.page = page;
        CCanvas.listScr.setList(5, memberList);
        CCanvas.listScr.isArmy2 = true;
        CCanvas.listScr.show(CCanvas.clanScreen);
    }

    @Override
    public void onGetImage(short id, Image img) {
        if (CCanvas.curScr == CCanvas.topClanScreen) {
            CCanvas.topClanScreen.findClan(id).icon = new mImage(img);
        }
        if (CCanvas.curScr == CCanvas.clanScreen) {
            CCanvas.clanScreen.clan.icon = new mImage(img);
        }
        if (CCanvas.curScr == CCanvas.listScr) {
            CCanvas.listScr.getPlayerIcon(id, img);
        }
        if (CCanvas.curScr == CCanvas.prepareScr) {
            CCanvas.prepareScr.getPlayerIcon(id, new mImage(img));
        }
    }

    static {
        GameLogicHandler.instance = new GameLogicHandler();
    }
}
