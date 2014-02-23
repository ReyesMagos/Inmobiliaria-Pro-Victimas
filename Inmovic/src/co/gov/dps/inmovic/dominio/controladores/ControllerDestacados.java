package co.gov.dps.inmovic.dominio.controladores;

import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import co.gov.dps.inmovic.dominio.adaptadores.Storage;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.dominio.entidades.Destacado;
import co.gov.dps.inmovic.dominio.singletonentidades.SingletonBienes;
import co.gov.dps.inmovic.presentacion.actividades.destacados.Acercade;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.SeleccionarTipoBusqueda;
import co.gov.dps.inmovic.presentacion.vistas.destacados.DestacadoUI;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;
import co.gov.dps.inmovic.servicio.servicioweb.ServicioImagenes;
import co.gov.dps.inmovic.servicio.servicioweb.ServicioRest;

public class ControllerDestacados {

	int[] visitados;
	static ServicioImagenes sI;
	int opcionpulsada;

	public int getOpcionpulsada() {
		return opcionpulsada;
	}

	public void setOpcionpulsada(int opcionpulsada) {
		this.opcionpulsada = opcionpulsada;
	}

	/**
	 * Metodo con el cual el controlador gestiona la carga de la informacion de
	 * los inmuebles que se utilizaran en la aplicacion. LLama el servicio web y
	 * le pide que valla por el JSON y lo devuelva o Bien si ya existe en
	 * memoria un archivo se carga este
	 */
	public void ordenaCargaDestacados() {
		// se declara un String y se lee el almacenamiento interno en busqueda
		// de archivos con la informacion de los inmuebles

		if (ComunicadorGeneral.Visitados != null) {
			devuelveBienesDestacados();
		} else {
			String r = Storage.readJSON(1);

			// si el String es nulo esto quiere decir que no hay archivos, por
			// lo
			// cual se hace la carga del servicio web
			if (r == null) {
				ServicioRest service = new ServicioRest(
						"inmueblesenarriendouariv?$format=json", "destacado");
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
					SingletonBienes singletonBienes = SingletonBienes
							.getInstance();

					// se mandan a llenar los archivos inmuebles con el arreglo
					singletonBienes.llenaInmboli(arregloJson);
					// singletonBienes.cargaImagenesDestacados();

					// se llama el metodo que devolvera a la vista los bienes
					// para
					// mostrar como destacados
					devuelveBienesDestacados();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public Destacado[] cargaDestacadosConVisitados() {
		// se lee la instancia de bienes inmobiliarios que hallan
		SingletonBienes bienes = SingletonBienes.getInstance();

		// se cargan los bienes de esta
		List<BienInmobiliario> listaBienesInmobiliarios = bienes
				.getListaBienesInmobiliarios();
		Destacado[] destacados = new Destacado[5];
		BienInmobiliario j;
		Destacado d;
		for (int i = 0; i < 5; i++) {
			j = listaBienesInmobiliarios
					.get(ComunicadorGeneral.getVisitados()[i]);
			// se carga con su nombre, su valor, su imagen un objeto de tipo
			// destacado
			d = new Destacado(compruebaInformacionFaltante(j.getTipo()),
					compruebaInformacionFaltante(j.getNombredelbien()),
					compruebaInformacionFaltante(j.getValor()),
					compruebaInformacionFaltante(j.getDepartamento()) + "-"
							+ compruebaInformacionFaltante(j.getMunicipio()),
					j.getImagenInicial());
			// se agrega al arreglo de destacados
			destacados[i] = d;
		}
		return destacados;
	}

	public Destacado[] cargaDestacadosAleatoriamente() {
		visitados = new int[5];

		for (int i = 0; i < 5; i++) {
			visitados[i] = -1;
		}
		// se lee la instancia de bienes inmobiliarios que hallan
		SingletonBienes bienes = SingletonBienes.getInstance();

		// se cargan los bienes de esta
		List<BienInmobiliario> listaBienesInmobiliarios = bienes
				.getListaBienesInmobiliarios();

		// se declara un objeto Destacado de tama–o 5 ya que son 5 los bienes
		// que se muestran
		Destacado[] destacados = new Destacado[5];
		Random aleatorio = new Random();
		int numeroAleatorio;
		int limiteAleatorio = listaBienesInmobiliarios.size();
		Destacado d;
		BienInmobiliario j;
		for (int i = 0; i < 5; i++) {
			// se lee un inmueble

			numeroAleatorio = aleatorio.nextInt(limiteAleatorio);

			while (!verificaAleatorioEnVisitados(numeroAleatorio)) {
				numeroAleatorio = aleatorio.nextInt(limiteAleatorio);
			}
			j = listaBienesInmobiliarios.get(numeroAleatorio);
			// se carga con su nombre, su valor, su imagen un objeto de tipo
			// destacado
			d = new Destacado(compruebaInformacionFaltante(j.getTipo()),
					compruebaInformacionFaltante(j.getNombredelbien()),
					compruebaInformacionFaltante(j.getValor()),
					compruebaInformacionFaltante(j.getDepartamento()) + "-"
							+ compruebaInformacionFaltante(j.getMunicipio()),
					j.getImagenInicial());
			// se agrega al arreglo de destacados
			destacados[i] = d;

		}

		return destacados;

	}

	public void MostrarInfoInmuebleDestacado(Destacado d) {
		SingletonBienes bien = SingletonBienes.getInstance();
		int[] v = ComunicadorGeneral.getVisitados();
		List<BienInmobiliario> lB = bien.getListaBienesInmobiliarios();

		if (v == null)
			return;
		for (int i = 0; i < v.length; i++) {
			BienInmobiliario j = lB.get(v[i]);
			if (j.getNombredelbien().equals(d.getNombre())
					&& j.getTipo().equals(d.getTipo())) {

				ComunicadorGeneral.setBienAMostrar(j);
				Intent h = new Intent(ComunicadorGeneral.getActividad(),
						Resultados.class);
				ComunicadorBusqueda.setOpcionResultados(1);
				ComunicadorGeneral.getActividad().startActivity(h);
				break;
			}
		}
	}

	/**
	 * Metodo que verifica si el numero aleatorio generado ya habia sido
	 * generado o no.
	 * 
	 * @param aleatorio
	 *            numero aleatorio generado
	 * @return falso de existir el numero o ya haber sido generado o verdadero
	 *         sino existe
	 */
	public boolean verificaAleatorioEnVisitados(int aleatorio) {
		// si el vector no ha sido inicializado se retorna
		if (visitados == null)
			return false;

		// si el primer elemento del vector es el -1 quiere decir que no ha sido
		// generado sino el numero recibido por lo que guardamos este y
		// retornamos
		if (visitados[0] == -1) {
			visitados[0] = aleatorio;
			return true;
		}
		int posicion;
		int m = 0;

		// se recorre el vector
		for (int i : visitados) {
			// si el dato en visitados[i] es igual al numero generado se retorna
			// falso
			if (i == aleatorio) {
				return false;
			}
			if (i == -1) { // si es igual a -1 salimos ya que no ha sido
							// generado
				posicion = m;
				break;
			}
			m += 1;
		}
		// se guarda en la posicion
		visitados[m] = aleatorio;

		return true;

	}

	/**
	 * Metodo que se encarga de mandarle a la vista los bienes que se muestran
	 * como destacados
	 */
	public void devuelveBienesDestacados() {

		Destacado[] destacados;
		if (ComunicadorGeneral.getVisitados() == null) {
			ComunicadorGeneral.setHaydatosminimos(true);
			destacados = cargaDestacadosAleatoriamente();
			ComunicadorGeneral.setVisitados(visitados);

			// se llama al metodo para gestionar la carga de las imagenes de los
			// bienes destacados
		} else {
			destacados = cargaDestacadosConVisitados();
		}
		// se manda a la vista los destacados para ser mostrados
		DestacadoUI.llenaListViewDestacado(destacados);
		cargaImagenesIniciales();
	}

	/**
	 * Metodo que se encarga de gestionar la carga de las imagenes; en este
	 * metodo se comprueba mandara a comprobar si existen las imagenes de los
	 * bienes a mostrar como destacados en la memoria; de no existir se gestiona
	 * su carga
	 */
	public void cargaImagenesIniciales() {
		if (sI != null) {
			return;
		}
		SingletonBienes bienes = SingletonBienes.getInstance();
		List<BienInmobiliario> listaBienesInmobiliarios = bienes
				.getListaBienesInmobiliarios();
		BienInmobiliario j;
		String[] URL = new String[5];

		for (int i = 0; i < 5; i++) {
			j = listaBienesInmobiliarios
					.get(ComunicadorGeneral.getVisitados()[i]);
			URL[i] = j.getUrlImagenes()[0];

		}
		sI = new ServicioImagenes(URL, 1);
		sI.execute();
	}

	public void llevaImagenesALista(Bitmap[] b) {
		SingletonBienes bienes = SingletonBienes.getInstance();
		List<BienInmobiliario> listaBienesInmobiliarios = bienes
				.getListaBienesInmobiliarios();
		BienInmobiliario j;

		for (int i = 0; i < 5; i++) {
			j = listaBienesInmobiliarios
					.get(ComunicadorGeneral.getVisitados()[i]);
			j.setImagenInicial(b[i]);
			listaBienesInmobiliarios.set(ComunicadorGeneral.getVisitados()[i],
					j);
		}

		DestacadoUI.imagenesInicialesListaDestacados();
	}

	public String compruebaInformacionFaltante(String s) {
		if (s == null) {
			return "Sin Información";
		} else if (s.length() <= 1) {
			return "Sin Información";
		}
		return s;
	}

	public void cambiarAACercaDe() {
		Intent i = new Intent(ComunicadorGeneral.getActividad(), Acercade.class);
		ComunicadorGeneral.getActividad().startActivity(i);
	}

	public void cambiaABusqueda() {
		if (ComunicadorGeneral.isHaydatosminimos()) {
			if (sI != null) {
				sI.cancel(true);

			}

			Intent i = new Intent(ComunicadorGeneral.getActividad(),
					SeleccionarTipoBusqueda.class);
			ComunicadorGeneral.getActividad().startActivity(i);
		} else {
			DestacadoUI
					.mensajeDeAlerta(
							"La Aplicaci—n no tiene los datos minimos para realizar esta acci—n. Debe estar conectado a internet al menos en una ejecuci—n",
							"Falta de Datos");
		}
	}

	public void establerActividadEnComunicadorGeneral(Activity actividad) {
		ComunicadorGeneral.setActividad(actividad);
	}

	public Activity devolverActividadEnComunicadorGeneral() {
		return ComunicadorGeneral.getActividad();
	}

	public boolean isLLamadoDesdeDestacados() {
		return ComunicadorGeneral.isFromDestacados();
	}

	public void llamarDesdeDestacados(Boolean llamado) {
		ComunicadorGeneral.setFromDestacados(true);
	}

}
