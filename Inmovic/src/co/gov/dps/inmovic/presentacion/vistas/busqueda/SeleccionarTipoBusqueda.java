package co.gov.dps.inmovic.presentacion.vistas.busqueda;

import co.gov.dps.inmovic.dominio.adaptadores.AdaptadorSeleccionar;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.controladores.ControlallerSeleccionarTipoBusqueda;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R.layout;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R.menu;
import co.gov.dps.inmovic.presentacion.vistas.destacados.DestacadoUI;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class SeleccionarTipoBusqueda extends ActionBarActivity {

	private ListView lsSeleccionar;
	private android.support.v7.app.ActionBar action;
	private ControlallerSeleccionarTipoBusqueda controladorSeleccionar;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seleccioninmueblesventas);
		ComunicadorGeneral.setActividad(this);
		action = ((SeleccionarTipoBusqueda) ComunicadorGeneral.getActividad())
				.getSupportActionBar();
		action.setTitle("Destacados");
		action.setDisplayHomeAsUpEnabled(true);
		controladorSeleccionar = new ControlallerSeleccionarTipoBusqueda();
		controladorSeleccionar.establerActividadEnComunicadorGeneral(this);
		controladorSeleccionar.setLLamadaDesdeDestacados();
		cargaElementoViewSeleccion();

	}

	public void cargaElementoViewSeleccion() {

		lsSeleccionar = (ListView) findViewById(R.id.listView1);
		AdaptadorSeleccionar adapter = new AdaptadorSeleccionar(this,
				new String[] { "Inmuebles", "Bienes en Venta" });
		lsSeleccionar.setAdapter(adapter);
		lsSeleccionar.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {

				if (position == 0) {
					Intent i = new Intent(controladorSeleccionar
							.devolverActividadEnComunicadorGeneral(),
							Busqueda.class);
					controladorSeleccionar
							.devolverActividadEnComunicadorGeneral()
							.startActivity(i);

				} else {

					Intent i = new Intent(controladorSeleccionar
							.devolverActividadEnComunicadorGeneral(),
							Busqueda2.class);
					controladorSeleccionar
							.devolverActividadEnComunicadorGeneral()
							.startActivity(i);

				}

				if (action != null) {
					action.setTitle("Seleccionar");

				}

			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seleccionar_tipo_busqueda, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
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

		return super.onKeyDown(keyCode, event);
	}

}
