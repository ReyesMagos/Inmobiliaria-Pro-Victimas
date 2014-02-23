package co.gov.dps.inmovic.presentacion.vistas.destacados;

import java.util.List;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import co.gov.dps.inmovic.dominio.adaptadores.AdaptadorDestacados;
import co.gov.dps.inmovic.dominio.controladores.ControllerDestacados;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.dominio.entidades.Destacado;
import co.gov.dps.inmovic.dominio.singletonentidades.SingletonBienes;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.SeleccionarTipoBusqueda;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.UpdateReceive;
import co.gov.dps.inmovic.servicio.servicioweb.RedSocial;

/**
 * @author Alexis
 * 
 */
public class DestacadoUI extends ActionBarActivity {

	private TextView tipo;
	private TextView nombre;
	private TextView canon;
	private TextView ubicacion;
	private static ListView lsDestacados;
	public Destacado destacado;
	static Destacado d;
	static ControllerDestacados controlador;
	static Destacado[] bienesDestacados;

	private Destacado[] destacados;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destacado);

		new MensajeAdvertencia(this).show();
		ComunicadorGeneral.setActividad(this);
		lsDestacados = (ListView) findViewById(R.id.lsDestacados);
		controlador = new ControllerDestacados();
		controlador = new ControllerDestacados();
		controlador.ordenaCargaDestacados();

		nombre = (TextView) findViewById(R.id.nombre);

	}

	public static void llenaListViewDestacado(Destacado[] arreglo) {

		bienesDestacados = arreglo;

		AdaptadorDestacados adaptador = new AdaptadorDestacados(
				ComunicadorGeneral.getActividad(), bienesDestacados);

		lsDestacados.setAdapter(adaptador);
		lsDestacados.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				// TODO Auto-generated method stub
				d = (Destacado) arg0.getAdapter().getItem(arg2);
				ComunicadorGeneral.getActividad().registerForContextMenu(
						ComunicadorGeneral.getActividad().getWindow()
								.getDecorView()
								.findViewById(android.R.id.content));

				return false;
			}

		});

	}

	public static void imagenesInicialesListaDestacados() {
		SingletonBienes bienes = SingletonBienes.getInstance();
		List<BienInmobiliario> listaBienesInmobiliarios = bienes
				.getListaBienesInmobiliarios();

		for (int i = 0; i < 5; i++) {
			BienInmobiliario j = listaBienesInmobiliarios
					.get(ComunicadorGeneral.getVisitados()[i]);
			bienesDestacados[i].setImagen(j.getImagenInicial());

		}
		AdaptadorDestacados adaptador = new AdaptadorDestacados(
				ComunicadorGeneral.getActividad(), bienesDestacados);
		lsDestacados.setAdapter(adaptador);
		lsDestacados.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				d = (Destacado) arg0.getAdapter().getItem(arg2);

			}

		});

	}

	/**
	 * Se crea el menu contextual que se le mostrara al usuario al hacer un
	 * click prolongado en la lista novedades
	 * 
	 * @return
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.item1:
			if (d != null) {
				RedSocial compartir = new RedSocial();
				compartir.compartirRedSocial(this,
						"Este bien: " + d.getNombre()
								+ " esta disponilbe para arrendar. \n"
								+ "Se encuentra ubicado en: " + d.getUbicacion()
								+ "@UnidadVictimas ");
			}
			return true;
		case R.id.item2:
			if (d != null) {
				Formulario.tipoBienSeleccionado = 0;
				controlador.llamarDesdeDestacados(true);
				controlador.MostrarInfoInmuebleDestacado(d);

			}
			return true;
		default:
			return super.onContextItemSelected(item);
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_contextual, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_search:

			controlador.cambiaABusqueda();

			return true;

		case R.id.acercade:

			controlador.cambiarAACercaDe();
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.destacado, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void btnBuscarClick(View view) {
		Intent one = new Intent(this, SeleccionarTipoBusqueda.class);
		startActivity(one);
	}

	public static void mensajeDeAlerta(String mensaje, String titulo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ComunicadorGeneral.getActividad());
		builder.setTitle(titulo);
		builder.setIcon(R.drawable.icono);
		builder.setMessage(mensaje).setPositiveButton(R.string.btn_aceptar,
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

	

	// ================================================================================
	// Metodos para cuando se vuewlve a la aplicacion
	// ================================================================================
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		controlador.establerActividadEnComunicadorGeneral(this);
		controlador.llamarDesdeDestacados(false);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		controlador.establerActividadEnComunicadorGeneral(this);
		controlador.llamarDesdeDestacados(false);
	}

	// ********************************************************************************

}
