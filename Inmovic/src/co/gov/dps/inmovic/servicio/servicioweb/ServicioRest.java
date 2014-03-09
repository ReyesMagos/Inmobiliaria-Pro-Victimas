package co.gov.dps.inmovic.servicio.servicioweb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.gov.dps.inmovic.dominio.adaptadores.Storage;
import co.gov.dps.inmovic.dominio.controladores.ControllerServices;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class ServicioRest extends AsyncTask<String, Integer, Boolean> {

	private String respStr;
	private String anadidoUrl;
	private String opcion;
	private String url;
	private String[] datosEscenarios;
	private String[] datosEventos;
	private int caso;
	private JSONArray arregloJson;
	private boolean funciono;
	private List<BienInmobiliario> listaInmbo;
	private ProgressDialog progressDialog;
	private ControllerServices controladorServicios;

	/**
	 * 
	 */
	public ServicioRest(String urlAux, String opcionAux) {
		this.url = "http://servicedatosabiertoscolombia.cloudapp.net/v1/Atencion_Reparacion_Integral_Victimas/";
		this.url += urlAux;
		this.opcion = opcionAux;
		controladorServicios = new ControllerServices();
		progressDialog = new ProgressDialog(
				controladorServicios.getGeneralActivity());
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
					controladorServicios.llenaBienesArriendo(arregloJson);
				} else if (opcion.toLowerCase().equals("venta")) {

					Storage.saveJSON(respStr, 2);
					controladorServicios.llenaBienesALaVenta(arregloJson);
					controladorServicios.chargeSpinners();

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
				if (controladorServicios.verificaConexion()) {
					controladorServicios
							.showDestacadosMessage("Error Inesperado");

				} else {
					controladorServicios
							.showDestacadosMessage("Error falta de conexi—n a internet");
				}
			} else if (opcion.toLowerCase().equals("venta")) {

				controladorServicios
						.showBusquedaErrorMessages("La Aplicacion no tiene los datos minimos"
								+ "para realizar esta acci—n. Debe estar conectado a internet al menos en una ejecuci—n");
			}
		}
		progressDialog.dismiss();

	}
}
