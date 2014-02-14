package co.gov.dps.inmovic.servicio.accesoweb.accesobieninmobiliario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.controladores.ControllerBusqueda2;
import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.dominio.singletonentidades.SingletonBienes;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda2;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.SeleccionarTipoBusqueda;
import co.gov.dps.inmovic.servicio.servicioweb.ServicioRest;

public class AccesoBienEnVenta {

	public List<BienALaVenta> listaBienesALaVenta;
	public ArrayList<String> elementosMostrados;

	public AccesoBienEnVenta() {
		SingletonBienes bienes = SingletonBienes.getInstance();
		listaBienesALaVenta = bienes.getListaBienesALaVenta();
		elementosMostrados = new ArrayList<String>();

	}

	public static void cargadoEspecial() {
		ServicioRest service = new ServicioRest(
				"bienesalaventauariv?$format=json", "especial");
		service.execute();
	}

	@SuppressLint("NewApi")
	public String[] cargarTipoBienes() {
		if (listaBienesALaVenta == null) {
			if (!verificaConexion(ComunicadorGeneral.getActividad())) {
				Busqueda.showErrorMessage(
						"La Aplicacion no tiene los datos minimos para realizar esta acci—n. Debe estar conectado a internet al menos en una ejecuci—n",
						"Falta de Datos", 1);
				return null;
			} else {
				cargadoEspecial();
			}

			// ComunicadorGeneral.actividad.finish();

		}
		for (BienALaVenta b : listaBienesALaVenta) {
			if (!verificarElementoEnVector(b.getTipo(), elementosMostrados)) {
				elementosMostrados.add(b.getTipo());
			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenarArreglo(arreglo);
		return arreglo;

	}

	public String[] cargarUbicaciones() {
		elementosMostrados = new ArrayList<String>();
		if (listaBienesALaVenta == null) {
			return null;
		}
		for (BienALaVenta b : listaBienesALaVenta) {
			if (!verificarElementoEnVector(b.getUbicacion(), elementosMostrados)) {
				elementosMostrados.add(b.getUbicacion());
			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenarArreglo(arreglo);
		return arreglo;

	}

	public String[] cargarValores() {

		elementosMostrados = new ArrayList<String>();
		if (listaBienesALaVenta == null) {
			return null;
		}
		for (BienALaVenta b : listaBienesALaVenta) {
			if (!verificarElementoEnVector(b.getValor(), elementosMostrados)) {
				elementosMostrados.add(b.getValor());
			}
		}
		String[] arreglo = new String[elementosMostrados.size()];
		arreglo = elementosMostrados.toArray(arreglo);
		arreglo = ordenaArregloNumeros(arreglo);
		return arreglo;

	}

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

	public static boolean verificaConexion(Context ctx) {
		boolean bConectado = false;
		ConnectivityManager connec = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] redes = connec.getAllNetworkInfo();
		for (int i = 0; i < 2; i++) {

			if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
				bConectado = true;
			}
		}
		return bConectado;
	}

}
