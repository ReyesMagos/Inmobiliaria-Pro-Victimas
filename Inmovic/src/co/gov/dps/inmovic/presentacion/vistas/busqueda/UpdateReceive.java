package co.gov.dps.inmovic.presentacion.vistas.busqueda;

import co.gov.dps.inmovic.servicio.servicioweb.ServicioRest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class UpdateReceive extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isConnected = activeNetInfo != null
				&& activeNetInfo.isConnectedOrConnecting();
		if (isConnected) {
			Log.i("NET", "connecte" + isConnected);
			ServicioRest s = new ServicioRest(
					"bienesalaventauariv?$format=json", "oculto2");
			s.execute();
		}

		else
			Log.i("NET", "not connecte" + isConnected);
	}
}
