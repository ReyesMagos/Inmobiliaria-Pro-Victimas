package co.gov.dps.inmovic.servicio.servicioweb;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorComentario;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.controladores.ControllerResultados;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Comentario;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;

public class TareaWSInsercion extends AsyncTask<String, Integer, Boolean> {

	private ProgressDialog progress;
	private static ControllerResultados controladorResultado;

	public TareaWSInsercion() {
		controladorResultado = Resultados.getControladorResultado();
		progress = new ProgressDialog(controladorResultado.obtenerActividad());
		progress.setMessage("Cargando Comentarios");
		progress.setCancelable(false);
	}

	@Override
	protected void onPreExecute() {
		progress.show();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean resul = true;

		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://190.12.157.123/UVInmuebles/ServicioInmueble.asmx";
		final String METHOD_NAME = "CrearInmueble";
		final String SOAP_ACTION = "http://tempuri.org/CrearInmueble";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		request.addProperty("id", ComunicadorGeneral.getBienAMostrar()
				.getPartitionKey().toString());
		request.addProperty("nombre", ComunicadorGeneral.getBienAMostrar()
				.getNombredelbien().toString());
		request.addProperty("descripcion", ComunicadorGeneral.getBienAMostrar()
				.getDescripcion().toString());
		request.addProperty("puntuacionPromedio", 0);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

		HttpTransportSE transport = new HttpTransportSE(URL);

		try {

			transport.call(SOAP_ACTION, envelope);

			SoapPrimitive resulatadoXml = (SoapPrimitive) envelope
					.getResponse();
			String res = resulatadoXml.toString();

			if (res.equals("1")) {
				resul = false;
			}

		} catch (Exception e) {
			resul = false;
		}

		return resul;
	}

	@Override
	protected void onPostExecute(Boolean result) {

		if (result) {
			System.out.println("Insertado Ok");
			Resultados.iniciarActividad();
		} else {
			System.out.println("Error no inserto");
			Resultados
					.mensajeAlertaError("En este momento no se pueden mostrar los comentarios, intente mas tarde");

		

		}
		progress.dismiss();
	}
}
