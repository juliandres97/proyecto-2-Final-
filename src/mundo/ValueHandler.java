package mundo;

import estructuras.*;

public class ValueHandler<K, V>
{
	public SeparateChainingHashTable<K, V> tablaSC;
	public LinearProbingHashTable<K, V> tablaLP;
	
	public ValueHandler( SeparateChainingHashTable<K, V> tablaSC, LinearProbingHashTable<K, V> tablaLP )
	{
		this.tablaSC = tablaSC;
		this.tablaLP = tablaLP;
	}
}
