package com.github.mindvr;

import org.flywaydb.core.Flyway;

public class Main {
    public static void main(String[] args) {
        // Configure Flyway with SQLite database
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:sqlite:sample.db", "", "")

                .load();

        // Start the migration
        flyway.migrate();


        System.out.println("Migration successful!");
    }
}