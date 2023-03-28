import io.javalin.websocket.WsConfig;
import java.util.function.Consumer;

public class WsShell implements Consumer<WsConfig> {
    @Override
    public void accept(WsConfig wsConfig) {
        wsConfig.onMessage(new MyHandler());
    }
}
