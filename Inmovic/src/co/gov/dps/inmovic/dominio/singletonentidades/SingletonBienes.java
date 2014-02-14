package co.gov.dps.inmovic.dominio.singletonentidades;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.controladores.ControllerBusqueda;
import co.gov.dps.inmovic.dominio.entidades.Bien;
import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.servicio.servicioweb.ServicioImagenes;

public class SingletonBienes {

	private static SingletonBienes instance;
	private static List<BienInmobiliario> listaBienesInmobiliarios;
	private static List<BienALaVenta> listaBienesALaVenta;

	public static List<BienALaVenta> getListaBienesALaVenta() {
		return listaBienesALaVenta;
	}

	public static void setListaBienesALaVenta(
			List<BienALaVenta> listaBienesALaVenta) {
		SingletonBienes.listaBienesALaVenta = listaBienesALaVenta;
	}

	private SingletonBienes() {

	}

	public static SingletonBienes getInstance() {
		if (instance == null) {
			instance = new SingletonBienes();
		}

		return instance;
	}

	public static List<BienInmobiliario> getListaBienesInmobiliarios() {
		return listaBienesInmobiliarios;
	}

	private static void setListaBienesInmobiliarios(
			List<BienInmobiliario> listaBienesInmobiliarios) {
		SingletonBienes.listaBienesInmobiliarios = listaBienesInmobiliarios;
	}

	public void llenaInmboli(JSONArray arregloJson) {
		JSONObject obj;
		listaBienesInmobiliarios = new ArrayList<BienInmobiliario>();
		String[] urls = new String[7];
		try {
			for (int i = 0; i < arregloJson.length(); i++) {
				obj = arregloJson.getJSONObject(i);
				BienInmobiliario inmb = new BienInmobiliario();
				inmb.setRowKey(verificaInformacionFaltante(obj
						.getString("RowKey")));
				inmb.setPartitionKey(verificaInformacionFaltante(obj
						.getString("PartitionKey")));
				inmb.setDepartamento(verificaInformacionFaltante(obj
						.getString("departamento")));
				inmb.setMunicipio(verificaInformacionFaltante(obj
						.getString("municipio")));
				inmb.setTipo(verificaInformacionFaltante(obj
						.getString("tipodeinmueble")));
				inmb.setTipoDeBien(verificaInformacionFaltante(obj
						.getString("tipodebien")));
				inmb.setUsodelbien(verificaInformacionFaltante(obj
						.getString("usodelbien")));
				inmb.setNombredelbien(verificaInformacionFaltante(obj
						.getString("nombredelbien")));
				inmb.setFoliodematriculainmobiliaria(verificaInformacionFaltante(obj
						.getString("foliodematriculainmobiliaria")));
				inmb.setAreadelterreno(verificaInformacionFaltante(obj
						.getString("areadelterreno")));
				inmb.setAreacontruidamts2(verificaInformacionFaltante(obj
						.getString("areaconstruida")));
				inmb.setDescripcion(verificaInformacionFaltante(obj
						.getString("descripcion")));
				inmb.setNumBaño(verificaInformacionFaltante(obj
						.getString("numerodebanos")));
				inmb.setNumHabitacion(verificaInformacionFaltante(obj
						.getString("numerodehabitaciones")));
				inmb.setCoordenadas((verificaInformacionFaltante(obj
						.getString("coordenadas"))));
				inmb.setContacto(verificaInformacionFaltante(obj
						.getString("contacto")));
				inmb.setPuntuaciondelinmueble(verificaInformacionFaltante(obj
						.getString("puntuaciondelinmueble")));
				inmb.setValor(verificaInformacionFaltante(obj
						.getString("canondearrendamiento")));
				urls = new String[7];
				urls[0] = verificaInformacionFaltante(obj.getString("imagen1"));
				urls[1] = verificaInformacionFaltante(obj.getString("imagen3"));
				urls[2] = verificaInformacionFaltante(obj.getString("imagen2"));
				urls[3] = verificaInformacionFaltante(obj.getString("imagen4"));
				urls[4] = verificaInformacionFaltante(obj.getString("imagen5"));
				urls[5] = verificaInformacionFaltante(obj.getString("imagen6"));
				urls[6] = verificaInformacionFaltante(obj.getString("imagen7"));
				inmb.setUrlImagenes(urls);
				listaBienesInmobiliarios.add(inmb);
			}
		} catch (JSONException e) {

		}

	}

	public void llenaBienesAlaVenta(JSONArray arregloJson) {
		JSONObject obj;

		listaBienesALaVenta = new ArrayList<BienALaVenta>();
		String[] urls = new String[7];
		try {
			for (int i = 0; i < arregloJson.length(); i++) {
				obj = arregloJson.getJSONObject(i);
				BienALaVenta bienventa = new BienALaVenta();
				bienventa.setRowKey(verificaInformacionFaltante(obj
						.getString("RowKey")));
				bienventa.setPartitionKey(verificaInformacionFaltante(obj
						.getString("PartitionKey")));
				bienventa.setTipo(verificaInformacionFaltante(obj
						.getString("tipodebien")));
				bienventa.setDescripcion(verificaInformacionFaltante(obj
						.getString("descripcion")));
				bienventa.setUbicacion(verificaInformacionFaltante(obj
						.getString("ubicacion")));
				bienventa
						.setInformaciondecontacto(verificaInformacionFaltante(obj
								.getString("informaciondecontacto")));
				bienventa.setValor(verificaInformacionFaltante(obj
						.getString("valordeventa")));

				urls = new String[7];
				urls[0] = verificaInformacionFaltante(obj.getString("foto1"));
				urls[1] = verificaInformacionFaltante(obj.getString("foto2"));
				urls[2] = verificaInformacionFaltante(obj.getString("foto3"));
				urls[3] = verificaInformacionFaltante(obj.getString("foto4"));
				urls[4] = verificaInformacionFaltante(obj.getString("foto5"));
				urls[5] = verificaInformacionFaltante(obj.getString("foto6"));
				urls[6] = verificaInformacionFaltante(obj.getString("foto7"));
				bienventa.setUrlImagenes(urls);
				listaBienesALaVenta.add(bienventa);
			}
		} catch (JSONException e) {

		}

	}

	private String verificaInformacionFaltante(String s) {
		if (s == null || s.equals("")) {
			return "Sin Informaci—n";
		}
		return s;
	}

}
