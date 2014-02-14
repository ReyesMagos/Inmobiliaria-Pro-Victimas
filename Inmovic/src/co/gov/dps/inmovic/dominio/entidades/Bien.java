package co.gov.dps.inmovic.dominio.entidades;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Bien {

	public String partitionKey;
	public String rowKey;
	public String descripcion;
	public String valor;
	public String tipo;
	public String[] urlImagenes;
	public Bitmap imagenInicial;
	public Bitmap[] imagenes;

	public Bien() {

	}

	public Bien(String partitionKey, String rowKey, String descripcion,
			String valor) {
		super();
		this.partitionKey = partitionKey;
		this.rowKey = rowKey;
		this.descripcion = descripcion;
		this.valor = valor;
	}

	public String getPartitionKey() {
		return partitionKey;
	}

	public void setPartitionKey(String partitionKey) {
		this.partitionKey = partitionKey;
	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String[] getUrlImagenes() {
		return urlImagenes;
	}

	public void setUrlImagenes(String[] urlImagenes) {
		this.urlImagenes = urlImagenes;
	}

	public Bitmap getImagenInicial() {
		return imagenInicial;
	}

	public void setImagenInicial(Bitmap imagenInicial) {
		this.imagenInicial = imagenInicial;
	}

	public Bitmap[] getImagenes() {
		return imagenes;
	}

	public void setImagenes(Bitmap[] imagenes) {
		this.imagenes = imagenes;
	}

}
