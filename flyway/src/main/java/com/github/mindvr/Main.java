package com.github.mindvr;

import org.flywaydb.core.Flyway;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        boolean shouldClear = Arrays.asList(args).contains("--clear");
        String path = args[args.length - 1];

        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:sqlite:sample.db", "", "")
                .locations("classpath:migrations/" + path)
                .cleanDisabled(false)
                .load();
        if (shouldClear) {
            flyway.clean();
        }

        flyway.migrate();
    }
}