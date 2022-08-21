package effect;

import screen.GameScr;
import CLib.mGraphics;
import CLib.mImage;

public class Effect {

    private static int[] clips;
    private static mImage s_imgTransparent;
    static int[] s_transparentBuf;
    static mImage s_transparentImg;
    static final int TRANSPARENT_BUF_H = 1;
    static final int BACK_IMAGE_HEIGHT = 1;
    static final boolean DRAW_TransparentRect_USE_DrawRGB = false;

    public static void Flash(mGraphics g, int x, int y, int width, int height, int alpha) {
        int[] data = new int[width * height];
        for (int i = 0; i < data.length; ++i) {
            data[i] = (alpha << 24 | 0xFFFFFF);
        }
        g.drawRGB(data, 0, width, x, y, width, height, true);
    }

    public static void DrawBlurImage(mGraphics g, mImage img, int x, int y, int blurLevel) {
        if (blurLevel == 0) {
            g.drawImage(img, x, y, mGraphics.TOP | mGraphics.LEFT, false);
            return;
        }
        Effect.clips[0] = g.getClipX();
        Effect.clips[1] = g.getClipY();
        Effect.clips[2] = g.getClipWidth();
        Effect.clips[3] = g.getClipHeight();
        int blockSize = blurLevel * 2;
        int width = img.image.getWidth();
        int height = img.image.getHeight();
        int[] imgData = new int[width * height];
        img.getRGB(imgData, 0, width, 0, 0, width, height);
        if (imgData == null) {
            return;
        }
        int x2 = Math.max(x, Effect.clips[0]);
        int y2 = Math.max(y, Effect.clips[1]);
        int width2 = Math.min(x + width, Effect.clips[0] + Effect.clips[2]) - x2;
        int height2 = Math.min(y + height, Effect.clips[1] + Effect.clips[3]) - y2;
        if (width2 <= 0 || height2 <= 0) {
            return;
        }
        g.setClip(x2, y2, width2, height2);
        for (int i = 0; i < height; i += blockSize) {
            for (int j = 0; j < width; j += blockSize) {
                if ((i + blockSize / 2) * width + j + blockSize / 2 <= imgData.length) {
                    g.fillRect(x + j, y + i, blockSize, blockSize, false);
                }
            }
        }
        g.setClip(Effect.clips[0], Effect.clips[1], Effect.clips[2], Effect.clips[3]);
    }

    public static void FillTransparentRect(mGraphics g, int x, int y, int width, int height) {
        Effect.clips[0] = g.getClipX();
        Effect.clips[1] = g.getClipY();
        Effect.clips[2] = g.getClipWidth();
        Effect.clips[3] = g.getClipHeight();
        int x2 = Math.max(x, Effect.clips[0]);
        int y2 = Math.max(y, Effect.clips[1]);
        int width2 = Math.min(x + width, Effect.clips[0] + Effect.clips[2]) - x2;
        int height2 = Math.min(y + height, Effect.clips[1] + Effect.clips[3]) - y2;
        if (width2 <= 0 || height2 <= 0) {
            return;
        }
        g.setClip(x2, y2, width2, height2);
        for (int i = 0; i < width / 120 + 1; ++i) {
            for (int j = 0; j < height / 40 + 1; ++j) {
                g.drawImage(Effect.s_imgTransparent, x + 120 * i, y + 40 * j, 0, false);
            }
        }
        g.setClip(Effect.clips[0], Effect.clips[1], Effect.clips[2], Effect.clips[3]);
    }

    public static void FillTransparentRectRGB(mGraphics g, int x, int y, int width, int height, int color) {
        Effect.clips[0] = g.getClipX();
        Effect.clips[1] = g.getClipY();
        Effect.clips[2] = g.getClipWidth();
        Effect.clips[3] = g.getClipHeight();
        int x2 = Math.max(x, Effect.clips[0]);
        int y2 = Math.max(y, Effect.clips[1]);
        int width2 = Math.min(x + width, Effect.clips[0] + Effect.clips[2]) - x2;
        int height2 = Math.min(y + height, Effect.clips[1] + Effect.clips[3]) - y2;
        if (width2 <= 0 || height2 <= 0) {
            return;
        }
        g.setClip(x2, y2, width2, height2);
        int[] data = new int[width * height];
        g.setClip(Effect.clips[0], Effect.clips[1], Effect.clips[2], Effect.clips[3]);
    }

    private static void fillTransparentRect(mGraphics gra, int x, int y, int width, int height, int r, int g, int b, int transPercent, boolean useDrawRGB) {
        transPercent = 255 * transPercent / 100;
        if (Effect.s_transparentBuf == null || Effect.s_transparentBuf.length / 1 < width) {
            Effect.s_transparentBuf = new int[width * 1];
        }
        for (int len = Effect.s_transparentBuf.length, argb = (transPercent << 24) + (r << 16) + (g << 8) + b, i = 0; i < len && Effect.s_transparentBuf[i] != argb; ++i) {
            Effect.s_transparentBuf[i] = argb;
        }
        if (!useDrawRGB) {
            Effect.s_transparentImg = mImage.createImage(Effect.s_transparentBuf, width, 1);
        }
        for (int j = 0; j < height; ++j) {
            if (useDrawRGB) {
                gra.drawRGB(Effect.s_transparentBuf, 0, width, x, y + j, width, 1, true);
            } else {
                gra.drawRegion(Effect.s_transparentImg, 0, 0, width, 1, 0, x, y + j, 0, false);
            }
        }
    }

    public static void fillTransparentRect(mGraphics g, int x, int y, int width, int height, int color, int transPercent, boolean useDrawRGB) {
        if (width <= 0 || height <= 0) {
            return;
        }
        fillTransparentRect(g, x, y, width, height, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, transPercent, useDrawRGB);
    }

    public static void fillTransparentRect(mGraphics g, int x, int y, int width, int height, int color, int transPercent) {
        fillTransparentRect(g, x, y, width, height, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, transPercent, false);
    }

    public static void transparentRGB(int[] rgbData, int transPercent) {
        transPercent = 255 * transPercent / 100;
        for (int len = rgbData.length, i = 0; i < len; ++i) {
            int n3;
            int n = n3 = i;
            rgbData[n3] &= 0xFFFFFF;
            if (rgbData[i] != 16711935) {
                int n4;
                int n2 = n4 = i;
                rgbData[n4] |= transPercent << 24;
            }
        }
    }

    static {
        Effect.clips = new int[4];
        Effect.s_imgTransparent = GameScr.s_imgTransparent;
    }
}
