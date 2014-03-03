package co.gov.dps.inmovic.dominio.controladores;

import java.util.List;

import android.app.Activity;

import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.servicio.googlemap.MapaEscenario;

public class ControllerMapa {
	public static List<BienInmobiliario> li;
	public static Activity actividad;

	public static Activity getActividad() {
		return actividad;
	}

	public static void mapear(boolean x) {
		ComunicadorGeneral.setMapeados(x);
	}

	public static boolean isMapeando() {
		return ComunicadorGeneral.isMapeados();
	}

	public static void setActividad(Activity actividad) {
		ControllerMapa.actividad = actividad;
	}

	public static List<BienInmobiliario> getLi() {
		return li;
	}

	public static void setLi(List<BienInmobiliario> li) {
		ControllerMapa.li = li;

	}

	public static void setbienAmostrar(BienInmobiliario bienAMostrar) {
		ComunicadorGeneral.setBienAMostrar(bienAMostrar);
	}

}
