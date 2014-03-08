package co.gov.dps.inmovic.presentacion.vistas.destacados;

import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.controladores.ControllerFormulario;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;

import co.gov.dps.inmovic.presentacion.vistas.vistabien.Resultados;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
	public static int tipoBienSeleccionado;
	public static ControllerFormulario controladorFormulario;
	public android.support.v7.app.ActionBar action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario_inscripcion);
		ComunicadorGeneral.setFormOrComen(true);
		action = getSupportActionBar();
		action.setTitle("Resultado");
		action.setDisplayHomeAsUpEnabled(true);
		controladorFormulario = new ControllerFormulario();
		controladorFormulario.setActivityFormulario(this);
		

		// opcionesDoc = (Spinner) findViewById(R.id.spinner1);
		this.nombre = (EditText) findViewById(R.id.editText1);
		this.apellido = (EditText) findViewById(R.id.editTxtApell1);
		// nombre.setText(MainActivity.b.getNombredelbien().toString());
		this.ciudad = (EditText) findViewById(R.id.editText2);
		this.numero = (EditText) findViewById(R.id.editText3);
		this.tel = (EditText) findViewById(R.id.editText4);
		// this.inmueble = (TextView) findViewById(R.id.textView1);
		this.canon = (EditText) findViewById(R.id.editText6);
		if (controladorFormulario.getBienInmobiliarioThatWillBeShowed() != null) {
			/*
			 * inmueble.setText(ComunicadorGeneral.getBienALaVentaAMostrar()
			 * .getDescripcion());
			 */
			canon.setText(controladorFormulario
					.getBienInmobiliarioThatWillBeShowed().getValor());
		} else {
			/*
			 * inmueble.setText(ComunicadorGeneral.getBienAMostrar()
			 * .getNombredelbien());
			 */
			canon.setText(controladorFormulario
					.getBienALaVentaThatWillBeShowed().valor);
		}

		btnEnviar = (Button) findViewById(R.id.btnEnviar);
		btnEnviar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				controladorFormulario.eraseData();

				controladorFormulario.setNombre(nombre.getText().toString());

				controladorFormulario.setApell(apellido.getText().toString());

				controladorFormulario.setNum(numero.getText().toString());

				controladorFormulario.setCity(ciudad.getText().toString());

				controladorFormulario.setTelefono(tel.getText().toString());

				controladorFormulario.setValCanon(canon.getText().toString());
				if (!controladorFormulario.verificarInformation()) {

					controladorFormulario.eraseData();
					return;
				}

				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "arriendos.frv@unidadvictimas.gov.co" });

				if (Formulario.tipoBienSeleccionado == 0) {
					i.putExtra(
							Intent.EXTRA_SUBJECT,
							"Formulario arrendamiento inmuebles "
									+ controladorFormulario
											.getBienInmobiliarioThatWillBeShowed()
											.getNombredelbien());
					i.putExtra(
							Intent.EXTRA_TEXT,
							"Hay un usuario de Inmovic para Android que desea mas información acerca del inmueble: "
									+ controladorFormulario
											.getBienInmobiliarioThatWillBeShowed()
											.getNombredelbien()
									+ "\n"
									+ "Tipo: "
									+ controladorFormulario
											.getBienInmobiliarioThatWillBeShowed()
											.getTipo()
									+ "\n"
									+ "Area: "
									+ controladorFormulario
											.getBienInmobiliarioThatWillBeShowed()
											.getTipodeinmueble()
									+ "\n"
									+ "UbicaciÛn: "
									+ controladorFormulario
											.getBienInmobiliarioThatWillBeShowed()
											.getMunicipio()
									+ "\n"
									+ "Departamento: "
									+ controladorFormulario
											.getBienInmobiliarioThatWillBeShowed()
											.getDepartamento()
									+ "\n"
									+ "Folio Matricula"
									+ controladorFormulario
											.getBienInmobiliarioThatWillBeShowed()
											.getFoliodematriculainmobiliaria()
									+ "\n"
									+ "Precio: "
									+ controladorFormulario
											.getBienInmobiliarioThatWillBeShowed()
											.getValor()
									+ "\n"
									+ "A continuación encontrará la información del usuario para comunicarse con él:"
									+ "Nombre: "
									+ controladorFormulario.getNombre()
									+ " "
									+ controladorFormulario.getApell()
									+ "\n"
									+ "Nùmero documento: "
									+ controladorFormulario.getNum()
									+ "\n"
									+ "Ciudad:"
									+ controladorFormulario.getCity()
									+ "\n"
									+ "Teléfono: "
									+ controladorFormulario.getTelefono()
									+ "\n"
									+ "Valor Arriendo: $"
									+ controladorFormulario.getValCanon());
				} else {
					i.putExtra(Intent.EXTRA_SUBJECT,
							"Formulario Venta inmuebles ");
					i.putExtra(
							Intent.EXTRA_TEXT,
							"Hay un usuario de Inmovic para Android que desea mas información acerca del bien : "
									+ controladorFormulario
											.getBienALaVentaThatWillBeShowed()
											.getTipo()
									+ "\n"
									+ "Ubicación: "
									+ controladorFormulario
											.getBienALaVentaThatWillBeShowed()
											.getUbicacion()
									+ "\n"
									+ "Valor: "
									+ controladorFormulario
											.getBienALaVentaThatWillBeShowed()
											.getValor()
									+ "\n"
									+ "Descripción: "
									+ controladorFormulario
											.getBienALaVentaThatWillBeShowed()
											.getDescripcion()
									+ "\n"
									+ "A continuación"
									+ "n encontrará la información del usuario para comunicarse con él:"
									+ "\n"
									+ "Nombre: "
									+ controladorFormulario.getNombre()
									+ " "
									+ controladorFormulario.getApell()
									+ "\n"
									+ "Número documento: "
									+ controladorFormulario.getNum()
									+ "\n"
									+ "Ciudad:"
									+ controladorFormulario.getCity()
									+ "\n"
									+ "Teléfono: "
									+ controladorFormulario.getTelefono()
									+ "\n"
									+ "Valor Venta: $"
									+ controladorFormulario.getValCanon());
				}

				try {
					startActivity(Intent.createChooser(i, "Enviando Email..."));
					finish();
				} catch (android.content.ActivityNotFoundException ex) {

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

	public static void mensajeAlerta(String mensaje) {
		AlertDialog.Builder alerta = new AlertDialog.Builder(
				controladorFormulario.getGeneralActivity());
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
