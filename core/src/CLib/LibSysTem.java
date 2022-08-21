package CLib;

import com.badlogic.gdx.Gdx;
import java.io.InputStream;

public class LibSysTem {

    public static String res = "res";
    public static String font = "FontSys/x";

    public static InputStream getResourceAsStream(String path) {
        InputStream in = null;
        in = Gdx.files.internal(String.valueOf(LibSysTem.res) + path).read();
        if (in == null) {
            throw new IllegalArgumentException("InputStream cannot found path= " + path);
        }
        return in;
    }

    public static void openWeb(String links) {
        Gdx.net.openURI(links);
    }
}
