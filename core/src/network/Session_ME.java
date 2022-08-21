package network;

import java.io.EOFException;
import CLib.mSystem;
import CLib.mVector;
import java.util.concurrent.ArrayBlockingQueue;
import java.net.SocketException;
import java.io.IOException;
import coreLG.CCanvas;
import model.Language;
import model.CRes;
import java.util.concurrent.BlockingQueue;
import CLib.mSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Session_ME implements ISession {

    protected static Session_ME instance;
    private final Sender sender;
    private DataOutputStream dos;
    public DataInputStream dis;
    public static IMessageHandler messageHandler;
    private static mSocket _mSocket;
    public boolean connected;
    public boolean connecting;
    public boolean start;
    public Thread initThread;
    public Thread collectorThread;
    public Thread sendThread;
    public int sendByteCount;
    public int recvByteCount;
    private boolean getKeyComplete;
    public byte[] key;
    private byte curR;
    private byte curW;
    private long timeConnected;
    public String strRecvByteCount;
    public static BlockingQueue<Message> recieveMsg;
    public static int receiveSynchronized;
    public static int countRead;
    private int errip;
    public static String h;
    public static int p;
    public static boolean isCancel;
    private int countMsg;
    int err3;

    public Session_ME() {
        this.sender = new Sender();
        this.start = true;
        this.key = null;
        this.strRecvByteCount = "";
        this.errip = 0;
        this.countMsg = 0;
        this.err3 = 0;
    }

    public static Session_ME gI() {
        return instance;
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

    @Override
    public void setHandler(IMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void connect(String host, int port) {
        h = host;
        p = port;
        CRes.out("==========================> connect to  " + this.connected + " _ " + this.connecting);
        if (this.connected || this.connecting) {
            return;
        }
        CRes.out("==========================> connect to  " + host + " _ " + port);
        if (_mSocket != null && _mSocket.getState() == 1) {
            this.close(0);
        }
        this.sender.removeAllMessage();
        this.getKeyComplete = false;
    _mSocket = null;
        (this.initThread = new Thread(new NetworkInit(host, port))).start();
    }

    public void onRecieveMsg(Message msg) {
        recieveMsg.offer(msg);
    }

    public static void update() {
        if (gI().connecting && !gI().start && _mSocket == null) {
            CCanvas.startWaitDlgWithoutCancel(Language.connecting(), 11111);
            return;
        }
        if (receiveSynchronized <= 0 && recieveMsg.size() > 0) {
            Message msg = recieveMsg.poll();
            if (msg == null) {
                return;
            }
            messageHandler.onMessage(msg);
        }
    }

    @Override
    public void sendMessage(Message message) {
        this.sender.AddMessage(message);
    }

    private synchronized void doSendMessage(Message m) throws IOException {
        byte[] data = m.getData();
        try {
            if (this.getKeyComplete) {
                byte b = this.writeKey(m.command);
                this.dos.writeByte(b);
                CRes.err("send cmd " + m.command + " _ " + b);
            } else {
                this.dos.writeByte(m.command);
            }
            if (data != null) {
                int size = data.length;
                if (this.getKeyComplete) {
                    int byte1 = this.writeKey((byte) (size >> 8));
                    this.dos.writeByte(byte1);
                    int byte2 = this.writeKey((byte) (size & 0xFF));
                    this.dos.writeByte(byte2);
                } else {
                    this.dos.writeShort(size);
                }
                if (this.getKeyComplete) {
                    for (int i = 0; i < data.length; ++i) {
                        data[i] = this.writeKey(data[i]);
                    }
                }
                this.dos.write(data);
                this.sendByteCount += 5 + data.length;
            } else {
                this.dos.writeShort(0);
                this.sendByteCount += 5;
            }
            this.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte readKey(byte b) {
        this.err3 = 2;
        byte[] key = this.key;
        byte curR = this.curR;
        this.curR = (byte) (curR + 1);
        byte i = (byte) ((key[curR] & 0xFF) ^ (b & 0xFF));
        this.err3 = 4;
        if (this.curR >= this.key.length) {
            this.curR %= (byte) this.key.length;
        }
        return i;
    }

    private byte writeKey(byte b) {
        this.err3 = 2;
        byte[] key = this.key;
        byte curW = this.curW;
        this.curW = (byte) (curW + 1);
        byte i = (byte) ((key[curW] & 0xFF) ^ (b & 0xFF));
        this.err3 = 5;
        if (this.curW >= this.key.length) {
            this.curW %= (byte) this.key.length;
        }
        return i;
    }

    private Message readMessage2(byte cmd) throws Exception {
        try {
            int datalen = this.dis.readInt();
            if (datalen > 0) {
                byte[] data = new byte[datalen];
                for (int len = 0, byteRead = 0; len != -1
                        && byteRead < datalen; byteRead += len, this.recvByteCount += 5 + byteRead) {
                    len = this.dis.read(data, byteRead, datalen - byteRead);
                    if (len > 0) {
                    }
                }
                Message msg = new Message(cmd, data);
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            CRes.out(new StringBuilder().append(e.getCause()).toString());
        }
        return null;
    }

    public Message readMessage3(byte cmd) {
        try {
            short datalen = this.dis.readShort();
            CRes.err("===========. readMessage3 dataLen = " + datalen);
            if (datalen > 0) {
                byte[] data = new byte[datalen];
                for (int len = 0, byteRead = 0; len != -1
                        && byteRead < datalen; byteRead += len, this.recvByteCount += 5 + byteRead) {
                    len = this.dis.read(data, byteRead, datalen - byteRead);
                    if (len > 0) {
                    }
                }
                Message msg = new Message(cmd, data);
                CRes.out("==========> readmessage 3 BigImage " + data.length);
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            CRes.out(new StringBuilder().append(e.getCause()).toString());
        }
        return null;
    }

    @Override
    public void connect(String host, String port) {
        Session_ME.h = host;
        p = Short.parseShort(port);
        if (this.connected || this.connecting) {
            return;
        }
        this.sender.removeAllMessage();
        this.getKeyComplete = false;
        _mSocket = null;
        (this.initThread = new Thread(new NetworkInit(host, Short.parseShort(port)))).start();
    }

    public void close(int index) {
        CRes.out("==========================> Clean network " + index);
        this.cleanNetwork();
    }

    private void cleanNetwork() {
        this.key = null;
        this.curR = 0;
        this.curW = 0;
        try {
            recieveMsg.clear();
            this.connected = false;
            this.connecting = false;
            if (_mSocket != null) {
                _mSocket.close();
                _mSocket = null;
            }
            if (this.dos != null) {
                this.dos.close();
                this.dos = null;
            }
            if (this.dis != null) {
                this.dis.close();
                this.dis = null;
            }
            this.sendThread = null;
            this.collectorThread = null;
            if (this.initThread != null && this.initThread.isAlive()) {
                this.initThread.interrupt();
                this.initThread = null;
            }
            System.gc();
        } catch (SocketException socketException) {
            _mSocket = null;
            recieveMsg.clear();
            System.gc();
            socketException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        instance = new Session_ME();
        recieveMsg = new ArrayBlockingQueue<Message>(1000);
        receiveSynchronized = 0;
        countRead = 0;
        h = "";
    }

    private class Sender implements Runnable {

        public final mVector sendingMessage;
        int iErrIp;

        public Sender() {
            this.iErrIp = 0;
            this.sendingMessage = new mVector();
        }

        public void AddMessage(Message message) {
            this.sendingMessage.addElement(message);
        }

        public void removeAllMessage() {
            if (this.sendingMessage != null) {
                this.sendingMessage.removeAllElements();
            }
        }

        @Override
        public void run() {
            try {
                this.iErrIp = 0;
                while (connected) {
                    this.iErrIp = 1;
                    if (getKeyComplete) {
                        while (this.sendingMessage.size() > 0) {
                            this.iErrIp = 2;
                            Message m = (Message) this.sendingMessage.elementAt(0);
                            this.iErrIp = 300 + m.command;
                            this.sendingMessage.removeElementAt(0);
                            this.iErrIp = 400 + m.command;
                            doSendMessage(m);
                            this.iErrIp = 500 + m.command;
                        }
                    }
                    try {
                        this.iErrIp = 6;
                        Thread.sleep(10L);
                        this.iErrIp = 7;
                    } catch (InterruptedException e) {
                        this.iErrIp = 8;
                    }
                }
                this.iErrIp = 9;
            } catch (SocketException socketException) {
                socketException.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    private class MessageCollector implements Runnable {

        @Override
        public void run() {
            errip = 0;
            try {
                errip = 1;
                try {
                    errip = 2;
                    while (isConnected()) {
                        errip = 3;
                        Message message = this.readMessage();
                        if (message == null) {
                            errip = 1200 + message.command;
                            break;
                        }
                        CRes.out("Receive message " + message.command);
                        errip = 4;
                        ;
                        try {
                            errip = 500 + message.command;
                            if (message.command == -27) {
                                errip = 600 + message.command;
                                this.getKey(message);
                                errip = 700 + message.command;
                            } else {
                                errip = 800 + message.command;
                                onRecieveMsg(message);
                                errip = 900 + message.command;
                            }
                        } catch (Exception e) {
                            errip = 1000 + message.command;
                            e.printStackTrace();
                            errip = 1100 + message.command;
                        }
                        try {
                            errip = 6 + message.command;
                            Thread.sleep(100L);
                            errip = 7 + message.command;
                        } catch (InterruptedException e2) {
                            errip = 8 + message.command;
                        }
                    }
                    errip = 13;
                } catch (Exception ex) {
                    errip = 14;
                }
                errip = 15;
                if (connected) {
                    errip = 16;
                    if (messageHandler != null) {
                        errip = 17;
                        if (mSystem.currentTimeMillis() - timeConnected > 500L) {
                            messageHandler.onDisconnected();
                        } else {
                            messageHandler.onConnectionFail();
                        }
                        errip = 18;
                    }
                    errip = 19;
                    if (_mSocket != null) {
                        close(1);
                    }
                    errip = 20;
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }

        private void getKey(Message message) throws IOException {
            byte keySize = message.reader().readByte();
            key = new byte[keySize];
            for (int i = 0; i < keySize; ++i) {
                key[i] = message.reader().readByte();
            }
            for (int i = 0; i < key.length - 1; ++i) {
                key[i + 1] ^= key[i];
            }
            String keyS = "";
            for (int j = 0; j < key.length - 1; ++j) {
                keyS = String.valueOf(keyS) + key[j] + "_";
            }
            CRes.out("======> key is " + keyS);
            getKeyComplete = true;
        }

        private Message readMessage() throws Exception {
            try {
                ++countRead;
                byte cmd = dis.readByte();
                if (getKeyComplete) {
                    cmd = readKey(cmd);
                }
                CRes.out("Receive 1 cmd " + cmd);
                if (cmd == -120 || cmd == 90) {
                    return readMessage2(cmd);
                }
                int size;
                if (getKeyComplete) {
                    byte b1 = dis.readByte();
                    byte b2 = dis.readByte();
                    size = ((readKey(b1) & 0xFF) << 8 | (readKey(b2) & 0xFF));
                } else {
                    size = dis.readUnsignedShort();
                }
                byte[] data = new byte[size];
                for (int len = 0, byteRead = 0; len != -1 && byteRead < size; byteRead += len, recvByteCount += 5
                        + byteRead) {
                    len = dis.read(data, byteRead, size - byteRead);
                    if (len > 0) {
                    }
                }
                if (getKeyComplete) {
                    for (int i = 0; i < data.length; ++i) {
                        data[i] = readKey(data[i]);
                    }
                }
                Message msg = new Message(cmd, data);
                return msg;
            } catch (EOFException e) {
                CRes.out("====> Session readMessage() method  EOF exception ");
            } catch (Exception e2) {
                CRes.out("exception ");
            }
            return null;
        }
    }

    private class NetworkInit implements Runnable {

        private final String host;
        private final int port;
        int iErr;

        NetworkInit(String host, int port) {
            this.iErr = 0;
            this.host = host;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                this.iErr = 0;
                Session_ME.isCancel = false;
                this.iErr = 1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        iErr = 3;
                        try {
                            iErr = 4;
                            Thread.sleep(20000L);
                        } catch (InterruptedException e) {
                            iErr = 5;
                        }
                        if (connecting) {
                            iErr = 6;
                            try {
                                iErr = 7;
                                _mSocket.close();
                            } catch (Exception e2) {
                                iErr = 8;
                            }
                            iErr = 9;
                            isCancel = true;
                            connecting = false;
                            connected = false;
                            iErr = 10;
                            messageHandler.onConnectionFail();
                            iErr = 11;
                        }
                    }
                }).start();
                this.iErr = 12;
                connecting = true;
                Thread.currentThread().setPriority(1);
                this.iErr = 13;
                connected = true;
                this.iErr = 14;
                try {
                    this.iErr = 15;
                    CRes.out("1 do connect host =========> " + this.host + " __ " + this.port);
                    this.doConnect(this.host, this.port);
                    this.iErr = 16;
                    messageHandler.onConnectOK();
                    CCanvas.endDlg();
                    this.iErr = 17;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    this.iErr = 18;
                    try {
                        this.iErr = 19;
                        Thread.sleep(500L);
                        this.iErr = 20;
                    } catch (InterruptedException e) {
                        this.iErr = 21;
                    }
                    this.iErr = 22;
                    if (isCancel) {
                        return;
                    }
                    this.iErr = 23;
                    if (messageHandler != null) {
                        this.iErr = 24;
                        close(2);
                        this.iErr = 25;
                        messageHandler.onConnectionFail();
                        this.iErr = 26;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                CRes.err("throw Exception!!!!!");
            }
        }

        public void doConnect(String host, int port) throws Exception {
            _mSocket = new mSocket(host, port);
            if (_mSocket != null) {
                _mSocket.setKeepAlive(true);
                dos = _mSocket.getOutputStream();
                dis = _mSocket.getInputStream();
                new Thread(sender).start();
                (collectorThread = new Thread(new MessageCollector())).start();
                timeConnected = mSystem.currentTimeMillis();
                doSendMessage(new Message(-27));
                connecting = false;
                start = false;
            }
        }
    }
}
