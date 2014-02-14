package co.gov.dps.inmovic.presentacion.vistas.destacados;

import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;

import co.gov.dps.inmovic.presentacion.vistas.busqueda.SeleccionarTipoBusqueda;
import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Formulario extends ActionBarActivity {

	Spinner opcionesDoc;
	Button btnEnviar;
	EditText nombre;
	EditText apellido;
	EditText ciudad;
	EditText numero;
	EditText tel;
	TextView inmueble;
	EditText canon;
	EditText ingreso;

	private String name;
	private String apell;
	private String city;
	private String num;
	private String telefono;
	private String nameInmueble;
	private String valCanon;
	private String valIngreso;
	public static int tipoBienSeleccionado;
	public android.support.v7.app.ActionBar action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario_inscripcion);
		action = getSupportActionBar();
		action.setTitle("Resultado");

		action.setDisplayHomeAsUpEnabled(true);
		;

		// opcionesDoc = (Spinner) findViewById(R.id.spinner1);
		this.nombre = (EditText) findViewById(R.id.editText1);
		this.apellido = (EditText) findViewById(R.id.editTxtApell1);
		// nombre.setText(MainActivity.b.getNombredelbien().toString());
		this.ciudad = (EditText) findViewById(R.id.editText2);
		this.numero = (EditText) findViewById(R.id.editText3);
		this.tel = (EditText) findViewById(R.id.editText4);
		// this.inmueble = (TextView) findViewById(R.id.textView1);
		this.canon = (EditText) findViewById(R.id.editText6);
		if (ComunicadorGeneral.getBienAMostrar() == null) {
			/*
			 * inmueble.setText(ComunicadorGeneral.getBienALaVentaAMostrar()
			 * .getDescripcion());
			 */
			canon.setText(ComunicadorGeneral.getBienALaVentaAMostrar()
					.getValor());
		} else {
			/*
			 * inmueble.setText(ComunicadorGeneral.getBienAMostrar()
			 * .getNombredelbien());
			 */
			canon.setText(ComunicadorGeneral.getBienAMostrar().getValor());
		}

		// this.ingreso = (EditText) findViewById(R.id.editText7);

		// final String[] opciones = new String[] { "C.C", "C.E", "Otro" };

		/*
		 * ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, opciones); adaptador
		 * .setDropDownViewResource
		 * (android.R.layout.simple_expandable_list_item_1);
		 * opcionesDoc.setAdapter(adaptador);
		 * 
		 * opcionesDoc .setOnItemSelectedListener(new
		 * AdapterView.OnItemSelectedListener() {
		 * 
		 * @Override public void onItemSelected(AdapterView<?> arg0, View arg1,
		 * int arg2, long arg3) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onNothingSelected(AdapterView<?> arg0) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 */

		btnEnviar = (Button) findViewById(R.id.btnEnviar);
		btnEnviar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				name = nombre.getText().toString();

				if (name.equals("")) {
					mensajeAlerta("Por favor Ingrese su nombre");
					return;
				}

				apell = apellido.getText().toString();
				if (apell.equals("")) {
					mensajeAlerta("Por favor Ingrese su apellido");
					return;
				}

				num = numero.getText().toString();
				if (num.equals("")) {
					mensajeAlerta("Por favor Ingrese su documento de Identidad");
					return;
				} else if (!criticaCamposTexto(num)) {
					mensajeAlerta("Por favor verifique el documento de identidad.");
					numero.setText("");
					return;
				}

				city = ciudad.getText().toString();
				if (city.equals("")) {
					mensajeAlerta("Por favor Ingrese la Ciudad");
					return;
				}

				telefono = tel.getText().toString();
				if (telefono.equals("")) {
					mensajeAlerta("Por favor Ingrese un número de Teléfono");
					return;
				} else if (!criticaCamposTexto(telefono)) {
					mensajeAlerta("Por favor Ingrese un número de Teléfono valido");
					tel.setText("");
					return;

				}
				// nameInmueble =
				// ComunicadorGeneral.getBienAMostrar().getNombredelbien();
				valCanon = canon.getText().toString();
				if (valCanon.equals("")) {
					mensajeAlerta("Por favor Ingrese un valor para el "
							+ " Inmueble");
					return;
				} else if (!criticaCamposTexto(valCanon)
						&& !valCanon.equals("")) {
					valCanon = "En actualización";
				} else if (!criticaCamposTexto(valCanon)) {
					mensajeAlerta("Por favor Ingrese un valor valido para el Inmueble");
					return;
				}
				/*
				 * valIngreso = ingreso.getText().toString(); if
				 * (valIngreso.equals("")) {
				 * mensajeAlerta("Por favor Ingrese sus ingresos mensuales");
				 * return; } else if (!criticaCamposTexto(valIngreso)) {
				 * mensajeAlerta
				 * ("Por favor Ingrese un valor valido para los ingresos");
				 * ingreso.setText(""); return;
				 * 
				 * }
				 */

				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "arriendos.frv@unidadvictimas.gov.co" });

				if (Formulario.tipoBienSeleccionado == 0) {
					i.putExtra(Intent.EXTRA_SUBJECT,
							"Formulario arrendamiento inmuebles "
									+ ComunicadorGeneral.getBienAMostrar()
											.getNombredelbien());
					i.putExtra(
							Intent.EXTRA_TEXT,
							"Hay un usuario de Inmovic para Android que desea mas información acerca del inmueble: "
									+ ComunicadorGeneral.getBienAMostrar()
											.getNombredelbien()
									+ "\n"
									+ "Tipo: "
									+ ComunicadorGeneral.getBienAMostrar()
											.getTipo()
									+ "\n"
									+ "Area: "
									+ ComunicadorGeneral.getBienAMostrar()
											.getTipodeinmueble()
									+ "\n"
									+ "Ubicación: "
									+ ComunicadorGeneral.getBienAMostrar()
											.getMunicipio()
									+ "\n"
									+ "Departamento: "
									+ ComunicadorGeneral.getBienAMostrar()
											.getDepartamento()
									+ "\n"
									+ "Folio Matricula"
									+ ComunicadorGeneral.getBienAMostrar()
											.getFoliodematriculainmobiliaria()
									+ "\n"
									+ "Precio: "
									+ ComunicadorGeneral.getBienAMostrar()
											.getValor()
									+ "\n"
									+ "A continuación encontrará la información del usuario para comunicarse con él:"
									+ "Nombre: "
									+ name
									+ " "
									+ apell
									+ "\n"
									+ "Número documento: "
									+ num
									+ "\n"
									+ "Ciudad:"
									+ city
									+ "\n"
									+ "Teléfono: "
									+ telefono
									+ "\n"
									+ "Valor Arriendo: $"
									+ valCanon);
				} else {
					i.putExtra(Intent.EXTRA_SUBJECT,
							"Formulario Venta inmuebles ");
					i.putExtra(
							Intent.EXTRA_TEXT,
							"Hay un usuario de Inmovic para Android que desea mas información acerca del bien : "
									+ ComunicadorGeneral
											.getBienALaVentaAMostrar().tipo
									+ "\n"
									+ "Ubicación: "
									+ ComunicadorGeneral
											.getBienALaVentaAMostrar()
											.getUbicacion()
									+ "\n"
									+ "Valor: "
									+ ComunicadorGeneral
											.getBienALaVentaAMostrar()
											.getValor()
									+ "\n"
									+ "Descripción: "
									+ ComunicadorGeneral
											.getBienALaVentaAMostrar()
											.getDescripcion()
									+ "\n"
									+ "A continuación encontrará la información del usuario para comunicarse con él:"
									+ "\n"
									+ "Nombre: "
									+ name
									+ " "
									+ apell
									+ "\n"
									+ "Número documento: "
									+ num
									+ "\n"
									+ "Ciudad:"
									+ city
									+ "\n"
									+ "Teléfono: "
									+ telefono
									+ "\n"
									+ "Valor Venta: $"
									+ valCanon);
				}

				try {
					startActivity(Intent.createChooser(i, "Enviando Email..."));
					finish();
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(Formulario.this,
							"There are no email clients installed.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.formulario, menu);
		return true;
	}

	public void mensajeAlerta(String mensaje) {
		AlertDialog.Builder alerta = new AlertDialog.Builder(Formulario.this);
		alerta.setTitle("Error");
		alerta.setMessage(mensaje);
		alerta.setIcon(R.drawable.icono);

		alerta.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		alerta.show();
	}

	public boolean criticaCamposTexto(String num) {
		boolean numero = true;

		int k = 0;
		char c;
		while (k < num.length()) {
			c = num.charAt(k);
			if (k == 0) {
				if ((c < 48 || c > 57)) {

					return false;
				}
				k++;
				continue;
			}
			if (c < 48 || c > 57) {

				return false;
			}
			k++;
		}
		return numero;
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
			Intent upIntent = new Intent(this, Resultados.class);
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent upIntent = new Intent(this, Resultados.class);
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
