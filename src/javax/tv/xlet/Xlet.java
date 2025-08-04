package javax.tv.xlet;

// Stub interface for compilation outside BD-J environment
public interface Xlet {
    void initXlet(XletContext context);
    void startXlet();
    void pauseXlet();
    void destroyXlet(boolean unconditional);
}