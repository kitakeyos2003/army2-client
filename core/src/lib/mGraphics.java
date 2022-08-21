package lib;

import CLib.mImage;

public class mGraphics {

    public mGraphics g;
    public static int zoomLevel;
    public static final int HCENTER = 1;
    public static final int VCENTER = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;
    public static final int BASELINE = 64;
    public static final int SOLID = 0;
    public static final int DOTTED = 1;

    public mGraphics(mGraphics g) {
        this.g = g;
    }

    public mGraphics() {
    }

    public void drawImage(mImage arg0, int x, int y, int arg3) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        this.g.drawImage(arg0, x, y, arg3);
    }

    public void drawImage(mImage arg0, float x, float y, int arg3) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        this.g.drawImage(arg0, (int) x, (int) y, arg3);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        x1 *= mGraphics.zoomLevel;
        y1 *= mGraphics.zoomLevel;
        x2 *= mGraphics.zoomLevel;
        y2 *= mGraphics.zoomLevel;
        this.g.drawLine(x1, y1, x2, y2);
    }

    public void fillRect(int x, int y, int w, int h, int color, int alpha) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        this.g.setColor(color);
        this.g.fillRect(x, y, w, h);
    }

    public void drawRect(int x, int y, int w, int h) {
        this.fillRect(x, y, 1, h);
        this.fillRect(x + w, y, 1, h);
        this.fillRect(x, y, w, 1);
        this.fillRect(x, y + h, w + 1, 1);
    }

    public void drawRegion(mImage arg0, int x0, int y0, int w0, int h0, int arg5, int x, int y, int arg8) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        x0 *= mGraphics.zoomLevel;
        y0 *= mGraphics.zoomLevel;
        w0 *= mGraphics.zoomLevel;
        h0 *= mGraphics.zoomLevel;
        this.g.drawRegion(arg0, x0, y0, w0, h0, arg5, x, y, arg8);
    }

    public void drawRoundRect(int x, int y, int w, int h, int a, int b) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        a *= mGraphics.zoomLevel;
        b *= mGraphics.zoomLevel;
        this.g.drawRoundRect(x, y, w, h, a, b);
    }

    public void fillTrans(mImage imgTrans, int x, int y, int w, int h) {
    }

    public void drawString(String arg0, int x, int y, int arg3) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        this.g.drawString(arg0, x, y, arg3);
    }

    public static int blendColor(float level, int color, int colorBlend) {
        float R = (float) (colorBlend >> 16 & 0xFF);
        float B = (float) (colorBlend >> 8 & 0xFF);
        float G = (float) (colorBlend & 0xFF);
        int pixel = color;
        float r = (R + (pixel >> 16 & 0xFF)) * level + (pixel >> 16 & 0xFF);
        float g = (B + (pixel >> 8 & 0xFF)) * level + (pixel >> 8 & 0xFF);
        float b = (G + (pixel >> 0 & 0xFF)) * level + (pixel >> 0 & 0xFF);
        if (r > 255.0f) {
            r = 255.0f;
        }
        if (r < 0.0f) {
            r = 0.0f;
        }
        if (g > 255.0f) {
            g = 255.0f;
        }
        if (g < 0.0f) {
            g = 0.0f;
        }
        if (b < 0.0f) {
            b = 0.0f;
        }
        if (b > 255.0f) {
            b = 255.0f;
        }
        pixel = (0xFF000000 | (int) r << 16 | (int) g << 8 | ((int) b & 0xFF));
        return pixel;
    }

    public static mImage blend(mImage img, float level, int color) {
        return new mImage();
    }

    public void fillRect(int x, int y, int w, int h) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        this.g.fillRect(x, y, w, h);
    }

    public void fillRoundRect(int x, int y, int w, int h, int a, int b) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        a *= mGraphics.zoomLevel;
        b *= mGraphics.zoomLevel;
        this.g.fillRoundRect(x, y, w, h, a, b);
    }

    public int getTranslateX() {
        return this.g.getTranslateX() / mGraphics.zoomLevel;
    }

    public int getTranslateY() {
        return this.g.getTranslateY() / mGraphics.zoomLevel;
    }

    public void setClip(int x, int y, int w, int h) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        this.g.setClip(x, y, w, h);
    }

    public void setColor(int RGB) {
        this.g.setColor(RGB);
    }

    public void setColor(int R, int G, int B) {
        this.g.setColor(R, G, B);
    }

    public void translate(int x, int y) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        this.g.translate(x, y);
    }

    public static int getImageWidth(mImage image) {
        return mImage.getImageWidth(image.image) / mGraphics.zoomLevel;
    }

    public static int getImageHeight(mImage image) {
        return mImage.getImageHeight(image.image) / mGraphics.zoomLevel;
    }

    public static int getRealImageWidth(mImage image) {
        return mImage.getImageWidth(image.image);
    }

    public static int getRealImageHeight(mImage image) {
        return mImage.getImageHeight(image.image);
    }

    public void drawRGB(int[] data, int i, int width, int x, int y, int width2, int height, boolean b) {
    }

    public int getClipX() {
        return 0;
    }

    public int getClipY() {
        return 0;
    }

    public int getClipWidth() {
        return 0;
    }

    public int getClipHeight() {
        return 0;
    }

    public void fillArc(int i, int j, int wE, int wE2, int k, int l) {
    }

    public void fillTriangle(int x, int i, int j, int k, int l, int m) {
    }

    static {
        mGraphics.zoomLevel = 1;
    }
}
