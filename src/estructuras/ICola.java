package estructuras;

public abstract interface ICola<T>{
	public void enqueue(T t);
	public T dequeue();
	public boolean isEmpty();
	public int size();
}
