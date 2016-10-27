package estructuras;

public class Cola<T> extends ListaSimplementeEncadenada<T> implements ICola<T> {
	
	public void enqueue(T t) {
		// TODO Auto-generated method stub
		addLast(t);
	}

	public T dequeue() {
		// TODO Auto-generated method stub
		return removeFirst();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String resp = "Ida: [" + size() + "]: ";
		for( NodoSimple<T> p = getFirst(); p != null; p = (NodoSimple<T>) p.getNext() )
		{
			resp += p.getElement().toString( ) + " <-> ";
		}
		return resp;
	}
}
