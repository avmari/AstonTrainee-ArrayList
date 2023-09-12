package collections;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListTest {

    private final ArrayList<Integer> arrayList = new ArrayList<>();

    private final int[] integers = {5, 4, 1, 2, 7};
    private final int size = 5;

    @BeforeEach
    void init() {
        for (int i : integers)
            arrayList.add(i);
    }

    @Test
    void add() {
        int valueToAdd = 100;

        arrayList.add(valueToAdd);

        int[] newIntegers = {5, 4, 1, 2, 7, valueToAdd};

        for (int i = 0; i < size + 1; ++i) {
            assertEquals(arrayList.get(i), newIntegers[i]);
        }
        assertEquals(arrayList.size(), size + 1);
    }

    @Test
    void addIfCapacityEqualsSize() {
        int valueToAdd = 100;
        int[] newElements = {1, 2, 3, 4, 1, 3, 1, 2, 2, 1, 1};
        for (int i : newElements)
            arrayList.add(i);

        arrayList.add(valueToAdd);

        assertEquals(arrayList.get(arrayList.size() - 1), valueToAdd);
        assertEquals(arrayList.size(), size + newElements.length + 1);
    }

    @Test
    void addIfArrayListIsEmpty() {
        arrayList.clearAll();
        int valueToAdd = 100;

        arrayList.add(valueToAdd);

        assertEquals(arrayList.get(arrayList.size() - 1), valueToAdd);
        assertEquals(arrayList.size(), 1);
    }

    @ParameterizedTest
    @MethodSource("getIndexAndValueToAdd")
    void addWithIndex(int index, int value, int[] result) {
        arrayList.add(value, index);

        for (int i = 0; i < result.length; ++i) {
            assertEquals(result[i], arrayList.get(i));
        }
        assertEquals(arrayList.size(), result.length);
    }

    @ParameterizedTest
    @MethodSource("getWrongIndexAndValue")
    void addWithWrongIndex(int index, Integer value) {
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.add(value, index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 4})
    void get(int index) {
        assertEquals(arrayList.get(index), integers[index]);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, size})
    void getWithIndexOutOfBounds(int index) {
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(index));
    }

    @ParameterizedTest
    @MethodSource("getResultAndIndexToRemove")
    void remove(int index, int[] result) {
        arrayList.remove(index);

        for (int i = 0; i < result.length; ++i) {
            assertEquals(result[i], arrayList.get(i));
        }
        assertEquals(arrayList.size(), result.length);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, size})
    void removeWithIndexOutOfBounds(int index) {
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.remove(index));
    }

    @Test
    void clearAll() {
        arrayList.clearAll();

        assertEquals(arrayList.size(), 0);
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(0));
    }

    @Test
    void sort() {
        arrayList.sort(Comparator.naturalOrder());

        int[] sortedIntegers = Arrays.copyOf(integers, integers.length);
        Arrays.sort(sortedIntegers);

        for (int i = 0; i < sortedIntegers.length; ++i) {
            assertEquals(arrayList.get(i), sortedIntegers[i]);
        }
    }

    @Test
    void replace() {
        int newValue = 100;
        int index = 2;

        arrayList.replace(index, newValue);

        assertEquals(arrayList.get(index), newValue);
    }

    @ParameterizedTest
    @MethodSource("getWrongIndexAndValue")
    void replaceWithIndexOutOfBounds(int index, Integer value) {
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.replace(index, value));
    }

    private static Stream<Arguments> getWrongIndexAndValue() {
        return Stream.of(
                Arguments.of(-1, 1),
                Arguments.of(100, 1)
        );
    }

    private static Stream<Arguments> getResultAndIndexToRemove() {
        return Stream.of(
                Arguments.of(0, new int[]{4, 1, 2, 7}),
                Arguments.of(1, new int[]{5, 1, 2, 7}),
                Arguments.of(4, new int[]{5, 4, 1, 2})
        );
    }

    private static Stream<Arguments> getIndexAndValueToAdd() {
        return Stream.of(
                Arguments.of(0, 9, new int[]{9, 5, 4, 1, 2, 7}),
                Arguments.of(1, 2, new int[]{5, 2, 4, 1, 2, 7}),
                Arguments.of(4, 8, new int[]{5, 4, 1, 2, 8, 7}),
                Arguments.of(5, 10, new int[]{5, 4, 1, 2, 7, 10})
        );
    }

}
