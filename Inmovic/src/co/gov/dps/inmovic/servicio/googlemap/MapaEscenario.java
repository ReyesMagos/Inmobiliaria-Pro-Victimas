package co.gov.dps.inmovic.servicio.googlemap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerConfigurationException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.style.IconMarginSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.TextView;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.controladores.ControllerMapa;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.presentacion.actividades.destacados.Maps;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.SeleccionarTipoBusqueda;
import co.gov.dps.inmovic.presentacion.vistas.destacados.DestacadoUI;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaEscenario extends android.support.v4.app.FragmentActivity {

	static double[] latant, latmag, latvicha;
	static double[] latmet;
	static double[] lngant, lngmet, lngmag, lngvicha;
	private static int met = 0;
	private static int ant = 0;
	private static int mag = 0;
	private static int vich = 0;
	private static List<BienInmobiliario> lB;

	public void iniciarMapa(GoogleMap mapa, LatLng latlong, int fragmento) {
		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(fragmento)).getMap();

		CameraPosition posicion = new CameraPosition.Builder().target(latlong)
				.zoom(5).bearing(0).build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(posicion);
		mapa.animateCamera(camUpd);

	}

	// 10.428391,-74.361763
	// 10.087854,-74.559517
	// 10.131117,-74.218941
	// 10.45,-74.383736
	// 10.00131,-74.510078

	public static void ubicarEscenario(GoogleMap mapa, BienInmobiliario inmo,
			int size) {
		double x3[] = devuelveLat(inmo.getCoordenadas());
		double lat = x3[0];
		double lng = x3[1];

		LatLng latlong = new LatLng(lat, lng);
		// mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraPosition posicion = new CameraPosition.Builder().target(latlong)
				.zoom(size).bearing(0).build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(posicion);
		mapa.animateCamera(camUpd);

		Marker n = mapa.addMarker(new MarkerOptions()
				.position(new LatLng(lat, lng))
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED))
				.title(inmo.getNombredelbien())
				.snippet(inmo.getDepartamento() + "-" + inmo.getMunicipio()));

		// n.setIcon(BitmapDescriptorFactory.defaultMarker())

		n.showInfoWindow();

		mapa.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub

			}
		});

		// se muestra la venta de info

	}

	public static void ubicarEscenarios(GoogleMap mapa, BienInmobiliario inmo,
			int size) {
		double x3[] = devuelveLat(inmo.getCoordenadas());
		double lat = x3[0];
		double lng = x3[1];
		if (lB == null) {
			lB = new ArrayList<BienInmobiliario>();
		}
		LatLng latlong = new LatLng(lat, lng);
		// mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraPosition posicion = new CameraPosition.Builder().target(latlong)
				.zoom(size).bearing(0).build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(posicion);
		mapa.animateCamera(camUpd);

		Marker n = mapa.addMarker(new MarkerOptions()
				.position(new LatLng(lat, lng))
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED))
				.title(inmo.getNombredelbien())
				.snippet(inmo.getDepartamento() + "-" + inmo.getMunicipio()));

		// n.setIcon(BitmapDescriptorFactory.defaultMarker())
		lB.add(inmo);
		n.showInfoWindow();

		mapa.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub

				ControllerMapa.setbienAmostrar(findInmueble(marker.getTitle()));
				ControllerMapa.mapear(true);
				Intent i = new Intent(ControllerMapa.getActividad(),
						Resultados.class);
				ControllerMapa.getActividad().startActivity(i);
			}
		});
	}

	public static BienInmobiliario findInmueble(String nombre) {
		for (BienInmobiliario b : lB) {
			if (b.getNombredelbien().equals(nombre)) {
				return b;
			}
		}

		return lB.get(0);
	}

	public static void showMessage(BienInmobiliario bien) {
		/*
		 * / AlertDialog.Builder builder = new AlertDialog.Builder(
		 * ControllerMapa.getActividad());
		 * 
		 * builder.setIcon(R.drawable.icono);
		 * 
		 * LayoutInflater factory = LayoutInflater.from(ControllerMapa
		 * .getActividad()); final View view = factory.inflate(
		 * R.layout.contenido_info_inmueble_mapa, null);
		 * 
		 * TextView txtNombre = (TextView) view
		 * .findViewById(R.id.txtNombreBienMapa);
		 * txtNombre.setText(bien.getNombredelbien());
		 * 
		 * TextView txtDepartamento = (TextView) view
		 * .findViewById(R.id.txtDepartamentoBienMapa);
		 * txtDepartamento.setText(bien.getDepartamento());
		 * 
		 * TextView txtMunicipio = (TextView) view
		 * .findViewById(R.id.txtMunicipioBienMapa);
		 * txtMunicipio.setText(bien.getDepartamento());
		 * 
		 * TextView txtBanos = (TextView) view
		 * .findViewById(R.id.txtMBanosBienMapa);
		 * txtBanos.setText(bien.getNumBaño());
		 * 
		 * TextView txtHabitaciones = (TextView) view
		 * .findViewById(R.id.txtHabitacionesBienMapa);
		 * txtHabitaciones.setText(bien.getNumHabitacion());
		 * 
		 * TextView txtValorArriendo = (TextView) view
		 * .findViewById(R.id.txtValorArriendoBienMapa);
		 * txtValorArriendo.setText(bien.getValor());
		 * 
		 * TextView txtContacto = (TextView) view
		 * .findViewById(R.id.txtContactoBienMapa);
		 * txtContacto.setText(bien.getContacto());
		 * 
		 * TextView txtFolio = (TextView)
		 * view.findViewById(R.id.txtFolioBienMapa);
		 * txtFolio.setText(bien.getFoliodematriculainmobiliaria());
		 * 
		 * TextView txtArea = (TextView)
		 * view.findViewById(R.id.txtAreaBienMapa);
		 * txtArea.setText(bien.getAreacontruidamts2());
		 * builder.setTitle("Descripcion Inmueble"); builder.setView(view);
		 * 
		 * builder.setPositiveButton(R.string.btn_aceptar, new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int id) {
		 * 
		 * } });
		 * 
		 * AlertDialog dialog = builder.show();
		 * 
		 * dialog.show(); /
		 */
	}

	public static void limpiaMapa(GoogleMap mapa) {
		mapa.clear();
	}

	public void asignainfoWindow(GoogleMap mapa) {

	}

	public static double[] devuelveLat(String s) {
		double[] x1 = new double[2];
		s = s.replace(" ", "");
		String s1, s2;
		for (int i = 0; i < s.length(); i++) {
			if (Character.toString(s.charAt(i)).equals(";")) {
				s1 = s.substring(0, i);
				s2 = s.substring(i + 1, s.length());
				x1[0] = Double.parseDouble(s1);
				x1[1] = Double.parseDouble(s2);
				return x1;

			}
		}
		x1[0] = 4.75678;
		x1[1] = 78999;
		return x1;

	}
}
