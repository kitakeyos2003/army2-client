package player;

import model.CRes;
import screen.CScreen;
import effect.Camera;
import CLib.mGraphics;
import map.MM;
import network.GameService;
import item.BM;
import screen.GameScr;
import coreLG.CCanvas;
import screen.PrepareScr;
import coreLG.TerrainMidlet;
import model.PlayerInfo;

public class PM {

    public static int MAX_PLAYER;
    public static int NUMB_PLAYER;
    public static CPlayer[] p;
    public static int[] npXsend;
    public static int[] npYsend;
    public static int[] npNumSend;
    public static byte curP;
    static int curUpdateP;
    public static short[] xPResult;
    public static short[] yPResult;
    public int playerCount;
    public int allCount;
    int planeX;
    int planeY;
    public static int tKC;
    public static int deltaYKC;

    public void init() {
        PM.p = new CPlayer[PM.MAX_PLAYER];
        PM.npXsend = new int[PM.MAX_PLAYER];
        PM.npYsend = new int[PM.MAX_PLAYER];
        PM.npNumSend = new int[PM.MAX_PLAYER];
        PM.xPResult = new short[PM.MAX_PLAYER];
        PM.yPResult = new short[PM.MAX_PLAYER];
    }

    public void insertPlayer(short x, short y, byte index, PlayerInfo player, int currHP) {
        boolean isCom = TerrainMidlet.myInfo.IDDB != player.IDDB;
        PM.p[index] = new CPlayer(player.IDDB, index, x, y, isCom, 0, player.gun, player.myEquip, player.maxHP);
        PM.p[index].name = player.name;
        PM.p[index].nQuanHam = player.nQuanHam2;
        PM.p[index].hp = currHP;
        PM.p[index].hpRectW = currHP * 25 / PM.p[index].maxhp;
        PM.p[index].clanIcon = player.clanIcon;
        ++this.playerCount;
    }

    public void initPlayer(short[] pX, short[] pY, short[] maxHP) {
        this.playerCount = 0;
        this.allCount = 0;
        int num = 8;
        if (PrepareScr.currLevel == 7) {
            num = PM.NUMB_PLAYER;
        }
        for (int i = 0; i < num; ++i) {
            PM.p[i] = null;
            if (pX[i] != -1) {
                PlayerInfo pi = CCanvas.prepareScr.playerInfos.elementAt(i);
                boolean isCom = TerrainMidlet.myInfo.IDDB != pi.IDDB;
                if (!pi.isBoss) {
                    PM.p[i] = new CPlayer(pi.IDDB, (byte) i, pX[i], pY[i], isCom, (i % 2 == 0) ? 2 : 0, pi.gun, pi.myEquip, maxHP[i]);
                    PM.p[i].clanIcon = pi.clanIcon;
                    PM.p[i].equip = pi.myEquip;
                } else {
                    PM.p[i] = new Boss(pi.IDDB, (byte) i, pX[i], pY[i], isCom, (i % 2 == 0) ? 2 : 0, pi.gun, maxHP[i]);
                }
                PM.p[i].name = pi.name;
                PM.p[i].nQuanHam = pi.nQuanHam2;
                PM.p[i].maxhp = maxHP[i];
                if (!isCom) {
                    PM.p[i].item = CCanvas.prepareScr.copyItemCurrent();
                    GameScr.myIndex = (byte) i;
                }
            }
            ++this.playerCount;
        }
        this.allCount = this.playerCount;
        for (int i = 0; i < num; ++i) {
            PM.npXsend[i] = -1;
            PM.npYsend[i] = -1;
            PM.npNumSend[i] = i;
        }
    }

    public String getPlayerNameFromID(int id) {
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i].IDDB == id) {
                return PM.p[i].name;
            }
        }
        return "";
    }

    public CPlayer getPlayerFromID(int id) {
        if (PM.p == null) {
            return null;
        }
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null && PM.p[i].IDDB == id) {
                return PM.p[i];
            }
        }
        return null;
    }

    public void initBoss(short[] bX, short[] bY) {
        for (int i = 0; i < bX.length; ++i) {
            PM.p[i + this.allCount] = null;
            if (bX[i] != -1) {
                PlayerInfo pi = (PlayerInfo) CCanvas.prepareScr.bossInfos.elementAt(i);
                boolean isCom = TerrainMidlet.myInfo.IDDB != pi.IDDB;
                PM.p[i + this.allCount] = new Boss(pi.IDDB, (byte) 1, bX[i], bY[i], isCom, 2, pi.gun, pi.maxHP);
                PM.p[i + this.allCount].name = pi.name;
                PM.p[i + this.allCount].hp = pi.maxHP;
                PM.p[i + this.allCount].maxhp = pi.maxHP;
                PM.p[i + this.allCount].team = false;
                PM.p[i + this.allCount].index = pi.index;
            }
        }
        this.allCount += CCanvas.prepareScr.bossInfos.size();
        CCanvas.prepareScr.bossInfos.removeAllElements();
    }

    public static void getXYResult() {
        for (int i = 0; i < PM.p.length; ++i) {
            CPlayer player = PM.p[i];
            if (player != null) {
                PM.xPResult[i] = (short) PM.p[i].x;
                PM.yPResult[i] = (short) PM.p[i].y;
            } else {
                PM.xPResult[i] = -1;
                PM.yPResult[i] = -1;
            }
        }
    }

    public static int getIndexByIDDB(int iddb) {
        for (int i = 0; i < 8; ++i) {
            PlayerInfo pi = CCanvas.prepareScr.playerInfos.elementAt(i);
            if (pi.IDDB == iddb) {
                return i;
            }
        }
        return -1;
    }

    public static CPlayer getCurPlayer() {
        if (PM.p[PM.curP] != null) {
            return PM.p[PM.curP];
        }
        return null;
    }

    public static CPlayer getMyPlayer() {
        return PM.p[GameScr.myIndex];
    }

    public static CPlayer getPlayerByIndex(int index) {
        return PM.p[index];
    }

    public static CPlayer getPlayerByIDDB(int IDDB) {
        if (PM.p == null) {
            return null;
        }
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null && PM.p[i].IDDB == IDDB) {
                return PM.p[i];
            }
        }
        return null;
    }

    public static CPlayer findPlayerByIndex(int index) {
        if (PM.p == null) {
            return null;
        }
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null && PM.p[i].index == index) {
                return PM.p[i];
            }
        }
        return null;
    }

    public void onPointerPressed(int xScreen, int yScreen, int index) {
        PM.p[GameScr.myIndex].onPointerPressed(xScreen, yScreen, index);
    }

    public void onPointerDrag(int xScreen, int yScreen, int index) {
        PM.p[GameScr.myIndex].onPointerDrag(xScreen, yScreen, index);
    }

    public void onPointerDragRighCorner(int xScreen, int yScreen, int index) {
    }

    public void onPointerHold(int xScreen, int yScreen, int index) {
        PM.p[GameScr.myIndex].onPointerHold(xScreen, yScreen, index);
    }

    public void onPointerReleased(int x, int y, int index) {
        PM.p[GameScr.myIndex].onPointerReleased(x, x, index);
    }

    public void flyAnimation() {
        if (CCanvas.gameTick % 2 == 0) {
            ++PM.tKC;
            if (PM.tKC == 10) {
                PM.deltaYKC = 0;
                PM.tKC = 0;
            }
            if (PM.tKC <= 5) {
                ++PM.deltaYKC;
            } else {
                --PM.deltaYKC;
            }
        }
    }

    public void update() {
        byte nPstand = 0;
        boolean pStillFalling = false;
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null) {
                PM.curUpdateP = i;
                PM.p[i].update();
                if (PM.p[i].isAllowSendPosAfterShoot) {
                    PM.npNumSend[nPstand] = i;
                    PM.npXsend[nPstand] = PM.p[i].x;
                    PM.npYsend[nPstand] = PM.p[i].y;
                    ++nPstand;
                }
                if (PM.p[i].falling && PM.p[i].getState() != 5) {
                    pStillFalling = true;
                }
            }
        }
        this.flyAnimation();
        if (BM.allSendENDSHOOT && !pStillFalling) {
            GameService.gI().shootResult();
            getCurPlayer().shootFrame = false;
            BM.allSendENDSHOOT = false;
            GameService.gI().holeInfo(MM.vHoleInfo);
        }
    }

    public static boolean isLand(byte index) {
        CPlayer player = getPlayerByIndex(index);
        return GameScr.mm.isLand(player.x, player.y);
    }

    public void paint(mGraphics g) {
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null && PM.p[i].x + 75 > Camera.x && PM.p[i].x - 75 < Camera.x + CScreen.w) {
                if (GameScr.cantSee) {
                    if (i == GameScr.myIndex) {
                        PM.p[i].paint(g);
                    }
                } else {
                    PM.p[i].paint(g);
                }
            }
        }
    }

    public boolean isYourTurn() {
        return PM.curP == GameScr.myIndex;
    }

    public boolean isMyPlayerUpdate() {
        return PM.curUpdateP == GameScr.myIndex;
    }

    public void setNextPlayer(byte whoNext) {
        if (!CRes.isNullOrEmpty(GameScr.res)) {
            return;
        }
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null) {
                PM.p[i].isPaintCountDown = false;
            }
        }
        PM.p[whoNext].isPaintCountDown = true;
        PM.p[whoNext].active = true;
        if (PM.p[whoNext].getState() != 8) {
            PM.p[whoNext].setState((byte) 0);
            PM.p[whoNext].checkAngleForSprite();
        }
        PM.p[whoNext].movePoint = 0;
        PM.p[whoNext].itemUsed = -1;
        if (PM.p[whoNext].isUsedItem) {
            PM.p[whoNext].isUsedItem = false;
        }
        if (PM.p[whoNext].gun != 15) {
            PM.p[whoNext].falling = true;
        }
        if (PM.p[whoNext].isStopWind) {
            GameScr.changeWind(0, 0);
        }
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null) {
                PM.p[i].shootFrame = false;
                if (PM.p[i].getState() != 5) {
                    PM.p[i].isAllowSendPosAfterShoot = false;
                }
                BM.nBum = 0;
                if (i == GameScr.myIndex) {
                    PM.p[i].resetXYwhenNEXTTURN();
                    if (!PM.p[i].chophepGuiUpdateXY) {
                        GameService.gI().move((short) PM.p[i].x, (short) PM.p[i].y);
                        PM.p[i].chophepGuiUpdateXY = true;
                    }
                }
            }
        }
        GameScr.time.resetTime();
        PM.curP = whoNext;
        if (PM.curP == GameScr.myIndex) {
            if (PM.p[PM.curP].cantSee) {
                GameScr.cantSee = true;
            } else {
                GameScr.cantSee = false;
            }
            CPlayer.isShooting = false;
        }
        if (GameScr.cam != null) {
            GameScr.cam.setPlayerMode(whoNext);
        }
    }

    public void movePlayer(int whoMove, short x, short y) {
        PM.p[whoMove].nextx = x;
        PM.p[whoMove].nexty = y;
        PM.p[whoMove].isMove = true;
        PM.p[whoMove].tMove = 0;
        if (whoMove == GameScr.myIndex) {
            PM.p[whoMove].resetLastUpdateXY(x, y);
        }
    }

    public void flyTo(int whoFly, short xF, short yF) {
        PM.p[whoFly].flyToPoint(xF, yF);
    }

    public void updatePlayerXY(int whoUpdateXY, short x, short y) {
        PM.p[whoUpdateXY].x = x;
        PM.p[whoUpdateXY].y = y;
        PM.p[whoUpdateXY].nextx = x;
        PM.p[whoUpdateXY].lastx = x;
        PM.p[whoUpdateXY].nexty = y;
        if (whoUpdateXY == GameScr.myIndex) {
            PM.p[whoUpdateXY].resetLastUpdateXY(x, y);
        }
    }

    public void setPlayerAfterSetWin(boolean teamWin) {
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null) {
                PM.p[i].active = false;
                if (PM.p[i].hp > 0) {
                    if (PM.p[i].team && teamWin) {
                        PM.p[i].setWin();
                    } else if (!PM.p[i].team && !teamWin) {
                        PM.p[i].setWin();
                    }
                    if (i == GameScr.myIndex) {
                        CRes.out("After Set Win");
                    }
                }
            }
        }
    }

    public void setPlayerAfterDraw() {
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null) {
                PM.p[i].active = false;
                if (i == GameScr.myIndex) {
                    CRes.out("After Draw");
                }
            }
        }
    }

    public void onClearMap() {
    }
}
