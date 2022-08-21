package model;

import CLib.mImage;
import com.badlogic.gdx.Gdx;
import coreLG.CCanvas;
import java.io.IOException;
import java.io.EOFException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class FilePack {

    private String[] fname;
    private int[] fpos;
    private int[] flen;
    private byte[] fullData;
    private int nFile;
    private int hSize;
    private String name;
    private byte[] code;
    private int codeLen;
    private DataInputStream file;

    public FilePack(byte[] DATA) throws Exception {
        this.code = new byte[]{78, 103, 117, 121, 101, 110, 86, 97, 110, 77, 105, 110, 104};
        this.codeLen = this.code.length;
        ByteArrayInputStream array = new ByteArrayInputStream(DATA);
        this.file = new DataInputStream(array);
        int pos = 0;
        int size = 0;
        this.hSize = 0;
        try {
            this.nFile = this.file.readUnsignedByte();
            ++this.hSize;
            this.fname = new String[this.nFile];
            this.fpos = new int[this.nFile];
            this.flen = new int[this.nFile];
            for (int i = 0; i < this.nFile; ++i) {
                int lname = this.encode(this.file.readByte());
                byte[] data = new byte[lname];
                this.file.read(data);
                this.encode(data);
                this.fname[i] = new String(data);
                this.fpos[i] = pos;
                this.flen[i] = this.encode(this.file.readUnsignedShort());
                pos += this.flen[i];
                size += this.flen[i];
                this.hSize += lname + 3;
            }
            this.fullData = new byte[size];
            this.file.readFully(this.fullData);
            this.encode(this.fullData);
        } catch (EOFException e) {
            CRes.out("=====================>  11Cause: " + e.getClass());
            CRes.out("=====================>  11getStackTrace: " + e.getStackTrace());
            CRes.out("=====================>  11Cause: " + e.getLocalizedMessage());
        } catch (IOException e2) {
            CRes.out("=====================>  Cause: " + e2.getClass());
            CRes.out("=====================>  getStackTrace: " + e2.getStackTrace());
            CRes.out("=====================>  Cause: " + e2.getLocalizedMessage());
        } finally {
            if (this.file != null) {
                this.file.close();
            }
        }
        if (this.file != null) {
            this.file.close();
        }
    }

    public FilePack(String name) throws Exception {
        this.code = new byte[]{78, 103, 117, 121, 101, 110, 86, 97, 110, 77, 105, 110, 104};
        this.codeLen = this.code.length;
        int pos = 0;
        int size = 0;
        this.name = name;
        this.hSize = 0;
        this.open();
        try {
            this.nFile = this.encode(this.file.readUnsignedByte());
            ++this.hSize;
            this.fname = new String[this.nFile];
            this.fpos = new int[this.nFile];
            this.flen = new int[this.nFile];
            for (int i = 0; i < this.nFile; ++i) {
                int lname = this.encode(this.file.readByte());
                byte[] data = new byte[lname];
                this.file.read(data);
                this.encode(data);
                this.fname[i] = new String(data);
                this.fpos[i] = pos;
                this.flen[i] = this.encode(this.file.readUnsignedShort());
                pos += this.flen[i];
                size += this.flen[i];
                this.hSize += lname + 3;
            }
            this.fullData = new byte[size];
            this.file.readFully(this.fullData);
            this.encode(this.fullData);
        } catch (IOException ex) {
            return;
        } finally {
            if (this.file != null) {
                this.file.close();
            }
        }
        if (this.file != null) {
            this.file.close();
        }
    }

    public FilePack(String name, boolean isCheck) {
        this.code = new byte[]{78, 103, 117, 121, 101, 110, 86, 97, 110, 77, 105, 110, 104};
        this.codeLen = this.code.length;
    }

    private int encode(int i) {
        return i;
    }

    private void encode(byte[] bytes) {
        for (int len = bytes.length, i = 0; i < len; ++i) {
            int n = i;
            bytes[n] ^= this.code[i % this.codeLen];
        }
    }

    private void open() {
        try {
            InputStream inp = null;
            if (CCanvas.isPc()) {
                inp = this.getClass().getResourceAsStream(this.name);
            } else {
                inp = Gdx.files.internal(this.name).read();
            }
            if (inp != null) {
                this.file = new DataInputStream(inp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openbyArray(byte[] array) {
        ByteArrayInputStream dataRaw = new ByteArrayInputStream(array);
        this.file = new DataInputStream(dataRaw);
    }

    public byte[] loadFile(String fileName) throws Exception {
        for (int i = 0; i < this.nFile; ++i) {
            if (this.fname[i].compareTo(fileName) == 0) {
                byte[] bytes = new byte[this.flen[i]];
                System.arraycopy(this.fullData, this.fpos[i], bytes, 0, this.flen[i]);
                return bytes;
            }
        }
        throw new Exception("File '" + fileName + "' not found!");
    }

    public mImage loadImage(String fileName) {
        if (fileName == null) {
            return null;
        }
        if (fileName == "") {
            return null;
        }
        for (int i = 0; i < this.nFile; ++i) {
            if (this.fname[i].compareTo(fileName) == 0) {
                return mImage.createImage(this.fullData, this.fpos[i], this.flen[i], fileName);
            }
        }
        return null;
    }

    public mImage loadImage(String fileName, IAction2 action2) {
        for (int i = 0; i < this.nFile; ++i) {
            if (this.fname[i].compareTo(fileName) == 0) {
                return mImage.createImage(this.fullData, this.fpos[i], this.flen[i], action2);
            }
        }
        return null;
    }

    public mImage loadImage(String fileName, boolean isFont) {
        if (!isFont) {
            return null;
        }
        for (int i = 0; i < this.nFile; ++i) {
            if (this.fname[i].compareTo(fileName) == 0) {
                return mImage.createImage(this.fullData, this.fpos[i], this.flen[i], true);
            }
        }
        return null;
    }

    public void LoadAllNameInFile() {
        for (int i = 0; i < this.nFile; ++i) {
            System.out.println("Name File " + this.fname[i]);
        }
    }

    public String FileName(int index) {
        return this.fname[index];
    }

    public int lenght() {
        return this.fname.length;
    }

    public mImage loadImage(String fileName, String name) {
        if (fileName == null) {
            return null;
        }
        if (fileName == "") {
            return null;
        }
        for (int i = 0; i < this.nFile; ++i) {
            if (this.fname[i].compareTo(fileName) == 0) {
                return mImage.createImage(this.fullData, this.fpos[i], this.flen[i], fileName, name);
            }
        }
        return null;
    }
}
