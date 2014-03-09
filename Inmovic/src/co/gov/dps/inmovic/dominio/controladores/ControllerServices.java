package co.gov.dps.inmovic.dominio.controladores;

import org.json.JSONArray;

import co.gov.dps.inmovic.dominio.singletonentidades.SingletonBienes;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda2;
import co.gov.dps.inmovic.presentacion.vistas.destacados.DestacadoUI;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ControllerServices {

	public static Activity getGeneralActivity() {
		return ComunicadorGeneral.getActividad();
	}

	public void llenaBienesArriendo(JSONArray arregloJson) {
		SingletonBienes singletonBienes = SingletonBienes.getInstance();
		singletonBienes.llenaInmboli(arregloJson);
		// singletonBienes.cargaImagenesDestacados();
		ControllerDestacados controlador = new ControllerDestacados();
		controlador.devuelveBienesDestacados();
	}

	public void llenaBienesALaVenta(JSONArray arregloJson) {
		SingletonBienes singletonBienes = SingletonBienes.getInstance();
		singletonBienes.llenaBienesAlaVenta(arregloJson);
	}

	public void chargeSpinners() {
		Busqueda2.loadSpinnerTipoBienElements();
		Busqueda2.loadSpinnerUbicacionElements();
		Busqueda2.loadSpinnerValorElements();
	}

	public void showBusquedaErrorMessages(String s) {
		Busqueda.showErrorMessage(s, "faltan Datos", 2);

	}

	public void showDestacadosMessage(String s) {
		DestacadoUI.mensajeDeAlerta(s, "Error");
	}

	public static boolean verificaConexion() {
		boolean bConectado = false;
		ConnectivityManager connec = (ConnectivityManager) getGeneralActivity()
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
