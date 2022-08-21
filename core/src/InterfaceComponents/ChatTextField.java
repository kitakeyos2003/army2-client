package InterfaceComponents;

import CLib.mGraphics;
import coreLG.CCanvas;
import CLib.TField;

public class ChatTextField {

    public TField tfChat;
    public static ChatTextField instance;
    public static boolean isShow;
    byte typeTF;

    public static ChatTextField gI() {
        return (ChatTextField.instance == null) ? (ChatTextField.instance = new ChatTextField()) : ChatTextField.instance;
    }

    public void setChat(byte type) {
        ChatTextField.isShow = !ChatTextField.isShow;
        this.typeTF = type;
        if (ChatTextField.isShow) {
            this.tfChat.setPoiter();
        }
    }

    public void commandTab(int index, int subIndex) {
        switch (index) {
            case 0: {
                this.tfChat.setText("");
                ChatTextField.isShow = false;
                if (!CCanvas.isTouch) {
                    this.tfChat.setFocus(true);
                    break;
                }
                break;
            }
            case 1: {
                this.sendChat();
                break;
            }
        }
    }

    protected ChatTextField() {
    }

    public void init() {
        this.tfChat.width = CCanvas.hw;
    }

    public void keyPressed(int keyCode) {
        this.tfChat.keyPressed(keyCode);
    }

    public void updatekey() {
        this.tfChat.update();
    }

    public void paint(mGraphics g) {
        this.tfChat.paint(g);
    }

    public void updatePointer() {
    }

    public void sendChat() {
        ChatTextField.isShow = false;
    }

    static {
        ChatTextField.isShow = false;
    }
}
