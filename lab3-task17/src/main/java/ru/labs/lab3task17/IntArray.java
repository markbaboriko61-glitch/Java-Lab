package ru.labs.lab3task17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class IntArray {
    private final int[] values;

    public IntArray(int[] values) {
        this.values = Objects.requireNonNull(values).clone();
    }

    public int[] toArray() {
        return values.clone();
    }

    private void swap(int i, int j) {
        int temp = values[i];
        values[i] = values[j];
        values[j] = temp;
    }

    public void bubbleSort() {
        for (int end = values.length - 1; end > 0; end--) {
            boolean changed = false;
            for (int i = 0; i < end; i++) {
                if (values[i] > values[i + 1]) {
                    swap(i, i + 1);
                    changed = true;
                }
            }
            if (!changed) {
                break;
            }
        }
    }

    public void shakerSort() {
        int left = 0, right = values.length - 1;
        while (left < right) {
            boolean changed = false;
            for (int i = left; i < right; i++) {
                if (values[i] > values[i + 1]) {
                    swap(i, i + 1);
                    changed = true;
                }
            }
            right--;
            for (int i = right; i > left; i--) {
                if (values[i - 1] > values[i]) {
                    swap(i - 1, i);
                    changed = true;
                }
            }
            left++;
            if (!changed) {
                break;
            }
        }
    }

    public void selectionSort() {
        for (int i = 0; i < values.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] < values[min]) {
                    min = j;
                }
            }
            swap(i, min);
        }
    }

    public void addressSort() {
        if (values.length < 2) {
            return;
        }
        int min = values[0], max = values[0];
        for (int value : values) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        if (min == max) {
            return;
        }
        int bucketCount = Math.min(values.length, 1024);
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        long span = (long) max - min;
        for (int value : values) {
            int address = (int) (((long) value - min) * (bucketCount - 1) / span);
            List<Integer> bucket = buckets.get(address);
            int position = bucket.size();
            bucket.add(value);
            while (position > 0 && bucket.get(position - 1) > value) {
                bucket.set(position, bucket.get(position - 1));
                position--;
            }
            bucket.set(position, value);
        }
        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int value : bucket) {
                values[index++] = value;
            }
        }
    }

    public void insertionSort() {
        for (int i = 1; i < values.length; i++) {
            int value = values[i], j = i - 1;
            while (j >= 0 && values[j] > value) {
                values[j + 1] = values[j];
                j--;
            }
            values[j + 1] = value;
        }
    }

    public void mergeSort() {
        mergeSort(0, values.length, new int[values.length]);
    }

    private void mergeSort(int left, int right, int[] buffer) {
        if (right - left < 2) {
            return;
        }
        int middle = left + (right - left) / 2;
        mergeSort(left, middle, buffer);
        mergeSort(middle, right, buffer);
        int i = left, j = middle, k = left;
        while (i < middle && j < right) {
            buffer[k++] = values[i] <= values[j] ? values[i++] : values[j++];
        }
        while (i < middle) {
            buffer[k++] = values[i++];
        }
        while (j < right) {
            buffer[k++] = values[j++];
        }
        System.arraycopy(buffer, left, values, left, right - left);
    }

    public void shellSort() {
        for (int gap = values.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < values.length; i++) {
                int value = values[i], j = i;
                while (j >= gap && values[j - gap] > value) {
                    values[j] = values[j - gap];
                    j -= gap;
                }
                values[j] = value;
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
