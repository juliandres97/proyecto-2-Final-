package estructuras;

import java.util.NoSuchElementException;

public class SeparateChainingHashTable<K, V> implements IHashTable<K, V>
{
	private static final int INIT_CAPACITY = 0;
	private int nPairs;
	private int htSize;
	private SeparateChainingList<K, V>[] SCL;

	/**
	 * Initializes an empty symbol table.
	 */
	public SeparateChainingHashTable()
	{
		this(INIT_CAPACITY);
	} 

	/**
	 * Initializes an empty symbol table with {@code m} chains.
	 * @param m the initial number of chains
	 */
	@SuppressWarnings("unchecked")
	public SeparateChainingHashTable(int htSize)
	{
		this.htSize = nextPrime(htSize);
		this.nPairs = 0;
		SCL = (SeparateChainingList<K, V>[]) new SeparateChainingList[this.htSize];
		for (int i = 0; i < this.htSize; i++)
			SCL[i] = new SeparateChainingList<K, V>();
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
		if (key == null) throw new NoSuchElementException("argument to contains() is null");
		return get(key) != null;
	}

	@Override
	public V get(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to get() is null");
        int i = hash(key);
        return SCL[i].get(key);
	}

	@Override
	public void put(K key, V value)
	{
		// TODO Auto-generated method stub
		if(key == null) throw new NoSuchElementException("first argument to put() is null");
		if (value == null)
		{
			delete(key);
			return;
		}
		
		if(nPairs >= htSize*9) rehash(2*htSize);
		
		int i = hash(key);
		
		if(!SCL[i].contains(key)) nPairs++;
		SCL[i].put(key, value);
	}

	@Override
	public void delete(K key)
	{
		// TODO Auto-generated method stub
		if (key == null) throw new NullPointerException("argument to delete() is null");

        int i = hash(key);
        if (SCL[i].contains(key)) nPairs--;
        SCL[i].delete(key);

        // halve table size if average length of list <= 2
        if (htSize > INIT_CAPACITY && nPairs <= 2*htSize) rehash(htSize/2);
	}

	@Override
	public int hash(K key)
	{
		// TODO Auto-generated method stub
		return (key.hashCode() & 0x7fffffff) % htSize;
	}

	private void rehash(int newHTSize)
	{
		SeparateChainingHashTable<K, V> temp = new SeparateChainingHashTable<K, V>(newHTSize);

		for (int i = 0; i < this.htSize; i++)
		{
			for (K key : SCL[i].keys())
			{
				temp.put(key, SCL[i].get(key));
			}
		}

		this.htSize = temp.htSize;
		this.nPairs = temp.nPairs;
		this.SCL = temp.SCL;
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
}