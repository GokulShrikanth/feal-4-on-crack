package com.blaze;

import io.vertx.core.Vertx;

public class MainApplication {
        public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new feal4());
    }
}
