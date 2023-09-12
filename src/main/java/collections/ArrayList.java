package collections;


import java.util.Arrays;
import java.util.Comparator;

/**
 * Custom ArrayList with basic methods to add, remove, get and sort elements of ArrayList
 * Permits all elements, including null.
 *
 * @param <T> determines the type of elements stored in ArrayList
 */
public class ArrayList<T> {
    private T[] values;
    private int size;
    private static final int INITIAL_CAPACITY = 16;

    public ArrayList() {
        values = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    /**
     * appends new element to the end of ArrayList
     *
     * @param value - value to be added to ArrayList
     */
    public boolean add(T value) {
        add(value, size);
        return true;
    }

    /**
     * adds specified value at specified index to ArrayList
     *
     * @param value - value to be added to ArrayList
     * @param index - index at which specified value should be added
     * @throws IndexOutOfBoundsException if (index < 0 || index > size)
     */
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (size == values.length)
            grow();
        System.arraycopy(values, index, values, index + 1, size - index);
        values[index] = value;
        size++;
    }

    /**
     * returns element of ArrayList at specified index
     *
     * @param index - index of element to return
     * @return element of ArrayList at specified index
     * @throws IndexOutOfBoundsException if (index < 0 || index >= size)
     */
    public T get(int index) {
        checkIndex(index);
        return values[index];
    }

    /**
     * removes element of ArrayList at specified index
     *
     * @param index - index of element to remove
     * @throws IndexOutOfBoundsException if (index < 0 || index >= size)
     */
    public T remove(int index) {
        checkIndex(index);
        T removedValue = values[index];
        if (index != size - 1)
            System.arraycopy(values, index + 1, values, index, size - index - 1);
        values[size--] = null;
        return removedValue;
    }

    /**
     * removes all elements from ArrayList
     */
    public void clearAll() {
        size = 0;
        for (T o : values)
            o = null;
    }

    /**
     * sorts ArrayList with QuickSort algorithm, uses comparator for comparing elements of ArrayList
     *
     * @param comparator used for comparing elements of ArrayList
     */
    public void sort(Comparator<T> comparator) {
        quickSort(values, 0, size - 1, comparator);
    }

    /**
     * replaces element of ArrayList at specified index with specified value
     *
     * @param index - index of element that should be replaced
     * @param value - value with which element is replaced
     * @throws IndexOutOfBoundsException if (index < 0 || index >= size)
     */
    public void replace(int index, T value) {
        checkIndex(index);
        values[index] = value;
    }

    private void grow() {
        values = Arrays.copyOf(values, values.length << 1);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
    }

    private void quickSort(T[] values, int low, int high, Comparator<T> comparator) {
        if (low >= high)
            return;

        T pivot = get(high);

        int i = low - 1;
        for (int j = low; j < high; ++j) {

            if (comparator.compare(values[j], pivot) < 0) {
                ++i;

                T temp = values[j];
                values[j] = values[i];
                values[i] = temp;
            }
        }

        values[high] = values[i + 1];
        values[i + 1] = pivot;

        quickSort(values, low, i, comparator);
        quickSort(values, i + 2, high, comparator);
    }

}
