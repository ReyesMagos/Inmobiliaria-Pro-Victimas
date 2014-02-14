package co.gov.dps.inmovic.servicio.servicioSOAP;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import co.gov.dps.inmovic.dominio.controladores.ServicioSoapController;
import co.gov.dps.inmovic.dominio.entidades.*;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Comentario;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ServicioConsultaPuntuacion extends
		AsyncTask<String, Integer, Boolean> {

	private String idInmueble;
	public static Puntuacion[] puntuaciones;
	final String NAMESPACE = "http://tempuri.org/";
	final String URL = "http://190.12.157.123/UVInmuebles/ServicioInmueble.asmx";
	final String METHOD_NAME = "ConsultarPuntuacion";
	final String SOAP_ACTION = "http://tempuri.org/ConsultarPuntuacion";
	private ArrayAdapter<String> adaptador;
	private ListView lista;
	

	public ServicioConsultaPuntuacion(String idInmueble,
			Puntuacion[] puntuaciones, ArrayAdapter<String> adaptador,
			ListView lista) {
		super();
		this.idInmueble = this.getIdInmueble();
		ServicioConsultaPuntuacion.puntuaciones = this.getPuntuaciones();
		this.adaptador = this.getAdaptador();
		this.lista = this.getLista();
	}
	
	public ServicioConsultaPuntuacion(String id){
		id = this.getIdInmueble();
		
	}

	public String getIdInmueble() {
		return idInmueble;
	}

	public void setIdInmueble(String idInmueble) {
		this.idInmueble = idInmueble;
	}

	public Puntuacion[] getPuntuaciones() {
		return puntuaciones;
	}

	public void setPuntuaciones(Puntuacion[] puntuaciones) {
		ServicioConsultaPuntuacion.puntuaciones = puntuaciones;
	}

	public ArrayAdapter<String> getAdaptador() {
		return adaptador;
	}

	public void setAdaptador(ArrayAdapter<String> adaptador) {
		this.adaptador = adaptador;
	}

	public ListView getLista() {
		return lista;
	}

	public void setLista(ListView lista) {
		this.lista = lista;
	}

	@Override
	protected Boolean doInBackground(String... arg0) {

		boolean resul = true;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("idInmueble", this.getIdInmueble());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);

		try {

			transporte.call(SOAP_ACTION, envelope);

			SoapObject respSoap = (SoapObject) envelope.getResponse();

			ServicioConsultaPuntuacion.puntuaciones = new Puntuacion[respSoap.getPropertyCount()];
			System.out.println(respSoap.getPropertyCount());

			for (int i = 0; i < ServicioConsultaPuntuacion.puntuaciones.length; i++) {
				SoapObject ic = (SoapObject) respSoap.getProperty(i);

				Puntuacion p = new Puntuacion();

				p.setId(Integer.parseInt(ic.getProperty(0).toString()));
				p.setIdInmueble(ic.getProperty(1).toString());
				p.setPuntuacion(Integer.parseInt(ic.getProperty(2).toString()));
				//p.setComentario(ic.getProperty(4).toString());

				ServicioConsultaPuntuacion.puntuaciones[i] = p;
			}

		} catch (Exception e) {
			return false;
		}

		return resul;
	}

	@Override
	protected void onPostExecute(Boolean result) {

		if (result) {

		ServicioSoapController sc = new ServicioSoapController();
		sc.llenarPuntuaciones();
			
		}

		super.onPostExecute(result);
	}
	

}
