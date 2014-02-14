package co.gov.dps.inmovic.presentacion.vistas.vistabien;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import co.gov.dps.inmovic.dominio.controladores.ComunicadorComentario;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.controladores.ControllerResultados;
import co.gov.dps.inmovic.dominio.controladores.ServicioSoapController;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R.layout;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R.menu;
import co.gov.dps.inmovic.servicio.servicioSOAP.ServicioCrearPuntuacion;
import co.gov.dps.inmovic.servicio.servicioweb.TareaWSConsultarPuntuacion;
import co.gov.dps.inmovic.servicio.servicioweb.TareaWSInsertarPuntuacion;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

public class Comentario extends Activity {

	private static ListView listaComentario;
	public static RatingBar valPuntuacion;
	public static EditText comentario;
	public static Button btn;
	public static ImageView insertarComentario;
	private static ControllerResultados servioSoapController;
	static ArrayAdapter<String> adaptador;
	private ActionBar action;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comentario);
		
		Comentario.listaComentario = (ListView) findViewById(R.id.listView11);
		ComunicadorGeneral.setActividad(this);
		action = getActionBar();
		action.setTitle("Resultado");

		TareaWSConsultarPuntuacion t = new TareaWSConsultarPuntuacion();
		t.execute();

		Comentario.comentario = (EditText) findViewById(R.id.editText1);
		Comentario.valPuntuacion = (RatingBar) findViewById(R.id.ratingBar1);
		Comentario.btn = (Button) findViewById(R.id.buttonactividad);
		Comentario.insertarComentario = (ImageView) findViewById(R.id.imageView1);
		Comentario.insertarComentario.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Comentario.comentario.setVisibility(View.VISIBLE);
				Comentario.valPuntuacion.setVisibility(View.VISIBLE);
				Comentario.btn.setVisibility(View.VISIBLE);
				Comentario.insertarComentario.setVisibility(View.INVISIBLE);

			}
		});

		final int puntuacion = (int) Comentario.valPuntuacion.getRating();

		Comentario.btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ((int) Comentario.valPuntuacion.getRating() == 0
						|| Comentario.comentario.getText().toString()
								.equals("")) {
					mensajeAlerta("Por favor Ingrese tanto comentario, como puntuación");

				} else {

					TareaWSInsertarPuntuacion t = new TareaWSInsertarPuntuacion();
					t.execute();
					//finish();
					Toast.makeText(Comentario.this, "Comentario Enviado", 3).show();
					Comentario.valPuntuacion.setEnabled(false);
					Comentario.comentario.setEnabled(false);
				}

			}
		});

		Comentario.valPuntuacion
				.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						//Comentario.valPuntuacion.setEnabled(false);

					}
				});
	}

	public static void asignaAdpatador(String[] puntuacionesFinal) {
		if(puntuacionesFinal.length == 0){
			puntuacionesFinal = new String[1];
			puntuacionesFinal[0] = "No hay comentarios para este inmueble";
			
			adaptador = new ArrayAdapter<String>(ComunicadorGeneral.getActividad(),
					android.R.layout.simple_list_item_1, puntuacionesFinal);
		}else {
			adaptador = new ArrayAdapter<String>(ComunicadorGeneral.getActividad(),
					android.R.layout.simple_list_item_1, puntuacionesFinal);
		}
		listaComentario.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comentario, menu);
		return true;
	}

	public static void llenaListViewComentario(String[] c) {
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
				ComunicadorComentario.getActividad(),
				android.R.layout.simple_list_item_1, c);
		listaComentario.setAdapter(adaptador);
	}
	
		public void mensajeAlerta(String mensaje) {
		AlertDialog.Builder alerta = new AlertDialog.Builder(Comentario.this);
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
		
	
	

}
