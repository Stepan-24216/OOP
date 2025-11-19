package org.example;

/**
 * Класс ячейки с значением и ключом.
 */
public class Entry<K, V> {
    private final K key;
    private V value;
    private Entry<K, V> next;

    /**
     * Конструктор ячейки.
     */
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    /**
     * Получение ключа.
     */
    public K getKey() {
        return key;
    }

    /**
     * Получение значения.
     */
    public V getValue() {
        return value;
    }

    /**
     * Присвоить значение.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Получение следущего в цепочке элемента.
     */
    public Entry<K, V> getNext() {
        return next;
    }

    /**
     * Присвоить следующий элемент.
     */
    public void setNext(Entry<K, V> next) {
        this.next = next;
    }

    /**
     * Строковое представление ячейки.
     */
    @Override
    public String toString() {
        return "{" + key + ": " + value + "} ";
    }
}
