package co.gov.dps.inmovic.presentacion.vistas.destacados;

import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;
import co.gov.dps.inmovic.servicio.servicioweb.RedSocial;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class Destacados_main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destacados_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.destacado, menu);
		return true;
	}
	
	


}
