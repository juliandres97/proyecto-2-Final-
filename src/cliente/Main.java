package cliente;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(System.out));
		Scanner lector = new Scanner(System.in);
		
		
		PruebasParteA ppa = new PruebasParteA(escritor, lector);
		PruebasParteB ppb = new PruebasParteB(escritor, lector);
		PruebasParteC ppc = new PruebasParteC(escritor, lector);
		
		int opcion = -1;
		
		while (opcion != 0) {
			try {
				escritor.write("---------------Pruebas Proyecto 2---------------\n");
				escritor.write("Ingrese un numeral\n");
				escritor.write("Opciones:\n");
				escritor.write("1: Pruebas Parte A\n");
				escritor.write("2: Pruebas Parte B\n");
				escritor.write("3: Pruebas Parte C\n");
				escritor.write("0: Salir\n");
				escritor.write("------------------------------------------------\n");
				escritor.flush();
				opcion = lector.nextInt();
				
				switch(opcion) {
				case 1: ppa.pruebas(); break;
				case 2: ppb.pruebas(); break;
				case 3: ppc.pruebas(); break;
				}
			}
			catch (IOException e) {
				e.printStackTrace();
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
		
		try {
			escritor.close();
			lector.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

}