package CLib;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SysTemFont {

    public static String charLits;
    public BitmapFont font;
    private float r;
    private float g;
    private float b;
    private float a;
    public float[] charWidth;
    public byte charHeight;
    private float charSpace;
    float yAddFont;
    public Color cl;

    public SysTemFont(int ID, int color) {
        String name = "big";
        if (ID <= 8 || ID >= 30) {
            name = "big";
        } else {
            name = "mini";
        }
        if (mGraphics.zoomLevel == 1) {
            name = "big";
        }
        if (mGraphics.zoomLevel == 2) {
            this.yAddFont = 1.5f;
        } else if (mGraphics.zoomLevel == 3) {
            this.yAddFont = 2.0f;
        } else if (mGraphics.zoomLevel == 4) {
            this.yAddFont = 2.3f;
        }
        this.font = new BitmapFont(Gdx.files.internal(String.valueOf(LibSysTem.font) + mGraphics.zoomLevel + "/" + name + ".fnt"), Gdx.files.internal(String.valueOf(LibSysTem.font) + mGraphics.zoomLevel + "/" + name + ".png"), true);
        this.cl = mSystem.setColor(color);
        this.font.setColor(this.cl);
        this.charWidth = new float[SysTemFont.charLits.length()];
        this.charWidth = new float[SysTemFont.charLits.length()];
        GlyphLayout layout = new GlyphLayout();
        for (int i = 0; i < this.charWidth.length; ++i) {
            layout.setText(this.font, new StringBuilder(String.valueOf(SysTemFont.charLits.charAt(i))).toString());
            float width = layout.width;
            float height = layout.height;
            this.charWidth[i] = width / mGraphics.zoomLevel;
        }
        layout.setText(this.font, "A");
        float height2 = layout.height;
        this.charHeight = (byte) (height2 / mGraphics.zoomLevel + 3.0f);
        layout.setText(this.font, " ");
        float width = layout.width;
        this.charSpace = width / mGraphics.zoomLevel;
    }

    public Color rgba8888ToColor(int rgba8888) {
        Color color = new Color();
        color.r = ((rgba8888 & 0xFF000000) >>> 24) / 255.0f;
        color.g = ((rgba8888 & 0xFF0000) >>> 16) / 255.0f;
        color.b = ((rgba8888 & 0xFF00) >>> 8) / 255.0f;
        color.a = (rgba8888 & 0xFF) / 255.0f;
        return color;
    }

    public SysTemFont(String name, int color, float a) {
        this.font = new BitmapFont(Gdx.files.internal(String.valueOf(LibSysTem.font) + name + ".fnt"), Gdx.files.internal(String.valueOf(LibSysTem.font) + name + ".png"), true);
        this.cl = mSystem.setColor(color);
        this.font.setColor(this.cl);
        this.charWidth = new float[SysTemFont.charLits.length()];
        GlyphLayout layout = new GlyphLayout();
        for (int i = 0; i < this.charWidth.length; ++i) {
            layout.setText(this.font, new StringBuilder(String.valueOf(SysTemFont.charLits.charAt(i))).toString());
            float width = layout.width;
            this.charWidth[i] = width / mGraphics.zoomLevel;
        }
        layout.setText(this.font, "A");
        float height = layout.height;
        this.charHeight = (byte) height;
        layout.setText(this.font, " ");
        float width = layout.width;
        this.charSpace = width / mGraphics.zoomLevel;
    }

    public int getWidth(String st) {
        int len = 0;
        for (int i = 0; i < st.length(); ++i) {
            int pos = SysTemFont.charLits.indexOf(st.charAt(i));
            if (pos == -1) {
                pos = 0;
            }
            len += (int) (this.charWidth[pos] + this.charSpace);
        }
        return len;
    }

    public int convert_RGB_to_ARGB(int rgb) {
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb >> 0 & 0xFF;
        return 0xFF000000 | r << 16 | g << 8 | b;
    }

    public String[] splitString(String _text, String _searchStr) {
        int count = 0;
        int pos = 0;
        int searchStringLength = _searchStr.length();
        for (int aa = _text.indexOf(_searchStr, pos); aa != -1; aa = _text.indexOf(_searchStr, pos), ++count) {
            pos = aa + searchStringLength;
        }
        String[] sb = new String[count + 1];
        int searchStringPos;
        int startPos;
        int index;
        for (searchStringPos = _text.indexOf(_searchStr), startPos = 0, index = 0; searchStringPos != -1; searchStringPos = _text.indexOf(_searchStr, startPos), ++index) {
            sb[index] = _text.substring(startPos, searchStringPos);
            startPos = searchStringPos + searchStringLength;
        }
        sb[index] = _text.substring(startPos, _text.length());
        return sb;
    }

    public String[] splitFontArray(String src, int lineWidth) {
        mVector lines = this._splitFont(src, lineWidth);
        String[] arr = new String[lines.size()];
        for (int i = 0; i < lines.size(); ++i) {
            arr[i] = lines.elementAt(i).toString();
        }
        return arr;
    }

    public static String[] _splitString(String _text, String _searchStr) {
        int count = 0;
        int pos = 0;
        int searchStringLength = _searchStr.length();
        for (int aa = _text.indexOf(_searchStr, pos); aa != -1; aa = _text.indexOf(_searchStr, pos), ++count) {
            pos = aa + searchStringLength;
        }
        String[] sb = new String[count + 1];
        int searchStringPos;
        int startPos;
        int index;
        for (searchStringPos = _text.indexOf(_searchStr), startPos = 0, index = 0; searchStringPos != -1; searchStringPos = _text.indexOf(_searchStr, startPos), ++index) {
            sb[index] = _text.substring(startPos, searchStringPos);
            startPos = searchStringPos + searchStringLength;
        }
        sb[index] = _text.substring(startPos, _text.length());
        return sb;
    }

    public String replace(String _text, String _searchStr, String _replacementStr) {
        StringBuffer sb = new StringBuffer();
        int searchStringPos = _text.indexOf(_searchStr);
        int startPos = 0;
        int searchStringLength = _searchStr.length();
        while (searchStringPos != -1) {
            sb.append(_text.substring(startPos, searchStringPos)).append(_replacementStr);
            startPos = searchStringPos + searchStringLength;
            searchStringPos = _text.indexOf(_searchStr, startPos);
        }
        sb.append(_text.substring(startPos, _text.length()));
        return sb.toString();
    }

    public String[] splitFont(String src, int lineWidth) {
        mVector lines = this._splitFont(src, lineWidth);
        String[] arr = new String[lines.size()];
        for (int i = 0; i < lines.size(); ++i) {
            arr[i] = lines.elementAt(i).toString();
        }
        return arr;
    }

    public mVector _splitFont(String src, int lineWidth) {
        mVector lines = new mVector();
        if (lineWidth <= 0) {
            lines.add(src);
            return lines;
        }
        String line = "";
        for (int i = 0; i < src.length(); ++i) {
            if (src.charAt(i) == '\n') {
                lines.addElement(line);
                line = "";
            } else {
                line = String.valueOf(line) + src.charAt(i);
                if (this.getWidth(line) > lineWidth) {
                    int j;
                    for (j = 0, j = line.length() - 1; j >= 0 && line.charAt(j) != ' '; --j) {
                    }
                    if (j < 0) {
                        j = line.length() - 1;
                    }
                    lines.addElement(line.substring(0, j));
                    i = i - (line.length() - j) + 1;
                    line = "";
                }
                if (i == src.length() - 1 && !line.trim().equals("")) {
                    lines.addElement(line);
                }
            }
        }
        return lines;
    }

    public int getHeight() {
        return this.charHeight;
    }

    public void drawString(mGraphics g, String st, int x, int y, int align, boolean useClip) {
        int al = 8;
        switch (align) {
            case 1: {
                al = 16;
                break;
            }
            case 2: {
                al = 1;
                break;
            }
        }
        g.drawString(st, (float) x, y + this.yAddFont, this.font, al, useClip);
    }

    public mVector splitFontVector(String src, int lineWidth) {
        mVector lines = new mVector();
        if (lineWidth <= 0) {
            lines.add(src);
            return lines;
        }
        String line = "";
        for (int i = 0; i < src.length(); ++i) {
            if (src.charAt(i) == '\n' || src.charAt(i) == '\b') {
                lines.addElement(line);
                line = "";
            } else {
                line = String.valueOf(line) + src.charAt(i);
                if (this.getWidth(line) > lineWidth) {
                    int j;
                    for (j = 0, j = line.length() - 1; j >= 0 && line.charAt(j) != ' '; --j) {
                    }
                    if (j < 0) {
                        j = line.length() - 1;
                    }
                    lines.addElement(line.substring(0, j));
                    i = i - (line.length() - j) + 1;
                    line = "";
                }
                if (i == src.length() - 1 && !line.trim().equals("")) {
                    lines.addElement(line);
                }
            }
        }
        return lines;
    }

    static {
        SysTemFont.charLits = " \u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\ufffd?\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\ufffd?\u00f5\ufffd?\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\ufffd?\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111\ufffd?\u00c0\u1ea2\u00c3\u1ea0\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6\ufffd?\u00cc\u1ec8\u0128\u1eca\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\ufffd?\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0\ufffd?\u1ef2\u1ef6\u1ef8\u1ef4\ufffd?";
    }
}
