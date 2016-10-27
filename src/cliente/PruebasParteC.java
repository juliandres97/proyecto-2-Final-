package cliente;

import interfaz.ParteC;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class PruebasParteC
{
	private static final String RUTA_ROAD_CASUALTIES = "./data/road-casualties-severity-borough.json";
	private static final String RUTA_ROAD_CASUALTIES_PARTEC = "./data/road-casualties-severity-borough-ParteC.json";
	private static final String RUTA_ROAD_CASUALTIES_CHILD = "./data/road-casualties-severity-borough-child.json";
	private static final String RUTA_ROAD_CASUALTIES_WEIGHT = "./data/road-casualties-severity-borough-years-weight.json";
	private static final String RUTA_TRAFFIC_FLOW_ALL = "./data/traffic-flow-borough-all.json";
	private static final String RUTA_TRAFFIC_FLOW_CARS = "./data/traffic-flow-borough-cars.json";
	
	BufferedWriter escritor;
	Scanner lector;
	
	//TODO: Declarar objetos de la parte A
    //TODO: Declarar objetos de la parte B
	private ParteC pC;
	
	public PruebasParteC(BufferedWriter escritor, Scanner lector)
	{
		this.escritor = escritor;
		this.lector = lector;
	}
	
	public void pruebas() {
		int opcion = -1;
		
		//TODO: Inicializar objetos de la parte A
		pC = new ParteC();
		
        //TODO: Inicializar objetos de la parte B
		
		long tiempoDeCarga = System.nanoTime();
        //TODO: Cargar informacion de la parte A
        //TODO: Cargar informacion de la parte B
		try {
			pC.darParteA().cargarRoadCasualtiesSeverityBoroughsDesdeJSON(RUTA_ROAD_CASUALTIES, RUTA_ROAD_CASUALTIES_WEIGHT);
			pC.darParteA().cargarTrafficFlowBoroughsDesdeJSON(RUTA_TRAFFIC_FLOW_ALL, RUTA_TRAFFIC_FLOW_CARS);
			pC.darParteB().cargarRoadCasualtiesSeverityBoroughsDesdeJSON(RUTA_ROAD_CASUALTIES_CHILD, RUTA_ROAD_CASUALTIES_WEIGHT);
			pC.darParteB().cargarTrafficFlowBoroughsDesdeJSON(RUTA_TRAFFIC_FLOW_ALL, RUTA_TRAFFIC_FLOW_CARS);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tiempoDeCarga = System.nanoTime() - tiempoDeCarga;
		
		while (opcion != 0) {
			try {
				escritor.write("---------------Pruebas Proyecto ---------------\n");
				escritor.write("Informacion cargada en: " + tiempoDeCarga + " nanosegundos\n");
				escritor.write("Reportes:\n");
				escritor.write("1: Requerimiento 1C. \n");
				escritor.write("2: Requerimiento 2C - 1. \n");
				escritor.write("3: Requerimiento 2C - 2. \n");
				escritor.write("4: Requerimiento 3C. \n");
				escritor.write("0: Volver\n");
				escritor.write("------------------------------------------------\n");
				escritor.flush();
				opcion = lector.nextInt();
				
				switch(opcion) {
				case 1: c1(); break;
				case 2: c21(); break;
				case 3: c22(); break;
				case 4: c3(); break;
				}
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			catch (NumberFormatException nfe) {
				try {
					escritor.write("No ingreso el periodo de tiempo correctamente\n");
					escritor.write("Ingrese cualquier letra y enter para continuar\n");
					escritor.flush();
					lector.nextLine();
					lector.nextLine();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			catch (InputMismatchException ime) {
				try {
					escritor.write("No ingreso un numeral\n");
					escritor.write("Ingrese cualquier letra y enter para continuar\n");
					escritor.flush();
					lector.nextLine();
					lector.nextLine();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
	
	private void c1() throws IOException{
		long tiempo = System.nanoTime();
		//TODO:Generar y obtener el conjunto de datos requeridos para realizar la grafica solicitada.
		//Para graficar PUEDE hacer uso de la  libreria  JFREECHART disponible en "http://www.jfree.org/jfreechart/"
		//Si opta por usar JFREECHART puede hacer uso (como guia) del ejemplo que se presenta en el siguiente link "https://www.tutorialspoint.com/jfreechart/jfreechart_line_chart.htm" 
		//Recuerde que en el eje de abcisas se tendrï¿½ el # de ciudades (N) y en el eje de ordenadas se tendrï¿½n los tiempos de ejecuciï¿½n en milisegundos o nanosegundos 
		//Independientemente de la forma en que grafique, nombre tanto la grafica como sus ejes
		pC.R1C(RUTA_ROAD_CASUALTIES_PARTEC, RUTA_ROAD_CASUALTIES_WEIGHT);
		
		tiempo = System.nanoTime() - tiempo;
		escritor.write("Duracion: " + tiempo + " nanosegundos\n");
		escritor.write("Ingrese cualquier letra y Enter para continuar\n");
		escritor.flush();
		lector.next();
	}
	
	private void c21() throws IOException{
		long tiempo = System.nanoTime();
		//TODO:Generar y obtener el conjunto de datos requeridos para realizar la grafica solicitada.
		//Para graficar PUEDE hacer uso de la  libreria  JFREECHART disponible en "http://www.jfree.org/jfreechart/"
		//Si opta por usar JFREECHART puede hacer uso (como guia) del ejemplo que se presenta en el siguiente link "https://www.youtube.com/watch?v=qVkqyuTiWCc" 
		//Recuerde que en el eje de abcisas se tendrï¿½ los metodos para indexar ("Separate Chaining""Linear Probing") y en el eje de ordenadas se tendrï¿½n los tiempos de ejecuciï¿½n en milisegundos o nanosegundos 
		//Independientemente de la forma en que grafique, nombre tanto la grafica como sus ejes
		System.out.println("Ingrese el código de la Local Authority");
		String codigoLocalAuthority = lector.next();
		
		System.out.println("Ingrese el año que desea consultar");
		int anio = lector.nextInt();
		
		pC.R2C1ParteA(codigoLocalAuthority, anio);
		pC.R2C1ParteB(codigoLocalAuthority, anio);
		
		tiempo = System.nanoTime() - tiempo;
		escritor.write("Duracion: " + tiempo + " nanosegundos\n");
		escritor.write("Ingrese cualquier letra y Enter para continuar\n");
		escritor.flush();
		lector.next();		
	}
	
	private void c22() throws IOException{
		long tiempo = System.nanoTime();
		//TODO:Generar y obtener el conjunto de datos requeridos para realizar la grafica solicitada.
		//Para graficar PUEDE hacer uso de la  libreria  JFREECHART disponible en "http://www.jfree.org/jfreechart/"
		//Si opta por usar JFREECHART puede hacer uso (como guia) del ejemplo que se presenta en el siguiente link "https://www.youtube.com/watch?v=qVkqyuTiWCc" 
		//Independientemente de la forma en que grafique, nombre tanto las grafica como sus ejes
		tiempo = System.nanoTime() - tiempo;
		escritor.write("Duracion: " + tiempo + " nanosegundos\n");
		escritor.write("Ingrese cualquier letra y Enter para continuar\n");
		escritor.flush();
		lector.next();	
		
	}
	private void c3() throws IOException{
		long tiempo = System.nanoTime();
		//TODO:Haga la comparacion pertienente, explique los resultados obtenidos
		//y concluya (Imprima en pantalla lo que compara, explique en la misma impresion
		//en que consiste dicha comparaciï¿½n e imprima su conclusiï¿½n)
		System.out.println("Ingrese el nombre de la Local Authority");
		String nombreLocalAuthority = lector.next();
		
		System.out.println("Ingrese el año que desea consultar");
		int anio = lector.nextInt();
		
		pC.R3C(nombreLocalAuthority, anio);
		
		tiempo = System.nanoTime() - tiempo;
		escritor.write("Duracion: " + tiempo + " nanosegundos\n");
		escritor.write("Ingrese cualquier letra y Enter para continuar\n");
		escritor.flush();
		lector.next();	
	}
}