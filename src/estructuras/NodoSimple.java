package estructuras;

public class NodoSimple<T> implements INodoSimple<T> {
	private T t;
	private NodoSimple<T> next;
	
	public NodoSimple( T t ) {
		this.t = t;
		next = null;
	}
	
	public T getElement() {
		return t;
	}

	public void setElement(T t) {
		this.t = t;
	}

	public NodoSimple<T> getNext() {
		return next;
	}

	public void setNext(INodo<T> next) {
		this.next = (NodoSimple<T>) next;
	}
}