package co.gov.dps.inmovic.servicio.accesoweb.accesobieninmobiliario;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.dominio.singletonentidades.SingletonBienes;

public class AccesoBienInmobiliario {

	public List<BienInmobiliario> listaBienInmobiliario;
	public ArrayList<String> elementosMostrados;

	public AccesoBienInmobiliario() {
		SingletonBienes bienes = SingletonBienes.getInstance();
		listaBienInmobiliario = bienes.getListaBienesInmobiliarios();
		elementosMostrados = new ArrayList<String>();
	}

	public String[] cargarDepartamentos() {

		// se cargan los bienes de esta

		for (int i = 0; i < listaBienInmobiliario.size(); i++) {
			BienInmobiliario j = listaBienInmobiliario.get(i);
			if (!verificarElementoEnVector(j.getDepartamento(),
					elementosMostrados)) {
				elementosMostrados.add(j.getDepartamento());

			}

		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenarArreglo(arreglo);
		return arreglo;
	}

	public String[] cargaMunicipios(String depto) {

		elementosMostrados = new ArrayList<String>();

		for (int i = 0; i < listaBienInmobiliario.size(); i++) {
			BienInmobiliario j = listaBienInmobiliario.get(i);

			if (!verificarElementoEnVector(j.getMunicipio(), elementosMostrados)
					&& j.getDepartamento().equals(depto)) {
				elementosMostrados.add(j.getMunicipio());

			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenarArreglo(arreglo);
		return arreglo;

	}

	public String[] cargaTipoBien() {
		elementosMostrados = new ArrayList<String>();
		for (int i = 0; i < listaBienInmobiliario.size(); i++) {
			BienInmobiliario j = listaBienInmobiliario.get(i);

			if (!verificarElementoEnVector(j.getTipoDeBien(),
					elementosMostrados)) {
				elementosMostrados.add(j.getTipoDeBien());

			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenarArreglo(arreglo);
		return arreglo;
	}

	public String[] cargaTipoInmueble() {
		elementosMostrados = new ArrayList<String>();
		for (int i = 0; i < listaBienInmobiliario.size(); i++) {
			BienInmobiliario j = listaBienInmobiliario.get(i);

			if (!verificarElementoEnVector(j.getTipo(), elementosMostrados)) {
				elementosMostrados.add(j.getTipo());

			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenarArreglo(arreglo);
		return arreglo;
	}

	public String[] cargaUsoBien() {
		elementosMostrados = new ArrayList<String>();
		for (int i = 0; i < listaBienInmobiliario.size(); i++) {
			BienInmobiliario j = listaBienInmobiliario.get(i);

			if (!verificarElementoEnVector(j.getUsodelbien(),
					elementosMostrados)) {
				elementosMostrados.add(j.getUsodelbien());

			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenarArreglo(arreglo);
		return arreglo;

	}

	public String[] cargaBanos() {
		elementosMostrados = new ArrayList<String>();
		for (int i = 0; i < listaBienInmobiliario.size(); i++) {
			BienInmobiliario j = listaBienInmobiliario.get(i);

			if (!verificarElementoEnVector(j.getNumBaño(), elementosMostrados)) {
				elementosMostrados.add(j.getNumBaño());

			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenaArregloNumeros(arreglo);
		return arreglo;

	}

	public String[] carganumeroHabitaciones() {
		elementosMostrados = new ArrayList<String>();
		for (int i = 0; i < listaBienInmobiliario.size(); i++) {
			BienInmobiliario j = listaBienInmobiliario.get(i);

			if (!verificarElementoEnVector(j.getNumHabitacion(),
					elementosMostrados)) {
				elementosMostrados.add(j.getNumHabitacion());

			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenaArregloNumeros(arreglo);
		return arreglo;

	}

	public String[] cargaValores() {
		elementosMostrados = new ArrayList<String>();
		for (int i = 0; i < listaBienInmobiliario.size(); i++) {
			BienInmobiliario j = listaBienInmobiliario.get(i);

			if (!verificarElementoEnVector(j.getValor(), elementosMostrados)) {
				elementosMostrados.add(j.getValor());

			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenaArregloNumeros(arreglo);
		return arreglo;

	}

	/**
	 * Este metodo comprueba si determinado String se encuentra dentro de una
	 * arreglo, en la mayoria de los casos este arreglo correspondera a l de las
	 * opciones que mostramos en la vista de busqueda
	 * 
	 * @param s
	 *            el string
	 * @param vS
	 *            el arreglo
	 * @return true si el elemento esta dentro del vector false de lo contrario
	 */
	public boolean verificarElementoEnVector(String s, ArrayList<String> vS) {
		if (s.equals("Sin informaci—n"))
			return true;
		for (String x : vS) {
			if (x.equals(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Este metodo se encarga de ordenar alfavbeticamente de menor a mayor los
	 * elemento de un vector
	 * 
	 * @param vS
	 *            vector a ordenar
	 * @return el vector ordenado ademas de que en su elemento 0 tiene la
	 *         palabra Seleccione
	 */
	public String[] ordenarArreglo(String[] vS) {

		String[] vec = new String[vS.length + 1];

		Arrays.sort(vS, String.CASE_INSENSITIVE_ORDER);
		vec[0] = "Seleccione";
		int i = 1;
		for (String s : vS) {
			vec[i] = s;
			i++;
		}
		return vec;
	}

	public String[] ordenaArregloNumeros(String[] vS) {

		String[] vec = new String[vS.length + 1];

		String saux;
		for (int j = 0; j < vS.length - 1; j++) {
			for (int k = j+1; k < vS.length; k++) {
				if (tryParse(vS[j]) && tryParse(vS[k])) {
					if (Integer.parseInt(vS[j]) > Integer.parseInt(vS[k])) {
						saux = vS[j];
						vS[j] = vS[k];
						vS[k] = saux;

					}
				} 
			}
		}
		vec[0] = "Seleccione";
		int i = 1;
		for (String s : vS) {
			vec[i] = s;
			i++;
		}
		return vec;
	}

	public boolean tryParse(String s) {
		try {
			Integer.parseInt(s);
			

		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
