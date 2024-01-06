package dev.mindvr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TabulatorTest {
    Tabulator tabulator = new Tabulator();

    @ParameterizedTest
    @CsvSource({
            "4, 5, 5, 0",
            "5, 5, 5, 1",
            "6, 5, 5, 1",
            "9, 5, 5, 1",
            "10, 5, 5, 2",
            "11, 5, 5, 2",
            "14, 5, 5, 2",
            "15, 5, 5, 3",
            "16, 5, 5, 3",
    })
    void sumSingle_5_p_5(long t, long tStart, long xIncr, long expected) {
        assertEquals(expected, tabulator.sumSingle(t, tStart, xIncr));
    }

    @Test
    void findLeqIndex() {
        int[] even = {1, 2, 4, 8};
        assertEquals(1, tabulator.findLeqIndex(even, 2));
        assertEquals(1, tabulator.findLeqIndex(even, 3));
        assertEquals(2, tabulator.findLeqIndex(even, 4));
        assertEquals(2, tabulator.findLeqIndex(even, 5));
        assertEquals(3, tabulator.findLeqIndex(even, 8));
        assertEquals(3, tabulator.findLeqIndex(even, 9));

        int[] odd = {1, 2, 4, 8, 10};
        assertEquals(1, tabulator.findLeqIndex(odd, 3));
        assertEquals(1, tabulator.findLeqIndex(odd, 2));
        assertEquals(2, tabulator.findLeqIndex(odd, 4));
        assertEquals(2, tabulator.findLeqIndex(odd, 5));
        assertEquals(3, tabulator.findLeqIndex(odd, 8));
        assertEquals(3, tabulator.findLeqIndex(odd, 9));
        assertEquals(4, tabulator.findLeqIndex(odd, 10));
        assertEquals(4, tabulator.findLeqIndex(odd, 11));
    }

    @Test
    void sumTotal() {
        int x;
        int[] tI;

        // example 1
        tI = new int[]{1, 2, 3, 4, 5};
        x = 5;
        assertEquals(1, tabulator.sumTotal(1, tI, x));
        assertEquals(2, tabulator.sumTotal(2, tI, x));
        assertEquals(3, tabulator.sumTotal(3, tI, x));
        assertEquals(4, tabulator.sumTotal(4, tI, x));
        assertEquals(5, tabulator.sumTotal(5, tI, x));
        assertEquals(6, tabulator.sumTotal(6, tI, x));
        assertEquals(7, tabulator.sumTotal(7, tI, x));
        assertEquals(8, tabulator.sumTotal(8, tI, x));
        assertEquals(9, tabulator.sumTotal(9, tI, x));
        assertEquals(10, tabulator.sumTotal(10, tI, x));
        assertEquals(11, tabulator.sumTotal(11, tI, x));

        // example 2
        tI = new int[]{5, 8, 13, 17};
        x = 7;
        assertEquals(12, tabulator.sumTotal(27, tI, x));
        assertEquals(11, tabulator.sumTotal(26, tI, x));
    }

    @Test
    void findT() {
        int x, k;
        int[] tI;

        // example 1
        x = 5;
        k = 10;
        tI = new int[]{1, 2, 3, 4, 5};
        assertEquals(10, tabulator.findT(x, k, tI));

        // example 2
        x = 7;
        k = 12;
        tI = new int[]{5, 8, 13, 17};
        assertEquals(27, tabulator.findT(x, k, tI));

        // max tMax
        x = 1_000_000_000;
        k = x;
        tI = new int[]{1};
        assertEquals((long) (x - 1) * x + 1, tabulator.findT(x, k, tI));
    }
}