package co.gov.dps.inmovic.dominio.controladores;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import co.gov.dps.inmovic.dominio.entidades.*;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Comentario;
import co.gov.dps.inmovic.servicio.servicioSOAP.ServicioConsultaPuntuacion;
import co.gov.dps.inmovic.servicio.servicioSOAP.ServicioConsultarInmueble;

public class ServicioSoapController {

	private String id;
	private String name;
	
	public ServicioSoapController() {
		
	}
	
	public ServicioSoapController(String id){
		this.id = this.getId();
	}

	public ServicioSoapController(String id, String name) {
		this.id = this.getId();
		this.name = this.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void consultarInmueble() {

		if (id.equals("") || name.equals("")) {
			return;
		} else {
			ServicioConsultarInmueble consulta = new ServicioConsultarInmueble(
					this.getId(), this.getName());
			consulta.execute();
		}

	}

	public void consultarPuntuacion(String id) {
		if (!id.equals("")) {
			ServicioConsultaPuntuacion puntuacion = new ServicioConsultaPuntuacion(
					this.getId());
			//System.out.println(this.getId().toString());
			puntuacion.execute();
		}

	}

	public void llenarPuntuaciones() {

		String[] puntuacionesFinal = new String[ServicioConsultaPuntuacion.puntuaciones.length];

		for (int i = 0; i < ServicioConsultaPuntuacion.puntuaciones.length; i++) {
			puntuacionesFinal[i] = ServicioConsultaPuntuacion.puntuaciones[i]
					.getComentario()
					+ "\nPuntuaci—n: "
					+ ServicioConsultaPuntuacion.puntuaciones[i]
							.getPuntuacion();

		}

		Comentario.llenaListViewComentario(puntuacionesFinal);
	}
	
	public void enviarAComunicadorComentario(Activity a){
		ComunicadorComentario.setActivity(a);
	}

}
