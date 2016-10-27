package interfaz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import mundo.AccidentesEnAnio;
import mundo.Grafico;
import mundo.LocalAuthority;
import mundo.PriorityComparator;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import estructuras.ListaSimplementeEncadenada;
import estructuras.MaxHeap;

public class ParteC
{
	private ParteA parteA;
	private ParteB parteB;

	public ParteC()
	{
		this.parteA = new ParteA();
		this.parteB = new ParteB();
	}

	public ParteA darParteA()
	{
		return parteA;
	}

	public ParteB darParteB()
	{
		return parteB;
	}

	public void R1C(String pRutaRoadCasualtiesBoroughsParteC, String pRutaTrafficYearsWeight) throws IOException
	{
		MaxHeap<LocalAuthority> maxHeapLocalAuthorities = new MaxHeap<LocalAuthority>(new PriorityComparator());

		byte[] serializadoLocalAutorithies = Files.readAllBytes(Paths.get(pRutaRoadCasualtiesBoroughsParteC));
		String stringJSONLocalAuthorities = new String(serializadoLocalAutorithies, "UTF-8");
		JsonElement elementoLocalAuthorities = new JsonParser().parse(stringJSONLocalAuthorities);
		JsonArray jArrayLocalAuthorities = elementoLocalAuthorities.getAsJsonArray();

		byte[] serializadoPesos = Files.readAllBytes(Paths.get(pRutaTrafficYearsWeight));
		String stringJSONPesos = new String(serializadoPesos, "UTF-8");
		JsonElement elementoPesos = new JsonParser().parse(stringJSONPesos);
		JsonArray jArrayPesos = elementoPesos.getAsJsonArray();

		int intervaloCargaCiudades = 50;
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

		for (int i = 0; i < intervaloCargaCiudades; i++)
		{	
			if (i == (intervaloCargaCiudades - 1))
				intervaloCargaCiudades = 100;

			JsonObject jsonDataLocalAuthorities = jArrayLocalAuthorities.get(i).getAsJsonObject();
			JsonObject jsonDataPesos = jArrayPesos.get(0).getAsJsonObject();

			String nombreLocalAuthority = jsonDataLocalAuthorities.get("Local-Authority").getAsString();
			String codigo = jsonDataLocalAuthorities.get("Code").getAsString();

			ListaSimplementeEncadenada<AccidentesEnAnio> accidentesDeLocalAuthority = new ListaSimplementeEncadenada<AccidentesEnAnio>();
			double prioridad = 0;

			for (Integer j = 2004; j < 2015; j++)
			{
				int accidentesAnio = jsonDataLocalAuthorities.get("" + j).getAsInt();
				double pesoAnio = jsonDataPesos.get("" + j).getAsDouble();
				prioridad += (accidentesAnio)*(pesoAnio);
				AccidentesEnAnio accidenteActual = new AccidentesEnAnio(j, accidentesAnio);
				accidentesDeLocalAuthority.addLast(accidenteActual);
			}

			long inicio = System.nanoTime();

			LocalAuthority localAuthorityActual = new LocalAuthority(nombreLocalAuthority, codigo, accidentesDeLocalAuthority, prioridad);
			maxHeapLocalAuthorities.add(localAuthorityActual);

			long tiempoCarga = System.nanoTime() - inicio;

			dataSet.addValue((double) tiempoCarga, "TiempoA", "" + (i+1));
		}

		Grafico grafico = new Grafico("1C", "1A vs 1B", dataSet, "Número de ciudades", "Tiempo de ejecución");

		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	@SuppressWarnings("unused")
	public void R2C1ParteA(String codigoLocalAuthority, int anio)
	{
		long tiempo = 0;
		int i = 0;
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

		for (; i < 100; i++)
		{
			parteA.R2A(codigoLocalAuthority, anio);
			tiempo += System.nanoTime();
			dataSet.addValue(tiempo, "Tiempo", "" + (i+1));
		}

		Grafico grafico = new Grafico("2C-1", "2A", dataSet, "# Repeticiones", "Tiempo de ejecución");
	}

	@SuppressWarnings("unused")
	public void R2C1ParteB(String codigoLocalAuthority, int anio)
	{
		long tiempo = 0;
		int i = 0;
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

		for (; i < 100; i++)
		{
			parteB.R2B(codigoLocalAuthority, anio);
			tiempo += System.nanoTime();
			dataSet.addValue(tiempo, "Tiempo", "" + (i+1));
		}

		Grafico grafico = new Grafico("2C-1", "2B", dataSet, "# Repeticiones", "Tiempo de ejecución");
	}
	
	@SuppressWarnings("unused")
	public void R3C(String nombreLocalAuthority, int anio)
	{
		long inicio = System.nanoTime();
		parteA.R3A(nombreLocalAuthority, anio);
		parteB.R3B(nombreLocalAuthority, anio);
		long tiempoCarga = System.nanoTime() - inicio;
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.addValue(tiempoCarga, "Tiempo", "" + 1);
		
		Grafico grafico = new Grafico("3C", "3A vs 3B", dataSet, "# Búsquedas", "Tiempo de ejecución");
	}
}