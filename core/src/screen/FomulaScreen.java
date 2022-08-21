package screen;

import CLib.mGraphics;
import network.GameService;
import coreLG.CCanvas;
import network.Command;
import model.IAction;
import model.Language;
import model.Fomula;
import java.util.Vector;

public class FomulaScreen extends CScreen {

    public Vector fomulas;
    public int select;
    public CScreen lastScr;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    int pa;
    boolean trans;

    public FomulaScreen() {
        this.fomulas = new Vector();
        this.select = 0;
        this.pa = 0;
        this.trans = false;
    }

    public void setFomula(Fomula f) {
        this.fomulas.addElement(f);
    }

    @Override
    public void show() {
    }

    @Override
    public void show(CScreen last) {
        this.nameCScreen = "FomulaScreen screen!";
        this.lastScr = last;
        this.commandInit();
        super.show();
    }

    @Override
    public void moveCamera() {
        if (this.cmy != this.cmtoY) {
            this.cmvy = this.cmtoY - this.cmy << 2;
            this.cmdy += this.cmvy;
            this.cmy += this.cmdy >> 4;
            this.cmdy &= 0xF;
        }
    }

    public void input() {
    }

    public void commandInit() {
        this.right = new Command(Language.back(), new IAction() {
            @Override
            public void perform() {
                lastScr.show();
            }
        });
        this.left = new Command("Menu", new IAction() {
            @Override
            public void perform() {
                Vector menu = new Vector();
                for (int i = 0; i < fomulas.size(); ++i) {
                    final int dem = i;
                    menu.addElement(new Command(String.valueOf(Language.congthuccap()) + " " + (i + 1), new IAction() {
                        @Override
                        public void perform() {
                            select = dem;
                            commandInit();
                        }
                    }));
                }
                CCanvas.menu.startAt(menu, 0);
            }
        });
        if (((Fomula) this.fomulas.elementAt(this.select)).finish) {
            this.center = new Command(Language.chedo(), new IAction() {
                @Override
                public void perform() {
                    Fomula f = (Fomula) fomulas.elementAt(select);
                    GameService.gI().getFomula((byte) f.ID, (byte) 2, (byte) select);
                }
            });
        } else {
            this.center = null;
        }
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        g.translate(0, -this.cmy);
        ((Fomula) this.fomulas.elementAt(this.select)).paint(g);
        super.paint(g);
    }

    @Override
    public void update() {
        this.moveCamera();
        ((Fomula) this.fomulas.elementAt(this.select)).update();
        this.cmyLim = 105 + this.fomulas.size() * 30 - (CCanvas.hieght - FomulaScreen.cmdH - 25);
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }
    }

    public Fomula getFomula(int index) {
        return (Fomula) this.fomulas.elementAt(index);
    }
}
