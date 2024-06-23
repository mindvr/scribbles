package dev.mindvr.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.function.Function;

public class Server extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router())
                .listen(8888)
                .onSuccess(x -> System.out.println("Started HTTP server on " + x.actualPort()))
                .andThen(x -> startPromise.complete());
    }

    private Router router() {
        Router router = Router.router(vertx);
        router.get("/ping").respond(this::ping);
        router.get("/stop").respond(this::stop);
        return router;
    }

    private Future<Void> ping(RoutingContext ctx) {
        return ctx.response()
                .putHeader("content-type", "text/plain")
                .end("pong");
    }

    private Future<Void> stop(RoutingContext ctx) {
        return ctx.response()
                .putHeader("content-type", "text/plain")
                .end("stopping")
                .onSuccess(x -> vertx.close())
                .onSuccess((x) -> System.out.println("Vert.x closed"));
    }
}
