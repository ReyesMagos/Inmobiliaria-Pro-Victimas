package co.gov.dps.inmovic.servicio.servicioSOAP;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

public class ServicioConsultarInmueble extends
		AsyncTask<String, Integer, Boolean> {

	private String id;
	private String nombre;
	final String NAMESPACE = "http://tempuri.org/";
	final String URL = "http://190.12.157.123/UVInmuebles/ServicioInmueble.asmx";
	final String METHOD_NAME = "ConsultarInmueble";
	final String SOAP_ACTION = "http://tempuri.org/ConsultarInmueble";

	public ServicioConsultarInmueble(String id, String nombre) {
		super();
		this.id = this.getId();
		this.nombre = this.getNombre();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	protected Boolean doInBackground(String... params) {

		boolean resul = true;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id", this.getId());
		request.addProperty("nombre", this.getNombre());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);

		try {

			transporte.call(SOAP_ACTION, envelope);

			SoapObject respSoap = (SoapObject) envelope.getResponse();
			
			if(respSoap.getProperty(0) == null){
				resul = false;
			}

		} catch (Exception e) {
			return false;
		}

		return resul;
	}

	@Override
	protected void onPostExecute(Boolean result) {

		if (result) {

			System.out.println("Si existe");
		} else {
			ServicioCrearInmueble s = new ServicioCrearInmueble(ServicioCrearInmueble.id, ServicioCrearInmueble.nombre, ServicioCrearInmueble.descripcion);
			s.execute();
			System.out.println("NO existe");
		}

		super.onPostExecute(result);
	}

}
