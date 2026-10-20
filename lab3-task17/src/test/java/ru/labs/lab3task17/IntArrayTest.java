package ru.labs.lab3task17;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

class IntArrayTest {
    private final List<Consumer<IntArray>> algorithms =
            List.of(
                    IntArray::bubbleSort,
                    IntArray::shakerSort,
                    IntArray::selectionSort,
                    IntArray::addressSort,
                    IntArray::insertionSort,
                    IntArray::mergeSort,
                    IntArray::shellSort);

    private void check(int[] source) {
        int[] expected = source.clone();
        Arrays.sort(expected);
        for (Consumer<IntArray> algorithm : algorithms) {
            IntArray array = new IntArray(source);
            algorithm.accept(array);
            assertArrayEquals(expected, array.toArray());
        }
    }

    @Test
    void boundaryCases() {
        check(new int[] {});
        check(new int[] {1});
        check(new int[] {5, 5, 5});
        check(new int[] {1, 2, 3, 4});
        check(new int[] {5, 4, 3, 2, 1});
        check(new int[] {0, Integer.MAX_VALUE, Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE});
    }

    @Test
    void randomArrays() {
        Random random = new Random(17);
        for (int iteration = 0; iteration < 200; iteration++) {
            int[] source = random.ints(random.nextInt(100), -1000, 1001).toArray();
            check(source);
        }
    }

    @Test
    void defensiveCopy() {
        int[] source = {3, 2, 1};
        IntArray array = new IntArray(source);
        source[0] = 100;
        array.toArray()[0] = 200;
        assertArrayEquals(new int[] {3, 2, 1}, array.toArray());
    }
}
