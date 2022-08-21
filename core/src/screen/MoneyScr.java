package screen;

import effect.Cloud;
import CLib.mGraphics;
import model.Font;
import javax.microedition.midlet.MIDlet;
import coreLG.TerrainMidlet;
import CLib.mSystem;
import model.MoneyInfo;
import network.GameService;
import model.CRes;
import network.Command;
import model.Language;
import model.IAction;
import coreLG.CCanvas;
import model.AvatarInfo;
import CLib.mImage;
import java.util.Vector;

public class MoneyScr extends CScreen {

    public static String url_Nap;
    Vector avs;
    public int selected;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    public int xL;
    public static mImage imgCoin;
    int pa;
    boolean trans;

    public boolean isHaveMoneyList() {
        return this.avs != null && this.avs.size() > 0;
    }

    public int priceFromID(int id) {
        for (int i = 0; i < this.avs.size(); ++i) {
            AvatarInfo av = (AvatarInfo) this.avs.elementAt(i);
            if (av.ID == id) {
                return av.price;
            }
        }
        return 0;
    }

    public void startAnimLeft() {
        this.xL = -CCanvas.width;
    }

    public MoneyScr() {
        this.pa = 0;
        this.trans = false;
        this.nameCScreen = " MoneyScr screen!";
        IAction buyAction = new IAction() {
            @Override
            public void perform() {
                doBuy();
            }
        };
        this.center = new Command(Language.select(), buyAction);
        this.right = new Command(Language.close(), new IAction() {
            @Override
            public void perform() {
                CCanvas.menuScr.show();
            }
        });
    }

    private void doLoadCard(final String typeStr, String subTitle) {
        CRes.out(" ====>doLoadCard typeStr " + typeStr.trim());
        final String[] text = new String[2];
        final String[] name = {String.valueOf(Language.seriNumber()) + ":", String.valueOf(Language.pinNumber()) + ":"};
        CCanvas.inputDlg.setInfo(name[0], new IAction() {
            @Override
            public void perform() {
                if (CCanvas.inputDlg.tfInput.getText().equals("")) {
                    CCanvas.startOKDlg(Language.inputSeri(), new IAction() {
                        @Override
                        public void perform() {
                            CCanvas.inputDlg.show();
                        }
                    });
                    return;
                }
                text[0] = CCanvas.inputDlg.tfInput.getText();
                if (typeStr.trim().equals("giftcode")) {
                    GameService.gI().doLoadCard(typeStr.trim(), text[0], "");
                    CCanvas.startOKDlg(Language.pleaseWait());
                    return;
                }
                CCanvas.inputDlg.setInfo(name[1], new IAction() {
                    @Override
                    public void perform() {
                        if (CCanvas.inputDlg.tfInput.getText().equals("")) {
                            CCanvas.startOKDlg(Language.inputPin(), new IAction() {
                                @Override
                                public void perform() {
                                    CCanvas.inputDlg.show();
                                }
                            });
                            return;
                        }
                        text[1] = CCanvas.inputDlg.tfInput.getText();
                        GameService.gI().doLoadCard(typeStr, text[0], text[1]);
                        CCanvas.startOKDlg(Language.pleaseWait());
                    }
                }, new IAction() {
                    @Override
                    public void perform() {
                        CCanvas.endDlg();
                    }
                }, 1);
                CCanvas.inputDlg.show();
            }
        }, new IAction() {
            @Override
            public void perform() {
                CCanvas.endDlg();
            }
        }, 0);
        CCanvas.inputDlg.show();
    }

    protected void doBuy() {
        CRes.err(" ===========================> do buy a product!");
        if (this.avs != null) {
            MoneyInfo mi = (MoneyInfo) this.avs.elementAt(this.selected);
            if (mi.id.equals("napWeb")) {
                if (!CRes.isNullOrEmpty(MoneyScr.url_Nap)) {
                    mSystem.openUrl(MoneyScr.url_Nap);
                }
                return;
            }
            if (mi.smsContent.startsWith("http")) {
                try {
                    MIDlet.platformRequest(String.valueOf(mi.smsContent) + "?game=4&username=" + TerrainMidlet.myInfo.name);
                    CCanvas.startOKDlg(Language.autoOpen());
                } catch (Exception ex) {
                }
            }
            String napthe = "napthe:";
            if (mi.smsContent.indexOf(napthe) != -1) {
                int index = napthe.length();
                String link = mi.smsContent.substring(index);
                CRes.out("=====Str168 = " + link);
                this.doLoadCard(link, mi.info);
                return;
            }
            CCanvas.startWaitDlg(Language.sendMessMoney());
            TerrainMidlet.sendSMS(String.valueOf(mi.smsContent) + TerrainMidlet.myInfo.name, "sms://" + mi.smsTo, new IAction() {
                @Override
                public void perform() {
                    CCanvas.startOKDlg(Language.sendMoneySucc());
                }
            }, new IAction() {
                @Override
                public void perform() {
                    CCanvas.startOKDlg(Language.sendSMSFail());
                }
            });
            if (mi.smsContent.indexOf("http://") != -1) {
                final String link2 = Font.replace(mi.smsContent, "@username", TerrainMidlet.myInfo.name);
                CCanvas.startOKDlg(Language.wantExit(), new IAction() {
                    @Override
                    public void perform() {
                        try {
                            mSystem.connectHTTP(link2);
                            TerrainMidlet.instance.notifyDestroyed();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                return;
            }
            if (mi.smsContent.indexOf("napthe:") != -1) {
                String str = mi.smsContent.substring(0, mi.smsContent.indexOf("napthe:") + "napthe:".length());
                String link = Font.replace(mi.smsContent, str, "");
                CRes.out("=====Str200 = " + str);
                CRes.out("=====link200 = " + link);
                this.doLoadCard(link, mi.info);
                return;
            }
            CCanvas.startOKDlg(Language.sendMessMoney());
            GameService.gI().requestChargeMoneyInfo2((byte) 1, this.getSelectMoney().id);
        }
    }

    public MoneyInfo getSelectMoney() {
        if (this.avs == null) {
            return null;
        }
        return (MoneyInfo) this.avs.elementAt(this.selected);
    }

    public void showInputCard() {
    }

    public void startAnimRight() {
        this.xL = CCanvas.width << 1;
    }

    @Override
    public void moveCamera() {
        if (this.cmy != this.cmtoY) {
            this.cmvy = this.cmtoY - this.cmy << 2;
            this.cmdy += this.cmvy;
            this.cmy += this.cmdy >> 4;
            this.cmdy &= 0xF;
        }
    }

    @Override
    public void paint(mGraphics g) {
        CScreen.paintDefaultBg(g);
        Cloud.paintCloud(g);
        for (int i = 0; i <= CCanvas.width; i += 32) {
            g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
        }
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        g.translate(this.xL, 0);
        Font.bigFont.drawString(g, Language.charge(), 10, 3, 0);
        g.setColor(1407674);
        g.fillRect(0, 25, CCanvas.width, MoneyScr.ITEM_HEIGHT, false);
        Font.normalYFont.drawString(g, Language.payMethod(), 10, 28, 0);
        this.paintRichList(g);
        super.paint(g);
    }

    private void paintRichList(mGraphics g) {
        if (this.avs == null) {
            return;
        }
        if (this.avs.size() <= 0) {
            return;
        }
        g.translate(0, MoneyScr.ITEM_HEIGHT + 25);
        g.setClip(0, 1, CCanvas.width, CCanvas.hieght - 25 - 28 - MoneyScr.ITEM_HEIGHT);
        g.translate(0, -this.cmy);
        int y = 0;
        for (int i = 0; i < this.avs.size(); ++i) {
            if (i == this.selected) {
                g.setColor(16765440);
                g.fillRect(0, y, CCanvas.width, 20, true);
            }
            MoneyInfo avi = (MoneyInfo) this.avs.elementAt(i);
            g.drawImage(MoneyScr.imgCoin, 10, y + 2, 0, true);
            Font.borderFont.drawString(g, avi.info, 40, y + 2, 0, true);
            if (!CCanvas.isTouch) {
                y += 20;
            } else {
                y += 30;
            }
        }
    }

    @Override
    public void onPointerPressed(int xPress, int yPress, int index) {
        super.onPointerPressed(xPress, yPress, index);
    }

    @Override
    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
        if (this.avs == null) {
            return;
        }
        if (!this.trans) {
            this.pa = this.cmy;
            this.trans = true;
        }
        this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag);
        if (this.cmtoY < 0) {
            this.cmtoY = 0;
        }
        if (this.cmtoY > this.cmyLim) {
            this.cmtoY = this.cmyLim;
        }
        if (this.selected >= this.avs.size() - 1 || this.selected == 0) {
            this.cmy = this.cmtoY;
        }
    }

    @Override
    public void onPointerHold(int x, int y2, int index) {
        super.onPointerHold(x, y2, index);
    }

    @Override
    public void onPointerReleased(int xReleased, int yReleased, int index) {
        super.onPointerReleased(xReleased, yReleased, index);
        if (this.avs == null) {
            return;
        }
        if (CCanvas.isPointerDown[index]) {
            if (!this.trans) {
                this.pa = this.cmy;
                this.trans = true;
            }
            this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yReleased);
            if (this.cmtoY < 0) {
                this.cmtoY = 0;
            }
            if (this.cmtoY > this.cmyLim) {
                this.cmtoY = this.cmyLim;
            }
            if (this.selected >= this.avs.size() - 1 || this.selected == 0) {
                this.cmy = this.cmtoY;
            }
        }
        this.trans = false;
        int aa = (this.cmtoY + yReleased - MoneyScr.ITEM_HEIGHT - 25) / 30;
        if (aa == this.selected) {
            if (this.left != null && CCanvas.isDoubleClick) {
                this.left.action.perform();
            }
            if (CCanvas.isDoubleClick) {
                this.center.action.perform();
            }
        }
        if (aa >= 0 && aa < this.avs.size()) {
            this.selected = aa;
        }
    }

    @Override
    public void update() {
        if (this.xL != 0) {
            this.xL += -this.xL >> 1;
        }
        if (this.xL == -1) {
            this.xL = 0;
        }
        this.moveCamera();
        Cloud.updateCloud();
    }

    public void setMoneyList(Vector avatarList) {
        this.avs = avatarList;
    }

    public void setAvatarList(Vector avatarList) {
        this.avs = avatarList;
        this.selected = 0;
        int n = 0;
        this.cmtoY = n;
        this.cmy = n;
        this.cmyLim = avatarList.size() * 20 - (CCanvas.hh - 40);
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }
    }

    static {
        try {
            MoneyScr.imgCoin = mImage.createImage("/coin.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
