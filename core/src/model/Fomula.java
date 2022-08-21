package model;

import coreLG.TerrainMidlet;
import coreLG.CCanvas;
import CLib.mGraphics;
import Equipment.Equip;
import CLib.mImage;

public class Fomula {

    public String[] numMaterial;
    public mImage[] imgMaterial;
    public int[] idImage;
    public Equip equipRequire;
    public boolean isHave;
    public int levelRequire;
    public Equip e;
    public Equip eRequire;
    public String[] materialName;
    public String[] ability;
    public boolean finish;
    public int ID;
    public int h1;
    public int h2;

    public mImage getImageMaterial(int id) {
        for (int i = 0; i < this.idImage.length; ++i) {
            if (this.idImage[i] == id) {
                return this.imgMaterial[i];
            }
        }
        return null;
    }

    public void paint(mGraphics g) {
        Font.bigFont.drawString(g, Language.congthucchetao(), CCanvas.width / 2, 5, 3);
        if (this.e != null) {
            Font.normalFont.drawString(g, this.e.name, CCanvas.width / 2, 30, 3);
            this.e.drawIcon(g, CCanvas.width / 2 + Font.normalFont.getWidth(this.e.name) / 2 + 10, 30, false);
        }
        for (int i = 0; i < this.ability.length; ++i) {
            Font.borderFont.drawString(g, this.ability[i], CCanvas.width / 2, 50 + i * 18, 3);
        }
        int myLevel = TerrainMidlet.myInfo.level2;
        Font.borderFont.drawString(g, String.valueOf(Language.levelRequire()) + ": " + myLevel + "/" + this.levelRequire, CCanvas.width / 2, this.h1 + 53, 3);
        this.equipRequire.drawIcon(g, CCanvas.width / 2 - 70 - 8, this.h1 + 78 - 8, false);
        Font.normalFont.drawString(g, "1x " + this.equipRequire.name, CCanvas.width / 2 - 55, this.h1 + 71, 0);
        Font.normalFont.drawString(g, this.isHave ? "1/1" : "0/1", CCanvas.width / 2 + 70, this.h1 + 71, 3);
        for (int j = 0; j < this.imgMaterial.length; ++j) {
            if (this.imgMaterial[j] != null) {
                g.drawImage(this.imgMaterial[j], CCanvas.width / 2 - 70, this.h1 + 97 + j * 18, 3, false);
            }
            Font.normalFont.drawString(g, this.materialName[j], CCanvas.width / 2 - 55, this.h1 + 89 + j * 18, 0);
            Font.normalFont.drawString(g, this.numMaterial[j], CCanvas.width / 2 + 70, this.h1 + 89 + j * 18, 3);
        }
    }

    public void update() {
    }

    public void input() {
    }
}
