package dev.mindvr.demo;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class Main {
    public static void main(String[] args) {
        System.setProperty("vertxweb.environment", "dev");
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.get("/ping")
                .respond(ctx -> ctx.response()
                        .putHeader("content-type", "text/plain")
                        .end("pong"));
        router.get("/stop")
                .respond(ctx ->
                        ctx.response()
                                .putHeader("content-type", "text/plain")
                                .end("stopping").onSuccess(
                                        x -> vertx.close()
                                ).onSuccess((x) -> System.out.println("Vert.x closed"))
                );
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router)
                .listen(8888)
                .onSuccess(x -> System.out.println("Started HTTP server on " + x.actualPort()));
    }
}