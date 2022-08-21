package com.teamobi.mobiarmy2;

import model.Font;
import network.Message;
import CLib.mSystem;
import javax.microedition.midlet.MIDletStateChangeException;
import CLib.mGraphics;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import model.CRes;
import CLib.RMS;
import screen.ServerListScreen;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import coreLG.CONFIG;
import model.IAction2;
import model.Language;
import java.io.InputStream;
import network.ISession;
import network.GameService;
import network.IMessageHandler;
import network.Session_ME;
import network.IGameLogicHandler;
import network.MessageHandler;
import network.GameLogicHandler;
import android.content.Context;
import model.PlayerInfo;
import coreLG.CCanvas;
import javax.microedition.midlet.MIDlet;

public class GameMidlet extends MIDlet implements IActionListener {

    public static GameMidlet instance;
    public static CCanvas gameCanvas;
    public static String version;
    public static short versionByte;
    public static byte versioncode;
    public static byte server;
    public static String serverName;
    public static int timePingPaint;
    public static int pingCount;
    public static boolean ping;
    public static short versionServer;
    public static boolean isStartGame;
    public static final byte NONE = 0;
    public static final byte NOKIA_STORE = 1;
    public static final byte GOOGLE_STORE = 2;
    public static final byte IOS_STORE = 3;
    public static final byte DEVICE_TYPE_JAVA = 0;
    public static final byte DEVICE_TYPE_ANDROID = 1;
    public static final byte DEVICE_TYPE_IOS = 2;
    public static final byte DEVICE_TYPE_WINPHONE = 3;
    public static final byte DEVICE_TYPE_PC = 4;
    public static final byte DEVICE_TYPE_ANDROID_STORE = 5;
    public static final byte DEVICE_TYPE_IOS_STORE = 6;
    public static final byte DEVICE_TYPE_DEV = 7;
    public static byte DEVICE;
    public static final byte DEVELOPING = 0;
    public static final byte BUILD = 1;
    public static final byte OTHER = 2;
    public static byte COMPILE;
    public static boolean lowGraphic;
    public static byte currentIAPStore;
    public static byte langServer;
    public static byte ZOOM_IOS;
    public static byte PROVIDER;
    public static final byte BIG_PROVIDER = 0;
    public static String IP;
    public static int PORT;
    public static PlayerInfo myInfo;
    public static String AGENT;
    public static byte filePackVersion;
    public static boolean[] isVip;
    public static boolean isTeamClient;
    public static String linkGetHost;
    public static String linkReg;
    public static String latitude;
    public static String longitude;

    public void initGame(Context c) {
        GameMidlet.gameCanvas = new CCanvas(c);
        this.initGame2();
    }

    public void initGame() {
        GameMidlet.gameCanvas = new CCanvas();
        this.initGame2();
    }

    private void initGame2() {
        InputStream in = this.getClass().getResourceAsStream("/provider.txt");
        try {
            byte[] array = new byte[in.available()];
            in.read(array);
            String str = new String(array, "UTF-8");
            GameMidlet.PROVIDER = Byte.parseByte(str);
        } catch (Exception ex) {
        }
        String str2 = GameLogicHandler.loadIP();
        if (str2 != null && str2.length() > 0) {
            try {
                int p = str2.indexOf(":");
                String IP = str2.substring(0, p);
                String port = str2.substring(p + 1);
                GameMidlet.IP = IP;
                GameMidlet.PORT = Integer.parseInt(port);
            } catch (Exception e) {
                System.err.println("===> error midlet connects " + e);
            }
        }
        GameMidlet.gameCanvas.start();
        MessageHandler.gI().setGameLogicHandler(GameLogicHandler.gI());
        Session_ME.gI().setHandler(MessageHandler.gI());
        GameService.gI().setSession(Session_ME.gI());
        setcurrentIAPStore();
    }

    public static void doUpdateServer() {
        CCanvas.startWaitDlg(Language.pleaseWait());
        connectHTTP(GameMidlet.linkGetHost, new IAction2() {
            @Override
            public void perform(Object object) {
                String dataReceive = "";
                if (CCanvas.isDebugging()) {
                    dataReceive = "Trái Đất:27.0.14.68:19149,Sao Hỏa:27.0.14.66:19149";
                }
                if (GameMidlet.versionByte < 240) {
                    dataReceive = "Trái Đất:27.0.14.68:19149,Sao Hỏa:27.0.14.66:19149";
                }
                if (object != null) {
                    dataReceive = (String) object;
                }
                boolean isHaveSunSERVER = false;
                String[] splits = dataReceive.split(",");
                if (splits != null) {
                    for (int i = 0; i < splits.length; ++i) {
                        if (splits[i].trim().regionMatches(0, "Mặt Trời", 0, 8)) {
                            isHaveSunSERVER = true;
                            break;
                        }
                    }
                }
                if (!isHaveSunSERVER) {
                    dataReceive = String.valueOf(dataReceive) + "," + CONFIG.ROOT_SERVER_MATTROI_URL_TEST;
                }
                if (GameMidlet.versionByte >= 240) {
                    dataReceive = CONFIG.ROOT_SERVER_MATTROI_URL_TEST;
                }
                if (CCanvas.isDebugging()) {
                    dataReceive = String.valueOf(dataReceive) + "," + CONFIG.ROOT_LOCAL_URL;
                }
                GameMidlet.getServerList(dataReceive);
                GameMidlet.saveIP();
                CCanvas.closeDlg();
                //CCanvas.startOKDlg(Language.updateServer());// Thông báo: Bản cập nhật mới nhất
            }
        });
    }

    public static void saveIP() {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bo);
        try {
            int leng = ServerListScreen.nameServer.length;
            for (int i = 0; i < ServerListScreen.nameServer.length; ++i) {
                if (GameMidlet.versionByte < 240) {
                    if (ServerListScreen.nameServer[i].equals("Trái Đất")) {
                        --leng;
                    }
                    if (ServerListScreen.nameServer[i].equals("Sao Hỏa")) {
                        --leng;
                    }
                }
                if (ServerListScreen.nameServer[i].equals("LOCAL")) {
                    --leng;
                }
            }
            dos.writeByte(leng);
            for (int i = 0; i < leng; ++i) {
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

    public static void loadIP() {
        byte[] bData = CRes.loadRMSData("ipArmy2");
        if (bData == null) {
            doUpdateServer();
            return;
        }
        CRes.out(" 1 ==================> loadIP");
        ByteArrayInputStream bi = new ByteArrayInputStream(bData);
        DataInputStream dis = new DataInputStream(bi);
        if (dis == null) {
            return;
        }
        try {
            byte len = dis.readByte();
            ServerListScreen.nameServer = new String[len];
            ServerListScreen.address = new String[len];
            ServerListScreen.port = new short[len];
            if (GameMidlet.versionByte < 240) {
                ServerListScreen.nameServer = new String[len + 1];
                ServerListScreen.address = new String[len + 1];
                ServerListScreen.port = new short[len + 1];
            }
            if (GameMidlet.versionByte < 240) {
                ServerListScreen.nameServer[len] = "Mặt Trời";
                ServerListScreen.address[len] = "27.0.12.164";
                ServerListScreen.port[len] = 19149;
            }
            for (int i = 0; i < len; ++i) {
                ServerListScreen.nameServer[i] = dis.readUTF();
                ServerListScreen.address[i] = dis.readUTF();
                ServerListScreen.port[i] = dis.readShort();
            }
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getServerList(String str) {
        String[] temp = CRes.split(str, ",");
        ServerListScreen.nameServer = new String[temp.length];
        ServerListScreen.address = new String[temp.length];
        ServerListScreen.port = new short[temp.length];
        for (int i = 0; i < temp.length; ++i) {
            String tempRaw = temp[i].trim();
            String[] sub = CRes.split(tempRaw, ":");
            ServerListScreen.nameServer[i] = sub[0];
            ServerListScreen.address[i] = sub[1];
            ServerListScreen.port[i] = Short.parseShort(sub[2].trim());
        }
    }

    public static void setZOOM_IOS() {
        GameMidlet.ZOOM_IOS = (byte) mGraphics.zoomLevel;
        int i = 1;
        if (CCanvas.isPc()) {
            i = 1;
        }
        if (mGraphics.zoomLevel > i) {
            GameMidlet.ZOOM_IOS = 2;
        }
    }

    public static void setcurrentIAPStore() {
        if (GameMidlet.DEVICE == 5) {
            GameMidlet.currentIAPStore = 2;
        } else if (GameMidlet.DEVICE == 6) {
            GameMidlet.currentIAPStore = 3;
        } else {
            GameMidlet.currentIAPStore = 0;
        }
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
    }

    public void destroy() {
        try {
            GameMidlet.instance.destroyApp(true);
        } catch (MIDletStateChangeException e) {
            e.printStackTrace();
        }
    }

    protected void pauseApp() {
    }

    public void startApp() throws MIDletStateChangeException {
        if (!GameMidlet.isStartGame) {
            this.initGame();
            GameMidlet.gameCanvas.displayMe(GameMidlet.instance);
            GameMidlet.isStartGame = true;
        }
    }

    public static void exit() {
        MotherCanvas.bRun = false;
        System.gc();
        notifyDestroyed();
    }

    @Override
    public void perform(int idAction, Object p) {
    }

    public static void openUrl(String url) {
        mSystem.openUrl(url);
    }

    public static String loginPlus() {
        return "";
    }

    public static String connectHTTP(String url) {
        return mSystem.connectHTTP(url);
    }

    public static void connectHTTP(String url, IAction2 action2) {
        mSystem.connectHTTP(url, action2);
    }

    public void CheckPerGPS() {
        this.getLocation();
    }

    public void getLocation() {
        GameMidlet.longitude = "";
        GameMidlet.latitude = "";
    }

    public static void handleMessage(Message msg) {
        try {
            msg.reader().readUTF();
        } catch (Exception ex) {
        }
    }

    public static void handleAllMessage() {
    }

    public static void serverInformation(Font paint, mGraphics g) {
        paint.drawString(g, GameMidlet.serverName, CCanvas.width - 2 - paint.getWidth(GameMidlet.serverName), 2, 0,
                false);
        paint.drawString(g, GameMidlet.version, CCanvas.width - 2 - paint.getWidth(GameMidlet.version),
                2 + paint.getHeight(), 0, false);
        if (CCanvas.isDebugging()) {
            paint.drawString(g, new StringBuilder(String.valueOf(GameMidlet.timePingPaint)).toString(),
                    CCanvas.width - 2 - paint.getWidth(GameMidlet.version), 2 + paint.getHeight() * 2, 0, false);
        }
    }

    static {
        GameMidlet.instance = new GameMidlet();
        GameMidlet.version = "2.4.1";
        GameMidlet.versionByte = 241;
        GameMidlet.versioncode = 11;
        GameMidlet.server = -2;
        GameMidlet.versionServer = 3;
        GameMidlet.DEVICE = 4;
        GameMidlet.COMPILE = 1;
        GameMidlet.lowGraphic = false;
        GameMidlet.currentIAPStore = 0;
        GameMidlet.langServer = 0;
        GameMidlet.ZOOM_IOS = 1;
        GameMidlet.PROVIDER = 0;
        GameMidlet.IP = "192.168.1.88";
        GameMidlet.PORT = 19152;
        GameMidlet.isVip = new boolean[10];
        GameMidlet.isTeamClient = true;
        GameMidlet.linkGetHost = "http://gmb.teamobi.com/srvip/army2list.txt";
        GameMidlet.linkReg = "http://my.teamobi.com/app/view/register.php";
        GameMidlet.latitude = "";
        GameMidlet.longitude = "";
    }
}
