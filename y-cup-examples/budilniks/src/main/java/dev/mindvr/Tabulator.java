package dev.mindvr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Tabulator {
    private int step(long v) {
        return v < 0 ? 0 : 1;
    }

    public long sumSingle(long t, long tStart, long xIncr) {
        return step(t - tStart) + (t - tStart) / xIncr;
    }

    // input is presumed to be pre-processed
    public long findT(int x, int k, int[] tI) {
        long l = 0;
        long r = tI[tI.length - 1] + (long) k * x / (tI.length);
        long tRes = -1;
        long t, vT;
        int i = 0;
        while (l <= r) {
            i++;
            t = l + (r - l) / 2;
            vT = sumTotal(t, tI, x);
            if (vT == k) {
                tRes = t;
                r = t - 1;
            } else if (vT < k) {
                l = t + 1;
            } else {
                r = t - 1;
            }
        }
        System.out.println("cycles: " + i);
        return tRes;
    }

    public long sumTotal(long t, int[] tI, int x) {
        int m = findLeqIndex(tI, t) + 1;
        long sum = m;
        for (int i = 0; i < m; i++) {
            sum += (t - tI[i]) / x;
        }
        return sum;
    }


    int findLeqIndex(int[] sortedUnique, long value) {
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

    int[] preprocess(int[] raw, int k) {
        Set<Integer> remainders = new HashSet<>();
        return Arrays.stream(raw)
                .sorted()
                .filter(v -> remainders.add(v % k))
                .toArray();
    }
}
