package co.gov.dps.inmovic.presentacion.vistas.busqueda;

import java.util.List;

import co.gov.dps.inmovic.dominio.controladores.ComunicadorBusqueda;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.controladores.ControllerBusqueda2;
import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Busqueda2 extends ActionBarActivity {

	// ================================================================================
	// Definici—n varible y controles
	// ================================================================================
	private static Spinner spUbicacion;
	private static Spinner spTipoBien2;
	private static Spinner spValor2;
	private static String[] arregloUbicacion;
	private static String[] arregloTipoDeBien;
	private static String[] arregloValor2;
	private static ArrayAdapter<String> adaptadorTipoBien2;
	private static ArrayAdapter<String> adaptadorUbicacion;
	private static ArrayAdapter<String> adaptadorValor2;
	private static ControllerBusqueda2 controladorBusqueda;
	private android.support.v7.app.ActionBar action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		controladorBusqueda = new ControllerBusqueda2();
		controladorBusqueda.setBusqueda2Activity(this);
		setContentView(R.layout.activity_busqueda2);
		action = ((Busqueda2) controladorBusqueda
				.devolverActividadEnComunicadorGeneral()).getSupportActionBar();
		action.setTitle("Selecciona");
		action.setDisplayHomeAsUpEnabled(true);

		loadLayoutControls();
		/*
		 * / if (controladorBusqueda.getLlamadas2() == 1) { if
		 * (controladorBusqueda.verificarOpcionesBusquedaSeleccionadas() !=
		 * false) { // Se ordena al controlador crear una lista ligada con las
		 * // opciones // seleccionadas por el usuario
		 * controladorBusqueda.crearListaLigadaBusquedaBienEnVenta(); } else {
		 * controladorBusqueda.searchEveryThing(); } /
		 */

	}

	// ================================================================================
	// Metodos de carga de controles de la vista
	// ================================================================================

	/**
	 * Este Metodo se encarga de relacionar los controles que se usan en la
	 * vista con los controles del layout; ademas se encarga de darle la orden
	 * al controlador de cargar la informacion delos bienes a la venta
	 * 
	 */
	private void loadLayoutControls() {

		spUbicacion = (Spinner) findViewById(R.id.spUbicacion);

		spTipoBien2 = (Spinner) findViewById(R.id.spTipoBien2);

		spValor2 = (Spinner) findViewById(R.id.spValor2);

		// Se ordena al controlador cargar la informacion de los bienes a la
		// venta
		controladorBusqueda.gestionarCargaBienesVenta();

		// Se asignan a los demas controles las opciones de busqueda que les
		// corresponden
		/*
		 * / cargarElementosSpTipoBien2(); cargarElementosSpUbicacion();
		 * cargarElementosSpValor2(); /
		 */

	}

	public static void ordenaCargarTipoBienes2() {

	}

	/**
	 * En este metodo se ordena al controlador que gestione la carga de las
	 * diferentes opciones que se le dan al usuario para seleccionar el tipo de
	 * bien del Bien a la Venta
	 */
	public static void loadSpinnerTipoBienElements() {
		// se carga un vector con las diferentes opciones
		arregloTipoDeBien = controladorBusqueda
				.gestionarCargaTipoDeBienBienesEnVenta();

		// si lo devuelto es nulo, quiere decir que hubo algun error o no hay
		// datos minimos con los cuales trabajar; por lo que se retorna
		if (arregloTipoDeBien == null)
			return;

		// se crea un adaptador con las opciones cargadas en el vector
		adaptadorTipoBien2 = new ArrayAdapter<String>(
				controladorBusqueda.devolverActividadEnComunicadorGeneral(),
				R.layout.contenido_listas_personalizadas, arregloTipoDeBien);

		// se le asigana al adaptador la manera en que sera la lista que
		// contiene cada uno de los elementos que esta mostrara
		adaptadorTipoBien2
				.setDropDownViewResource(R.layout.contenido_listas_personalizadas);

		// se le asigana al spinner el adaptador
		spTipoBien2.setAdapter(adaptadorTipoBien2);

		// Se asigana el metodo que detecta cuando se selecciona un elemento del
		// spinner
		spTipoBien2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				// se verifica si la seleccion es la de la primera posicion,
				// osea la opcion seleccion; de ser asi solo vaciamos la opcion
				// de seleccion, de lo contrario llevamos a memoria una variable
				// con dicha seleccion
				if (arg2 != 0) {
					controladorBusqueda.enviarTipoDeBien2Seleccionado(arg0
							.getAdapter().getItem(arg2).toString());
					Log.i("Tipo de Bien", arg0.getAdapter().getItem(arg2)
							.toString());
				} else {
					controladorBusqueda.enviarTipoDeBien2Seleccionado(null);
					Log.i("e", "Restarted");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * En este metodo se ordena al controlador que gestione la carga de las
	 * diferentes opciones que se le dan al usuario para seleccionar el valor
	 * del Bien a la Venta
	 */
	public static void loadSpinnerValorElements() {
		// se carga un vector con las diferentes opciones
		arregloValor2 = controladorBusqueda
				.gestionarCargaValoresBienesEnVenta();

		// si lo devuelto es nulo, quiere decir que hubo algun error o no hay
		// datos minimos con los cuales trabajar; por lo que se retorna
		if (arregloValor2 == null)
			return;

		// se crear un adaptador con los valores que contenera el spinner
		adaptadorValor2 = new ArrayAdapter<String>(
				controladorBusqueda.devolverActividadEnComunicadorGeneral(),
				R.layout.contenido_listas_personalizadas, arregloValor2);

		// se le asigana al adaptador la manera en que sera la lista que
		// contiene cada uno de los elementos que esta mostrara
		adaptadorValor2
				.setDropDownViewResource(R.layout.contenido_listas_personalizadas);

		// se le asigana al spinner el adaptador
		spValor2.setAdapter(adaptadorValor2);

		// Se asigana el metodo que detecta cuando se selecciona un elemento del
		// spinner
		spValor2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				// se verifica si la seleccion es la de la primera posicion,
				// osea la opcion seleccion; de ser asi solo vaciamos la opcion
				// de seleccion, de lo contrario llevamos a memoria una variable
				// con dicha seleccion
				if (arg2 != 0) {
					controladorBusqueda.enviarValor2Seleccionado(arg0
							.getAdapter().getItem(arg2).toString());
					Log.i("Valor 2 seleccionado",
							arg0.getAdapter().getItem(arg2).toString());
				} else {
					controladorBusqueda.enviarValor2Seleccionado(null);
					Log.i("e", "Restarted");

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	public static void loadSpinnerUbicacionElements() {
		// se carga un vector con las diferentes opciones
		arregloUbicacion = controladorBusqueda
				.gestionarCargaUbicacionesBienesEnVenta();

		// si lo devuelto es nulo, quiere decir que hubo algun error o no hay
		// datos minimos con los cuales trabajar; por lo que se retorna
		if (arregloUbicacion == null)
			return;

		// se crear un adaptador con los valores que contenera el spinner
		adaptadorUbicacion = new ArrayAdapter<String>(
				controladorBusqueda.devolverActividadEnComunicadorGeneral(),
				R.layout.contenido_listas_personalizadas, arregloUbicacion);

		// se le asigana al adaptador la manera en que sera la lista que
		// contiene cada uno de los elementos que esta mostrara
		adaptadorUbicacion
				.setDropDownViewResource(R.layout.contenido_listas_personalizadas);

		// se le asigana al spinner el adaptador
		spUbicacion.setAdapter(adaptadorUbicacion);

		// Se asigana el metodo que detecta cuando se selecciona un elemento del
		// spinner
		spUbicacion.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				// se verifica si la seleccion es la de la primera posicion,
				// osea la opcion seleccion; de ser asi solo vaciamos la opcion
				// de seleccion, de lo contrario llevamos a memoria una variable
				// con dicha seleccion
				if (arg2 != 0) {
					controladorBusqueda.enviarUbicacionSeleccionado(arg0
							.getAdapter().getItem(arg2).toString());
					Log.i("Ubicaci—n seleccionado",
							arg0.getAdapter().getItem(arg2).toString());
				} else {
					controladorBusqueda.enviarUbicacionSeleccionado(null);
					Log.i("e", "Restarted");

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	// ********************************************************************************

	// ================================================================================
	// Metodos de Ejecucion de busquedas
	// ================================================================================

	/**
	 * Este metodo es llama cuando se presiona sobre el boton mostrar. Se
	 * encarga de obtener los resultados que considen con los parametro
	 * ingresados por el usuario
	 * 
	 * @param view
	 *            vista desde la cual es presionado el objeto
	 */
	public void btnMostrarClick2(View view) {
		controladorBusqueda.setLlamadas2(1);

		if (controladorBusqueda.verificarOpcionesBusquedaSeleccionadas() != false) {
			// Se ordena al controlador crear una lista ligada con las opciones
			// seleccionadas por el usuario
			controladorBusqueda.crearListaLigadaBusquedaBienEnVenta();
		} else {
			controladorBusqueda.searchEveryThing();
		}

	}

	/**
	 * Este metodo se encarga de mostrar los resultados que coinciden con los
	 * parametros ingresados por el usuario
	 */
	public static void showResults() {

		// se crear una lista de Biene en venta
		final List<BienALaVenta> b1 = controladorBusqueda
				.traerListaBienesALaVentaBuscados();

		// se mira si su tama–o es igual al cero lo que indica que no se obtuvo
		// ningun Resultado
		if (b1.size() == 0) {
			showAlertMessage(
					"Los criterios de bœsqueda no han arrojado resultados,  por favor verifique y vuelva a intentarlo",
					"Sin Resultados");
			Log.i("Resultados", " nulo");
			Log.i("Resultados", " nulo");
		} else { // de lo contrario
			// se ordena al controlador le informe al comunicador que
			// moestraremos los resultados de bienes en Venta
			controladorBusqueda.enviarOpcionResultado(2);

			// Se crea una alerta con todos los resultados dentro en una lista,
			// Definimos sus opciones mas relevantes
			AlertDialog.Builder builderSingle = new AlertDialog.Builder(
					controladorBusqueda.devolverActividadEnComunicadorGeneral());
			builderSingle.setIcon(R.drawable.icono);
			builderSingle.setTitle("Resultador");

			// se crea un adaptador para la vista que se mostrara
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					controladorBusqueda.devolverActividadEnComunicadorGeneral(),
					R.layout.contenido_listas_personalizadas);

			// se borra el adaptador en caso de que este tenga algun elemento
			// previo
			arrayAdapter.clear();

			// se recorren los bienes que se cargaron el los resultados y se
			// agrega cada uno al adaptador
			for (BienALaVenta b : b1) {
				arrayAdapter.add(controladorBusqueda.getTittleForListView(b
						.getDescripcion()) + "-" + b.getTipo());

			}

			// Se agrega un boton para cancelar la seleccion de alguna opcion
			builderSingle.setPositiveButton("Cerrar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});

			// se a–ade el metodo que estara pendiente del evento cuando se
			// selecciona algun elemento de la lista
			builderSingle.setAdapter(arrayAdapter,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							ComunicadorGeneral.setBienALaVentaAMostrar(b1
									.get(which));
							// En caso de que se presione algun elemento de la
							// lista se crea un intent y se llama la actividad
							// donde se muestran los resultados
							Intent i = new Intent(controladorBusqueda
									.devolverActividadEnComunicadorGeneral(),
									Resultados.class);
							controladorBusqueda
									.devolverActividadEnComunicadorGeneral()
									.startActivity(i);
						}

					});

			// Se muestra el dialogo
			AlertDialog dialogo = builderSingle.show();
			dialogo.show();
		}

		ComunicadorBusqueda.setListaBienesInmobiliariosBuscados(null);
	}

	// ********************************************************************************

	// ================================================================================
	// Metodos propios de los menus
	// ================================================================================

	/**
	 * Este metodo se encarga de seleccionar a que actividad vamos cuando el
	 * boton de atras o home es presionado
	 */
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		controladorBusqueda.setLlamadas2(0);
		switch (item.getItemId()) {
		case android.R.id.home:
			// se crea el intent adecuado para esta accion y se llama la
			// acitvidad
			Intent upIntent = new Intent(this, SeleccionarTipoBusqueda.class);
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
		getMenuInflater().inflate(R.menu.busqueda2, menu);
		return true;
	}

	// ********************************************************************************

	// ================================================================================
	// Metodos para cuando se vuewlve a la aplicacion
	// ================================================================================
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		controladorBusqueda.setBusqueda2Activity(this);
		if (controladorBusqueda.getLlamadas2() == 1) {
			if (controladorBusqueda.verificarOpcionesBusquedaSeleccionadas() != false) {
				// Se ordena al controlador crear una lista ligada con las
				// opciones
				// seleccionadas por el usuario
				controladorBusqueda.crearListaLigadaBusquedaBienEnVenta();
			} else {
				controladorBusqueda.searchEveryThing();
			}
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		controladorBusqueda.setBusqueda2Activity(this);
		if (controladorBusqueda.getLlamadas2() == 1) {
			if (controladorBusqueda.verificarOpcionesBusquedaSeleccionadas() != false) {
				// Se ordena al controlador crear una lista ligada con las
				// opciones
				// seleccionadas por el usuario
				controladorBusqueda.crearListaLigadaBusquedaBienEnVenta();
			} else {
				controladorBusqueda.searchEveryThing();
			}
		}
	}

	// ********************************************************************************

	/**
	 * Mensaje que alerta al usuario acerca de que ha ocurrido algun error, y lo
	 * regresa al a primer vista. posiblemente no hay datos minimos para
	 * ejecutar la aplicaci—n
	 * 
	 * @param mensaje
	 *            mensaje descriptivo que se mostrara
	 * @param titulo
	 *            titulo del mensaje que sera mostrado
	 */
	public static void showAlertMessage(String mensaje, String titulo) {
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		controladorBusqueda.getLlamadas2();
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent upIntent = new Intent(this, SeleccionarTipoBusqueda.class);
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
