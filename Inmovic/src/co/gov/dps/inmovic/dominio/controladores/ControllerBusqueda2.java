package co.gov.dps.inmovic.dominio.controladores;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.gov.dps.inmovic.dominio.adaptadores.Storage;
import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.dominio.entidades.Nodo;
import co.gov.dps.inmovic.dominio.singletonentidades.SingletonBienes;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda2;
import co.gov.dps.inmovic.servicio.accesoweb.accesobieninmobiliario.AccesoBienEnVenta;
import co.gov.dps.inmovic.servicio.servicioweb.ServicioRest;
import android.app.Activity;

public class ControllerBusqueda2 {

	private AccesoBienEnVenta accesoBienEnVenta;
	private List<BienALaVenta> listaBusquedaVenta;

	public ControllerBusqueda2() {
		accesoBienEnVenta = new AccesoBienEnVenta();
		listaBusquedaVenta = new ArrayList<BienALaVenta>();

	}

	public String[] gestionarCargaTipoDeBienBienesEnVenta() {
		accesoBienEnVenta = new AccesoBienEnVenta();

		return accesoBienEnVenta.cargarTipoBienes();
	}

	public String[] gestionarCargaUbicacionesBienesEnVenta() {
		return accesoBienEnVenta.cargarUbicaciones();
	}

	public String[] gestionarCargaValoresBienesEnVenta() {
		return accesoBienEnVenta.cargarValores();
	}

	public void enviarTipoDeBien2Seleccionado(String s) {
		ComunicadorBusqueda.setTipoBien2Seleccionado(s);

	}

	public void enviarUbicacionSeleccionado(String s) {
		ComunicadorBusqueda.setUbicacionSeleccionado(s);
	}

	public void enviarValor2Seleccionado(String s) {
		ComunicadorBusqueda.setValor2Seleccionado(s);
	}

	public void enviarOpcionResultado(int i) {
		ComunicadorBusqueda.setOpcionResultados(i);
	}

	public int traerOpcionResultado() {
		return ComunicadorBusqueda.getOpcionResultados();
	}

	public void establerActividadEnComunicadorGeneral(Activity actividad) {
		ComunicadorGeneral.setActividad(actividad);
	}

	public Activity devolverActividadEnComunicadorGeneral() {
		return ComunicadorGeneral.getActividad();
	}

	public static List<BienALaVenta> traerListaBienesALaVentaBuscados() {
		return ComunicadorBusqueda.getListaBienesALaVentaBuscados();
	}

	public boolean verificarOpcionesBusquedaSeleccionadas() {
		if (ComunicadorBusqueda.getTipoBien2Seleccionado() != null
				|| ComunicadorBusqueda.getUbicacionSeleccionado() != null
				|| ComunicadorBusqueda.getValor2Seleccionado() != null) {
			return true;

		}
		return false;
	}

	public static void gestionarCargaBienesVenta() {
		String r = Storage.readJSON(2);

		// si el String es nulo esto quiere decir que no hay archivos, por
		// lo
		// cual se hace la carga del servicio web
		if (r == null) {
			ServicioRest service = new ServicioRest(
					"bienesalaventauariv?$format=json", "venta");
			service.execute();

		} else { // si hay archivos se cargan los inmuebles con la
			// informacion
			// del archivo

			try {

				// se declara un objeto tipo JSON y se carga pasandole el
				// archivo encontrado
				JSONObject respJSON = new JSONObject(r);

				// se llevaa un arreglo los diferentes objetos almacenados
				// en el
				// JSON
				JSONArray arregloJson = respJSON.getJSONArray("d");

				// se llama la instancia de bienes inmuebles
				SingletonBienes singletonBienes = SingletonBienes.getInstance();

				// se mandan a llenar los archivos inmuebles con el arreglo
				singletonBienes.llenaBienesAlaVenta(arregloJson);

				Busqueda2.loadSpinnerTipoBienElements();
				Busqueda2.loadSpinnerUbicacionElements();
				Busqueda2.loadSpinnerValorElements();
				// singletonBienes.cargaImagenesDestacados();

				// se llama el metodo que devolvera a la vista los bienes
				// para
				// mostrar como destacados

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void crearListaLigadaBusquedaBienEnVenta() {
		Nodo Inicio = new Nodo(-1, "Comienzo", null);
		Nodo Primero = new Nodo();
		Nodo x = new Nodo();
		if (ComunicadorBusqueda.getTipoBien2Seleccionado() != null) {
			Primero.setBien(ComunicadorBusqueda.getTipoBien2Seleccionado());
			Primero.setValor(1);
			Inicio.setLiga(Primero);

		}
		if (ComunicadorBusqueda.getUbicacionSeleccionado() != null) {
			if (Inicio.getLiga() == null) {
				Primero.setBien(ComunicadorBusqueda.getUbicacionSeleccionado());
				Primero.setValor(2);
				Inicio.setLiga(Primero);
			} else {
				x = new Nodo(2, ComunicadorBusqueda.getUbicacionSeleccionado());
				Primero.setLiga(x);
				Primero = x;
			}
		}
		if (ComunicadorBusqueda.getValor2Seleccionado() != null) {
			if (Inicio.getLiga() == null) {
				Primero.setBien(ComunicadorBusqueda.getValor2Seleccionado());
				Primero.setValor(3);
				Inicio.setLiga(Primero);
			} else {
				x = new Nodo(3, ComunicadorBusqueda.getValor2Seleccionado());
				Primero.setLiga(x);
				Primero = x;
			}
		}
		if (Inicio.getLiga() != null) {
			listaBusquedaVenta = new ArrayList<BienALaVenta>();
			ComunicadorBusqueda
					.setListaBienesALaVenta(new ArrayList<BienALaVenta>());
			SingletonBienes bienes = SingletonBienes.getInstance();
			ComunicadorBusqueda.setBienesALaVentaBuscados(false);
			buscarBienesALaVenta(Inicio.getLiga(),
					bienes.getListaBienesALaVenta());
		}
	}

	public void buscarBienesALaVenta(Nodo x,
			List<BienALaVenta> listaBienesVentaAux) {
		int n;
		n = listaBienesVentaAux.size();
		switch (x.getValor()) {
		case 1:
			for (int i = 0; i < n; i++) {
				if (listaBienesVentaAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isBienesALaVentaBuscados() == false) {
					if (listaBienesVentaAux.get(i).getTipo()
							.equals(x.getBien())) {

						listaBusquedaVenta.add(listaBienesVentaAux.get(i));
					}
				} else if (!listaBienesVentaAux.get(i).getTipo()
						.equals(x.getBien())) {
					listaBusquedaVenta.remove(listaBienesVentaAux.get(i));
					i -= 1;
					n = listaBusquedaVenta.size();
				}
			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setBienesALaVentaBuscados(true);
				buscarBienesALaVenta(x.getLiga(), listaBusquedaVenta);
			} else {
				ComunicadorBusqueda
						.setListaBienesALaVentaBuscados(listaBusquedaVenta);
				Busqueda2.showResults();

			}
			break;
		case 2:
			for (int i = 0; i < n; i++) {
				if (listaBienesVentaAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isBienesALaVentaBuscados() == false) {
					if (listaBienesVentaAux.get(i).getUbicacion()
							.equals(x.getBien())) {

						listaBusquedaVenta.add(listaBienesVentaAux.get(i));
					}
				} else if (!listaBienesVentaAux.get(i).getUbicacion()
						.equals(x.getBien())) {
					listaBusquedaVenta.remove(listaBienesVentaAux.get(i));
					i -= 1;
					n = listaBusquedaVenta.size();
				}
			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setBienesALaVentaBuscados(true);
				buscarBienesALaVenta(x.getLiga(), listaBusquedaVenta);
			} else {
				ComunicadorBusqueda
						.setListaBienesALaVentaBuscados(listaBusquedaVenta);
				Busqueda2.showResults();

			}
			break;
		case 3:
			for (int i = 0; i < n; i++) {
				if (listaBienesVentaAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isBienesALaVentaBuscados() == false) {
					if (listaBienesVentaAux.get(i).getValor()
							.equals(x.getBien())) {

						listaBusquedaVenta.add(listaBienesVentaAux.get(i));
					}
				} else if (!listaBienesVentaAux.get(i).getValor()
						.equals(x.getBien())) {
					listaBusquedaVenta.remove(listaBienesVentaAux.get(i));
					i -= 1;
					n = listaBusquedaVenta.size();
				}
			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setBienesALaVentaBuscados(true);
				buscarBienesALaVenta(x.getLiga(), listaBusquedaVenta);
			} else {
				ComunicadorBusqueda
						.setListaBienesALaVentaBuscados(listaBusquedaVenta);
				Busqueda2.showResults();

			}
			break;
		default:
			break;
		}
	}

	public void searchEveryThing() {
		SingletonBienes bienes = SingletonBienes.getInstance();
		if (bienes.getListaBienesALaVenta() != null) {
			ComunicadorBusqueda.setListaBienesALaVentaBuscados(bienes
					.getListaBienesALaVenta());
			Busqueda2.showResults();
		}
	}

	public String getTittleForListView(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.toString(s.charAt(i)).equals(";")) {
				s = s.substring(0, i);
				return s;
			}
		}
		return s;
	}

}
