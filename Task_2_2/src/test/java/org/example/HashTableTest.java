package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование Хэш-Таблицы.
 */
public class HashTableTest {
    @Test
    public void testCreateEmptyHashTable() {
        HashTable<String, Integer> table = new HashTable<>();
        assertEquals(0, table.getSize());
        assertEquals(16, table.getCapacity());
    }

    @Test
    public void testPutAndGet() {
        HashTable<String, String> table = new HashTable<>();

        table.put("key1", "value1");
        table.put("key2", "value2");
        table.put("key3", "value3");

        assertEquals("value1", table.get("key1"));
        assertEquals("value2", table.get("key2"));
        assertEquals("value3", table.get("key3"));
        assertEquals(3, table.getSize());
    }

    @Test
    public void testUpdateValue() {
        HashTable<Integer, String> table = new HashTable<>();

        table.put(1, "oldValue");
        assertEquals("oldValue", table.get(1));

        table.update(1, "newValue");
        assertEquals("newValue", table.get(1));
    }

    @Test
    public void testDeleteEntry() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);

        assertEquals(3, table.getSize());

        table.deleteEntry("two", 2);

        assertEquals(2, table.getSize());
        assertNull(table.get("two"));
        assertEquals(1, table.get("one"));
        assertEquals(3, table.get("three"));
    }

    @Test
    public void testCheckValueWithKey() {
        HashTable<String, Double> table = new HashTable<>();

        table.put("price", 19.99);
        table.put("discount", 5.0);

        assertTrue(table.checkEntry("price", 19.99));
        assertTrue(table.checkEntry("discount", 5.0));
        assertFalse(table.checkEntry("price", 20.0));
        assertFalse(table.checkEntry("gg", 19.99));
    }

    @Test
    public void testResize() {
        HashTable<Integer, String> table = new HashTable<>();

        table.put(1, "one");
        table.put(2, "two");
        table.put(3, "three");
        table.put(4, "four");
        table.put(5, "five");
        table.put(6, "six");
        table.put(7, "seven");
        table.put(8, "eight");
        table.put(9, "nine");
        table.put(10, "ten");
        table.put(11, "eleven");
        table.put(12, "twelve");
        table.put(13, "thirteen");
        table.put(14, "fourteen");
        table.put(15, "fifteen");
        table.put(16, "sixteen");
        table.put(17, "seventeen");

        assertEquals(17, table.getSize());
        assertTrue(table.getCapacity() > 16);
        assertEquals("one", table.get(1));
        assertEquals("two", table.get(2));
        assertEquals("three", table.get(3));
    }

    @Test
    public void testIterator() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("a", 1);
        table.put("b", 2);
        table.put("c", 3);

        int count = 0;
        for (Entry<String, Integer> entry : table) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
            count++;
        }

        assertEquals(3, count);
    }

    @Test
    public void testEquals() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();
        HashTable<String, Integer> table3 = new HashTable<>();

        table1.put("a", 1);
        table1.put("b", 2);

        table2.put("a", 1);
        table2.put("b", 2);

        table3.put("a", 1);
        table3.put("b", 3);

        assertTrue(table1.equals(table2));
        assertFalse(table1.equals(table3));
        assertTrue(table1.equals(table1));
    }

    @Test
    public void testToString() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("x", 10);
        table.put("y", 20);

        String result = table.toString();
        assertTrue(result.contains("x: 10"));
        assertTrue(result.contains("y: 20"));
        assertTrue(result.startsWith("HashTable["));
        assertTrue(result.endsWith("]"));
    }

}