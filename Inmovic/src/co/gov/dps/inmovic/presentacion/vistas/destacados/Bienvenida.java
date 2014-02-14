package co.gov.dps.inmovic.presentacion.vistas.destacados;


import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Bienvenida extends Activity{
	
	final int WELCOME = 25;
	TextView lineaAyuda;
	ProgressBar barraProgreso;
	int progreso = 0;
	int paso = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bienvenida);

		barraProgreso = (ProgressBar)findViewById(R.id.progressbar);
		lineaAyuda = (TextView) findViewById(R.id.bienvenida);
	}

	@Override
	protected void onResume() {
		super.onResume();

		lineaAyuda.setText("Cargando...");
		cuentaAtras(3000);

	}

	public void cuentaAtras(long milisegundos) {
		CountDownTimer mCountDownTimer;
		barraProgreso.setMax((int) milisegundos);
		barraProgreso.setProgress(paso);

		mCountDownTimer = new CountDownTimer(milisegundos, paso) {

			@Override
			public void onTick(long arg0) {
				Log.v("Log_tag", "Tick of progress" + progreso + arg0);
				progreso += paso;
				barraProgreso.setProgress(progreso);

			}

			@Override
			public void onFinish() {
				Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG)
						.show();
				progreso += paso;
				barraProgreso.setProgress(progreso);
				barraProgreso.setVisibility(View.INVISIBLE);
				Intent i = new Intent(
						"co.gov.dps.inmovic.presentacion.vistas.destacados.DestacadoUI");
				startActivityForResult(i, WELCOME);
			}
		};

		mCountDownTimer.start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == WELCOME)
			finish();
		else
			super.onActivityResult(requestCode, resultCode, data);
	}

}
