package dev.mindvr.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.client.WebClient;

public class VertxWebClient extends AbstractVerticle {
    @Override
    public void start() {
        WebClient webClient = WebClient.create(vertx);
        vertx.eventBus().consumer("webclient", msg -> {
            webClient.get(8888, "localhost", "/ping")
                    .send()
                    .onSuccess(res -> msg.reply(res.statusCode() + " " + res.bodyAsString()))
                    .onFailure(err -> msg.fail(500, err.getMessage()));
        });
    }
}
