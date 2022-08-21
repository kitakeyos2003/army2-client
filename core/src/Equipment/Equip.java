package Equipment;

import screen.EquipScreen;
import model.Font;
import CLib.mGraphics;
import model.Language;
import model.PlayerInfo;
import coreLG.TerrainMidlet;
import java.util.Vector;
import CLib.mImage;

public class Equip {

    public int dbKey;
    public short id;
    public byte frame;
    public short[] x;
    public short[] y;
    public byte[] w;
    public byte[] h;
    public byte[] dx;
    public byte[] dy;
    public short icon;
    public byte type;
    public byte glass;
    public byte slot;
    public byte vip;
    public boolean isVip;
    public int num;
    public int numSelected;
    public boolean isBuyNum;
    public short[] inv_attAddPoint;
    public byte[] inv_ability;
    public byte[] inv_percen;
    public short[] shop_attAddPoint;
    public byte[] shop_ability;
    public byte[] shop_percen;
    public int index;
    public byte date;
    public String name;
    public int xu;
    public int luong;
    public byte level;
    public int level2;
    public boolean isSelect;
    public byte bullet;
    public boolean isMaterial;
    public String strDetail;
    public mImage materialIcon;
    public boolean notPaint;
    public int shopDetailNunmLines;
    public Vector<String> shopDetailNunStrs;
    int header;

    public Equip(short id, byte type, byte frame, short[] x, short[] y, byte[] w, byte[] h, byte[] dx, byte[] dy, short icon, byte bullet) {
        this.num = 1;
        this.numSelected = 1;
        this.isBuyNum = false;
        this.inv_attAddPoint = new short[5];
        this.inv_ability = new byte[5];
        this.inv_percen = new byte[5];
        this.shop_attAddPoint = new short[5];
        this.shop_ability = new byte[5];
        this.shop_percen = new byte[5];
        this.isSelect = false;
        this.isMaterial = false;
        this.shopDetailNunStrs = null;
        this.id = id;
        this.type = type;
        this.frame = frame;
        this.x = new short[frame];
        this.y = new short[frame];
        this.w = new byte[frame];
        this.h = new byte[frame];
        this.dx = new byte[frame];
        this.dy = new byte[frame];
        for (int i = 0; i < frame; ++i) {
            this.x[i] = x[i];
            this.y[i] = y[i];
            this.w[i] = w[i];
            this.h[i] = h[i];
            this.dx[i] = dx[i];
            this.dy[i] = dy[i];
        }
        this.icon = icon;
        this.type = type;
        this.bullet = bullet;
    }

    public Equip() {
        this.num = 1;
        this.numSelected = 1;
        this.isBuyNum = false;
        this.inv_attAddPoint = new short[5];
        this.inv_ability = new byte[5];
        this.inv_percen = new byte[5];
        this.shop_attAddPoint = new short[5];
        this.shop_ability = new byte[5];
        this.shop_percen = new byte[5];
        this.isSelect = false;
        this.isMaterial = false;
        this.shopDetailNunStrs = null;
    }

    public int[] getBaseAttribute() {
        int[] atts = new int[5];
        PlayerInfo info = TerrainMidlet.myInfo;
        int[] ability = new int[5];
        int[] percen = new int[5];
        for (int i = 0; i < 5; ++i) {
            int[] array = ability;
            int n = i;
            int[] array7 = array;
            int n7 = n;
            array7[n7] += this.inv_ability[i];
            int[] array2 = percen;
            int n2 = i;
            int[] array8 = array2;
            int n8 = n2;
            array8[n8] += this.inv_percen[i];
        }
        atts[0] = ability[0] * 10;
        int[] array3 = atts;
        int n3 = 0;
        int[] array9 = array3;
        int n9 = n3;
        array9[n9] += (1000 + info.ability[0] * 10) * percen[0] / 100;
        int maxDamage = PlayerEquip.getEquipGlass(info.gun).maxDamage;
        int damPoint = ability[1] + info.attribute[1];
        int defPoint = ability[2] + info.attribute[2];
        int luckPoint = ability[3] + info.attribute[3];
        int team = ability[4] + info.attribute[4];
        atts[1] = maxDamage * (damPoint / 3 + 100 + percen[1]) / 100;
        atts[2] = defPoint * 10;
        int[] array4 = atts;
        int n4 = 2;
        int[] array10 = array4;
        int n10 = n4;
        array10[n10] += atts[2] * percen[2] / 100;
        atts[3] = luckPoint * 10;
        int[] array5 = atts;
        int n5 = 3;
        int[] array11 = array5;
        int n11 = n5;
        array11[n11] += atts[3] * percen[3] / 100;
        atts[4] = team * 10;
        int[] array6 = atts;
        int n6 = 4;
        int[] array12 = array6;
        int n12 = n6;
        array12[n12] += atts[4] * percen[4] / 100;
        return atts;
    }

    public void setInvAtribute() {
        PlayerInfo m = TerrainMidlet.myInfo;
        for (int i = 0; i < 5; ++i) {
            this.inv_attAddPoint[i] = 0;
            short[] inv_attAddPoint = this.inv_attAddPoint;
            int n = i;
            short[] array = inv_attAddPoint;
            int n3 = n;
            array[n3] += this.inv_ability[i];
            short[] inv_attAddPoint2 = this.inv_attAddPoint;
            int n2 = i;
            short[] array2 = inv_attAddPoint2;
            int n4 = n2;
            array2[n4] += (short) (m.attribute[i] * this.inv_percen[i] / 100);
        }
    }

    public void removeAbility() {
        for (int i = 0; i < 5; ++i) {
            this.inv_attAddPoint[i] = 0;
            this.inv_percen[i] = 0;
            this.inv_ability[i] = 0;
        }
    }

    public void addAbilityFromEquip(Equip e) {
        for (int i = 0; i < 5; ++i) {
            this.inv_ability[i] = e.inv_ability[i];
            this.inv_attAddPoint[i] = e.inv_attAddPoint[i];
            this.inv_percen[i] = e.inv_percen[i];
        }
    }

    public void getInvAtribute(byte[] ability) {
        PlayerInfo m = TerrainMidlet.myInfo;
        int a = 0;
        int b = 0;
        int c = 0;
        for (int i = 0; i < ability.length; ++i) {
            this.inv_attAddPoint[a] = 0;
            if (i % 2 == 0) {
                short[] inv_attAddPoint = this.inv_attAddPoint;
                int n = a;
                short[] array = inv_attAddPoint;
                int n3 = n;
                array[n3] += ability[i];
            } else {
                short[] inv_attAddPoint2 = this.inv_attAddPoint;
                int n2 = a;
                short[] array2 = inv_attAddPoint2;
                int n4 = n2;
                array2[n4] += (short) (m.attribute[a] * ability[i] / 100);
                ++a;
            }
        }
        for (int i = 0; i < ability.length; ++i) {
            if (i % 2 == 0) {
                this.inv_ability[b] = ability[i];
                ++b;
            } else {
                this.inv_percen[c] = ability[i];
                ++c;
            }
        }
    }

    public boolean isSameEquip(Equip e) {
        return this.id == e.id && this.type == e.type && this.glass == e.glass;
    }

    public void getShopAtribute(byte[] ability) {
        int b = 0;
        int c = 0;
        for (int i = 0; i < ability.length; ++i) {
            if (i % 2 == 0) {
                this.shop_ability[b] = ability[i];
                ++b;
            } else {
                this.shop_percen[c] = ability[i];
                ++c;
            }
        }
    }

    public void changeToEquip(Equip e) {
        this.icon = e.icon;
        this.glass = e.glass;
        this.type = e.type;
        this.id = e.id;
        this.date = e.date;
        int l = e.x.length;
        this.x = new short[l];
        this.y = new short[l];
        this.w = new byte[l];
        this.h = new byte[l];
        this.dx = new byte[l];
        this.dy = new byte[l];
        for (int i = 0; i < l; ++i) {
            this.x[i] = e.x[i];
            this.y[i] = e.y[i];
            this.dx[i] = e.dx[i];
            this.dy[i] = e.dy[i];
            this.w[i] = e.w[i];
            this.h[i] = e.h[i];
        }
        this.bullet = e.bullet;
        this.frame = e.frame;
        this.addAbilityFromEquip(e);
        this.dbKey = e.dbKey;
        this.level = e.level;
    }

    public String getStrInvDetail() {
        String attribute = "";
        if (this.inv_ability[0] != 0) {
            attribute = String.valueOf(attribute) + Language.sinhluc() + " +" + this.inv_ability[0] + ".";
        }
        if (this.inv_ability[1] != 0) {
            attribute = String.valueOf(attribute) + " " + Language.sucmanh() + " +" + this.inv_ability[1] + ".";
        }
        if (this.inv_ability[2] != 0) {
            attribute = String.valueOf(attribute) + " " + Language.phongthu() + " +" + this.inv_ability[2] + ".";
        }
        if (this.inv_ability[3] != 0) {
            attribute = String.valueOf(attribute) + " " + Language.mayman() + " +" + this.inv_ability[3] + ".";
        }
        if (this.inv_ability[4] != 0) {
            attribute = String.valueOf(attribute) + " " + Language.dongdoi() + " +" + this.inv_ability[4] + ".";
        }
        if (this.vip == 0) {
            if (this.inv_percen[0] != 0) {
                attribute = String.valueOf(attribute) + " " + Language.sinhluc() + " +" + this.inv_percen[0] + "%.";
            }
            if (this.inv_percen[1] != 0) {
                attribute = String.valueOf(attribute) + " " + Language.sucmanh() + " +" + this.inv_percen[1] + "%.";
            }
            if (this.inv_percen[2] != 0) {
                attribute = String.valueOf(attribute) + " " + Language.phongthu() + " +" + this.inv_percen[2] + "%.";
            }
            if (this.inv_percen[3] != 0) {
                attribute = String.valueOf(attribute) + " " + Language.mayman() + " +" + this.inv_percen[3] + "%.";
            }
            if (this.inv_percen[4] != 0) {
                attribute = String.valueOf(attribute) + " " + Language.dongdoi() + " +" + this.inv_percen[4] + "%.";
            }
        } else {
            attribute = String.valueOf(attribute) + " " + Language.All5();
        }
        if (this.date != 0) {
            attribute = String.valueOf(attribute) + " " + Language.expr() + ": " + this.date + ".";
        } else {
            attribute = String.valueOf(attribute) + " H\u1ebft h\u1ea1n s\u1eed d\u1ee5ng .";
        }
        attribute = String.valueOf(attribute) + " " + Language.eSlot() + " " + this.slot + ".";
        return attribute;
    }

    public String getStrShopDetail() {
        String attribute = "";
        this.shopDetailNunmLines = 0;
        this.shopDetailNunStrs = new Vector<String>();
        if (this.shop_ability[0] != 0) {
            attribute = String.valueOf(attribute) + Language.sinhluc() + " +" + this.shop_ability[0] + " ";
            this.shopDetailNunStrs.addElement(String.valueOf(Language.sinhluc()) + " +" + this.shop_ability[0] + " ");
            ++this.shopDetailNunmLines;
        }
        if (this.shop_ability[1] != 0) {
            ++this.shopDetailNunmLines;
            attribute = String.valueOf(attribute) + " " + Language.sucmanh() + " +" + this.shop_ability[1] + " ";
            this.shopDetailNunStrs.addElement(" " + Language.sucmanh() + " +" + this.shop_ability[1] + " ");
        }
        if (this.shop_ability[2] != 0) {
            ++this.shopDetailNunmLines;
            attribute = String.valueOf(attribute) + " " + Language.phongthu() + " +" + this.shop_ability[2] + " ";
            this.shopDetailNunStrs.addElement(" " + Language.phongthu() + " +" + this.shop_ability[2] + " ");
        }
        if (this.shop_ability[3] != 0) {
            ++this.shopDetailNunmLines;
            attribute = String.valueOf(attribute) + " " + Language.mayman() + " +" + this.shop_ability[3] + " ";
            this.shopDetailNunStrs.addElement(" " + Language.mayman() + " +" + this.shop_ability[3] + " ");
        }
        if (this.shop_ability[4] != 0) {
            ++this.shopDetailNunmLines;
            attribute = String.valueOf(attribute) + " " + Language.dongdoi() + " +" + this.shop_ability[4] + " ";
            this.shopDetailNunStrs.addElement(" " + Language.dongdoi() + " +" + this.shop_ability[4] + " ");
        }
        if (this.shop_percen[0] != 0) {
            ++this.shopDetailNunmLines;
            attribute = String.valueOf(attribute) + " " + Language.sinhluc() + " +" + this.shop_percen[0] + "% ";
            this.shopDetailNunStrs.addElement(" " + Language.sinhluc() + " +" + this.shop_percen[0] + "% ");
        }
        if (this.shop_percen[1] != 0) {
            ++this.shopDetailNunmLines;
            attribute = String.valueOf(attribute) + " " + Language.sucmanh() + " +" + this.shop_percen[1] + "% ";
            this.shopDetailNunStrs.addElement(" " + Language.sucmanh() + " +" + this.shop_percen[1] + "% ");
        }
        if (this.shop_percen[2] != 0) {
            ++this.shopDetailNunmLines;
            attribute = String.valueOf(attribute) + " " + Language.phongthu() + " +" + this.shop_percen[2] + "% ";
            this.shopDetailNunStrs.addElement(" " + Language.phongthu() + " +" + this.shop_percen[2] + "% ");
        }
        if (this.shop_percen[3] != 0) {
            ++this.shopDetailNunmLines;
            attribute = String.valueOf(attribute) + " " + Language.mayman() + " +" + this.shop_percen[3] + "% ";
            this.shopDetailNunStrs.addElement(" " + Language.mayman() + " +" + this.shop_percen[3] + "% ");
        }
        if (this.shop_percen[4] != 0) {
            ++this.shopDetailNunmLines;
            attribute = String.valueOf(attribute) + " " + Language.dongdoi() + " +" + this.shop_percen[4] + "%";
            this.shopDetailNunStrs.addElement(String.valueOf(Language.sucmanh()) + " +" + this.shop_ability[1] + " ");
        }
        return attribute;
    }

    public void drawImage(mGraphics g, int Look, int Frame, int X, int Y) {
        if (this.notPaint) {
            return;
        }
        int W = 0;
        int H = 0;
        if (this.glass == 0 || this.glass == 1 || this.glass == 2 || this.glass == 4 || this.glass == 5 || this.glass == 8 || this.glass == 9) {
            W = 24;
            H = 24;
        }
        if (this.glass == 3) {
            W = 30;
            H = 32;
        }
        if (this.glass == 6) {
            W = 29;
            H = 24;
        }
        if (this.glass == 7) {
            W = 32;
            H = 32;
        }
        if (Frame < 6) {
            if (Look == 0) {
                g.drawRegion(PlayerEquip.imgData[this.glass], this.x[Frame], this.y[Frame], this.w[Frame], this.h[Frame], Look, X - W / 2 + (this.dx[Frame] + 18), Y - H + (this.dy[Frame] + 40), 0, false);
                Font.smallFont.drawString(g, " ", X - W / 2 + (this.dx[Frame] + 18), Y - H + (this.dy[Frame] + 40), 0, false);
            }
            if (Look == 2) {
                g.drawRegion(PlayerEquip.imgData[this.glass], this.x[Frame], this.y[Frame], this.w[Frame], this.h[Frame], Look, X + W / 2 - (this.dx[Frame] + 18), Y - H + (this.dy[Frame] + 40), mGraphics.TOP | mGraphics.RIGHT, false);
                Font.smallFont.drawString(g, "  ", X + W / 2 - (this.dx[Frame] + 18), Y - H + (this.dy[Frame] + 40), 0, false);
            }
        }
    }

    public void drawIcon(mGraphics g, int X, int Y, boolean isClip) {
        if (!this.isMaterial) {
            int offset = this.icon * 16;
            int index = 0;
            while (offset >= 1024) {
                ++index;
                offset -= 1024;
            }
            g.drawRegion(EquipScreen.imgIconEQ[index], 0, offset, 16, 16, 0, X, Y, 0, isClip);
        } else {
            if (this.materialIcon != null) {
                g.drawImage(this.materialIcon, X + 8, Y + 8, 3, isClip);
            }
            if (this.num > 1) {
                Font.smallFontYellow.drawString(g, new StringBuilder(String.valueOf(this.num)).toString(), X + 11, Y + 11, 0);
            }
            if ((this.numSelected > 1 && !this.isSelect) || (this.numSelected >= 1 && this.isSelect)) {
                Font.smallFontRed.drawString(g, new StringBuilder(String.valueOf(this.numSelected)).toString(), X + 11, Y, 0);
            }
        }
        if (this.isSelect) {
            g.setColor(0);
            g.drawRect(X, Y, 16, 16, true);
        }
    }
}
