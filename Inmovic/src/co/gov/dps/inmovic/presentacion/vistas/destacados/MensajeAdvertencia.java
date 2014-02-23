package co.gov.dps.inmovic.presentacion.vistas.destacados;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;

public class MensajeAdvertencia {

	private String EULA_PREFIX = "eula_";
	private Activity mActivity;

	public MensajeAdvertencia(Activity context) {
		mActivity = context;
	}

	private PackageInfo getPackageInfo() {
		PackageInfo pi = null;
		try {
			pi = mActivity.getPackageManager().getPackageInfo(
					mActivity.getPackageName(),
					PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi;
	}

	public void show() {
		PackageInfo versionInfo = getPackageInfo();

		// the eulaKey changes every time you increment the version number
		// in the AndroidManifest.xml
		final String eulaKey = EULA_PREFIX + versionInfo.versionCode;
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(mActivity);
		boolean hasBeenShown = prefs.getBoolean(eulaKey, false);
		if (hasBeenShown == false) {

			// Show the Eula
			String title = mActivity.getString(R.string.app_name) + " v"
					+ versionInfo.versionName;

			// Includes the updates as well so users know what changed.
			String message = "ola";

			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
					.setTitle("Politica De Privacidad")
					.setIcon(R.drawable.icono)
					.setMessage(
							"Con esta aplicaci—n se quiere crear condiciones de prosperidad en la poblaci—n vulnerable, " +
							"contribuir a la reconciliaci—n de los Colombianos y promover la integraci—n regional." + "\n" + 
							"Todos los bienes aqui mostrados pertenecen al gobierno de la republica de Colombia, e integran " +
							"e integran a la Unidad para la Atenci—n y Reparaci—n Integral a las Víctimas.")
					.setPositiveButton("Comprendo",
							new Dialog.OnClickListener() {

								@Override
								public void onClick(
										DialogInterface dialogInterface,
										int i) {
									// Mark this version as read.
									SharedPreferences.Editor editor = prefs
											.edit();
									editor.putBoolean(eulaKey, true);
									editor.commit();
									dialogInterface.dismiss();
								}
							})
					.setNegativeButton("Salir",
							new Dialog.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// Close the activity as they have
									// declined the EULA
									mActivity.finish();
								}

							});
			builder.create().show();
		}
	}

}
