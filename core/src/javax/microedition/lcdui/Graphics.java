package javax.microedition.lcdui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Graphics {

    public SpriteBatch g;
    public static int HCENTER;
    public static int VCENTER;
    public static int LEFT;
    public static int RIGHT;
    public static int TOP;
    public static int BOTTOM;
    public static final int TRANS_NONE = 0;
    public static final int TRANS_ROT90 = 5;
    public static final int TRANS_ROT180 = 3;
    public static final int TRANS_ROT270 = 6;
    public static final int TRANS_MIRROR = 2;
    public static final int TRANS_MIRROR_ROT90 = 7;
    public static final int TRANS_MIRROR_ROT180 = 1;
    public static final int TRANS_MIRROR_ROT270 = 4;

    public Graphics(SpriteBatch g) {
        this.g = g;
    }

    static {
        Graphics.HCENTER = 1;
        Graphics.VCENTER = 2;
        Graphics.LEFT = 4;
        Graphics.RIGHT = 8;
        Graphics.TOP = 16;
        Graphics.BOTTOM = 32;
    }
}
