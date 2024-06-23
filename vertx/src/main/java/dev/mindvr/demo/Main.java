package dev.mindvr.demo;

import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        System.setProperty("vertxweb.environment", "dev");
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(Server.class.getName())
                .onSuccess(id -> System.out.println("deployed server with id " + id));
        vertx.eventBus().consumer("foo", msg -> {
            msg.reply("ack + " + msg.body());
        });
    }
}