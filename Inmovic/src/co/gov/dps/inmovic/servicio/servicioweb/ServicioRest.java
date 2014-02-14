package co.gov.dps.inmovic.servicio.servicioweb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.gov.dps.inmovic.dominio.adaptadores.Storage;
import co.gov.dps.inmovic.dominio.controladores.ControllerBusqueda;
import co.gov.dps.inmovic.dominio.controladores.ControllerDestacados;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.dominio.singletonentidades.SingletonBienes;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda2;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.SeleccionarTipoBusqueda;
import co.gov.dps.inmovic.presentacion.vistas.destacados.DestacadoUI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.util.Log;

public class ServicioRest extends AsyncTask<String, Integer, Boolean> {

	private String respStr;
	private String añadidoUrl;
	private String opcion;
	private String url;
	private String[] datosEscenarios;
	private String[] datosEventos;
	private int caso;
	private JSONArray arregloJson;
	private boolean funciono;
	private List<BienInmobiliario> listaInmbo;
	private ProgressDialog progressDialog;

	/**
	 * 
	 */
	public ServicioRest(String urlAux, String opcionAux) {
		this.url = "http://servicedatosabiertoscolombia.cloudapp.net/v1/Atencion_Reparacion_Integral_Victimas/";
		this.url += urlAux;
		this.opcion = opcionAux;
		progressDialog = new ProgressDialog(ComunicadorGeneral.getActividad());
		progressDialog.setMessage("Buscando; Por Favor Espere");
		progressDialog.setCancelable(false);

	}

	public JSONArray getArregloJson() {
		return arregloJson;
	}

	public void setArregloJson(JSONArray arregloJson) {
		this.arregloJson = arregloJson;
	}

	public boolean isFunciono() {
		return funciono;
	}

	public void setFunciono(boolean funciono) {
		this.funciono = funciono;
	}

	public String getRespStr() {
		return respStr;
	}

	public void setRespStr(String respStr) {
		this.respStr = respStr;
	}

	public void llenaBienesArriendo() {
		SingletonBienes singletonBienes = SingletonBienes.getInstance();
		singletonBienes.llenaInmboli(arregloJson);
		// singletonBienes.cargaImagenesDestacados();
		ControllerDestacados controlador = new ControllerDestacados();
		controlador.devuelveBienesDestacados();
	}

	public void llenaBienesALaVenta() {
		SingletonBienes singletonBienes = SingletonBienes.getInstance();
		singletonBienes.llenaBienesAlaVenta(arregloJson);
	}

	protected Boolean doInBackground(String... params) {

		boolean resul = true;

		HttpClient httpClient = new DefaultHttpClient();

		HttpGet del = new HttpGet(url);

		InputStream inputStream = null;

		try {
			HttpResponse resp = httpClient.execute(del);

			HttpEntity entity = resp.getEntity();
			inputStream = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			respStr = sb.toString();

		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
			resul = false;
		}
		return resul;
	}

	@Override
	protected void onPreExecute() {
		if (!opcion.equals("oculto") && !opcion.equals("oculto2")) {
			progressDialog.show();
		}

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

	protected void onPostExecute(Boolean result) {
		if (result) {
			funciono = true;
			JSONObject respJSON;
			try {
				if (respStr == null) {

					progressDialog.dismiss();
					return;
				}

				respJSON = new JSONObject(respStr);
				arregloJson = respJSON.getJSONArray("d");
				if (opcion.toLowerCase().equals("destacado")) {
					Storage.saveJSON(respStr, 1);
					llenaBienesArriendo();
				} else if (opcion.toLowerCase().equals("venta")) {

					Storage.saveJSON(respStr, 2);
					llenaBienesALaVenta();
					Busqueda2.loadSpinnerTipoBienElements();
					Busqueda2.loadSpinnerUbicacionElements();
					Busqueda2.loadSpinnerValorElements();

				} else if (opcion.equals("oculto")) {
					Storage.saveJSON(respStr, 1);

				} else if (opcion.equals("oculto2")) {
					Storage.saveJSON(respStr, 2);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		} else {
			if (opcion.toLowerCase().equals("destacado")) {
				if (verificaConexion(ComunicadorGeneral.getActividad())) {
					DestacadoUI.mensajeDeAlerta("Error Inesperado", "Error");

				} else {
					DestacadoUI.mensajeDeAlerta(
							"Error falta de conexion a internet", "Error");
				}
			} else if (opcion.toLowerCase().equals("venta")) {

				Busqueda.showErrorMessage(
						"La Aplicacion no tiene los datos minimos para realizar esta acci—n. Debe estar conectado a internet al menos en una ejecuci—n",
						"Falta de Datos", 2);/*
											 * / Intent upIntent = new
											 * Intent(ComunicadorGeneral
											 * .getActividad(),
											 * SeleccionarTipoBusqueda.class);
											 * if
											 * (NavUtils.shouldUpRecreateTask(
											 * ComunicadorGeneral
											 * .getActividad(), upIntent)) {
											 * android
											 * .support.v4.app.TaskStackBuilder
											 * .
											 * from(ComunicadorGeneral.getActividad
											 * ()) .addNextIntent(upIntent).
											 * startActivities();
											 * 
											 * } else { NavUtils.navigateUpTo(
											 * ComunicadorGeneral
											 * .getActividad(), upIntent);
											 * 
											 * }/
											 */
			}
		}
		progressDialog.dismiss();

	}
}
