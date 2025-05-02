package dev.mindvr.demo;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniSubscribe;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.mutiny.core.eventbus.Message;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;

public class MutinyWebClient extends AbstractVerticle {
    private WebClient webClient;

    @Override
    public void start() {
        this.webClient = WebClient.create(vertx);
        vertx.eventBus().consumer("vertxclient")
                .handler(this::handleMessage);
    }

    private void handleMessage(Message<?> msg) {
        webClient.get(8888, "localhost", "/ping")
                .send()
                .onItem()
                .transform(resp -> resp.statusCode() + " " + resp.bodyAsString())
                .onFailure()
                .invoke(err -> msg.fail(500, err.getMessage()))
                .subscribe()
                .with(msg::reply);
    }
}
