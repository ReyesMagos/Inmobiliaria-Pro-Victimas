package co.gov.dps.inmovic.presentacion.vistas.busqueda;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import co.gov.dps.inmovic.dominio.controladores.ControllerBusqueda;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.vistas.destacados.DestacadoUI;
import co.gov.dps.inmovic.presentacion.vistas.destacados.MensajeAdvertencia;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;

@SuppressLint("NewApi")
public class Busqueda extends ActionBarActivity {

	private Spinner spDepartamentos;
	private Spinner spMunicipio;
	private Spinner spTipoBien;
	private Spinner spTipoInmueble;
	private Spinner spValor;
	private Spinner spUsodelBien;
	private Spinner spNumHabitaciones;
	private Spinner spNumBaños;
	private String[] arregloDepartamento;
	private String[] arregloMunicipios;
	private String[] arregloTipoDeBien;
	private String[] arregloTipoDeInmueble;
	private String[] arregloUsoBien;
	private String[] arregloNumeroBanos;
	private String[] arregloNumeroHabitaciones;
	private String[] arregloValor;
	private ArrayAdapter<String> adaptadorDepartamento;
	private ArrayAdapter<String> adaptadorMunicipio;
	private ArrayAdapter<String> adaptadorTipoBien;
	private ArrayAdapter<String> adaptadorTipoInmueble;
	private ArrayAdapter<String> adaptadorNumHabitacion;
	private ArrayAdapter<String> adaptadorNumBaños;
	private ArrayAdapter<String> adaptadorUsoBien;
	private ArrayAdapter<String> adaptadorValor;
	private android.support.v7.app.ActionBar action;
	private static ControllerBusqueda controladorBusqueda;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_busqueda);
		controladorBusqueda = new ControllerBusqueda();
		controladorBusqueda.establerActividadEnComunicadorGeneral(this);
		action = ((Busqueda) controladorBusqueda
				.devolverActividadEnComunicadorGeneral()).getSupportActionBar();
		action.setTitle("Selecciona");
		action.setDisplayHomeAsUpEnabled(true);

		cargaControlesLayout1();

	}

	// ================================================================================
	// Carga de elementos de la Vista Para Seleccionar si se busca por inmueble
	// o bien
	// ================================================================================

	// ********************************************************************************

	// ================================================================================
	// Carga de elementos de la Vista Busqueda por Inmuebles
	// ================================================================================

	/**
	 * Este metodo se encarga de controlar la carga de los controles de la
	 * vista, en el se inicializan y llaman los metodos para asignarle los
	 * elementos para seleccionar en cada uno de los spinners
	 * 
	 */
	private void cargaControlesLayout1() {

		// Se inicializan lo diferentes controles y se enlazan con sus pares en
		// el XML
		spDepartamentos = (Spinner) findViewById(R.id.spDepartamento);

		spMunicipio = (Spinner) findViewById(R.id.spMunicipio);

		spTipoBien = (Spinner) findViewById(R.id.spTipoBien);

		spTipoInmueble = (Spinner) findViewById(R.id.spInmueble);

		spUsodelBien = (Spinner) findViewById(R.id.spUsodelBien);

		spNumHabitaciones = (Spinner) findViewById(R.id.spnumerohabitaciones);

		spNumBaños = (Spinner) findViewById(R.id.spnumerobanos);

		spValor = (Spinner) findViewById(R.id.spValor);

		// Se cargan los departamentos y se llevan a un arreglo de Strings
		arregloDepartamento = controladorBusqueda.gestionaCargaDepartamentos();

		cargarElementosSpinnerAcDepartamento();
		cargarElementosSpinnerTipoDeBien();
		cargaElementosSpinnerTipoDeInmueble();
		cargarElementosSpinnerUsoBien();
		cargarElementosNumeroBanos();
		cargarElementosSpinnerNumeroHabitaciones();
		cargarElementosValor();

	}

	/**
	 * Este metodo se encarga de cargar el spinner de seleccion de los
	 * departamentos que se mostraran al usuario para su posible seleccion
	 */
	private void cargarElementosSpinnerAcDepartamento() {

		// Se inicializa un adaptador para el spinner con un arreglo que
		// contiene los departamentos posibles a seleccionar
		adaptadorDepartamento = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arregloDepartamento);

		// Se anade el layout para mostrar las opciones y la forma en que este
		// se desplegara
		adaptadorDepartamento
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spDepartamentos.setAdapter(adaptadorDepartamento);

		// Se anade el metodo que se ejecutara cada vez que halla una seleccion
		spDepartamentos.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				// Si la opcion seleccionada es distinta de 0, que es donde se
				// encuentra la opcion de seleccione se guarda el departamento
				// seleccionado y se cargan los municipios para este
				// departamento
				if (arg2 != 0) {

					controladorBusqueda.enviarDepartamentoSeleccionado(arg0
							.getAdapter().getItem(arg2).toString());

					// se cargan los municipios para el departamento
					// seleccionado
					cargarElementosSpinnerMunicipios(arg0.getAdapter()
							.getItem(arg2).toString());

					Log.i("Departamento Seleccionado", arg0.getAdapter()
							.getItem(arg2).toString());
				} else {
					// si la opcion seleccionada es la 0, se borra lo que halla
					// en el departamento seleccionado
					controladorBusqueda.enviarDepartamentoSeleccionado(null);
					Log.i("e", "Restarted");

					// ademas como no hay departamento seleccionado no debe
					// haber municipio
					adaptadorMunicipio = new ArrayAdapter<String>(
							ComunicadorGeneral.getActividad(),
							android.R.layout.simple_list_item_1,
							new String[] { "Seleccione" });

					// se asigna el adaptador sin opciones a el spinner de los
					// municipios
					spMunicipio.setAdapter(adaptadorMunicipio);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * Este metodo se encarga de cargar el spinner de seleccion de los
	 * municipios que se mostraran al usuario para su posible seleccion
	 */
	private void cargarElementosSpinnerMunicipios(String depto) {

		// se ordena al controlador que traiga los municipios a un arreglo
		arregloMunicipios = controladorBusqueda.gestionaCargaMunicipios(depto);

		// Se inicializa un adaptador para el spinner con un arreglo que
		// contiene los municipios posibles a seleccionar
		adaptadorMunicipio = new ArrayAdapter<String>(
				ComunicadorGeneral.getActividad(),
				android.R.layout.simple_list_item_1, arregloMunicipios);

		// Se anade el layout para mostrar las opciones y la forma en que este
		// se desplegara
		adaptadorMunicipio
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);

		// Se anade el metodo que se ejecutara cada vez que halla una seleccion
		spMunicipio.setAdapter(adaptadorMunicipio);

		spMunicipio.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 != 0) {

					controladorBusqueda.enviarMunicipioSeleccionado(arg0
							.getAdapter().getItem(arg2).toString());

					Log.i("muniSeleccionado", arg0.getAdapter().getItem(arg2)
							.toString());
				} else {
					controladorBusqueda.enviarMunicipioSeleccionado(null);
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
	 * Este metodo se encarga de cargar el spinner de seleccion de los tipos de
	 * bienes que se mostraran al usuario para su posible seleccion. La
	 * descripcion de este metodo es similar a la de los dos anteriores
	 */
	private void cargarElementosSpinnerTipoDeBien() {
		arregloTipoDeBien = controladorBusqueda.gestionaCargaTipoBien();
		adaptadorTipoBien = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arregloTipoDeBien);
		adaptadorTipoBien
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spTipoBien.setAdapter(adaptadorTipoBien);
		spTipoBien.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 != 0) {
					controladorBusqueda.enviarTipodeBienSeleccionado(arg0
							.getAdapter().getItem(arg2).toString());
					Log.i("tipo de bien seleccionado ", arg0.getAdapter()
							.getItem(arg2).toString());
				} else {
					controladorBusqueda.enviarTipodeBienSeleccionado(null);
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
	 * Este metodo se encarga de cargar el spinner de seleccion de los tipos de
	 * Inmueble que se mostraran al usuario para su posible seleccion. La
	 * descripcion de este metodo es similar a la de los dos anteriores
	 */
	private void cargaElementosSpinnerTipoDeInmueble() {
		arregloTipoDeInmueble = controladorBusqueda
				.gestionaCargaTipoDeInmueble();
		adaptadorTipoInmueble = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arregloTipoDeInmueble);
		adaptadorTipoInmueble
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spTipoInmueble.setAdapter(adaptadorTipoInmueble);
		spTipoInmueble.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 != 0) {
					controladorBusqueda.enviarTipoInmuebleSeleccionado(arg0
							.getAdapter().getItem(arg2).toString());
					Log.i("Tipo Inmueble", arg0.getAdapter().getItem(arg2)
							.toString());
				} else {
					controladorBusqueda.enviarTipoInmuebleSeleccionado(null);
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
	 * Este metodo se encarga de cargar el spinner de seleccion de los tipos de
	 * uso de bien que se mostraran al usuario para su posible seleccion. La
	 * descripcion de este metodo es similar a la de los dos anteriores
	 */
	private void cargarElementosSpinnerUsoBien() {
		arregloUsoBien = controladorBusqueda.gestionaCargaUsoDelBien();
		adaptadorUsoBien = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arregloUsoBien);
		adaptadorUsoBien
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spUsodelBien.setAdapter(adaptadorUsoBien);
		spUsodelBien.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 != 0) {
					controladorBusqueda.enviarUsoBienSeleccionado(arg0
							.getAdapter().getItem(arg2).toString());
					Log.i("Uso de bien seleccionado", arg0.getAdapter()
							.getItem(arg2).toString());
				} else {
					controladorBusqueda.enviarUsoBienSeleccionado(null);
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
	 * Este metodo se encarga de cargar el spinner de seleccion de los numeros
	 * de banos que se mostraran al usuario para su posible seleccion. La
	 * descripcion de este metodo es similar a la de los dos anteriores
	 */
	private void cargarElementosNumeroBanos() {
		arregloNumeroBanos = controladorBusqueda.gestionaCargaNumeroBanos();
		adaptadorNumBaños = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arregloNumeroBanos);
		adaptadorNumBaños
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spNumBaños.setAdapter(adaptadorNumBaños);
		spNumBaños.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 != 0) {
					controladorBusqueda.enviarNumeroBanosSeleccionado(arg0
							.getAdapter().getItem(arg2).toString());
					Log.i("Número de Baños Seleccionado", arg0.getAdapter()
							.getItem(arg2).toString());
				} else {
					controladorBusqueda.enviarNumeroBanosSeleccionado(null);
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
	 * Este metodo se encarga de cargar el spinner de seleccion de los numeros
	 * de habitacion, que se mostraran al usuario para su posible seleccion. La
	 * descripcion de este metodo es similar a la de los dos anteriores
	 */
	private void cargarElementosSpinnerNumeroHabitaciones() {
		arregloNumeroHabitaciones = controladorBusqueda
				.gestionaCargaNumeroHabitaciones();
		adaptadorNumHabitacion = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arregloNumeroHabitaciones);
		adaptadorNumHabitacion
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spNumHabitaciones.setAdapter(adaptadorNumHabitacion);
		spNumHabitaciones
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						if (arg2 != 0) {
							controladorBusqueda
									.enviarNumeroHabitacionesSeleccionado(arg0
											.getAdapter().getItem(arg2)
											.toString());
							Log.i("Número de habitaciones seleccionado", arg0
									.getAdapter().getItem(arg2).toString());
						} else {
							controladorBusqueda
									.enviarNumeroHabitacionesSeleccionado(null);
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
	 * Este metodo se encarga de cargar el spinner de seleccion de los posibles
	 * precios de arriendo que se mostraran al usuario para su posible
	 * seleccion. La descripcion de este metodo es similar a la de los dos
	 * anteriores
	 */
	private void cargarElementosValor() {
		arregloValor = controladorBusqueda.gestionaCargaValores();
		adaptadorValor = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arregloValor);
		adaptadorValor
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spValor.setAdapter(adaptadorValor);
		spValor.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 != 0) {
					controladorBusqueda.enviarValorSeleccionado(arg0
							.getAdapter().getItem(arg2).toString());
					Log.i("Valor seleccionado", arg0.getAdapter().getItem(arg2)
							.toString());
				} else {
					controladorBusqueda.enviarValorSeleccionado(null);
					Log.i("e", "Restarted");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	// ================================================================================
	// Carga de elementos de la vista de busqueda por bienes en venta
	// ================================================================================

	// ********************************************************************************

	// ================================================================================
	// Metodos de la Busqueda por Inmuebles
	// ================================================================================

	/**
	 * Metodo que se llama al ser presionado el Boton Buscar
	 * 
	 * @param view
	 *            la vista en la que se encuentra el boton
	 */
	public void btnMostrarClick(View view) {

		controladorBusqueda.setLlamadas(1);
		if (controladorBusqueda.verificarOpcionesBusquedaSeleccionadas() != false) {
			// Se ordena al controlador crear una lista ligada con las opciones
			// seleccionadas por el usuario
			controladorBusqueda.crearLigaBusquedaBieneInmobiliarios();
		} else {
			controladorBusqueda.searchEveryThing();
		}

	}

	/**
	 * Metodo que se encarga de mostrar o no los resultados de acuerdo a los
	 * paramentros de busqueda seleccionados por el usuario
	 */
	public static void showResults() {

		// Se crea una lista de bienes inmobiliarios y se ordena al controlador
		// traer la lista de resultados que coinciden con los criterios de
		// busqueda seleccionados por el usuario
		final List<BienInmobiliario> listaResultadosBienesInmobiliarios = controladorBusqueda
				.traerListaDeBienesInmobiliariosBuscados();

		// Si no se produjeron resultados para los criterios de busqueda
		// ingresados por el usario se le informa
		if (listaResultadosBienesInmobiliarios.size() == 0) {
			showAlertMessage(
					"Los criterios de busqueda no han arrojado resultados,  por favor verifique y vuelva a intentarlo",
					"Sin Resultados");
			Log.i("Resultados", " nulo");
		} else {
			// se se producen resiltados se le indica al comunicador general
			// mediante el controlador que se mostran resultados de bienes
			// inmuebles
			controladorBusqueda.enviarOpcionResultado(1);

			// Se crea un objeto del tipo alertdialog y se le asigna esta
			// actividad como contexto
			AlertDialog.Builder builderSingle = new AlertDialog.Builder(
					controladorBusqueda.devolverActividadEnComunicadorGeneral());

			// Se le asigna un icono al alertdialog
			builderSingle.setIcon(R.drawable.icono);

			// Se le anade un titulo al alertdialog
			builderSingle.setTitle("Resultador");

			// Se carga un adaptador para mostrar los resultados , el cual sera
			// del tipo listview donde solo sera posible una seleccion
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					ComunicadorGeneral.getActividad(),
					android.R.layout.select_dialog_singlechoice);

			// Se limp“a el adaptador en caso de que halla sido llamado ya en la
			// ejecucion del programa, esto con el fin de borrar los resultados
			// previos
			arrayAdapter.clear();

			// Se recorre cada resultado que hay en la lista y de ellos se
			// guarda el nombre del bien, para mostrarlo en la lista luego
			for (BienInmobiliario b : listaResultadosBienesInmobiliarios) {
				arrayAdapter
						.add(b.getNombredelbien() + "-" + b.getTipodebien());
				Log.i("Resultados", b.getNombredelbien() + "-" + b.getTipo()
						+ "-" + b.getNumBaño() + "-" + b.getNumHabitacion()
						+ "-" + b.getValor());
			}

			// Se anade un boton al alertdialog en caso de que se desee cerrar
			// la vista de resultados
			builderSingle.setPositiveButton("Cerrar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});

			// se anade el metodo que se ejecuta cuando un bien de la lista de
			// los resultados es seleccionado para ampliar su informacion
			builderSingle.setAdapter(arrayAdapter,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							// Se anade o se le indica al comunicador general
							// mediante el controlador cual es el bien
							// seleccionado por el usuario para ampliar su
							// informaci—n
							controladorBusqueda
									.anadirBienAMostrarEnComunicadorGeneral(listaResultadosBienesInmobiliarios
											.get(which));

							// Se crea un nuevo intento
							Intent i = new Intent(controladorBusqueda
									.devolverActividadEnComunicadorGeneral(),
									Resultados.class);

							// Se inicia la actividad asociada al intento
							// anterior
							controladorBusqueda
									.devolverActividadEnComunicadorGeneral()
									.startActivity(i);
						}

					});

			// Se Muestra el alertdialog con los bienes posibles a seleccionar
			AlertDialog dialogo = builderSingle.show();
			dialogo.show();
		}

		// Se le indica al comunicador de la busqueda que no hay bienes buscados
		// pues estos han sido mostrados ya
		controladorBusqueda.anadirListaDeBienesInmobiliariosBuscados(null);
	}

	// ********************************************************************************

	// ********************************************************************************

	// ================================================================================
	// Metodos propios de los menus
	// ================================================================================

	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		controladorBusqueda.setLlamadas(0);
		switch (item.getItemId()) {
		// Si se selecciona en el actionbar la opcion de regresar se crea un
		// intento con la actividad previa y se llama esta
		case android.R.id.home:

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
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.busqueda, menu);
		return super.onCreateOptionsMenu(menu);

	}

	// ********************************************************************************
	// ================================================================================
	// Metodos para cuando se vuewlve a la aplicacion
	// ================================================================================
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		controladorBusqueda.establerActividadEnComunicadorGeneral(this);
		if (controladorBusqueda.getLlamadas() == 1) {
			if (controladorBusqueda.verificarOpcionesBusquedaSeleccionadas() != false) {
				// Se ordena al controlador crear una lista ligada con las
				// opciones
				// seleccionadas por el usuario
				controladorBusqueda.crearLigaBusquedaBieneInmobiliarios();
			} else {
				controladorBusqueda.searchEveryThing();
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (controladorBusqueda.getLlamadas() == 1) {
			if (controladorBusqueda.verificarOpcionesBusquedaSeleccionadas() != false) {
				// Se ordena al controlador crear una lista ligada con las
				// opciones
				// seleccionadas por el usuario
				controladorBusqueda.crearLigaBusquedaBieneInmobiliarios();
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
	public static void showErrorMessage(String mensaje, String titulo,
			final int opcion) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ComunicadorGeneral.getActividad());
		builder.setTitle(titulo);
		builder.setIcon(R.drawable.icono);
		builder.setMessage(mensaje).setPositiveButton(R.string.btn_aceptar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent i;
						if (opcion == 1) {
							i = new Intent(ComunicadorGeneral.getActividad(),
									DestacadoUI.class);
						} else {
							i = new Intent(ComunicadorGeneral.getActividad(),
									SeleccionarTipoBusqueda.class);
						}
						ComunicadorGeneral.getActividad().startActivity(i);

					}
				});

		AlertDialog dialog = builder.show();
		TextView messageText = (TextView) dialog
				.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);
		dialog.show();
	}

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

	/**
	 * Este metodo se encarga de verificar la conexion a internet del
	 * dispositivo, ya sea por datos o por wifi
	 * 
	 * @param ctx
	 *            este parametro es el contexto actual de la aplicacion
	 * @return retorna falso de no haber conexion y verdadero en el caso que si
	 *         halla
	 */
	public static boolean inspectConection(Context ctx) {
		boolean bConectado = false;
		// se obtiene el servicio de conectividad el dispositivo
		ConnectivityManager connec = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// se anade el estado de las conecciones del servicio de conectividad
		NetworkInfo[] redes = connec.getAllNetworkInfo();
		for (int i = 0; i < 2; i++) {

			if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
				bConectado = true;
			}
		}
		return bConectado;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		controladorBusqueda.setLlamadas(0);
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
