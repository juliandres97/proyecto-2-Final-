package cliente;

import interfaz.ParteA;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class PruebasParteA
{
	private static final String RUTA_ROAD_CASUALTIES = "./data/road-casualties-severity-borough.json";
	private static final String RUTA_ROAD_CASUALTIES_WEIGHT = "./data/road-casualties-severity-borough-years-weight.json";
	private static final String RUTA_TRAFFIC_FLOW_ALL = "./data/traffic-flow-borough-all.json";
	private static final String RUTA_TRAFFIC_FLOW_CARS = "./data/traffic-flow-borough-cars.json";

	BufferedWriter escritor;
	Scanner lector;


	//TODO: Declarar objetos de la parte A
	private ParteA pA;

	public PruebasParteA(BufferedWriter escritor, Scanner lector) {
		this.escritor = escritor;
		this.lector = lector;
	}

	public void pruebas() {
		int opcion = -1;

		//TODO: Inicializar objetos de la parte A
		pA = new ParteA();


		long tiempoDeCarga = System.nanoTime();
		//TODO: Cargar informacion de la parte A
		try
		{
			pA.cargarRoadCasualtiesSeverityBoroughsDesdeJSON(RUTA_ROAD_CASUALTIES, RUTA_ROAD_CASUALTIES_WEIGHT);
			pA.cargarTrafficFlowBoroughsDesdeJSON(RUTA_TRAFFIC_FLOW_ALL, RUTA_TRAFFIC_FLOW_CARS);
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
				escritor.write("1: Imprimir la información de cada local_authority de mayor a menor prioridad. \n");
				escritor.write("2: Responder consultas sobre el número de accidentes en un año particular. \n");
				escritor.write("3: Conocer los accidentes, flujo de carros y flujo de todos los vehículos, de una local_authority (dado su nombre), en un año específico \n");
				escritor.write("0: Volver\n");
				escritor.write("------------------------------------------------\n");
				escritor.flush();
				opcion = lector.nextInt();

				switch(opcion) {
				case 1: a1(); break;
				case 2: a2(); break;
				case 3: a3(); break;

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

	private void a1() throws IOException{

		//TODO: Genere la prioridad de cada local_authority usando la media ponderada de los accidentes de todos los años.
		//Recuerde que los pesos por año corresponden a un porcentaje y que estos pesos se encuentran en el archivo road-casualties-severity-borough-years-weight.json.

		//TODO: Defina una cola de prioridad orientada a mayor a partir de los datos que se encuentran en el archivo road-casualties-severity-borough.json.

		long tiempo = System.nanoTime();
		//TODO: Obtener e imprimir la información de cada local_authority de mayor a menor prioridad, incluyendo el valor de prioridad, ejemplo: <local-authority_x>, <prioridad_local_authority_x>.
		//RECUERDE: Debe hacer uso del algoritmo HeapSort
		pA.R1A();
		
		tiempo = System.nanoTime() - tiempo;
		escritor.write("Duracion: " + tiempo + " nanosegundos\n");
		escritor.write("Ingrese cualquier letra y Enter para continuar\n");
		escritor.flush();
		lector.next();
	}

	private void a2() throws IOException{
		//TODO: Defina una tabla de hash aplicando la técnica "Separate Chaining" para el indexamiento por c—digo de local_authority
		//y utilizando como llave el código de las local_authorities 

		//RECUERDE: El valor asociado a cada llave es el conjunto de accidentes detallado por a–o para la Local Authority
		//y estos valores se indexarán nuevamente en una segunda tabla de hash utilizando una implementación de "Linear Probing"

		//TODO: Defina una segunda tabla de hash utilizando una implementación de "Linear Probing".
		//En esta segunda tabla la llave es el año y el valor el número de accidentes de dicho año.

		//RECUERDE: La segunda tabla de hash esta almacenada dentro de la primera tabla de hash: Estructura de datos doble hash 

		//TODO: Solicitar al usuario la informaci—n requerida para el desarrollo del literal: c—digo de una local_authority y a–o de consulta
		//RECUERDE: El objeto del literal es responder consultas sobre el nœmero de accidentes en un a–o particular de una local_authority dado su c—digo.
		//En ese orden de ideas debe solicitar al usuario un a–o y el c—digo de una local_authority
		System.out.println("Ingrese el código de la Local Authority");
		String codigoLocalAuthority = lector.next();
		
		System.out.println("Ingrese el año que desea consultar");
		int anio = lector.nextInt();

		long tiempo = System.nanoTime();
		//TODO: Obtener e imprimir la información sobre el nœmero de accidentes en el año de interes.
		pA.R2A(codigoLocalAuthority, anio);
		
		tiempo = System.nanoTime() - tiempo;
		escritor.write("Duracion: " + tiempo + " nanosegundos\n");
		escritor.write("Ingrese cualquier letra y Enter para continuar\n");
		escritor.flush();
		lector.next();		
	}

	private void a3() throws IOException{
		//TODO: Construya un árbol binario balanceado ordenado por el nombre de las local_authority (llave).
		//En cada nodo se debe tener asociado el código de la local_autority. 

		//TODO: Construya un segundo ‡rbol binario balanceado ordenado por el código de las local_authority (llave).
		//En cada nodo se debe tener dos tablas de hash:

		//TABLA 1: Esta tabla debe contener la información de "traffic flow cars". Llave: año de consulta
		//TABLA 2: Esta tabla debe contener la información de "traffic flow all vehicles". Llave: año de consulta 

		//RECUERDE: La implementación de la primera tabla se debe hacer mediante "Separate Chaining" y la segunda mediante "Linear Probing"

		//TODO: Solicitar al usuario la información requerida para el desarrollo del literal: nombre de una local_authority y a–o de consulta
		//RECUERDE: El objeto del literal es darle a conocer al usuario los accidentes, flujo de carros y flujo de todos los vehículos, de una local_authority dado su nombre, en un año específico.
		//En ese orden de ideas debe solicitar al usuario un a–o y el nombre de una local_authority	
		System.out.println("Ingrese el nombre de la Local Authority");
		String nombreLocalAuthority = lector.next();
		
		System.out.println("Ingrese el año que desea consultar");
		int anio = lector.nextInt();

		long tiempo = System.nanoTime();
		//TODO: Obtener e imprimir la informaci—n sobre los accidentes, flujo de carros y flujo de todos los vehículos dado el nombre del local_authority de interes.
		pA.R3A(nombreLocalAuthority, anio);
		
		tiempo = System.nanoTime() - tiempo;
		escritor.write("Duracion: " + tiempo + " nanosegundos\n");
		escritor.write("Ingrese cualquier letra y Enter para continuar\n");
		escritor.flush();
		lector.next();		
	}
}