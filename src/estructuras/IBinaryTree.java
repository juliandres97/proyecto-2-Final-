package estructuras;

public interface IBinaryTree<K, V>
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
	 */
	public void deleteMin();
	
	/**
	 * 
	 */
	public void deleteMax();
	
	/**
	 * 
	 * @param key
	 */
	public void delete(K key);
	
	/**
	 * 
	 * @return
	 */
	public K min();
	
	/**
	 * 
	 * @return
	 */
	public K max();
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public K floor(K key);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public K ceiling(K key);
	
	/**
	 * 
	 * @param k
	 * @return
	 */
	public K select(int k);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public int rank(K key);
	
	/**
	 * 
	 * @return
	 */
	public Iterable<K> keys();
	
	/**
	 * 
	 * @param lo
	 * @param hi
	 * @return
	 */
	public Iterable<K> keys(K lo, K hi);
	
	/**
	 * 
	 * @param lo
	 * @param hi
	 * @return
	 */
	public int size(K lo, K hi);

	/**
	 * 
	 * @return
	 */
	public int height();
}
