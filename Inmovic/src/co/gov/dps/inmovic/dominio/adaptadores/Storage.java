package co.gov.dps.inmovic.dominio.adaptadores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;
import java.io.InputStreamReader;

import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;

public class Storage {

	public Storage() {

	}

	/**
	 * Este metodo se encarga de guardar en la memoria interna el archivo dque
	 * contiene la informacion de los diferentes inmuebles para facilitar el
	 * acceso de la aplicacion cuando se esta en modo offline
	 * 
	 * @param JSON
	 *            String que contiene el archivo JSON con la informacion de los
	 *            diferentes inmuebles
	 */
	public static void saveJSON(String JSON, int x) {

		// se declara el nombre con el cual va a quedar el archivo

		String filename = "myfile";
		if (x == 2) {
			filename = "myfile2";
		}

		// se guarda el String recibido
		String string = JSON;

		FileOutputStream outputStream;

		try {

			// se declara el modo en el que se guardara el archivo y se carga su
			// direccion
			outputStream = ComunicadorGeneral.actividad.openFileOutput(
					filename, Context.MODE_PRIVATE);

			// se escribe el archivo en la memoria
			outputStream.write(string.getBytes());

			// se cierra la comunicacion
			outputStream.close();
			if (x == 1) {
				Log.i("salvado", "Guarde json Inmuebles");
			} else {
				Log.i("salvado", "Guarde json Bienes Venta");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo se encarga de leer la memoria interna para buscar el archivo
	 * JSON y acelerar la carga del programa, o ofrecer la informacion de los
	 * inmuebles en el metodo Offline.
	 * 
	 * 
	 * 
	 * @return regresa un string con el archivo leido o nulo cuando hay un error
	 */
	@SuppressWarnings("finally")
	public static String readJSON(int x) {
		// primero se declara el nombre del archivo
		String filename = "myfile";
		if (x == 2) {
			filename = "myfile2";
		}

		// creamos una variable tipo File, accediendo a la direccion de las
		// carpetas de la aplicacion y mandandole el nombre del archivo
		File yourFile = new File(ComunicadorGeneral.actividad.getFilesDir(),
				filename);

		String line = null;

		FileInputStream fis;
		try {
			// abrimos la direccion del archivo
			fis = ComunicadorGeneral.actividad.openFileInput(filename);

			// lee la direccion del archivo
			InputStreamReader isr = new InputStreamReader(fis);

			// se declara un buffer para la direccion del archivo
			BufferedReader bufferedReader = new BufferedReader(isr);

			StringBuilder sb = new StringBuilder();

			// se inicia en este ciclo para leer cada una de las lineas del
			// archivo
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}

			// se lleva a string la buffer
			line = sb.toString();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return line;
		}

	}

}
