package CLib;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import coreLG.CCanvas;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import java.util.Enumeration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.teamobi.mobiarmy2.MainGame;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class mGraphics {

    public static int zoomLevel;
    public SpriteBatch g;
    private float r;
    private float gl;
    private float b;
    private float a;
    private boolean isTranslate;
    private boolean isClip;
    public int clipX;
    public int clipY;
    public int clipW;
    public int clipH;
    public static final boolean isTrue = true;
    public static final boolean isFalse = false;
    private int clipTX;
    private int clipTY;
    int translateX;
    int translateY;
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
    public static mHashtable cachedTextures;
    public boolean isRorate;
    public int xRotate;
    public int yRotate;
    public float rotation;

    public void translate(int tx, int ty) {
        tx *= mGraphics.zoomLevel;
        ty *= mGraphics.zoomLevel;
        this.translateX += tx;
        this.translateY += ty;
        this.isTranslate = true;
        if (this.translateX == 0 && this.translateY == 0) {
            this.isTranslate = false;
        }
    }

    public void begin() {
        this.g.begin();
    }

    public void end() {
        this.translateX = 0;
        this.translateY = 0;
        this.isTranslate = false;
        this.isClip = false;
        this.g.end();
    }

    public int getTranslateX() {
        return this.translateX / mGraphics.zoomLevel;
    }

    public int getTranslateY() {
        return this.translateY / mGraphics.zoomLevel;
    }

    public void enableBlending(float alpha) {
        this.g.setColor(1.0f, 1.0f, 1.0f, alpha);
    }

    public void disableBlending() {
        this.g.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void setClip(int x, int y, int w, int h) {
        if (w <= 0) {
            w = 1;
        }
        if (h <= 0) {
            h = 1;
        }
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }
        this.clipX = x;
        this.clipY = y;
        this.clipW = w;
        this.clipH = h;
        this.isClip = true;
    }

    public void beginClip() {
        Rectangle scissors = new Rectangle();
        Rectangle clipBounds = new Rectangle((float) this.clipX, (float) this.clipY, (float) this.clipW, (float) this.clipH);
        ScissorStack.calculateScissors(MainGame.me.camera, this.g.getTransformMatrix(), clipBounds, scissors);
        ScissorStack.pushScissors(scissors);
    }

    public void endClip() {
        this.g.flush();
        ScissorStack.peekScissors();
    }

    public void endClip0() {
        this.g.flush();
        ScissorStack.popScissors();
    }

    public mGraphics(SpriteBatch g) {
        this.g = g;
    }

    public mGraphics() {
    }

    void cache(String key, final Texture value) {
        if (mGraphics.cachedTextures.size() > 500) {
            Enumeration<Object> k = mGraphics.cachedTextures.keys();
            while (k.hasMoreElements()) {
                String k2 = (String) k.nextElement();
                Texture img = (Texture) mGraphics.cachedTextures.get(k2);
                mGraphics.cachedTextures.remove(k2);
                mGraphics.cachedTextures.remove(img);
                img.dispose();
                img = null;
            }
            mGraphics.cachedTextures.clear();
            System.gc();
        }
        mGraphics.cachedTextures.put(key, value);
    }

    public void clearCache() {
        Enumeration<Object> k = mGraphics.cachedTextures.keys();
        while (k.hasMoreElements()) {
            String k2 = (String) k.nextElement();
            Texture img = (Texture) mGraphics.cachedTextures.get(k2);
            mGraphics.cachedTextures.remove(img);
            mGraphics.cachedTextures.remove(k2);
            img.dispose();
            img = null;
        }
        mGraphics.cachedTextures.clear();
        System.gc();
    }

    public void drawTextureRegion(mImage tx, int x, int y, final int alg) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }
        this.g.draw(tx.image.tRegion, (float) x, (float) y);
    }

    public float getAngle(Vector2 centerPt, final Vector2 target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.y - centerPt.y, target.x - centerPt.x));
        if (angle < 0.0f) {
            angle += 360.0f;
        }
        return angle;
    }

    public void setColor(int rgb) {
        float R = (float) (rgb >> 16 & 0xFF);
        float B = (float) (rgb >> 8 & 0xFF);
        float G = (float) (rgb & 0xFF);
        this.b = B / 256.0f;
        this.gl = G / 256.0f;
        this.r = R / 256.0f;
        this.a = 1.0f;
    }

    public void setColor(int rgb, final float alpha) {
        float R = (float) (rgb >> 16 & 0xFF);
        float B = (float) (rgb >> 8 & 0xFF);
        float G = (float) (rgb & 0xFF);
        this.b = B / 256.0f;
        this.gl = G / 256.0f;
        this.r = R / 256.0f;
        this.a = alpha;
    }

    public void setColor(int rgb, final int rgb1, final int grb2) {
        int num = rgb & 0xFF;
        int num2 = rgb >> 8 & 0xFF;
        int num3 = rgb >> 16 & 0xFF;
        this.b = num / 256.0f;
        this.gl = num2 / 256.0f;
        this.r = num3 / 256.0f;
        this.a = 255.0f;
    }

    public void drawRegion(mImage img, final int x_src, final int y_src, final int width, final int height, final int flip, int x_dest, int y_dest, final int anchor, final boolean useClip) {
        if (img == null) {
            return;
        }
        x_dest *= mGraphics.zoomLevel;
        y_dest *= mGraphics.zoomLevel;
        this._drawRegion(img.image.texture, x_src, y_src, width, height, flip, x_dest, y_dest, anchor, useClip, false);
    }

    public void drawRegion(mImage img, final int x_src, final int y_src, final int width, final int height, final int flip, int x_dest, int y_dest, final int anchor, final boolean isScale, final boolean useClip) {
        if (img == null) {
            return;
        }
        x_dest *= mGraphics.zoomLevel;
        y_dest *= mGraphics.zoomLevel;
        this._drawRegion(img.image.texture, x_src, y_src, width, height, flip, x_dest, y_dest, anchor, useClip, isScale);
    }

    public void drawRegionNotSetClip(mImage img, int x, int y, int arg3, int arg4, final int arg5, final int arg6, final int arg7, final int anchor) {
        if (img == null) {
            return;
        }
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        arg3 *= mGraphics.zoomLevel;
        arg4 *= mGraphics.zoomLevel;
        this._drawRegion(img.image.texture, x, y, arg3, arg4, arg5, arg6, arg7, anchor, false, false);
    }

    public void fillTriangle(int x1, final int y1, final int x2, final int y2, final int x3, final int y3, final boolean useClip) {
        this.fillRect(x2, y2, x3, y3, useClip);
    }

    public void resetRotate() {
        this.isRorate = false;
        this.xRotate = 0;
        this.yRotate = 0;
    }

    public void rotate(int pAngle, final int x, final int y) {
        if (pAngle == 0) {
            return;
        }
        this.isRorate = true;
        this.rotation = (float) pAngle;
        this.xRotate = x;
        this.yRotate = y;
    }

    public void drawImageMap(mImage img, int x, int y) {
        if (img == null) {
            return;
        }
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }
        int w = img.image._getWidth();
        int h = img.image._getHeight();
        this.g.draw(img.image.texture, (float) x, (float) (y + h), (float) w, (float) (-h));
    }

    public void drawImage(mImage img, int x, int y, final int anchor, final boolean useClip, final boolean isScale) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        this._drawRegion(img.image.texture, 0, 0, img.image._getWidth(), img.image._getHeight(), 0, x, y, anchor, useClip, isScale);
    }

    public void drawImage(mImage img, int x, int y, final int anchor, final boolean useClip) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        this._drawRegion(img.image.texture, 0, 0, img.image._getWidth(), img.image._getHeight(), 0, x, y, anchor, useClip, false);
    }

    public void _drawImage(Texture img, int x, int y, final int anchor, final boolean useClip) {
        if (img == null) {
            return;
        }
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }
        int ixA = 0;
        int iyA = 0;
        int iyA2 = 0;
        int ixA2 = 0;
        int w = img.getWidth();
        int h = img.getHeight();
        switch (anchor) {
            case 0:
            case 20: {
                ixA = 0;
                ixA2 = w;
                iyA = h;
                iyA2 = -iyA;
                break;
            }
            case 17: {
                ixA = -w / 2;
                ixA2 = w;
                iyA = h;
                iyA2 = -iyA;
                break;
            }
            case 24: {
                ixA = -w;
                ixA2 = w;
                iyA = h;
                iyA2 = -iyA;
                break;
            }
            case 6: {
                ixA = 0;
                ixA2 = w;
                iyA = h / 2;
                iyA2 = -h;
                break;
            }
            case 3: {
                ixA = -w / 2;
                ixA2 = w;
                iyA = h / 2;
                iyA2 = -h;
                break;
            }
            case 10: {
                ixA = -w;
                ixA2 = w;
                iyA = h / 2;
                iyA2 = -h;
                break;
            }
            case 36: {
                ixA = 0;
                ixA2 = w;
                iyA = 0;
                iyA2 = -h;
                break;
            }
            case 33: {
                ixA = -w / 2;
                ixA2 = w;
                iyA = 0;
                iyA2 = -h;
                break;
            }
            case 40: {
                ixA = -w;
                ixA2 = w;
                iyA = 0;
                iyA2 = -h;
                break;
            }
        }
        if (this.isClip && useClip) {
            this.beginClip();
        }
        this.g.draw(img, (float) (x + ixA), (float) (y + iyA), (float) ixA2, (float) iyA2);
        if (this.isClip && useClip) {
            this.endClip0();
        }
    }

    public void drawRect(int x, final int y, final int w, final int h, final boolean useClip) {
        int xx = 1;
        this.fillRect(x, y, w, xx, useClip);
        this.fillRect(x, y, xx, h, useClip);
        this.fillRect(x + w, y, xx, h + 1, useClip);
        this.fillRect(x, y + h, w + 1, xx, useClip);
    }

    public void drawRoundRect(int x, final int y, final int w, final int h, final int a, final int b, final boolean useClip) {
        this.drawRect(x, y, w, h, useClip);
    }

    public void fillRoundRect(int x, final int y, final int w, final int h, final int a, final int b, final boolean useClip) {
        this.fillRect(x, y, w, h, useClip);
    }

    public void drawString(mVector total) {
    }

    public void drawStringNotSetClip(mVector total) {
    }

    public void drawString(String s, final float x, final float y, final BitmapFont font, final int al, final boolean useClip) {
    }

    public void setClipTrung(int x, int y, int w, int h) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }
        this.clipX = x;
        this.clipY = y;
        this.clipW = w;
        this.clipH = h;
        this.isClip = true;
    }

    public static Image blend(Image img, final float level, final int color) {
        int[] rgbdata = new int[img._getWidth() * img._getHeight()];
        img.texture.getTextureData().prepare();
        Pixmap a = img.texture.getTextureData().consumePixmap();
        int ww = img._getWidth();
        int hh = img._getHeight();
        int R = 0xFF & color >> 24;
        int B = 0xFF & color >> 16;
        int G = 0xFF & color >> 8;
        int A = 0xFF & color >> 0;
        for (int x = 0; x < ww; ++x) {
            for (int y = 0; y < hh; ++y) {
                int pixel = a.getPixel(x, y);
                if (pixel != -256) {
                    float r = (R - (pixel >> 24 & 0xFF)) * level + (pixel >> 24 & 0xFF);
                    float g = (B - (pixel >> 16 & 0xFF)) * level + (pixel >> 16 & 0xFF);
                    float b = (G - (pixel >> 8 & 0xFF)) * level + (pixel >> 8 & 0xFF);
                    float al = (A - (pixel >> 0 & 0xFF)) * level + (pixel >> 0 & 0xFF);
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
                    int rr = (int) r;
                    int gg = (int) g;
                    int bb = (int) b;
                    int aa = (int) al;
                    a.setColor((rr << 24) + (gg << 16) + (bb << 8) + (aa << 0));
                    a.drawPixel(x, y);
                }
            }
        }
        Image image = Image.createImage(ww, hh);
        image.texture = new Texture(a);
        a.dispose();
        return image;
    }

    public boolean isClipWithWHZero() {
        return this.isClip && (this.clipH == 0 || this.clipW == 0);
    }

    public void fillRecAlpla(int x, final int y, final int w, final int h, final int color) {
    }

    public void saveCanvas() {
    }

    public void ClipRec(int xbegin, final int ypaint, final int wScreen, final int i) {
    }

    public static void resetTransAndroid(mGraphics g2) {
    }

    public void restoreCanvas() {
    }

    public void translateAndroid(int x, final int i) {
    }

    public int getClipX() {
        return 0;
    }

    public int getClipY() {
        return 0;
    }

    public int getClipWidth() {
        if (this.isClip) {
            return CCanvas.width;
        }
        return 0;
    }

    public int getClipHeight() {
        if (this.isClip) {
            return CCanvas.hieght;
        }
        return 0;
    }

    public void fillArc(int x, final int y, final mImage imgCircle, final int startAngle, final int stopAngle, final boolean uClip) {
        int num = Math.abs(stopAngle);
        mImage image = mSystem.imgCircle_45;
        if (num == 60) {
            image = mSystem.imgCircle_30;
        } else if (num == 70) {
            image = mSystem.imgCircle_20;
        } else if (num == 90) {
            image = mSystem.imgCircle_0;
        }
        if (stopAngle > 0) {
            this.drawRegion(image, 0, 0, image.image.getWidth(), image.image.getHeight(), 0, x, y, 0, uClip);
        } else if (stopAngle < 0) {
            this.drawRegion(image, 0, 0, image.image.getWidth(), image.image.getHeight(), 2, x, y, 0, uClip);
        }
    }

    public void fillArc(int x, final int y, final int width, final int height, final int startAngle, final int arcAngle, final boolean uClip) {
        this.fillRect(x, y, width, height, uClip);
    }

    public void fillArc(int x, final int y, final int radius, final int startAngle, final int arcAngle) {
        this._drawArc(null, x, y, (float) radius, (float) startAngle, (float) arcAngle, null);
    }

    public void drawRGB(int[] raw, final int rectx, final int recty, final int w, final int h, final int destW, final int destH, final boolean useClip) {
    }

    public void fillRect(int x, int y, int w, int h, final boolean useClip) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        if (w < 0 || h < 0) {
            return;
        }
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }
        String key = "fr" + this.r + this.gl + this.b + this.a;
        Texture rgb_texture = (Texture) mGraphics.cachedTextures.get(key);
        if (rgb_texture == null) {
            Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            p.setColor(this.r, this.b, this.gl, this.a);
            p.drawPixel(0, 0);
            rgb_texture = new Texture(p);
            p.dispose();
            this.cache(key, rgb_texture);
        }
        if (this.isClip && useClip) {
            this.beginClip();
        }
        this.g.draw(rgb_texture, (float) x, (float) y, 0.0f, 0.0f, 1.0f, 1.0f, (float) w, (float) h, 0.0f, 0, 0, 1, 1, false, false);
        if (this.isClip && useClip) {
            this.endClip0();
        }
    }

    public void _drawRegion(Texture imgscr, final int x_src, final int y_src, final int width, final int height, int flip, int x_dest, int y_dest, final int anchor, final boolean isUseSetClip, final boolean isScale) {
        if (imgscr == null) {
            return;
        }
        if (this.isTranslate) {
            x_dest += this.translateX;
            y_dest += this.translateY;
        }
        boolean flipX = false;
        boolean flipY = true;
        int scX = 1;
        int scY = 1;
        int y0 = 0;
        int orx = 0;
        int ory = 0;
        int ixA = 0;
        int iyA = 0;
        switch (anchor) {
            case 0:
            case 20: {
                ixA = 0;
                iyA = 0;
                break;
            }
            case 17: {
                ixA = width / 2;
                if (flip == 4 || flip == 6 || flip == 5 || flip == 7) {
                    ixA = height / 2;
                }
                iyA = 0;
                break;
            }
            case 24: {
                ixA = width;
                if (flip == 4 || flip == 6 || flip == 5 || flip == 7) {
                    ixA = height;
                }
                iyA = 0;
                break;
            }
            case 6: {
                ixA = 0;
                iyA = height / 2;
                if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                    iyA = width / 2;
                    break;
                }
                break;
            }
            case 3: {
                ixA = width / 2;
                iyA = height / 2;
                if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                    ixA = height / 2;
                    iyA = width / 2;
                    break;
                }
                break;
            }
            case 10: {
                ixA = width;
                iyA = height / 2;
                if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                    ixA = height;
                    iyA = width / 2;
                    break;
                }
                break;
            }
            case 36: {
                ixA = 0;
                iyA = height;
                if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                    iyA = width;
                    break;
                }
                break;
            }
            case 33: {
                ixA = width / 2;
                iyA = height;
                if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                    ixA = height / 2;
                    iyA = width;
                    break;
                }
                break;
            }
            case 40: {
                ixA = width;
                iyA = height;
                if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                    ixA = height;
                    iyA = width;
                    break;
                }
                break;
            }
        }
        x_dest -= ixA * mGraphics.zoomLevel;
        y_dest -= iyA * mGraphics.zoomLevel;
        int ix = 0;
        int iy = 0;
        switch (flip) {
            case 2: {
                flip = 0;
                flipX = true;
                break;
            }
            case 3: {
                flip = 180;
                iy = -height;
                ix = -width;
                break;
            }
            case 6: {
                flip = 90;
                flipX = true;
                scY = -1;
                break;
            }
            case 5: {
                flip = 90;
                flipX = true;
                ix = -height;
                iy = -width;
                scX = -1;
                break;
            }
            case 1: {
                flip = 0;
                flipY = false;
                break;
            }
            case 4: {
                flip = 90;
                flipY = false;
                flipX = true;
                ix = -height;
                iy = -width;
                scX = -1;
                break;
            }
            case 7: {
                flip = 270;
                flipY = false;
                flipX = true;
                scX = -1;
                break;
            }
        }
        if (this.isRorate) {
            orx = this.xRotate;
            ory = this.yRotate;
            flip += (int) this.rotation;
        }
        if (this.isClip && isUseSetClip) {
            this.beginClip();
        }
        this.g.draw(imgscr, (float) (x_dest - ix * mGraphics.zoomLevel), (float) (y_dest - iy * mGraphics.zoomLevel), (float) orx, (float) ory, (float) width, (float) height, (float) (scX * (isScale ? 1 : mGraphics.zoomLevel)), scY * (isScale ? 1 : mGraphics.zoomLevel) + (isScale ? 0.05f : 0.0f), (float) flip, x_src, y_src, width, height, flipX, flipY);
        if (this.isClip && isUseSetClip) {
            this.endClip0();
        }
    }

    public void drawLine(int x1, int y1, int x2, int y2, final boolean useClip) {
        x1 *= mGraphics.zoomLevel;
        y1 *= mGraphics.zoomLevel;
        x2 *= mGraphics.zoomLevel;
        y2 *= mGraphics.zoomLevel;
        if (this.isTranslate) {
            x1 += this.translateX;
            y1 += this.translateY;
            x2 += this.translateX;
            y2 += this.translateY;
        }
        String key = "dl" + this.r + this.gl + this.b;
        Texture rgb_texture = (Texture) mGraphics.cachedTextures.get(key);
        if (rgb_texture == null) {
            Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            p.setColor(this.r, this.b, this.gl, this.a);
            p.drawPixel(0, 0);
            rgb_texture = new Texture(p);
            p.dispose();
            this.cache(key, rgb_texture);
            return;
        }
        float xSl = (float) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
        int ySl = mGraphics.zoomLevel;
        Vector2 start = new Vector2((float) x1, (float) y1);
        Vector2 end = new Vector2((float) x2, (float) y2);
        float angle = this.getAngle(start, end);
        if (this.isClip && useClip) {
            this.beginClip();
        }
        this.g.draw(rgb_texture, (float) x1, (float) y1, 0.0f, 0.0f, 1.0f, 1.0f, xSl, (float) ySl, angle, 0, 0, 1, 1, false, false);
        if (this.isClip && useClip) {
            this.endClip0();
        }
    }

    public void drawRecAlpa(int x, int y, int w, int h, final int color) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        if (w < 0 || h < 0) {
            return;
        }
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }
        this.setColor(color);
        this.a = 0.5f;
        String key = "fr" + this.r + this.gl + this.b + this.a;
        Texture rgb_texture = (Texture) mGraphics.cachedTextures.get(key);
        if (rgb_texture == null) {
            Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            p.setColor(this.r, this.b, this.gl, this.a);
            p.drawPixel(0, 0);
            rgb_texture = new Texture(p);
            p.dispose();
            this.cache(key, rgb_texture);
            return;
        }
        this.g.draw(rgb_texture, (float) x, (float) y, 0.0f, 0.0f, 1.0f, 1.0f, (float) w, (float) h, 0.0f, 0, 0, 1, 1, false, false);
    }

    public void fillRect(int x, int y, int w, int h, final int color, final int alpha, final boolean useClip) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        w *= mGraphics.zoomLevel;
        h *= mGraphics.zoomLevel;
        if (w < 0 || h < 0 || this.isClipWithWHZero()) {
            return;
        }
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }
        String key = "fr" + this.r + this.gl + this.b + this.a + color;
        Texture rgb_texture = (Texture) mGraphics.cachedTextures.get(key);
        if (rgb_texture == null) {
            this.setColor(color, 0.5f);
            Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            p.setColor(this.r, this.b, this.gl, this.a);
            p.drawPixel(0, 0);
            rgb_texture = new Texture(p);
            p.dispose();
            this.cache(key, rgb_texture);
            return;
        }
        if (this.isClip && useClip) {
            this.beginClip();
        }
        this.g.draw(rgb_texture, (float) x, (float) y, 0.0f, 0.0f, 1.0f, 1.0f, (float) w, (float) h, 0.0f, 0, 0, 1, 1, false, false);
        if (this.isClip && useClip) {
            this.endClip0();
        }
    }

    public void _drawArc(Texture imgscr, int x, int y, final float radius, final float start, final float degrees, final ShapeRenderer.ShapeType shapeType) {
        x *= mGraphics.zoomLevel;
        y *= mGraphics.zoomLevel;
        boolean flipX = false;
        boolean flipY = true;
        int scX = 1;
        int scY = 1;
        int y2 = 0;
        int orx = 0;
        int ory = 0;
        int ixA = 0;
        int iyA = 0;
    }

    static {
        mGraphics.zoomLevel = 1;
        mGraphics.HCENTER = 1;
        mGraphics.VCENTER = 2;
        mGraphics.LEFT = 4;
        mGraphics.RIGHT = 8;
        mGraphics.TOP = 16;
        mGraphics.BOTTOM = 32;
        mGraphics.cachedTextures = new mHashtable();
    }
}
