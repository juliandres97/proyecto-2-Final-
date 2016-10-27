package estructuras;

public interface IListaSimplementeEncadenada<T> extends Iterable<T> {
	public INodoSimple<T> getFirst();
	public INodoSimple<T> getLast();
	public void addFirst( T t );
	public void addLast( T t );
	public T removeFirst();
	public boolean isEmpty();
	public int size();
	public T search(T pElement);
	public T get(int index);
}