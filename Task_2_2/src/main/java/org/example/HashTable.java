package org.example;

import java.util.Iterator;
import java.util.ConcurrentModificationException;

/**
 * Класс Хэш-Таблицы.
 */
public class HashTable<K, V> implements Iterable<Entry<K, V>> {

    private Entry<K, V>[] table;
    private int size;
    private int capacity;

    /**
     * Конструктор Хэш-Таблицы.
     */
    public HashTable() {
        this.capacity = 16;
        this.table = (Entry<K, V>[]) new Entry[this.capacity];
        this.size = 0;
    }

    /**
     * Гетер для получения размера.
     */
    public int getSize() {
        return size;
    }

    /**
     * Гетер для получения размера выделенного места.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Добавить элемент.
     */
    public void put(K key, V value) {
        int index = Math.abs(key.hashCode()) % capacity;
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.setNext(table[index]);
        table[index] = newEntry;
        size++;
        resize();
    }

    /**
     * Удаление элемента.
     */
    public void deleteEntry(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        if (capacity <= 0 || table == null || entry == null) {
            return;
        }
        int index = Math.abs(entry.getKey().hashCode()) % capacity;
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;
        while (current != null) {
            if (current.getKey().equals(entry.getKey()) &&
                current.getValue().equals(entry.getValue())) {
                if (previous == null) {
                    table[index] = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                return;
            }
            previous = current;
            current = current.getNext();
        }
    }

    /**
     * Обновить значение.
     */
    public void update(K key, V value) {
        if (capacity <= 0 || table == null || key == null) {
            return;
        }
        int index = Math.abs(key.hashCode()) % capacity;
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }

        put(key, value);
    }

    /**
     * Проверка соответствия ключа и значения.
     */
    public boolean checkEntry(K key, V value) {
        if (capacity <= 0 || table == null || key == null) {
            return false;
        }
        int index = Math.abs(key.hashCode()) % capacity;
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key) && current.getValue().equals(value)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Получение значения.
     */
    public V get(K key) {
        if (capacity <= 0 || table == null || key == null) {
            return null;
        }
        int index = Math.abs(key.hashCode()) % capacity;
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Проверка на надобность расширения таблицы.
     */
    private void resize() {
        double loadFactor = 0.75;
        if (size >= capacity * loadFactor) {
            capacity = capacity * 2;
            Entry<K, V>[] newTable = new Entry[capacity];
            for (Entry<K, V> entry : table) {
                while (entry != null) {
                    int index = Math.abs(entry.getKey().hashCode()) % capacity;
                    Entry<K, V> nextEntry = entry.getNext();
                    entry.setNext(newTable[index]);
                    newTable[index] = entry;
                    entry = nextEntry;
                }
            }
            table = newTable;
        }
    }

    /**
     * Сравнение двух Хэш-таблиц.
     */
    public boolean equals(HashTable<K, V> table) {
        for (Entry<K, V> e : this) {
            if (!table.checkEntry(e.getKey(), e.getValue())) {
                return false;
            }
        }
        for (Entry<K, V> e : table) {
            if (!this.checkEntry(e.getKey(), e.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Получение строки с хранящимися значениями.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HashTable[");
        for (Entry<K, V> entry : this) {
            sb.append(entry.toString()).append(", ");
        }
        if (size > 0) {
            sb.setLength(sb.length() - 2); // Удаляем последнюю запятую
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Итератор.
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private final int expectedSize = size;
            private int currentIndex = 0;
            private Entry<K, V> currentEntry = null;

            @Override
            public boolean hasNext() {
                if (expectedSize != size) {
                    throw new ConcurrentModificationException();
                }

                while (currentIndex < capacity) {
                    if (currentEntry != null) {
                        return true;
                    }
                    currentEntry = table[currentIndex++];
                    if (currentEntry != null) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {
                if (expectedSize != size) {
                    throw new ConcurrentModificationException();
                }
                if (currentEntry == null) {
                    while (currentIndex < capacity && table[currentIndex] == null) {
                        currentIndex++;
                    }
                    if (currentIndex < capacity) {
                        currentEntry = table[currentIndex++];
                    } else {
                        return null;
                    }
                }
                Entry<K, V> entryToReturn = currentEntry;
                currentEntry = currentEntry.getNext();
                return entryToReturn;
            }
        };
    }
}
