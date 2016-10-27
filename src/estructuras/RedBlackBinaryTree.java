/**
 * Código tomado de Algorithms 4th Edition
 * Copyright © 2002–2016, Robert Sedgewick and Kevin Wayne.
 * Last updated: Tue Aug 30 10:09:18 EDT 2016.
 */

package estructuras;

import java.util.NoSuchElementException;

public class RedBlackBinaryTree<K extends Comparable<K>, V> implements IBinaryTree<K, V>
{
	private static final boolean RED   = true;
	private static final boolean BLACK = false;
	private Node root;

	// BST helper node data type
	private class Node
	{
		private K key;
		private V value;
		private Node left;
		private Node right;
		private boolean color;
		private int size;

		public Node(K key, V value, boolean color, int size)
		{
			this.key = key;
			this.value = value;
			this.color = color;
			this.size = size;
		}
	}

	public RedBlackBinaryTree()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return size(root);
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return root == null;
	}

	@Override
	public boolean contains(K key)
	{
		// TODO Auto-generated method stub
		return get(key) != null;
	}

	@Override
	public V get(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to get() is null");
		return get(root, key);
	}

	@Override
	public void put(K key, V value)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("first argument to put() is null");
		if (value == null)
		{
			delete(key);
			return;
		}

		root = put(root, key, value);
		root.color = BLACK;
	}

	@Override
	public void deleteMin()
	{
		// TODO Auto-generated method stub
		if (isEmpty()) throw new NoSuchElementException("RedBlackBinaryTree underflow");

		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMin(root);
		if (!isEmpty()) root.color = BLACK;
		// assert check();
	}

	@Override
	public void deleteMax()
	{
		// TODO Auto-generated method stub
		if (isEmpty()) throw new NoSuchElementException("RedBlackBinaryTree underflow");

		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMax(root);
		if (!isEmpty()) root.color = BLACK;
		// assert check();
	}

	@Override
	public void delete(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to delete() is null");
		if (!contains(key)) return;

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = delete(root, key);
		if (!isEmpty()) root.color = BLACK;
		// assert check();
	}

	@Override
	public K min()
	{
		// TODO Auto-generated method stub
		if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
		return min(root).key;
	}

	@Override
	public K max()
	{
		// TODO Auto-generated method stub
		if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
		return max(root).key;
	}

	@Override
	public K floor(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to floor() is null");
		if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
		
		Node x = floor(root, key);
		if (x == null)
			return null;
		else
			return x.key;
	}

	@Override
	public K ceiling(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to floor() is null");
		if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
		
		Node x = ceiling(root, key);
		if (x == null)
			return null;
		else
			return x.key;
	}

	@Override
	public K select(int k)
	{
		// TODO Auto-generated method stub
		if (k < 0 || k >= size()) throw new IllegalArgumentException();
        
		Node x = select(root, k);
        return x.key;
	}

	@Override
	public int rank(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to rank() is null");
        return rank(key, root);
	}

	@Override
	public Iterable<K> keys()
	{
		// TODO Auto-generated method stub
		if (isEmpty()) return new Cola<K>();
        return keys(min(), max());
	}

	@Override
	public Iterable<K> keys(K lo, K hi)
	{
		// TODO Auto-generated method stub
		if (lo == null) throw new NullPointerException("first argument to keys() is null");
        if (hi == null) throw new NullPointerException("second argument to keys() is null");

        Cola<K> queue = new Cola<K>();
        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
        keys(root, queue, lo, hi);
        return queue;
	}

	@Override
	public int size(K lo, K hi)
	{
		// TODO Auto-generated method stub
		if (lo == null) throw new NullPointerException("first argument to size() is null");
        if (hi == null) throw new NullPointerException("second argument to size() is null");

        if (lo.compareTo(hi) > 0)
        	return 0;
        if (contains(hi))
        	return rank(hi) - rank(lo) + 1;
        else
        	return rank(hi) - rank(lo);
	}

	@Override
	public int height()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private boolean isRed(Node x)
	{
		if (x == null) return false;
		return x.color == RED;
	}

	// number of node in subtree rooted at x; 0 if x is null
	private int size(Node x)
	{
		if (x == null) return 0;
		return x.size;
	}

	private V get(Node x, K key)
	{
		while (x != null)
		{
			int cmp = key.compareTo(x.key);

			if (cmp < 0)
				x = x.left;
			else if (cmp > 0)
				x = x.right;
			else 
				return x.value;
		}
		return null;
	}

	private Node put(Node h, K key, V val)
	{ 
		if (h == null) return new Node(key, val, RED, 1);

		int cmp = key.compareTo(h.key);

		if (cmp < 0)
			h.left  = put(h.left,  key, val); 
		else if (cmp > 0)
			h.right = put(h.right, key, val); 
		else
			h.value = val;

		// fix-up any right-leaning links
		if (isRed(h.right) && !isRed(h.left))
			h = rotateLeft(h);
		if (isRed(h.left)  &&  isRed(h.left.left))
			h = rotateRight(h);
		if (isRed(h.left)  &&  isRed(h.right))
			flipColors(h);

		h.size = size(h.left) + size(h.right) + 1;

		return h;
	}

	private Node deleteMin(Node h)
	{ 
		if (h.left == null)
			return null;

		if (!isRed(h.left) && !isRed(h.left.left))
			h = moveRedLeft(h);

		h.left = deleteMin(h.left);
		return balance(h);
	}

	private Node deleteMax(Node h)
	{
		if (isRed(h.left))
			h = rotateRight(h);

		if (h.right == null)
			return null;

		if (!isRed(h.right) && !isRed(h.right.left))
			h = moveRedRight(h);

		h.right = deleteMax(h.right);

		return balance(h);
	}

	private Node delete(Node h, K key) { 
		// assert get(h, key) != null;

		if (key.compareTo(h.key) < 0)
		{
			if (!isRed(h.left) && !isRed(h.left.left))
				h = moveRedLeft(h);
			h.left = delete(h.left, key);
		}
		else
		{
			if (isRed(h.left))
				h = rotateRight(h);

			if (key.compareTo(h.key) == 0 && (h.right == null))
				return null;

			if (!isRed(h.right) && !isRed(h.right.left))
				h = moveRedRight(h);

			if (key.compareTo(h.key) == 0)
			{
				Node x = min(h.right);
				h.key = x.key;
				h.value = x.value;
				// h.val = get(h.right, min(h.right).key);
				// h.key = min(h.right).key;
				h.right = deleteMin(h.right);
			}

			else h.right = delete(h.right, key);
		}

		return balance(h);
	}

	private Node min(Node x)
	{ 
		// assert x != null;
		if (x.left == null)
			return x; 
		else
			return min(x.left); 
	}

	private Node max(Node x)
	{
		// assert x != null;
		if (x.right == null)
			return x;
		else
			return max(x.right);
	}

	private Node floor(Node x, K key)
	{
		if (x == null)
			return null;

		int cmp = key.compareTo(x.key);

		if (cmp == 0)
			return x;
		if (cmp < 0)
			return floor(x.left, key);

		Node t = floor(x.right, key);

		if (t != null)
			return t; 
		else
			return x;
	}

	private Node ceiling(Node x, K key)
	{  
		if (x == null)
			return null;

		int cmp = key.compareTo(x.key);

		if (cmp == 0)
			return x;
		if (cmp > 0) 
			return ceiling(x.right, key);

		Node t = ceiling(x.left, key);

		if (t != null)
			return t; 
		else
			return x;
	}

	private Node select(Node x, int k)
	{
		// assert x != null;
		// assert k >= 0 && k < size(x);
		int t = size(x.left); 

		if (t > k)
			return select(x.left,  k); 
		else if (t < k)
			return select(x.right, k-t-1); 
		else
			return x; 
	}

	private int rank(K key, Node x)
	{
		if (x == null) return 0; 

		int cmp = key.compareTo(x.key); 

		if (cmp < 0)
			return rank(key, x.left); 
		else if (cmp > 0)
			return 1 + size(x.left) + rank(key, x.right); 
		else
			return size(x.left); 
	}
	
	private void keys(Node x, Cola<K> queue, K lo, K hi)
	{ 
        if (x == null)
        	return;
        
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        
        if (cmplo < 0)
        	keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0)
        	queue.enqueue(x.key); 
        if (cmphi > 0)
        	keys(x.right, queue, lo, hi); 
    } 

	/***************************************************************************
	 *  Red-black tree helper functions.
	 ***************************************************************************/

	// make a left-leaning link lean to the right
	private Node rotateRight(Node h)
	{
		// assert (h != null) && isRed(h.left);
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = x.right.color;
		x.right.color = RED;
		x.size = h.size;
		h.size = size(h.left) + size(h.right) + 1;
		return x;
	}

	// make a right-leaning link lean to the left
	private Node rotateLeft(Node h)
	{
		// assert (h != null) && isRed(h.right);
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = x.left.color;
		x.left.color = RED;
		x.size = h.size;
		h.size = size(h.left) + size(h.right) + 1;
		return x;
	}

	// flip the colors of a node and its two children
	private void flipColors(Node h)
	{
		// h must have opposite color of its two children
		// assert (h != null) && (h.left != null) && (h.right != null);
		// assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
		//    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}

	// Assuming that h is red and both h.left and h.left.left
	// are black, make h.left or one of its children red.
	private Node moveRedLeft(Node h)
	{
		// assert (h != null);
		// assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

		flipColors(h);
		if (isRed(h.right.left))
		{ 
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private Node moveRedRight(Node h)
	{
		// assert (h != null);
		// assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
		flipColors(h);
		if (isRed(h.left.left)) { 
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	// restore red-black tree invariant
	private Node balance(Node h)
	{
		// assert (h != null);

		if (isRed(h.right))
			h = rotateLeft(h);

		if (isRed(h.left) && isRed(h.left.left))
			h = rotateRight(h);

		if (isRed(h.left) && isRed(h.right))
			flipColors(h);

		h.size = size(h.left) + size(h.right) + 1;
		return h;
	}
}