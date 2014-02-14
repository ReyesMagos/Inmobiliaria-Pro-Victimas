package co.gov.dps.inmovic.servicio.servicioweb;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Comentario;

public class TareaWSInsertarPuntuacion extends
		AsyncTask<String, Integer, Boolean> {

	@Override
	protected Boolean doInBackground(String... params) {
		boolean resul = true;
		int puntuacion = (int) Comentario.valPuntuacion.getRating();

		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://190.12.157.123/UVInmuebles/ServicioInmueble.asmx";
		final String METHOD_NAME = "CrearPuntuacion";
		final String SOAP_ACTION = "http://tempuri.org/CrearPuntuacion";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		request.addProperty("idInmueble", ComunicadorGeneral.getBienAMostrar()
				.getPartitionKey().toString());
		request.addProperty("puntuacion", puntuacion);
		request.addProperty("comentario", Comentario.comentario.getText()
				.toString());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

		HttpTransportSE transport = new HttpTransportSE(URL);

		try {

			transport.call(SOAP_ACTION, envelope);

			SoapPrimitive resultadoXML = (SoapPrimitive) envelope.getResponse();
			String res = resultadoXML.toString();

			if (!res.equals("1")) {
				resul = false;
			}

		} catch (Exception e) {
			resul = false;
		}

		return resul;
	}

	@SuppressLint("ShowToast")
	@Override
	protected void onPostExecute(Boolean result) {

		if (result) {
			// Toast.makeText(getApplicationContext(),
			// "Comentario Enviado Correctamente", 2);
			System.out.println("Insertado Ok");
		} else {
			System.out.println("Error al insertar");
		}

		super.onPostExecute(result);
	}

}