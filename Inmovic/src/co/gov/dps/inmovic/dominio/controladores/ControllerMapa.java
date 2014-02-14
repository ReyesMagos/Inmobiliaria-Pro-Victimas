package co.gov.dps.inmovic.dominio.controladores;

import java.util.List;

import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.servicio.googlemap.MapaEscenario;

public class ControllerMapa {
	public static List<BienInmobiliario> li;

	public static List<BienInmobiliario> getLi() {
		return li;
	}

	public static void setLi(List<BienInmobiliario> li) {
		ControllerMapa.li = li;
	
	}
	
	
}
