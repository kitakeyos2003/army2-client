package CLib;

import com.badlogic.gdx.Input;

public class MapKey {

    private static mHashtable h;

    public static void load() {
        MapKey.h.put("A", 97);
        MapKey.h.put("B", 98);
        MapKey.h.put("C", 99);
        MapKey.h.put("D", 100);
        MapKey.h.put("E", 101);
        MapKey.h.put("F", 102);
        MapKey.h.put("G", 103);
        MapKey.h.put("H", 104);
        MapKey.h.put("I", 105);
        MapKey.h.put("J", 106);
        MapKey.h.put("K", 107);
        MapKey.h.put("L", 108);
        MapKey.h.put("M", 109);
        MapKey.h.put("N", 110);
        MapKey.h.put("O", 111);
        MapKey.h.put("P", 112);
        MapKey.h.put("Q", 113);
        MapKey.h.put("R", 114);
        MapKey.h.put("S", 115);
        MapKey.h.put("T", 116);
        MapKey.h.put("U", 117);
        MapKey.h.put("V", 118);
        MapKey.h.put("W", 119);
        MapKey.h.put("X", 120);
        MapKey.h.put("Y", 121);
        MapKey.h.put("Z", 122);
        MapKey.h.put("0", 48);
        MapKey.h.put("1", 49);
        MapKey.h.put("2", 50);
        MapKey.h.put("3", 51);
        MapKey.h.put("4", 52);
        MapKey.h.put("5", 53);
        MapKey.h.put("6", 54);
        MapKey.h.put("7", 55);
        MapKey.h.put("8", 56);
        MapKey.h.put("9", 57);
        MapKey.h.put("SPACE", 32);
        MapKey.h.put("F1", -21);
        MapKey.h.put("F2", -22);
        MapKey.h.put("EQUALS", -25);
        MapKey.h.put("MINUS", 45);
        MapKey.h.put("F3", -23);
        MapKey.h.put("UP", -1);
        MapKey.h.put("DOWN", -2);
        MapKey.h.put("LEFT", -3);
        MapKey.h.put("RIGHT", -4);
        MapKey.h.put("BACKSPACE", -8);
        MapKey.h.put("PERIOD", 46);
        MapKey.h.put("AT", 64);
        MapKey.h.put("TAB", -26);
        MapKey.h.put("DELET", -8);
    }

    public static int map(int kyeCode) {
        try {
            String k = Input.Keys.toString(kyeCode).toUpperCase();
            int v = (int) MapKey.h.get(k);
            return v;
        } catch (Exception ex) {
            return 0;
        }
    }

    static {
        MapKey.h = new mHashtable();
    }
}
