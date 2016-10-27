package estructuras;

public class SeparateChainingList<K, V> implements ISeparateChainingList<K, V>
{
	private int n;
	private Node first;

	// a helper linked list data type
	private class Node
	{
		private K key;
		private V value;
		private Node next;

		public Node(K key, V value, Node next)
		{
			this.key  = key;
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * Initializes an empty symbol table.
	 */
	public SeparateChainingList()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return n;
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return size() == 0;
	}

	@Override
	public boolean contains(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to contains() is null");
		return get(key) != null;
	}

	@Override
	public V get(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to get() is null"); 
		for (Node x = first; x != null; x = x.next)
		{
			if (key.equals(x.key))
				return x.value;
		}
		return null;
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

        for (Node x = first; x != null; x = x.next)
        {
            if (key.equals(x.key))
            {
                x.value = value;
                return;
            }
        }
        
        first = new Node(key, value, first);
        n++;
	}

	@Override
	public void delete(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to delete() is null"); 
        first = delete(first, key);
	}
	
	// delete key in linked list beginning at Node x
    // warning: function call stack too large if table is large
    private Node delete(Node x, K key)
    {
        if (x == null) return null;
        if (x.key.equals(key))
        {
            n--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

	@Override
	public Iterable<K> keys()
	{
		// TODO Auto-generated method stub
		Cola<K> queue = new Cola<K>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue;
	}
}