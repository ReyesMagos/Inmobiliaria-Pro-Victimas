package co.gov.dps.inmovic.dominio.entidades;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Puntuacion implements KvmSerializable{
	
	private int id;
	private String idInmueble;
	private String fecha;
	private int puntuacion;
	private String comentario;
	
	public Puntuacion() {
		id = 0;
		idInmueble = "";
		puntuacion = 0;
		comentario = "";

	}
	
	public Puntuacion(int id, String idInmueble,  int puntuacion,
			String comentario) {
		super();
		this.id = id;
		this.idInmueble = idInmueble;
		
		this.puntuacion = puntuacion;
		this.comentario = comentario;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdInmueble() {
		return idInmueble;
	}

	public void setIdInmueble(String idInmueble) {
		this.idInmueble = idInmueble;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return id;

		case 1:
			return idInmueble;
		case 3:
			return fecha;
		case 4:
			return puntuacion;
		case 5:
			return comentario;
		default:
			break;
		}

		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo info) {
		switch (arg0) {
		case 0:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "id";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "IdInmueble";
			break;
		case 2:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "puntuacion";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "FechaCreacion";
			break;
		case 4:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "comentario";

		default:
			break;
		}
		
	}

	@Override
	public void setProperty(int arg0, Object val) {
		switch (arg0) {
		case 0:
			 id = Integer.parseInt(val.toString());
			break;
		case 1:
			idInmueble = val.toString();
			break;
		case 2:
			puntuacion = Integer.parseInt(val.toString());
			break;
		case 3:
			fecha = val.toString();
			break;
		case 4:
			comentario = val.toString();
			break;
		default:
			break;
		}
		
	}

}
