package co.gov.dps.inmovic.presentacion.actividades.destacados;

import co.gov.dps.inmovic.presentacion.vistas.busqueda.SeleccionarTipoBusqueda;
import co.gov.dps.inmovic.presentacion.vistas.destacados.DestacadoUI;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Acercade extends ActionBarActivity {

	private android.support.v7.app.ActionBar action;
	Activity acti;
	int toques;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acerca_de_inmovic);
		action = getSupportActionBar();
		action.setDisplayHomeAsUpEnabled(true);
		action.setTitle("Destacados");
		acti= this;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.acercade, menu);
		return true;
	}

	/**
	 * Este metodo se encarga de seleccionar a que actividad vamos cuando el
	 * boton de atras o home es presionado
	 */
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			// se crea el intent adecuado para esta accion y se llama la
			// acitvidad
			Intent upIntent = new Intent(this, DestacadoUI.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				android.support.v4.app.TaskStackBuilder.from(this)
						.addNextIntent(upIntent).startActivities();
				finish();
			} else {
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	

}
