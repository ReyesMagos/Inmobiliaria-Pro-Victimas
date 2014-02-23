package co.gov.dps.inmovic.servicio.servicioweb;

import java.net.URL;

import co.gov.dps.inmovic.dominio.controladores.ControllerDestacados;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.webkit.URLUtil;
import android.widget.TextView;

public class ServicioImagenes extends AsyncTask<String, Integer, Boolean> {

	private String urls[];
	private Bitmap[] bmp;
	private int identificador;
	int escala;
	boolean error;
	private ProgressDialog progressDialog;

	public ServicioImagenes(String[] s, int x) {

		urls = s;
		identificador = x;
		if (x == 1) {
			escala = 4;
		} else {
			escala = 2;
		}

		progressDialog = new ProgressDialog(ComunicadorGeneral.getActividad());
		progressDialog
				.setMessage("La Carga de imagenes puede tardar, toque la pantalla para continuar navegando.");

	}

	@Override
	protected void onPreExecute() {

		progressDialog.show();

	}

	@SuppressWarnings("finally")
	public boolean verificaUrl(String u) {
		URLUtil uu = new URLUtil();
		if (uu.isValidUrl(u)) {
			return true;
		}
		return false;

	}

	protected Boolean doInBackground(String... params) {

		boolean resul = true;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = escala;
		URL url7;
		Bitmap bitmapAux;

		try {
			int cont = 0;
			int i = 0;
			bmp = new Bitmap[urls.length];
			for (String s : urls) {
				if (URLUtil.isValidUrl(s)) {
					url7 = new URL(s);

					// Bitmap largeIcon =
					// BitmapFactory.decodeResource(comunicador.getActividad().getResources(),
					// R.drawable.icono_contacto);
					try {
						bitmapAux = BitmapFactory.decodeStream(url7
								.openConnection().getInputStream(), null,
								options);
						bmp[i] = bitmapAux;
					} catch (Exception e) {
						cont += 1;
						Bitmap largeIcon = BitmapFactory
								.decodeResource(ComunicadorGeneral
										.getActividad().getResources(),
										R.drawable.logo_unidad);
						bitmapAux = largeIcon;

						bmp[i] = bitmapAux;
					}
					i += 1;
				} else {
					cont += 1;
					Bitmap largeIcon = BitmapFactory.decodeResource(
							ComunicadorGeneral.getActividad().getResources(),
							R.drawable.logo_unidad);
					bitmapAux = largeIcon;

					bmp[i] = bitmapAux;
					i += 1;

				}
			}
			if (cont >= bmp.length) {
				resul = false;

			}

		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
			resul = false;
		}
		return resul;
	}

	protected void onPostExecute(Boolean result) {
		if (result) {

			if (identificador == 1) {

				ControllerDestacados cD = new ControllerDestacados();
				cD.llevaImagenesALista(bmp);
			} else if (identificador == 2) {
				Resultados.cargaImagenes(bmp);
				// Resultados.mensajeDeAlerta("Cargue LAs Imagenes",
				// "Imagenes");
			}

		} else {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					ComunicadorGeneral.getActividad());
			builder.setTitle("Error");
			builder.setIcon(R.drawable.icono);
			if (urls != null) {

			}
			if (searchForEmptyUrlArray() == 1) {
				builder.setMessage("Por el momento este inmueble no cuenta con imagenes");
			} else if (searchForEmptyUrlArray() == 2) {
				builder.setMessage("Imagen no disponible");

			} else {
				builder.setMessage("Lo sentimos ocurrio un problema al cargar las imagenes, revise su conexion a internet e intente de nuevo");
			}

			builder.setPositiveButton(R.string.btn_aceptar,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

						}
					});

			AlertDialog dialog = builder.show();
			TextView messageText = (TextView) dialog
					.findViewById(android.R.id.message);
			messageText.setGravity(Gravity.CENTER);
			dialog.show();

		}

		progressDialog.dismiss();

	}

	public int searchForEmptyUrlArray() {
		for (String s : urls) {
			if (s == null || s.equals("Sin informaci—n") || s.equals("")) {
				return 1;
			} else if (!URLUtil.isValidUrl(s)) {
				return 2;
			}
		}
		return 0;

	}

	public Bitmap[] getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap[] bmp) {
		this.bmp = bmp;
	}

}
