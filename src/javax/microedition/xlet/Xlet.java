package javax.microedition.xlet;

// Stub interface for compilation outside BD-J environment
public interface Xlet {
    void initXlet(XletContext context) throws XletStateChangeException;
    void startXlet() throws XletStateChangeException;
    void pauseXlet();
    void destroyXlet(boolean unconditional) throws XletStateChangeException;
}