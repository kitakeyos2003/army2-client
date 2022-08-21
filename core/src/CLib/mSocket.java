package CLib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import model.CRes;
import java.net.Socket;

public class mSocket {

    Socket _socket;

    public mSocket(String str, int port) {
        try {
            this._socket = new Socket(str, port);
        } catch (ConnectException e) {
            e.printStackTrace();
            CRes.err("====> ConnectException ");
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void close() {
        try {
            if (this._socket != null) {
                this._socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setKeepAlive(boolean isAlive) {
        try {
            if (this._socket != null) {
                this._socket.setKeepAlive(isAlive);
            }
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public DataOutputStream getOutputStream() {
        try {
            DataOutputStream dos = new DataOutputStream(this._socket.getOutputStream());
            return dos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DataInputStream getInputStream() {
        try {
            DataInputStream dis = new DataInputStream(this._socket.getInputStream());
            return dis;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getIP() {
        if (this._socket == null) {
            return "do not connect!";
        }
        return String.valueOf(this._socket.getInetAddress().getHostAddress()) + " " + this._socket.getPort();
    }

    public byte getState() {
        if (this._socket == null) {
            return -1;
        }
        if (this._socket.isInputShutdown()) {
            return 2;
        }
        if (this._socket.isOutputShutdown()) {
            return 3;
        }
        if (this._socket.isConnected()) {
            return 1;
        }
        if (this._socket.isClosed()) {
            return 0;
        }
        return -1;
    }
}
