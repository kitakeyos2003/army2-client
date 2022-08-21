package model;

import Equipment.Equip;
import CLib.mImage;
import Equipment.PlayerEquip;
import java.util.Vector;

public class PlayerInfo {

    public String name;
    public int IDDB;
    public int exp;
    public int level2;
    public int level2Percen;
    public int nextExp;
    public int STT;
    public byte index;
    public short point;
    public int win;
    public String aa;
    public int cup;
    public byte gun;
    public short clanID;
    public byte isMaster;
    public final byte YELLOW = 0;
    public final byte GREEN = 1;
    public final byte RED = 2;
    public short[][] equipID;
    public short[][] equipVipID;
    public short[] gunDame;
    public short[] attribute;
    public byte[] UpOrDown1;
    public byte[] UpOrDown2;
    public int[] attAddPoint1;
    public int[] attAddPoint2;
    public boolean isVip;
    public boolean isReady;
    public int xu;
    public int luong;
    public int nQuanHam2;
    public Vector itemME;
    public boolean isBoss;
    public int maxHP;
    public LevelDetail level;
    public byte lvl;
    public byte lvl2;
    public short[] ability;
    public String[] detail;
    public PlayerEquip myEquip;
    public PlayerEquip myVipEquip;
    public int[] dbKey;
    public static String[] strLevelCaption;
    public static int[] levelCaption;
    public mImage clanIcon;
    public static int vipID;
    public String clanContribute1;
    public String clanContribute2;

    public PlayerInfo() {
        this.IDDB = -1;
        this.equipID = new short[10][5];
        this.equipVipID = new short[10][5];
        this.gunDame = new short[10];
        this.attribute = new short[5];
        this.UpOrDown1 = new byte[5];
        this.UpOrDown2 = new byte[5];
        this.attAddPoint1 = new int[5];
        this.attAddPoint2 = new int[5];
        this.level = new LevelDetail();
        this.ability = new short[5];
        this.detail = new String[5];
        this.dbKey = new int[5];
    }

    public void getAttribute() {
        for (int i = 0; i < this.ability.length; ++i) {
            this.attribute[i] = this.ability[i];
            this.attAddPoint1[i] = 0;
            this.attAddPoint2[i] = 0;
        }
    }

    public void setAllEquipEffect() {
        this.getAttribute();
        for (int i = 0; i < 5; ++i) {
            if (this.myEquip.equips[i] != null) {
                this.myEquip.equips[i].setInvAtribute();
                this.addCurrEquip(this.myEquip.equips[i]);
            }
        }
    }

    public void getMyEquip(int index) {
        CRes.err(" ================> getMyEquip() " + index);
        short[] sung = {this.gun, 0, this.equipID[this.gun][0]};
        short[] non = {this.gun, 1, this.equipID[this.gun][1]};
        short[] giap = {this.gun, 2, this.equipID[this.gun][2]};
        short[] kinh = {this.gun, 3, this.equipID[this.gun][3]};
        short[] canh = {this.gun, 4, this.equipID[this.gun][4]};
        this.myEquip = new PlayerEquip(new short[][]{sung, non, giap, kinh, canh});
    }

    public void getVipEquip() {
        short[] sung = {this.gun, 0, this.equipVipID[this.gun][0]};
        short[] non = {this.gun, 1, this.equipVipID[this.gun][1]};
        short[] giap = {this.gun, 2, this.equipVipID[this.gun][2]};
        short[] kinh = {this.gun, 3, this.equipVipID[this.gun][3]};
        short[] canh = {this.gun, 4, this.equipVipID[this.gun][4]};
        this.myVipEquip = new PlayerEquip(new short[][]{sung, non, giap, kinh, canh});
    }

    public String getStrMoney() {
        return String.valueOf(this.xu) + Language.xu();
    }

    public String getStrMoney2() {
        return String.valueOf(this.xu) + Language.xu();
    }

    public void getAddAttPoint(Equip eq) {
        for (int i = 0; i < 5; ++i) {
            this.attAddPoint1[i] = eq.inv_ability[i];
            this.attAddPoint2[i] = eq.inv_percen[i];
        }
    }

    public void clearAttAddPoint() {
        for (int i = 0; i < 5; ++i) {
            this.attAddPoint1[i] = 0;
            this.attAddPoint2[i] = 0;
        }
    }

    public void addCurrEquip(Equip eq) {
        for (int i = 0; i < 5; ++i) {
            short[] attribute = this.attribute;
            int n = i;
            short[] array = attribute;
            int n2 = n;
            array[n2] += eq.inv_attAddPoint[i];
        }
    }

    public void addChangeEquip(Equip eqSelect, Equip lastEquip) {
        for (int i = 0; i < 5; ++i) {
            short attLast = 0;
            if (lastEquip != null) {
                attLast = lastEquip.inv_attAddPoint[i];
            }
            short attSel = 0;
            if (eqSelect != null) {
                attSel = eqSelect.inv_attAddPoint[i];
            }
            this.attAddPoint1[i] = 0;
            this.attAddPoint2[i] = 0;
            this.UpOrDown1[i] = 0;
            this.UpOrDown2[i] = 0;
        }
    }

    public void compareEquip(Equip eqSelect, Equip lastEquip) {
        byte[] a = eqSelect.inv_ability;
        byte[] b = null;
        byte[] c = eqSelect.inv_percen;
        byte[] d = null;
        if (lastEquip != null) {
            b = lastEquip.inv_ability;
            d = lastEquip.inv_percen;
        }
        for (int i = 0; i < 5; ++i) {
            int attSl = 0;
            int percenSl = 0;
            if (eqSelect != null) {
                attSl = a[i];
                percenSl = c[i];
            }
            int attLast = 0;
            int percenLast = 0;
            if (lastEquip != null) {
                attLast = b[i];
                percenLast = d[i];
            }
            if (lastEquip == null) {
                lastEquip = new Equip();
                for (int k = 0; k < 5; ++k) {
                    lastEquip.inv_ability[i] = 0;
                    lastEquip.inv_percen[i] = 0;
                }
                b = lastEquip.inv_ability;
                this.attAddPoint1[i] = attSl - b[i];
                d = lastEquip.inv_percen;
                this.attAddPoint2[i] = percenSl - d[i];
            } else {
                this.attAddPoint1[i] = attSl - attLast;
                this.attAddPoint2[i] = percenSl - percenLast;
            }
            if (this.attAddPoint1[i] == 0) {
                this.UpOrDown1[i] = 0;
            } else if (this.attAddPoint1[i] > 0) {
                this.UpOrDown1[i] = 1;
            } else {
                this.UpOrDown1[i] = 2;
            }
            if (this.attAddPoint2[i] == 0) {
                this.UpOrDown2[i] = 0;
            } else if (this.attAddPoint2[i] > 0) {
                this.UpOrDown2[i] = 1;
            } else {
                this.UpOrDown2[i] = 2;
            }
        }
    }

    public void getQuanHam() {
        for (int i = 0; i < PlayerInfo.levelCaption.length; ++i) {
            if (this.level2 >= PlayerInfo.levelCaption[i]) {
                this.nQuanHam2 = i;
                break;
            }
        }
    }

    public int getExp() {
        return this.exp;
    }
}
