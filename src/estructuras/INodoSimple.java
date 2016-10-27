package estructuras;

public interface INodoSimple<T> extends INodo<T> {
	public INodo<T> getNext();
	public void setNext( INodo<T> next );
}
