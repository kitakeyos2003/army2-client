package model;

import CLib.Image;
import java.util.Vector;

public class IconManager {

    public Vector<Clan> icon;

    public IconManager() {
        this.icon = new Vector<>();
    }

    public void addIcon(Clan clan) {
        if (this.icon.size() < 20) {
            this.icon.addElement(clan);
            CRes.out("================> IconManager add icon");
        } else {
            this.icon.removeElementAt(19);
            this.icon.insertElementAt(clan, 0);
        }
    }

    public void test() {
        int a = 0;
        int b = 0;
    }

    public boolean isExist(int clanID) {
        if (this.icon.size() == 0) {
            return false;
        }
        for (int i = 0; i < this.icon.size(); ++i) {
            Clan cl = this.icon.elementAt(i);
            if (cl.id == clanID) {
                return true;
            }
        }
        return false;
    }

    public Image getImage(int clanID) {
        for (int i = 0; i < this.icon.size(); ++i) {
            Clan _clan = this.icon.elementAt(i);
            if (_clan.id == clanID) {
                return _clan.icon.image;
            }
        }
        return null;
    }
}
