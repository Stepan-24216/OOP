package org.example;

public class main {
    public static void main(String[] args) {
        HashTable<String, Integer> table = new HashTable();
        HashTable.Entry<String, Integer> entry1 = new HashTable.Entry<>("One", 1);
        HashTable.Entry<String, Integer> entry2 = new HashTable.Entry<>("Two", 2);
        HashTable.Entry<String, Integer> entry3 = new HashTable.Entry<>("Three", 3);
        HashTable.Entry<String, Integer> entry4 = new HashTable.Entry<>("Four", 4);
        HashTable<String, Integer> table1 = new HashTable();
        table.put("One", 1);
        table.put("Two", 2);
        table.put("Three", 3);
        table1.put("One", 1);
        table1.put("Two", 2);
        table1.put("Three", 3);
        System.out.println(table.toString());
        System.out.println(table.equals(table1));
    }
}