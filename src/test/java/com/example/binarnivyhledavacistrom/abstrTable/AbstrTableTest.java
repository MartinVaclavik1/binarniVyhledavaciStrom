package com.example.binarnivyhledavacistrom.abstrTable;

import com.example.binarnivyhledavacistrom.enumy.eTypProhl;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AbstrTableTest {

    private static class TestClass {

        int a;

        public TestClass(int a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "T" + a;
        }

    }

    private final TestClass T1 = new TestClass(1);
    private final TestClass T2 = new TestClass(2);
    private final TestClass T3 = new TestClass(3);
    private final TestClass T4 = new TestClass(4);
    private final TestClass T5 = new TestClass(5);
    private final TestClass T6 = new TestClass(6);
    private final TestClass T7 = new TestClass(7);
    private final TestClass T8 = new TestClass(8);
    private final TestClass T9 = new TestClass(9);

    @Test
    void testVloz01() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T9", T9);
            strom.vloz("T1", T1);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);
        } catch (AbstrTableException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testNajdi01() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {

            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T9", T9);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);
            assertEquals(strom.najdi("T9"), T9);
        } catch (AbstrTableException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testNajdi02() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);
            assertEquals(strom.najdi("T9"), T9);
            fail();
        } catch (AbstrTableException e) {
            System.err.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    void testIterator01() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);

            Iterator<TestClass> iterator = strom.vytvorIterator(eTypProhl.DO_SIRKY);
            while (iterator.hasNext()) {
                System.out.println(iterator.next().toString());
            }

        } catch (AbstrTableException | NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testIterator02() {
        IAbstrTable<String, Integer> strom = new AbstrTable<>();
        try {
            strom.vloz("f", 40);
            strom.vloz("a", 4);
            strom.vloz("e", 34);
            strom.vloz("c", 14);
            strom.vloz("b", 13);
            strom.vloz("d", 15);
            strom.vloz("g", 45);
            strom.vloz("k", 55);
            strom.vloz("i", 48);
            strom.vloz("h", 47);
            strom.vloz("j", 49);


            Iterator<Integer> iterator = strom.vytvorIterator(eTypProhl.DO_SIRKY);

            int[] expected = {40, 4, 45, 34, 55, 14, 48, 13, 15, 47, 49};
            int[] actual = new int[expected.length];
            int i = 0;
            while (iterator.hasNext()) {
                actual[i++] = iterator.next();
            }

            assertArrayEquals(expected, actual);
        } catch (AbstrTableException | NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testIterator03() {
        IAbstrTable<String, Integer> strom = new AbstrTable<>();
        try {
            strom.vloz("f", 40);
            strom.vloz("a", 4);
            strom.vloz("e", 34);
            strom.vloz("c", 14);
            strom.vloz("b", 13);
            strom.vloz("d", 15);
            strom.vloz("g", 45);
            strom.vloz("k", 55);
            strom.vloz("i", 48);
            strom.vloz("h", 47);
            strom.vloz("j", 49);


            Iterator<Integer> iterator = strom.vytvorIterator(eTypProhl.DO_HLOUBKY);

            int[] expected = {40, 4, 34, 14, 13, 15, 45, 55, 48, 47, 49};
            int[] actual = new int[expected.length];
            int i = 0;
            while (iterator.hasNext()) {
                actual[i++] = iterator.next();
            }

            assertArrayEquals(expected, actual);
        } catch (AbstrTableException | NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testIterator04() {
        IAbstrTable<String, Integer> strom = new AbstrTable<>();
        try {
            strom.vloz("f", 40);
            strom.vloz("a", 4);
            strom.vloz("e", 34);
            strom.vloz("c", 14);
            strom.vloz("b", 13);
            strom.vloz("d", 15);
            strom.vloz("g", 45);
            strom.vloz("k", 55);
            strom.vloz("i", 48);
            strom.vloz("h", 47);
            strom.vloz("j", 49);


            Iterator<Integer> iterator = strom.vytvorIterator(eTypProhl.IN_ORDER);

            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (AbstrTableException | NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testOdeber01() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T9", T9);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);
            assertEquals(strom.odeber("T9"), T9);
        } catch (AbstrTableException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testOdeber02() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);
            assertEquals(strom.odeber("T5"), T5);
        } catch (AbstrTableException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testOdeber03() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T9", T9);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);
            assertEquals(strom.odeber("T3"), T3);
        } catch (AbstrTableException e) {
            System.err.println(e.getMessage());
        }
    }
}