package dev.mindvr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static long findT(int x, int k, int[] tI) {
        long l = 0;
        long r = tI[tI.length - 1] + (long) k * x / (tI.length);
        long tRes = -1;
        long t, vT;
        while (l <= r) {
            t = l + (r - l) / 2;
            vT = sum(t, tI, x);
            if (vT == k) {
                tRes = t;
                r = t - 1;
            } else if (vT < k) {
                l = t + 1;
            } else {
                r = t - 1;
            }
        }
        return tRes;
    }

    static long sum(long t, int[] tI, int x) {
        int m = findLeqIndex(tI, t) + 1;
        long sum = m;
        for (int i = 0; i < m; i++) {
            sum += (t - tI[i]) / x;
        }
        return sum;
    }


    static int findLeqIndex(int[] sortedUnique, long value) {
        int l, r, m, res;
        l = 0;
        res = -1;
        r = sortedUnique.length - 1;
        while (l <= r) {
            m = l + (r - l) / 2;
            if (sortedUnique[m] <= value) {
                res = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return res;
    }

    static int[] preprocess(int[] raw, int x) {
        Set<Integer> remainders = new HashSet<>();
        return Arrays.stream(raw)
                .sorted()
                .filter(v -> remainders.add(v % x))
                .toArray();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int x = scanner.nextInt();
            int k = scanner.nextInt();
            scanner.nextLine();

            if (n < 1 || n > 100_000
                    || x < 1 || x > 1000_000_000
                    || k < 1 || k > 1000_000_000
            ) {
                throw new IllegalArgumentException();
            }
            String tIStr = scanner.nextLine();
            String[] parts = tIStr.split(" ");
            int[] tI = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                tI[i] = Integer.parseInt(parts[i]);
            }
            if (tI.length != n) {
                throw new IllegalArgumentException();
            }

            long t = findT(x, k, preprocess(tI, x));
            System.out.println(t);
        }
    }
}