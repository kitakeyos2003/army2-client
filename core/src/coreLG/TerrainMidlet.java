package coreLG;

import com.badlogic.gdx.Gdx;
import model.IAction;
import CLib.mSystem;
import CLib.RMS;
import screen.ServerListScreen;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import network.ISession;
import network.GameService;
import network.IMessageHandler;
import network.Session_ME;
import network.IGameLogicHandler;
import network.MessageHandler;
import network.GameLogicHandler;
import model.CRes;
import model.PlayerInfo;
import com.teamobi.mobiarmy2.TemCanvas;

public class TerrainMidlet {

    public static final String version = "2.4.1";
    public static byte PROVIDER;
    public static final byte BIG_PROVIDER = 0;
    public static TemCanvas temCanvas;
    public static TerrainMidlet instance;
    int a;
    public static int PORT;
    public static String IP;
    public static PlayerInfo myInfo;
    public static String AGENT;
    public static byte filePackVersion;
    public static boolean[] isVip;
    public static boolean isTeamClient;
    public static String linkGetHost;

    public TerrainMidlet() {
        this.a = 0;
        if (TerrainMidlet.temCanvas == null) {
            CRes.init();
            TerrainMidlet.temCanvas = new TemCanvas();
            TerrainMidlet.temCanvas.gamecanvas = new CCanvas();
            TerrainMidlet.temCanvas.start();
        }
        TerrainMidlet.temCanvas.gamecanvas.isRunning = true;
        InputStream in = this.getClass().getResourceAsStream("/provider.txt");
        try {
            byte[] array = new byte[in.available()];
            in.read(array);
            String str = new String(array, "UTF-8");
            TerrainMidlet.PROVIDER = Byte.parseByte(str);
        } catch (Exception ex) {
        }
        String str2 = GameLogicHandler.loadIP();
        if (str2 != null && str2.length() > 0) {
            try {
                int p = str2.indexOf(":");
                String IP = str2.substring(0, p);
                String port = str2.substring(p + 1);
                TerrainMidlet.IP = IP;
                TerrainMidlet.PORT = Integer.parseInt(port);
            } catch (Exception e) {
                System.err.println("===> error midlet connects " + e);
            }
        }
        MessageHandler.gI().setGameLogicHandler(GameLogicHandler.gI());
        Session_ME.gI().setHandler(MessageHandler.gI());
        GameService.gI().setSession(Session_ME.gI());
    }

    public static void saveIP() {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bo);
        try {
            dos.writeByte(ServerListScreen.nameServer.length);
            for (int i = 0; i < ServerListScreen.nameServer.length; ++i) {
                dos.writeUTF(ServerListScreen.nameServer[i]);
                dos.writeUTF(ServerListScreen.address[i]);
                dos.writeShort(ServerListScreen.port[i]);
            }
            try {
                RMS.saveRMS("ipArmy2", bo.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dos.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String[] split(String _text, String _searchStr) {
        int count = 0;
        int pos = 0;
        int searchStringLength = _searchStr.length();
        for (int aa = _text.indexOf(_searchStr, pos); aa != -1; aa = _text.indexOf(_searchStr, pos), ++count) {
            pos = aa + searchStringLength;
        }
        String[] sb = new String[count + 1];
        int searchStringPos;
        int startPos;
        int index;
        for (searchStringPos = _text.indexOf(_searchStr), startPos = 0, index = 0; searchStringPos != -1; searchStringPos = _text.indexOf(_searchStr, startPos), ++index) {
            sb[index] = _text.substring(startPos, searchStringPos);
            startPos = searchStringPos + searchStringLength;
        }
        sb[index] = _text.substring(startPos, _text.length());
        return sb;
    }

    public static String connectHTTP(String url) {
        return mSystem.connectHTTP(url);
    }

    protected void destroyApp(boolean arg0) {
        if (TerrainMidlet.temCanvas != null) {
            TerrainMidlet.temCanvas.gamecanvas.stopGame();
            TerrainMidlet.temCanvas = null;
        }
        this.notifyDestroyed();
    }

    public void showMyCanvas() {
    }

    protected void pauseApp() {
        this.notifyPaused();
    }

    protected void startApp() {
        this.showMyCanvas();
        TerrainMidlet.instance = this;
    }

    public static void exit() {
        TerrainMidlet.instance.destroyApp(false);
    }

    public static void sendSMS(String data, String to, IAction successAction, IAction failAction) {
    }

    public static void vibrate(int s) {
        try {
            Gdx.input.vibrate(CRes.abs(s * 10));
        } catch (SecurityException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyDestroyed() {
        Gdx.app.exit();
    }

    public void notifyPaused() {
    }

    static {
        TerrainMidlet.PORT = 19152;
        TerrainMidlet.IP = "192.168.1.88";
        TerrainMidlet.isVip = new boolean[10];
        TerrainMidlet.isTeamClient = true;
        TerrainMidlet.linkGetHost = "http://gmb.teamobi.com/srvip/army2list.txt";
    }
}
