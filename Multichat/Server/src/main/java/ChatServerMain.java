import context.ApplicationContext;
import context.ApplicationContextReflectionBased;
import server.MultiClientServer;

public class ChatServerMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContextReflectionBased();
        MultiClientServer multiClientServer = applicationContext.getComponent(MultiClientServer.class, "multiClientServer");
        multiClientServer.start(Integer.parseInt(args[0]));
    }
}
