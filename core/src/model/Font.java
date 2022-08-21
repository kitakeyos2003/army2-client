package model;

import java.util.Vector;
import CLib.mGraphics;
import CLib.Image;
import CLib.mImage;

public class Font {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int CENTER = 2;
    public mImage imgFont;
    private String charList;
    public byte[] charWidth;
    private int charHeight;
    private int charSpace;
    public String nameFont;
    public int imgWidth;
    public static Font normalFont;
    public static Font normalYFont;
    public static Font normalGFont;
    public static Font normalRFont;
    public static Font borderFont;
    public static Font bigFont;
    public static Font numberFont;
    public static Font smallFontRed;
    public static Font smallFontYellow;
    public static Font smallFont;
    public static Font arialFont;
    public static Font blackF;

    public Font(String charList, byte[] charWidth, int charHeight, String fontFile, int charSpace) {
        try {
            this.charSpace = charSpace;
            this.charList = charList;
            this.charWidth = charWidth;
            this.charHeight = charHeight;
            this.nameFont = fontFile;
            mImage.createImage("/font/fontT/" + fontFile, new IAction2() {
                @Override
                public void perform(Object object) {
                    imgFont = new mImage((Image) object);
                    imgWidth = imgFont.image.getWidth();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawString(mGraphics g, String st, int x, int y, int align, boolean isClip) {
        int len = st.length();
        int x2;
        if (align == 0) {
            x2 = x;
        } else if (align == 1) {
            x2 = x - this.getWidth(st);
        } else {
            x2 = x - (this.getWidth(st) >> 1);
        }
        for (int i = 0; i < len; ++i) {
            int pos = this.charList.indexOf(st.charAt(i));
            if (pos == -1) {
                pos = 0;
            }
            if (pos > -1) {
                g.drawRegion(this.imgFont, 0, pos * this.charHeight, this.imgWidth, this.charHeight, 0, x2, y, 20, isClip);
            }
            if (pos > this.charWidth.length - 1) {
                pos = this.charWidth.length - 1;
            }
            x2 += this.charWidth[pos] + this.charSpace;
        }
    }

    public void drawString(mGraphics g, String st, int x, int y, int align) {
        int len = st.length();
        int x2;
        if (align == 0) {
            x2 = x;
        } else if (align == 1) {
            x2 = x - this.getWidth(st);
        } else {
            x2 = x - (this.getWidth(st) >> 1);
        }
        for (int i = 0; i < len; ++i) {
            int pos = this.charList.indexOf(st.charAt(i));
            if (pos == -1) {
                pos = 0;
            }
            if (pos > -1) {
                g.drawRegion(this.imgFont, 0, pos * this.charHeight, this.imgWidth, this.charHeight, 0, x2, y, mGraphics.BOTTOM | mGraphics.VCENTER, true);
            }
            if (pos > this.charWidth.length - 1) {
                pos = this.charWidth.length - 1;
            }
            x2 += this.charWidth[pos] + this.charSpace;
        }
    }

    public int getWidth(String st) {
        int len = 0;
        for (int i = 0; i < st.length(); ++i) {
            int pos = this.charList.indexOf(st.charAt(i));
            if (pos == -1) {
                pos = 0;
            }
            if (pos > this.charWidth.length - 1) {
                pos = this.charWidth.length - 1;
            }
            len += this.charWidth[pos];
        }
        return len;
    }

    public static String replace(String _text, String _searchStr, String _replacementStr) {
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

    public String[] splitFontBStrInLine(String src, int lineWidth) {
        Vector<String> list = new Vector<String>();
        src = src.trim();
        int srclen = src.length();
        if (srclen == 0) {
            return null;
        }
        if (srclen == 1) {
            return new String[]{src};
        }
        String tem = "";
        int start = 0;
        int end = 0;
        while (true) {
            if (this.getWidth(tem) < lineWidth) {
                tem = String.valueOf(tem) + src.charAt(end);
                ++end;
                if (src.charAt(end) != '\n') {
                    if (end < srclen - 1) {
                        continue;
                    }
                    end = srclen - 1;
                }
            }
            if (end != srclen - 1 && src.charAt(end + 1) != ' ') {
                int endAnyway = end;
                while (src.charAt(end + 1) == '\n') {
                }
                if ((src.charAt(end + 1) != ' ' || src.charAt(end) == ' ') && end != start) {
                    --end;
                    continue;
                }
                if (end == start) {
                    end = endAnyway;
                }
            }
            list.addElement(src.substring(start, end + 1));
            if (end == srclen - 1) {
                break;
            }
            for (start = end + 1; start != srclen - 1 && src.charAt(start) == ' '; ++start) {
            }
            if (start == srclen - 1) {
                break;
            }
            end = start;
            tem = "";
        }
        String[] strs = new String[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            strs[i] = list.elementAt(i);
        }
        return strs;
    }

    public int getHeight() {
        return this.charHeight;
    }

    public static void OnSaveImageFont() {
    }

    static {
        Font.normalFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ\u0110$", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6}, 13, "fb.png", 0);
        Font.normalYFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ\u0110$@", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6, 9}, 13, "fb2.png", 0);
        Font.normalGFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00c1\u00c0\u1ea2\u00c3\u1ea0\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6\u00cd\u00cc\u1ec8\u0128\u1eca\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0\u00dd\u1ef2\u1ef6\u1ef8\u1ef4\u0110$", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6}, 13, "fb3.png", 0);
        Font.normalRFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ\u0110$", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6}, 13, "fb4.png", 0);
        Font.borderFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ\u0110$", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6}, 15, "fb1.png", 0);
        Font.bigFont = new Font(" 0123456789.,:!?()-'/ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00c1\u00c0\u1ea2\u00c3\u1ea0\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6\u00cd\u00cc\u1ec8\u0128\u1eca\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0\u00dd\u1ef2\u1ef6\u1ef8\u1ef4\u0110", new byte[]{4, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 4, 4, 4, 4, 8, 6, 6, 6, 3, 7, 10, 10, 10, 10, 8, 8, 10, 10, 5, 8, 9, 8, 13, 11, 10, 10, 10, 10, 10, 9, 10, 10, 13, 11, 11, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 5, 5, 5, 5, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}, 21, "fcg14m.png", 0);
        Font.numberFont = new Font("0123456789$+-", new byte[]{15, 12, 15, 15, 15, 15, 15, 15, 15, 15, 15, 10, 10}, 13, "so.png", -3);
        Font.smallFontRed = new Font("0123456789+-%$:ABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 6, 5, 5, 5, 5, 5, 5, 5, 5, 7, 5, 5, 5}, 8, "fs0.png", -1);
        Font.smallFontYellow = new Font("0123456789+-%$:ABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 6, 5, 5, 5, 5, 5, 5, 5, 5, 7, 5, 5, 5}, 8, "fs1.png", -1);
        Font.smallFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{2, 5, 4, 5, 5, 6, 5, 5, 5, 5, 5, 2, 3, 2, 2, 5, 4, 4, 6, 6, 6, 6, 4, 3, 8, 5, 5, 4, 5, 5, 4, 5, 5, 2, 3, 5, 2, 8, 5, 5, 5, 5, 4, 4, 3, 5, 6, 8, 4, 4, 4, 6, 5, 6, 6, 5, 5, 6, 6, 4, 4, 5, 5, 8, 7, 7, 5, 7, 6, 6, 6, 6, 6, 8, 6, 6, 5}, 10, "tahoma9.png", 0);
        Font.arialFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{3, 5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 5, 3, 2, 5, 4, 5, 6, 2, 2, 9, 5, 5, 4, 5, 5, 3, 5, 5, 1, 1, 5, 1, 9, 5, 5, 5, 5, 3, 5, 2, 5, 4, 8, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2, 1, 2, 3, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 7, 7, 7, 7, 7, 7, 4, 4, 4, 4, 4, 6, 6, 6, 7, 7, 6, 5, 7, 7, 1, 4, 7, 6, 7, 7, 7, 6, 7, 7, 6, 6, 7, 6, 8, 6, 6, 6, 7, 4}, 14, "arialf.png", 1);
        Font.blackF = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{4, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 2, 2, 6, 4, 4, 6, 5, 6, 7, 4, 4, 10, 6, 6, 6, 6, 6, 4, 6, 6, 2, 2, 5, 2, 8, 6, 6, 6, 6, 4, 6, 3, 6, 6, 10, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3, 2, 3, 4, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 6, 6, 6, 6, 6, 8, 8, 8, 8, 8, 8, 6, 6, 6, 6, 6, 7, 8, 7, 7, 7, 6, 6, 8, 7, 2, 5, 7, 6, 8, 7, 8, 6, 8, 7, 7, 6, 7, 8, 11, 7, 8, 7, 7, 6, 7}, 13, "arial11b.png", 0);
    }
}
