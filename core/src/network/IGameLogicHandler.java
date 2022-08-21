package network;

import CLib.Image;
import model.AvatarInfo;
import model.UserData;
import model.MsgInfo;
import model.PlayerInfo;
import java.util.Vector;

public interface IGameLogicHandler {

    void onLoginSuccess();

    void onLoginFail(String p0);

    void onConnectOK();

    void onConnectFail();

    void onDisconnect();

    void onRoomList(Vector p0);

    void onBoardList(byte p0, Vector p1);

    void onJoinGameSuccess(int p0, int p1, Vector p2, byte p3);

    void onJoinGameFail(String p0);

    void onSomeOneJoinBoard(int p0, PlayerInfo p1);

    void onSomeOneLeaveBoard(int p0, int p1);

    void onSomeOneReady(int p0, boolean p1);

    void onOwnerSetMoney(int p0);

    void onChatFromBoard(String p0, int p1);

    void onKicked(int p0, String p1);

    void onStartGame(byte p0, byte p1, Vector p2, int p3, byte p4);

    void onMove(byte p0, byte p1, int p2, byte[] p3, int p4);

    void onForceLose(byte p0, byte p1, int p2);

    void onMoveAndWin(byte p0, byte p1, int p2, byte p3, byte p4);

    void onOpponentWantDraw(byte p0, byte p1);

    void onGameDraw(byte p0, byte p1);

    void onDenyDraw(byte p0, byte p1);

    void onWantLose(byte p0, byte p1, int p2);

    void onRichestList(int p0, Vector p1);

    void onStrongestList(int p0, Vector p1);

    void onRegisterInfo(String p0, boolean p1, String p2, String p3);

    void onRegisterInfo2(String p0, boolean p1, String p2, String p3);

    void onChargeMoneySms(String p0, String p1, String p2);

    void onFriendList(Vector p0);

    void onClanMemberList(byte p0, Vector p1);

    void onInviteList(Vector p0);

    void onSearchResult(Vector p0);

    void onAddFriendResult(byte p0);

    void onDelFriendResult(byte p0);

    void onMatchResult(Vector p0);

    void onChatFrom(MsgInfo p0);

    void onMyUserData(UserData p0);

    void onAvatar(AvatarInfo p0);

    void onPing();

    void onAvatarList(Vector p0);

    void onBuyAvtarSuccess(short p0);

    void onMoneyInfo(Vector p0);

    void onServerMessage(String p0);

    void onServerInfo(String p0);

    void onVersion(String p0, String p1);

    void onAdminCommandResponse(String p0);

    void onSomeOneFinish(byte p0, byte p1, int p2, byte p3, int p4, int p5);

    void onStopGame(byte p0, byte p1, int p2, byte[] p3);

    void onMoveError(byte p0, byte p1, String p2);

    void onSetMoneyError(String p0);

    void onStartArmy(byte p0, byte p1, short[] p2, short[] p3);

    void onMoveArmy(byte p0, short p1, short p2);

    void onUpdateXY(byte p0, short p1, short p2);

    void onFireArmy(byte p0, byte p1, short p2, short p3, short p4, byte p5);

    void onUpdateHP(byte p0, short p1);

    void onNextTurn(byte p0);

    void onWind(byte p0);

    void onUseItem(int p0, byte p1);

    void onChooseGun(int p0, byte p1);

    void onMapChanged(byte p0);

    void onChangeTeam(int p0, byte p1);

    void onBonusMoney(int p0, int p1, int p2);

    void onURL(String p0, String p1, byte p2);

    void onGetImage(short p0, Image p1);

    void onXepHanglist(byte p0, byte p1, Vector p2, String p3);
}
