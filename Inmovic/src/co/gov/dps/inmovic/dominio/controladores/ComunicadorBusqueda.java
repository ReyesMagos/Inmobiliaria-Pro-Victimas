package co.gov.dps.inmovic.dominio.controladores;

import java.util.List;

import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;

public class ComunicadorBusqueda {

	public static String departamentoSeleccionado;
	public static String municipioSeleccionado;
	public static String tipoBienSeleccionado;
	public static String tipoInmuebleSeleccionado;
	public static String usoBienSeleccionado;
	public static String banosSeleccionado;
	public static String habitacionesSeleccionado;
	public static String valorSeleccionado;
	public static String tipoBien2Seleccionado;
	public static String ubicacionSeleccionado;
	public static String valor2Seleccionado;
	public static List<BienInmobiliario> listaBienesInmobiliariosBuscados;
	public static List<BienALaVenta> listaBienesALaVentaBuscados;
	public static boolean inmueblesBuscadosCargados;
	public static boolean bienesALaVentaBuscados;
	public static int opcionResultados;
	
	

	public static int getOpcionResultados() {
		return opcionResultados;
	}

	public static void setOpcionResultados(int opcionResultados) {
		ComunicadorBusqueda.opcionResultados = opcionResultados;
	}

	public static List<BienALaVenta> getListaBienesALaVentaBuscados() {
		return listaBienesALaVentaBuscados;
	}

	public static void setListaBienesALaVentaBuscados(
			List<BienALaVenta> listaBienesALaVentaBuscados) {
		ComunicadorBusqueda.listaBienesALaVentaBuscados = listaBienesALaVentaBuscados;
	}

	public static boolean isBienesALaVentaBuscados() {
		return bienesALaVentaBuscados;
	}

	public static void setBienesALaVentaBuscados(boolean bienesALaVentaBuscados) {
		ComunicadorBusqueda.bienesALaVentaBuscados = bienesALaVentaBuscados;
	}

	public static List<BienALaVenta> getListaBienesALaVenta() {
		return getListaBienesALaVenta();
	}

	public static void setListaBienesALaVenta(
			List<BienALaVenta> listaBienesALaVenta) {
		ComunicadorBusqueda.listaBienesALaVentaBuscados = listaBienesALaVenta;
	}

	public static String getTipoBien2Seleccionado() {
		return tipoBien2Seleccionado;
	}

	public static void setTipoBien2Seleccionado(String tipoBien2Seleccionado) {
		ComunicadorBusqueda.tipoBien2Seleccionado = tipoBien2Seleccionado;
	}

	public static String getUbicacionSeleccionado() {
		return ubicacionSeleccionado;
	}

	public static void setUbicacionSeleccionado(String ubicacionSeleccionado) {
		ComunicadorBusqueda.ubicacionSeleccionado = ubicacionSeleccionado;
	}

	public static String getValor2Seleccionado() {
		return valor2Seleccionado;
	}

	public static void setValor2Seleccionado(String valor2Seleccionado) {
		ComunicadorBusqueda.valor2Seleccionado = valor2Seleccionado;
	}

	public static String getUsoBienSeleccionado() {
		return usoBienSeleccionado;
	}

	public static void setUsoBienSeleccionado(String usoBienSeleccionado) {
		ComunicadorBusqueda.usoBienSeleccionado = usoBienSeleccionado;
	}

	public static boolean isInmueblesBuscadosCargados() {
		return inmueblesBuscadosCargados;
	}

	public static void setInmueblesBuscadosCargados(
			boolean inmueblesBuscadosCargados) {
		ComunicadorBusqueda.inmueblesBuscadosCargados = inmueblesBuscadosCargados;
	}

	public static List<BienInmobiliario> getListaBienesInmobiliariosBuscados() {
		return listaBienesInmobiliariosBuscados;
	}

	public static void setListaBienesInmobiliariosBuscados(
			List<BienInmobiliario> listaBienesInmobiliariosBuscados) {
		ComunicadorBusqueda.listaBienesInmobiliariosBuscados = listaBienesInmobiliariosBuscados;
	}

	public static String getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	public static void setDepartamentoSeleccionado(
			String departamentoSeleccionado) {
		ComunicadorBusqueda.departamentoSeleccionado = departamentoSeleccionado;
	}

	public static String getMunicipioSeleccionado() {
		return municipioSeleccionado;
	}

	public static void setMunicipioSeleccionado(String municipioSeleccionado) {
		ComunicadorBusqueda.municipioSeleccionado = municipioSeleccionado;
	}

	public static String getTipoBienSeleccionado() {
		return tipoBienSeleccionado;
	}

	public static void setTipoBienSeleccionado(String tipoBienSeleccionado) {
		ComunicadorBusqueda.tipoBienSeleccionado = tipoBienSeleccionado;
	}

	public static String getTipoInmuebleSeleccionado() {
		return tipoInmuebleSeleccionado;
	}

	public static void setTipoInmuebleSeleccionado(
			String tipoInmuebleSeleccionado) {
		ComunicadorBusqueda.tipoInmuebleSeleccionado = tipoInmuebleSeleccionado;
	}

	public static String getUsBienSeleccionado() {
		return usoBienSeleccionado;
	}

	public static void setUsBienSeleccionado(String usBienSeleccionado) {
		ComunicadorBusqueda.usoBienSeleccionado = usBienSeleccionado;
	}

	public static String getBanosSeleccionado() {
		return banosSeleccionado;
	}

	public static void setBanosSeleccionado(String banosSeleccionado) {
		ComunicadorBusqueda.banosSeleccionado = banosSeleccionado;
	}

	public static String getHabitacionesSeleccionado() {
		return habitacionesSeleccionado;
	}

	public static void setHabitacionesSeleccionado(
			String habitacionesSeleccionado) {
		ComunicadorBusqueda.habitacionesSeleccionado = habitacionesSeleccionado;
	}

	public static String getValorSeleccionado() {
		return valorSeleccionado;
	}

	public static void setValorSeleccionado(String valorSeleccionado) {
		ComunicadorBusqueda.valorSeleccionado = valorSeleccionado;
	}

}
