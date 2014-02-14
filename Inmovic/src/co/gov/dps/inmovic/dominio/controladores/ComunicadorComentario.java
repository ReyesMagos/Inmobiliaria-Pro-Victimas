package co.gov.dps.inmovic.dominio.controladores;

import android.app.Activity;

public class ComunicadorComentario {
	
	public static Activity actividad;
	
	public static Activity getActividad() {
		return actividad;
	}
	
	public static void setActivity(Activity a){
		ComunicadorComentario.actividad = a;
	}

}
