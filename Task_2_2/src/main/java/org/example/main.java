package org.example;

public class main {
    public static void main(String[] args) {
        HashTable<String, Integer> table = new HashTable(10);
        HashTable.Entry<String, Integer> entry1 = new HashTable.Entry<>("One", 1);
        HashTable.Entry<String, Integer> entry2 = new HashTable.Entry<>("Two", 2);
        HashTable.Entry<String, Integer> entry3 = new HashTable.Entry<>("Three", 3);
        HashTable.Entry<String, Integer> entry4 = new HashTable.Entry<>("Four", 4);
        HashTable<String, Integer> table1 = new HashTable(10);
        table.put(entry1);
        table.put(entry2);
        table.put(entry3);
        table1.put(entry4);
        table1.put(entry1);
        table1.put(entry2);
        table1.put(entry3);
        for (HashTable.Entry<String, Integer> e : table) {
            e.print();
        }
        System.out.println(table.equals(table1));
    }
}