package co.gov.dps.inmovic.dominio.controladores;

import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import android.app.Activity;
import android.view.View;

public class ComunicadorGeneral {

	public static Activity actividad;
	public static Activity actividadBuscado;
	public static int[] Visitados;
	public static int layoutActivoEnBusqueda;
	public static BienInmobiliario bienAMostrar;
	public static BienALaVenta bienALaVentaAMostrar;
	public static boolean haydatosminimos;
	public static boolean fromDestacados;
	public static int llamadas;

	public static int llamada2;
	
	
	public static View imagen;

	public static View getImagen() {
		return imagen;
	}
	
	

	public static int getLlamada2() {
		return llamada2;
	}



	public static void setLlamada2(int llam2) {
		llamada2 = llam2;
	}



	public static int getLlamadas() {
		return llamadas;
	}

	public static void setLlamadas(int ll) {
		llamadas = ll;
	}

	public static void setImagen(View imagen) {
		ComunicadorGeneral.imagen = imagen;
	}

	public static boolean isFromDestacados() {
		return ComunicadorGeneral.fromDestacados;
	}

	public static void setFromDestacados(boolean fromDestacados) {
		ComunicadorGeneral.fromDestacados = fromDestacados;
	}

	public static boolean isHaydatosminimos() {
		return haydatosminimos;
	}

	public static void setHaydatosminimos(boolean haydatosminimos) {
		ComunicadorGeneral.haydatosminimos = haydatosminimos;
	}

	public static BienALaVenta getBienALaVentaAMostrar() {
		return bienALaVentaAMostrar;
	}

	public static void setBienALaVentaAMostrar(BienALaVenta bienALaVentaAMostrar) {
		ComunicadorGeneral.bienALaVentaAMostrar = bienALaVentaAMostrar;
	}

	public static Activity getActividadBuscado() {
		return actividadBuscado;
	}

	public static void setActividadBuscado(Activity actividadBuscado) {
		ComunicadorGeneral.actividadBuscado = actividadBuscado;
	}

	public static BienInmobiliario getBienAMostrar() {
		return bienAMostrar;

	}

	public static void setBienAMostrar(BienInmobiliario bienAMostrar) {
		ComunicadorGeneral.bienAMostrar = bienAMostrar;
	}

	public static int getLayoutActivoEnBusqueda() {
		return layoutActivoEnBusqueda;
	}

	public static void setLayoutActivoEnBusqueda(int layoutActivoEnBusqueda) {
		ComunicadorGeneral.layoutActivoEnBusqueda = layoutActivoEnBusqueda;
	}

	public static int[] getVisitados() {
		return Visitados;
	}

	public static void setVisitados(int[] visitados2) {
		Visitados = visitados2;
	}

	public static Activity getActividad() {
		return actividad;
	}

	public static void setActividad(Activity actividad) {
		ComunicadorGeneral.actividad = actividad;
	}

}
