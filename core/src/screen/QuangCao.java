package screen;

import CLib.mGraphics;
import network.Command;
import model.IAction;
import model.Language;
import coreLG.CCanvas;
import model.Font;

public class QuangCao extends CScreen {

    public static String content;
    public static String link;
    public static String[] contents;

    public QuangCao() {
        this.nameCScreen = " QuangCao screen!";
    }

    public void getCommand() {
        QuangCao.contents = Font.borderFont.splitFontBStrInLine(QuangCao.content, CCanvas.width - 30);
        this.right = new Command(Language.exit(), new IAction() {
            @Override
            public void perform() {
                CCanvas.menuScr.show();
            }
        });
        this.center = new Command("T\u1ea3i game", new IAction() {
            @Override
            public void perform() {
            }
        });
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        if (QuangCao.contents != null) {
            for (int i = 0; i < QuangCao.contents.length; ++i) {
                Font.borderFont.drawString(g, QuangCao.contents[i], CCanvas.width / 2, 30 + i * 20, 2);
            }
        }
        super.paint(g);
    }
}
