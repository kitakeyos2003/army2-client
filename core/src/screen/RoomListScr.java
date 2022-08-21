package screen;

import com.teamobi.mobiarmy2.MainGame;
import model.Font;
import CLib.mGraphics;
import network.GameService;
import network.Command;
import model.IAction;
import model.Language;
import coreLG.CCanvas;
import map.MM;
import java.util.Vector;
import CLib.mImage;

public class RoomListScr extends CScreen {

    public static mImage imgArrowRed;
    public static mImage imgMap;
    public static mImage imgCurPos;
    public static mImage imgSmallCloud;
    public static Vector roomList;
    int selected;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    int nBoardPerLine;
    int defX;
    int numH;
    int dis;
    static int nMap;
    static int curMapIndex;
    static int[] _iconX;
    static int _centerIX;
    static int rangeSplit;
    static int imgMapIW;
    static int imgMapIH;
    static boolean isMoveMenu;
    static int[] cloudType;
    static int[] cloudX;
    static int[] cloudY;
    static int curPosIndex;
    static int radaX;
    static int radaY;
    static int mapX;
    static int mapY;
    static int k;
    static int roundCamera;
    public int NUMB;
    public boolean isBoss;
    int pa;
    boolean trans;
    int speed;

    @Override
    public void moveCamera() {
        if (this.cmy != this.cmtoY) {
            this.cmvy = this.cmtoY - this.cmy << 2;
            this.cmdy += this.cmvy;
            this.cmy += this.cmdy >> 4;
            this.cmdy &= 0xF;
        }
    }

    public RoomListScr() {
        this.pa = 0;
        this.trans = false;
        this.speed = 1;
    }

    public void init() {
        this.nameCScreen = " RoomListScr screen!";
        this.NUMB = (this.isBoss ? PrepareScr.mapBossID.length : (MM.NUM_MAP - PrepareScr.mapBossID.length));
        RoomListScr.nMap = this.NUMB;
        this.nBoardPerLine = CCanvas.width / 90;
        this.defX = (CCanvas.width - this.nBoardPerLine * 90 >> 1) + 40;
        for (int i = 0; i < RoomListScr.nMap; ++i) {
            RoomListScr._iconX[i] = i * RoomListScr.rangeSplit;
        }
        RoomListScr._centerIX = RoomListScr.w >> 1;
        RoomListScr.curMapIndex = RoomListScr.nMap / 2;
        this.dis = (RoomListScr._centerIX - RoomListScr._iconX[RoomListScr.curMapIndex]) / 2;
        RoomListScr.isMoveMenu = true;
        if (RoomListScr.cloudX == null) {
            RoomListScr.cloudType = new int[]{0, 1, 1};
            RoomListScr.cloudX = new int[]{-50, 100, 190};
            RoomListScr.cloudY = new int[]{-20, 0, 130};
        }
        this.center = new Command(Language.select(), new IAction() {
            @Override
            public void perform() {
                doSelect();
            }
        });
        this.right = new Command(Language.close(), new IAction() {
            @Override
            public void perform() {
                CCanvas.prepareScr.show();
            }
        });
        this.dis = CCanvas.hieght - (5 + RoomListScr.cmdH);
        this.numH = this.NUMB / this.nBoardPerLine;
        if (this.NUMB % this.nBoardPerLine != 0) {
            ++this.numH;
        }
        int y = this.numH * 57 + 40;
        this.cmyLim = y - this.dis;
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }
        this.setCamY();
    }

    @Override
    public void show() {
        this.init();
        super.show();
    }

    protected void doSelect() {
        CCanvas.startWaitDlg(Language.starting());
        int tem = this.isBoss ? (MM.NUM_MAP - PrepareScr.mapBossID.length) : 0;
        if (RoomListScr.curMapIndex >= this.NUMB) {
            return;
        }
        byte id = (byte) (RoomListScr.curMapIndex + tem);
        if (id == -1) {
            return;
        }
        GameService.gI().mapSelect(id);
        CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 11);
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        this.paintRoomList(g);
        super.paint(g);
    }

    public static void paintMapBar(int y, mGraphics g) {
    }

    private void paintRoomList(mGraphics g) {
        g.translate(0, -this.cmy);
        int tem = this.isBoss ? (MM.NUM_MAP - PrepareScr.mapBossID.length) : 0;
        for (int i = 0; i < this.NUMB; ++i) {
            int xP = i % this.nBoardPerLine;
            int yP = i / this.nBoardPerLine;
            int x = xP * 90 + this.defX;
            int y = yP * 57 + 60;
            if (i == RoomListScr.curMapIndex) {
                g.setColor(3684546);
                g.fillRect(x - (RoomListScr.imgMapIW >> 1) - 5, y - (RoomListScr.imgMapIH >> 1) - 5, RoomListScr.imgMapIW + 10, RoomListScr.imgMapIH + 10, false);
            }
            g.setColor(16777215);
            g.fillRect(x - (RoomListScr.imgMapIW >> 1) + 1, y - (RoomListScr.imgMapIH >> 1) + 1, RoomListScr.imgMapIW - 2, RoomListScr.imgMapIH - 2, false);
            try {
                if (PrepareScr.imgMap[i + tem] != null) {
                    g.drawImage(PrepareScr.imgMap[i + tem], x, y, 3, false);
                    g.drawImage(PrepareScr.khungMap, x, y, 3, false);
                }
            } catch (Exception ex) {
            }
        }
        g.translate(-g.getTranslateX(), -g.getTranslateY());
        g.setColor(1133755);
        g.fillRect(0, 0, CCanvas.width, 20, false);
        Font.borderFont.drawString(g, String.valueOf(RoomListScr.curMapIndex + 1) + ". " + MM.mapName[RoomListScr.curMapIndex + tem], RoomListScr.w >> 1, 2, 2);
    }

    public void setCamY() {
        int yP = RoomListScr.curMapIndex / this.nBoardPerLine;
        int Y = yP * 57 + 60 - (CCanvas.hieght / 2 - RoomListScr.cmdH);
        this.cmtoY = Y;
        if (this.cmtoY > this.cmyLim) {
            this.cmtoY = this.cmyLim;
        }
        if (this.cmtoY < 0) {
            this.cmtoY = 0;
        }
        if (RoomListScr.curMapIndex == this.NUMB - 1 || RoomListScr.curMapIndex == 0) {
            this.cmy = this.cmtoY;
        }
    }

    @Override
    public void onPointerPressed(int xPress, int yPress, int index) {
        super.onPointerPressed(xPress, yPress, index);
    }

    @Override
    public void onPointerReleased(int xReleased, int yReleased, int index) {
        super.onPointerReleased(xReleased, yReleased, index);
        this.trans = false;
        int b = RoomListScr.ITEM_HEIGHT + 5;
        int aa = (this.cmtoY + yReleased - b) / 57 * this.nBoardPerLine + (xReleased - 10) / 90;
        if (!MainGame.touchDrag) {
            if (aa == RoomListScr.curMapIndex) {
                if (this.center != null) {
                    if (CCanvas.isDoubleClick) {
                        this.center.action.perform();
                    }
                } else if (this.left != null && CCanvas.isDoubleClick) {
                    this.left.action.perform();
                }
            }
            RoomListScr.curMapIndex = aa;
            if (RoomListScr.curMapIndex < 0) {
                RoomListScr.curMapIndex = 0;
            }
            if (RoomListScr.curMapIndex >= this.NUMB) {
                RoomListScr.curMapIndex = this.NUMB - 1;
            }
        }
    }

    @Override
    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
        if (!this.trans) {
            this.pa = this.cmy;
            this.trans = true;
        }
        if (!CCanvas.isPc()) {
            this.speed = 3;
        }
        this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag) * this.speed;
        if (this.cmtoY < 0) {
            this.cmtoY = 0;
        }
        if (this.cmtoY > this.cmyLim) {
            this.cmtoY = this.cmyLim;
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void mainLoop() {
        super.mainLoop();
        this.moveCamera();
    }

    static {
        RoomListScr.imgMap = GameScr.imgMap;
        RoomListScr.imgCurPos = GameScr.imgCurPos;
        RoomListScr.imgSmallCloud = GameScr.imgSmallCloud;
        RoomListScr.imgArrowRed = GameScr.imgArrowRed;
        RoomListScr.nMap = MM.NUM_MAP;
        RoomListScr.curMapIndex = 0;
        RoomListScr._iconX = new int[MM.NUM_MAP];
        RoomListScr.rangeSplit = 100;
        RoomListScr.imgMapIW = 80;
        RoomListScr.imgMapIH = 50;
        RoomListScr.isMoveMenu = false;
        RoomListScr.mapX = 0;
        RoomListScr.mapY = 0;
        RoomListScr.k = 0;
        RoomListScr.roundCamera = 80;
    }
}
