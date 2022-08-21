package com.teamobi.mobiarmy2;

import network.Message;
import CLib.mSystem;
import CLib.RMS;
import com.badlogic.gdx.Gdx;

public class TemMidlet {

    public static TemCanvas temCanvas;
    public static TemMidlet instance;
    public static byte DIVICE;
    public static final byte NONE = 0;
    public static final byte NOKIA_STORE = 1;
    public static final byte GOOGLE_STORE = 2;
    public static byte currentIAPStore;
    public static boolean isBlockNOKIAStore;
    public static byte langServer;
    public static final String[] productIds;
    public static String[] listGems;
    public static final String[] google_productIds;
    public static String[] google_listGems;

    public TemMidlet() {
        TemMidlet.instance = this;
        (TemMidlet.temCanvas = new TemCanvas()).start();
    }

    protected void destroyApp(boolean arg0) {
    }

    public static void makePurchase(String productId) {
    }

    protected void pauseApp() {
    }

    protected void startApp() {
    }

    public void destroy() {
        Gdx.app.exit();
    }

    public static byte[] encoding(byte[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                int n = i;
                array[n] ^= -1;
            }
        }
        return array;
    }

    public static byte[] loadRMS(String filename) {
        return RMS.loadRMS(filename);
    }

    public static void openUrl(String url) {
        mSystem.openUrl(url);
    }

    public static void delRMS() {
    }

    public static String connectHTTP(String url) {
        return mSystem.connectHTTP(url);
    }

    public static void handleMessage(Message msg) {
    }

    public void call(String num) {
    }

    public static void submitPurchase() {
    }

    public static void handleAllMessage(Message msg) {
    }

    static {
        TemMidlet.DIVICE = 2;
        TemMidlet.currentIAPStore = 0;
        TemMidlet.isBlockNOKIAStore = true;
        TemMidlet.langServer = 0;
        productIds = new String[]{"1311457"};
        TemMidlet.listGems = new String[]{"24 Gems"};
        google_productIds = new String[]{"hs_gold_10_2", "hs_gold_30_2", "hs_gold_70_2", "hs_gold_180_2", "hs_gold_380_2", "hs_gold_800_2"};
        TemMidlet.google_listGems = new String[]{"24 Gems ($0.99)", "84 Gems ($2.99)", "150 Gems ($4.99)", "350 Gems ($9.99)", "1.000 Gems ($24.99)", "2.500 Gems ($49.99)"};
    }
}
