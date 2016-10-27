package estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaSimplementeEncadenada<T> implements Iterable<T>, IListaSimplementeEncadenada<T>, Comparable<ListaSimplementeEncadenada<T>>
{
	private NodoSimple<T> first;
	private NodoSimple<T> last;
	private int n;

	public ListaSimplementeEncadenada( ) {
		first = null;
		last = null;
	}


	public NodoSimple<T> getFirst() {
		// TODO Auto-generated method stub
		return first;
	}


	public NodoSimple<T> getLast() {
		// TODO Auto-generated method stub
		return last;
	}


	public void addFirst(T t) {
		// TODO Auto-generated method stub
		NodoSimple<T> newFirst = new NodoSimple<T>(t);
		if (first == null) {
			first = newFirst;
			last = newFirst;
		}
		newFirst.setNext(first);
		first = newFirst;
		n++;
	}


	public void addLast(T t) {
		// TODO Auto-generated method stub
		NodoSimple<T> newLast = new NodoSimple<T>(t);
		if (last == null) {
			last = newLast;
			first = newLast;
		}
		last.setNext(newLast);
		last = newLast;
		n++;
	}


	public T removeFirst() {
		NodoSimple<T> oldFirst = first;
		if (size() == 1) {
			first = null;
		}
		first = first.getNext();
		n--;
		return oldFirst.getElement();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size() == 0;
	}


	public int size() {
		// TODO Auto-generated method stub
		return n;
	}

	public T search(T t) {
		T found = null;
		Iterator<T> it = iterator();
		
		while(it.hasNext()) {
			T actual = it.next();
			if(actual.equals(t)) {
				found = actual;
			}
		}
		
		return found;
	}

	public T get( int index )
	{
		if( index >= n || index < 0 )
		{
			throw new IndexOutOfBoundsException( "" + index );
		}
		else
		{
			NodoSimple<T> aux = first;

			for( int cont = 0; cont < index; cont++ )
			{
				aux = aux.getNext();
			}

			return aux.getElement();
		}
	}

	public Iterator<T> iterator()
	{
		return new ListIterator<T>(first);
	}

	@SuppressWarnings("hiding")
	private class ListIterator<T> implements Iterator<T>
	{
		private NodoSimple<T> current;

		public ListIterator(NodoSimple<T> first)
		{
			current = first;
		}

		public boolean hasNext()  { return current != null;                     }
		public void remove()      { throw new UnsupportedOperationException();  }

		public T next()
		{
			if (!hasNext()) throw new NoSuchElementException();
			T t = current.getElement();
			current = current.getNext(); 
			return t;
		}
	}

	@Override
	public int compareTo(ListaSimplementeEncadenada<T> o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
}