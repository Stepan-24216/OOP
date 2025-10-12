package org.example;

import java.util.Iterator;

public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {

    private Entry<K, V>[] table;
    private int size;
    private int capacity;

    public static class Entry<K, V> {
        private final K key;
        private final V value;
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
    }

    public void put(Entry<K, V> entry){
        int index = Math.abs(entry.getKey().hashCode()) % capacity;
        if (table[index] == null) {
            table[index] = entry;
        } else {
            Entry<K, V> current = table[index];
            while (current.getNext() != null) {
                if (current.getKey().equals(entry.getKey())) {
                    current.setNext(entry.getNext());
                    return;
                }
                current = current.getNext();
            }
            if (current.getKey().equals(entry.getKey())) {
                current.setNext(entry.getNext());
            } else {
                current.setNext(entry);
            }
        }
        size++;
        resize();
    }

    private void resize(){
        if (size >= (capacity-1)){
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

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }
}
