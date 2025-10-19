package org.example;

import java.util.Iterator;
import java.util.ConcurrentModificationException;

public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {

    private Entry<K, V>[] table;
    private int size;
    private int capacity;

    public static class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void setNext(Entry<K, V> next) {
            this.next = next;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void print(){
            System.out.println("{" + key + ": " + value + "} ");
        }
    }

    public HashTable(int initialCapacity){
        this.table = (Entry<K, V>[]) new Entry[initialCapacity];
        size = 0;
        capacity = initialCapacity;
    }

    public void put(K key, V value){
        int index = Math.abs(key.hashCode()) % capacity;
        Entry<K, V> current = table[index];
        while (current.getNext() != null) {
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

    public void deleteEntry(Entry<K, V> entry){
        if (capacity <= 0 || table == null || entry == null) return;
        int index = Math.abs(entry.getKey().hashCode()) % capacity;
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;
        while (current != null) {
            if (current.getKey().equals(entry.getKey()) && current.getValue().equals(entry.getValue())) {
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

    public void updateValue(K key, V value) {
        if (capacity <= 0 || table == null || key == null) return;
        int index = Math.abs(key.hashCode()) % capacity;
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }
    }

    public boolean checkValueWithKey(K key, V value) {
        if (capacity <= 0 || table == null || key == null) return false;
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

    public V get(K key) {
        if (capacity <= 0 || table == null || key == null) return null;
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

    private void resize(){
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

    public boolean equals(HashTable<K, V> table) {
        for (HashTable.Entry<K, V> e : this){
            if (!table.checkValueWithKey(e.getKey(), e.getValue())) {
                return false;
            }
        }
        for (HashTable.Entry<K, V> e : table){
            if (!this.checkValueWithKey(e.getKey(), e.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private int currentIndex = 0;
            private Entry<K, V> currentEntry = null;
            private final int expectedSize = size;

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
