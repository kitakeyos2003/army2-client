package javax.microedition.lcdui;

import com.badlogic.gdx.graphics.TextureData;
import java.io.DataInputStream;
import CLib.mSystem;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import CLib.LibSysTem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;

public class Image {

    public Texture texture;
    public TextureRegion tRegion;
    public int width;
    public int height;
    public Sprite sp;
    public static int Zoom;
    public boolean isZoom_IOS;
    public static int transColor;

    public Image() {
        this.isZoom_IOS = false;
    }

    public static Image createImage(String url) {
        Image img = new Image();
        img.texture = new Texture(Gdx.files.internal(String.valueOf(LibSysTem.res) + url), Pixmap.Format.RGBA8888,
                false);
        if (img.texture == null) {
            throw new IllegalArgumentException("!!! createImage scr is NULL-----------." + LibSysTem.res + url);
        }
        img.width = img.texture.getWidth();
        img.height = img.texture.getHeight();
        img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        if (img.texture == null) {
            return null;
        }
        return img;
    }

    public static Color getColor(int rgb) {
        int blue = rgb & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int red = rgb >> 16 & 0xFF;
        int al = rgb >> 24 & 0xFF;
        float b = blue / 256.0f;
        float g = green / 256.0f;
        float r = red / 256.0f;
        float a = al / 256.0f;
        return new Color(r, g, b, a);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public static Image createImageTextureRegion() {
        Image img = new Image();
        (img.tRegion = new TextureRegion()).flip(false, true);
        return img;
    }

    public static Image createImage(int w, int h) {
        Image img = new Image();
        img.texture = new Texture(w, h, Pixmap.Format.RGBA4444);
        img.width = img.texture.getWidth();
        img.height = img.texture.getHeight();
        return img;
    }

    public static Image createImage(final byte[] encodedData, final int offset, final int len) {
        final Image img = new Image();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    img.texture = new Texture(new Pixmap(encodedData, offset, len));
                    img.width = img.texture.getWidth();
                    img.height = img.texture.getHeight();
                } catch (Exception ex) {
                }
            }
        });
        return img;
    }

    public static Image ScaleImage(Image scr, int w, int h) {
        return scr;
    }

    public static Image createImageMiniMap(Image imgTile, int wMap, int hMap, int[] dataMap, int testValue,
            int sizeMini) {
        if (imgTile == null) {
            throw new IllegalArgumentException("Image imgTile is NULL-----------.");
        }
        imgTile.texture.getTextureData().prepare();
        Pixmap a = imgTile.texture.getTextureData().consumePixmap();
        Pixmap b = new Pixmap(wMap * sizeMini, hMap * sizeMini, Pixmap.Format.RGBA8888);
        for (int i = 0; i < wMap; ++i) {
            for (int j = 0; j < hMap; ++j) {
                int u = dataMap[j * wMap + i] - 1;
                if (u > testValue) {
                    b.drawPixmap(a, i * sizeMini, (hMap - 1 - j) * sizeMini, 0, u * sizeMini, sizeMini, sizeMini);
                }
            }
        }
        Image img = new Image();
        img.texture = new Texture(b);
        img.width = wMap * sizeMini;
        img.height = hMap * sizeMini;
        a.dispose();
        b.dispose();
        return img;
    }

    public static Image createRGBImage(int[] rgb, int w, int h, boolean i) {
        return createImage(rgb, w, h);
    }

    public static byte[] int2byte(int[] values) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        for (int i = 0; i < values.length; ++i) {
            try {
                dos.writeInt(values[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }

    public static Image createImage(int[] encodedData, int w, int h, boolean oi) {
        return createImage(encodedData, w, h);
    }

    public static Image createImage(final int[] encodedData, final int w, final int h) {
        final Image img = new Image();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    Color cl = null;
                    Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);
                    for (int i = 0; i < w; ++i) {
                        for (int j = 0; j < h; ++j) {
                            if (encodedData[j * w + i] == Image.transColor) {
                                p.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
                            } else {
                                p.setColor(mSystem.setColor(encodedData[j * w + i]));
                            }
                            p.drawPixel(i, j);
                        }
                    }
                    img.texture = new Texture(p);
                    img.width = img.texture.getWidth();
                    img.height = img.texture.getHeight();
                    img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                    (img.sp = new Sprite(img.texture)).setPosition(0.0f, 0.0f);
                    img.sp.setCenterX(0.0f);
                    img.sp.setOrigin(0.0f, 0.0f);
                    img.sp.flip(false, true);
                    img.sp.scale((float) (Image.Zoom - 1));
                    p.dispose();
                } catch (Exception ex) {
                }
            }
        });
        return img;
    }

    public static DataInputStream openFile(String path) {
        DataInputStream is = new DataInputStream(LibSysTem.getResourceAsStream(path));
        return is;
    }

    public static int getIntByColor(Color cl) {
        float r = cl.r * 255.0f;
        float b = cl.b * 255.0f;
        float g = cl.g * 255.0f;
        return ((int) r & 0xFF) << 16 | ((int) g & 0xFF) << 8 | ((int) b & 0xFF);
    }

    public static int argb(int alpha, int red, int green, int blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {
        if (this.texture == null) {
            throw new IllegalArgumentException("texture Image getRGB is NULL-----------.");
        }
        try {
            TextureData tData = this.texture.getTextureData();
            if (!tData.isPrepared()) {
                tData.prepare();
            }
            Pixmap a = tData.consumePixmap();
            boolean isRGB888 = false;
            if (a.getFormat() == Pixmap.Format.RGB888) {
            }
            int[] r = new int[width * height];
            Color color = new Color();
            for (int i = 0; i < width; ++i) {
                for (int j = 0; j < height; ++j) {
                    int val = a.getPixel(i + x, j + y);
                    if (isRGB888) {
                        Color.rgb888ToColor(color, val);
                    } else {
                        Color.rgba8888ToColor(color, val);
                    }
                    int R = (int) (color.r * 255.0f);
                    int G = (int) (color.g * 255.0f);
                    int B = (int) (color.b * 255.0f);
                    int A = (int) (color.a * 255.0f);
                    if (isRGB888) {
                        A = 255;
                    }
                    if (color.a == 0.0f) {
                        r[j * width + i] = 16777215;
                    } else {
                        r[j * width + i] = argb(A, R, G, B);
                    }
                }
            }
            for (int i = 0; i < rgbData.length; ++i) {
                rgbData[i] = r[i];
            }
        } catch (Exception ex) {
        }
    }

    public void dispose() {
        if (this.texture != null) {
            this.texture.dispose();
        }
    }

    static {
        Image.Zoom = 1;
        Image.transColor = 16777215;
    }
}
