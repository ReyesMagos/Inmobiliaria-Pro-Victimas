package co.gov.dps.inmovic.dominio.controladores;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import co.gov.dps.inmovic.dominio.adaptadores.Storage;
import co.gov.dps.inmovic.dominio.entidades.Bien;
import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.dominio.entidades.Nodo;
import co.gov.dps.inmovic.dominio.singletonentidades.SingletonBienes;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda;
import co.gov.dps.inmovic.presentacion.vistas.destacados.Bienvenida;
import co.gov.dps.inmovic.servicio.accesoweb.accesobieninmobiliario.AccesoBienEnVenta;
import co.gov.dps.inmovic.servicio.accesoweb.accesobieninmobiliario.AccesoBienInmobiliario;
import co.gov.dps.inmovic.servicio.servicioweb.ServicioRest;

public class ControllerBusqueda {

	private AccesoBienInmobiliario accesoBienInmobiliario;

	private List<BienInmobiliario> listaBusqueda;

	public ControllerBusqueda() {
		accesoBienInmobiliario = new AccesoBienInmobiliario();
		listaBusqueda = new ArrayList<BienInmobiliario>();

	}

	// ================================================================================
	// Metodos Para Cargar los elementos de la Vista
	// ================================================================================

	public String[] gestionaCargaDepartamentos() {
		return accesoBienInmobiliario.cargarDepartamentos();
	}

	public void setLLamadaDesdeDestacados() {
		ComunicadorGeneral.setFromDestacados(false);
	}

	public String[] gestionaCargaMunicipios(String depto) {
		return accesoBienInmobiliario.cargaMunicipios(depto);
	}

	public String[] gestionaCargaTipoBien() {
		return accesoBienInmobiliario.cargaTipoBien();
	}

	public String[] gestionaCargaTipoDeInmueble() {
		return accesoBienInmobiliario.cargaTipoInmueble();
	}

	public String[] gestionaCargaUsoDelBien() {
		return accesoBienInmobiliario.cargaUsoBien();
	}

	public String[] gestionaCargaNumeroBanos() {
		return accesoBienInmobiliario.cargaBanos();
	}

	public String[] gestionaCargaNumeroHabitaciones() {
		return accesoBienInmobiliario.carganumeroHabitaciones();
	}

	public String[] gestionaCargaValores() {
		return accesoBienInmobiliario.cargaValores();
	}

	// ********************************************************************************

	// ================================================================================
	// Metodos De la Gestion Con El Comunicador
	// ================================================================================

	public void enviarDepartamentoSeleccionado(String s) {
		ComunicadorBusqueda.setDepartamentoSeleccionado(s);
	}

	public void enviarMunicipioSeleccionado(String s) {
		ComunicadorBusqueda.setMunicipioSeleccionado(s);
	}

	public void enviarTipodeBienSeleccionado(String s) {
		ComunicadorBusqueda.setTipoBienSeleccionado(s);
	}

	public void enviarTipoInmuebleSeleccionado(String s) {
		ComunicadorBusqueda.setTipoInmuebleSeleccionado(s);
	}

	public void enviarUsoBienSeleccionado(String s) {
		ComunicadorBusqueda.setUsBienSeleccionado(s);
	}

	public void enviarNumeroBanosSeleccionado(String s) {
		ComunicadorBusqueda.setBanosSeleccionado(s);
	}

	public void enviarNumeroHabitacionesSeleccionado(String s) {
		ComunicadorBusqueda.setHabitacionesSeleccionado(s);
	}

	public void enviarValorSeleccionado(String s) {
		ComunicadorBusqueda.setValorSeleccionado(s);
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

	public List<BienInmobiliario> traerListaDeBienesInmobiliariosBuscados() {
		return ComunicadorBusqueda.getListaBienesInmobiliariosBuscados();

	}

	public void anadirBienAMostrarEnComunicadorGeneral(BienInmobiliario b) {
		ComunicadorGeneral.setBienAMostrar(b);
	}

	public void anadirListaDeBienesInmobiliariosBuscados(
			List<BienInmobiliario> lb) {
		ComunicadorBusqueda.setListaBienesInmobiliariosBuscados(lb);
	}

	public boolean verificarOpcionesBusquedaSeleccionadas() {
		if (ComunicadorBusqueda.getDepartamentoSeleccionado() != null
				|| ComunicadorBusqueda.getMunicipioSeleccionado() != null
				|| ComunicadorBusqueda.getTipoBienSeleccionado() != null
				|| ComunicadorBusqueda.getTipoInmuebleSeleccionado() != null
				|| ComunicadorBusqueda.getUsBienSeleccionado() != null
				|| ComunicadorBusqueda.getBanosSeleccionado() != null
				|| ComunicadorBusqueda.getHabitacionesSeleccionado() != null
				|| ComunicadorBusqueda.getValorSeleccionado() != null) {
			return true;

		}
		return false;
	}

	// ********************************************************************************

	// ================================================================================
	// Metodos Para Realizar Las Busquedas
	// ================================================================================

	public void crearLigaBusquedaBieneInmobiliarios() {
		Nodo Inicio = new Nodo(-1, "Comienzo", null);
		Nodo Primero = new Nodo();
		Nodo x = new Nodo();
		ComunicadorBusqueda.setListaBienesInmobiliariosBuscados(null);
		if (ComunicadorBusqueda.departamentoSeleccionado != null) {
			Primero.setBien(ComunicadorBusqueda.departamentoSeleccionado);
			Primero.setValor(1);
			Inicio.setLiga(Primero);
			if (ComunicadorBusqueda.municipioSeleccionado != null) {
				x.setBien(ComunicadorBusqueda.municipioSeleccionado);
				x.setValor(2);
				Primero.setLiga(x);
				Primero = x;
			}
		}
		if (ComunicadorBusqueda.tipoBienSeleccionado != null) {
			if (Inicio.getLiga() == null) {
				Primero.setBien(ComunicadorBusqueda.tipoBienSeleccionado);
				Primero.setValor(3);
				Inicio.setLiga(Primero);
			} else {
				x = new Nodo(3, ComunicadorBusqueda.tipoBienSeleccionado);
				Primero.setLiga(x);
				Primero = x;
			}
		}
		if (ComunicadorBusqueda.tipoInmuebleSeleccionado != null) {
			if (Inicio.getLiga() == null) {
				Primero.setBien(ComunicadorBusqueda.tipoInmuebleSeleccionado);
				Primero.setValor(4);
				Inicio.setLiga(Primero);
			} else {
				x = new Nodo(4, ComunicadorBusqueda.tipoInmuebleSeleccionado);
				Primero.setLiga(x);
				Primero = x;
			}
		}
		if (ComunicadorBusqueda.usoBienSeleccionado != null) {
			if (Inicio.getLiga() == null) {
				Primero.setBien(ComunicadorBusqueda.usoBienSeleccionado);
				Primero.setValor(5);
				Inicio.setLiga(Primero);
			} else {
				x = new Nodo(5, ComunicadorBusqueda.usoBienSeleccionado);
				Primero.setLiga(x);
				Primero = x;
			}
		}
		if (ComunicadorBusqueda.banosSeleccionado != null) {
			if (Inicio.getLiga() == null) {
				Primero.setBien(ComunicadorBusqueda.banosSeleccionado);
				Primero.setValor(6);
				Inicio.setLiga(Primero);
			} else {
				x = new Nodo(6, ComunicadorBusqueda.banosSeleccionado);
				Primero.setLiga(x);
				Primero = x;
			}
		}
		if (ComunicadorBusqueda.habitacionesSeleccionado != null) {
			if (Inicio.getLiga() == null) {
				Primero.setBien(ComunicadorBusqueda.habitacionesSeleccionado);
				Primero.setValor(7);
				Inicio.setLiga(Primero);
			} else {
				x = new Nodo(7, ComunicadorBusqueda.habitacionesSeleccionado);
				Primero.setLiga(x);
				Primero = x;
			}
		}
		if (ComunicadorBusqueda.valorSeleccionado != null) {
			if (Inicio.getLiga() == null) {
				Primero.setBien(ComunicadorBusqueda.valorSeleccionado);
				Primero.setValor(8);
				Inicio.setLiga(Primero);
			} else {
				x = new Nodo(8, ComunicadorBusqueda.valorSeleccionado);
				Primero.setLiga(x);
				Primero = x;
			}
		}
		if (Inicio.getLiga() != null) {
			listaBusqueda = new ArrayList<BienInmobiliario>();
			ComunicadorBusqueda
					.setListaBienesInmobiliariosBuscados(new ArrayList<BienInmobiliario>());
			SingletonBienes bienes = SingletonBienes.getInstance();
			ComunicadorBusqueda.setInmueblesBuscadosCargados(false);
			buscarBienesInmuebles(Inicio.getLiga(),
					bienes.getListaBienesInmobiliarios());

		}

	}

	public void buscarBienesInmuebles(Nodo x,
			List<BienInmobiliario> listaBienesAux) {

		int n;
		n = listaBienesAux.size();
		switch (x.getValor()) {
		case 1:
			for (int i = 0; i < n; i++) {
				if (listaBienesAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isInmueblesBuscadosCargados() == false) {

					if (listaBienesAux.get(i).getDepartamento()
							.equals(x.getBien())) {

						listaBusqueda.add(listaBienesAux.get(i));
					}
				} else if (!listaBienesAux.get(i).getDepartamento()
						.equals(x.getBien())) {
					listaBusqueda.remove(listaBienesAux.get(i));
					i -= 1;
					n = listaBusqueda.size();
				}

			}

			if (x.getLiga() != null) {
				ComunicadorBusqueda.setInmueblesBuscadosCargados(true);
				buscarBienesInmuebles(x.getLiga(), listaBusqueda);
			} else {
				ComunicadorBusqueda
						.setListaBienesInmobiliariosBuscados(listaBusqueda);
				Busqueda.showResults();

			}
			break;
		case 2:
			for (int i = 0; i < n; i++) {
				if (listaBienesAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isInmueblesBuscadosCargados() == false) {

					if (listaBienesAux.get(i).getMunicipio()
							.equals(x.getBien())) {

						listaBusqueda.add(listaBienesAux.get(i));
					}
				} else if (!listaBienesAux.get(i).getMunicipio()
						.equals(x.getBien())) {
					listaBusqueda.remove(listaBienesAux.get(i));
					i -= 1;
					n = listaBusqueda.size();
				}

			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setInmueblesBuscadosCargados(true);
				buscarBienesInmuebles(x.getLiga(), listaBusqueda);
			} else {
				ComunicadorBusqueda
						.setListaBienesInmobiliariosBuscados(listaBusqueda);
				Busqueda.showResults();
			}
			break;
		case 3:
			for (int i = 0; i < n; i++) {
				if (listaBienesAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isInmueblesBuscadosCargados() == false) {

					if (listaBienesAux.get(i).getTipodebien()
							.equals(x.getBien())) {

						listaBusqueda.add(listaBienesAux.get(i));
					}
				} else if (!listaBienesAux.get(i).getTipodebien()
						.equals(x.getBien())) {
					listaBusqueda.remove(listaBienesAux.get(i));
					i -= 1;
					n = listaBusqueda.size();
				}

			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setInmueblesBuscadosCargados(true);
				buscarBienesInmuebles(x.getLiga(), listaBusqueda);
			} else {
				ComunicadorBusqueda
						.setListaBienesInmobiliariosBuscados(listaBusqueda);
				Busqueda.showResults();
			}
			break;
		case 4:
			for (int i = 0; i < n; i++) {
				if (listaBienesAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isInmueblesBuscadosCargados() == false) {

					if (listaBienesAux.get(i).getTipo().equals(x.getBien())) {

						listaBusqueda.add(listaBienesAux.get(i));
					}
				} else if (!listaBienesAux.get(i).getTipo().equals(x.getBien())) {
					listaBusqueda.remove(listaBienesAux.get(i));
					i -= 1;
					n = listaBusqueda.size();
				}

			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setInmueblesBuscadosCargados(true);
				buscarBienesInmuebles(x.getLiga(), listaBusqueda);
			} else {
				ComunicadorBusqueda
						.setListaBienesInmobiliariosBuscados(listaBusqueda);
				Busqueda.showResults();
			}
			break;
		case 5:
			for (int i = 0; i < n; i++) {
				if (listaBienesAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isInmueblesBuscadosCargados() == false) {

					if (listaBienesAux.get(i).getUsodelbien()
							.equals(x.getBien())) {

						listaBusqueda.add(listaBienesAux.get(i));
					}
				} else if (!listaBienesAux.get(i).getUsodelbien()
						.equals(x.getBien())) {
					listaBusqueda.remove(listaBienesAux.get(i));
					i -= 1;
					n = listaBusqueda.size();
				}

			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setInmueblesBuscadosCargados(true);
				buscarBienesInmuebles(x.getLiga(), listaBusqueda);
			} else {
				ComunicadorBusqueda
						.setListaBienesInmobiliariosBuscados(listaBusqueda);
				Busqueda.showResults();
			}
			break;
		case 6:
			for (int i = 0; i < n; i++) {
				if (listaBienesAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isInmueblesBuscadosCargados() == false) {

					if (listaBienesAux.get(i).getNumBaño().equals(x.getBien())) {

						listaBusqueda.add(listaBienesAux.get(i));
					}
				} else if (!listaBienesAux.get(i).getNumBaño()
						.equals(x.getBien())) {
					listaBusqueda.remove(listaBienesAux.get(i));
					i -= 1;
					n = listaBusqueda.size();
				}

			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setInmueblesBuscadosCargados(true);
				buscarBienesInmuebles(x.getLiga(), listaBusqueda);
			} else {
				ComunicadorBusqueda
						.setListaBienesInmobiliariosBuscados(listaBusqueda);
				Busqueda.showResults();
			}
			break;
		case 7:
			for (int i = 0; i < n; i++) {
				if (listaBienesAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isInmueblesBuscadosCargados() == false) {

					if (listaBienesAux.get(i).getNumHabitacion()
							.equals(x.getBien())) {

						listaBusqueda.add(listaBienesAux.get(i));
					}
				} else if (!listaBienesAux.get(i).getNumHabitacion()
						.equals(x.getBien())) {
					listaBusqueda.remove(listaBienesAux.get(i));
					i -= 1;
					n = listaBusqueda.size();
				}

			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setInmueblesBuscadosCargados(true);
				buscarBienesInmuebles(x.getLiga(), listaBusqueda);
			} else {
				ComunicadorBusqueda
						.setListaBienesInmobiliariosBuscados(listaBusqueda);
				Busqueda.showResults();
			}
			break;
		case 8:
			for (int i = 0; i < n; i++) {
				if (listaBienesAux.get(i) == null)
					break;
				if (ComunicadorBusqueda.isInmueblesBuscadosCargados() == false) {

					if (listaBienesAux.get(i).getValor().equals(x.getBien())) {

						listaBusqueda.add(listaBienesAux.get(i));
					}
				} else if (!listaBienesAux.get(i).getValor()
						.equals(x.getBien())) {
					listaBusqueda.remove(listaBienesAux.get(i));
					i -= 1;
					n = listaBusqueda.size();
				}

			}
			if (x.getLiga() != null) {
				ComunicadorBusqueda.setInmueblesBuscadosCargados(true);
				buscarBienesInmuebles(x.getLiga(), listaBusqueda);
			} else {
				ComunicadorBusqueda
						.setListaBienesInmobiliariosBuscados(listaBusqueda);
				Busqueda.showResults();
			}
			break;
		default:
			break;
		}

	}

	public void searchEveryThing() {
		SingletonBienes bienes = SingletonBienes.getInstance();
		if (bienes.getListaBienesInmobiliarios() != null) {
			ComunicadorBusqueda.setListaBienesInmobiliariosBuscados(bienes
					.getListaBienesInmobiliarios());
			Busqueda.showResults();
		}
	}

}
