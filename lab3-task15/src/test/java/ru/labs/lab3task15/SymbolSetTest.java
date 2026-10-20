package ru.labs.lab3task15;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SymbolSetTest {
    @Test
    void supportsAllOperations() {
        SymbolSet a = new SymbolSet("abca"), b = new SymbolSet("bcd");
        assertTrue(a.contains('a'));
        assertFalse(a.contains('d'));
        assertEquals(3, a.size());
        assertEquals('b', a.get(1));
        assertEquals(new SymbolSet("abcd"), a.add(b));
        assertEquals(new SymbolSet("bc"), a.multiply(b));
        assertEquals(new SymbolSet("a"), a.subtract(b));
        assertEquals(new SymbolSet("ad"), SetOperations.symmetricDifference(a, b));
        assertEquals(new SymbolSet(""), SetOperations.symmetricDifference(a, a));
        assertEquals(new SymbolSet("abc"), a);
    }

    @Test
    void copiesAndIndexesUnicode() {
        SymbolSet a = new SymbolSet("я😀я"), b = new SymbolSet("");
        b.assign(a);
        assertEquals(2, a.size());
        assertEquals(0x1F600, a.get(1));
        a.assign(new SymbolSet("x"));
        assertEquals(new SymbolSet("я😀"), b);
        b.assign(b);
        assertEquals(2, b.size());
        assertThrows(IndexOutOfBoundsException.class, () -> b.get(2));
        assertEquals(new SymbolSet("ab").hashCode(), new SymbolSet("ba").hashCode());
    }
}
