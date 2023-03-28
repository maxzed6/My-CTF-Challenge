import controller.IndexController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;


public class MainApplication {
    public static void main(String[] args) {
        Javalin app = Javalin.create()
                .start(8888);
        app.routes(() -> {
            get("/", IndexController.test);
            post("/evil", IndexController.index);
        });
        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
        });
    }
}
