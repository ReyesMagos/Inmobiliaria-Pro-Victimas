package co.gov.dps.inmovic.servicio.servicioweb;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.entidades.Bien;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;

public class ConsultaInmueble extends AsyncTask<String, Integer, Boolean> {

	ProgressDialog progressDialog;
	public ConsultaInmueble(){
		progressDialog = new ProgressDialog(ComunicadorGeneral.getActividad());
		progressDialog.setMessage("Cargando Comentarios");
		progressDialog.setCancelable(false);
	}
	@Override
	protected Boolean doInBackground(String... params) {
		boolean resul = true;

		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://190.12.157.123/UVInmuebles/ServicioInmueble.asmx";
		final String METHOD_NAME = "ConsultarInmueble";
		final String SOAP_ACTION = "http://tempuri.org/ConsultarInmueble";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id", ComunicadorGeneral.getBienAMostrar()
				.getPartitionKey().toString());
		request.addProperty("nombre", ComunicadorGeneral.getBienAMostrar()
				.getNombredelbien().toString());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);

		try {

			transporte.call(SOAP_ACTION, envelope);

			SoapObject respSoap = (SoapObject) envelope.getResponse();

			SoapObject so = (SoapObject) respSoap.getProperty(0);

			if (so == null) {
				resul = false;
			}

			Bien b = new Bien();

			b.setPartitionKey(so.getProperty(0).toString());

		} catch (Exception e) {
			return false;
		}

		return resul;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		progressDialog.show();
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Boolean result) {

		if (result) {

			Log.i("Inmueble", "Si existe");
			Resultados.iniciarActividad();
		} else {
			TareaWSInsercion insertarInmueble = new TareaWSInsercion();
			insertarInmueble.execute();

			Log.i("Inmueble", "no existe");
		}
		progressDialog.dismiss();
		super.onPostExecute(result);
	}

}
