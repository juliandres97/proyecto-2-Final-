package mundo;

import estructuras.*;

public class LocalAuthority implements Comparable<LocalAuthority>
{
	private String nombreLocalAuthority;
	private String codigo;
	private ListaSimplementeEncadenada<AccidentesEnAnio> accidentesDeLocalAuthority;
	private double prioridad;

	public LocalAuthority(String nombreLocalAuthority, String codigo, ListaSimplementeEncadenada<AccidentesEnAnio> accidentesDeLocalAuthority, double prioridad)
	{
		this.nombreLocalAuthority = nombreLocalAuthority;
		this.codigo = codigo;
		this.accidentesDeLocalAuthority = accidentesDeLocalAuthority;
		this.prioridad = prioridad;
	}
	
	public String getNombreLocalAuthority()
	{
		return nombreLocalAuthority;
	}

	public String getCodigo()
	{
		return codigo;
	}

	public ListaSimplementeEncadenada<AccidentesEnAnio> getAnios()
	{
		return accidentesDeLocalAuthority;
	}

	public double getPrioridad()
	{
		return prioridad;
	}

	@Override
	public int compareTo(LocalAuthority lA0)
	{
		// TODO Auto-generated method stub
		if (this.prioridad < lA0.prioridad)
			return -1;
		else if (this.prioridad > lA0.prioridad)
			return 1;
		else
			return 0;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		String acc = "";
		
		for (AccidentesEnAnio accidentes : accidentesDeLocalAuthority)	
			acc += accidentes.getAnio() + " : " + accidentes.getAccidentes() + "\n";
		
		return nombreLocalAuthority + " : " + prioridad + "\n" + acc; 
	}
}