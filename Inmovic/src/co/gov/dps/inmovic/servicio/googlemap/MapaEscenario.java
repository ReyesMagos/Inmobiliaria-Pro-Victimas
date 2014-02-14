package co.gov.dps.inmovic.servicio.googlemap;

import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
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

	public void iniciarMapa(GoogleMap mapa, LatLng latlong, int fragmento) {
		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(fragmento)).getMap();

		CameraPosition posicion = new CameraPosition.Builder().target(latlong)
				.zoom(5).bearing(0).build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(posicion);
		mapa.animateCamera(camUpd);

		// 10.428391,-74.361763
		// 10.087854,-74.559517
		// 10.131117,-74.218941
		// 10.45,-74.383736
		// 10.00131,-74.510078

	}

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
		Marker n = mapa
				.addMarker(new MarkerOptions()
						.position(new LatLng(lat, lng))
						.title(inmo.getNombredelbien() + " ")
						.snippet(
								inmo.getDepartamento() + "-"
										+ inmo.getMunicipio()));
		n.showInfoWindow();
		// se muestra la venta de info

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
