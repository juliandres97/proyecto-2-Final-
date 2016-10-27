package estructuras;

public class LinearProbingHashTable<K, V> implements IHashTable<K, V>
{
	private static final int INIT_CAPACITY = 7;
    private int nPairs;
    private int htSize;
    private K[] keys;
    private V[] values;
	
    /**
     * Initializes an empty symbol table.
     */
    public LinearProbingHashTable() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param htSize the initial capacity
     */
    @SuppressWarnings("unchecked")
	public LinearProbingHashTable(int htSize)
    {
        this.htSize = nextPrime(htSize);
        nPairs = 0;
        keys = (K[])   new Object[this.htSize];
        values = (V[]) new Object[this.htSize];
    }
    
	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return nPairs;
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
        for (int i = hash(key); keys[i] != null; i = (i + 1) % htSize)
            if (keys[i].equals(key))
                return values[i];
        return null;
	}

	@Override
	public void put(K key, V value)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("first argument to put() is null");

        if (value == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (nPairs >= htSize/2) rehash(2*htSize);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % htSize) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
        }
        keys[i] = key;
        values[i] = value;
        nPairs++;
	}

	@Override
	public void delete(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to delete() is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i]))
        {
            i = (i + 1) % htSize;
        }

        // delete key and associated value
        keys[i] = null;
        values[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % htSize;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            K   keyToRehash = keys[i];
            V valToRehash = values[i];
            keys[i] = null;
            values[i] = null;
            nPairs--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % htSize;
        }

        nPairs--;

        // halves size of array if it's 12.5% full or less
        if (nPairs > 0 && nPairs <= htSize/8) rehash(htSize/2);

        assert check();
	}

	@Override
	public int hash(K key)
	{
		// TODO Auto-generated method stub
		return (key.hashCode() & 0x7fffffff) % htSize;
	}
	
	private void rehash(int newHTSize)
	{
		LinearProbingHashTable<K, V> temp = new LinearProbingHashTable<K, V>(newHTSize);
        for (int i = 0; i < htSize; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], values[i]);
            }
        }
        keys = temp.keys;
        values = temp.values;
        htSize = temp.htSize;
	}
	
	private static int nextPrime(int n)
	{
		while (true)
		{
			boolean isPrime = true;
			n = n+1;
			int sqrt = (int) Math.sqrt(n);
			
			for (int i = 2; i <= sqrt ; i++)
			{
				if (n%i == 0)
				{
					isPrime = false;
					break;
				}
			}
			
			if (isPrime)
			{
				return n;
			}
		}
	}
	
	private boolean check()
	{
        // check that hash table is at most 50% full
        if (htSize < 2*nPairs)
        {
            System.err.println("Hash table size m = " + htSize + "; array size n = " + nPairs);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < htSize; i++)
        {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != values[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; values[i] = " + values[i]);
                return false;
            }
        }
        return true;
    }
}