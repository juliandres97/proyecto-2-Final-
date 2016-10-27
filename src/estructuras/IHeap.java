package estructuras;

/**
 * Interfaz de un heap binario gen�rico.
 * @author Diego
 */
public interface IHeap<K>
{
	/**
	 * Agrega un elemento al heap
	 */
	public void add(K key);
	
	/**
	 * Retorna pero no remueve el elemento m�ximo/m�nimo del heap.
	 * @return T elemento 
	 */
	public K peek();
	
	/**
	 * Retorna el elemento m�ximo/m�nimo luego de removerlo del heap.
	 * @return T El elemento m�ximo/m�nimo del heap
	 */
	public K poll();
	
	/**
	 * Retorna el n�mero de elementos en el heap
	 * @return size N�mero de elementos en el heap
	 */
	public int size();
	
	/**
	 * Retorna true si el heap no tiene elementos; false de lo contrario.
	 * @return
	 */
	public boolean isEmpty();
	
	/**
	 * Mueve el �ltimo elemento arriba en el arbol, mientras que sea necesario.
	 * Es usado para restaurar la condici�n de heap luego de inserci�n.
	 */
	void siftUp(int i);
	
	/**
	 * Mueve la ra�z abajo en el arbol, mientras que sea necesario.
	 * Es usado para restaurar la condici�n de heap luego de la eliminaci�n o reemplazo.
	 */
	void siftDown(int i);
}