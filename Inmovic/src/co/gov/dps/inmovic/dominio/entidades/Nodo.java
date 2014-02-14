package co.gov.dps.inmovic.dominio.entidades;

public class Nodo {

	private int valor;
	private String parametro ;
	private Nodo liga;
	
	public Nodo(){
		valor =0;
		parametro= null;
		liga= null;
	}
	
	public Nodo(int v, String b){
		valor = v;
		parametro = b;
	}
	
	public Nodo(int v, String b, Nodo l){
		valor = v;
		parametro = b;
		liga =l;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getBien() {
		return parametro;
	}

	public void setBien(String bien) {
		this.parametro = bien;
	}

	public Nodo getLiga() {
		return liga;
	}

	public void setLiga(Nodo liga) {
		this.liga = liga;
	}
	
	
	
	

}
