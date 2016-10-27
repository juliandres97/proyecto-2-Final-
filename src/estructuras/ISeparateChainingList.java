package estructuras;

public interface ISeparateChainingList<K, V>
{ 
	/**
	 * Retorna el número de elementos en el heap
	 * @return size Número de elementos en el heap
	 */
	public int size();
	
	/**
	 * Retorna true si el heap no tiene elementos; false de lo contrario.
	 * @return
	 */
	public boolean isEmpty();
	
	/**
	 * Retona true si este simbolo contiene la llave especificada.
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
	 * @param k
	 * @return
	 */
	public void delete(K key);
	
	/**
	 * 
	 * @return
	 */
	public Iterable<K> keys();
}
