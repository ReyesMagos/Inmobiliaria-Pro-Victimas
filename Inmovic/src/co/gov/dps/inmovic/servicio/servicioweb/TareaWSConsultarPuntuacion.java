package co.gov.dps.inmovic.servicio.servicioweb;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorComentario;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Comentario;

public class TareaWSConsultarPuntuacion extends
		AsyncTask<String, Integer, Boolean> {
	
	private ProgressDialog progress;
	
	public TareaWSConsultarPuntuacion(){
		
		/*progress = new ProgressDialog(ComunicadorComentario.getActividad());
		progress.setMessage("Cargando Comentarios");*/
	}
	
	@Override
	protected void onPreExecute() {
		
		//progress.show();
		
		super.onPreExecute();
	}

	private co.gov.dps.inmovic.dominio.entidades.Puntuacion[] puntuaciones;

	@Override
	protected Boolean doInBackground(String... params) {

		boolean resul = true;

		final String METHOD_NAME = "ConsultarPuntuacion";
		final String SOAP_ACTION = "http://tempuri.org/ConsultarPuntuacion";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://190.12.157.123/UVInmuebles/ServicioInmueble.asmx";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		String id = ComunicadorGeneral.getBienAMostrar().getPartitionKey()
				.toString();
		request.addProperty("idInmueble", id);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);

		try {

			transporte.call(SOAP_ACTION, envelope);

			SoapObject respSoap = (SoapObject) envelope.getResponse();

			puntuaciones = new co.gov.dps.inmovic.dominio.entidades.Puntuacion[respSoap
					.getPropertyCount()];

			for (int i = 0; i < puntuaciones.length; i++) {
				SoapObject ic = (SoapObject) respSoap.getProperty(i);

				co.gov.dps.inmovic.dominio.entidades.Puntuacion p = new co.gov.dps.inmovic.dominio.entidades.Puntuacion();

				p.setId(Integer.parseInt(ic.getProperty(0).toString()));
				p.setIdInmueble(ic.getProperty(1).toString());
				p.setPuntuacion(Integer.parseInt(ic.getProperty(2).toString()));
				p.setComentario(ic.getProperty(4).toString());

				puntuaciones[i] = p;

			}

		} catch (Exception e) {
			System.out.print(e);
			return false;
		}

		return resul;
	}
	
	

	@Override
	protected void onPostExecute(Boolean result) {

		if (result) {

			final String[] puntuacionesFinal = new String[puntuaciones.length];

			for (int i = 0; i < puntuaciones.length; i++) {
				puntuacionesFinal[i] = puntuaciones[i].getComentario()
						+ "\nPuntuacion: " + puntuaciones[i].getPuntuacion();

			}

			Comentario.asignaAdpatador(puntuacionesFinal);

		} else {
			System.out.println("Error");
			return;
		}
		
		//progress.dismiss();

	}

}
