package model;

import java.io.InputStream;

public class GetString {

    public static byte[] array;
    public static String strdata;

    public GetString() {
        InputStream in = this.getClass().getResourceAsStream("/agent.txt");
        try {
            in.read(GetString.array = new byte[in.available()]);
            GetString.strdata = new String(GetString.array, "UTF-8");
        } catch (Exception e) {
            GetString.strdata = "";
        }
    }
}
