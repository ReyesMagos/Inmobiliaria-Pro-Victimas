package co.gov.dps.inmovic.servicio.servicioSOAP;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

public class ServicioCrearInmueble extends AsyncTask<String, Integer, Boolean>{

	public static String id;
	public static String nombre;
	public static  String descripcion;
	final String NAMESPACE  = "http://tempuri.org/";
	final String URL = "http://190.12.157.123/UVInmuebles/ServicioInmueble.asmx";
	final String METHOD_NAME = "CrearInmueble";
	final String SOAP_ACTION = "http://tempuri.org/CrearInmueble";
	
	
	
	public ServicioCrearInmueble(String id, String nombre, String descripcion) {
		super();
		ServicioCrearInmueble.id = this.getId();
		ServicioCrearInmueble.nombre = this.getNombre();
		ServicioCrearInmueble.descripcion = this.getDescripcion();
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		ServicioCrearInmueble.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		ServicioCrearInmueble.nombre = nombre;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		ServicioCrearInmueble.descripcion = descripcion;
	}



	@Override
	protected Boolean doInBackground(String... arg0) {
		
		boolean resul = true;
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("id", this.getId());
		request.addProperty("nombre",this.getNombre());
		request.addProperty("descripcion", this.getDescripcion());
		request.addProperty("puntuacionPromedio", 0);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet= true;
		
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE transport = new HttpTransportSE(URL);
		
		try {
			
			transport.call(SOAP_ACTION, envelope);
			
			SoapPrimitive resulatadoXml = (SoapPrimitive)envelope.getResponse();
			String res = resulatadoXml.toString();
			
			if(!res.equals("1")){
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
		}else {
			System.out.println("Error no inserto");
		}
		super.onPostExecute(result);
	}

}
