package interfaz;

import estructuras.*;
import mundo.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.*;

public class ParteA
{	
	private static final String RUTA_ROAD_CASUALTIES = "./data/road-casualties-severity-borough.json";
	
	private MaxHeap<LocalAuthority> maxHeapLocalAuthorities;
	private SeparateChainingHashTable<String, LinearProbingHashTable<Integer, Integer>> tablaLocalAuthoritiesCodigoSCHashTable;
	private SeparateChainingHashTable<String, String> tablaLocalAuthoritiesCodigoNombre;
	private RedBlackBinaryTree<String, String> arbolLocalAuthorityNombreCodigo;
	private RedBlackBinaryTree<String, ValueHandler<Integer, Integer>> arbolCodigoTablasHash;
	
	public ParteA()
	{
		maxHeapLocalAuthorities = new MaxHeap<LocalAuthority>(new PriorityComparator());
		tablaLocalAuthoritiesCodigoSCHashTable = new SeparateChainingHashTable<String, LinearProbingHashTable<Integer, Integer>>();
		arbolLocalAuthorityNombreCodigo = new RedBlackBinaryTree<String, String>();
		arbolCodigoTablasHash = new RedBlackBinaryTree<String, ValueHandler<Integer,Integer>>();
		tablaLocalAuthoritiesCodigoNombre = new SeparateChainingHashTable<String, String>();
		
		try {
			cargarTablaLocalAuthoritiesCodigoNombre(RUTA_ROAD_CASUALTIES);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarRoadCasualtiesSeverityBoroughsDesdeJSON( String pRutaRoadCasualtiesBoroughs, String pRutaTrafficYearsWeight ) throws FileNotFoundException, IOException
	{
		byte[] serializadoLocalAutorithies = Files.readAllBytes(Paths.get(pRutaRoadCasualtiesBoroughs));
		String stringJSONLocalAuthorities = new String(serializadoLocalAutorithies, "UTF-8");
		JsonElement elementoLocalAuthorities = new JsonParser().parse(stringJSONLocalAuthorities);
		JsonArray jArrayLocalAuthorities = elementoLocalAuthorities.getAsJsonArray();

		byte[] serializadoPesos = Files.readAllBytes(Paths.get(pRutaTrafficYearsWeight));
		String stringJSONPesos = new String(serializadoPesos, "UTF-8");
		JsonElement elementoPesos = new JsonParser().parse(stringJSONPesos);
		JsonArray jArrayPesos = elementoPesos.getAsJsonArray();

		for (int i = 0; i < jArrayLocalAuthorities.size(); i++)
		{	
			JsonObject jsonDataLocalAuthorities = jArrayLocalAuthorities.get(i).getAsJsonObject();
			JsonObject jsonDataPesos = jArrayPesos.get(0).getAsJsonObject();

			String nombreLocalAuthority = jsonDataLocalAuthorities.get("Local-Authority").getAsString();
			String codigo = jsonDataLocalAuthorities.get("Code").getAsString();

			ListaSimplementeEncadenada<AccidentesEnAnio> accidentesDeLocalAuthority = new ListaSimplementeEncadenada<AccidentesEnAnio>();
			double prioridad = 0;

			LinearProbingHashTable<Integer, Integer> tablaAniosAccidentes = new LinearProbingHashTable<Integer, Integer>(22);

			for (int j = 2004; j < 2015; j++)
			{
				int accidentesAnio = jsonDataLocalAuthorities.get("" + j).getAsInt();
				double pesoAnio = jsonDataPesos.get("" + j).getAsDouble();
				prioridad += (accidentesAnio)*(pesoAnio);
				AccidentesEnAnio accidenteActual = new AccidentesEnAnio(j, accidentesAnio);
				accidentesDeLocalAuthority.addLast(accidenteActual);

				tablaAniosAccidentes.put(j, accidentesAnio);
			}

			tablaLocalAuthoritiesCodigoSCHashTable.put(codigo, tablaAniosAccidentes);

			LocalAuthority localAuthorityActual = new LocalAuthority(nombreLocalAuthority, codigo, accidentesDeLocalAuthority, prioridad);
			maxHeapLocalAuthorities.add(localAuthorityActual);
		}
	}

	public void cargarTrafficFlowBoroughsDesdeJSON( String pRutaTrafficFlowAll, String pRutaTrafficFlowCars ) throws FileNotFoundException, IOException
	{	
		byte[] serializadoTrafficFlowAll = Files.readAllBytes(Paths.get(pRutaTrafficFlowAll));
		String stringJSONTrafficFlowAll = new String(serializadoTrafficFlowAll, "UTF-8");
		JsonElement elementoTrafficFlowAll = new JsonParser().parse(stringJSONTrafficFlowAll);
		JsonArray jArrayTrafficFlowAll = elementoTrafficFlowAll.getAsJsonArray();

		byte[] serializadoTrafficFlowCars = Files.readAllBytes(Paths.get(pRutaTrafficFlowCars));
		String stringJSONTrafficFlowCars = new String(serializadoTrafficFlowCars, "UTF-8");
		JsonElement elementoTrafficFlowCars = new JsonParser().parse(stringJSONTrafficFlowCars);
		JsonArray jArrayTrafficFlowCars = elementoTrafficFlowCars.getAsJsonArray();

		for (int i = 0; i < jArrayTrafficFlowAll.size(); i++)
		{
			JsonObject jsonDataTrafficFlowAll = jArrayTrafficFlowAll.get(i).getAsJsonObject();
			JsonObject jsonDataTrafficFlowCars = jArrayTrafficFlowCars.get(i).getAsJsonObject();

			String codigo = jsonDataTrafficFlowAll.get("Code").getAsString();
			String nombreLocalAuthority = jsonDataTrafficFlowAll.get("Local-Authority").getAsString();

			arbolLocalAuthorityNombreCodigo.put(nombreLocalAuthority, codigo);

			LinearProbingHashTable<Integer, Integer> tablaLinearProbingAll = new LinearProbingHashTable<Integer, Integer>(44);
			SeparateChainingHashTable<Integer, Integer> tablaSeparateChainingCars = new SeparateChainingHashTable<Integer, Integer>();

			for (int j = 1993; j < 2015; j++)
			{
				int trafficFlowAllAnio = jsonDataTrafficFlowAll.get("" + j).getAsInt();
				int trafficFlowCarsAnio = jsonDataTrafficFlowCars.get("" + j).getAsInt();

				tablaLinearProbingAll.put(j, trafficFlowAllAnio);
				tablaSeparateChainingCars.put(j, trafficFlowCarsAnio);
			}

			ValueHandler<Integer, Integer> tablasHash = new ValueHandler<Integer, Integer>(tablaSeparateChainingCars, tablaLinearProbingAll);

			arbolCodigoTablasHash.put(codigo, tablasHash);
		}
	}
	
	private void cargarTablaLocalAuthoritiesCodigoNombre(String pRutaRoadCasualtiesBoroughs) throws IOException
	{
		byte[] serializadoLocalAutorithies = Files.readAllBytes(Paths.get(pRutaRoadCasualtiesBoroughs));
		String stringJSONLocalAuthorities = new String(serializadoLocalAutorithies, "UTF-8");
		JsonElement elementoLocalAuthorities = new JsonParser().parse(stringJSONLocalAuthorities);
		JsonArray jArrayLocalAuthorities = elementoLocalAuthorities.getAsJsonArray();

		for (int i = 0; i < jArrayLocalAuthorities.size(); i++)
		{	
			JsonObject jsonDataLocalAuthorities = jArrayLocalAuthorities.get(i).getAsJsonObject();

			String nombreLocalAuthority = jsonDataLocalAuthorities.get("Local-Authority").getAsString();
			String codigo = jsonDataLocalAuthorities.get("Code").getAsString();

			tablaLocalAuthoritiesCodigoNombre.put(codigo, nombreLocalAuthority);
		}
	}

	public void R1A()
	{
		for (LocalAuthority actual : maxHeapLocalAuthorities)
		{
			print(actual.toString());
		}
	}
	
	public void R2A(String codigoLocalAuthority, int anio)
	{
		LinearProbingHashTable<Integer, Integer> linearProbingTable = tablaLocalAuthoritiesCodigoSCHashTable.get(codigoLocalAuthority);
		int accidentes = linearProbingTable.get(anio);
		
		String nombreLocalidad = tablaLocalAuthoritiesCodigoNombre.get(codigoLocalAuthority);
		
		print("Local Authority: " + nombreLocalidad);
		print("Accidentes: " + accidentes);
	}
	
	public void R3A(String nombreLocalAuthority, int anio)
	{
		String codigo = arbolLocalAuthorityNombreCodigo.get(nombreLocalAuthority);
		String nombreRecuperadoLocalAuthority = tablaLocalAuthoritiesCodigoNombre.get(codigo);
		
		LinearProbingHashTable<Integer, Integer> tablaLPAccidentes = tablaLocalAuthoritiesCodigoSCHashTable.get(codigo);
		int accidentes = tablaLPAccidentes.get(anio);
		
		ValueHandler<Integer, Integer> hashTables = arbolCodigoTablasHash.get(codigo);
		SeparateChainingHashTable<Integer, Integer> tablaSCCars = hashTables.tablaSC;
		LinearProbingHashTable<Integer, Integer> tablaLPAll = hashTables.tablaLP;
		int flujoTraficoCarros = tablaSCCars.get(anio);
		int flujoTraficoTodo = tablaLPAll.get(anio);
		
		print(  "Local Authority: " + nombreRecuperadoLocalAuthority + "\n" +
				"Accidentes: " + accidentes + "\n" +
				"Flujo de carros en la zona: " + flujoTraficoCarros + "\n" +
				"Flujo de vehículos en la zona: " + flujoTraficoTodo );
	}
	
	private void print(String toPrint)
	{
		System.out.println(toPrint);
	}
}