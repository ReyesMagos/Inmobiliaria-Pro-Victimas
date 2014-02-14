package co.gov.dps.inmovic.dominio.controladores;

import android.app.Activity;

public class ControlallerSeleccionarTipoBusqueda {

	public void establerActividadEnComunicadorGeneral(Activity actividad) {
		ComunicadorGeneral.setActividad(actividad);
	}

	public Activity devolverActividadEnComunicadorGeneral() {
		return ComunicadorGeneral.getActividad();
	}
	public void setLLamadaDesdeDestacados(){
		ComunicadorGeneral.setFromDestacados(false);
	}

}
