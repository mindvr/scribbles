package dev.mindvr;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String J = scanner.nextLine();
            String S = scanner.nextLine();
            Set<Byte> jewels = new HashSet<>();
            for (byte j : J.getBytes(StandardCharsets.UTF_8)) {
                jewels.add(j);
            }
            int count = 0;
            for (byte s : S.getBytes(StandardCharsets.UTF_8)) {
                if (jewels.contains(s)) {
                    count++;
                }
            }
            System.out.println(count);
        }
    }
}