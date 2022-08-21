package map;

import CLib.mGraphics;
import player.CPlayer;
import model.TimeBomb;
import player.PM;
import effect.Explosion;
import java.util.Hashtable;
import network.GameService;
import screen.MenuScr;
import coreLG.CCanvas;
import CLib.mImage;
import CLib.Image;
import model.IAction2;
import screen.GameScr;
import model.CRes;
import java.util.Vector;

public class MM {

    public static int mapWidth;
    public static int mapHeight;
    public static Vector<CMap> maps;
    public static Vector<MapFile> mapFiles;
    int index;
    public static boolean isHaveWaterOrGlass;
    public static final byte WATER = 0;
    public static final byte GLASS = 1;
    public static final byte GLASS_2 = 2;
    public static byte curWaterType;
    public static int mapID;
    static Background background;
    public static byte NUM_MAP;
    public static String[] mapName;
    public static String[] mapFileName;
    public static final byte WATERBUM_SMALL_THING = 0;
    public static final byte WATERBUM_NORMAL_THING = 1;
    public static final byte WATERBUM_BIG_THING = 2;
    public static byte[] fullData;
    int count1;
    int count2;
    public static Vector<MapImage> mapImages;
    public static Vector<Object> vHoleInfo;
    public static short[] undestroyTile;

    public MM() {
        this.index = 0;
        this.count1 = 0;
        this.count2 = 0;
    }

    public void createMap(int mapID) {
        MM.isHaveWaterOrGlass = false;
        CRes.out("=====================> Create map = " + mapID);
        this.loadMapFile(MM.mapID = mapID);
    }

    public void createBackGround() {
        Background.waterY = MM.mapHeight - (Background.water.image.getHeight() + Background.inWater.image.getHeight()) + 37;
        CRes.out(String.valueOf(this.getClass().getName()) + " createBackGround have mapID = " + MM.mapID);
        if (GameScr.curGRAPHIC_LEVEL != 2) {
            int i = 0;
            while (i < MM.mapFiles.size()) {
                MapFile mf = MM.mapFiles.elementAt(i);
                if (MM.mapID == mf.mapID) {
                    MM.background = new Background((byte) mf.backGroundID);
                    if (mf.yBackGround != -1) {
                        MM.background.yBackGr = mf.yBackGround;
                    }
                    if (mf.yCloud != -1) {
                        MM.background.yCloud = mf.yCloud - 100;
                    }
                    if (mf.yWater != -1) {
                        Background.waterY += mf.yWater;
                    }
                    if (mf.water_class != -1) {
                        MM.curWaterType = (byte) mf.water_class;
                        MM.isHaveWaterOrGlass = true;
                        break;
                    }
                    break;
                } else {
                    ++i;
                }
            }
        }
    }

    public void clearBackGround() {
        CRes.out("===================================> OnClear BG");
        MM.mapFiles.removeAllElements();
    }

    public byte[] getDataByID(int id) {
        byte[] data = null;
        for (int i = 0; i < MM.mapFiles.size(); ++i) {
            MapFile mf = MM.mapFiles.elementAt(i);
            if (mf.mapID == id) {
                data = mf.data;
            }
        }
        return data;
    }

    public boolean isTileDestroy(int id) {
        for (int i = 0; i < MM.undestroyTile.length; ++i) {
            if (id == MM.undestroyTile[i]) {
                return true;
            }
        }
        return false;
    }

    public void addImage(final int id, byte[] dataRaw, int len) {
        mImage.createImage(dataRaw, 0, len, new IAction2() {
            @Override
            public void perform(Object object) {
                Image imgTem = (Image) object;
                for (int i = 0; i < MM.maps.size(); ++i) {
                    CMap cmap = MM.maps.elementAt(i);
                    if (cmap.id == id) {
                        cmap.createRGB(new mImage(imgTem));
                    }
                }
                ++count2;
                if (count2 == count1) {
                    CCanvas.endDlg();
                    if (MenuScr.isTraining) {
                        MenuScr.isTraining = false;
                        GameService.gI().training((byte) 0);
                    }
                }
                MM.mapImages.addElement(new MapImage(new mImage(imgTem), id));
                int sizeLimit = (GameScr.curGRAPHIC_LEVEL == 2) ? 5 : 30;
                if (MM.mapImages.size() >= sizeLimit) {
                    while (MM.mapImages.size() > sizeLimit) {
                        MM.mapImages.removeElementAt(0);
                    }
                }
                CRes.out("=====================> MapImage " + MM.mapImages.size());
            }
        });
    }

    public void addImage(int id, mImage image) {
        for (int i = 0; i < MM.maps.size(); ++i) {
            CMap cmap = MM.maps.elementAt(i);
            if (cmap.id == id) {
                cmap.createRGB(image);
            }
        }
        ++this.count2;
        if (this.count2 == this.count1) {
            CCanvas.endDlg();
            if (MenuScr.isTraining) {
                MenuScr.isTraining = false;
                GameService.gI().training((byte) 0);
            }
        }
        MM.mapImages.addElement(new MapImage(image, id));
        int sizeLimit = (GameScr.curGRAPHIC_LEVEL == 2) ? 5 : 30;
        if (MM.mapImages.size() >= sizeLimit) {
            while (MM.mapImages.size() > sizeLimit) {
                MM.mapImages.removeElementAt(0);
            }
        }
    }

    public boolean containsImage(int id) {
        for (int i = 0; i < MM.mapImages.size(); ++i) {
            MapImage mI = MM.mapImages.elementAt(i);
            if (mI.id == id) {
                return true;
            }
        }
        return false;
    }

    public mImage getImage(int id) {
        for (int i = 0; i < MM.mapImages.size(); ++i) {
            MapImage mI = MM.mapImages.elementAt(i);
            if (mI.id == id) {
                return mI.image;
            }
        }
        return null;
    }

    public static boolean isExistId(int id) {
        for (int i = 0; i < MM.maps.size(); ++i) {
            CMap cmap = MM.maps.elementAt(i);
            if (cmap.aMap != null && cmap.id == id) {
                return true;
            }
        }
        return false;
    }

    public static int[] rgbMap(int id) {
        for (int i = 0; i < MM.maps.size(); ++i) {
            CMap array = MM.maps.elementAt(i);
            if (array.id == id) {
                return array.aMap;
            }
        }
        return null;
    }

    private void loadMapFile(int id) {
        byte[] data = this.getDataByID(id);
        if (data == null) {
            return;
        }
        this.count1 = 0;
        this.count2 = 0;
        int seek = 0;
        MM.mapWidth = CRes.getShort(seek, data);
        seek += 2;
        MM.mapHeight = CRes.getShort(seek, data);
        byte[] array = data;
        seek += 2;
        int nLand = array[seek];
        int[] landID = new int[nLand];
        ++seek;
        Hashtable<String, MapImage> h = new Hashtable<>();
        CMap cmap = null;
        for (int i = 0; i < nLand; ++i) {
            landID[i] = data[seek];
            if (this.containsImage(landID[i])) {
                cmap = new CMap(landID[i], CRes.getShort(seek + 1, data), CRes.getShort(seek + 3, data), this.getImage(landID[i]), !this.isTileDestroy(landID[i]));
                cmap.index = i;
                this.addMap(cmap);
            } else {
                cmap = new CMap(landID[i], CRes.getShort(seek + 1, data), CRes.getShort(seek + 3, data), null, !this.isTileDestroy(landID[i]));
                cmap.index = i;
                this.addMap(cmap);
                if (!h.containsKey(new StringBuilder(String.valueOf(landID[i])).toString())) {
                    GameService.gI().getMaterialIcon((byte) 2, landID[i], -1);
                }
                h.put(new StringBuilder(String.valueOf(landID[i])).toString(), new MapImage(null, landID[i]));
            }
            seek += 5;
        }
        data = null;
        if (h != null) {
            this.count1 = h.size();
        }
        if (MenuScr.isTraining) {
            MenuScr.isTraining = false;
            GameService.gI().training((byte) 0);
        }
        CRes.out("=====================> MapImage " + MM.mapImages.size());
        if (this.count1 == 0) {
            CCanvas.endDlg();
        }
    }

    public void addMap(CMap m) {
        MM.maps.addElement(m);
    }

    public CMap getMap(int i) {
        return MM.maps.elementAt(i);
    }

    public boolean isLand(int x, int y) {
        for (int i = 0; i < MM.maps.size(); ++i) {
            CMap curT = MM.maps.elementAt(i);
            if (CRes.inRect(x, y, curT.x, curT.y, curT.width, curT.height) && CRes.isLand(curT.getPixel(x - curT.x, y - curT.y))) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkWaterBum(int x, int y, byte bumType) {
        if (GameScr.curGRAPHIC_LEVEL == 2) {
            if (GameScr.exs.size() > 3) {
                return true;
            }
            if (GameScr.curGRAPHIC_LEVEL == 1 && GameScr.exs.size() > 6) {
                return true;
            }
        }
        if (MM.curWaterType == 0) {
            if (y >= Background.waterY + 48) {
                switch (bumType) {
                    case 0: {
                        new Explosion(x, y, (byte) 2);
                        return true;
                    }
                    case 1: {
                        new Explosion(x, y - 5, (byte) 2);
                        new Explosion(x, y + 6, (byte) 2);
                        return true;
                    }
                    case 2: {
                        new Explosion(x, y - 10, (byte) 2);
                        new Explosion(x + 12, y, (byte) 2);
                        new Explosion(x - 12, y, (byte) 2);
                        return true;
                    }
                }
            }
        } else if (MM.curWaterType == 1) {
            if (y >= Background.glassY) {
                switch (bumType) {
                    case 0: {
                        GameScr.sm.addRock(x, y, CRes.random(-4, 4), CRes.random(-11, -9), (byte) 6);
                        return true;
                    }
                    case 1: {
                        GameScr.sm.addRock(x, y, CRes.random(-4, 4), CRes.random(-14, -12), (byte) 6);
                        GameScr.sm.addRock(x, y, CRes.random(-6, 6), CRes.random(-11, -9), (byte) 6);
                        return true;
                    }
                    case 2: {
                        GameScr.sm.addRock(x, y, CRes.random(-4, 4), CRes.random(-13, -11), (byte) 6);
                        GameScr.sm.addRock(x, y, CRes.random(-6, 6), CRes.random(-11, -9), (byte) 6);
                        GameScr.sm.addRock(x, y, CRes.random(-2, 2), CRes.random(-17, -15), (byte) 6);
                        return true;
                    }
                }
            }
        } else if (MM.curWaterType == 2 && y >= Background.glassY) {
            switch (bumType) {
                case 0: {
                    GameScr.sm.addRock(x, y, CRes.random(-4, 4), CRes.random(-11, -9), (byte) 14);
                    return true;
                }
                case 1: {
                    GameScr.sm.addRock(x, y, CRes.random(-4, 4), CRes.random(-14, -12), (byte) 14);
                    GameScr.sm.addRock(x, y, CRes.random(-6, 6), CRes.random(-11, -9), (byte) 14);
                    return true;
                }
                case 2: {
                    GameScr.sm.addRock(x, y, CRes.random(-4, 4), CRes.random(-13, -11), (byte) 14);
                    GameScr.sm.addRock(x, y, CRes.random(-6, 6), CRes.random(-11, -9), (byte) 14);
                    GameScr.sm.addRock(x, y, CRes.random(-2, 2), CRes.random(-17, -15), (byte) 14);
                    return true;
                }
            }
        }
        return false;
    }

    public void makeHole(int x, int y, byte bulletType, int indexTest) {
        int holeW = CMap.getHoleW(bulletType);
        int holeH = CMap.getHoleH(bulletType);
        for (int i = 0; i < MM.maps.size(); ++i) {
            CMap curT = MM.maps.elementAt(i);
            if (curT.isDestroy && CRes.isHit(x - holeW / 2, y - holeH / 2, holeW, holeH, curT.x, curT.y, curT.width, curT.height)) {
                MM.maps.elementAt(i).makeHole(x, y, bulletType);
            }
        }
        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null) {
                if (CRes.inRect(PM.p[i].x, PM.p[i].y, x - holeW / 2, y - holeH / 2, holeW, holeH)) {
                    if (PM.p[i].getState() != 5 && PM.p[i].bulletType != 30) {
                        PM.p[i].activeHurt((x > PM.p[i].x) ? 2 : 0);
                    }
                    PM.p[i].isActiveFall = false;
                    PM.p[i].activeFallbyEx = true;
                    PM.p[i].chophepGuiUpdateXY = true;
                }
                if (PM.p[i].gun == 16) {
                    while (!this.isLand(PM.p[i].x, PM.p[i].yPoint)) {
                        CPlayer cPlayer2;
                        CPlayer cPlayer = cPlayer2 = PM.p[i];
                        ++cPlayer2.yPoint;
                        if (PM.p[i].yPoint > MM.mapHeight) {
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < GameScr.timeBombs.size(); ++i) {
            TimeBomb b = GameScr.timeBombs.elementAt(i);
            if (b != null && !b.isFall) {
                b.isFall = true;
            }
        }
    }

    public void update() {
        if (MM.background != null) {
            MM.background.update();
        }
        for (int i = 0; i < MM.maps.size(); ++i) {
            if (MM.maps.elementAt(i) != null) {
                MM.maps.elementAt(i).update();
            }
        }
    }

    public void paint(mGraphics g) {
        if (GameScr.cantSee) {
            return;
        }
        for (int i = 0; i < MM.maps.size(); ++i) {
            if (MM.maps.elementAt(i) != null) {
                MM.maps.elementAt(i).paint(g);
            }
        }
    }

    public void paintBackGround(mGraphics g) {
        if (GameScr.cantSee) {
            return;
        }
        if (MM.background != null) {
            MM.background.paint(g);
        }
    }

    public void paintWater(mGraphics g) {
        if (GameScr.cantSee) {
            return;
        }
        Background.drawWater(MM.curWaterType, g);
    }

    public void onClearMap() {
        MM.maps.removeAllElements();
    }

    static {
        MM.maps = new Vector<CMap>();
        MM.mapFiles = new Vector<MapFile>();
        MM.mapImages = new Vector<MapImage>();
        MM.vHoleInfo = new Vector<Object>();
    }
}
