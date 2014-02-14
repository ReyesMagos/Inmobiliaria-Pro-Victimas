package co.gov.dps.inmovic.dominio.entidades;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Destacado {

	private String tipo;
	private String nombre;
	private String canon;
	private Bitmap imagen;
	private String ubicacion;
	

	public Destacado(String tipo, String nombre, String canon, String ubicacion,Bitmap imagen) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.canon = canon;
		this.ubicacion = ubicacion;
		this.imagen= imagen;

	}
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCanon() {
		return canon;
	}

	public void setCanon(String canon) {
		this.canon = canon;
	}

	public Bitmap getImagen() {
		return imagen;
	}

	public void setImagen(Bitmap imagen) {
		this.imagen = imagen;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

}
