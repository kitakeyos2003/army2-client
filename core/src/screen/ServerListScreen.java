package screen;

import model.Font;
import CLib.mGraphics;
import model.CRes;
import model.GetString;
import network.GameService;
import com.badlogic.gdx.Gdx;
import com.teamobi.mobiarmy2.GameMidlet;
import network.Command;
import network.Session_ME;
import coreLG.CCanvas;
import model.IAction;
import model.Language;

public class ServerListScreen extends CScreen {

    public static String[] nameServer;
    public static String[] address;
    public static boolean[] newServer;
    public static short[] port;
    int selected;
    int yPaint;

    public ServerListScreen() {
        this.yPaint = 0;
        this.indexScreen = 1;
        this.nameCScreen = " ServerListScreen screen!";
        this.center = new Command(Language.select(), new IAction() {
            @Override
            public void perform() {
                String name = String.valueOf(ServerListScreen.nameServer[selected]) + ":" + ServerListScreen.address[selected] + ":" + ServerListScreen.port[selected];
                OnConnectToServer(name);
                (CCanvas.loginScr = new LoginScr()).show();
                Session_ME.gI().start = false;
            }
        });
        this.left = new Command(Language.update(), new IAction() {
            @Override
            public void perform() {
                ServerListScreen.nameServer = null;
                GameMidlet.doUpdateServer();
            }
        });
        this.right = new Command(Language.exit(), new IAction() {
            @Override
            public void perform() {
                Gdx.app.exit();
                System.exit(-1);
            }
        });
    }

    private void OnConnectToServer(String stringConnect) {
        stringConnect.trim();
        String[] svConfig = stringConnect.split(":");
        String nameSv = svConfig[0].trim().toLowerCase();
        String strIPConnect = svConfig[1];
        String strPortConnect = svConfig[2];
        GameMidlet.IP = strIPConnect;
        GameMidlet.PORT = Short.parseShort(strPortConnect);
        Session_ME.gI().connect(GameMidlet.IP, GameMidlet.PORT);
        GameMidlet.serverName = svConfig[0];
        if (GameMidlet.isTeamClient) {
            GameService.gI().setProvider(GameMidlet.PROVIDER);
            GetString str = new GetString();
            GameService.gI().getString("abc");
            GameService.gI().platform_request();
        } else {
            GameMidlet.PROVIDER = (byte) CRes.loadRMSInt("provider");
            GameMidlet.AGENT = CRes.loadRMS_String("agent");
            if (GameMidlet.AGENT == null) {
                GameMidlet.AGENT = "";
            }
            if (GameMidlet.PROVIDER != -1) {
                GameService.gI().setProvider(GameMidlet.PROVIDER);
                GameService.gI().getString(GameMidlet.AGENT);
            }
        }
    }

    @Override
    public void paint(mGraphics g) {
        g.setColor(7852799);
        g.fillRect(0, 0, ServerListScreen.w, ServerListScreen.h, false);
        if (ServerListScreen.nameServer != null) {
            this.yPaint = CCanvas.hieght / 2 - ServerListScreen.ITEM_HEIGHT;
            g.setColor(16767817);
            g.fillRect(0, this.yPaint + this.selected * 20 - 3, CCanvas.width, ServerListScreen.ITEM_HEIGHT, true);
            Font.borderFont.drawString(g, Language.chonmaychu(), CCanvas.width / 2, this.yPaint - ServerListScreen.ITEM_HEIGHT - 5, 3);
            for (int i = 0; i < ServerListScreen.nameServer.length; ++i) {
                if (ServerListScreen.nameServer[i] != null) {
                    Font.normalFont.drawString(g, ServerListScreen.nameServer[i], CCanvas.width / 2, this.yPaint + i * 20, 2);
                }
            }
        }
        super.paint(g);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void onPointerPressed(int x, int y2, int index) {
        super.onPointerPressed(x, y2, index);
    }

    @Override
    public void onPointerReleased(int x, int y2, int index) {
        super.onPointerReleased(x, y2, index);
        if (ServerListScreen.nameServer != null) {
            if (CCanvas.keyPressed[8] || ServerListScreen.keyDown) {
                clearKey();
                ++this.selected;
                if (this.selected > ServerListScreen.nameServer.length - 1) {
                    this.selected = 0;
                }
            } else if (CCanvas.keyPressed[2] || ServerListScreen.keyUp) {
                clearKey();
                --this.selected;
                if (this.selected < 0) {
                    this.selected = ServerListScreen.nameServer.length - 1;
                }
            }
            if (y2 < CCanvas.hieght - ServerListScreen.cmdH) {
                int aa = (y2 - this.yPaint) / 20;
                if (aa == this.selected && CCanvas.isDoubleClick && this.center != null) {
                    this.center.action.perform();
                }
                if (aa >= 0 && aa < ServerListScreen.nameServer.length) {
                    this.selected = aa;
                }
            }
        }
        GameMidlet.server = (byte) ((this.selected >= 3) ? -1 : this.selected);
        if (GameMidlet.versionByte >= 240) {
            GameMidlet.server = 2;
        }
    }

    @Override
    public void show() {
        super.show();
        GameMidlet.loadIP();
    }
}
