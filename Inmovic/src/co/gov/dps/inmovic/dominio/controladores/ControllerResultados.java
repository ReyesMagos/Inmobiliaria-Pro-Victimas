package co.gov.dps.inmovic.dominio.controladores;

import android.app.Activity;
import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;
import co.gov.dps.inmovic.servicio.servicioweb.ServicioImagenes;

public class ControllerResultados {
	ServicioImagenes s1;
	BienInmobiliario b;
	BienALaVenta bV;

	public static int getOpcionResultados() {
		return ComunicadorBusqueda.getOpcionResultados();
	}

	public static void setOpcionResultados(int opcionResultados) {
		ComunicadorBusqueda.setOpcionResultados(opcionResultados);
	}

	public BienALaVenta traeBienALaVenta() {
		return ComunicadorGeneral.bienALaVentaAMostrar;
	}

	public void gestionarCargaElementoLayoutBienALaVenta() {
		bV = traeBienALaVenta();
		if (bV != null) {
			Resultados.llenarELementosCondatos2(bV);
		}
	}

	public void gestionaCargaElementoLayoutBieneInmobiliarios() {
		b = ComunicadorGeneral.getBienAMostrar();
		if (b != null) {
			Resultados.llenarELementosConDatosd(b);

		} else {
			Resultados.llenarELementosConDatosPorDefecto();
		}

	}

	public void ordenaCargaImagenes() {
		s1 = new ServicioImagenes(b.getUrlImagenes(), 2);

		s1.execute();
	}

	public void ordenaCargaImagenes2() {
		s1 = new ServicioImagenes(bV.getUrlImagenes(), 2);

		s1.execute();
	}

	public Activity obtenerActividad() {

		return ComunicadorGeneral.getActividad();

	}

	public void enviarActividadAComunicadorGeneral(Activity A) {
		ComunicadorGeneral.setActividad(A);
	}

	public void cancelarCargaDeImagenes() {
		if (s1 != null) {
			s1.cancel(true);
		}
	}

	public boolean isLLamadoDesdeDestacados() {
		return ComunicadorGeneral.isFromDestacados();
	}

	public void llamarDesdeDestacados(Boolean llamado) {
		ComunicadorGeneral.setFromDestacados(true);
	}
}
