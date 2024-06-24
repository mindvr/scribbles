package dev.mindvr.demo;

import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        System.setProperty("vertxweb.environment", "dev");
        Vertx vertx = Vertx.vertx();
        deploy(vertx, Server.class);
        deploy(vertx, VertxWebClient.class);
        deploy(vertx, MutinyWebClient.class);
    }

    private static void deploy(Vertx vertx, Class<?> clazz) {
        vertx.deployVerticle(clazz.getName())
                .onSuccess(id -> System.out.println("deployed " + clazz.getSimpleName()));
    }
}