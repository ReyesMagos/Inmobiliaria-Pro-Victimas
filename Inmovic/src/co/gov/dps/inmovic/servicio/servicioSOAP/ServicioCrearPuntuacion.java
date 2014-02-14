package co.gov.dps.inmovic.servicio.servicioSOAP;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

public class ServicioCrearPuntuacion extends AsyncTask<String, Integer, Boolean> {

	private int puntuacion;
	private String inmuebleId;
	private String comentario;

	
	
	
	public ServicioCrearPuntuacion(int puntuacion, String inmuebleId,
			String comentario) {
		super();
		this.puntuacion = this.getPuntuacion();
		this.inmuebleId = this.getInmuebleId();
		this.comentario = this.getComentario();
	}



	public int getPuntuacion() {
		return puntuacion;
	}



	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}



	public String getInmuebleId() {
		return inmuebleId;
	}



	public void setInmuebleId(String inmuebleId) {
		this.inmuebleId = inmuebleId;
	}



	public String getComentario() {
		return comentario;
	}



	public void setComentario(String comentario) {
		this.comentario = comentario;
	}



	@Override
	protected Boolean doInBackground(String... arg0) {
		boolean resul = true;
		
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://190.12.157.123/UVInmuebles/ServicioInmueble.asmx";
		final String METHOD_NAME = "CrearPuntuacion";
		final String SOAP_ACTION = "http://tempuri.org/CrearPuntuacion";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("idInmueble", this.getInmuebleId().toString());
		request.addProperty("puntuacion", puntuacion);
		request.addProperty("comentario", this.getComentario().toString());
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE transport = new HttpTransportSE(URL);
		
		try {
			
			transport.call(SOAP_ACTION,envelope);
			
			SoapPrimitive resultadoXML = (SoapPrimitive)envelope.getResponse();
			String res = resultadoXML.toString();
			
			if (!res.equals("1")) {
				resul = false;
			}
			
		} catch (Exception e) {
			resul = false;
		}
		
		return resul;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		
		if(result){
			System.out.println("Insertado Ok");
		}else{
			System.out.println("Error al insertar");
		}
		
		super.onPostExecute(result);
	}
	
	

}
