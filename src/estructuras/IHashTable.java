package estructuras;

public interface IHashTable<K, V>
{
	/**
	 * 
	 * @return
	 */
	public int size();
	
	/**
	 * 
	 */
	public boolean isEmpty();
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(K key);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key);
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public void delete(K key);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public int hash(K key);
}