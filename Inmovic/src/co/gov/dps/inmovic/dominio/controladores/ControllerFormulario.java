package co.gov.dps.inmovic.dominio.controladores;

import android.app.Activity;

import com.google.android.gms.internal.co;

import co.gov.dps.inmovic.dominio.entidades.BienALaVenta;
import co.gov.dps.inmovic.dominio.entidades.BienInmobiliario;
import co.gov.dps.inmovic.presentacion.vistas.destacados.Formulario;

public class ControllerFormulario {

	public String nombre;
	public String apell;
	public String num;
	public String city;
	public String telefono;
	public String valCanon;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApell() {
		return apell;
	}

	public void setApell(String apell) {
		this.apell = apell;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getValCanon() {
		return valCanon;
	}

	public String getValorReal() {
		if (ComunicadorGeneral.getBienAMostrar() != null) {
			return ComunicadorGeneral.getBienAMostrar().getValor();
		} else if (ComunicadorGeneral.getBienALaVentaAMostrar() != null) {
			return ComunicadorGeneral.getBienALaVentaAMostrar().getValor();
		}
		else return "Error al calcular";
		
	}

	public void setValCanon(String valCanon) {
		this.valCanon = valCanon;
	}

	public void eraseData() {
		nombre = null;
		apell = null;
		num = null;
		city = null;
		telefono = null;
		valCanon = null;

	}

	public boolean verificarInformation() {
		if (getNombre() == null || getNombre().equals("")) {
			Formulario.mensajeAlerta("Por favor Ingrese su nombre");
			return false;
		}

		if (getApell() == null || getApell().equals("")) {
			Formulario.mensajeAlerta("Por favor Ingrese su apellido");
			return false;
		}

		if (getNum() == null || getNum().equals("")) {
			Formulario
					.mensajeAlerta("Por favor Ingrese su documento de Identidad");
			return false;
		} else if (!criticaCamposTexto(getNum())) {
			Formulario
					.mensajeAlerta("Por favor verifique el documento de identidad.");

			return false;
		}

		if (getCity() == null || getCity().equals("")) {
			Formulario.mensajeAlerta("Por favor Ingrese la Ciudad");
			return false;
		}

		if (getTelefono() == null || getTelefono().equals("")) {
			Formulario.mensajeAlerta("Por favor Ingrese un número de Teléfono");
			return false;
		} else if (!criticaCamposTexto(getTelefono())) {
			Formulario
					.mensajeAlerta("Por favor Ingrese un número de Teléfono valido");

			return false;

		}

		if (getValCanon() == null || getValCanon().equals("")) {
			Formulario.mensajeAlerta("Por favor Ingrese un valor para el "
					+ " Inmueble");
			return false;
		} else if (!criticaCamposTexto(getValCanon())
				&& !getValCanon().equals("")) {
			valCanon = "En actualización";
		} else if (!criticaCamposTexto(getValCanon())) {
			Formulario
					.mensajeAlerta("Por favor Ingrese un valor valido para el Inmueble");
			return false;
		} else if (Formulario.tipoBienSeleccionado == 0
				&& tryParse(getBienInmobiliarioThatWillBeShowed().getValor())
				&& Integer.parseInt(getValCanon()) < Integer
						.parseInt(getBienInmobiliarioThatWillBeShowed()
								.getValor())) {
			Formulario.mensajeAlerta("Solo se debe proponer un valor mayor a: "
					+ getValorReal());
			return false;

		} else if (Formulario.tipoBienSeleccionado == 1
				&& tryParse(getBienALaVentaThatWillBeShowed().getValor())
				&& Integer.parseInt(getValCanon()) < Integer
						.parseInt(getBienALaVentaThatWillBeShowed().getValor())) {
			Formulario.mensajeAlerta("Solo se debe proponer un valor mayor a: "
					+ getValorReal());
			return false;

		}
		return true;
	}

	public Activity getGeneralActivity() {
		return ComunicadorGeneral.getActividad();
	}

	public void setActivityFormulario(Activity actividad) {
		ComunicadorGeneral.setActividad(actividad);

	}

	public BienInmobiliario getBienInmobiliarioThatWillBeShowed() {
		return ComunicadorGeneral.getBienAMostrar();
	}

	public BienALaVenta getBienALaVentaThatWillBeShowed() {
		return ComunicadorGeneral.getBienALaVentaAMostrar();
	}

	public boolean tryParse(String s) {
		try {
			Integer.parseInt(s);

		} catch (Exception ex) {
			return false;
		}
		return true;
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

}
