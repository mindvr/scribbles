package dev.mindvr.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

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
        router.post("/dispatch").respond(this::dispatch);
        return router;
    }

    private Future<Void> dispatch(RoutingContext ctx) {
        return ctx.request().body()
                .andThen(AsyncResult::result)
                .map(Buffer::toJsonObject)
                .flatMap(obj -> {
                    var topic = obj.getString("topic");
                    var message = obj.getString("message");
                    return vertx.eventBus().request(topic, message);
                }).andThen(AsyncResult::result)
                .map(msg -> (String) msg.body())
                .flatMap(reply -> ctx.response()
                        .putHeader("content-type", "text/plain")
                        .end(reply)
                );
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
