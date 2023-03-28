package controller;

import io.javalin.http.Handler;
//import javassist.ClassPool;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

import static io.javalin.apibuilder.ApiBuilder.ws;

public class IndexController {
    public static Handler index = ctx -> {
        String base64 = ctx.formParam("data");
        byte[] bytes = Base64.getDecoder().decode(base64);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        ois.readObject();
        };

    public static Handler test = ctx -> {
        ctx.result("I have some deep dark fantasy");
    };
}
