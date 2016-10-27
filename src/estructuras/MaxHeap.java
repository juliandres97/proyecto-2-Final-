package estructuras;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxHeap<K> implements IHeap<K>, Iterable<K>
{
	private K[] elements;                    // store items at indices 1 to n
	private int n;                       // number of items on priority queue
	private Comparator<K> comparator;  // optional comparator

	/**
	 * Initializes an empty priority queue with the given initial capacity.
	 *
	 * @param  initCapacity the initial capacity of this priority queue
	 */
	@SuppressWarnings("unchecked")
	public MaxHeap(int initCapacity) {
		elements = (K[]) new Object[initCapacity + 1];
		n = 0;
	}

	/**
	 * Initializes an empty priority queue.
	 */
	public MaxHeap() {
		this(1);
	}

	/**
	 * Initializes an empty priority queue with the given initial capacity,
	 * using the given comparator.
	 *
	 * @param  initCapacity the initial capacity of this priority queue
	 * @param  comparator the order to use when comparing keys
	 */
	@SuppressWarnings("unchecked")
	public MaxHeap(int initCapacity, Comparator<K> comparator) {
		this.comparator = comparator;
		elements = (K[]) new Object[initCapacity + 1];
		n = 0;
	}

	/**
	 * Initializes an empty priority queue using the given comparator.
	 *
	 * @param  comparator the order to use when comparing keys
	 */
	public MaxHeap(Comparator<K> comparator) {
		this(1, comparator);
	}

	/**
	 * Initializes a priority queue from the array of keys.
	 * <p>
	 * Takes time proportional to the number of keys, using sink-based heap construction.
	 *
	 * @param  keys the array of keys
	 */
	@SuppressWarnings("unchecked")
	public MaxHeap(K[] keys) {
		n = keys.length;
		elements = (K[]) new Object[keys.length + 1];
		for (int i = 0; i < n; i++)
			elements[i+1] = keys[i];
		for (int k = n/2; k >= 1; k--)
			siftDown(k);
		assert isMaxHeap();
	}

	/**
	 * Returns true if this priority queue is empty.
	 *
	 * @return {@code true} if this priority queue is empty;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * Returns the number of keys on this priority queue.
	 *
	 * @return the number of keys on this priority queue
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns a smallest key on this priority queue.
	 *
	 * @return a smallest key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public K peek() {
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
		return elements[1];
	}


	/**
	 * Adds a new key to this priority queue.
	 *
	 * @param  x the key to add to this priority queue
	 */
	public void add(K x) {
		// double size of array if necessary
		if (n == elements.length - 1) resize(2 * elements.length);

		// add x, and percolate it up to maintain heap invariant
		elements[++n] = x;
		siftUp(n);
		assert isMaxHeap();
	}

	/**
	 * Removes and returns a smallest key on this priority queue.
	 *
	 * @return a smallest key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public K poll() {
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        K max = elements[1];
        exch(1, n--);
        siftDown(1);
        elements[n+1] = null;     // to avoid loiterig and help with garbage collection
        if ((n > 0) && (n == (elements.length - 1) / 4)) resize(elements.length / 2);
        assert isMaxHeap();
        return max;
	}


	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/

	// helper function to double the size of the heap array
	@SuppressWarnings("unchecked")
	private void resize(int capacity)
	{
		assert capacity > n;
		K[] temp = (K[]) new Object[capacity];
		for (int i = 1; i <= n; i++) {
			temp[i] = elements[i];
		}
		elements = temp;
	}
	
	public void siftUp(int k)
	{
		while (k > 1 && less(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}

	public void siftDown(int k)
	{
		while (2*k <= n) {
			int j = 2*k;
			if (j < n && less(j, j+1)) j++;
			if (!less(k, j)) break;
			exch(k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for compares and swaps.
	 ***************************************************************************/
	@SuppressWarnings("unchecked")
	private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<K>) elements[i]).compareTo(elements[j]) < 0;
        }
        else {
            return comparator.compare(elements[i], elements[j]) < 0;
        }
    }

	private void exch(int i, int j) {
		K swap = elements[i];
		elements[i] = elements[j];
		elements[j] = swap;
	}

	// is pq[1..N] a min heap?
	private boolean isMaxHeap() {
		return isMaxHeap(1);
	}

	// is subtree of pq[1..n] rooted at k a min heap?
	private boolean isMaxHeap(int k) {
        if (k > n) return true;
        int left = 2*k;
        int right = 2*k + 1;
        if (left  <= n && less(k, left))  return false;
        if (right <= n && less(k, right)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }


	/**
	 * Returns an iterator that iterates over the keys on this priority queue
	 * in ascending order.
	 * <p>
	 * The iterator doesn't implement {@code remove()} since it's optional.
	 *
	 * @return an iterator that iterates over the keys in ascending order
	 */
	public Iterator<K> iterator() { return new HeapIterator(); }

	private class HeapIterator implements Iterator<K> {
		// create a new pq
		private MaxHeap<K> copy;

		// add all items to copy of heap
		// takes linear time since already in heap order so no keys move
		public HeapIterator() {
			if (comparator == null) copy = new MaxHeap<K>(size());
			else                    copy = new MaxHeap<K>(size(), comparator);
			for (int i = 1; i <= n; i++)
				copy.add(elements[i]);
		}

		public boolean hasNext()  { return !copy.isEmpty();                     }
		public void remove()      { throw new UnsupportedOperationException();  }

		public K next() {
			if (!hasNext()) throw new NoSuchElementException();
			return copy.poll();
		}
	}
}