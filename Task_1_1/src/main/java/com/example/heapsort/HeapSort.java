package com.example.heapsort;

/**
 * Класс для реализации пирамидальной сортировки (HeapSort)
 * Сложность: O(n log n) во всех случаях
 * @author Student
 * @version 1.0
 */
public class HeapSort {

    /**
     * Сортирует массив с помощью пирамидальной сортировки
     * @param arr массив для сортировки, может быть null
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // Построение max-heap (сложность: O(n))
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Извлечение элементов из кучи (сложность: O(n log n))
        for (int i = n - 1; i > 0; i--) {
            // Перемещаем текущий корень в конец
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Вызываем heapify на уменьшенной куче
            heapify(arr, i, 0);
        }
    }

    /**
     * Преобразует поддерево в max-heap
     * @param arr массив
     * @param n размер кучи
     * @param i корневой индекс
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }

    /**
     * Проверяет, отсортирован ли массив по возрастанию
     * @param arr массив для проверки, может быть null
     * @return true если массив отсортирован или null/пустой, иначе false
     */
    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}