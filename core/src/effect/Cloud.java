package effect;

import screen.PrepareScr;
import CLib.mGraphics;
import map.Background;
import coreLG.CCanvas;
import CLib.mImage;

public class Cloud {

    public static mImage imgSun;
    public static mImage[] imgCloud;
    private static int[] yCloud;
    private static int[] xCloud;
    private static int[] dxCloud;
    private static int xB1;
    private static int xB2;

    public static void updateCloud() {
        for (int i = 0; i < 2; ++i) {
            int[] xCloud = Cloud.xCloud;
            int n = i;
            int[] array = xCloud;
            int n4 = n;
            array[n4] += Cloud.dxCloud[i];
            if (Cloud.xCloud[i] > CCanvas.width) {
                Cloud.xCloud[i] = -Cloud.imgCloud[i].image.getWidth();
            }
        }
        int[] xCloud2 = Cloud.xCloud;
        int n2 = 3;
        int[] array2 = xCloud2;
        int n5 = n2;
        array2[n5] += Cloud.dxCloud[3];
        if (Cloud.xCloud[2] > CCanvas.width) {
            Cloud.xCloud[2] = -Cloud.imgCloud[2].image.getWidth();
        }
        if (Cloud.xCloud[3] > CCanvas.width) {
            Cloud.xCloud[3] = -Cloud.imgCloud[2].image.getWidth();
        }
        if (CCanvas.gameTick % 2 == 0) {
            int[] xCloud3 = Cloud.xCloud;
            int n3 = 2;
            int[] array3 = xCloud3;
            int n6 = n3;
            array3[n6] += Cloud.dxCloud[2];
        }
    }

    public static void balloonUpdate() {
        if (CCanvas.gameTick % 2 == 0) {
            ++Cloud.xB2;
        }
        if (CCanvas.gameTick % 3 == 0) {
            ++Cloud.xB1;
        }
        if (Cloud.xB1 > CCanvas.width) {
            Cloud.xB1 = -Background.balloon.image.getWidth();
        }
        if (Cloud.xB2 > CCanvas.width) {
            Cloud.xB2 = -Background.balloon.image.getWidth();
        }
    }

    public static void paintCloud(mGraphics g) {
        g.drawImage(Cloud.imgSun, 30, 40, 0, false);
        for (int i = 2; i >= 0; --i) {
            g.drawImage(Cloud.imgCloud[i], Cloud.xCloud[i], Cloud.yCloud[i], 0, false);
        }
    }

    public static void paintBalloonWithCloud(mGraphics g) {
        g.drawImage(Background.sun, CCanvas.width - 20, 20, mGraphics.TOP | mGraphics.RIGHT, false);
        g.drawImage(Cloud.imgCloud[2], Cloud.xCloud[2], 100, 0, false);
        g.drawImage(Cloud.imgCloud[2], Cloud.xCloud[3], 20, 0, false);
        g.drawImage(Cloud.imgCloud[1], Cloud.xCloud[1], Cloud.yCloud[1], 0, false);
        g.drawImage(Background.balloon, Cloud.xB1, 20, 0, false);
        g.drawImage(Background.balloon, Cloud.xB2, 50, 0, false);
        g.drawImage(Cloud.imgCloud[0], Cloud.xCloud[0], Cloud.yCloud[0], 0, false);
    }

    public static void paintSimpleClound(int CloundType, int x, int y, mGraphics g) {
        g.drawImage(Cloud.imgCloud[CloundType], x, y, 0, false);
    }

    static {
        Cloud.xB1 = 60;
        Cloud.xB2 = 170;
        Cloud.xCloud = new int[]{0, CCanvas.hw, 20, CCanvas.width / 2 + 10};
        Cloud.yCloud = new int[]{30, 80, 40, 0};
        Cloud.dxCloud = new int[]{2, 1, 1, 1};
        try {
            Cloud.imgSun = PrepareScr.imgSun;
            Cloud.imgCloud = PrepareScr.imgCloud;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
