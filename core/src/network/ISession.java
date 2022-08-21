package network;

public interface ISession {

    boolean isConnected();

    void setHandler(IMessageHandler p0);

    void connect(String p0, String p1);

    void sendMessage(Message p0);
}
