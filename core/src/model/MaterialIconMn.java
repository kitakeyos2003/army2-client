package model;

import CLib.mImage;
import java.util.Vector;

public class MaterialIconMn {

    public static Vector<ImageIcon> icons;

    public static void addIcon(ImageIcon img) {
        MaterialIconMn.icons.addElement(img);
    }

    public static boolean isExistIcon(int id) {
        for (int i = 0; i < MaterialIconMn.icons.size(); ++i) {
            if (MaterialIconMn.icons.elementAt(i).id == id) {
                return true;
            }
        }
        return false;
    }

    public static mImage getImageFromID(int id) {
        for (int i = 0; i < MaterialIconMn.icons.size(); ++i) {
            if (MaterialIconMn.icons.elementAt(i).id == id) {
                return MaterialIconMn.icons.elementAt(i).img;
            }
        }
        return null;
    }

    static {
        MaterialIconMn.icons = new Vector<ImageIcon>();
    }
}
