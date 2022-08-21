package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

public class Message {

    public byte command;
    private ByteArrayOutputStream os;
    private DataOutputStream dos;
    private ByteArrayInputStream iss;
    private DataInputStream dis;

    public Message() {
        this.os = null;
        this.dos = null;
        this.iss = null;
        this.dis = null;
    }

    public Message(int command) {
        this.os = null;
        this.dos = null;
        this.iss = null;
        this.dis = null;
        this.command = (byte) command;
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.os);
    }

    public Message(byte command) {
        this.os = null;
        this.dos = null;
        this.iss = null;
        this.dis = null;
        this.command = command;
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.os);
    }

    public Message(byte command, byte[] data) {
        this.os = null;
        this.dos = null;
        this.iss = null;
        this.dis = null;
        this.command = command;
        this.iss = new ByteArrayInputStream(data);
        this.dis = new DataInputStream(this.iss);
    }

    public byte[] getData() {
        return this.os.toByteArray();
    }

    public DataInputStream reader() {
        return this.dis;
    }

    public DataOutputStream writer() {
        return this.dos;
    }

    public void cleanup() {
        try {
            if (this.dis != null) {
                this.dis.close();
            }
            if (this.dos != null) {
                this.dos.close();
            }
        } catch (IOException ex) {
        }
    }
}
