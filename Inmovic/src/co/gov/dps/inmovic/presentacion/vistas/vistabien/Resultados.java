package co.gov.dps.inmovic.presentacion.vistas.vistabien;

import java.util.ArrayList;
import java.util.List;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import co.gov.dps.inmovic.dominio.controladores.ControllerMapa;
import co.gov.dps.inmovic.dominio.controladores.ComunicadorGeneral;
import co.gov.dps.inmovic.dominio.controladores.ControllerResultados;
import co.gov.dps.inmovic.dominio.controladores.ServicioSoapController;
import co.gov.dps.inmovic.dominio.entidades.Bien;
import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.presentacion.actividades.destacados.Maps;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.Busqueda2;
import co.gov.dps.inmovic.presentacion.vistas.busqueda.SeleccionarTipoBusqueda;
import co.gov.dps.inmovic.presentacion.vistas.destacados.DestacadoUI;
import co.gov.dps.inmovic.presentacion.vistas.destacados.Formulario;
import co.gov.dps.inmovic.servicio.servicioweb.ConsultaInmueble;
import co.gov.dps.inmovic.servicio.servicioweb.RedSocial;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;

@SuppressLint("NewApi")
public class Resultados extends ActionBarActivity {

	static int mFlipping = 0;
	private static ImageView im1;
	private static ImageView im2;
	private static ImageView im3;
	private static ImageView im4;
	private static ImageView im5;
	private static ImageView im6;
	private static ImageView im7;
	private static ImageView btnComentar;
	private static ImageView start;
	private static ImageView btnMapa;
	private static ImageView s2;
	private static ImageView s3;
	static TextView txtNombre, txtDepto, txtMuni, txtNumba, txtNumpi, txtCanon,
			txtPer, txtTel, txtMail, txtMail2;
	BienInmobiliario inmo;
	static BienInmobiliario b;
	static BienALaVenta bV;
	static ViewFlipper flipper;
	int ju = 0;
	static float initX = 0;
	static Bitmap imagenesToshow[];
	private static ControllerResultados controladorResultado;
	private ServicioSoapController soap;
	private ImageView imgForm;
	Intent i;
	static Intent i2;
	private android.support.v7.app.ActionBar action;
	public static String inserto = "si";
	public static boolean mapeados;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ComunicadorGeneral.setActividad(this);
		
		action = ((Resultados) ComunicadorGeneral.getActividad())
				.getSupportActionBar();

		action.setDisplayHomeAsUpEnabled(true);
		controladorResultado = new ControllerResultados();
		controladorResultado.setResultadoActivity(Resultados.this);
		if (controladorResultado.getOpcionResultados() == 1) {
			setContentView(R.layout.activity_main);
			controladorResultado.setResultadoActivity(Resultados.this);
			if (controladorResultado.isLLamadoDesdeDestacados()) {
				action.setTitle("Destacados");
			} else {
				if (controladorResultado.verifyMapeando()) {
					action.setTitle("Mapa");
					mapeados = true;
				} else {
					action.setTitle("Busqueda");
				}
			}
			CargaElementosLayout();

		} else {
			setContentView(R.layout.activity_resultado2);
			controladorResultado.setResultadoActivity(Resultados.this);
			controladorResultado.llamarDesdeDestacados(false);

			action.setTitle("Busqueda");

			// action.setTitle("Bï¿½squeda");

			cargarELementosLayout2();
		}

	}

	public void btnCLickCompartirBienALaVenta(View v) {
		RedSocial compartir = new RedSocial();
		compartir.compartirRedSocial(this,
				organizeDescription(ComunicadorGeneral
						.getBienALaVentaAMostrar().getDescripcion())
						+ " esta disponible para comprar.\n "
						+ "Se encuentra ubicado en:  "
						+ ComunicadorGeneral.getBienALaVentaAMostrar()
								.getUbicacion() + " @UnidadVictimas ");
	}

	public void btnCLickCompartirBienInmueble(View v) {
		RedSocial compartir = new RedSocial();
		compartir.compartirRedSocial(this, ComunicadorGeneral.getBienAMostrar()
				.getNombredelbien()
				+ " esta disponible para arrendar. \n"
				+ "Se encuentra ubicado en:  "
				+ ComunicadorGeneral.getBienAMostrar().getDepartamento()
				+ "-"
				+ ComunicadorGeneral.getBienAMostrar().getMunicipio()
				+ " @UnidadVictimas ");
	}

	public String organizeDescription(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.toString(s.charAt(i)).equals(";")) {
				s = s.substring(0, i);
				return s;
			}
		}
		return s;
	}

	public void btnClickFormulario(View v) {
		ComunicadorGeneral.setBienALaVentaAMostrar(controladorResultado
				.traeBienALaVenta());
		Formulario.tipoBienSeleccionado = 1;
		i = new Intent(this, Formulario.class);
		startActivity(i);
	}

	// ================================================================================
	// Carga de elementos de la Vista Busqueda por Inmuebles
	// ================================================================================

	public void CargaElementosLayout() {
		txtNombre = (TextView) findViewById(R.id.txtNombre);
		txtDepto = (TextView) findViewById(R.id.txtDpto);
		txtMuni = (TextView) findViewById(R.id.txtMuni);
		txtNumba = (TextView) findViewById(R.id.txtNumba);
		txtNumpi = (TextView) findViewById(R.id.txtNumPieza);
		txtCanon = (TextView) findViewById(R.id.txtCanon);
		txtPer = (TextView) findViewById(R.id.txtPer);
		txtMail2 = (TextView) findViewById(R.id.txtMail2);
		txtTel = (TextView) findViewById(R.id.txtTel);
		flipper = (ViewFlipper) findViewById(R.id.flipper1);
		txtMail = (TextView) findViewById(R.id.txtMail);
		im1 = (ImageView) findViewById(R.id.im1);
		im2 = (ImageView) findViewById(R.id.im2);
		im3 = (ImageView) findViewById(R.id.im3);
		im4 = (ImageView) findViewById(R.id.im4);
		im5 = (ImageView) findViewById(R.id.im5);
		im6 = (ImageView) findViewById(R.id.im6);
		im7 = (ImageView) findViewById(R.id.im7);
		im1.setImageResource(R.drawable.logo_unidad);
		im2.setImageResource(R.drawable.logo_unidad);
		im3.setImageResource(R.drawable.logo_unidad);
		im4.setImageResource(R.drawable.logo_unidad);
		im5.setImageResource(R.drawable.logo_unidad);
		im6.setImageResource(R.drawable.logo_unidad);
		im7.setImageResource(R.drawable.logo_unidad);

		i2 = new Intent(controladorResultado.obtenerActividad(),
				Comentario.class);
		btnComentar = (ImageView) findViewById(R.id.btnComentario);
		btnComentar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ConsultaInmueble c = new ConsultaInmueble();
				c.execute();

				// controladorResultado.obtenerActividad().startActivity(i2);

			}
		});

		i = new Intent(this, Formulario.class);

		imgForm = (ImageView) findViewById(R.id.imageView2);
		imgForm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Formulario.tipoBienSeleccionado = 0;
				startActivity(i);

			}
		});
		controladorResultado.gestionaCargaElementoLayoutBieneInmobiliarios();

	}

	public static void llenarELementosConDatosd(BienInmobiliario bien) {
		b = bien;

		txtNombre.setText(b.getNombredelbien());
		txtDepto.setText(b.getDepartamento());
		txtMuni.setText(b.getMunicipio());
		txtNumba.setText(b.getNumBaño());
		txtNumpi.setText(b.getNumHabitacion());
		txtCanon.setText(b.getValor());
		txtPer.setText(b.getContacto());
		txtTel.setText(b.getFoliodematriculainmobiliaria());
		txtMail.setText(b.getAreacontruidamts2());
		txtMail2.setText(b.getDescripcion());

		controladorResultado.ordenaCargaImagenes();

	}

	public static void llenarELementosConDatosPorDefecto() {

		txtNombre.setText("Error");
		txtDepto.setText("Error");
		txtMuni.setText("Error");
		txtNumba.setText("Error");
		txtNumpi.setText("Error");
		txtCanon.setText("Error");
		txtPer.setText("Error");
		txtTel.setText("Error");
		txtMail.setText("Error");
		txtMail2.setText("Error");

	}

	public void cargarELementosLayout2() {
		flipper = (ViewFlipper) findViewById(R.id.flipper2);
		txtNombre = (TextView) findViewById(R.id.txtTipoV);
		txtDepto = (TextView) findViewById(R.id.txtDescripcionV);
		txtMuni = (TextView) findViewById(R.id.txtUbicacionV);
		txtNumba = (TextView) findViewById(R.id.txtInformacionV);
		txtCanon = (TextView) findViewById(R.id.txtCanonV);
		controladorResultado.gestionarCargaElementoLayoutBienALaVenta();
		im1 = (ImageView) findViewById(R.id.imv1);
		im2 = (ImageView) findViewById(R.id.imv2);
		im3 = (ImageView) findViewById(R.id.imv3);
		im4 = (ImageView) findViewById(R.id.imv4);
		im5 = (ImageView) findViewById(R.id.imv5);
		im6 = (ImageView) findViewById(R.id.imv6);
		im7 = (ImageView) findViewById(R.id.imv7);
		im1.setImageResource(R.drawable.logo_unidad);
		im2.setImageResource(R.drawable.logo_unidad);
		im3.setImageResource(R.drawable.logo_unidad);
		im4.setImageResource(R.drawable.logo_unidad);
		im5.setImageResource(R.drawable.logo_unidad);
		im6.setImageResource(R.drawable.logo_unidad);
		im7.setImageResource(R.drawable.logo_unidad);
	}

	public static void llenarELementosCondatos2(BienALaVenta bienventa) {

		txtNombre.setText(bienventa.getTipo());
		txtMuni.setText(bienventa.getUbicacion());
		txtNumba.setText(bienventa.getInformaciondecontacto());
		txtCanon.setText(bienventa.getValor());
		txtDepto.setText(bienventa.getDescripcion());

		controladorResultado.ordenaCargaImagenes2();
	}

	public static void llenarElementosConDatosPorDefecto2() {
		txtNombre.setText("Error");
		txtMuni.setText("Error");
		txtNumba.setText("Error");
		txtCanon.setText("Error");
		txtDepto.setText("Error");
	}

	public static void cargaImagenes(Bitmap[] iB) {
		if (iB == null) {
			return;
		}
		imagenesToshow = iB;
		if (iB[0] != null) {
			im1.setImageBitmap(iB[0]);
			im1.setOnClickListener(listener1);
		}
		if (iB[1] != null) {
			im2.setImageBitmap(iB[1]);
			im2.setOnClickListener(listener2);
		}
		if (iB[2] != null) {
			im3.setImageBitmap(iB[2]);
			im3.setOnClickListener(listener3);
		}
		if (iB[3] != null) {
			im4.setImageBitmap(iB[3]);
			im4.setOnClickListener(listener4);
		}
		if (iB[4] != null) {
			im5.setImageBitmap(iB[4]);
			im5.setOnClickListener(listener5);
		}
		if (iB[5] != null) {
			im6.setImageBitmap(iB[5]);
			im6.setOnClickListener(listener6);
		}
		if (iB[6] != null) {
			im7.setImageBitmap(iB[6]);
			im7.setOnClickListener(listener7);
		}

	}

	// ================================================================================
	// Listener de las Imagenes Encargados de cambiar las Imagenes que se
	// muestran
	// ================================================================================

	public static OnClickListener listener1 = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(final View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertadd = new AlertDialog.Builder(
					controladorResultado.obtenerActividad());
			LayoutInflater factory = LayoutInflater.from(controladorResultado
					.obtenerActividad());
			final View view = factory.inflate(R.layout.imagen_show, null);
			ImageView imd = (ImageView) view.findViewById(R.id.imageShow);
			if (imagenesToshow[0] != null) {
				imd.setImageBitmap(imagenesToshow[0]);
			}

			alertadd.setNeutralButton("Cerrar",
					new DialogInterface.OnClickListener() {
						@SuppressLint("NewApi")
						public void onClick(DialogInterface dlg, int sumthin) {

						}
					});
			alertadd.setView(view);
			alertadd.show();

		}

	};

	public static OnClickListener listener2 = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(final View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertadd = new AlertDialog.Builder(
					controladorResultado.obtenerActividad());
			LayoutInflater factory = LayoutInflater.from(controladorResultado
					.obtenerActividad());
			final View view = factory.inflate(R.layout.imagen_show, null);
			ImageView imd = (ImageView) view.findViewById(R.id.imageShow);
			if (imagenesToshow[1] != null) {
				imd.setImageBitmap(imagenesToshow[1]);
			}

			alertadd.setNeutralButton("Cerrar",
					new DialogInterface.OnClickListener() {
						@SuppressLint("NewApi")
						public void onClick(DialogInterface dlg, int sumthin) {

						}
					});
			alertadd.setView(view);
			alertadd.show();

		}

	};

	public static OnClickListener listener3 = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(final View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertadd = new AlertDialog.Builder(
					controladorResultado.obtenerActividad());
			LayoutInflater factory = LayoutInflater.from(controladorResultado
					.obtenerActividad());
			final View view = factory.inflate(R.layout.imagen_show, null);
			ImageView imd = (ImageView) view.findViewById(R.id.imageShow);
			if (imagenesToshow[2] != null) {
				imd.setImageBitmap(imagenesToshow[2]);
			}

			alertadd.setNeutralButton("Cerrar",
					new DialogInterface.OnClickListener() {
						@SuppressLint("NewApi")
						public void onClick(DialogInterface dlg, int sumthin) {

						}
					});
			alertadd.setView(view);
			alertadd.show();

		}

	};

	public static OnClickListener listener4 = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(final View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertadd = new AlertDialog.Builder(
					controladorResultado.obtenerActividad());
			LayoutInflater factory = LayoutInflater.from(controladorResultado
					.obtenerActividad());
			final View view = factory.inflate(R.layout.imagen_show, null);
			ImageView imd = (ImageView) view.findViewById(R.id.imageShow);
			if (imagenesToshow[3] != null) {
				imd.setImageBitmap(imagenesToshow[3]);
			}

			alertadd.setNeutralButton("Cerrar",
					new DialogInterface.OnClickListener() {
						@SuppressLint("NewApi")
						public void onClick(DialogInterface dlg, int sumthin) {

						}
					});
			alertadd.setView(view);
			alertadd.show();

		}

	};

	public static OnClickListener listener5 = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(final View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertadd = new AlertDialog.Builder(
					controladorResultado.obtenerActividad());
			LayoutInflater factory = LayoutInflater.from(controladorResultado
					.obtenerActividad());
			final View view = factory.inflate(R.layout.imagen_show, null);
			ImageView imd = (ImageView) view.findViewById(R.id.imageShow);
			if (imagenesToshow[4] != null) {
				imd.setImageBitmap(imagenesToshow[4]);
			}

			alertadd.setNeutralButton("Cerrar",
					new DialogInterface.OnClickListener() {
						@SuppressLint("NewApi")
						public void onClick(DialogInterface dlg, int sumthin) {

						}
					});
			alertadd.setView(view);
			alertadd.show();

		}

	};

	public static OnClickListener listener6 = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(final View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertadd = new AlertDialog.Builder(
					controladorResultado.obtenerActividad());
			LayoutInflater factory = LayoutInflater.from(controladorResultado
					.obtenerActividad());
			final View view = factory.inflate(R.layout.imagen_show, null);
			ImageView imd = (ImageView) view.findViewById(R.id.imageShow);
			if (imagenesToshow[5] != null) {
				imd.setImageBitmap(imagenesToshow[5]);
			}

			alertadd.setNeutralButton("Cerrar",
					new DialogInterface.OnClickListener() {
						@SuppressLint("NewApi")
						public void onClick(DialogInterface dlg, int sumthin) {

						}
					});
			alertadd.setView(view);
			alertadd.show();

		}

	};

	public static OnClickListener listener7 = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(final View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertadd = new AlertDialog.Builder(
					controladorResultado.obtenerActividad());
			LayoutInflater factory = LayoutInflater.from(controladorResultado
					.obtenerActividad());
			final View view = factory.inflate(R.layout.imagen_show, null);
			ImageView imd = (ImageView) view.findViewById(R.id.imageShow);
			if (imagenesToshow[6] != null) {
				imd.setImageBitmap(imagenesToshow[6]);
			}

			alertadd.setNeutralButton("Cerrar",
					new DialogInterface.OnClickListener() {
						@SuppressLint("NewApi")
						public void onClick(DialogInterface dlg, int sumthin) {

						}
					});
			alertadd.setView(view);
			alertadd.show();

		}

	};

	// ================================================================================
	// Animaciones Para la Trancicion de imagenes
	// ================================================================================

	public void MueveADerecha(View v) {
		flipper.setOutAnimation(outToLeftAnimation());
		flipper.showPrevious();

	}

	public void mueveAIzquierda(View v) {
		flipper.setOutAnimation(outToRightAnimation());
		flipper.showNext();

	}

	private static Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);

		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new AccelerateInterpolator());

		return inFromRight;

	}

	private static Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	private static Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	// ********************************************************************************
	public static void mensajeDeAlerta(String mensaje, String titulo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				controladorResultado.obtenerActividad());
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

	public void btnMapa(View v) {
		if (ComunicadorGeneral.bienAMostrar == null) {
			return;
		}
		List<BienInmobiliario> liss = new ArrayList<BienInmobiliario>();
		liss.add(ComunicadorGeneral.bienAMostrar);
		ControllerMapa.setLi(liss);

		Intent dos = new Intent(Resultados.this, Maps.class);
		startActivity(dos);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		controladorResultado.cancelarCargaDeImagenes();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ================================================================================
	// Metodos para cuando se vuewlve a la aplicacion
	// ================================================================================
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		controladorResultado.setResultadoActivity(this);
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		controladorResultado.setResultadoActivity(this);
	}

	// ********************************************************************************

	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			// se crea el intent adecuado para esta accion y se llama la
			// acitvidad

			Intent upIntent;
			if (mapeados) {
				upIntent = new Intent(this, Maps.class);
				mapeados = false;
			} else if (controladorResultado.getOpcionResultados() == 1) {
				upIntent = new Intent(this, Busqueda.class);
			} else {
				upIntent = new Intent(this, Busqueda2.class);
				controladorResultado.llamarDesdeDestacados(false);

			}

			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				android.support.v4.app.TaskStackBuilder.from(this)
						.addNextIntent(upIntent).startActivities();

				finish();
			} else {
				if (controladorResultado.isLLamadoDesdeDestacados()
						&& controladorResultado.getOpcionResultados() != 2) {
					upIntent = new Intent(this, DestacadoUI.class);
					controladorResultado.obtenerActividad().startActivity(
							upIntent);
				}
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	public void setInserto(String inserto) {
		this.inserto = inserto;
	}

	public String getInserto() {
		return inserto;
	}

	public static void iniciarActividad() {
		i2 = new Intent(controladorResultado.obtenerActividad(),
				Comentario.class);
		controladorResultado.obtenerActividad().startActivity(i2);

	}

	public static ControllerResultados getControladorResultado() {
		return controladorResultado;
	}

	public static void mensajeAlertaError(String msn) {

		AlertDialog.Builder alerta = new AlertDialog.Builder(
				controladorResultado.obtenerActividad());
		alerta.setTitle("Error");
		alerta.setMessage(msn);
		alerta.setIcon(R.drawable.icono);

		alerta.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) { //

					}
				});
		alerta.show();

	}

}
