package screen;

import model.Font;
import model.RoomInfo;
import CLib.mGraphics;
import effect.Cloud;
import network.GameService;
import model.BoardInfo;
import network.Command;
import model.IAction;
import model.Language;
import coreLG.CCanvas;
import java.util.Vector;
import CLib.mImage;

public class BoardListScr extends CScreen {

    public static mImage imgMode;
    public static mImage lock;
    public static String boardName;
    int nBoardPerLine;
    int defX;
    public static Vector boardList;
    public static int selected;
    public static int cmtoY;
    public static int cmy;
    public static int cmdy;
    public static int cmvy;
    public static int cmyLim;
    public byte roomID;
    int xList;
    int yList;
    static int boxX;
    static int boxY;
    static int boxW;
    static int boxH;
    int boxMaxW;
    int boxMaxH;
    int boxSpeed;
    static boolean isOpenBox;
    static boolean isAllowPaintBoard;
    static Vector roomInfo;
    public static int currRoom;
    int pa;
    boolean trans;

    @Override
    public void moveCamera() {
        if (BoardListScr.cmy != BoardListScr.cmtoY) {
            BoardListScr.cmvy = BoardListScr.cmtoY - BoardListScr.cmy << 2;
            BoardListScr.cmdy += BoardListScr.cmvy;
            BoardListScr.cmy += BoardListScr.cmdy >> 4;
            BoardListScr.cmdy &= 0xF;
        }
    }

    public BoardListScr() {
        this.boxSpeed = 4;
        this.pa = 0;
        this.trans = false;
        this.nBoardPerLine = CCanvas.width / 55;
        this.defX = CCanvas.width - this.nBoardPerLine * 55 >> 1;
        this.center = new Command(Language.join(), new IAction() {
            @Override
            public void perform() {
                if (BoardListScr.boardList.size() == 0) {
                    return;
                }
                doJoinBoard();
            }
        });
        this.left = new Command(Language.update(), new IAction() {
            @Override
            public void perform() {
                doUpdate();
            }
        });
        this.right = new Command(Language.close(), new IAction() {
            @Override
            public void perform() {
                doExitBoardList();
                BoardListScr.isOpenBox = false;
            }
        });
        this.boxMaxW = ((CScreen.w - 20 > 170) ? (CScreen.w - 20) : 170);
        this.boxMaxW = CScreen.w;
        this.boxMaxH = ((CScreen.h - 120 > 180) ? (CScreen.h - 120) : 180);
        BoardListScr.boxW = this.boxMaxW;
        this.nameCScreen = "BoardListScr screen!";
    }

    public static void setBoardName(int id, String name) {
        if (name != null && !name.equals("")) {
            BoardListScr.boardName = String.valueOf(Language.area()) + ": " + name;
        } else {
            BoardListScr.boardName = String.valueOf(Language.area()) + ": " + id;
        }
    }

    protected void doJoinBoard() {
        BoardInfo selectedBoard = (BoardInfo) BoardListScr.boardList.elementAt(BoardListScr.selected);
        setBoardName(selectedBoard.boardID, selectedBoard.name);
        if (selectedBoard.isPass) {
            CCanvas.inputDlg.show();
        } else {
            PrepareScr.currentRoom = this.roomID;
            GameService.gI().joinBoard(this.roomID, selectedBoard.boardID, "");
            CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 13);
        }
    }

    private void doExitBoardList() {
    }

    private void doUpdate() {
        CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 14);
        GameService.gI().requestBoardList(this.roomID);
    }

    @Override
    public void show() {
        super.show();
        this.xList = CScreen.w;
        BoardListScr.isOpenBox = true;
        BoardListScr.selected = 0;
        BoardListScr.cmtoY = BoardListScr.selected * BoardListScr.ITEM_HEIGHT - (CCanvas.hh - 2 * BoardListScr.ITEM_HEIGHT);
        if (BoardListScr.cmtoY > BoardListScr.cmyLim) {
            BoardListScr.cmtoY = BoardListScr.cmyLim;
        }
        if (BoardListScr.cmtoY < 0) {
            BoardListScr.cmtoY = 0;
        }
        if (BoardListScr.selected == BoardListScr.boardList.size() - 1 || BoardListScr.selected == 0) {
            BoardListScr.cmy = BoardListScr.cmtoY;
        }
    }

    public static void activeBox() {
        BoardListScr.boxW = 0;
        BoardListScr.boxH = 0;
        BoardListScr.isOpenBox = true;
        BoardListScr.isAllowPaintBoard = false;
    }

    private void updateBox() {
        if (BoardListScr.isOpenBox) {
            if (BoardListScr.boxW < this.boxMaxW) {
                BoardListScr.boxW += Math.max(this.boxMaxW / this.boxSpeed, 1);
            }
            if (BoardListScr.boxH < this.boxMaxH) {
                BoardListScr.boxH += Math.max(this.boxMaxH / this.boxSpeed, 1);
            } else {
                BoardListScr.isAllowPaintBoard = true;
            }
            if (BoardListScr.boxX != CScreen.w / 2) {
                BoardListScr.boxX += CScreen.w / 2 - BoardListScr.boxX;
            }
            if (BoardListScr.boxY != CScreen.h / 2) {
                BoardListScr.boxY += CScreen.h / 2 - BoardListScr.boxY;
            }
        } else {
            if (BoardListScr.boxW > 0) {
                Math.max(BoardListScr.boxW -= BoardListScr.boxW / this.boxSpeed, 1);
            }
            if (BoardListScr.boxH > 0) {
                Math.max(BoardListScr.boxH -= BoardListScr.boxH / this.boxSpeed, 1);
            }
        }
    }

    @Override
    public void update() {
        Cloud.updateCloud();
        this.moveCamera();
        if (BoardListScr.isOpenBox && this.xList > 0) {
            this.xList -= Math.max(this.xList / 2, 1);
        }
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        Cloud.paintCloud(g);
        for (int i = 0; i <= CCanvas.width; i += 32) {
            g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
        }
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        BoardListScr.currRoom = ((RoomInfo) BoardListScr.roomInfo.elementAt(CCanvas.roomListScr2.selected)).id;
        Font.bigFont.drawString(g, String.valueOf(Language.ROOM()) + " " + this.roomID, 10, 3, 0);
        g.setColor(2378093);
        g.fillRect(0, 25, CCanvas.width, BoardListScr.ITEM_HEIGHT, false);
        Font.normalYFont.drawString(g, Language.battleArea(), 10, 28, 0);
        if (BoardListScr.isAllowPaintBoard) {
            paintRichList(this.xList, 0, g);
        }
        super.paint(g);
    }

    public static void paintRichList(int x, int y, mGraphics g) {
        int tam = CCanvas.isTouch ? 40 : BoardListScr.ITEM_HEIGHT;
        g.translate(x, y + tam + (CCanvas.isTouch ? 14 : 25));
        g.setClip(x, y + (CCanvas.isTouch ? -10 : 1), CCanvas.width, CCanvas.hieght);
        g.translate(x, y - BoardListScr.cmy);
        int _y = y - tam;
        for (int i = 0; i < BoardListScr.boardList.size(); ++i) {
            _y += tam;
            if (_y >= BoardListScr.cmy - tam && _y <= BoardListScr.cmy + CCanvas.hieght) {
                if (i == BoardListScr.selected) {
                    g.setColor(16767817);
                    g.fillRect(x, _y - (CCanvas.isTouch ? 10 : 0), CCanvas.width, tam, false);
                }
                BoardInfo boardInfo = (BoardInfo) BoardListScr.boardList.elementAt(i);
                String name = String.valueOf(Language.area()) + " " + boardInfo.boardID;
                if (!boardInfo.name.equals("")) {
                    name = boardInfo.name;
                }
                g.drawRegion(BoardListScr.imgMode, 0, boardInfo.mode * 17, 18, 17, 0, x + 8, _y + 1, 0, false);
                Font.borderFont.drawString(g, name, x + 30, _y + 2, 0);
                Font.borderFont.drawString(g, String.valueOf(boardInfo.nPlayer) + "/" + boardInfo.maxPlayer, x + CCanvas.width - 3, _y + 2, 1);
                Font.borderFont.drawString(g, String.valueOf(boardInfo.money) + Language.xu(), x + CCanvas.width - 30, _y + 2, 1);
                BoardInfo selectedBoard = (BoardInfo) BoardListScr.boardList.elementAt(i);
                if (selectedBoard.isPass) {
                    g.drawImage(BoardListScr.lock, x + CCanvas.width - 30 - Font.borderFont.getWidth(String.valueOf(boardInfo.money) + Language.xu()) - 5, _y + 1, 24, false);
                }
            }
        }
        g.translate(-g.getTranslateX(), -g.getTranslateY());
    }

    public void input() {
    }

    public void setBoardList(Vector boardList) {
        BoardListScr.roomInfo = CCanvas.roomListScr2.roomList;
        BoardListScr.boardList = boardList;
        int tam = CCanvas.isTouch ? 40 : BoardListScr.ITEM_HEIGHT;
        int aa = CCanvas.isTouch ? 5 : 0;
        BoardListScr.cmyLim = BoardListScr.boardList.size() * tam - (CCanvas.hieght - BoardListScr.ITEM_HEIGHT * 4 - aa);
    }

    static {
        BoardListScr.imgMode = GameScr.imgMode;
        BoardListScr.lock = GameScr.lock;
        BoardListScr.isAllowPaintBoard = true;
    }
}
