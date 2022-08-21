package CLib;

import com.badlogic.gdx.graphics.Pixmap;

public class ManagerTile {

    public static final byte[] totalTile;
    public static mImage[] tileBig;
    public static mImage[] tileMapLogin;
    public static Pixmap[] miniTile;
    public static Pixmap[] bigTile;

    public static void loadTileBig(int id) {
        freeBigTile(id);
        ManagerTile.tileBig = new mImage[ManagerTile.totalTile[id]];
        ManagerTile.miniTile = new Pixmap[ManagerTile.totalTile[id]];
        for (int i = 0; i < ManagerTile.tileBig.length; ++i) {
            String path = (i < 9) ? ("tile" + id + "_0") : ("tile" + id + "_");
            mImage img = mImage.createImage("/Tile/tile" + id + "/" + path + (i + 1) + ".png");
            ManagerTile.tileBig[i] = img;
        }
        for (int j = 0; j < ManagerTile.miniTile.length; ++j) {
            String path = (j < 9) ? ("tile_small" + id + "_0") : ("tile_small" + id + "_");
            ManagerTile.miniTile[j] = mSystem.createPixmap("/Tile/tile_small" + id + "/" + path + (j + 1) + ".png");
        }
    }

    public static void loadTileBigLogin(int id) {
        ManagerTile.tileMapLogin = new mImage[ManagerTile.totalTile[id]];
        for (int i = 0; i < ManagerTile.tileMapLogin.length; ++i) {
            String path = (i < 9) ? ("tile" + id + "_0") : ("tile" + id + "_");
            mImage img = mImage.createImage("/Tile/tile" + id + "/" + path + (i + 1) + ".png");
            ManagerTile.tileMapLogin[i] = img;
        }
    }

    public static void freeBigTile(int id) {
        try {
            for (int i = 0; i < ManagerTile.tileBig.length; ++i) {
                if (ManagerTile.tileBig[i] != null && ManagerTile.tileBig[i].image != null) {
                    ManagerTile.tileBig[i].image.texture.dispose();
                    ManagerTile.tileBig[i].image = null;
                    ManagerTile.tileBig[i] = null;
                }
            }
            for (int i = 0; i < ManagerTile.miniTile.length; ++i) {
                if (ManagerTile.miniTile[i] != null) {
                    ManagerTile.miniTile[i].dispose();
                }
            }
        } catch (Exception ex) {
        }
        System.gc();
    }

    public static void freeBigTile1(int id) {
        try {
            for (int i = 0; i < ManagerTile.bigTile.length; ++i) {
                if (ManagerTile.bigTile[i] != null) {
                    ManagerTile.bigTile[i].dispose();
                }
            }
            for (int i = 0; i < ManagerTile.miniTile.length; ++i) {
                if (ManagerTile.miniTile[i] != null) {
                    ManagerTile.miniTile[i].dispose();
                }
            }
        } catch (Exception ex) {
        }
        System.gc();
    }

    static {
        totalTile = new byte[]{73, 60, 57, 64, 47, 43, 66, 61, 52, 51, 61, 62, 67, 69, 59};
    }
}
